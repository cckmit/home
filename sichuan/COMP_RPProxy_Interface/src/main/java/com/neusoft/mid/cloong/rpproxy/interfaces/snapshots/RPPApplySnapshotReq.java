package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 请求类
 */
public class RPPApplySnapshotReq extends RPPBaseReq implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3904247986296225947L;
	/**
	 * 虚拟机ID
	 */
	private String vmId;
	/**
	 * 快照标识
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

	private String  resourcePoolId;
	private String resourcePoolPartId;
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
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
