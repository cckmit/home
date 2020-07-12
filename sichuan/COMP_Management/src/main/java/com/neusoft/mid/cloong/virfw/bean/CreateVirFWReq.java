/**
 * 
 */
package com.neusoft.mid.cloong.virfw.bean;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * @author Administrator
 *
 */
public class CreateVirFWReq extends RPPBaseReq implements Serializable {
	
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
