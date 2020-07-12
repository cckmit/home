/**
 * 
 */
package com.neusoft.mid.cloong.rpproxy.interfaces.virfw;

/**
 * @author Administrator
 *
 */
public class CreateVirFwBean {
	
	private String fwName;
	
	private String appId;
	
	private String appName;
	
	private String resPoolId;
	
	private String resPoolPartId;

	public String getFwName() {
		return fwName;
	}

	public void setFwName(String fwName) {
		this.fwName = fwName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getResPoolPartId() {
		return resPoolPartId;
	}

	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

}
