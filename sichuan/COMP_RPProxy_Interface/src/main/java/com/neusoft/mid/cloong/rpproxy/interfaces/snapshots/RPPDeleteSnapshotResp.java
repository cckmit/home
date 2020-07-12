package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPDeleteSnapshotResp  extends RPPBaseResp implements Serializable{
	/**
     * 故障描述
     */
    private String faultstring;
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3553464135357134165L;
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	
}
