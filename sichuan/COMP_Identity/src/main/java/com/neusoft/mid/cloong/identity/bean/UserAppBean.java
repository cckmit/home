
package com.neusoft.mid.cloong.identity.bean;

/**
 * 用户业务绑定表Bean
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-2-25 下午3:43:19
 */
public class UserAppBean extends IdentityBean {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务绑定状态
     */
    private String appBind_status;
    
    /**
     * 审批时间.
     */
    private String auditTime;
    
    /**
     * 审批人.
     */
    private String auditUser;
    
    /**
     * 审批意见.
     */
    private String auditInfo;
    
    
    /**
	 * @return the auditTime
	 */
	public String getAuditTime() {
		return auditTime;
	}

	/**
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * @return the auditUser
	 */
	public String getAuditUser() {
		return auditUser;
	}

	/**
	 * @param auditUser the auditUser to set
	 */
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	/**
	 * @return the auditInfo
	 */
	public String getAuditInfo() {
		return auditInfo;
	}

	/**
	 * @param auditInfo the auditInfo to set
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	/**
     * 业务名称.
     */
    private String appName;
    
    @Override
    public boolean equals(Object obj) {
    	if(this == obj)                                    
    		return true;
    	if(obj == null)         
    		return false;
    	if(obj instanceof UserAppBean){
    		UserAppBean app = (UserAppBean)obj;
    		return this.appId.equals(app.appId);
    	}
    	
    	return false;
    }
    
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @return the appBind_status
	 */
	public String getAppBind_status() {
		return appBind_status;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @param appBind_status the appBind_status to set
	 */
	public void setAppBind_status(String appBind_status) {
		this.appBind_status = appBind_status;
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

}
