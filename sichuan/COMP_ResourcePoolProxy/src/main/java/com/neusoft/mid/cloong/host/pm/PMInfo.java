/*******************************************************************************
 * @(#)PMInfo.java 2014-1-17
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

/**
 * 物理机信息
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:49:54
 */
public class PMInfo {

	/**
     * 物理机编码
     */
    private String pmId;

    /**
     * 物理机状态
     */
    private String pmState;

    /**
     * 操作流水号
     */
    private String serialNum;

    /**
     * 操作IP
     */
    private String operationIP;

    /**
     * 登陆用户名
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String password;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmState() {
        return pmState;
    }

    public void setPmState(String pmState) {
        this.pmState = pmState;
    }

    /**
     * 获取userName字段数据
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName字段数据
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取password字段数据
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password字段数据
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

	public String getOperationIP() {
		return operationIP;
	}

	public void setOperationIP(String operationIP) {
		this.operationIP = operationIP;
	}
}
