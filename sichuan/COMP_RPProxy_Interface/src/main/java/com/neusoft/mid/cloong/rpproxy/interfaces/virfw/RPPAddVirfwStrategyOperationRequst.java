package com.neusoft.mid.cloong.rpproxy.interfaces.virfw;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPAddVirfwStrategyOperationRequst extends RPPBaseReq implements Serializable {
	private String FWID;
	
	private String Protocol;
	
	private String SourceIP;
	
	private String SourcePort;
	
	private String DestinationIP;
	
	private String DestinationPort;
	
	private String ActType;

	public String getFWID() {
		return FWID;
	}

	public void setFWID(String fWID) {
		FWID = fWID;
	}

	public String getProtocol() {
		return Protocol;
	}

	public void setProtocol(String protocol) {
		Protocol = protocol;
	}

	public String getSourceIP() {
		return SourceIP;
	}

	public void setSourceIP(String sourceIP) {
		SourceIP = sourceIP;
	}

	public String getSourcePort() {
		return SourcePort;
	}

	public void setSourcePort(String sourcePort) {
		SourcePort = sourcePort;
	}

	public String getDestinationIP() {
		return DestinationIP;
	}

	public void setDestinationIP(String destinationIP) {
		DestinationIP = destinationIP;
	}

	public String getDestinationPort() {
		return DestinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		DestinationPort = destinationPort;
	}

	public String getActType() {
		return ActType;
	}

	public void setActType(String actType) {
		ActType = actType;
	}
	
	

}
