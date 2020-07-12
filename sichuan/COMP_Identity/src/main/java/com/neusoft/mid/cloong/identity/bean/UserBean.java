/*******************************************************************************
 * @(#)UserBean.java 2014年1月10日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.core.UserStatus;

/**
 * 用户基本信息Bean
 * 
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月10日 上午8:56:56
 */
public class UserBean extends IdentityBean {

	/**
	 * 用户ID，即用户的帐号
	 */
	private String userId;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 用户密码，MD5加密
	 */
	private String password;

	/**
	 * 用户登录错误时间
	 */
	private String loginFailedTime;

	/**
	 * 用户锁定的历史时间(默认最近三次)
	 */
	private String lockTime;

	/**
	 * 用户手机号码
	 */
	private String mobile;

	/**
	 * 用户email
	 */
	private String email;

	/**
	 * 用户地址
	 */
	private String address;

	/**
	 * 用户固定电话
	 */
	private String telphone;

	/**
	 * 用户传真
	 */
	private String fax;

	/**
	 * 用户描述信息
	 */
	private String desc;

	/**
	 * 用户的部门名称
	 */
	private String departmentName;

	/**
	 * 用户绑定的角色
	 */
	private List<RoleBean> roles = new ArrayList<RoleBean>();

	/**
	 * 用户绑定的业务
	 */
	private List<UserAppBean> apps = new ArrayList<UserAppBean>();

	/**
	 * 用户绑定的业务Id
	 */
	private List<String> appIdList = new ArrayList<String>();

	/**
	 * 用户绑定的业务Id字符串
	 */
	private String appIdStr;
	
	/**
	 * 用户绑定的用户组
	 */
	private List<GroupBean> groups;

	/**
	 * 用户的状态
	 */
	private UserStatus status;

	/**
	 * 确认密码
	 */
	private String comfirmPassword;

	/**
	 * 旧密码
	 */
	private String oldPassword;
	/**
	 * 密保问题
	 */
	private String security_question;
	/**
	 * 密保答案
	 */
	private String security_answer;

	/**
     * 用户应用名称.
     * @author zhangyunfeng.
     */
    private String appName;
    
    /**
     * 应用ID.
     * @author zhangyunfeng
     */
    private String appId;
    
    /**
	 * 审批意见
	 */
    private String auditInfo;
    
    /**
     * 用户计费ID.
     */
    private String chargesUserId;
    
    private String ifLogin;
    
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 获取comfirmPassword字段数据
	 * 
	 * @return Returns the comfirmPassword.
	 */
	public String getComfirmPassword() {
		return comfirmPassword;
	}

	/**
	 * 设置comfirmPassword字段数据
	 * 
	 * @param comfirmPassword
	 *            The comfirmPassword to set.
	 */
	public void setComfirmPassword(String comfirmPassword) {
		this.comfirmPassword = comfirmPassword;
	}

	/**
	 * 获取oldPassword字段数据
	 * 
	 * @return Returns the oldPassword.
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * 设置oldPassword字段数据
	 * 
	 * @param oldPassword
	 *            The oldPassword to set.
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * 获取status字段数据
	 * 
	 * @return Returns the status.
	 */
	public UserStatus getStatus() {
		return status;
	}

	/**
	 * 设置status字段数据
	 * 
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
	 * 获取userId字段数据
	 * 
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置userId字段数据
	 * 
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取userName字段数据
	 * 
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置userName字段数据
	 * 
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取password字段数据
	 * 
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置password字段数据
	 * 
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取mobile字段数据
	 * 
	 * @return Returns the mobile.
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置mobile字段数据
	 * 
	 * @param mobile
	 *            The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取email字段数据
	 * 
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置email字段数据
	 * 
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取address字段数据
	 * 
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置address字段数据
	 * 
	 * @param address
	 *            The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取telphone字段数据
	 * 
	 * @return Returns the telphone.
	 */
	public String getTelphone() {
		return telphone;
	}

	/**
	 * 设置telphone字段数据
	 * 
	 * @param telphone
	 *            The telphone to set.
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * 获取fax字段数据
	 * 
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 设置fax字段数据
	 * 
	 * @param fax
	 *            The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 获取desc字段数据
	 * 
	 * @return Returns the desc.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置desc字段数据
	 * 
	 * @param desc
	 *            The desc to set.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取departmentName字段数据
	 * 
	 * @return Returns the departmentName.
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @return the chargesUserId
	 */
	public String getChargesUserId() {
		return chargesUserId;
	}

	/**
	 * @param chargesUserId the chargesUserId to set
	 */
	public void setChargesUserId(String chargesUserId) {
		this.chargesUserId = chargesUserId;
	}

	/**
	 * 设置departmentName字段数据
	 * 
	 * @param departmentName
	 *            The departmentName to set.
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * 获取roles字段数据
	 * 
	 * @return Returns the roles.
	 */
	public List<RoleBean> getRoles() {
		return roles;
	}

	/**
	 * 设置roles字段数据
	 * 
	 * @param roles
	 *            The roles to set.
	 */
	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}

	/**
	 * 获取groups字段数据
	 * 
	 * @return Returns the groups.
	 */
	public List<GroupBean> getGroups() {
		return groups;
	}

	/**
	 * 设置groups字段数据
	 * 
	 * @param groups
	 *            The groups to set.
	 */
	public void setGroups(List<GroupBean> groups) {
		this.groups = groups;
	}

	/**
	 * 获取lockTime字段数据
	 * 
	 * @return Returns the lockTime.
	 */
	public String getLockTime() {
		return lockTime;
	}

	/**
	 * 设置lockTime字段数据
	 * 
	 * @param lockTime
	 *            The lockTime to set.
	 */
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}

	/**
	 * 获取loginFailedTime字段数据
	 * 
	 * @return Returns the loginFailedTime.
	 */
	public String getLoginFailedTime() {
		return loginFailedTime;
	}

	/**
	 * 设置loginFailedTime字段数据
	 * 
	 * @param loginFailedTime
	 *            The loginFailedTime to set.
	 */
	public void setLoginFailedTime(String loginFailedTime) {
		this.loginFailedTime = loginFailedTime;
	}

	/**
	 * @return the security_question
	 */
	public String getSecurity_question() {
		return security_question;
	}

	/**
	 * @return the security_answer
	 */
	public String getSecurity_answer() {
		return security_answer;
	}

	/**
	 * @param security_question
	 *            the security_question to set
	 */
	public void setSecurity_question(String security_question) {
		this.security_question = security_question;
	}

	/**
	 * @param security_answer
	 *            the security_answer to set
	 */
	public void setSecurity_answer(String security_answer) {
		this.security_answer = security_answer;
	}

	/**
	 * @return the apps
	 */
	public List<UserAppBean> getApps() {
		return apps;
	}

	/**
	 * @param apps
	 *            the apps to set
	 */
	public void setApps(List<UserAppBean> apps) {
		this.apps = apps;
	}

	public List<String> getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(List<String> appIdList) {
		this.appIdList = appIdList;
	}

	public String getAppIdStr() {
		return appIdStr;
	}

	public void setAppIdStr(String appIdStr) {
		this.appIdStr = appIdStr;
	}

	/**
	 * @return the auditInfo
	 */
	public String getAuditInfo() {
		return auditInfo;
	}

	/**
	 * @param auditInfo the auditInfo to set
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	/**
	 * @return the ifLogin
	 */
	public String getIfLogin() {
		return ifLogin;
	}

	/**
	 * @param ifLogin the ifLogin to set
	 */
	public void setIfLogin(String ifLogin) {
		this.ifLogin = ifLogin;
	}

}
