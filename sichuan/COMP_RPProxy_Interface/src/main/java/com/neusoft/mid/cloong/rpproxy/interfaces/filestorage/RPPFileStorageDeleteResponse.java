package com.neusoft.mid.cloong.rpproxy.interfaces.filestorage;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPFileStorageDeleteResponse extends RPPBaseResp implements Serializable {
	
	private String FaultString;

	public String getFaultString() {
		return FaultString;
	}

	public void setFaultString(String faultString) {
		FaultString = faultString;
	}
	
	
}
