package com.neusoft.mid.cloong.web.page.user.order;

public class Vlan4SDNInstanceInfo {
	/**
     * 实例id
     */
    private String caseId;
    
    /**
     * VLAN范围ID
     */
    private String rangeId;
    
    /**
     * 名称
     */
    private String vlanName;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 业务ID
     */
    private String appId;
    
    /**
     * 业务名称
     */
    private String appName;
    /**
     * 资源池
     */
    private String resPoolId;
    
    /**
     * 资源池分区
     */
    private String resPoolPartId;
    
    /**
     * 资源池分区
     */
    private String resPoolPartName;
    
    /**
     * 是否取消
     */
    private String canceled;
    
    private String startId;
    
    private String endId;
    
    private String allocated;
    
    private String createTime;
    
    private String createUser;
    
    private String cancelTime;
    
    private String cancelUser;
    
    private String updateTime;
    
    private String updateUser;

	/**
	 * @return the caseId
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * @return the vlanName
	 */
	public String getVlanName() {
		return vlanName;
	}

	/**
	 * @param vlanName the vlanName to set
	 */
	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}

	/**
	 * @return the resPoolPartId
	 */
	public String getResPoolPartId() {
		return resPoolPartId;
	}

	/**
	 * @param resPoolPartId the resPoolPartId to set
	 */
	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	/**
	 * @return the resPoolPartName
	 */
	public String getResPoolPartName() {
		return resPoolPartName;
	}

	/**
	 * @param resPoolPartName the resPoolPartName to set
	 */
	public void setResPoolPartName(String resPoolPartName) {
		this.resPoolPartName = resPoolPartName;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the canceled
	 */
	public String getCanceled() {
		return canceled;
	}

	/**
	 * @param canceled the canceled to set
	 */
	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}

	public String getStartId() {
		return startId;
	}

	public void setStartId(String startId) {
		this.startId = startId;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public String getRangeId() {
		return rangeId;
	}

	public void setRangeId(String rangeId) {
		this.rangeId = rangeId;
	}

	public String getAllocated() {
		return allocated;
	}

	public void setAllocated(String allocated) {
		this.allocated = allocated;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
}
