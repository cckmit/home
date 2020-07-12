package com.neusoft.mid.cloong.web.page.resourceManagement.info;


/**
 * 各项指标超标的设备相关信息bean
 * 
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li</a>
 * @version Revision 1.0 2015年1月5日 下午1:12:23
 */
public class DeviceExceededNum
{
	/**
	 * 业务id
	 */
	private String appId;

	/**
	 * 业务名称
	 */
	private String appName;

	/**
	 * 设备类型:0：小型机,1：小型机分区,2：物理机,3：虚拟机,4：虚拟硬盘(无性能统计)
	 */
	private String deviceType;

	/**
	 * 性能统计类型
	 */
	private String performanceType;

	/**
	 * 该业务下的设备总数
	 */
	private int deviceNum;

	/**
	 * CPU负载超标的设备数
	 */
	private int cpuOverNum;
	
	/**
	 * CPU负载超标的平均值
	 */
	private float cpuOverAve;
	
	/**
	 * CPU负载未超标的设备数
	 */
	private int cpuNotOverNum;
	
	/**
	 * CPU负载未超标的平均值
	 */
	private float cpuNotOverAve;

	/**
	 * CPU负载超标百分比
	 */
	private float cpuOverPer;
	
	/**
	 * CPU负载未超标百分比
	 */
	private float cpuNotOverPer;

	/**
	 * 内存负载超标的设备数
	 */
	private int memOverNum;
	
	/**
	 * 内存负载超标的平均值
	 */
	private float memOverAve;
	
	/**
	 * 内存负载超标的可用内存量平均值
	 */
	private float memOverFreeAve;
	
	/**
	 * 内存负载未超标的设备数
	 */
	private int memNotOverNum;
	
	/**
	 * 内存负载未超标的平均值
	 */
	private float memNotOverAve;
	
	/**
	 * 内存负载未超标的可用内存量平均值
	 */
	private float memNotOverFreeAve;

	/**
	 * 内存负载超标百分比
	 */
	private float memOverPer;
	
	/**
	 * 内存负载未超标百分比
	 */
	private float memNotOverPer;

	/**
	 * 磁盘负载区间1(使用率>70%)设备数
	 */
	private int diskRange1Num;
	
	/**
	 * 磁盘负载区间2(70%>=使用率>30%)设备数
	 */
	private int diskRange2Num;
	
	/**
	 * 磁盘负载区间3(30%>=使用率)设备数
	 */
	private int diskRange3Num;

	/**
	 * 磁盘负载区间1百分比
	 */
	private float diskRange1Per;
	
	/**
	 * 磁盘负载区间2百分比
	 */
	private float diskRange2Per;
	
	/**
	 * 磁盘负载区间3百分比
	 */
	private float diskRange3Per;
	
	/**
	 * 未上报性能的设备数
	 */
	private int otherNum;
	
	/**
	 * 未上报性能的设备数百分比
	 */
	private float otherPer;

	/**
	 * 统计时间 格式 20150106
	 */
	private String reportDate;

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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getPerformanceType() {
		return performanceType;
	}

	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
	}

	public int getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(int deviceNum) {
		this.deviceNum = deviceNum;
	}

	public int getCpuOverNum() {
		return cpuOverNum;
	}

	public void setCpuOverNum(int cpuOverNum) {
		this.cpuOverNum = cpuOverNum;
	}

	public float getCpuOverAve() {
		return cpuOverAve;
	}

	public void setCpuOverAve(float cpuOverAve) {
		this.cpuOverAve = cpuOverAve;
	}

	public int getCpuNotOverNum() {
		return cpuNotOverNum;
	}

	public void setCpuNotOverNum(int cpuNotOverNum) {
		this.cpuNotOverNum = cpuNotOverNum;
	}

	public float getCpuNotOverAve() {
		return cpuNotOverAve;
	}

	public void setCpuNotOverAve(float cpuNotOverAve) {
		this.cpuNotOverAve = cpuNotOverAve;
	}

	public float getCpuOverPer() {
		return cpuOverPer;
	}

	public void setCpuOverPer(float cpuOverPer) {
		this.cpuOverPer = cpuOverPer;
	}

	public float getCpuNotOverPer() {
		return cpuNotOverPer;
	}

	public void setCpuNotOverPer(float cpuNotOverPer) {
		this.cpuNotOverPer = cpuNotOverPer;
	}

	public int getMemOverNum() {
		return memOverNum;
	}

	public void setMemOverNum(int memOverNum) {
		this.memOverNum = memOverNum;
	}

	public float getMemOverAve() {
		return memOverAve;
	}

	public void setMemOverAve(float memOverAve) {
		this.memOverAve = memOverAve;
	}

	public float getMemOverFreeAve() {
		return memOverFreeAve;
	}

	public void setMemOverFreeAve(float memOverFreeAve) {
		this.memOverFreeAve = memOverFreeAve;
	}

	public int getMemNotOverNum() {
		return memNotOverNum;
	}

	public void setMemNotOverNum(int memNotOverNum) {
		this.memNotOverNum = memNotOverNum;
	}

	public float getMemNotOverAve() {
		return memNotOverAve;
	}

	public void setMemNotOverAve(float memNotOverAve) {
		this.memNotOverAve = memNotOverAve;
	}

	public float getMemNotOverFreeAve() {
		return memNotOverFreeAve;
	}

	public void setMemNotOverFreeAve(float memNotOverFreeAve) {
		this.memNotOverFreeAve = memNotOverFreeAve;
	}

	public float getMemOverPer() {
		return memOverPer;
	}

	public void setMemOverPer(float memOverPer) {
		this.memOverPer = memOverPer;
	}

	public float getMemNotOverPer() {
		return memNotOverPer;
	}

	public void setMemNotOverPer(float memNotOverPer) {
		this.memNotOverPer = memNotOverPer;
	}

	public int getDiskRange1Num() {
		return diskRange1Num;
	}

	public void setDiskRange1Num(int diskRange1Num) {
		this.diskRange1Num = diskRange1Num;
	}

	public int getDiskRange2Num() {
		return diskRange2Num;
	}

	public void setDiskRange2Num(int diskRange2Num) {
		this.diskRange2Num = diskRange2Num;
	}

	public int getDiskRange3Num() {
		return diskRange3Num;
	}

	public void setDiskRange3Num(int diskRange3Num) {
		this.diskRange3Num = diskRange3Num;
	}

	public float getDiskRange1Per() {
		return diskRange1Per;
	}

	public void setDiskRange1Per(float diskRange1Per) {
		this.diskRange1Per = diskRange1Per;
	}

	public float getDiskRange2Per() {
		return diskRange2Per;
	}

	public void setDiskRange2Per(float diskRange2Per) {
		this.diskRange2Per = diskRange2Per;
	}

	public float getDiskRange3Per() {
		return diskRange3Per;
	}

	public void setDiskRange3Per(float diskRange3Per) {
		this.diskRange3Per = diskRange3Per;
	}

	public int getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(int otherNum) {
		this.otherNum = otherNum;
	}

	public float getOtherPer() {
		return otherPer;
	}

	public void setOtherPer(float otherPer) {
		this.otherPer = otherPer;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

}
