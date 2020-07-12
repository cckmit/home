/**
 * 
 */
package com.neusoft.mid.cloong.filestorage.bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */

public class FileStorageBean implements Serializable{
	private String fsName;

	private String appName;

	private String appId;
	
	private String fsQuotasize;
	
	private String fsShareType;
	
	private String fsAdminUser;
	
	private String fsAdminPassword;
	
	private String resPoolId;
	
	private String resPoolPartId;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFsName() {
		return fsName;
	}

	public void setFsName(String fsName) {
		this.fsName = fsName;
	}

	public String getFsQuotasize() {
		return fsQuotasize;
	}

	public void setFsQuotasize(String fsQuotasize) {
		this.fsQuotasize = fsQuotasize;
	}

	public String getFsShareType() {
		return fsShareType;
	}

	public void setFsShareType(String fsShareType) {
		this.fsShareType = fsShareType;
	}

	public String getFsAdminUser() {
		return fsAdminUser;
	}

	public void setFsAdminUser(String fsAdminUser) {
		this.fsAdminUser = fsAdminUser;
	}

	public String getFsAdminPassword() {
		return fsAdminPassword;
	}

	public void setFsAdminPassword(String fsAdminPassword) {
		this.fsAdminPassword = fsAdminPassword;
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
