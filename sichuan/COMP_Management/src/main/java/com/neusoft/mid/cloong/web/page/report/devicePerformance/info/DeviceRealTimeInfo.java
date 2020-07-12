package com.neusoft.mid.cloong.web.page.report.devicePerformance.info;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 实时性能
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version 创建时间：2015-1-6 下午4:18:30
 */
public class DeviceRealTimeInfo extends BaseInfo {
	/**
	 * 设备ID
	 */
	private String deviceId;

	/**
	 * 采集时间
	 */
	private String createTime;

	/**
     * CPU使用率
     */
    private String cpuProcessorUtilization;

    /**
     * 内存使用率
     */
    private String memUsedPer;
    
    /**
	 * 读吞吐量
	 */
	private String hstDiskReadBytes;
	
	/**
	 * 写吞吐量
	 */
	private String hstDiskWriteBytes;
	
	/**
     * 包总数
     */
    private String pkts;
    
    /**
     * 丢包数
     */
    private String discards;
    
    /**
     * 字节总数
     */
    private String octets;
    
    /**
	 * 吞吐量
	 */
	private String throughput;
	
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

	public String getCpuProcessorUtilization() {
        return cpuProcessorUtilization;
    }

    public void setCpuProcessorUtilization(String cpuProcessorUtilization) {
        this.cpuProcessorUtilization = cpuProcessorUtilization;
    }

    public String getMemUsedPer() {
        return memUsedPer;
    }

    public void setMemUsedPer(String memUsedPer) {
        this.memUsedPer = memUsedPer;
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
	 * @return the hstDiskReadBytes
	 */
	public String getHstDiskReadBytes() {
		return hstDiskReadBytes;
	}

	/**
	 * @param hstDiskReadBytes the hstDiskReadBytes to set
	 */
	public void setHstDiskReadBytes(String hstDiskReadBytes) {
		this.hstDiskReadBytes = hstDiskReadBytes;
	}

	/**
	 * @return the hstDiskWriteBytes
	 */
	public String getHstDiskWriteBytes() {
		return hstDiskWriteBytes;
	}

	/**
	 * @param hstDiskWriteBytes the hstDiskWriteBytes to set
	 */
	public void setHstDiskWriteBytes(String hstDiskWriteBytes) {
		this.hstDiskWriteBytes = hstDiskWriteBytes;
	}

    public String getPkts() {
        return pkts;
    }

    public void setPkts(String pkts) {
        this.pkts = pkts;
    }

    public String getDiscards() {
        return discards;
    }

    public void setDiscards(String discards) {
        this.discards = discards;
    }

    public String getOctets() {
        return octets;
    }

    public void setOctets(String octets) {
        this.octets = octets;
    }

	/**
	 * @return the throughput
	 */
	public String getThroughput() {
		return throughput;
	}

	/**
	 * @param throughput the throughput to set
	 */
	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}
	
	

}
