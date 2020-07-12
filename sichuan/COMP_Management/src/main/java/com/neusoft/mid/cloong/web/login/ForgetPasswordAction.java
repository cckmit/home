/*******************************************************************************
 * @(#)ForgetPasswordAction.java 2014年1月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.SMorMailValidateBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.ForgetPasswordService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 找回密码
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月13日 下午2:02:00
 */
public class ForgetPasswordAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4122044167767001589L;
    
    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(ForgetPasswordAction.class);


    /**
     * 页面传入用户id
     */
    private String userId;
    
    /**
     * 页面传入短信or邮件方式找回密码
     */
    private String radio;
    
    /**
     * 短信页面
     */
    private static final String SHORTMESSAGE = "SHORTMESSAGE";

    /**
     * email页面
     */
    private static final String EMAIL = "EMAIL";
    
    /**
     * 用户bean
     */
    private UserBean userInfo = new UserBean();
    
    /**
     * 短信或者邮件找回密码时生成的动态密钥信息
     */
    private SMorMailValidateBean smMailValidateInfo =  new SMorMailValidateBean();
    
    /**
     * 发送邮件接口
     */
    private ForgetPasswordService forgetPasswordService;
    
    /**
     * 
     * execute 找回密码校验用户名和判断短信or邮件找回
     * @return error：在原页面打印错误日志；success：跳转短信页面
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        String tempUserId = checkExistUserid();
        if (null == userInfo) { // 用户ID不存在
            errMsg = MessageFormat.format(getText("user.not.exist"), tempUserId);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString(); // 页面提示错误信息
        } else  {
            if ("shortMessage".equals(getRadio())) {
                double random = Math.round(Math.random()*9000+1000);
                String mailBody = "尊敬的用户,您申请找回密码的动态验证码为" + String.valueOf(random).substring(0, 4) + "有效期为" + Constants.SAVETIME + "分钟"
                        + "请妥善保管";
                String receiver = userInfo.getMobile();
                // TODO 下发短信 communciate.writeMessage(receiver, mailBody.getBytes("UTF-16BE"));
                logger.info("用户" + userInfo.getUserId() + "短信验证码 " + random + "已下发");
                String lostTime = DateParse.generateDateFormatyyyyMMddHHmmss();
                smMailValidateInfo.setKeyWord(String.valueOf(String.valueOf(random).substring(0, 4)));
                insertSMorMailTime(tempUserId, lostTime);
                return SHORTMESSAGE; // 进入输入短信验证码页面
            } else if ("email".equals(getRadio())) {
                String lostTime = DateParse.generateDateFormatyyyyMMddHHmmss();
                try {
                    forgetPasswordService.runMail(lostTime, userInfo);
                } catch (Exception e) {
                    logger.error("忘记密码发送邮件错误", e);
                }
                
                logger.info("用户" + userInfo.getUserId() + "找回密码链接为 " + Constants.FINDPASSWORDURL + "uuid="+Base64.encode(tempUserId)+"&lostTime="+Base64.encode(lostTime));
                smMailValidateInfo.setKeyWord("uuid="+Base64.encode(tempUserId)+"&lostTime="+Base64.encode(lostTime));
                insertSMorMailTime(tempUserId, lostTime);
                return EMAIL; 
            }
        }
        return SHORTMESSAGE;
    }
    
    /**
     * 
     * insertSMorMailTime 往数据库中存短信验证码或者邮件url
     * @param tempUserId 用户id
     * @param lostTime 申请找回密码时间
     */
    private void insertSMorMailTime(String tempUserId, String lostTime) {
        try {
            smMailValidateInfo.setUserId(tempUserId);
            smMailValidateInfo.setTimeStamp(lostTime);
            ibatisDAO.insertData("insertSMorMailTime", smMailValidateInfo);
        } catch (SQLException e) {
            errMsg = MessageFormat.format("往数据库存短信验证码或者邮件url出错！", tempUserId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e.getMessage(), e);
        } catch (Exception e1) {
            errMsg = MessageFormat.format("往数据库存短信验证码或者邮件url出错！", tempUserId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e1.getMessage(), e1);
        }
    }

    /**
     * 
     * checkExistUserid 检测用户id是否存在
     * @return tempUserId
     */
    private String checkExistUserid() {
        String tempUserId = getUserId().trim();
        try {
            userInfo = (UserBean) ibatisDAO.getSingleRecord("getSingleUser", tempUserId);
        } catch (SQLException e) {
            errMsg = MessageFormat.format(getText("read.error"), tempUserId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e.getMessage(), e);
        } catch (Exception e1) {
            errMsg = MessageFormat.format(getText("read.error"), tempUserId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e1.getMessage(), e1);
        }
        return tempUserId;
    }

    public UserBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public SMorMailValidateBean getSmMailValidateInfo() {
        return smMailValidateInfo;
    }

    public void setSmMailValidateInfo(SMorMailValidateBean smMailValidateInfo) {
        this.smMailValidateInfo = smMailValidateInfo;
    }

    public ForgetPasswordService getForgetPasswordService() {
        return forgetPasswordService;
    }

    public void setForgetPasswordService(ForgetPasswordService forgetPasswordService) {
        this.forgetPasswordService = forgetPasswordService;
    }

}
