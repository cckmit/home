package com.neusoft.mid.cloong.rpproxy.interfaces.vfw;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPDeleteVirFWStrategyReq extends RPPBaseReq implements Serializable {
	private String fwStrategyID;

    public String getFwStrategyID() {
        return fwStrategyID;
    }

    public void setFwStrategyID(String fwStrategyID) {
        this.fwStrategyID = fwStrategyID;
    }
}
