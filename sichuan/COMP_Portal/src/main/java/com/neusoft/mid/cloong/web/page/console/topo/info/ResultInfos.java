/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.topo.info;

import java.util.List;

import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMResultInfo;

public class ResultInfos {

	private List<VMResultInfo> vmResultInfos;

	private List<DiskInfo> diskInfos;

	private List<DiskInfo> diskInfos4Up;
	
	private String ebsCount;
	
	private String ebsUpCount;
	
	private String vmCount;
	
	private String appId;
	
	private String appName;
	
	private String ip;
	private String vmname;
	private String cpuidle;
	private String cpuspeed;
	private String diskread;
	private String diskwrite;
	private String mempercent;
	private String bytesin;
	private String bytesout;
	private String ipsubnet;
	private String subnetstart;
	private String subnetend;
	private String userId;
	private VMStatus status;
	private String statuscode;
	private String statustext;
	private String resPoolId;
	private String resPoolPartId;
	private String vmId;
	private String click;
	
	
	

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getStatustext() {
		return statustext;
	}

	public void setStatustext(String statustext) {
		this.statustext = statustext;
	}

	public VMStatus getStatus() {
		return status;
	}

	public void setStatus(VMStatus status) {
		this.status = status;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getResPoolPartId() {
		return resPoolPartId;
	}

	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubnetstart() {
		return subnetstart;
	}

	public void setSubnetstart(String subnetstart) {
		this.subnetstart = subnetstart;
	}

	public String getSubnetend() {
		return subnetend;
	}

	public void setSubnetend(String subnetend) {
		this.subnetend = subnetend;
	}

	public String getIpsubnet() {
		return ipsubnet;
	}

	public void setIpsubnet(String ipsubnet) {
		this.ipsubnet = ipsubnet;
	}

	

	public List<VMResultInfo> getVmResultInfos() {
		return vmResultInfos;
	}

	public void setVmResultInfos(List<VMResultInfo> vmResultInfos) {
		this.vmResultInfos = vmResultInfos;
	}

	public List<DiskInfo> getDiskInfos() {
		return diskInfos;
	}

	public void setDiskInfos(List<DiskInfo> diskInfos) {
		this.diskInfos = diskInfos;
	}

	public List<DiskInfo> getDiskInfos4Up() {
		return diskInfos4Up;
	}

	public void setDiskInfos4Up(List<DiskInfo> diskInfos4Up) {
		this.diskInfos4Up = diskInfos4Up;
	}

	public String getEbsCount() {
		return ebsCount;
	}

	public void setEbsCount(String ebsCount) {
		this.ebsCount = ebsCount;
	}

	public String getEbsUpCount() {
		return ebsUpCount;
	}

	public void setEbsUpCount(String ebsUpCount) {
		this.ebsUpCount = ebsUpCount;
	}

	public String getVmCount() {
		return vmCount;
	}

	public void setVmCount(String vmCount) {
		this.vmCount = vmCount;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVmname() {
		return vmname;
	}

	public void setVmname(String vmname) {
		this.vmname = vmname;
	}

	public String getCpuidle() {
		return cpuidle;
	}

	public void setCpuidle(String cpuidle) {
		this.cpuidle = cpuidle;
	}

	public String getCpuspeed() {
		return cpuspeed;
	}

	public void setCpuspeed(String cpuspeed) {
		this.cpuspeed = cpuspeed;
	}

	public String getDiskread() {
		return diskread;
	}

	public void setDiskread(String diskread) {
		this.diskread = diskread;
	}

	public String getDiskwrite() {
		return diskwrite;
	}

	public void setDiskwrite(String diskwrite) {
		this.diskwrite = diskwrite;
	}

	public String getMempercent() {
		return mempercent;
	}

	public void setMempercent(String mempercent) {
		this.mempercent = mempercent;
	}

	public String getBytesin() {
		return bytesin;
	}

	public void setBytesin(String bytesin) {
		this.bytesin = bytesin;
	}

	public String getBytesout() {
		return bytesout;
	}

	public void setBytesout(String bytesout) {
		this.bytesout = bytesout;
	}
	
	
}
