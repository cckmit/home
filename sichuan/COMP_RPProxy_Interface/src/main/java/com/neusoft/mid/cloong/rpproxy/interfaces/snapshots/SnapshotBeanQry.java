package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;

public class SnapshotBeanQry  implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6379569077286545829L;
	
	private String snapshot_id;
	
	private String snapshot_name;
	
	private String snapshot_desc;
	
	private String snapshot_state;
	
	private String snapshot_time;

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

	public String getSnapshot_name() {
		return snapshot_name;
	}

	public void setSnapshot_name(String snapshot_name) {
		this.snapshot_name = snapshot_name;
	}

	public String getSnapshot_desc() {
		return snapshot_desc;
	}

	public void setSnapshot_desc(String snapshot_desc) {
		this.snapshot_desc = snapshot_desc;
	}

	public String getSnapshot_state() {
		return snapshot_state;
	}

	public void setSnapshot_state(String snapshot_state) {
		this.snapshot_state = snapshot_state;
	}

	public String getSnapshot_time() {
		return snapshot_time;
	}

	public void setSnapshot_time(String snapshot_time) {
		this.snapshot_time = snapshot_time;
	}
	
	

}
