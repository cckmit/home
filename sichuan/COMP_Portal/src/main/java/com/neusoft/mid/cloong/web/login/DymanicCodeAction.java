/*******************************************************************************
 * @(#)LoginAction.java 2009-5-27
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Random;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.core.DymanicCode;
import com.neusoft.mid.cloong.core.DymanicCodeMap;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 动态密码登录
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月8日 下午3:02:51
 */
public class DymanicCodeAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(DymanicCodeAction.class);

    /**
     * 登录输入的用户id
     */
    private String userId1;

    /**
     * 用户手机号码
     */
    private String phoneNum;
    /**
     * 动态密码错误信息
     */
    private String errMsg1;
    /**
     * 超时时间
     */
    private int timeout;
    /**
     * 用户登录时,首先判断用户是否可用，然后判断id和password是否正确；如果不正确，则提示错误并返回登录页面
     * @return String
     * @throws Exception
     */
    public String execute() {
        if(!checkUser()){
            return  ConstantEnum.ERROR.toString();
        }
      //创建随机类
        Random r = new Random();
        //随机产生4位数字的验证码
        DymanicCode code = new DymanicCode();
        StringBuffer rs = new StringBuffer();;
        String rn = "";
        for (int i = 0; i < 4; i++) {
            //产生10以内的随机数字rn
            rn = String.valueOf(r.nextInt(10));
            rs.append(rn);
        }
        code.setCode(rs.toString());
        code.setTimestamp(System.currentTimeMillis());
        DymanicCodeMap.getInstance().put(userId1+"_"+phoneNum, code);
        timeout = Constants.DYMANICCODETIMEOUT/1000;
        logger.info("code>>=="+code.getCode()+",userId>>=="+userId1+",phoneNum>>=="+phoneNum);
        return ConstantEnum.SUCCESS.toString();
    }
    /**
     * 校验用户是否合法
     * @return 是否合法
     */
    private boolean checkUser(){
        boolean userstate = true;
        UserBean userInfo = null;
        String tempUserId = getUserId1().trim();

        // 从数据库中读取与用户输入的用户ID对应的用户信息
        try {
            userInfo = (UserBean) ibatisDAO.getSingleRecord("getSingleUser", tempUserId);
        } catch (SQLException e) {
            errMsg1 = MessageFormat.format(getText("read.error"), tempUserId);
            this.addActionError(errMsg1);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg1 + e.getMessage(), e);
            userstate = false;
        }
        if (null == userInfo) {
            errMsg1 = getText("user.not.exist");
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
            ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
            userstate = false;
        }
        if(!phoneNum.equals(userInfo.getMobile())){
            errMsg1 = getText("user.mobile.not.match");
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg1);
            ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), errMsg1);
            userstate = false;
        }
        return userstate;
    }
    

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
}
