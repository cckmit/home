package com.neusoft.mid.cloong.host.vm.core;

import java.util.List;

import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;

public class VMModifyReq extends ReqBodyInfo {

	private String vmId;

	private String vmName;

	private String cpuNum;

	private String ramSize;

	private String discSize;

	private List<NetInfo> netList;

	/**
	 * 操作流水号
	 */
	private String serialNum;

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	public String getRamSize() {
		return ramSize;
	}

	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	public String getDiscSize() {
		return discSize;
	}

	public void setDiscSize(String discSize) {
		this.discSize = discSize;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public List<NetInfo> getNetList() {
		return netList;
	}

	public void setNetList(List<NetInfo> netList) {
		this.netList = netList;
	}

}
