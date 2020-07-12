package com.neusoft.mid.cloong.web.page.user.order.mail;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.page.user.order.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 发送邮件方法
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-2-12 下午04:49:21
 */
public class SendMail extends TimerTask {

    /**
     * 日志类
     */
	private static LogService logger = LogService.getLogger(SendMail.class);

	/**
	 * 邮件服务器IP
	 * */
	private String mailServerIp;

	/**
	 * 邮件服务器端口
	 */
	private String partNo;

	/**
	 * 发送邮件用户名
	 */
	private String sendUserName;

	/**
	 * 发送邮件密码
	 */
	private String sendPassowrd;

	/**
	 * 邮件标题
	 */
	private String mailSubject;

	/**
	 * 邮件发送地址
	 */
	private String mailFrom;

	/**
	 * 发送邮件名称
	 */
	private String mailFormName;

	/**
	 * 证书位置
	 */
	private String mailFilePath;

	/**
	 * 是否需要证书
	 */
	private String isSSL;

	/**
	 * 离到期还有几天进行提醒
	 */
	private String mailWarnDate;

	/**
	 * 时间戳 yyyyMMddHHmmss
	 */
	private static final DateTimeFormatter TIMESTAMP = DateTimeFormat
			.forPattern("yyyyMMddHHmmss");

	/**
	 * 数据库操作者类
	 */
	private IbatisDAO ibatisDAO;

	/**
	 * 发送邮件
	 * 
	 * @param mailBody
	 *            邮件内容
	 * @param mailTo
	 *            接收人
	 * @throws Exception
	 */
	public synchronized void send(String mailBody, String mailTo) {
		try {
			Properties props = new Properties(); // 获取系统环境
			if ("true".equals(isSSL)) {
				props.setProperty("mail.smtp.starttls.enable", "true");
				System.setProperty("javax.net.ssl.trustStore", mailFilePath);// 设置证书
			}
			props.put("mail.smtp.host", mailServerIp);// 主机
			props.put("mail.smtp.auth", "true");// 验证
			props.setProperty("mail.smtp.port", partNo); // 端口号

			Session session = Session.getDefaultInstance(props,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(sendUserName,
									sendPassowrd);
						}
					}); // 获得邮件会话对象

			// 设置session,和邮件服务器进行通讯。
			MimeMessage message = new MimeMessage(session);
			MimeMultipart mp = new MimeMultipart();
			message.setSubject(mailSubject); // 设置邮件主题
			message.setText(mailBody); // 设置邮件正文
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(mailFrom, mailFormName);
			message.setFrom(address); // 设置邮件发送者的地址
			Address toAddress = new InternetAddress(mailTo); // 设置邮件接收方的地址
			message.addRecipient(Message.RecipientType.TO, toAddress);

			BodyPart bp = new MimeBodyPart();
			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
							+ mailBody, "text/html;charset=GB2312");
			mp.addBodyPart(bp);
			message.setContent(mp);
			message.saveChanges();
			logger.info("正在发送提醒邮件...");

			message.setSentDate(new Date());
			Transport smtpTransport = session.getTransport("smtp");
			smtpTransport.connect(mailServerIp, Integer.valueOf(partNo),
					sendUserName, sendPassowrd);
			smtpTransport.sendMessage(message, message
					.getRecipients(Message.RecipientType.TO));
			if (smtpTransport.isConnected()) {
				smtpTransport.close();
			}
			logger.info("发送成功!");
		} catch (Exception e) {
			logger.error(CommonStatusCode.INTERCEPTION_EXCEPTION_CODE,
					"邮件发送失败:" + mailTo, e);
		}
	}

	/**
	 * 
	 * run 拼装邮件信息并发送邮件
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {

		try {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DAY_OF_MONTH, Integer.parseInt(mailWarnDate));
			date = calendar.getTime();
			String time = String.valueOf(TIMESTAMP.print(new DateTime(date)));
			List<OrderInfo> orderInfos = ibatisDAO.getData(
					"selectExpireOrderAll", time);
			Map<String, String> orderInfoMap = new HashMap<String, String>();
			for (OrderInfo orderInfo : orderInfos) {
				if (orderInfoMap.get(orderInfo.getCreateUser()) == null) {
					StringBuffer mailBody = new StringBuffer();
					mailBody.append("订单编号：").append(orderInfo.getOrderId());
					mailBody.append("\t租户：").append(orderInfo.getCreateUser());
					mailBody.append("\t服务到期日期：").append(
							DateParse.parse(orderInfo.getExpireTime())).append(
							"<br/>");
					orderInfoMap.put(orderInfo.getCreateUser(), mailBody
							.toString());
				} else {
					StringBuffer mailBody = new StringBuffer();
					mailBody.append("订单编号：").append(orderInfo.getOrderId());
					mailBody.append("\t租户：").append(orderInfo.getCreateUser());
					mailBody.append("\t服务到期日期：").append(
							DateParse.parse(orderInfo.getExpireTime())).append(
							"<br/>");
					mailBody
							.append(orderInfoMap.get(orderInfo.getCreateUser()));
					orderInfoMap.put(orderInfo.getCreateUser(), mailBody
							.toString());
				}
			}
			Iterator iter = orderInfoMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String userId = (String) entry.getKey();
				String mailBody = (String) entry.getValue();
				UserBean userBean = new UserBean();
				userBean.setUserId(userId);
				userBean = (UserBean) ibatisDAO.getSingleRecord(
						"selectUserBean", userBean);
				send(mailBody.toString(), userBean.getEmail());
			}
		} catch (SQLException e) {
			logger.error(CommonStatusCode.INTERCEPTION_EXCEPTION_CODE,
					"组装邮件内容失败:", e);
		}catch (Exception e) {
			logger.error(CommonStatusCode.INTERCEPTION_EXCEPTION_CODE,
					"组装邮件内容失败:", e);
		}

	}
	
	/**
	 * 
	 * getMailServerIp TODO 方法
	 * @return TODO
	 */
	public String getMailServerIp() {
        return mailServerIp;
    }

	/**
	 * 
	 * setMailServerIp TODO 方法
	 * @param mailServerIp TODO
	 */
    public void setMailServerIp(String mailServerIp) {
        this.mailServerIp = mailServerIp;
    }

    /**
     * 
     * getPartNo TODO 方法
     * @return TODO
     */
    public String getPartNo() {
        return partNo;
    }

    /**
     * 
     * setPartNo TODO 方法
     * @param partNo TODO
     */
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    /**
     * 
     * getSendUserName TODO 方法
     * @return TODO
     */
    public String getSendUserName() {
        return sendUserName;
    }

    /**
     * 
     * setSendUserName TODO 方法
     * @param sendUserName TODO
     */
    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    /**
     * 
     * getSendPassowrd TODO 方法
     * @return TODO
     */
    public String getSendPassowrd() {
        return sendPassowrd;
    }

    /**
     * 
     * setSendPassowrd TODO 方法
     * @param sendPassowrd TODO
     */
    public void setSendPassowrd(String sendPassowrd) {
        this.sendPassowrd = sendPassowrd;
    }

    /**
     * 
     * getMailSubject TODO 方法
     * @return TODO
     */
    public String getMailSubject() {
        return mailSubject;
    }

    /**
     * 
     * setMailSubject TODO 方法
     * @param mailSubject TODO
     */
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    /**
     * 
     * getMailFrom TODO 方法
     * @return TODO
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * 
     * setMailFrom TODO 方法
     * @param mailFrom TODO
     */
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * 
     * getMailFormName TODO 方法
     * @return TODO
     */
    public String getMailFormName() {
        return mailFormName;
    }

    /**
     * 
     * setMailFormName TODO 方法
     * @param mailFormName TODO
     */
    public void setMailFormName(String mailFormName) {
        this.mailFormName = mailFormName;
    }

    /**
     * 
     * getMailFilePath TODO 方法
     * @return TODO
     */
    public String getMailFilePath() {
        return mailFilePath;
    }

    /**
     * 
     * setMailFilePath TODO 方法
     * @param mailFilePath TODO
     */
    public void setMailFilePath(String mailFilePath) {
        this.mailFilePath = mailFilePath;
    }

    /**
     * 
     * getIbatisDAO TODO 方法
     * @return TODO
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * 
     * setIbatisDAO TODO 方法
     * @param ibatisDAO TODO
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * 
     * getIsSSL TODO 方法
     * @return TODO
     */
    public String getIsSSL() {
        return isSSL;
    }

    /**
     * 
     * setIsSSL TODO 方法
     * @param isSSL TODO
     */
    public void setIsSSL(String isSSL) {
        this.isSSL = isSSL;
    }

    /**
     * 
     * getMailWarnDate TODO 方法
     * @return TODO
     */
    public String getMailWarnDate() {
        return mailWarnDate;
    }

    /**
     * 
     * setMailWarnDate TODO 方法
     * @param mailWarnDate TODO
     */
    public void setMailWarnDate(String mailWarnDate) {
        this.mailWarnDate = mailWarnDate;
    }
}
