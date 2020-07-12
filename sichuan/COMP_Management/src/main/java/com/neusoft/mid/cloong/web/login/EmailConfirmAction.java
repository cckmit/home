/*******************************************************************************
 * @(#)EmailConfirmActionAction.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.identity.bean.SMorMailValidateBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 找回密码之邮件url校验
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月14日 下午4:41:59
 */
public class EmailConfirmAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2741958667577485447L;
    
    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(EmailConfirmAction.class);
    
    /**
     * 页面隐藏的用户id
     */
    private String userId;
    
    private String uuid;
    
    private String lostTime;
    
    /**
     * 短信或者邮件找回密码时生成的动态密钥信息
     */
    private SMorMailValidateBean smMailValidateInfo =  new SMorMailValidateBean();
    
    /**
     * 
     * execute 找回密码之邮件url校验
     * @return success：重置页面, error：提示错误的url页面
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
            userId = Base64.decode(uuid);
        } catch (UnsupportedEncodingException e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "用户名解析uuid出错", e);
        }
        smMailValidateInfo.setKeyWord("uuid="+uuid+"&lostTime="+lostTime);
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
        if (null == smMailValidateInfo) { // 数据库中没有对应的url
            errMsg = getText("emailurl.input.failed");
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } else {
            Date date = new Date();
            SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMddHHmmss");
            long lostTimes = 0;
            long cc;
            try {
                lostTimes = timeformat.parse(smMailValidateInfo.getTimeStamp()).getTime();
            } catch (ParseException e) {
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "时间解析错误", e);
            }
            cc = date.getTime() - lostTimes;
            if (Long.parseLong(Constants.SAVETIME)*60*1000 < cc) { // 校验时间超过有效期
                deleteSmMailValidateInfo();
                errMsg = getText("emailurl.input.validate.failed"); // 链接地址失效
                this.addActionError(errMsg);
                return ConstantEnum.ERROR.toString(); // 返回失败页面
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLostTime() {
        return lostTime;
    }

    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }
    
}
