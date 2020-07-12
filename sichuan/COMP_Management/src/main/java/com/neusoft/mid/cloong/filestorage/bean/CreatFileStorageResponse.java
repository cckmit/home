/**
 * 
 */
package com.neusoft.mid.cloong.filestorage.bean;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * @author Administrator
 *
 */
public class CreatFileStorageResponse extends RPPBaseResp implements Serializable{
	private String FileStorageID;
	
	private String FSUrl;
	
	private String FaultString;

	public String getFileStorageID() {
		return FileStorageID;
	}

	public void setFileStorageID(String fileStorageID) {
		FileStorageID = fileStorageID;
	}

	public String getFSUrl() {
		return FSUrl;
	}

	public void setFSUrl(String fSUrl) {
		FSUrl = fSUrl;
	}

	public String getFaultString() {
		return FaultString;
	}

	public void setFaultString(String faultString) {
		FaultString = faultString;
	}

}
