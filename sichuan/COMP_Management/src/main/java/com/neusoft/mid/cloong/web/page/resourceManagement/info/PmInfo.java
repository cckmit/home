/*******************************************************************************
 * @(#)PmInfo.java 2015年1月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

//import com.neusoft.mid.cloong.web.page.resourceManagement.pm.action.CurStatus;
//import com.neusoft.mid.cloong.web.page.resourceManagement.pm.action.PmState;

/**
 * 物理机bean
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月7日 下午6:21:38
 */
public class PmInfo {

    /**
     * 物理机ID
     */
    private String pmId;

    /**
     * 物理机名称
     */
    private String pmName;

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
     * 主机单CPU核数
     */
    private String nucNumPerCpu;

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
     * 网卡个数
     */
    private String ethadaNum;

    /**
     * 网卡规格（千兆或万兆）
     */
    private String ethadaType;

    /**
     * SCSI卡个数
     */
    private String scsiadaNum;

    /**
     * HBA卡个数
     */
    private String hbaNum;

    /**
     * HBA卡规格（4GB或8GB）
     */
    private String hbaType;

    /**
     * 应用标识
     */
    private String usedFlag;

    /**
     * 虚拟化软件信息
     */
    private String hvInfo;

    /**
     * 虚拟化集群标识
     */
    private String hvpoolId;

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
     * 操作系统版本
     */
    private String osVersion;

    /**
     * CPU型号
     */
    private String cpuModel;

    /**
     * NTP服务IP
     */
    private String ntpIp;

    /**
     * 维保厂商
     */
    private String maintenanceFactory;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 互备方式描述
     */
    private String hbDescription;

    /**
     * 备份主机
     */
    private String bkPm;

    /**
     * 业务联系人
     */
    private String businessContact;

    /**
     * 系统交换区大小
     */
    private String sysExchangeAreaSize;

    /**
     * 上线时间
     */
    private String onlineTime;

    /**
     * TCPP值
     */
    private String tcppValue;

    /**
     * 应用描述
     */
    private String applicationdescribe;

    /**
     * 端口标签
     */
    private String portlabel;

    /**
     * CPU序列号
     */
    private String cpueqpserialnum;
    
    /**
     * 物理机分配状态
     */
    private String pmState;
    
    
    /**
     * 
     */
    private String querypmState;

    /**
     * 
     */
    private String querycurStatus;


    /**
     * 获取pmId字段数据
     * @return Returns the pmId.
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * 设置pmId字段数据
     * @param pmId The pmId to set.
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    /**
     * 获取pmName字段数据
     * @return Returns the pmName.
     */
    public String getPmName() {
        return pmName;
    }

    /**
     * 设置pmName字段数据
     * @param pmName The pmName to set.
     */
    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

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
     * 获取nucNumPerCpu字段数据
     * @return Returns the nucNumPerCpu.
     */
    public String getNucNumPerCpu() {
        return nucNumPerCpu;
    }

    /**
     * 设置nucNumPerCpu字段数据
     * @param nucNumPerCpu The nucNumPerCpu to set.
     */
    public void setNucNumPerCpu(String nucNumPerCpu) {
        this.nucNumPerCpu = nucNumPerCpu;
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
     * 获取ethadaNum字段数据
     * @return Returns the ethadaNum.
     */
    public String getEthadaNum() {
        return ethadaNum;
    }

    /**
     * 设置ethadaNum字段数据
     * @param ethadaNum The ethadaNum to set.
     */
    public void setEthadaNum(String ethadaNum) {
        this.ethadaNum = ethadaNum;
    }

    /**
     * 获取ethadaType字段数据
     * @return Returns the ethadaType.
     */
    public String getEthadaType() {
        return ethadaType;
    }

    /**
     * 设置ethadaType字段数据
     * @param ethadaType The ethadaType to set.
     */
    public void setEthadaType(String ethadaType) {
        this.ethadaType = ethadaType;
    }

    /**
     * 获取scsiadaNum字段数据
     * @return Returns the scsiadaNum.
     */
    public String getScsiadaNum() {
        return scsiadaNum;
    }

    /**
     * 设置scsiadaNum字段数据
     * @param scsiadaNum The scsiadaNum to set.
     */
    public void setScsiadaNum(String scsiadaNum) {
        this.scsiadaNum = scsiadaNum;
    }

    /**
     * 获取hbaNum字段数据
     * @return Returns the hbaNum.
     */
    public String getHbaNum() {
        return hbaNum;
    }

    /**
     * 设置hbaNum字段数据
     * @param hbaNum The hbaNum to set.
     */
    public void setHbaNum(String hbaNum) {
        this.hbaNum = hbaNum;
    }

    /**
     * 获取hbaType字段数据
     * @return Returns the hbaType.
     */
    public String getHbaType() {
        return hbaType;
    }

    /**
     * 设置hbaType字段数据
     * @param hbaType The hbaType to set.
     */
    public void setHbaType(String hbaType) {
        this.hbaType = hbaType;
    }

    /**
     * 获取usedFlag字段数据
     * @return Returns the usedFlag.
     */
    public String getUsedFlag() {
        return usedFlag;
    }

    /**
     * 设置usedFlag字段数据
     * @param usedFlag The usedFlag to set.
     */
    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
    }

    /**
     * 获取hvInfo字段数据
     * @return Returns the hvInfo.
     */
    public String getHvInfo() {
        return hvInfo;
    }

    /**
     * 设置hvInfo字段数据
     * @param hvInfo The hvInfo to set.
     */
    public void setHvInfo(String hvInfo) {
        this.hvInfo = hvInfo;
    }

    /**
     * 获取hvpoolId字段数据
     * @return Returns the hvpoolId.
     */
    public String getHvpoolId() {
        return hvpoolId;
    }

    /**
     * 设置hvpoolId字段数据
     * @param hvpoolId The hvpoolId to set.
     */
    public void setHvpoolId(String hvpoolId) {
        this.hvpoolId = hvpoolId;
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

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getNtpIp() {
        return ntpIp;
    }

    public void setNtpIp(String ntpIp) {
        this.ntpIp = ntpIp;
    }

    public String getMaintenanceFactory() {
        return maintenanceFactory;
    }

    public void setMaintenanceFactory(String maintenanceFactory) {
        this.maintenanceFactory = maintenanceFactory;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getHbDescription() {
        return hbDescription;
    }

    public void setHbDescription(String hbDescription) {
        this.hbDescription = hbDescription;
    }

    public String getBkPm() {
        return bkPm;
    }

    public void setBkPm(String bkPm) {
        this.bkPm = bkPm;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public String getSysExchangeAreaSize() {
        return sysExchangeAreaSize;
    }

    public void setSysExchangeAreaSize(String sysExchangeAreaSize) {
        this.sysExchangeAreaSize = sysExchangeAreaSize;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getTcppValue() {
        return tcppValue;
    }

    public void setTcppValue(String tcppValue) {
        this.tcppValue = tcppValue;
    }

    public String getApplicationdescribe() {
        return applicationdescribe;
    }

    public void setApplicationdescribe(String applicationdescribe) {
        this.applicationdescribe = applicationdescribe;
    }

    public String getPortlabel() {
        return portlabel;
    }

    public void setPortlabel(String portlabel) {
        this.portlabel = portlabel;
    }

    public String getCpueqpserialnum() {
        return cpueqpserialnum;
    }

    public void setCpueqpserialnum(String cpueqpserialnum) {
        this.cpueqpserialnum = cpueqpserialnum;
    }

	/**
	 * @return the pmState
	 */
	public String getPmState() {
		return pmState;
	}

	/**
	 * @param pmState the pmState to set
	 */
	public void setPmState(String pmState) {
		this.pmState = pmState;
	}
	 
	/**
	 * @return the querypmState
	 */
	public String getQuerypmState() {
		return querypmState;
	}

	/**
	 * @param querypmState the querypmState to set
	 */
	public void setQuerypmState(String querypmState) {
		this.querypmState = querypmState;
	}

	/**
	 * @return the querycurStatus
	 */
	public String getQuerycurStatus() {
		return querycurStatus;
	}

	/**
	 * @param querycurStatus the querycurStatus to set
	 */
	public void setQuerycurStatus(String querycurStatus) {
		this.querycurStatus = querycurStatus;
	}

	/**
	 * @param obtainItemStatusEunm
	 */
	//public void setCurStatus(CurStatus obtainItemStatusEunm) {
		// TODO Auto-generated method stub
		
	//}

	/**
	 * @param obtainItemStatusEunm
	 */
	//public void setPmState(PmState obtainItemStatusEunm) {
		// TODO Auto-generated method stub
		
	//}

	/**
	 * @param obtainItemStatusEunm
	 */
	/*public void setPmState(PmState obtainItemStatusEunm) {
		// TODO Auto-generated method stub
		
	}*/

   
	

}
