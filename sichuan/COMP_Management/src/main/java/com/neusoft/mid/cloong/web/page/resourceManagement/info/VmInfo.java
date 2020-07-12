/*******************************************************************************
 * @(#)VmInfo.java 2015年1月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

/**
 * 虚拟机bean
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月7日 下午5:57:30
 */
public class VmInfo {

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 私网IP
     */
    private String privateIp;

    /**
     * 网络掩码
     */
    private String netMask;

    /**
     * 绑定的cluster浮动ip地址
     */
    private String clusterIp;

    /**
     * cpu数量
     */
    private String cpuNum;

    /**
     * cpu主频
     */
    private String cpuFrequency;

    /**
     * 内存容量
     */
    private String memorySize;

    /**
     * 磁盘空间
     */
    private String diskSize;

    /**
     * 投入生产运行的时间
     */
    private String runTime;

    /**
     * 当前状态
     */
    private String curStatus;

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
     * 虚拟网卡个数
     */
    private String ethadaNum;

    /**
     * 虚拟SCSI卡个数
     */
    private String scsiadaNum;

    /**
     * 虚拟FC-HBA卡个数
     */
    private String fchbaNum;

    /**
     * 操作系统盘类型
     */
    private String osType;

    /**
     * 操作系统
     */
    private String vmOs;

    /**
     * 所属物理机ID
     */
    private String pmId;

    /**
     * 所属物理机名称
     */
    private String pmName;

    /**
     * 创建标志
     */
    private String createFlag;

    /**
     * 更新时间
     */
    private String updateTime;
    
    
    
    
    public String querycurStatus;
    
    /**
     * 公网IP.
     */
    private String publicIp;

    /**
     * 
     * 
     * 
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 获取vmName字段数据
     * @return Returns the vmName.
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 设置vmName字段数据
     * @param vmName The vmName to set.
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    /**
     * 获取privateIp字段数据
     * @return Returns the privateIp.
     */
    public String getPrivateIp() {
        return privateIp;
    }

    /**
     * 设置privateIp字段数据
     * @param privateIp The privateIp to set.
     */
    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
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
     * 获取vmOs字段数据
     * @return Returns the vmOs.
     */
    public String getVmOs() {
        return vmOs;
    }

    /**
     * 设置vmOs字段数据
     * @param vmOs The vmOs to set.
     */
    public void setVmOs(String vmOs) {
        this.vmOs = vmOs;
    }

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
     * 获取fchbaNum字段数据
     * @return Returns the fchbaNum.
     */
    public String getFchbaNum() {
        return fchbaNum;
    }

    /**
     * 设置fchbaNum字段数据
     * @param fchbaNum The fchbaNum to set.
     */
    public void setFchbaNum(String fchbaNum) {
        this.fchbaNum = fchbaNum;
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
	 * @return the publicIp
	 */
	public String getPublicIp() {
		return publicIp;
	}

	/**
	 * @param publicIp the publicIp to set
	 */
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
    

}
