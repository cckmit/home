/*******************************************************************************
 * @(#)CloseAccountService.java 2014年1月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 发邮件service
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月15日 下午3:14:45
 */
public class MailService {

    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(MailService.class);
    
    /**
     * 邮件服务器session
     */
    private Session session = null;
    
    /**
     * smtp服务器
     */
    private Transport smtpTransport = null;

    /**
     * MIME邮件对象
     */
    private MimeMessage message = null;
    
    /**
     * 引用配置文件
     */
    private static Properties prop = Constants.prop;
    
    /**
     * Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
     */
    private Multipart mp;
    
    /**
     * 发送邮件服务器IP
     */
    private String serviceIp = Constants.SERVICEIP;

    /**
     * 发送邮件服务器端口
     */
    private String servicePort = Constants.SERVICEPORT;

    /**
     * 邮件服务器登录用户名
     */
    private String addresser = Constants.ADDRESSER;

    /**
     * 邮件服务器登录密码
     */
    private String servicePassword = Constants.SERVICEPASSWORD;

    /**
     * 发送人邮件地址
     */
    private String email = Constants.EMAIL; 
    
    /**
     * 是否是SSL
     */
    private String isSSL = Constants.ISSSL;
    
    /**
     * 证书位置
     */
    private String filePath = Constants.MAILFILEPATH;
    
    /**
     * 
     * runMail 发送邮件
     * @param email 用户邮箱,
     * @param flag 标识为哪个功能发邮件
     */
    public void runMail(String email, String flag) {
        if (initConn()) {
            this.sendMail(email, flag);
        }
        this.releaseConn();
    }
    
    /**
     * 
     * sendMail 发送邮件
     * @param email 用户邮箱
     * @param flag 标识为哪个功能发邮件
     */
    public void sendMail(String email, String flag){
        /* 邮件通知 */
        if (("true").equals(isSSL)) {
            this.sendMailBySSL(email, flag);
        } else {
            this.sendMailNotBySSL(email, flag);
        }
    }
    
    /**
     * 
     * sendMailNotBySSL 不通过SSL发送邮件
     * @param email 用户邮箱
     * @param flag 标识为哪个功能发邮件
     */
    public void sendMailNotBySSL(String email, String flag) {
        Properties props = new Properties();
        props.put("mail.smtp.host", serviceIp);
        props.put("mail.smtp.auth", "true");
        
        // 创建邮件会话对象Session、MIME邮件对象MimeMessage
        try {
            // 获得邮件会话对象
            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(addresser, servicePassword);
                }
            });
        } catch (Exception e) {
            logger.info("获取邮件会话对象时发生错误！" + e);
        }
        try {
            message = new MimeMessage(session); // 创建MIME邮件对象
            mp = new MimeMultipart();
        } catch (Exception e) {
            logger.info("创建MIME邮件对象失败！" + e);
        }
        // 设置发件人
        InternetAddress fromAddress;
        try {
            fromAddress = new InternetAddress(email);
            message.setFrom(fromAddress);
        } catch (MessagingException e) {
            logger.error("设置发件人错误！", e);
        }
        // 设置收件人
        try {
            message.addRecipients(Message.RecipientType.TO, email);
        } catch (MessagingException e1) {
            logger.error("设置收件人错误！", e1);
        }
        // 设置邮件标题和邮件内容
        try {
            if ("closeAccount".equals(flag)) { // 销户成功发送邮件
                message.setSubject("云运营管理平台-销户成功通知");
            }
            BodyPart bp = new MimeBodyPart();
            String mailBody = setMsgbody(flag);
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
                    + mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
            
        } catch (Exception e) {
            logger.error("设置邮件标题或正文时发生错误！" + e);
        }
        // 发送邮件
        try {
            message.setContent(mp);
            message.saveChanges();
            message.setSentDate(new Date());
            smtpTransport = session.getTransport("smtp");
            smtpTransport.connect(serviceIp, Integer.valueOf(servicePort), addresser,
                    servicePassword);
            smtpTransport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            smtpTransport.close();
        } catch (MessagingException ex) {
            if ("closeAccount".equals(flag)) { // 销户成功发送邮件
                logger.error("销户成功通知邮件发送失败！", ex);
            }
        }
    }
    
    /**
     * 
     * sendMailBySSL SSL方式发邮件
     * @param email 用户邮箱
     * @param flag 标识为哪个功能发邮件
     */
    public void sendMailBySSL(String email, String flag) {
        Properties props = setSysProps(); // 获取系统环境

        /* 创建邮件会话对象Session、MIME邮件对象MimeMessage */
        try {
            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(addresser, servicePassword);
                }
            }); // 获得邮件会话对象
        } catch (Exception e) {
            logger.error("获取邮件会话对象时发生错误！", e);
        }
        try {
            message = new MimeMessage(session); // 创建MIME邮件对象
            mp = new MimeMultipart();
        } catch (Exception e) {
            logger.error("创建MIME邮件对象失败！", e);
        }
        /* 设置发件人 */
        InternetAddress fromAddress;
        try {
            fromAddress = new InternetAddress(email);
            message.setFrom(fromAddress);
        } catch (MessagingException e) {
            logger.error("设置发件人错误！", e);
        }

        /* 设置收件人 */
        try {
            message.addRecipients(Message.RecipientType.TO, email);
        } catch (MessagingException e1) {
            logger.error("设置收件人错误！", e1);
        }

        /* 设置邮件标题和邮件内容 */
        try {
            if ("closeAccount".equals(flag)) { // 销户成功发送邮件
                message.setSubject("云运营管理平台-销户成功通知");
            }
            BodyPart bp = new MimeBodyPart();
            String mailBody = setMsgbody(flag);
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
                    + mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
        } catch (Exception e) {
            logger.error("设置邮件标题或正文时发生错误！", e);
        }

        /* 发送邮件 */
        try {
            message.setContent(mp);
            message.saveChanges();
            message.setSentDate(new Date());
            smtpTransport = session.getTransport("smtp");
            smtpTransport.connect(serviceIp, Integer.valueOf(servicePort), addresser,
                    servicePassword);
            smtpTransport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            smtpTransport.close();
        } catch (MessagingException ex) {
            if ("closeAccount".equals(flag)) { // 销户成功发送邮件
                logger.error("销户成功通知邮件发送失败！", ex);
            }
        }
    }
    
    /**
     * 
     * setMsgbody 整理邮件体
     * @param flag 标识为哪个功能发邮件
     * @return mailBody.toString()
     */
    public String setMsgbody(String flag) {
        StringBuffer mailBody = new StringBuffer();
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        if ("closeAccount".equals(flag)) { // 销户成功发送邮件
            mailBody.append("尊敬的用户：<br> 您好！<br>");
            mailBody.append("您在" + dateTime.substring(0, 4) + "年" + dateTime.substring(4, 6) + "月" + dateTime.substring(6, 8) + "日" + dateTime.substring(8, 10) + "点"
                    + dateTime.substring(10, 12) + "分" + dateTime.substring(12, 14) + "秒" + "提交销户请求，现销户已成功，谢谢使用！<br>");
        }
        return mailBody.toString();
    }
    
    /**
     * 
     * initConn 初始化邮件服务器连接
     * @return true：连接成功   false：链接失败
     */
    boolean initConn() {
        if (("true").equals(isSSL)) {
            Properties props = setSysProps();

            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(addresser, servicePassword);
                }
            });
            try {
                smtpTransport = session.getTransport("smtp");
                smtpTransport.connect(serviceIp, Integer.valueOf(servicePort), addresser,
                        servicePassword);
                logger.info("Mail服务器连接成功..............");
            } catch (Exception e) {
                logger.error("initMailConn get mail host connection exception:", e);
                return false;
            }
        } else {
            Properties props = prop;
            props.put("mail.smtp.host", serviceIp);
            props.put("mail.smtp.auth", "true");

            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(addresser, servicePassword);
                }
            });
            try {
                smtpTransport = session.getTransport("smtp");
                smtpTransport.connect(serviceIp, Integer.valueOf(servicePort), addresser,
                        servicePassword);
                logger.info("Mail服务器连接成功..............");
            } catch (Exception e) {
                logger.error("initMailConn get mail host connection exception:", e);
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * setSysProps SSL获取系统环境
     * @return 邮件配置
     */
    private Properties setSysProps() {
        Properties props = prop; // 获取系统环境
        props.put("mail.smtp.host", serviceIp);
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        System.setProperty("javax.net.ssl.trustStore", filePath);
        System.setProperty("javax.net.ssl.trustStorePassword", "neuadmin");
        props.setProperty("mail.smtp.port", servicePort);
        return props;
    }
    
    /**
     * 
     * releaseConn 释放邮件服务器连接
     * @return true：释放成功     false：释放失败 
     */
    boolean releaseConn() {
        try {
            smtpTransport.close();
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            logger.error("release connection exception:", e);
            return false;
        }
        return true;
    }
}
