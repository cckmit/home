/*******************************************************************************
 * @(#)RtInfo.java 2015年11月6日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

/**
 * 路由器bean
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class RtInfo {

	/**
     * 路由器ID
     */
    private String routerId;
	
    /**
     * 路由器名称
     */
    private String routerName;
    
    /**
     * 设备型号
     */
    private String routerType;
    
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
    private String routerIp;
    
    /**
     * 当前状态
     */
    private String curStatus;
    
    /**
     * 设备序列号
     */
    private String routerSerialnum;
    
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
     * 端口ID
     */
    private String routerIfId;
    
    /**
     * 端口名称
     */
    private String routerIfName;
    
    /**
     * 端口描述
     */
    private String ifDescription;
    
    /**
     * 端口状态
     */
    private String ifStatus; 
    
    /**
     * 端口类型
     */
    private String ifType; 
    
    /**
     * 端口本端配置IP地址
     */
    private String ifSetLocalIp; 
    
    /**
     * 端口IP网络掩码
     */
    private String ipSubNetmask; 
    
    /**
     * 端口所连接设备的IP地址
     */
    private String ifConnectEqpIp; 
    
    /**
     * 端口物理地址
     */
    private String ifPhyAddress; 
    
    /**
     * 端口速率
     */
    private String ifSpeed;
    
    /**
     * 端口所连对端端口唯一标识
     */
    private String destIfId;

	/**
	 * @return the routerId
	 */
	public String getRouterId() {
		return routerId;
	}

	/**
	 * @param routerId the routerId to set
	 */
	public void setRouterId(String routerId) {
		this.routerId = routerId;
	}

	/**
	 * @return the routerName
	 */
	public String getRouterName() {
		return routerName;
	}

	/**
	 * @param routerName the routerName to set
	 */
	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	/**
	 * @return the routerType
	 */
	public String getRouterType() {
		return routerType;
	}

	/**
	 * @param routerType the routerType to set
	 */
	public void setRouterType(String routerType) {
		this.routerType = routerType;
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
	 * @return the routerIp
	 */
	public String getRouterIp() {
		return routerIp;
	}

	/**
	 * @param routerIp the routerIp to set
	 */
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
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
	 * @return the routerSerialnum
	 */
	public String getRouterSerialnum() {
		return routerSerialnum;
	}

	/**
	 * @param routerSerialnum the routerSerialnum to set
	 */
	public void setRouterSerialnum(String routerSerialnum) {
		this.routerSerialnum = routerSerialnum;
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
	 * @return the routerIfId
	 */
	public String getRouterIfId() {
		return routerIfId;
	}

	/**
	 * @param routerIfId the routerIfId to set
	 */
	public void setRouterIfId(String routerIfId) {
		this.routerIfId = routerIfId;
	}

	/**
	 * @return the routerIfName
	 */
	public String getRouterIfName() {
		return routerIfName;
	}

	/**
	 * @param routerIfName the routerIfName to set
	 */
	public void setRouterIfName(String routerIfName) {
		this.routerIfName = routerIfName;
	}

	/**
	 * @return the ifDescription
	 */
	public String getIfDescription() {
		return ifDescription;
	}

	/**
	 * @param ifDescription the ifDescription to set
	 */
	public void setIfDescription(String ifDescription) {
		this.ifDescription = ifDescription;
	}

	/**
	 * @return the ifStatus
	 */
	public String getIfStatus() {
		return ifStatus;
	}

	/**
	 * @param ifStatus the ifStatus to set
	 */
	public void setIfStatus(String ifStatus) {
		this.ifStatus = ifStatus;
	}

	/**
	 * @return the ifType
	 */
	public String getIfType() {
		return ifType;
	}

	/**
	 * @param ifType the ifType to set
	 */
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}

	/**
	 * @return the ifSetLocalIp
	 */
	public String getIfSetLocalIp() {
		return ifSetLocalIp;
	}

	/**
	 * @param ifSetLocalIp the ifSetLocalIp to set
	 */
	public void setIfSetLocalIp(String ifSetLocalIp) {
		this.ifSetLocalIp = ifSetLocalIp;
	}

	/**
	 * @return the ipSubNetmask
	 */
	public String getIpSubNetmask() {
		return ipSubNetmask;
	}

	/**
	 * @param ipSubNetmask the ipSubNetmask to set
	 */
	public void setIpSubNetmask(String ipSubNetmask) {
		this.ipSubNetmask = ipSubNetmask;
	}

	/**
	 * @return the ifConnectEqpIp
	 */
	public String getIfConnectEqpIp() {
		return ifConnectEqpIp;
	}

	/**
	 * @param ifConnectEqpIp the ifConnectEqpIp to set
	 */
	public void setIfConnectEqpIp(String ifConnectEqpIp) {
		this.ifConnectEqpIp = ifConnectEqpIp;
	}

	/**
	 * @return the ifPhyAddress
	 */
	public String getIfPhyAddress() {
		return ifPhyAddress;
	}

	/**
	 * @param ifPhyAddress the ifPhyAddress to set
	 */
	public void setIfPhyAddress(String ifPhyAddress) {
		this.ifPhyAddress = ifPhyAddress;
	}

	/**
	 * @return the ifSpeed
	 */
	public String getIfSpeed() {
		return ifSpeed;
	}

	/**
	 * @param ifSpeed the ifSpeed to set
	 */
	public void setIfSpeed(String ifSpeed) {
		this.ifSpeed = ifSpeed;
	}

	/**
	 * @return the destIfId
	 */
	public String getDestIfId() {
		return destIfId;
	}

	/**
	 * @param destIfId the destIfId to set
	 */
	public void setDestIfId(String destIfId) {
		this.destIfId = destIfId;
	}
    
}
