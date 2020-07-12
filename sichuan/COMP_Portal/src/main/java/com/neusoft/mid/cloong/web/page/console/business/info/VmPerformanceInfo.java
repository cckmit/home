package com.neusoft.mid.cloong.web.page.console.business.info;

public class VmPerformanceInfo{

	/**
	 * CPU使用率
	 */
	private String cpuUtilization;
	
	/**
	 * CPU空闲率
	 */
	private String CpuIdle;
	
	/**
	 * CPU速度
	 */
	private String CpuSpeed;
	
	/**
	 * 内存总数(单位：KB)
	 */
	private String memTotalKb;
		
	/**
	 * 内存使用率
	 */
	private String memUtilization;
	
	/**
	 * 内存总数(单位：G)
	 */
	private String diskTotalG;
	
	/**
	 * 磁盘使用率
	 */
	private String diskUtilization;

	/**
	 * 网络输入
	 */
	private String bytesIn;
	
	/**
	 * 网络输出
	 */
	private String bytesOut;
	
	/**
	 * 开始日期（查询条件）
	 */
	private String startDate;
	
	/**
	 * 结束日期（查询条件）
	 */
	private String endDate;
	
	/**
	 * 虚拟机id
	 */
	private String vmId;
	
	/**
	 * 虚拟机名称
	 */
	private String vmName;
	
	/**
	 * 虚拟机IP
	 */
	private String vmIp;
	
	/**
	 * 企业客户ID
	 */
	private String appId;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 导出日期
	 */
	private String reportDate;

	public String getCpuUtilization() {
		return cpuUtilization;
	}

	public void setCpuUtilization(String cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}

	public String getMemUtilization() {
		return memUtilization;
	}

	public void setMemUtilization(String memUtilization) {
		this.memUtilization = memUtilization;
	}

	public String getDiskUtilization() {
		return diskUtilization;
	}

	public void setDiskUtilization(String diskUtilization) {
		this.diskUtilization = diskUtilization;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getCpuIdle() {
		return CpuIdle;
	}

	public void setCpuIdle(String cpuIdle) {
		CpuIdle = cpuIdle;
	}

	public String getCpuSpeed() {
		return CpuSpeed;
	}

	public void setCpuSpeed(String cpuSpeed) {
		CpuSpeed = cpuSpeed;
	}

	public String getMemTotalKb() {
		return memTotalKb;
	}

	public void setMemTotalKb(String memTotalKb) {
		this.memTotalKb = memTotalKb;
	}

	public String getDiskTotalG() {
		return diskTotalG;
	}

	public void setDiskTotalG(String diskTotalG) {
		this.diskTotalG = diskTotalG;
	}

	public String getBytesIn() {
		return bytesIn;
	}

	public void setBytesIn(String bytesIn) {
		this.bytesIn = bytesIn;
	}

	public String getBytesOut() {
		return bytesOut;
	}

	public void setBytesOut(String bytesOut) {
		this.bytesOut = bytesOut;
	}

}
