package com.neusoft.mid.cloong.web.page.console.vmbak.info;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;

/**
 * 虚拟机备份实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午07:52:44
 */
public class VmBakInstanceInfo implements Serializable{
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
     * 虚拟机备份受理时间（调用虚拟机备份任务创建接口返回的备份创建时间）
     */
    private String acceptTime;

    /**
     * 虚拟机ID
     */
    private String vmId;
    
    /**
     * 虚拟机名称
     */
    private String vmName;
    
    /**
     * 业务ID
     */
    private String appId;
    
    /**
     * 业务名称
     */
    private String appName;

    /**
     * 数据库返回虚拟机备份状态
     */
    private VmBakStatus status;
    
    /**
     * 数据库返回虚拟机备份状态
     */
    private String bakupStatus;
    
    /**
     * 数据库返回虚拟机备份状态
     */
    private String bakupStatusText;
    
    /**
     * 数据库返回虚拟机备份状态
     */
    private String restoreStatus;
    
    /**
     * 数据库返回虚拟机备份状态
     */
    private String restoreStatusText;
    
    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池分区ID
     */
    private String resPoolPartId;
    
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 更新人
     */
    private String updateUser;
    
    /**
     * 恢复备份ID
     */
    private String restoreVmBakInternalId;
    
    /**
     * 恢复时间
     */
    private String restoreTime;
    
    /**
     * 查询去除虚拟机状态
     */
    private String vmStatus;
    
    /**
	 * 当前用户绑定的业务ID
	 */
	private List<String> appIdList;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机备份任务实例编号为：").append(this.caseId).append("\n");
        sb.append("虚拟机备份任务编号为：").append(this.vmBakId).append("\n");
        sb.append("虚拟机备份任务名称为：").append(this.vmBakName).append("\n");
        sb.append("备份周期为：").append(this.backupCyc).append("\n");
        sb.append("备份保留时间为：").append(this.backStoreTime).append("\n");
        sb.append("备份开始时间为：").append(this.backupStartTime).append("\n");
        sb.append("备份受理时间为：").append(this.acceptTime).append("\n");
        sb.append("虚拟机ID：").append(this.vmId).append("\n");
        sb.append("虚拟机备份状态为：").append(this.status).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("虚拟机备份任务订单变化为：").append(this.orderId).append("\n");
        sb.append("虚拟机备份任务备注信息为：").append(this.description).append("\n");
        sb.append("虚拟机备份任务创建时间为：").append(this.createTime).append("\n");
        sb.append("虚拟机备份任务创建人为：").append(this.createUser).append("\n");
        sb.append("虚拟机备份任务更新时间为：").append(this.updateTime).append("\n");
        sb.append("虚拟机备份任务更新人为：").append(this.updateUser).append("\n");
        sb.append("恢复备份ID为：").append(this.restoreVmBakInternalId).append("\n");
        sb.append("恢复时间为：").append(this.restoreTime).append("\n");
        return sb.toString();
    }

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

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public VmBakStatus getStatus() {
		return status;
	}

	public void setStatus(VmBakStatus status) {
		this.status = status;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getResPoolPartId() {
		return resPoolPartId;
	}

	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

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

	public String getRestoreVmBakInternalId() {
		return restoreVmBakInternalId;
	}

	public void setRestoreVmBakInternalId(String restoreVmBakInternalId) {
		this.restoreVmBakInternalId = restoreVmBakInternalId;
	}

	public String getRestoreTime() {
		return restoreTime;
	}

	public void setRestoreTime(String restoreTime) {
		this.restoreTime = restoreTime;
	}

	public String getVmStatus() {
		return vmStatus;
	}

	public void setVmStatus(String vmStatus) {
		this.vmStatus = vmStatus;
	}

	public List<String> getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(List<String> appIdList) {
		this.appIdList = appIdList;
	}

	public String getBakupStatus() {
		return bakupStatus;
	}

	public void setBakupStatus(String bakupStatus) {
		this.bakupStatus = bakupStatus;
	}

	public String getBakupStatusText() {
		return bakupStatusText;
	}

	public void setBakupStatusText(String bakupStatusText) {
		this.bakupStatusText = bakupStatusText;
	}

	public String getRestoreStatus() {
		return restoreStatus;
	}

	public void setRestoreStatus(String restoreStatus) {
		this.restoreStatus = restoreStatus;
	}

	public String getRestoreStatusText() {
		return restoreStatusText;
	}

	public void setRestoreStatusText(String restoreStatusText) {
		this.restoreStatusText = restoreStatusText;
	}
}
