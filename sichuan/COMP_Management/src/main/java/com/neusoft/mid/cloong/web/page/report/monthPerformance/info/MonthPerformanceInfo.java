package com.neusoft.mid.cloong.web.page.report.monthPerformance.info;

/**
 * 月份性能统计相关信息bean
 * 
 * @author <a href="mailto:x_wang@neusoft.com">wangxin</a>
 * @version Revision 1.0 2016年4月7日 下午5:12:23
 */
public class MonthPerformanceInfo {
	
	private String deviceId;
	
	private String deviceName;
	
	// 0：物理机，1：虚拟机
	private String deviceType;
	
	private String deviceTypeName;

	private String appName;
	
	private String ip;
	
	private String cpuNum;
	
	private String memorySize;
	
	private String serverType;
	
	private String os;
	
	private String appContacts;
	
	private String cpuProcessorUtilization;
    
    private String maxCpuProcessorUtilization;
    
    private String memUsedPer;
    
    private String maxMemUsedPer;
    
    private String swapMemUsedPer;
    
    private String maxSwapMemUsedPer;
    
    private String diskIOspeed;
    
    private String maxDiskIOspeed;
	
	/**
	 * 三个时间段的cpu数据
	 */
	private String cpuProcessorUtilization0;
	
	private String maxCpuProcessorUtilization0;
	
	private String cpuProcessorUtilization1;
    
    private String maxCpuProcessorUtilization1;
    
    private String cpuProcessorUtilization2;
    
    private String maxCpuProcessorUtilization2;
    
    /**
     *三个时间段的内存 
     */
	private String memUsedPer0;
	
	private String maxMemUsedPer0;
	
	private String memUsedPer1;
    
    private String maxMemUsedPer1;
    
    private String memUsedPer2;
    
    private String maxMemUsedPer2;
	
    /**
     *三个时间段的pagespace 
     */
	private String swapMemUsedPer0;
	
	private String maxSwapMemUsedPer0;
	 
	private String swapMemUsedPer1;
	    
	private String maxSwapMemUsedPer1;
	    
	private String swapMemUsedPer2;
	    
    private String maxSwapMemUsedPer2;
    
	
	private String resPoolId;
	
	private String resPoolName;
	
	private String poolPartId;
	
	private String poolPartName;
	
	private String startDate;
	
	private String endDate;
	
	private String dateType;
	
	private String orderType;
	
	private String performanceType;
	
	/**
	 * 三个时间段的磁盘io
	 */
	private String diskIOspeed0;
	
	private String maxDiskIOspeed0;

	private String diskIOspeed1;
    
    private String maxDiskIOspeed1;

    private String diskIOspeed2;
    
    private String maxDiskIOspeed2;
	
	private String onlineTime;
	
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the cpuNum
	 */
	public String getCpuNum() {
		return cpuNum;
	}

	/**
	 * @param cpuNum the cpuNum to set
	 */
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	/**
	 * @return the memorySize
	 */
	public String getMemorySize() {
		return memorySize;
	}

	/**
	 * @param memorySize the memorySize to set
	 */
	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the appContacts
	 */
	public String getAppContacts() {
		return appContacts;
	}

	/**
	 * @param appContacts the appContacts to set
	 */
	public void setAppContacts(String appContacts) {
		this.appContacts = appContacts;
	}

	/**
	 * @return the resPoolId
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * @param resPoolId the resPoolId to set
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * @return the poolPartId
	 */
	public String getPoolPartId() {
		return poolPartId;
	}

	/**
	 * @param poolPartId the poolPartId to set
	 */
	public void setPoolPartId(String poolPartId) {
		this.poolPartId = poolPartId;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the dateType
	 */
	public String getDateType() {
		return dateType;
	}

	/**
	 * @param dateType the dateType to set
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	 * @return the serverType
	 */
	public String getServerType() {
		return serverType;
	}

	/**
	 * @param serverType the serverType to set
	 */
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	/**
	 * @return the resPoolName
	 */
	public String getResPoolName() {
		return resPoolName;
	}

	/**
	 * @param resPoolName the resPoolName to set
	 */
	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
	}

	/**
	 * @return the poolPartName
	 */
	public String getPoolPartName() {
		return poolPartName;
	}

	/**
	 * @param poolPartName the poolPartName to set
	 */
	public void setPoolPartName(String poolPartName) {
		this.poolPartName = poolPartName;
	}

	public String getPerformanceType() {
		return performanceType;
	}

	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
	}

    /**
     * @return the onlineTime
     */
    public String getOnlineTime() {
        return onlineTime;
    }

    /**
     * @param onlineTime the onlineTime to set
     */
    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    /**
     * @return the cpuProcessorUtilization0
     */
    public String getCpuProcessorUtilization0() {
        return cpuProcessorUtilization0;
    }

    /**
     * @param cpuProcessorUtilization0 the cpuProcessorUtilization0 to set
     */
    public void setCpuProcessorUtilization0(String cpuProcessorUtilization0) {
        this.cpuProcessorUtilization0 = cpuProcessorUtilization0;
    }

    /**
     * @return the maxCpuProcessorUtilization0
     */
    public String getMaxCpuProcessorUtilization0() {
        return maxCpuProcessorUtilization0;
    }

    /**
     * @param maxCpuProcessorUtilization0 the maxCpuProcessorUtilization0 to set
     */
    public void setMaxCpuProcessorUtilization0(String maxCpuProcessorUtilization0) {
        this.maxCpuProcessorUtilization0 = maxCpuProcessorUtilization0;
    }

    /**
     * @return the cpuProcessorUtilization1
     */
    public String getCpuProcessorUtilization1() {
        return cpuProcessorUtilization1;
    }

    /**
     * @param cpuProcessorUtilization1 the cpuProcessorUtilization1 to set
     */
    public void setCpuProcessorUtilization1(String cpuProcessorUtilization1) {
        this.cpuProcessorUtilization1 = cpuProcessorUtilization1;
    }

    /**
     * @return the maxCpuProcessorUtilization1
     */
    public String getMaxCpuProcessorUtilization1() {
        return maxCpuProcessorUtilization1;
    }

    /**
     * @param maxCpuProcessorUtilization1 the maxCpuProcessorUtilization1 to set
     */
    public void setMaxCpuProcessorUtilization1(String maxCpuProcessorUtilization1) {
        this.maxCpuProcessorUtilization1 = maxCpuProcessorUtilization1;
    }

    /**
     * @return the cpuProcessorUtilization2
     */
    public String getCpuProcessorUtilization2() {
        return cpuProcessorUtilization2;
    }

    /**
     * @param cpuProcessorUtilization2 the cpuProcessorUtilization2 to set
     */
    public void setCpuProcessorUtilization2(String cpuProcessorUtilization2) {
        this.cpuProcessorUtilization2 = cpuProcessorUtilization2;
    }

    /**
     * @return the maxCpuProcessorUtilization2
     */
    public String getMaxCpuProcessorUtilization2() {
        return maxCpuProcessorUtilization2;
    }

    /**
     * @param maxCpuProcessorUtilization2 the maxCpuProcessorUtilization2 to set
     */
    public void setMaxCpuProcessorUtilization2(String maxCpuProcessorUtilization2) {
        this.maxCpuProcessorUtilization2 = maxCpuProcessorUtilization2;
    }

    /**
     * @return the memUsedPer0
     */
    public String getMemUsedPer0() {
        return memUsedPer0;
    }

    /**
     * @param memUsedPer0 the memUsedPer0 to set
     */
    public void setMemUsedPer0(String memUsedPer0) {
        this.memUsedPer0 = memUsedPer0;
    }

    /**
     * @return the maxMemUsedPer0
     */
    public String getMaxMemUsedPer0() {
        return maxMemUsedPer0;
    }

    /**
     * @param maxMemUsedPer0 the maxMemUsedPer0 to set
     */
    public void setMaxMemUsedPer0(String maxMemUsedPer0) {
        this.maxMemUsedPer0 = maxMemUsedPer0;
    }

    /**
     * @return the memUsedPer1
     */
    public String getMemUsedPer1() {
        return memUsedPer1;
    }

    /**
     * @param memUsedPer1 the memUsedPer1 to set
     */
    public void setMemUsedPer1(String memUsedPer1) {
        this.memUsedPer1 = memUsedPer1;
    }

    /**
     * @return the maxMemUsedPer1
     */
    public String getMaxMemUsedPer1() {
        return maxMemUsedPer1;
    }

    /**
     * @param maxMemUsedPer1 the maxMemUsedPer1 to set
     */
    public void setMaxMemUsedPer1(String maxMemUsedPer1) {
        this.maxMemUsedPer1 = maxMemUsedPer1;
    }

    /**
     * @return the memUsedPer2
     */
    public String getMemUsedPer2() {
        return memUsedPer2;
    }

    /**
     * @param memUsedPer2 the memUsedPer2 to set
     */
    public void setMemUsedPer2(String memUsedPer2) {
        this.memUsedPer2 = memUsedPer2;
    }

    /**
     * @return the maxMemUsedPer2
     */
    public String getMaxMemUsedPer2() {
        return maxMemUsedPer2;
    }

    /**
     * @param maxMemUsedPer2 the maxMemUsedPer2 to set
     */
    public void setMaxMemUsedPer2(String maxMemUsedPer2) {
        this.maxMemUsedPer2 = maxMemUsedPer2;
    }

    /**
     * @return the swapMemUsedPer0
     */
    public String getSwapMemUsedPer0() {
        return swapMemUsedPer0;
    }

    /**
     * @param swapMemUsedPer0 the swapMemUsedPer0 to set
     */
    public void setSwapMemUsedPer0(String swapMemUsedPer0) {
        this.swapMemUsedPer0 = swapMemUsedPer0;
    }

    /**
     * @return the maxSwapMemUsedPer0
     */
    public String getMaxSwapMemUsedPer0() {
        return maxSwapMemUsedPer0;
    }

    /**
     * @param maxSwapMemUsedPer0 the maxSwapMemUsedPer0 to set
     */
    public void setMaxSwapMemUsedPer0(String maxSwapMemUsedPer0) {
        this.maxSwapMemUsedPer0 = maxSwapMemUsedPer0;
    }

    /**
     * @return the swapMemUsedPer1
     */
    public String getSwapMemUsedPer1() {
        return swapMemUsedPer1;
    }

    /**
     * @param swapMemUsedPer1 the swapMemUsedPer1 to set
     */
    public void setSwapMemUsedPer1(String swapMemUsedPer1) {
        this.swapMemUsedPer1 = swapMemUsedPer1;
    }

    /**
     * @return the maxSwapMemUsedPer1
     */
    public String getMaxSwapMemUsedPer1() {
        return maxSwapMemUsedPer1;
    }

    /**
     * @param maxSwapMemUsedPer1 the maxSwapMemUsedPer1 to set
     */
    public void setMaxSwapMemUsedPer1(String maxSwapMemUsedPer1) {
        this.maxSwapMemUsedPer1 = maxSwapMemUsedPer1;
    }

    /**
     * @return the swapMemUsedPer2
     */
    public String getSwapMemUsedPer2() {
        return swapMemUsedPer2;
    }

    /**
     * @param swapMemUsedPer2 the swapMemUsedPer2 to set
     */
    public void setSwapMemUsedPer2(String swapMemUsedPer2) {
        this.swapMemUsedPer2 = swapMemUsedPer2;
    }

    /**
     * @return the maxSwapMemUsedPer2
     */
    public String getMaxSwapMemUsedPer2() {
        return maxSwapMemUsedPer2;
    }

    /**
     * @param maxSwapMemUsedPer2 the maxSwapMemUsedPer2 to set
     */
    public void setMaxSwapMemUsedPer2(String maxSwapMemUsedPer2) {
        this.maxSwapMemUsedPer2 = maxSwapMemUsedPer2;
    }

    /**
     * @return the diskIOspeed0
     */
    public String getDiskIOspeed0() {
        return diskIOspeed0;
    }

    /**
     * @param diskIOspeed0 the diskIOspeed0 to set
     */
    public void setDiskIOspeed0(String diskIOspeed0) {
        this.diskIOspeed0 = diskIOspeed0;
    }

    /**
     * @return the maxDiskIOspeed0
     */
    public String getMaxDiskIOspeed0() {
        return maxDiskIOspeed0;
    }

    /**
     * @param maxDiskIOspeed0 the maxDiskIOspeed0 to set
     */
    public void setMaxDiskIOspeed0(String maxDiskIOspeed0) {
        this.maxDiskIOspeed0 = maxDiskIOspeed0;
    }

    /**
     * @return the diskIOspeed1
     */
    public String getDiskIOspeed1() {
        return diskIOspeed1;
    }

    /**
     * @param diskIOspeed1 the diskIOspeed1 to set
     */
    public void setDiskIOspeed1(String diskIOspeed1) {
        this.diskIOspeed1 = diskIOspeed1;
    }

    /**
     * @return the maxDiskIOspeed1
     */
    public String getMaxDiskIOspeed1() {
        return maxDiskIOspeed1;
    }

    /**
     * @param maxDiskIOspeed1 the maxDiskIOspeed1 to set
     */
    public void setMaxDiskIOspeed1(String maxDiskIOspeed1) {
        this.maxDiskIOspeed1 = maxDiskIOspeed1;
    }

    /**
     * @return the diskIOspeed2
     */
    public String getDiskIOspeed2() {
        return diskIOspeed2;
    }

    /**
     * @param diskIOspeed2 the diskIOspeed2 to set
     */
    public void setDiskIOspeed2(String diskIOspeed2) {
        this.diskIOspeed2 = diskIOspeed2;
    }

    /**
     * @return the maxDiskIOspeed2
     */
    public String getMaxDiskIOspeed2() {
        return maxDiskIOspeed2;
    }

    /**
     * @param maxDiskIOspeed2 the maxDiskIOspeed2 to set
     */
    public void setMaxDiskIOspeed2(String maxDiskIOspeed2) {
        this.maxDiskIOspeed2 = maxDiskIOspeed2;
    }

    /**
     * @return the cpuProcessorUtilization
     */
    public String getCpuProcessorUtilization() {
        return cpuProcessorUtilization;
    }

    /**
     * @param cpuProcessorUtilization the cpuProcessorUtilization to set
     */
    public void setCpuProcessorUtilization(String cpuProcessorUtilization) {
        this.cpuProcessorUtilization = cpuProcessorUtilization;
    }

    /**
     * @return the maxCpuProcessorUtilization
     */
    public String getMaxCpuProcessorUtilization() {
        return maxCpuProcessorUtilization;
    }

    /**
     * @param maxCpuProcessorUtilization the maxCpuProcessorUtilization to set
     */
    public void setMaxCpuProcessorUtilization(String maxCpuProcessorUtilization) {
        this.maxCpuProcessorUtilization = maxCpuProcessorUtilization;
    }

    /**
     * @return the memUsedPer
     */
    public String getMemUsedPer() {
        return memUsedPer;
    }

    /**
     * @param memUsedPer the memUsedPer to set
     */
    public void setMemUsedPer(String memUsedPer) {
        this.memUsedPer = memUsedPer;
    }

    /**
     * @return the maxMemUsedPer
     */
    public String getMaxMemUsedPer() {
        return maxMemUsedPer;
    }

    /**
     * @param maxMemUsedPer the maxMemUsedPer to set
     */
    public void setMaxMemUsedPer(String maxMemUsedPer) {
        this.maxMemUsedPer = maxMemUsedPer;
    }

    /**
     * @return the swapMemUsedPer
     */
    public String getSwapMemUsedPer() {
        return swapMemUsedPer;
    }

    /**
     * @param swapMemUsedPer the swapMemUsedPer to set
     */
    public void setSwapMemUsedPer(String swapMemUsedPer) {
        this.swapMemUsedPer = swapMemUsedPer;
    }

    /**
     * @return the maxSwapMemUsedPer
     */
    public String getMaxSwapMemUsedPer() {
        return maxSwapMemUsedPer;
    }

    /**
     * @param maxSwapMemUsedPer the maxSwapMemUsedPer to set
     */
    public void setMaxSwapMemUsedPer(String maxSwapMemUsedPer) {
        this.maxSwapMemUsedPer = maxSwapMemUsedPer;
    }

    /**
     * @return the diskIOspeed
     */
    public String getDiskIOspeed() {
        return diskIOspeed;
    }

    /**
     * @param diskIOspeed the diskIOspeed to set
     */
    public void setDiskIOspeed(String diskIOspeed) {
        this.diskIOspeed = diskIOspeed;
    }

    /**
     * @return the maxDiskIOspeed
     */
    public String getMaxDiskIOspeed() {
        return maxDiskIOspeed;
    }

    /**
     * @param maxDiskIOspeed the maxDiskIOspeed to set
     */
    public void setMaxDiskIOspeed(String maxDiskIOspeed) {
        this.maxDiskIOspeed = maxDiskIOspeed;
    }

	
}
