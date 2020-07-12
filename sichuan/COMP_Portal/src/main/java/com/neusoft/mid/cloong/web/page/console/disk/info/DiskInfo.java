/*******************************************************************************
 * @(#)DiskInfo.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk.info;

import java.io.Serializable;
import java.util.List;

/**
 * 云硬盘信息
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:18:58
 */
public class DiskInfo implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 实例ID
	 */
	private String caseId;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 云硬盘ID
	 */
	private String diskId;

	/**
	 * 云硬盘容量
	 */
	private String diskSize;

	/**
	 * 云硬盘类型
	 */
	private String resourceType;

	/**
	 * 云硬盘名称
	 */
	private String diskName;

	/**
	 * 申请时长
	 */
	private String diskLength;

	/**
	 * 时长单位
	 */
	private String timeUnit;

	/**
	 * 云硬盘状态
	 */
	private String diskStatus;

	/**
	 * 挂载的虚拟机
	 */
	private String mountVmName;

	/**
	 * 挂载点
	 */
	private String mountPoint;

	/**
	 * 挂载主机ID
	 */
	private String hostId;

	/**
	 * 拥有者
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 过期时间
	 */
	private String expireTime;

	/**
	 * 资源池ID
	 */
	private String resourcePoolId;

	/**
	 * 资源池分区ID
	 */
	private String resourcePoolPartId;

	/**
	 * 云硬盘所属的业务ID
	 */
	private String businessId;

	/**
	 * 业务IDlist
	 */
	private List<String> businessList;

	/**
	 * 业务名称
	 */
	private String appName;

	/**
	 * 更新人
	 */
	private String updateUser;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 查询状态list
	 */
	private List<String> statusList;

	private String ebsBossOrderId;

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public String getDiskName() {
		return diskName;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	public String getDiskLength() {
		return diskLength;
	}

	public void setDiskLength(String diskLength) {
		this.diskLength = diskLength;
	}

	public String getDiskStatus() {
		return diskStatus;
	}

	public void setDiskStatus(String diskStatus) {
		this.diskStatus = diskStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMountVmName() {
		return mountVmName;
	}

	public void setMountVmName(String mountVmName) {
		this.mountVmName = mountVmName;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @param hostId
	 *            the hostId to set
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getResourcePoolId() {
		return resourcePoolId;
	}

	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}

	public String getResourcePoolPartId() {
		return resourcePoolPartId;
	}

	public void setResourcePoolPartId(String resourcePoolPartId) {
		this.resourcePoolPartId = resourcePoolPartId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 获取createUser字段数据
	 * 
	 * @return Returns the createUser.
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置createUser字段数据
	 * 
	 * @param createUser
	 *            The createUser to set.
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 获取businessId字段数据
	 * 
	 * @return Returns the businessId.
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * 设置businessId字段数据
	 * 
	 * @param businessId
	 *            The businessId to set.
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * 获取businessList字段数据
	 * 
	 * @return Returns the businessList.
	 */
	public List<String> getBusinessList() {
		return businessList;
	}

	/**
	 * 设置businessList字段数据
	 * 
	 * @param businessList
	 *            The businessList to set.
	 */
	public void setBusinessList(List<String> businessList) {
		this.businessList = businessList;
	}

	/**
	 * 获取updateUser字段数据
	 * 
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 设置updateUser字段数据
	 * 
	 * @param updateUser
	 *            The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 获取updateTime字段数据
	 * 
	 * @return Returns the updateTime.
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置updateTime字段数据
	 * 
	 * @param updateTime
	 *            The updateTime to set.
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取orderId字段数据
	 * 
	 * @return Returns the orderId.
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 设置orderId字段数据
	 * 
	 * @param orderId
	 *            The orderId to set.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取statusList字段数据
	 * 
	 * @return Returns the statusList.
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	/**
	 * 设置statusList字段数据
	 * 
	 * @param statusList
	 *            The statusList to set.
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getEbsBossOrderId() {
		return ebsBossOrderId;
	}

	public void setEbsBossOrderId(String ebsBossOrderId) {
		this.ebsBossOrderId = ebsBossOrderId;
	}

}
