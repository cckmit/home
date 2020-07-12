/*******************************************************************************
 * @(#)ForgetPasswordMailService.java 2014年1月16日
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

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 忘记密码 - 用邮件找回密码  - 下发邮件
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月16日 下午3:14:43
 */
public class ForgetPasswordService {

    /**
     * 记录日志输出
     */
    private static LogService logger = LogService.getLogger(ForgetPasswordService.class);
    
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
     * @param lostTime 申请找回密码时间  
     * @param userInfo 用户bean
     */
    public void runMail(String lostTime, UserBean userInfo) {
        if (initConn()) {
            this.sendMail(lostTime, userInfo);
        }
        this.releaseConn();
    }
    
    /**
     * 
     * sendMail 发送邮件
     * @param lostTime 申请找回密码时间
     * @param userInfo 用户bean
     */
    public void sendMail(String lostTime, UserBean userInfo){
        /* 邮件通知 */
        if (("true").equals(isSSL)) {
            this.sendMailBySSL(lostTime, userInfo);
        } else {
            this.sendMailNotBySSL(lostTime, userInfo);
        }
    }
    
    /**
     * 
     * sendMailNotBySSL 不通过SSL发送邮件
     * @param lostTime 申请找回密码时间
     * @param userInfo 用户bean
     */
    public void sendMailNotBySSL(String lostTime, UserBean userInfo) {
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
            message.addRecipients(Message.RecipientType.TO, userInfo.getEmail());
        } catch (MessagingException e1) {
            logger.error("设置收件人错误！", e1);
        }
        // 设置邮件标题和邮件内容
        try {
            message.setSubject("云运营管理平台-用户密码找回");
            BodyPart bp = new MimeBodyPart();
            String mailBody = setMsgbody(lostTime, userInfo);
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
            logger.info("正在发送找回密码通知邮件.");
            message.setSentDate(new Date());
            smtpTransport = session.getTransport("smtp");
            smtpTransport.connect(serviceIp, Integer.valueOf(servicePort), addresser,
                    servicePassword);
            smtpTransport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            smtpTransport.close();
        } catch (MessagingException ex) {
            logger.error("找回密码通知邮件发送失败！", ex);
        }
    }
    
    /**
     * 
     * sendMailBySSL SSL方式发邮件
     * @param lostTime 申请找回密码时间
     * @param userInfo 用户bean
     */
    private void sendMailBySSL(String lostTime, UserBean userInfo) {
        Properties props = setSysProps(); // 获取系统环境

        /* 创建邮件会话对象Session、MIME邮件对象MimeMessage */
        try {
            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(addresser, servicePassword);
                }
            }); // 获得邮件会话对象
        } catch (Exception e) {
            logger.error("获取邮件会话对象时发生错误！" + e);
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
            message.addRecipients(Message.RecipientType.TO, userInfo.getEmail());
        } catch (MessagingException e1) {
            logger.error("设置收件人错误！", e1);
        }

        /* 设置邮件标题和邮件内容 */
        try {
            message.setSubject("云运营管理平台-用户密码找回");
            BodyPart bp = new MimeBodyPart();
            String mailBody = setMsgbody(lostTime, userInfo);
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
                    + mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
        } catch (Exception e) {
            logger.error("设置邮件标题或正文时发生错误！" + e);
        }

        /* 发送邮件 */
        try {
            message.setContent(mp);
            message.saveChanges();
            logger.info("正在发送找回密码通知邮件.");
            message.setSentDate(new Date());
            smtpTransport = session.getTransport("smtp");
            smtpTransport.connect(serviceIp, Integer.valueOf(servicePort), addresser,
                    servicePassword);
            smtpTransport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            smtpTransport.close();
        } catch (MessagingException ex) {
            logger.error("找回密码通知邮件发送失败！", ex);
        }
    }
    
    /**
     * 
     * setMsgbody 整理邮件体
     * @param lostTime 申请找回密码时间
     * @param userInfo 用户bean
     * @return mailBody.toString()
     */
    public String setMsgbody(String lostTime, UserBean userInfo) {
        StringBuffer mailBody = new StringBuffer();
        mailBody.append("尊敬的用户：<br> " + userInfo.getUserName() + " 您好！<br>");
        mailBody.append("您在" + lostTime.substring(0, 4) + "年" + lostTime.substring(4, 6) + "月" + lostTime.substring(6, 8) + "日" + lostTime.substring(8, 10) + "点"
                + lostTime.substring(10, 12) + "分" + lostTime.substring(12, 14) + "秒" + "提交找回密码请求，请点击下面的链接修改用户" + userInfo.getUserId() + "的密码：<br>");
        mailBody.append(Constants.FINDPASSWORDURL + "uuid="+Base64.encode(userInfo.getUserId())+"&lostTime="+Base64.encode(lostTime) + "<br>");
        mailBody.append("(如果您无法点击这个链接，请将此链接复制到浏览器地址栏后访问) <br>");
        mailBody.append("为了保证您帐号的安全性，该链接有效期为" + Constants.SAVETIME + "分钟" + "并且点击一次后将失效! <br>");
        mailBody.append("设置并牢记密码保护问题将更好地保障您的帐号安全。 <br>");
        mailBody.append("如果您误收到此电子邮件，则可能是其他用户在尝试帐号设置时的误操作，如果您并未发起该请求，则无需再进行任何操作，并可以放心地忽略此电子邮件。 <br>");
        mailBody.append("若您担心帐号安全，建议您立即登录，密码修改中修改密码。 <br>");
        mailBody.append("感谢您使用云运营管理平台！ ");
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
    public Properties setSysProps() {
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
