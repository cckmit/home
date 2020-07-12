package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPDeleteSnapshotReq extends RPPBaseReq implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5184784086521667565L;
	private String vmId;
	private String snapshot_id;
    private String  resourcePoolId;
	private String resourcePoolPartId; 
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
	public String getResourcePoolId() {
		return resourcePoolId;
	}
	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}
	public String getResourcePoolPartId() {
		return resourcePoolPartId;
	}
	public void setResourcePoolPartId(String resourcePoolPartId) {
		this.resourcePoolPartId = resourcePoolPartId;
	}
	
}
