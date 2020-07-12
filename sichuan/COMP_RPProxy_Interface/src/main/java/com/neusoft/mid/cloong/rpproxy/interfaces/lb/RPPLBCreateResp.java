package com.neusoft.mid.cloong.rpproxy.interfaces.lb;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPLBCreateResp extends RPPBaseResp implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = -4070983490687717236L;
	/**
     * 故障描述
     */
    private String faultstring;
    
    private String lbId;
    
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	public String getLbId() {
		return lbId;
	}
	public void setLbId(String lbId) {
		this.lbId = lbId;
	}
    
}
