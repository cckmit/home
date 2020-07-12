package com.neusoft.mid.cloong.web.page.console.vmbak.info;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;


/**
 * 虚拟机备份查询列表实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午07:04:22
 */
public class VmBakResultInfo implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 虚拟机备份任务实例ID
     */
    private String caseId;

    /**
     * 虚拟机备份任务ID
     */
    private String vmBakId;

    /**
     * 虚拟机备份任务名称
     */
    private String vmBakName;

    /**
     * 备份周期（备份周期：每天：1，每周：7）
     */
    private int backupCyc;

    /**
     * 备份保留的最长时间（单位：天）
     */
    private int backStoreTime;

    /**
     * 备份任务开始时间
     */
    private String backupStartTime;

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 虚拟机备份任务状态
     */
    private VmBakStatus status;

    /**
     * 备注
     */
    private String description;

    /**
     * 虚拟机备份任务状态名称
     */
    private String statusText;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 查询去除虚拟机备份任务状态
     */
    private String vmBakStatus;

    /**
     * 虚拟机备份任务状态
     */
    private String queryStatus;
    
    /**
	 * 当前用户绑定的业务ID
	 */
	private List<String> appIdList;
	
	/**
     * 业务ID
     */
    private String appId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getVmBakId() {
		return vmBakId;
	}

	public void setVmBakId(String vmBakId) {
		this.vmBakId = vmBakId;
	}

	public String getVmBakName() {
		return vmBakName;
	}

	public void setVmBakName(String vmBakName) {
		this.vmBakName = vmBakName;
	}

	public int getBackupCyc() {
		return backupCyc;
	}

	public void setBackupCyc(int backupCyc) {
		this.backupCyc = backupCyc;
	}

	public int getBackStoreTime() {
		return backStoreTime;
	}

	public void setBackStoreTime(int backStoreTime) {
		this.backStoreTime = backStoreTime;
	}

	public String getBackupStartTime() {
		return backupStartTime;
	}

	public void setBackupStartTime(String backupStartTime) {
		this.backupStartTime = backupStartTime;
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

	public VmBakStatus getStatus() {
		return status;
	}

	public void setStatus(VmBakStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public List<String> getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(List<String> appIdList) {
		this.appIdList = appIdList;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getVmBakStatus() {
		return vmBakStatus;
	}

	public void setVmBakStatus(String vmBakStatus) {
		this.vmBakStatus = vmBakStatus;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
