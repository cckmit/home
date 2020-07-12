package com.neusoft.mid.cloong.rpproxy.interfaces.filestorage;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPFileStorageDeleteReq extends RPPBaseReq implements Serializable {
	private String FileStorageID;

	public String getFileStorageID() {
		return FileStorageID;
	}

	public void setFileStorageID(String fileStorageID) {
		FileStorageID = fileStorageID;
	}
	
	
}
