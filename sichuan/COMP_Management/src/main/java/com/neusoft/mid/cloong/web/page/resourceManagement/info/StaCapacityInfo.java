package com.neusoft.mid.cloong.web.page.resourceManagement.info;

/**
 * 资源视图整体情况报表
 * 
 * @author <a href="mailto:x_wang@neusoft.com">WangXin</a>
 * @version version 1.0：2015-1-8 上午11:22:39
 */
public class StaCapacityInfo {

	/**
	 * 资源池ID
	 */
	private String resPoolId;

	/**
	 * 资源池名称
	 */
	private String resPoolName;
	
	/**
	 * 分区ID
	 */
	private String  poolPartId;

	/**
	 * 分区名称
	 */
	private String poolPartName;

	/**
	 * 资源类型
	 */
	private String resType;

	/**
	 * 资源总量
	 */
	private String resTotal;

	/**
	 * 已用资源占用率百分数
	 */
	private String resUsed;
	
	/**
	 * vcpu资源总量
	 */
	private String vcpuResTotal;

	/**
	 * vcpu已用资源占用率百分数
	 */
	private String vcpuResUsed;
	
	/**
	 * memory资源总量
	 */
	private String memoryResTotal;

	/**
	 * memory已用资源占用率百分数
	 */
	private String memoryResUsed;
	
	/**
	 * disk资源总量
	 */
	private String diskResTotal;

	/**
	 * disk已用资源占用率百分数
	 */
	private String diskResUsed;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 虚拟机数
	 */
	private String vmCount;

	/**
	 * 物理机数
	 */
	private String pmCount;

	/**
	 * 虚拟磁盘数
	 */
	private String ebsCount;
	
	/**
	 * 小型机数
	 */
	private String miniPmCount;
	
	/**
	 * 小型机分区数
	 */
	private String miniPmParCount;
	
	/**
	 * 存储阵列数
	 */
	private String raidCount;

	/**
	 * 交换机数
	 */
	private String swCount;

	/**
	 * 路由器数
	 */
	private String rtCount;
	
	/**
	 * 防火墙数
	 */
	private String fwCount;

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getResPoolName() {
		return resPoolName;
	}

	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResTotal() {
		return resTotal;
	}

	public void setResTotal(String resTotal) {
		this.resTotal = resTotal;
	}

	public String getResUsed() {
		return resUsed;
	}

	public void setResUsed(String resUsed) {
		this.resUsed = resUsed;
	}

	public String getVmCount() {
		return vmCount;
	}

	public void setVmCount(String vmCount) {
		this.vmCount = vmCount;
	}

	public String getPmCount() {
		return pmCount;
	}

	public void setPmCount(String pmCount) {
		this.pmCount = pmCount;
	}

	public String getEbsCount() {
		return ebsCount;
	}

	public void setEbsCount(String ebsCount) {
		this.ebsCount = ebsCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the miniPmCount
	 */
	public String getMiniPmCount() {
		return miniPmCount;
	}

	/**
	 * @param miniPmCount the miniPmCount to set
	 */
	public void setMiniPmCount(String miniPmCount) {
		this.miniPmCount = miniPmCount;
	}

	/**
	 * @return the miniPmParCount
	 */
	public String getMiniPmParCount() {
		return miniPmParCount;
	}

	/**
	 * @param miniPmParCount the miniPmParCount to set
	 */
	public void setMiniPmParCount(String miniPmParCount) {
		this.miniPmParCount = miniPmParCount;
	}

	/**
	 * @return the raidCount
	 */
	public String getRaidCount() {
		return raidCount;
	}

	/**
	 * @param raidCount the raidCount to set
	 */
	public void setRaidCount(String raidCount) {
		this.raidCount = raidCount;
	}

	/**
	 * @return the swCount
	 */
	public String getSwCount() {
		return swCount;
	}

	/**
	 * @param swCount the swCount to set
	 */
	public void setSwCount(String swCount) {
		this.swCount = swCount;
	}

	/**
	 * @return the rtCount
	 */
	public String getRtCount() {
		return rtCount;
	}

	/**
	 * @param rtCount the rtCount to set
	 */
	public void setRtCount(String rtCount) {
		this.rtCount = rtCount;
	}

	/**
	 * @return the fwCount
	 */
	public String getFwCount() {
		return fwCount;
	}

	/**
	 * @param fwCount the fwCount to set
	 */
	public void setFwCount(String fwCount) {
		this.fwCount = fwCount;
	}

	public String getPoolPartId() {
		return poolPartId;
	}

	public void setPoolPartId(String poolPartId) {
		this.poolPartId = poolPartId;
	}

	public String getPoolPartName() {
		return poolPartName;
	}

	public void setPoolPartName(String poolPartName) {
		this.poolPartName = poolPartName;
	}

	public String getVcpuResTotal() {
		return vcpuResTotal;
	}

	public void setVcpuResTotal(String vcpuResTotal) {
		this.vcpuResTotal = vcpuResTotal;
	}

	public String getVcpuResUsed() {
		return vcpuResUsed;
	}

	public void setVcpuResUsed(String vcpuResUsed) {
		this.vcpuResUsed = vcpuResUsed;
	}

	public String getMemoryResTotal() {
		return memoryResTotal;
	}

	public void setMemoryResTotal(String memoryResTotal) {
		this.memoryResTotal = memoryResTotal;
	}

	public String getMemoryResUsed() {
		return memoryResUsed;
	}

	public void setMemoryResUsed(String memoryResUsed) {
		this.memoryResUsed = memoryResUsed;
	}

	public String getDiskResTotal() {
		return diskResTotal;
	}

	public void setDiskResTotal(String diskResTotal) {
		this.diskResTotal = diskResTotal;
	}

	public String getDiskResUsed() {
		return diskResUsed;
	}

	public void setDiskResUsed(String diskResUsed) {
		this.diskResUsed = diskResUsed;
	}

}
