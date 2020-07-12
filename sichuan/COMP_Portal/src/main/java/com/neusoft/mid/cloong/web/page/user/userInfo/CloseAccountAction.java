/*******************************************************************************
 * @(#)CloseAccountAction.java 2014年1月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.userInfo;

import java.sql.SQLException;
import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.login.RegisterAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户自销户
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月15日 下午2:05:45
 */
public class CloseAccountAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -9214922714764707798L;
    
    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(RegisterAction.class);
    
    /**
     * 用户bean对象
     */
    private UserBean user = new UserBean();
    
    /**
     * 发送邮件接口
     */
//    private MailService mailService;

    /**
     * 发信地址
     */
    private String fromMailAddress;
    
    /**
     * 邮件发送类
     */
    private com.neusoft.mid.cloong.common.util.MailService mailSender;

    public String execute() {
        int num;
        int number;
        try {
            num = ibatisDAO.getCount("selectCaseVmCount", user);
            number = ibatisDAO.getCount("selectCaseEBSCount", user);
        } catch (SQLException e) {
            this.addActionError(MessageFormat.format(getText("user.closeAccount.failed"), user.getUserId()));
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询用户信息失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } catch (Exception e) {
            this.addActionError(MessageFormat.format(getText("user.closeAccount.failed"), user.getUserId()));
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询用户信息失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }
        if (num != 0 || number != 0) {
            this.addActionError(MessageFormat.format(getText("user.cannot.closeAccount"), user.getUserId()));
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } else {
            try {
                user.setStatus(UserStatus.CANCELLATION); // 销户
                ibatisDAO.updateData("updateUserState", user);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "销户失败", e);
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "销户失败", e);
            }
        }
        this.addActionMessage(MessageFormat.format(getText("user.closeAccount"), user.getUserId()));
        /*
           //销户时发邮件or短信暂屏蔽
        if ("mail".equals(Constants.ISMAILORSM)) {
            try {
                mailSender.sendMails(this.fromMailAddress, user.getEmail(), "云运营管理平台-销户成功通知", setMsgbody());
            } catch (Exception e) {
                logger.error("销户失败", e);
            }
        } else {
            // TODO 下发短信
            logger.info("下发短信成功");
        }*/
        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 
     * setMsgbody 整理邮件体
     * @return mailBody.toString()
     */
    private String setMsgbody() {
        StringBuffer mailBody = new StringBuffer();
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
            mailBody.append("尊敬的用户：<br> 您好！<br>");
            mailBody.append("您在" + dateTime.substring(0, 4) + "年" + dateTime.substring(4, 6) + "月" + dateTime.substring(6, 8) + "日" + dateTime.substring(8, 10) + "点"
                    + dateTime.substring(10, 12) + "分" + dateTime.substring(12, 14) + "秒" + "提交销户请求，现销户已成功，谢谢使用！<br>");
        return mailBody.toString();
    }
    
    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

//    public MailService getMailService() {
//        return mailService;
//    }
//
//    public void setMailService(MailService mailService) {
//        this.mailService = mailService;
//    }

    /**
     * 设置mailSender字段数据
     * @param mailSender The mailSender to set.
     */
    public void setMailSender(com.neusoft.mid.cloong.common.util.MailService mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 设置fromMailAddress字段数据
     * @param fromMailAddress The fromMailAddress to set.
     */
    public void setFromMailAddress(String fromMailAddress) {
        this.fromMailAddress = fromMailAddress;
    }
    
    
    
    

}
