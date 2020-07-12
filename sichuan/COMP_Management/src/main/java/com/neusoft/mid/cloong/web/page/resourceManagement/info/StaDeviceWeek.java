package com.neusoft.mid.cloong.web.page.resourceManagement.info;



/**
 * 业务下--性能统计
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-5 上午8:37:39
 */
public class StaDeviceWeek
{   
	/**
	 * 业务ID
	 */
	private String app_id; 
	/**
	 * 设备ID
	 */
	private String device_id; 
	/**
	 * 设备类型
	 */
	private String device_type; 
	/**
	 * 设备名称
	 */
	private String device_name; 
	/**
	 * 设备数-物理机
	 */
	private String pm_num; 
	/**
	 * 设备数-虚拟机
	 */
	private String vm_num; 
	/**
	 * 设备数-虚拟硬盘
	 */
	private String ebs_num; 
	
	/**
	 * 设备数-小型机
	 */
	private String miniPm_num; 
	
	/**
	 * 设备数-小型机分区
	 */
	private String miniPmPar_num; 
	
	/**
	 * CPU使用率（单位%）
	 */
	private String cpu_processor_utilization; 
	/**
	 * 内存使用率（单位%）
	 */
	private String mem_used_per; 
	/**
	 * 磁盘使用率（单位%）
	 */
	private String disk_used_per;
	/**
	 * 统计日期（格式：YYYYMMDD）
	 */
	private String report_date;
	/**
	 *  为列表   展示使用率
	 */
	private String used_per;

	/**
	 * @return the app_id
	 */
	public String getApp_id() {
		return app_id;
	}

	/**
	 * @param app_id the app_id to set
	 */
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	/**
	 * @return the cpu_processor_utilization
	 */
	public String getCpu_processor_utilization() {
		return cpu_processor_utilization;
	}

	/**
	 * @param cpu_processor_utilization the cpu_processor_utilization to set
	 */
	public void setCpu_processor_utilization(String cpu_processor_utilization) {
		this.cpu_processor_utilization = cpu_processor_utilization;
	}

	/**
	 * @return the mem_used_per
	 */
	public String getMem_used_per() {
		return mem_used_per;
	}

	/**
	 * @param mem_used_per the mem_used_per to set
	 */
	public void setMem_used_per(String mem_used_per) {
		this.mem_used_per = mem_used_per;
	}

	/**
	 * @return the disk_used_per
	 */
	public String getDisk_used_per() {
		return disk_used_per;
	}

	/**
	 * @param disk_used_per the disk_used_per to set
	 */
	public void setDisk_used_per(String disk_used_per) {
		this.disk_used_per = disk_used_per;
	}

	/**
	 * @return the report_date
	 */
	public String getReport_date() {
		return report_date;
	}

	/**
	 * @param report_date the report_date to set
	 */
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}

	/**
	 * @return the device_id
	 */
	public String getDevice_id() {
		return device_id;
	}

	/**
	 * @param device_id the device_id to set
	 */
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	/**
	 * @return the device_type
	 */
	public String getDevice_type() {
		return device_type;
	}

	/**
	 * @param device_type the device_type to set
	 */
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	/**
	 * @return the device_name
	 */
	public String getDevice_name() {
		return device_name;
	}

	/**
	 * @param device_name the device_name to set
	 */
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	/**
	 * @return the used_per
	 */
	public String getUsed_per() {
		return used_per;
	}

	/**
	 * @param used_per the used_per to set
	 */
	public void setUsed_per(String used_per) {
		this.used_per = used_per;
	}

	/**
	 * @return the pm_num
	 */
	public String getPm_num() {
		return pm_num;
	}

	/**
	 * @param pm_num the pm_num to set
	 */
	public void setPm_num(String pm_num) {
		this.pm_num = pm_num;
	}

	/**
	 * @return the vm_num
	 */
	public String getVm_num() {
		return vm_num;
	}

	/**
	 * @param vm_num the vm_num to set
	 */
	public void setVm_num(String vm_num) {
		this.vm_num = vm_num;
	}

	/**
	 * @return the ebs_num
	 */
	public String getEbs_num() {
		return ebs_num;
	}

	/**
	 * @param ebs_num the ebs_num to set
	 */
	public void setEbs_num(String ebs_num) {
		this.ebs_num = ebs_num;
	}

	/**
	 * @return the miniPm_num
	 */
	public String getMiniPm_num() {
		return miniPm_num;
	}

	/**
	 * @param miniPm_num the miniPm_num to set
	 */
	public void setMiniPm_num(String miniPm_num) {
		this.miniPm_num = miniPm_num;
	}

	/**
	 * @return the miniPmPar_num
	 */
	public String getMiniPmPar_num() {
		return miniPmPar_num;
	}

	/**
	 * @param miniPmPar_num the miniPmPar_num to set
	 */
	public void setMiniPmPar_num(String miniPmPar_num) {
		this.miniPmPar_num = miniPmPar_num;
	}

	
}
