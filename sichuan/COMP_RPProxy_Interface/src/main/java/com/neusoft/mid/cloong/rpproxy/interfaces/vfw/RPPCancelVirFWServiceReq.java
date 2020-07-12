package com.neusoft.mid.cloong.rpproxy.interfaces.vfw;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPCancelVirFWServiceReq extends RPPBaseReq implements Serializable {
	private String virfwid;

    public String getVirfwid() {
        return virfwid;
    }

    public void setVirfwid(String virfwid) {
        this.virfwid = virfwid;
    }

	
}
