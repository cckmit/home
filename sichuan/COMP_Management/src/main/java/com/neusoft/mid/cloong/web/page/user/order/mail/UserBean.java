/*******************************************************************************
 * @(#)UserBean.java 2014年1月10日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order.mail;

/**
 * 用户基本信息Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月10日 上午8:56:56
 */
public class UserBean {

    /**
     * 用户ID，即用户的帐号
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 用户email
     */
    private String email;


    /**
     * 
     * getUserId TODO 方法
     * @return TODO
     */
	public String getUserId() {
		return userId;
	}

	/**
	 * 
	 * setUserId TODO 方法
	 * @param userId TODO
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * getUserName TODO 方法
	 * @return TODO
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * setUserName TODO 方法
	 * @param userName TODO
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * getMobile TODO 方法
	 * @return TODO
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 
	 * setMobile TODO 方法
	 * @param mobile TODO
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 
	 * getEmail TODO 方法
	 * @return TODO
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * setEmail TODO 方法
	 * @param email TODO
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
