
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

public class BatchVMInfo {

	private String id;
	
    private String vmId;
    
    private String vmIP;
    
    private String vmName;
    
    private String vmModifyFlag;
    
    private String modifyDesc;
    
    private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmModifyFlag() {
		return vmModifyFlag;
	}

	public void setVmModifyFlag(String vmModifyFlag) {
		this.vmModifyFlag = vmModifyFlag;
	}

	public String getModifyDesc() {
		return modifyDesc;
	}

	public void setModifyDesc(String modifyDesc) {
		this.modifyDesc = modifyDesc;
	}

	public String getVmIP() {
		return vmIP;
	}

	public void setVmIP(String vmIP) {
		this.vmIP = vmIP;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
