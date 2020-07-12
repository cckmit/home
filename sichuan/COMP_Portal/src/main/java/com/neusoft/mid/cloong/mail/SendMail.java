package com.neusoft.mid.cloong.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
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
import javax.mail.internet.MimeUtility;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.web.login.RegisterAction;
import com.neusoft.mid.iamp.logger.LogService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Component
public class SendMail implements Runnable {

    private Thread t;

    private String threadName;

    //private Logger logger = LoggerFactory.getLogger(SendMail.class);
    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(RegisterAction.class);

    /**
     * 是否加密配置
     */
    //@Value("${mailIsSSL}")
    private String mailIsSSL = Constants.mailIsSSL;

    /**
     * 邮件服务器地址配置
     */
    //@Value("${mailServiceIp}")
    private String mailServiceIp = Constants.mailServiceIp;

    /**
     * 邮件服务器端口配置
     */
    //@Value("${mailServicePort}")
    private int mailServicePort = Constants.mailServicePort;

    /**
     * 邮件发送人登录名配置
     */
    //@Value("${sendmailuser}")
    private String username = Constants.username;

    /**
     * 邮件发送人登陆密码配置
     */
    //@Value("${sendmailpasswd}")
    private String password = Constants.password;

    /**
     * 邮件发送人邮件地址
     */
    //@Value("${sendmailfrom}")
    private String mail_from = Constants.sendmailfrom;

    /*
     * 校验是否不用用户名密码发送邮件
     */
    //@Value("${validateSimple}")
    private String validateSimple = Constants.validateSimple;

    /*
     * 邮件附件路径
     */
   // @Value("${enclosure.path}")
    private String enclosurePath = Constants.enclosurePath;

    /**
     * 邮件默认标题
     */
    private String mail_head_name = "this is head of this mail";

    /**
     * 邮件默认标题
     */
    private String mail_head_value = "this is head of this mail";

    /**
     * 邮件服务器地址
     */
    private String propsHost = "mail.smtp.host";

    /**
     * 邮件服务器端口
     */
    private String propsPort = "mail.smtp.port";

    /**
     * 邮件标题
     */
    private String mail_subject = null;

    /**
     * 收件人
     */
    private String mail_to;

    private String fileName;

    /**
     * 邮件正文
     */
    private String mail_body = null;

    /**
     * 显示发件人信息
     */
    private String personalName = null;

    /**
     * 构造函数
     * @param strMailHead
     * @param strMailTo
     */
    public void intMail(String strMailHead, String strMailTo, String fileName) {
        this.mail_subject = strMailHead;
        this.mail_to = strMailTo;
        this.fileName = fileName;
        this.threadName = UUID.randomUUID().toString().replace("-", "");
    }

    public SendMail() {

    }

    /** 多条消息时组装成一封邮件 */
    public void setMessage(String newMessage) {
        logger.info("Content:" + newMessage);
        this.mail_body = newMessage;
    }

    /**
     * 有证书时设置邮件会话属性
     * @return
     */
    private Properties setSysProps() {
        logger.debug("SendMail.send() 加密发送");
        Properties props = new Properties();

        String path1 = SendMail.class.getResource("").getPath();
        path1 = path1.substring(1, path1.length());

        logger.debug("path" + path1);

        String path = path1 + "neusoft.ssl";
        logger.debug(path);
        /** 此处设置公钥位置 */
        System.setProperty("javax.net.ssl.trustStore", path);
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        /** 获取系统环境 */
        props.put(propsHost, mailServiceIp); // GlobalVariable.configBean.getServiceIP());
        /** 此处设置成为加密连接 */
//      props.put("mail.smtp.auth", true);
//      props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.auth", "true"); // 之前是true，但不好用必须是"true",可能和jdk6版本低有关
        props.put("mail.smtp.starttls.enable", "true"); // 之前是true，但不好用必须是"true",可能和jdk6版本低有关
        props.put("mail.smtp.ssl.trust", mailServiceIp);
        /** 此处设置服务器端口 */
        props.put(propsPort, mailServicePort); // port);
        return props;
    }

    /**
     * 无证书时设置邮件会话属性
     * @return
     */
    private Properties setNoSysProps() {
        Properties props = new Properties();
        props.put(propsHost, mailServiceIp);
        // props.put("mail.smtp.auth", true);
        props.put("mail.smtp.auth", "true");  // 之前是true，但不好用必须是"true",可能和jdk6版本低有关
        return props;
    }

    /**
     * 创建一个发送邮件的session
     * @return
     */
    private Session createSession() {
        Properties props;
        if (mailIsSSL.equals("true")) {
            props = this.setSysProps();
        } else {
            props = this.setNoSysProps();
        }
        /** 创建一个发送邮件的session */
        // return Session.getDefaultInstance(props, new Authenticator() {
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * 创建消息
     * @param session
     * @return
     */
    private MimeMessage createMimeMessage(Session session) {
        /** 设置session,和邮件服务器进行通讯 */
        MimeMessage message = new MimeMessage(session);
        /** 设置邮件主题 */
        try {
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // message.addHeader("format", "flowed");
            message.setSubject(MimeUtility.encodeText(mail_subject, "UTF-8", "B"));
            logger.info("change before:" + mail_body);
//            mail_body = new String(mail_body.getBytes(), "UTF-8");
            logger.info("change after:" + mail_body);
            // msg.setContent(body, "text/plain;charset=gb2312");
            /** 设置邮件正文 */
            message.setText(MimeUtility.encodeText(mail_body, "UTF-8", "B"),
                    "text/plain; charset=utf-8");
            /** 设置邮件标题 */
            // message.setHeader(mail_head_name, mail_head_value);
            /** 设置邮件发送日期 */
            // message.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();
            // 添加邮件正文
            BodyPart contentBodyPart = new MimeBodyPart();
            contentBodyPart.setContent(mail_body, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentBodyPart);
            message.setSentDate(new Date());
            Address address = new InternetAddress(mail_from, personalName);
            /** 设置邮件发送者的地址 */
            message.setFrom(address);
            session.setDebug(true);
            /** 设置邮件接收方的地址 */
            String[] mailToArr = mail_to.split(";");
            for (String to : mailToArr) {
                Address toAddress = new InternetAddress(to);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            if (!"".equals(fileName) && fileName != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                // 根据附件路径获取文件
                String filePath = enclosurePath + fileName;
                FileDataSource dataSource = new FileDataSource(filePath);
                attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
                // MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart
                        .setFileName(MimeUtility.encodeWord(dataSource.getFile().getName()));
                multipart.addBodyPart(attachmentBodyPart);
            }
            message.setContent(multipart);
        } catch (UnsupportedEncodingException ue) {
            logger.error("SendMail.send() UnsupportedEncodingException ", ue);
        } catch (MessagingException me) {
            logger.error("SendMail.send() MessagingException ", me);
        }

        return message;
    }

    /**
     * 此段代码用来发送普通电子邮件
     * @throws Exception
     */

    public void simpleSendMail() {
        Properties properties = System.getProperties();
        // Properties props = new Properties();
        properties.put(propsHost, mailServiceIp);
        properties.put("mail.smtp.port", mailServicePort);
        properties.put("mail.smtp.starttls.enable", "true");
        try{
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);

            properties.put("mail.smtp.ssl.socketFactory",sf);
        }catch (Exception e){
            e.printStackTrace();
        }

        properties.put("mail.smtp.ssl.trust", mailServiceIp);
        properties.put("mail.smtp.socketFactory.fallback", "true");
        // 设置邮件服务器
        // properties.setProperty("mail.smtp.host", mailServiceIp);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties);

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            message.setSubject(mail_subject);
            /** 设置邮件正文 */
            // message.setText(mail_body,"UTF-8");
            // message.setText(mail_body);
            /** 设置邮件标题 */
            // message.setHeader(mail_head_name, mail_head_value,);
            /** 设置邮件发送日期 */
            Multipart multipart = new MimeMultipart();
            // 添加邮件正文
            BodyPart contentBodyPart = new MimeBodyPart();
            contentBodyPart.setContent(mail_body, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentBodyPart);
            message.setSentDate(new Date());
            Address address = new InternetAddress(mail_from, personalName);
            /** 设置邮件发送者的地址 */
            message.setFrom(address);
            session.setDebug(true);
            /** 设置邮件接收方的地址 */
            String[] mailToArr = mail_to.split(";");
            for (String to : mailToArr) {
                Address toAddress = new InternetAddress(to);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }

            if (!"".equals(fileName) && fileName != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                // 根据附件路径获取文件
                String filePath = enclosurePath + fileName;
                FileDataSource dataSource = new FileDataSource(filePath);
                attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
                // MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart
                        .setFileName(MimeUtility.encodeWord(dataSource.getFile().getName()));
                multipart.addBodyPart(attachmentBodyPart);
            }

            message.setContent(multipart);

            /*
             * // Set From: 头部头字段 // message.setFrom(new InternetAddress(mail_from)); // Set To:
             * 头部头字段 message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
             * // Set Subject: 头部头字段 message.setText(mail_body);
             *//** 设置邮件标题 */
            /*
             * message.setHeader(mail_head_name, mail_head_value);
             *//** 设置邮件发送日期 */
            /*
             * message.setSentDate(new Date()); Address address = new InternetAddress(mail_from,
             * personalName);
             *//** 设置邮件发送者的地址 *//*
                                * message.setFrom(address);
                                */
            message.saveChanges();
            // 发送消息
            Transport.send(message);
            logger.info("Sent message successfully....");
        } catch (Exception mex) {
            mex.printStackTrace();
        }

    }

    public void start() throws Exception {
        if (validateSimple.equalsIgnoreCase("true")) {
            simpleSendMail();
        } else {
            Session session = createSession();
            session.setDebug(true);
            MimeMessage message = createMimeMessage(session);
            /** 需要使用加密连接发送 */
            Transport t = (Transport) session.getTransport("smtp");
            try {
                t.connect(mailServiceIp, mailServicePort, username, password);
                t.sendMessage(message, message.getAllRecipients());
            } finally {
                t.close();
            }
        }
    }

    public void send() {
        System.out.println("Starting " + threadName);
        //if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        //}
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
