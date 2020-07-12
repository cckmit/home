/*******************************************************************************
 * @(#)MiniPmInfo.java 2015年1月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

/**
 * 小型机bean
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年2月11日 上午9:43:38
 */
public class MiniPmInfo {

    /**
     * 小型机ID
     */
    private String miniPmId;

    /**
     * 小型机名称
     */
    private String miniPmName;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 网络掩码
     */
    private String netMask;

    /**
     * 绑定的cluster浮动IP地址
     */
    private String clusterIp;

    /**
     * 主机型号
     */
    private String serverType;

    /**
     * CPU数量
     */
    private String cpuNum;

    /**
     * CPU类型
     */
    private String cpuType;

    /**
     * CPU主频
     */
    private String cpuFrequency;

    /**
     * 内存容量
     */
    private String memorySize;

    /**
     * 内存条数量
     */
    private String memoryNum;

    /**
     * 磁盘空间（单位：GB）
     */
    private String diskSize;

    /**
     * 硬盘个数
     */
    private String diskNum;

    /**
     * 系统网络接口总数
     */
    private String ifNum;

    /**
     * 主板数量
     */
    private String mainbordNum;

    /**
     * 电源模块数量
     */
    private String powerMoudleNum;

    /**
     * 风扇数量
     */
    private String funNum;

    /**
     * 设备厂商
     */
    private String vendorName;

    /**
     * 投入生产运行的时间
     */
    private String runTime;

    /**
     * 当前状态
     */
    private String curStatus;

    /**
     * 设备序列号
     */
    private String serialNum;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池名称
     */
    private String resPoolName;

    /**
     * 资源池分区ID
     */
    private String resPoolPartId;

    /**
     * 资源池分区名称
     */
    private String resPoolPartName;

    /**
     * 操作系统
     */
    private String osType;

    /**
     * 连接到的交换机及端口标识
     */
    private String switchIfRelations;

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
     * 创建标志
     */
    private String createFlag;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 所属机房ID
     */
    private String machinerRoomId;

    /**
     * 所属机房名称
     */
    private String machinerRoomName;

    /**
     * 所属机柜ID
     */
    private String cabinetId;

    /**
     * 所属机柜名称
     */
    private String cabinetName;

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取netMask字段数据
     * @return Returns the netMask.
     */
    public String getNetMask() {
        return netMask;
    }

    /**
     * 设置netMask字段数据
     * @param netMask The netMask to set.
     */
    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    /**
     * 获取clusterIp字段数据
     * @return Returns the clusterIp.
     */
    public String getClusterIp() {
        return clusterIp;
    }

    /**
     * 设置clusterIp字段数据
     * @param clusterIp The clusterIp to set.
     */
    public void setClusterIp(String clusterIp) {
        this.clusterIp = clusterIp;
    }

    /**
     * 获取serverType字段数据
     * @return Returns the serverType.
     */
    public String getServerType() {
        return serverType;
    }

    /**
     * 设置serverType字段数据
     * @param serverType The serverType to set.
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    /**
     * 获取cpuNum字段数据
     * @return Returns the cpuNum.
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 获取cpuType字段数据
     * @return Returns the cpuType.
     */
    public String getCpuType() {
        return cpuType;
    }

    /**
     * 设置cpuType字段数据
     * @param cpuType The cpuType to set.
     */
    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    /**
     * 获取cpuFrequency字段数据
     * @return Returns the cpuFrequency.
     */
    public String getCpuFrequency() {
        return cpuFrequency;
    }

    /**
     * 设置cpuFrequency字段数据
     * @param cpuFrequency The cpuFrequency to set.
     */
    public void setCpuFrequency(String cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    /**
     * 获取memorySize字段数据
     * @return Returns the memorySize.
     */
    public String getMemorySize() {
        return memorySize;
    }

    /**
     * 设置memorySize字段数据
     * @param memorySize The memorySize to set.
     */
    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    /**
     * 获取memoryNum字段数据
     * @return Returns the memoryNum.
     */
    public String getMemoryNum() {
        return memoryNum;
    }

    /**
     * 设置memoryNum字段数据
     * @param memoryNum The memoryNum to set.
     */
    public void setMemoryNum(String memoryNum) {
        this.memoryNum = memoryNum;
    }

    /**
     * 获取diskSize字段数据
     * @return Returns the diskSize.
     */
    public String getDiskSize() {
        return diskSize;
    }

    /**
     * 设置diskSize字段数据
     * @param diskSize The diskSize to set.
     */
    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    /**
     * 获取diskNum字段数据
     * @return Returns the diskNum.
     */
    public String getDiskNum() {
        return diskNum;
    }

    /**
     * 设置diskNum字段数据
     * @param diskNum The diskNum to set.
     */
    public void setDiskNum(String diskNum) {
        this.diskNum = diskNum;
    }

    /**
     * 获取ifNum字段数据
     * @return Returns the ifNum.
     */
    public String getIfNum() {
        return ifNum;
    }

    /**
     * 设置ifNum字段数据
     * @param ifNum The ifNum to set.
     */
    public void setIfNum(String ifNum) {
        this.ifNum = ifNum;
    }

    /**
     * 获取mainbordNum字段数据
     * @return Returns the mainbordNum.
     */
    public String getMainbordNum() {
        return mainbordNum;
    }

    /**
     * 设置mainbordNum字段数据
     * @param mainbordNum The mainbordNum to set.
     */
    public void setMainbordNum(String mainbordNum) {
        this.mainbordNum = mainbordNum;
    }

    /**
     * 获取powerMoudleNum字段数据
     * @return Returns the powerMoudleNum.
     */
    public String getPowerMoudleNum() {
        return powerMoudleNum;
    }

    /**
     * 设置powerMoudleNum字段数据
     * @param powerMoudleNum The powerMoudleNum to set.
     */
    public void setPowerMoudleNum(String powerMoudleNum) {
        this.powerMoudleNum = powerMoudleNum;
    }

    /**
     * 获取funNum字段数据
     * @return Returns the funNum.
     */
    public String getFunNum() {
        return funNum;
    }

    /**
     * 设置funNum字段数据
     * @param funNum The funNum to set.
     */
    public void setFunNum(String funNum) {
        this.funNum = funNum;
    }

    /**
     * 获取vendorName字段数据
     * @return Returns the vendorName.
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * 设置vendorName字段数据
     * @param vendorName The vendorName to set.
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * 获取runTime字段数据
     * @return Returns the runTime.
     */
    public String getRunTime() {
        return runTime;
    }

    /**
     * 设置runTime字段数据
     * @param runTime The runTime to set.
     */
    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    /**
     * 获取curStatus字段数据
     * @return Returns the curStatus.
     */
    public String getCurStatus() {
        return curStatus;
    }

    /**
     * 设置curStatus字段数据
     * @param curStatus The curStatus to set.
     */
    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    /**
     * 获取serialNum字段数据
     * @return Returns the serialNum.
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 设置serialNum字段数据
     * @param serialNum The serialNum to set.
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取resPoolId字段数据
     * @return Returns the resPoolId.
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 设置resPoolId字段数据
     * @param resPoolId The resPoolId to set.
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 获取resPoolPartId字段数据
     * @return Returns the resPoolPartId.
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 设置resPoolPartId字段数据
     * @param resPoolPartId The resPoolPartId to set.
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 获取osType字段数据
     * @return Returns the osType.
     */
    public String getOsType() {
        return osType;
    }

    /**
     * 设置osType字段数据
     * @param osType The osType to set.
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * 获取switchIfRelations字段数据
     * @return Returns the switchIfRelations.
     */
    public String getSwitchIfRelations() {
        return switchIfRelations;
    }

    /**
     * 设置switchIfRelations字段数据
     * @param switchIfRelations The switchIfRelations to set.
     */
    public void setSwitchIfRelations(String switchIfRelations) {
        this.switchIfRelations = switchIfRelations;
    }

    /**
     * 获取assetOriginType字段数据
     * @return Returns the assetOriginType.
     */
    public String getAssetOriginType() {
        return assetOriginType;
    }

    /**
     * 设置assetOriginType字段数据
     * @param assetOriginType The assetOriginType to set.
     */
    public void setAssetOriginType(String assetOriginType) {
        this.assetOriginType = assetOriginType;
    }

    /**
     * 获取assetState字段数据
     * @return Returns the assetState.
     */
    public String getAssetState() {
        return assetState;
    }

    /**
     * 设置assetState字段数据
     * @param assetState The assetState to set.
     */
    public void setAssetState(String assetState) {
        this.assetState = assetState;
    }

    /**
     * 获取assetSlaType字段数据
     * @return Returns the assetSlaType.
     */
    public String getAssetSlaType() {
        return assetSlaType;
    }

    /**
     * 设置assetSlaType字段数据
     * @param assetSlaType The assetSlaType to set.
     */
    public void setAssetSlaType(String assetSlaType) {
        this.assetSlaType = assetSlaType;
    }

    /**
     * 获取createFlag字段数据
     * @return Returns the createFlag.
     */
    public String getCreateFlag() {
        return createFlag;
    }

    /**
     * 设置createFlag字段数据
     * @param createFlag The createFlag to set.
     */
    public void setCreateFlag(String createFlag) {
        this.createFlag = createFlag;
    }

    /**
     * 获取updateTime字段数据
     * @return Returns the updateTime.
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime字段数据
     * @param updateTime The updateTime to set.
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取resPoolName字段数据
     * @return Returns the resPoolName.
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * 设置resPoolName字段数据
     * @param resPoolName The resPoolName to set.
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    /**
     * 获取resPoolPartName字段数据
     * @return Returns the resPoolPartName.
     */
    public String getResPoolPartName() {
        return resPoolPartName;
    }

    /**
     * 设置resPoolPartName字段数据
     * @param resPoolPartName The resPoolPartName to set.
     */
    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    /**
     * 获取machinerRoomId字段数据
     * @return Returns the machinerRoomId.
     */
    public String getMachinerRoomId() {
        return machinerRoomId;
    }

    /**
     * 设置machinerRoomId字段数据
     * @param machinerRoomId The machinerRoomId to set.
     */
    public void setMachinerRoomId(String machinerRoomId) {
        this.machinerRoomId = machinerRoomId;
    }

    /**
     * 获取machinerRoomName字段数据
     * @return Returns the machinerRoomName.
     */
    public String getMachinerRoomName() {
        return machinerRoomName;
    }

    /**
     * 设置machinerRoomName字段数据
     * @param machinerRoomName The machinerRoomName to set.
     */
    public void setMachinerRoomName(String machinerRoomName) {
        this.machinerRoomName = machinerRoomName;
    }

    /**
     * 获取cabinetId字段数据
     * @return Returns the cabinetId.
     */
    public String getCabinetId() {
        return cabinetId;
    }

    /**
     * 设置cabinetId字段数据
     * @param cabinetId The cabinetId to set.
     */
    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    /**
     * 获取cabinetName字段数据
     * @return Returns the cabinetName.
     */
    public String getCabinetName() {
        return cabinetName;
    }

    /**
     * 设置cabinetName字段数据
     * @param cabinetName The cabinetName to set.
     */
    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

	/**
	 * @return the miniPmId
	 */
	public String getMiniPmId() {
		return miniPmId;
	}

	/**
	 * @param miniPmId the miniPmId to set
	 */
	public void setMiniPmId(String miniPmId) {
		this.miniPmId = miniPmId;
	}

	/**
	 * @return the miniPmName
	 */
	public String getMiniPmName() {
		return miniPmName;
	}

	/**
	 * @param miniPmName the miniPmName to set
	 */
	public void setMiniPmName(String miniPmName) {
		this.miniPmName = miniPmName;
	}

}
