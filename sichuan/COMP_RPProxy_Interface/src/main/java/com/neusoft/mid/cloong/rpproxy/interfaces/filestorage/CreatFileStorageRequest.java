/**
 * 
 */
package com.neusoft.mid.cloong.rpproxy.interfaces.filestorage;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * @author Administrator
 *
 */
public class CreatFileStorageRequest extends RPPBaseReq implements Serializable{
	private FileStorageParamArray fileStorageParamArray;
	private String fileStorageName;
	private String fileAdminUser;
	private String password;
	private String appID;
	private String appName;

	public FileStorageParamArray getFileStorageParamArray() {
		return fileStorageParamArray;
	}

	public void setFileStorageParamArray(FileStorageParamArray fileStorageParamArray) {
		this.fileStorageParamArray = fileStorageParamArray;
	}

	public String getFileStorageName() {
		return fileStorageName;
	}

	public void setFileStorageName(String fileStorageName) {
		this.fileStorageName = fileStorageName;
	}

	public String getFileAdminUser() {
		return fileAdminUser;
	}

	public void setFileAdminUser(String fileAdminUser) {
		this.fileAdminUser = fileAdminUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

}
