package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPQuerySnapshotReq  extends RPPBaseReq implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4569050679178570324L;
	private String vmId;
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	
}
