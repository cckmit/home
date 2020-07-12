/*******************************************************************************
 * @(#)FwInfo.java 2015年11月6日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

import java.util.ArrayList;
import java.util.List;

/**
 * 防火墙bean
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class FwInfo {

    /**
     * 防火墙ID
     */
    private String firewallId;

    /**
     * 防火墙名称
     */
    private String firewallName;

    /**
     * 型号
     */
    private String fwType;

    /**
     * 设备软件版本
     */
    private String swVersion;
    
    /**
     * 网元设备厂家
     */
    private String vendorName;

    /**
     * 网元IP地址
     */
    private String fwIp;

    /**
     * 当前状态
     */
    private String curStatus;

    /**
     * 设备序列号
     */
    private String fwSerialnum;

    /**
     * 吞吐能力
     */
    private String throughput;

    /**
     * 所属业务系统
     */
    private String appIds;
    
    private List appIdList = new ArrayList();
    
    /**
     * 所属业务系统名称
     */
    private String appNames;

    /**
     * 并发链接数
     */
    private String connectNum;

    /**
     * 新建链接数
     */
    private String newConnectNum;

    /**
     * 资产来源分类
     */
    private String assetOriginType;

    /**
     * 资产状态
     */
    private String assetState;

    /**
     * 资产SLA类型
     */
    private String assetSlaType;

    /**
     * 端口数量
     */
    private String portNum;

    /**
     * 防火墙策略
     */
    private String firewallPolicy;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池名称
     */
    private String resPoolName;

    /**
     * 创建标志
     */
    private String createFlag;

    /**
     * 更新时间
     */
    private String updateTime;

	/**
	 * @return the firewallId
	 */
	public String getFirewallId() {
		return firewallId;
	}

	/**
	 * @param firewallId the firewallId to set
	 */
	public void setFirewallId(String firewallId) {
		this.firewallId = firewallId;
	}

	/**
	 * @return the firewallName
	 */
	public String getFirewallName() {
		return firewallName;
	}

	/**
	 * @param firewallName the firewallName to set
	 */
	public void setFirewallName(String firewallName) {
		this.firewallName = firewallName;
	}

	/**
	 * @return the fwType
	 */
	public String getFwType() {
		return fwType;
	}

	/**
	 * @param fwType the fwType to set
	 */
	public void setFwType(String fwType) {
		this.fwType = fwType;
	}

	/**
	 * @return the swVersion
	 */
	public String getSwVersion() {
		return swVersion;
	}

	/**
	 * @param swVersion the swVersion to set
	 */
	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the fwIp
	 */
	public String getFwIp() {
		return fwIp;
	}

	/**
	 * @param fwIp the fwIp to set
	 */
	public void setFwIp(String fwIp) {
		this.fwIp = fwIp;
	}

	/**
	 * @return the curStatus
	 */
	public String getCurStatus() {
		return curStatus;
	}

	/**
	 * @param curStatus the curStatus to set
	 */
	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	/**
	 * @return the fwSerialnum
	 */
	public String getFwSerialnum() {
		return fwSerialnum;
	}

	/**
	 * @param fwSerialnum the fwSerialnum to set
	 */
	public void setFwSerialnum(String fwSerialnum) {
		this.fwSerialnum = fwSerialnum;
	}

	/**
	 * @return the throughput
	 */
	public String getThroughput() {
		return throughput;
	}

	/**
	 * @param throughput the throughput to set
	 */
	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}

	/**
	 * @return the appIds
	 */
	public String getAppIds() {
		return appIds;
	}

	/**
	 * @param appIds the appIds to set
	 */
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}

	/**
	 * @return the connectNum
	 */
	public String getConnectNum() {
		return connectNum;
	}

	/**
	 * @param connectNum the connectNum to set
	 */
	public void setConnectNum(String connectNum) {
		this.connectNum = connectNum;
	}

	/**
	 * @return the newConnectNum
	 */
	public String getNewConnectNum() {
		return newConnectNum;
	}

	/**
	 * @param newConnectNum the newConnectNum to set
	 */
	public void setNewConnectNum(String newConnectNum) {
		this.newConnectNum = newConnectNum;
	}

	/**
	 * @return the assetOriginType
	 */
	public String getAssetOriginType() {
		return assetOriginType;
	}

	/**
	 * @param assetOriginType the assetOriginType to set
	 */
	public void setAssetOriginType(String assetOriginType) {
		this.assetOriginType = assetOriginType;
	}

	/**
	 * @return the assetState
	 */
	public String getAssetState() {
		return assetState;
	}

	/**
	 * @param assetState the assetState to set
	 */
	public void setAssetState(String assetState) {
		this.assetState = assetState;
	}

	/**
	 * @return the assetSlaType
	 */
	public String getAssetSlaType() {
		return assetSlaType;
	}

	/**
	 * @param assetSlaType the assetSlaType to set
	 */
	public void setAssetSlaType(String assetSlaType) {
		this.assetSlaType = assetSlaType;
	}

	/**
	 * @return the portNum
	 */
	public String getPortNum() {
		return portNum;
	}

	/**
	 * @param portNum the portNum to set
	 */
	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}

	/**
	 * @return the firewallPolicy
	 */
	public String getFirewallPolicy() {
		return firewallPolicy;
	}

	/**
	 * @param firewallPolicy the firewallPolicy to set
	 */
	public void setFirewallPolicy(String firewallPolicy) {
		this.firewallPolicy = firewallPolicy;
	}

	/**
	 * @return the resPoolId
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * @param resPoolId the resPoolId to set
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * @return the resPoolName
	 */
	public String getResPoolName() {
		return resPoolName;
	}

	/**
	 * @param resPoolName the resPoolName to set
	 */
	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
	}

	/**
	 * @return the createFlag
	 */
	public String getCreateFlag() {
		return createFlag;
	}

	/**
	 * @param createFlag the createFlag to set
	 */
	public void setCreateFlag(String createFlag) {
		this.createFlag = createFlag;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the appNames
	 */
	public String getAppNames() {
		return appNames;
	}

	/**
	 * @param appNames the appNames to set
	 */
	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}

	/**
	 * @return the appIdList
	 */
	public List getAppIdList() {
		return appIdList;
	}

	/**
	 * @param appIdList the appIdList to set
	 */
	public void setAppIdList(List appIdList) {
		this.appIdList = appIdList;
	}

}
