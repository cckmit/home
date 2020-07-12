package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPRecoverySnapshotResp extends RPPBaseResp implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4848787851283168560L;
	/**
     * 故障描述
     */
    private String faultstring;
	/**
	 * 序列号
	 */
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
}
