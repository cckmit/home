/*******************************************************************************
 * @(#)ShortMessageConfirmAction.java 2014年1月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.identity.bean.SMorMailValidateBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 找回密码之短信验证码校验
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月13日 下午3:43:58
 */
public class ShortMessageConfirmAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7832317836709775649L;

    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(ShortMessageConfirmAction.class);
    
    /**
     * 页面输入短信验证码信息
     */
    private String shortMessage;
    
    /**
     * 页面隐藏的用户id
     */
    private String userId;
    
    /**
     * 短信或者邮件找回密码时生成的动态密钥信息
     */
    private SMorMailValidateBean smMailValidateInfo =  new SMorMailValidateBean();
    
    
    /**
     * 重新获取短信验证码页面
     */
    private static final String REPEATSHORTMESSAGE = "REPEATSHORTMESSAGE";
    
    /**
     * 
     * execute 找回密码之校验短信验证码
     * @return 如果与网关一致进入重置密码，如果不一致在原界面打印错误信息（前提需查看有效时间）
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        smMailValidateInfo.setKeyWord(getShortMessage());
        smMailValidateInfo.setUserId(userId);
        try {
            smMailValidateInfo = (SMorMailValidateBean)ibatisDAO.getSingleRecord("getSMvalidation", smMailValidateInfo);
        } catch (SQLException e) {
            errMsg = MessageFormat.format(getText("read.error"), userId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e.getMessage(), e);
        } catch (Exception e1) {
            errMsg = MessageFormat.format(getText("read.error"), userId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e1.getMessage(), e1);
        }
        if (null == smMailValidateInfo) { // 数据库中没有输入的验证码
            errMsg = getText("shortMessage.input.failed");
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } else {
            Date date = new Date();
            SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMddHHmmss");
            long lostTime = 0;
            long cc;
            try {
                lostTime = timeformat.parse(smMailValidateInfo.getTimeStamp()).getTime();
            } catch (ParseException e) {
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "时间解析错误", e);
            }
            cc = date.getTime() - lostTime;
            if (Long.parseLong(Constants.SAVETIME)*60*1000 < cc) { // 校验时间超过有效期
                deleteSmMailValidateInfo();
                errMsg = getText("shortMessage.input.validate.failed"); // 验证码失效
                this.addActionError(errMsg);
                return REPEATSHORTMESSAGE; // 返回重新获取验证码页面
            } 
        }
        deleteSmMailValidateInfo();
        
        return ConstantEnum.SUCCESS.toString(); // 验证码验证正确，进入重置密码界面 
    }

    /**
     * deleteSmMailValidateInfo 校验成功后删除数据库中数据
     */
    private void deleteSmMailValidateInfo() {
        try {
            ibatisDAO.deleteData("deleteSmMailValidateInfo", smMailValidateInfo);
        } catch (SQLException e) {
            errMsg = MessageFormat.format("删除数据库存短信验证码或者邮件url出错！", userId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e.getMessage(), e);
        } catch (Exception e1) {
            errMsg = MessageFormat.format("删除数据库存短信验证码或者邮件url出错！", userId);
            logger.error(LoginStatusCode.LOGIN_EXCEPTION, errMsg + e1.getMessage(), e1);
        }
    }
    
    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public SMorMailValidateBean getSmMailValidateInfo() {
        return smMailValidateInfo;
    }

    public void setSmMailValidateInfo(SMorMailValidateBean smMailValidateInfo) {
        this.smMailValidateInfo = smMailValidateInfo;
    }
    
}
