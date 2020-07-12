/*******************************************************************************
 * @(#)LoginAction.java 2009-5-27
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.io.IOException;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.EncryptUtils;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version $Revision 1.1 $ 2009-5-27 上午10:05:51
 */
public class LoginAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 失败页面
	 */
	private static final String RET_FAILURE = "FAILURE";

	/**
	 * 毫秒与分钟转换
	 */
	private static final long MS = 60000;

	/**
	 * 无效页面
	 */
	private static final String RET_INVALID = "INVALID";

	/**
	 * 成功页面
	 */
	private static final String RET_SUCCESS = "SUCCESS";

	/**
	 * 记录日志输出
	 */
	private static LogService logger = LogService.getLogger(LoginAction.class);

	/**
	 * 登录输入的用户id
	 */
	private String aesUserId;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 登录输入的校验码
	 */
	private String verify;

	/**
	 * 用户登录时,首先判断用户是否可用，然后判断id和password是否正确；如果不正确，则提示错误并返回登录页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String execute() {
		// 校验输入
		if (!validateInputPara()) {
			this.addActionError(errMsg);
			return RET_INVALID;
		}
		// 校验用户名密码
		UserBean userInfo;
		String tempUserId = "";

		// 从数据库中读取与用户输入的用户ID对应的用户信息
		try {
			tempUserId = EncryptUtils.aesDecrypt(aesUserId.trim());
			logger.info("当前登录的用户名为userId:" + tempUserId);
			userInfo = (UserBean) ibatisDAO.getSingleRecord(
					"getUserForPortalLogin", tempUserId);
		} catch (SQLException e) {
			errMsg = MessageFormat.format(getText("read.error"), tempUserId);
			this.addActionError(errMsg);
			logger.error(LoginStatusCode.LOGIN_EXCEPTION,
					errMsg + e.getMessage(), e);
			return RET_FAILURE;
		} catch (Exception e1) {
			errMsg = MessageFormat.format(getText("read.error"), tempUserId);
			this.addActionError(errMsg);
			logger.error(LoginStatusCode.LOGIN_EXCEPTION,
					errMsg + e1.getMessage(), e1);
			return RET_FAILURE;
		}

		// 校验验证码
		String sessionRS = (String) ActionContext.getContext().getSession()
				.get("random");
		Boolean verifyFlag = verify.equalsIgnoreCase(sessionRS);
		ActionContext.getContext().getSession().remove("random");
		// 若当前用户已被禁用，或者用户输入的用户ID或密码错误，则在登录页面进行提示
		if (null != userInfo) { // 用户ID存在
			try {
				/*
				 * sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
				 * String passwordBase64Bak = new String(
				 * dec.decodeBuffer(password));// 解密
				 */
				logger.info("页面加密的密码为:" + password);
				logger.info("数据库中Base64解密之后的密码为:"
						+ Base64.decode(userInfo.getPassword()));
				if (!verifyFlag
						|| !password.equalsIgnoreCase(Base64.decode(userInfo
								.getPassword()))) { // 用户名和密码不正确
					if ((UserStatus.ENABLE).equals(userInfo.getStatus())) { // 用户状态为启用
						int loginFailedNum = 0;
						if ("".equals(userInfo.getLoginFailedTime())
								|| null == userInfo.getLoginFailedTime()) {
							userInfo.setLoginFailedTime("");
						}
						Date date = new Date();
						SimpleDateFormat timeformat = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						long lastTime = 0;
						long cc;
						try {
							if (1 == userInfo.getLoginFailedTime().split("-").length) { // 有时间和只有一个时间长度都是1
								if ("".equals(userInfo.getLoginFailedTime())) { // 没有时间
									lastTime = date.getTime();
								} else { // 有一个时间
									lastTime = timeformat.parse(
											userInfo.getLoginFailedTime())
											.getTime();
								}
							} else {
								lastTime = timeformat
										.parse(userInfo
												.getLoginFailedTime()
												.substring(
														userInfo.getLoginFailedTime()
																.lastIndexOf(
																		"-"))
												.substring(1, 15)).getTime();
							}
						} catch (ParseException e) {
							logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
									"时间解析错误", e);
						}
						cc = date.getTime() - lastTime;
						if (cc > Constants.FAILDTIME) { // 上次登录错误时间距离现在时间大于10分钟，数据清零，重记登错时间重记锁定
							userInfo.setStatus(UserStatus.ENABLE);
							userInfo.setLoginFailedTime(DateParse
									.generateDateFormatyyyyMMddHHmmss());
							userInfo.setLockTime("");
						} else { // 上次登录错误时间距现在时间小于等于10分钟，记录登错时间
							if (!"".equals(userInfo.getLoginFailedTime())
									&& null != userInfo.getLoginFailedTime()) { // 锁定时间不为空
								loginFailedNum = userInfo.getLoginFailedTime()
										.split("-").length + 1;
								userInfo.setLoginFailedTime(userInfo
										.getLoginFailedTime().trim()
										+ "-"
										+ DateParse
												.generateDateFormatyyyyMMddHHmmss());
							} else {
								loginFailedNum = 1;
								userInfo.setLoginFailedTime(DateParse
										.generateDateFormatyyyyMMddHHmmss());
							}
							if (loginFailedNum > Constants.LOGINFAILDNUM) {
								userInfo.setStatus(UserStatus.LOCK); // 登录失败超过3次状态变锁定
								userInfo.setLoginFailedTime(""); // 数据清空
								checkLockTime(userInfo);
							}
						}

						updateUserStatus(userInfo); // 更新用户状态至DB
						if (loginFailedNum > Constants.LOGINFAILDNUM) {
							errMsg = "账户被锁定，请十分钟后再操作！";
						} else {
							if (!verifyFlag) {
								errMsg = getText("user.name.verify.error");
							} else {
								errMsg = getText("user.name.psw.error");
							}
						}

					} else if ((UserStatus.LOCK).equals(userInfo.getStatus())) { // 锁定
						Date date = new Date();
						SimpleDateFormat timeformat = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						long lastTime = 0;
						long cc;
						try {
							if (1 == userInfo.getLockTime().split("-").length) {
								lastTime = timeformat.parse(
										userInfo.getLockTime()).getTime();
							} else {
								lastTime = timeformat.parse(
										userInfo.getLockTime()
												.substring(
														userInfo.getLockTime()
																.lastIndexOf(
																		"-"))
												.substring(1, 15)).getTime();
							}
						} catch (ParseException e) {
							logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
									"时间解析错误", e);
						}
						cc = date.getTime() - lastTime;
						if (cc >= Constants.LOCKTIME) { // 上次锁定时间距离现在时间大于等于10分钟，自动解锁
							userInfo.setStatus(UserStatus.ENABLE); // 状态变为启用，解锁成功
							userInfo.setLoginFailedTime("");
							userInfo.setLockTime("");

							updateUserStatus(userInfo); // 更新用户状态至DB
							if (!verifyFlag) {
								errMsg = getText("user.name.verify.error");
							} else {
								errMsg = getText("user.name.psw.error");
							}

						} else { // 没到解锁时间，不让登录系统
							errMsg = MessageFormat.format(
									getText("account.lock.cannot.login"),
									((Constants.LOCKTIME - cc) / MS + 1));// 剩余锁定时间
							ActionContext
									.getContext()
									.getSession()
									.put(SessionKeys.invalid.toString(), errMsg);
							logger.error(
									LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
									errMsg);
						}
					}

					ActionContext.getContext().getSession()
							.put(SessionKeys.invalid.toString(), errMsg);
					logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
							errMsg);
					this.addActionError(errMsg);
					return RET_INVALID;
				} else { // 用户ID和密码都正确时，将当前用户存入session中，以便页面显示用
					if ((UserStatus.DISABLE).equals(userInfo.getStatus())) { // 用户状态为停用，不让登
						errMsg = getText("user.cannot.login");
						ActionContext.getContext().getSession()
								.put(SessionKeys.invalid.toString(), errMsg);
						logger.error(
								LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
								errMsg);
						this.addActionError(errMsg);
						return RET_INVALID;
					} else if ((UserStatus.LOCK).equals(userInfo.getStatus())) { // 锁定
						Date date = new Date();
						SimpleDateFormat timeformat = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						long lastTime = 0;
						long cc;
						try {
							if (1 == userInfo.getLockTime().split("-").length) {
								lastTime = timeformat.parse(
										userInfo.getLockTime()).getTime();
							} else {
								lastTime = timeformat.parse(
										userInfo.getLockTime()
												.substring(
														userInfo.getLockTime()
																.lastIndexOf(
																		"-"))
												.substring(1, 15)).getTime();
							}
						} catch (ParseException e) {
							logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
									"时间解析错误", e);
						}
						cc = date.getTime() - lastTime;
						if (cc >= Constants.LOCKTIME) { // 上次锁定时间距离现在时间大于等于10分钟，自动解锁
							userInfo.setStatus(UserStatus.ENABLE); // 状态变为启用，解锁成功
							userInfo.setLoginFailedTime("");
							userInfo.setLockTime("");

							updateUserStatus(userInfo); // 更新用户状态至DB

						} else { // 没到解锁时间，不让登录系统
							errMsg = MessageFormat.format(
									getText("user.lock.cannot.login"),
									((Constants.LOCKTIME - cc) / MS + 1));// 剩余锁定时间
							ActionContext
									.getContext()
									.getSession()
									.put(SessionKeys.invalid.toString(), errMsg);
							logger.error(
									LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
									errMsg);
							return RET_INVALID;
						}
					} else if ((UserStatus.CANCELLATION).equals(userInfo
							.getStatus())) { // 销户
						errMsg = getText("user.delete.cannot.login");
						ActionContext.getContext().getSession()
								.put(SessionKeys.invalid.toString(), errMsg);
						logger.error(
								LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
								errMsg);
						this.addActionError(errMsg);
						return RET_INVALID;
					} else if ((UserStatus.EXAMINATION).equals(userInfo
							.getStatus())) { // 待审批
						errMsg = getText("user.audit.cannot.login");
						ActionContext.getContext().getSession()
								.put(SessionKeys.invalid.toString(), errMsg);
						logger.error(
								LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
								errMsg);
						this.addActionError(errMsg);
						return RET_INVALID;
					} else if ((UserStatus.UNAUDITPASS).equals(userInfo
							.getStatus())) { // 审批不通过
						errMsg = getText("user.audit.not.login");
						ActionContext.getContext().getSession()
								.put(SessionKeys.invalid.toString(), errMsg);
						logger.error(
								LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
								errMsg);
						this.addActionError(errMsg);
						return RET_INVALID;
					} else if ((UserStatus.ENABLE).equals(userInfo.getStatus())) { // 启用
																					// 数据清零
						userInfo.setLoginFailedTime("");
						userInfo.setLockTime("");

						updateUserStatus(userInfo); // 更新用户状态至DB
					} else { // 异常情况
						errMsg = getText("login.fail");
						ActionContext.getContext().getSession()
								.put(SessionKeys.invalid.toString(), errMsg);
						logger.error(
								LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
								errMsg);
						this.addActionError(errMsg);
						return RET_INVALID;
					}
					
					// ----------- 用户信息DB中存储登录标志位，查到是0置为1，查到是1置为0 BEGIN --------------
					if ("0".equals(userInfo.getIfLogin())) {
						userInfo.setIfLogin("1");
					} else if ("1".equals(userInfo.getIfLogin())) {
						userInfo.setIfLogin("0");
					}
					try {
						ibatisDAO.updateData("updateUserLogin", userInfo);
					} catch (SQLException e) {
						logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
								"修改用户已登录失败", e);
					}
					// ----------- 用户信息DB中存储登录标志位，查到是0置为1，查到是1置为0 END --------------
					
					// 将当前用户绑定的业务以list形式存储
					String appIdStr = userInfo.getAppIdStr();
					if (appIdStr != null && !"".equals(appIdStr)) {
						userInfo.setAppIdList(Arrays.asList(appIdStr.split(",")));
					} else {
						errMsg = getText("user.noApp.cannot.login");
						ActionContext.getContext().getSession()
								.put(SessionKeys.invalid.toString(), errMsg);
						logger.error(
								LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
								errMsg);
						this.addActionError(errMsg);
						return RET_INVALID;
					}
					
					// 将用户信息存放在session中
					ActionContext.getContext().getSession()
							.put(SessionKeys.userInfo.toString(), userInfo);
					String operationInfo = MessageFormat.format(
							getText("user.login.success"), tempUserId,
							userInfo.getUserName());
					logger.info(operationInfo);
					
				}
			} catch (UnsupportedEncodingException e) {
				errMsg = MessageFormat.format(getText("pwd.base.error"),
						tempUserId);
				this.addActionError(errMsg);
				logger.error(LoginStatusCode.LOGIN_EXCEPTION,
						errMsg + e.getMessage(), e);
				return RET_FAILURE;
			} catch (IOException e1) {// 解密抛出异常
				LOG.error(e1.getMessage());
				throw new RuntimeException(e1.getMessage(), e1.getCause());
			}

		} else { // 用户不存在
			if (!verifyFlag) {
				errMsg = "验证码不正确！";
			} else {
				errMsg = getText("user.name.psw.error");
			}
			logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg);
			ActionContext.getContext().getSession()
					.put(SessionKeys.invalid.toString(), errMsg);
			this.addActionError(errMsg);
			return RET_INVALID;
		}

		return RET_SUCCESS;
	}

	/**
	 * 
	 * updateUserStatus 登录校验用户状态时需更新status，loginFaildNum，lockTime
	 * 
	 * @param userInfo
	 */
	private void updateUserStatus(UserBean userInfo) {
		try {
			ibatisDAO.updateData("updateUserState", userInfo);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"修改用户状态、失败登录次数、锁定时间失败", e);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"修改用户状态、失败登录次数、锁定时间失败", e);
		}
	}

	/**
	 * 
	 * checkLockTime 用户名密码错时，校验锁定时间是否为停用 登录失败三次状态变为锁定时，需要记录锁定时间
	 * 
	 * @param userInfo
	 *            用户信息
	 */
	private void checkLockTime(UserBean userInfo) {
		if (!"".equals(userInfo.getLockTime())
				&& null != userInfo.getLockTime()) { // 锁定时间不为空
			userInfo.setLockTime(userInfo.getLockTime().trim() + "-"
					+ DateParse.generateDateFormatyyyyMMddHHmmss());
		} else {
			userInfo.setLockTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		}
		if (userInfo.getLockTime().split("-").length > Constants.LOCKNUM) { // 锁定次数大于3次
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMddHHmmss");
			long last1Time = 0;
			long last3Time = 0;
			long cc;
			try {
				last1Time = timeformat
						.parse(userInfo.getLockTime().split("-")[Constants.LOCKNUM - 1])
						.getTime();
				last3Time = timeformat.parse(
						userInfo.getLockTime().split("-")[0]).getTime();
			} catch (ParseException e) {
				logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "时间解析错误", e);
			}
			cc = last1Time - last3Time;
			if (cc < Constants.NOTUSEDTIME) { // 三次锁定时间间隔小于24小时
				userInfo.setStatus(UserStatus.DISABLE); // 登录状态变为停用
			}
		}
	}

	/**
	 * 
	 * validateInputPara 校验验证码和用户名、密码是否为空
	 * 
	 * @return boolean
	 */
	private boolean validateInputPara() {
		if (null == verify || ("").equals(verify)) {
			errMsg = getText("user.name.verify.null");
			logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg,
					null);
			ActionContext.getContext().getSession()
					.put(SessionKeys.invalid.toString(), errMsg);
			return false;
		}
		/*
		 * // 校验验证码 String sessionRS = (String)
		 * ActionContext.getContext().getSession() .get("random"); if (sessionRS
		 * == null || !verify.equals(sessionRS)) { errMsg =
		 * getText("user.name.verify.error");
		 * ActionContext.getContext().getSession()
		 * .put(SessionKeys.invalid.toString(), errMsg);
		 * logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg);
		 * return false; }
		 */
		if (null == aesUserId || null == password || ("").equals(aesUserId)
				|| ("").equals(password)) {
			errMsg = getText("user.name.psw.null");
			logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg,
					null);
			ActionContext.getContext().getSession()
					.put(SessionKeys.invalid.toString(), errMsg);
			return false;
		}

		return true;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the aesUserId
	 */
	public String getAesUserId() {
		return aesUserId;
	}

	/**
	 * @param aesUserId
	 *            the aesUserId to set
	 */
	public void setAesUserId(String aesUserId) {
		this.aesUserId = aesUserId;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

}
