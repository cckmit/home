package com.neusoft.mid.cloong.web.page.user.order;

/**
 * vlan实例信息
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-3-9 下午04:17:50
 */
public class VlanInstanceInfo {
	/**
     * 实例id
     */
    private String caseId;
    /**
     * id
     */
    private String vlanId;
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
	 * @return the vlanId
	 */
	public String getVlanId() {
		return vlanId;
	}

	/**
	 * @param vlanId the vlanId to set
	 */
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
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
}
