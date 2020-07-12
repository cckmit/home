package com.neusoft.mid.cloong.rpproxy.interfaces.filestorage;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPFileStorageCreateReq extends RPPBaseReq implements Serializable {
	
	private String appID;
	
	private String appName;
	
	private String fileStorageName;
	
	private int quotaSize;
	
	private int sharetype;
	
	private String password;

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

	public String getFileStorageName() {
		return fileStorageName;
	}

	public void setFileStorageName(String fileStorageName) {
		this.fileStorageName = fileStorageName;
	}

	public int getQuotaSize() {
		return quotaSize;
	}

	public void setQuotaSize(int quotaSize) {
		this.quotaSize = quotaSize;
	}

	public int getSharetype() {
		return sharetype;
	}

	public void setSharetype(int sharetype) {
		this.sharetype = sharetype;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
