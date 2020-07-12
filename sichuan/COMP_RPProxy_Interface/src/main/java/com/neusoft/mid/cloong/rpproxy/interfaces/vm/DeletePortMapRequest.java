package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class DeletePortMapRequest extends RPPBaseReq implements Serializable {
	private String portMapId;

	public String getPortMapId() {
		return portMapId;
	}

	public void setPortMapId(String portMapId) {
		this.portMapId = portMapId;
	}
}
