/*******************************************************************************
 * @(#)VMInfo.java 2013-2-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

/**
 * 虚拟机状态信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-26 下午01:45:23
 */
public class VMInfo {
    /**
     * 虚拟机编码
     */
    private String vmId;

    /**
     * 虚拟机状态
     */
    private String vmState;

    /**
     * 操作流水号
     */
    private String serialNum;

    /**
     * 操作URL
     */
    private String operationURL;

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

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmState() {
        return vmState;
    }

    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    /**
     * 获取operationURL字段数据
     * @return Returns the operationURL.
     */
    public String getOperationURL() {
        return operationURL;
    }

    /**
     * 设置operationURL字段数据
     * @param operationURL The operationURL to set.
     */
    public void setOperationURL(String operationURL) {
        this.operationURL = operationURL;
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

}
