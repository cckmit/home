package com.neusoft.mid.cloong.web.page.snapshots.info;

import java.io.Serializable;

public class SnapshotBean implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3841069105745787354L;
	/**
	 * 虚拟机ID
	 */
	private String vmId;
	/**
	 * 快照ID
	 */
	private String snapshot_id;
	/**
	 * name
	 */
	private String snapshot_name;
	/**
	 * 快照描述
	 */
	private String snapshot_desc;
	/**
	 * 是否生成虚拟机快照
	 */
	private String generate_memory;
	/**
	 * 是否是虚拟机文件系统处于静默状态quiesce目前只支持0，不支持1
	 */
	private String quiesce;
	
	private String snapshot_force;
	
	private String create_user;
	
	private String snapshot_time;
	
	private String snapshot_state;
	
	
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getSnapshot_force() {
		return snapshot_force;
	}
	public void setSnapshot_force(String snapshot_force) {
		this.snapshot_force = snapshot_force;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getSnapshot_time() {
		return snapshot_time;
	}
	public void setSnapshot_time(String snapshot_time) {
		this.snapshot_time = snapshot_time;
	}
	public String getSnapshot_state() {
		return snapshot_state;
	}
	public void setSnapshot_state(String snapshot_state) {
		this.snapshot_state = snapshot_state;
	}
	public String getSnapshot_id() {
		return snapshot_id;
	}
	public void setSnapshot_id(String snapshot_id) {
		this.snapshot_id = snapshot_id;
	}
	public String getSnapshot_desc() {
		return snapshot_desc;
	}
	public void setSnapshot_desc(String snapshot_desc) {
		this.snapshot_desc = snapshot_desc;
	}
	public String getGenerate_memory() {
		return generate_memory;
	}
	public void setGenerate_memory(String generate_memory) {
		this.generate_memory = generate_memory;
	}
	public String getQuiesce() {
		return quiesce;
	}
	public void setQuiesce(String quiesce) {
		this.quiesce = quiesce;
	}
	public SnapshotBean() {
		super();
	}
	public String getSnapshot_name() {
		return snapshot_name;
	}
	public void setSnapshot_name(String snapshot_name) {
		this.snapshot_name = snapshot_name;
	}
	
}
