package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPRecoverySnapshotReq extends RPPBaseReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8825054214497447985L;
	private String vmId;
	private String snapshot_id;
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getSnapshot_id() {
		return snapshot_id;
	}
	public void setSnapshot_id(String snapshot_id) {
		this.snapshot_id = snapshot_id;
	}
	

}
