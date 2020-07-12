/*******************************************************************************
 * @(#)CloseAccountService.java 2014年1月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.util;

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
     * 发送邮件服务器IP
     */
    private String smtpIp = "";

    /**
     * 发送邮件服务器端口
     */
    private String smtpPort = "";

    /**
     * 邮件服务器登录用户名
     */
    private String smtpUserName = "";

    /**
     * 邮件服务器登录密码
     */
    private String smtpPasswd = "";

    /**
     * 是否是SSL
     */
    private boolean enableSSL = true;

    /**
     * 证书位置
     */
    private String keyStoreFilePath = "";
    
    
    /**
     * 证书密码
     */
    private String keyStorePassword = "";

      /**
     * 创建一个新的实例 MailService
     * @param smtpIp SMTP服务器IP
     * @param smtpPort SMTP服务器端口
     * @param smtpUserName SMTP服务器登录名
     * @param smtpPasswd SMTP服务器登录密码
     * @param enableSSL 同服务器是否启用SSL链接
     * @param sslCertFilePath SSL链接证书路径
     */
    public MailService(String smtpIp, String smtpPort, String smtpUserName, String smtpPasswd,
            boolean enableSSL, String sslCertFilePath,String keyStorePassword) {
        this.smtpIp = smtpIp;
        this.smtpPort = smtpPort;
        this.smtpUserName = smtpUserName;
        this.smtpPasswd = smtpPasswd;
        this.enableSSL = enableSSL;
        this.keyStoreFilePath = sslCertFilePath;
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * sendMail 发送邮件
     * @param email 用户邮箱
     * @param flag 标识为哪个功能发邮件
     * @throws Exception
     */
    public void sendMails(String sendMailAddress, String revMailAddress, String title,
            String content) throws Exception {
        if (!initConn()) {
            logger.error("初始化SMTP服务器的链接失败");
            return;
        }
        this.sendMail(sendMailAddress, revMailAddress, title, content);

        this.releaseConn();
    }

    /**
     * sendMailNotBySSL 不通过SSL发送邮件
     * @param revEmailAddress 用户邮箱
     * @param flag 标识为哪个功能发邮件
     * @throws Exception
     */
    private void sendMail(String sendMailAddress, String revMailAddress, String title,
            String mailcontent) throws Exception {

        MimeMessage message = null;
        // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
        Multipart mailContent;
        try {
            message = new MimeMessage(session); // 创建MIME邮件对象
            mailContent = new MimeMultipart();
        } catch (Exception e) {
            logger.info("创建MIME邮件对象失败！", e);
            throw e;
        }
        // 设置发件人
        InternetAddress fromAddress;
        try {
            fromAddress = new InternetAddress(sendMailAddress);
            message.setFrom(fromAddress);
        } catch (MessagingException e) {
            logger.error("设置发件人错误！", e);
            throw e;
        }
        // 设置收件人
        try {
            message.addRecipients(Message.RecipientType.TO, revMailAddress);
        } catch (MessagingException e1) {
            logger.error("设置收件人错误！", e1);
            throw e1;
        }
        // 设置邮件标题和邮件内容
        try {
            message.setSubject(title);
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
                    + mailcontent, "text/html;charset=GB2312");
            mailContent.addBodyPart(bp);

        } catch (Exception e) {
            logger.error("设置邮件标题或正文时发生错误！" + e);
            throw e;
        }
        // 发送邮件
        try {
            message.setContent(mailContent);
            message.saveChanges();
            message.setSentDate(new Date());
            smtpTransport = session.getTransport("smtp");
            smtpTransport.connect(smtpIp, Integer.valueOf(smtpPort), smtpUserName, smtpPasswd);
            smtpTransport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            smtpTransport.close();
            logger.info("邮件发送成功！");
        } catch (MessagingException ex) {
            logger.error("邮件发送失败！", ex);
        }
    }

    /**
     * initConn 初始化邮件服务器连接
     * @return true：连接成功 false：链接失败
     */
    boolean initConn() {
        Properties props = new Properties(); // 获取系统环境
        if (enableSSL) {
            props.put("mail.smtp.host", smtpIp);
            props.put("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            System.setProperty("javax.net.ssl.trustStore", this.keyStoreFilePath);
            System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);
            props.setProperty("mail.smtp.port", smtpPort);
        } else {
            props.put("mail.smtp.host", smtpIp);
            props.put("mail.smtp.auth", "true");
        }
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUserName, smtpPasswd);
            }
        });
        try {
            smtpTransport = session.getTransport("smtp");
            smtpTransport.connect(smtpIp, Integer.valueOf(smtpPort), smtpUserName, smtpPasswd);
            logger.info("Mail服务器连接成功..............");
        } catch (Exception e) {
            logger.error("initMailConn get mail host connection exception:", e);
            return false;
        }
        return true;
    }

    /**
     * releaseConn 释放邮件服务器连接
     * @return true：释放成功 false：释放失败
     */
    private boolean releaseConn() {
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
