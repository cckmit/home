package com.neusoft.mid.cloong.web.page.resourceManagement.info;

import com.neusoft.mid.cloong.web.BaseInfo;
/**
 * 设备数统计
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version 创建时间：2015-1-6 下午4:18:30
 */
public class DeviceNum extends BaseInfo {

    /**
     * 业务ID
     */
    private String app_id;
    /**
     * 业务名称
     */
    private String app_name;
    /**
     * 小型机个数
     */
    private String miniPm_num;
    /**
     * 小型机分区个数
     */
    private String miniPmPar_num;
    /**
     * 物理机个数
     */
    private String pm_num;
    /**
     * 虚拟机个数
     */
    private String vm_num;
    /**
     * 虚拟硬盘个数
     */
    private String ebs_num;
    
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
	 * @return the app_name
	 */
	public String getApp_name() {
		return app_name;
	}
	/**
	 * @param app_name the app_name to set
	 */
	public void setApp_name(String app_name) {
		this.app_name = app_name;
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
}
