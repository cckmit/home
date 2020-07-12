package com.neusoft.mid.cloong.virfw.impl;



import com.neusoft.mid.cloong.info.ReqBodyInfo;

public class VirFwCreateReq extends ReqBodyInfo {

	private String appID;
	
	private String appName;
		
	private String fwName;

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFwName() {
		return fwName;
	}

	public void setFwName(String fwName) {
		this.fwName = fwName;
	}
	
	
}
