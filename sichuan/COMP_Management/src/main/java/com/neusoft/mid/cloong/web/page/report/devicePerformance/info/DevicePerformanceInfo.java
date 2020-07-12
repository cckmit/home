package com.neusoft.mid.cloong.web.page.report.devicePerformance.info;

/**
 * 设备性能统计相关信息bean
 * 
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version Revision 1.0 2015年11月6日 下午1:12:23
 */
public class DevicePerformanceInfo {

	/** 设备类型（0：物理机，1：虚拟机，2：防火墙，3：阵列，4：交换机，41：交换机接口，5：路由器，51：路由器接口） */
	private String deviceType;

	/** 设备类型名称 */
    private String deviceTypeName;
    
	/** 时间粒度：1小时，2小时*/
    private String timeType;

	/** 性能采集日期 */
	private String reportDate;
	
	/** 开始日期（查询条件） */
	private String startDate;
	
	/** 结束日期（查询条件） */
	private String endDate;
	
	/**
	 * CPU使用率
	 */
	private String cpuProcessorUtilization;

	/**
	 * 内存使用率
	 */
	private String memUsedPer;
	
	/** 性能指标标志（cpu，mem，disk） */
	private String performanceType;
	
	/**
	 * 设备id
	 */
	private String deviceId;
	
	/**
	 * 设备名称
	 */
	private String deviceName;
	
	/**
     * 设备父节点名称
     */
    private String deviceParentName;
	
	/**
	 * 读吞吐量
	 */
	private String hstDiskReadBytes;
	
	/**
	 * 写吞吐量
	 */
	private String hstDiskWriteBytes;
	
	/**
	 * 吞吐量
	 */
	private String throughput;
	
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
	 * @return the performanceType
	 */
	public String getPerformanceType() {
		return performanceType;
	}

	/**
	 * @param performanceType the performanceType to set
	 */
	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }
    
    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
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
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	/**
	 * @return the pkts
	 */
	public String getPkts() {
		return pkts;
	}

	/**
	 * @param pkts the pkts to set
	 */
	public void setPkts(String pkts) {
		this.pkts = pkts;
	}

	/**
	 * @return the discards
	 */
	public String getDiscards() {
		return discards;
	}

	/**
	 * @param discards the discards to set
	 */
	public void setDiscards(String discards) {
		this.discards = discards;
	}

	/**
	 * @return the octets
	 */
	public String getOctets() {
		return octets;
	}

	/**
	 * @param octets the octets to set
	 */
	public void setOctets(String octets) {
		this.octets = octets;
	}

    public String getDeviceParentName() {
        return deviceParentName;
    }

    public void setDeviceParentName(String deviceParentName) {
        this.deviceParentName = deviceParentName;
    }
	
}
