package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 实时性能
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version 创建时间：2015-1-6 下午4:18:30
 */
public class RealTimePerformance extends BaseInfo {
	/**
	 * 设备ID
	 */
	private String deviceId;

	/**
	 * 采集时间
	 */
	private String createTime;

	/**
	 * 使用率
	 */
	private String usedPer;

	/**
	 * CPU使用率
	 */
	private String cpuProcessorUtilization;
	
	/**
	 * 系统百分比
	 */
	private String cpuSysTime;
	
	/**
	 * 用户百分比
	 */
	private String cpuUserTime;
	
	/**
	 * 等待百分比
	 */
	private String cpuIdleTime;
	
	/**
	 * 内存使用率
	 */
	private String memUsedPer;
	
	/**
	 * 系统内存使用率
	 */
	private String sysMemUsedPer;
	
	/**
	 * 用户内存使用率
	 */
	private String userMemUsedPer;

	/**
	 * 可用内存量
	 */
	private String memFree;
	
	/**
	 * 磁盘ID
	 */
	private String diskId;

	/**
	 * 磁盘名称
	 */
	private String diskName;
	
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	
	private String minyear;
	private String minmonth;
	private String minday;
	private String minhour;
	
	private String maxyear;
	private String maxmonth;
	private String maxday;
	private String maxhour;
	
	private String maxRead;
	
	private String maxWrite;

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the usedPer
	 */
	public String getUsedPer() {
		return usedPer;
	}

	/**
	 * @param usedPer
	 *            the usedPer to set
	 */
	public void setUsedPer(String usedPer) {
		this.usedPer = usedPer;
	}

	/**
	 * @return the cpuSysTime
	 */
	public String getCpuSysTime() {
		return cpuSysTime;
	}

	/**
	 * @param cpuSysTime
	 *            the cpuSysTime to set
	 */
	public void setCpuSysTime(String cpuSysTime) {
		this.cpuSysTime = cpuSysTime;
	}

	/**
	 * @return the cpuUserTime
	 */
	public String getCpuUserTime() {
		return cpuUserTime;
	}

	/**
	 * @param cpuUserTime
	 *            the cpuUserTime to set
	 */
	public void setCpuUserTime(String cpuUserTime) {
		this.cpuUserTime = cpuUserTime;
	}

	/**
	 * @return the cpuIdleTime
	 */
	public String getCpuIdleTime() {
		return cpuIdleTime;
	}

	/**
	 * @param cpuIdleTime
	 *            the cpuIdleTime to set
	 */
	public void setCpuIdleTime(String cpuIdleTime) {
		this.cpuIdleTime = cpuIdleTime;
	}

	/**
	 * @return the sysMemUsedPer
	 */
	public String getSysMemUsedPer() {
		return sysMemUsedPer;
	}

	/**
	 * @param sysMemUsedPer
	 *            the sysMemUsedPer to set
	 */
	public void setSysMemUsedPer(String sysMemUsedPer) {
		this.sysMemUsedPer = sysMemUsedPer;
	}

	/**
	 * @return the userMemUsedPer
	 */
	public String getUserMemUsedPer() {
		return userMemUsedPer;
	}

	/**
	 * @param userMemUsedPer
	 *            the userMemUsedPer to set
	 */
	public void setUserMemUsedPer(String userMemUsedPer) {
		this.userMemUsedPer = userMemUsedPer;
	}

	/**
	 * @return the memFree
	 */
	public String getMemFree() {
		return memFree;
	}

	/**
	 * @param memFree
	 *            the memFree to set
	 */
	public void setMemFree(String memFree) {
		this.memFree = memFree;
	}

	/**
	 * @return the cpuProcessorUtilization
	 */
	public String getCpuProcessorUtilization() {
		return cpuProcessorUtilization;
	}

	/**
	 * @param cpuProcessorUtilization
	 *            the cpuProcessorUtilization to set
	 */
	public void setCpuProcessorUtilization(String cpuProcessorUtilization) {
		this.cpuProcessorUtilization = cpuProcessorUtilization;
	}

	/**
	 * @return the memUsedPer
	 */
	public String getMemUsedPer() {
		return memUsedPer;
	}

	/**
	 * @param memUsedPer
	 *            the memUsedPer to set
	 */
	public void setMemUsedPer(String memUsedPer) {
		this.memUsedPer = memUsedPer;
	}

	/**
	 * @return the diskId
	 */
	public String getDiskId() {
		return diskId;
	}

	/**
	 * @param diskId
	 *            the diskId to set
	 */
	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	/**
	 * @return the diskName
	 */
	public String getDiskName() {
		return diskName;
	}

	/**
	 * @param diskName
	 *            the diskName to set
	 */
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public String getMinute() {
		return minute;
	}

	/**
	 * @param minute
	 *            the minute to set
	 */
	public void setMinute(String minute) {
		this.minute = minute;
	}

	/**
	 * @return the second
	 */
	public String getSecond() {
		return second;
	}

	/**
	 * @param second
	 *            the second to set
	 */
	public void setSecond(String second) {
		this.second = second;
	}

	/**
	 * @return the minyear
	 */
	public String getMinyear() {
		return minyear;
	}

	/**
	 * @param minyear the minyear to set
	 */
	public void setMinyear(String minyear) {
		this.minyear = minyear;
	}

	/**
	 * @return the minmonth
	 */
	public String getMinmonth() {
		return minmonth;
	}

	/**
	 * @param minmonth the minmonth to set
	 */
	public void setMinmonth(String minmonth) {
		this.minmonth = minmonth;
	}

	/**
	 * @return the minday
	 */
	public String getMinday() {
		return minday;
	}

	/**
	 * @param minday the minday to set
	 */
	public void setMinday(String minday) {
		this.minday = minday;
	}

	/**
	 * @return the minhour
	 */
	public String getMinhour() {
		return minhour;
	}

	/**
	 * @param minhour the minhour to set
	 */
	public void setMinhour(String minhour) {
		this.minhour = minhour;
	}

	/**
	 * @return the maxyear
	 */
	public String getMaxyear() {
		return maxyear;
	}

	/**
	 * @param maxyear the maxyear to set
	 */
	public void setMaxyear(String maxyear) {
		this.maxyear = maxyear;
	}

	/**
	 * @return the maxmonth
	 */
	public String getMaxmonth() {
		return maxmonth;
	}

	/**
	 * @param maxmonth the maxmonth to set
	 */
	public void setMaxmonth(String maxmonth) {
		this.maxmonth = maxmonth;
	}

	/**
	 * @return the maxday
	 */
	public String getMaxday() {
		return maxday;
	}

	/**
	 * @param maxday the maxday to set
	 */
	public void setMaxday(String maxday) {
		this.maxday = maxday;
	}

	/**
	 * @return the maxhour
	 */
	public String getMaxhour() {
		return maxhour;
	}

	/**
	 * @param maxhour the maxhour to set
	 */
	public void setMaxhour(String maxhour) {
		this.maxhour = maxhour;
	}

	/**
	 * @return the maxRead
	 */
	public String getMaxRead() {
		return maxRead;
	}

	/**
	 * @param maxRead the maxRead to set
	 */
	public void setMaxRead(String maxRead) {
		this.maxRead = maxRead;
	}

	/**
	 * @return the maxWrite
	 */
	public String getMaxWrite() {
		return maxWrite;
	}

	/**
	 * @param maxWrite the maxWrite to set
	 */
	public void setMaxWrite(String maxWrite) {
		this.maxWrite = maxWrite;
	}

	
}
