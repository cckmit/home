package com.neusoft.mid.cloong.rpproxy.interfaces.virfw;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPDelVirfwStrategyOperationRequst extends RPPBaseReq implements Serializable {
	private String FWStrategyID;

	public String getFWStrategyID() {
		return FWStrategyID;
	}

	public void setFWStrategyID(String fWStrategyID) {
		FWStrategyID = fWStrategyID;
	}

}
