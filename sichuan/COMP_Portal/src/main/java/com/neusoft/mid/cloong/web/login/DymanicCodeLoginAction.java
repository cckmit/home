/*******************************************************************************
 * @(#)LoginAction.java 2009-5-27
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.core.DymanicCode;
import com.neusoft.mid.cloong.core.DymanicCodeMap;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 动态密码登录
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月8日 下午3:02:51
 */
public class DymanicCodeLoginAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 毫秒与分钟转换
     */
    private static final long MS = 360000;

    /**
     * 失败页面
     */
    private static final String RET_FAILURE = "FAILURE";

    /**
     * 无效页面
     */
    private static final String RET_INVALID = "INVALID";

    /**
     * 成功页面
     */
    private static final String RET_SUCCESS = "SUCCESS";

    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(DymanicCodeLoginAction.class);

    /**
     * 登录输入的用户id
     */
    private String userId1;

    /**
     * 登录输入的密码
     */
    private String password1;

    /**
     * 登录输入的校验码
     */
    private String verify1;
    /**
     * 用户手机号码
     */
    private String phoneNum;
    /**
     * 动态密码错误信息
     */
    private String errMsg1;

    /**
     * 用户登录时,首先判断用户是否可用，然后判断id和password是否正确；如果不正确，则提示错误并返回登录页面
     * @return String
     * @throws Exception
     */
    public String execute() {
        // 校验输入
        if (!validateInputPara()) {
            return RET_INVALID;
        }
        //校验用户名密码
        UserBean userInfo;
        String tempUserId = getUserId1().trim();

        // 从数据库中读取与用户输入的用户ID对应的用户信息
        try {
            userInfo = (UserBean) ibatisDAO.getSingleRecord("getSingleUser", tempUserId);
        } catch (SQLException e) {
            errMsg1 = MessageFormat.format(getText("read.error"), tempUserId);
            this.addActionError(errMsg1);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg1 + e.getMessage(), e);
            return RET_FAILURE;
        }
        return dymanicLogin(userInfo);
    }
    
    
    
    /**
     * 动态密码登陆
     * @param userInfo 用户信息
     * @return 操作状态
     */
    private String dymanicLogin(UserBean userInfo) {
        if (null == userInfo) {
            errMsg1 = getText("user.not.exist");
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
            ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
            return RET_INVALID; 
        }
            if(phoneNum.equals(userInfo.getMobile())){
                String key = userInfo.getUserId()+"_"+userInfo.getMobile();
                if(DymanicCodeMap.getInstance().containsKey(key)){
                    DymanicCode code = DymanicCodeMap.getInstance().get(key);
                    if(password1.equals(code.getCode())){
                        if(!isTimeout(code.getTimestamp())){
                            return checkUserState(userInfo);
                        } else {
    //                        动态密码失效
                            errMsg1 = getText("user.dymaniccode.timeout");
                            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
                            ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
                        }
                    } else {
    //                    动态密码错误
                        errMsg1 = getText("user.dymaniccode.not.match");
                        logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
                        ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
                    }
                } else {
    //                动态密码不存在
                    errMsg1 = getText("user.dymaniccode.not.exist");
                    logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
                    ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
                }
            } else {
    //            用户名手机号不匹配
                errMsg1 = getText("user.mobile.not.match");
                logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
                ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
            }
        return RET_INVALID;
    }
    /**
     * isTimeout 判断动态密码是否超时
     * @param timestamp 动态密码生成时间
     * @return 返回true or false
     */
    private boolean isTimeout(long timestamp){
        long diff = System.currentTimeMillis()-timestamp;
        if(diff > Constants.DYMANICCODETIMEOUT){
            return true;
        }
        return false;
    }
    /**
     * 
     * updateUserStatus 登录校验用户状态时需更新status，loginFaildNum，lockTime
     * @param userInfo 
     */
    private void updateUserStatus(UserBean userInfo) {
        try {
            ibatisDAO.updateData("updateUserState", userInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改用户状态、失败登录次数、锁定时间失败", e);
        }
    }
    /**
     * 校验用户状态
     * @param userInfo 用户信息
     * @return 处理结果
     */
    private String checkUserState(UserBean userInfo){
        if ((UserStatus.DISABLE).equals(userInfo.getStatus())) { // 用户状态为停用，不让登
            errMsg1 = getText("user.cannot.login");
            ActionContext.getContext().getSession()
                    .put(SessionKeys.invalid.toString(), errMsg1);
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
        } else if ((UserStatus.LOCK).equals(userInfo.getStatus())) { // 锁定
            Date date = new Date();
            SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMddHHmmss");
            long lastTime = 0;
            long cc;
            try {
                if (1 == userInfo.getLockTime().split("-").length) {
                    lastTime = timeformat.parse(userInfo.getLockTime()).getTime();
                } else {
                    lastTime = timeformat.parse(userInfo.getLockTime().substring(userInfo.getLockTime().lastIndexOf("-")).substring(1, 15)).getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cc = date.getTime() - lastTime;
            if (cc >= Constants.LOCKTIME) { // 上次锁定时间距离现在时间大于等于10分钟，自动解锁
                userInfo.setStatus(UserStatus.ENABLE); // 状态变为启用，解锁成功
                userInfo.setLoginFailedTime("");
                userInfo.setLockTime("");
                
                updateUserStatus(userInfo); //更新用户状态至DB
                
            } else { // 没到解锁时间，不让登录系统
                errMsg = MessageFormat.format(getText("user.lock.cannot.login"),Constants.LOCKTIME/MS);
                ActionContext.getContext().getSession()
                        .put(SessionKeys.invalid.toString(), errMsg1);
                logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
            }
        } else if ((UserStatus.CANCELLATION).equals(userInfo.getStatus())) { // 销户
            errMsg1 = getText("user.delete.cannot.login");
            ActionContext.getContext().getSession()
                    .put(SessionKeys.invalid.toString(), errMsg1);
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
        } else if ((UserStatus.EXAMINATION).equals(userInfo.getStatus())) { // 待审批
            errMsg1 = getText("user.audit.cannot.login");
            ActionContext.getContext().getSession()
                    .put(SessionKeys.invalid.toString(), errMsg1);
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
            
        } else if ((UserStatus.ENABLE).equals(userInfo.getStatus())) { // 启用  数据清零
            userInfo.setLoginFailedTime("");
            userInfo.setLockTime("");
            updateUserStatus(userInfo); //更新用户状态至DB
            ActionContext.getContext().getSession() .put(SessionKeys.userInfo.toString(), userInfo);
            String operationInfo = MessageFormat.format(getText("user.login.success"),
            userInfo.getUserId(), userInfo.getUserName());
            logger.info(operationInfo);
            return RET_SUCCESS;
        }
        return RET_INVALID;
    }
    

    /**
     * 
     * validateInputPara 校验验证码和用户名、密码是否为空
     * @return boolean
     */
    private boolean validateInputPara() {
        if (StringUtils.isEmpty(verify1)){
            errMsg1=getText("user.name.verify.null");
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1,
                    null);
            ActionContext.getContext().getSession()
            .put(SessionKeys.invalid.toString(), errMsg1);
            return false;
        }
        //校验验证码
        String sessionRS = (String) ActionContext.getContext().getSession().get("random");
        if (verify1.equals(sessionRS)) {
            if (StringUtils.isEmpty(userId1) || StringUtils.isEmpty(phoneNum)||StringUtils.isEmpty(password1))  {
                errMsg1=getText("user.name.psw.null");
                logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1,
                            null);
                ActionContext.getContext().getSession()
                    .put(SessionKeys.invalid.toString(), errMsg1);
             } else {
                 return true;
             }
        } else {
            errMsg1 = getText("user.name.verify.error");
            ActionContext.getContext().getSession()
                    .put(SessionKeys.invalid.toString(), errMsg1);
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
        }
        return false;
    }


    public String getUserId1() {
        return userId1;
    }


    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }


    public String getPassword1() {
        return password1;
    }


    public void setPassword1(String password1) {
        this.password1 = password1;
    }


    public String getVerify1() {
        return verify1;
    }


    public void setVerify1(String verify1) {
        this.verify1 = verify1;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }



    public String getErrMsg1() {
        return errMsg1;
    }



    public void setErrMsg1(String errMsg1) {
        this.errMsg1 = errMsg1;
    }
    

}
