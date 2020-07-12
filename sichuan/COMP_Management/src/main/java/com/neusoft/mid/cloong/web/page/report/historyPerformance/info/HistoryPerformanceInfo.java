package com.neusoft.mid.cloong.web.page.report.historyPerformance.info;

/**
 * 业务性能统计相关信息bean
 * 
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version Revision 1.0 2015年2月26日 下午1:12:23
 */
public class HistoryPerformanceInfo {
	/** 业务ID */
	private String appId;
	
	/** 业务名称 */
	private String appName;

	/** 设备类型（0：小型机，1：小型机分区，2：物理机，3：虚拟机） */
	private String deviceType;

	/** 设备类型名称 */
	private String deviceTypeName;

	/** 设备个数 */
	private int deviceNum;

	/** CPU平均使用率 */
	private float cpuAve;

	/** CPU最大使用率 */
	private float cpuMax;

	/** CPU最大使用率对应的设备ID */
	private String cpuMaxId;
	
	/** CPU最小使用率 */
	private float cpuMin;

	/** CPU最小使用率对应的设备ID */
	private String cpuMinId;
	
	/** CPU负载超标的设备数 */
	private int cpuOverNum;
	
	/** CPU负载超标的平均值 */
	private float cpuOverAve;
	
	/** CPU负载未超标的设备数 */
	private int cpuNotOverNum;
	
	/** CPU负载未超标的平均值 */
	private float cpuNotOverAve;
	
	/** 内存平均使用率 */
	private float memAve;

	/** 内存最大使用率 */
	private float memMax;

	/** 内存最大使用率对应的设备ID */
	private String memMaxId;
	
	/** 内存最大使用率对应的设备的可用内存 */
	private float memMaxFree;
	
	/** 内存最小使用率 */
	private float memMin;

	/** 内存最小使用率对应的设备ID */
	private String memMinId;
	
	/** 内存最小使用率对应的设备的可用内存 */
	private float memMinFree;
	
	/** 内存负载超标的设备数 */
	private int memOverNum;
	
	/** 内存负载超标的平均值 */
	private float memOverAve;
	
	/** 内存负载超标的可用内存量平均值 */
	private float memOverFreeAve;
	
	/** 内存负载未超标的设备数 */
	private int memNotOverNum;
	
	/** 内存负载未超标的平均值 */
	private float memNotOverAve;
	
	/** 内存负载未超标的可用内存量平均值 */
	private float memNotOverFreeAve;

	/** 磁盘平均使用率 */
	private float diskAve;

	/** 磁盘最大使用率 */
	private float diskMax;

	/** 磁盘最大使用率对应的设备ID */
	private String diskMaxId;
	
	/** 磁盘最小使用率 */
	private float diskMin;

	/** 磁盘最小使用率对应的设备ID */
	private String diskMinId;
	
	/** 磁盘负载区间1(使用率>70%)设备数 */
	private int diskRange1Num;
	
	/** 磁盘负载区间2(70%>=使用率>30%)设备数 */
	private int diskRange2Num;
	
	/** 磁盘负载区间3(30%>=使用率)设备数 */
	private int diskRange3Num;
	
	/** 性能采集日期 */
	private String reportDate;
	
	/** 开始日期（查询条件） */
	private String startDate;
	
	/** 结束日期（查询条件） */
	private String endDate;
	
	/** 性能指标标志（cpu，mem，disk） */
	private String performanceType;
	
	/** 统计类别标志（性能统计：performance，性能超标设备统计：performanceOver） */
	private String staFlag;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(int deviceNum) {
		this.deviceNum = deviceNum;
	}

	public float getCpuAve() {
		return cpuAve;
	}

	public void setCpuAve(float cpuAve) {
		this.cpuAve = cpuAve;
	}

	public float getCpuMax() {
		return cpuMax;
	}

	public void setCpuMax(float cpuMax) {
		this.cpuMax = cpuMax;
	}

	public String getCpuMaxId() {
		return cpuMaxId;
	}

	public void setCpuMaxId(String cpuMaxId) {
		this.cpuMaxId = cpuMaxId;
	}

	public float getCpuMin() {
		return cpuMin;
	}

	public void setCpuMin(float cpuMin) {
		this.cpuMin = cpuMin;
	}

	public String getCpuMinId() {
		return cpuMinId;
	}

	public void setCpuMinId(String cpuMinId) {
		this.cpuMinId = cpuMinId;
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

	public float getMemAve() {
		return memAve;
	}

	public void setMemAve(float memAve) {
		this.memAve = memAve;
	}

	public float getMemMax() {
		return memMax;
	}

	public void setMemMax(float memMax) {
		this.memMax = memMax;
	}

	public String getMemMaxId() {
		return memMaxId;
	}

	public void setMemMaxId(String memMaxId) {
		this.memMaxId = memMaxId;
	}

	public float getMemMaxFree() {
		return memMaxFree;
	}

	public void setMemMaxFree(float memMaxFree) {
		this.memMaxFree = memMaxFree;
	}

	public float getMemMin() {
		return memMin;
	}

	public void setMemMin(float memMin) {
		this.memMin = memMin;
	}

	public String getMemMinId() {
		return memMinId;
	}

	public void setMemMinId(String memMinId) {
		this.memMinId = memMinId;
	}

	public float getMemMinFree() {
		return memMinFree;
	}

	public void setMemMinFree(float memMinFree) {
		this.memMinFree = memMinFree;
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

	public float getDiskAve() {
		return diskAve;
	}

	public void setDiskAve(float diskAve) {
		this.diskAve = diskAve;
	}

	public float getDiskMax() {
		return diskMax;
	}

	public void setDiskMax(float diskMax) {
		this.diskMax = diskMax;
	}

	public String getDiskMaxId() {
		return diskMaxId;
	}

	public void setDiskMaxId(String diskMaxId) {
		this.diskMaxId = diskMaxId;
	}

	public float getDiskMin() {
		return diskMin;
	}

	public void setDiskMin(float diskMin) {
		this.diskMin = diskMin;
	}

	public String getDiskMinId() {
		return diskMinId;
	}

	public void setDiskMinId(String diskMinId) {
		this.diskMinId = diskMinId;
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

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPerformanceType() {
		return performanceType;
	}

	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
	}

	public String getStaFlag() {
		return staFlag;
	}

	public void setStaFlag(String staFlag) {
		this.staFlag = staFlag;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
}
