package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

public class QuerySnapshotStateReq {
	private String snapshot_id;

	private String vmId;
	
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
