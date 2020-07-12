/*******************************************************************************
 * @(#)EbsInfo.java 2015年1月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

/**
 * 虚拟硬盘bean
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月7日 下午6:50:43
 */
public class EbsInfo {

    /**
     * 虚拟硬盘ID
     */
    private String ebsId;
    
    /**
     * 虚拟硬盘名称
     */
    private String ebsName;
    
    /**
     * 存储性能级别
     */
    private String tier;
    
    /**
     * 磁盘空间（单位：GB）
     */
    private String diskSize;
    
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
     * 挂载的资源ID
     */
    private String parentId;
    
    /**
     * 挂载的资源类型
     */
    private String parentType;
    
    /**
     * 挂载的资源名称
     */
    private String vmName;

    /**
     * 创建标志
     */
    private String createFlag;
    
    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 获取ebsId字段数据
     * @return Returns the ebsId.
     */
    public String getEbsId() {
        return ebsId;
    }

    /**
     * 设置ebsId字段数据
     * @param ebsId The ebsId to set.
     */
    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    /**
     * 获取ebsName字段数据
     * @return Returns the ebsName.
     */
    public String getEbsName() {
        return ebsName;
    }

    /**
     * 设置ebsName字段数据
     * @param ebsName The ebsName to set.
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    /**
     * 获取tier字段数据
     * @return Returns the tier.
     */
    public String getTier() {
        return tier;
    }

    /**
     * 设置tier字段数据
     * @param tier The tier to set.
     */
    public void setTier(String tier) {
        this.tier = tier;
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
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
    
}
