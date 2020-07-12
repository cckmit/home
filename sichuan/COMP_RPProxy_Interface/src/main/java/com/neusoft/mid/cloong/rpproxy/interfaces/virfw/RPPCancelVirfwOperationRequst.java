package com.neusoft.mid.cloong.rpproxy.interfaces.virfw;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPCancelVirfwOperationRequst extends RPPBaseReq implements Serializable {
	private String virfwid;

	public String getVirfwid() {
		return virfwid;
	}

	public void setVirfwid(String virfwid) {
		this.virfwid = virfwid;
	}

}
