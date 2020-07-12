package com.neusoft.mid.cloong.web.page.user.order;

import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;

/**
 * 虚拟机备份实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午07:52:44
 */
public class VmBakInstanceInfo {

    /**
     * 实例id
     */
    private String caseId;

    /**
     * 备份id
     */
    private String vmBakId;

    /**
     * 备份名字
     */
    private String vmBakName;
    
    /**
     * 备份周期
     */
    private String backupCyc;
    
    /**
     * 每个备份保留的最长时间
     */
    private String backStoreTime;
    
    /**
     * 备份任务开始时间
     */
    private String backupStartTime;
    
    /**
     * 接收时间
     */
    private String acceptTime;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     * 资源池分区名称
     */
    private String resPoolPartName;
    
    /**
     * 状态
     */
    private VmBakStatus status;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 订单id
     */
    private String orderId;
    
    /**
     * 虚拟机id
     */
    private String vmId;
    
    /**
     * 虚拟机名称
     */
    private String vmName;

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
     * 规格id
     */
    private String standardId;

    /**
     * 
     * getCaseId TODO 方法
     * @return TODO
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 
     * setCaseId TODO 方法
     * @param caseId TODO
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 
     * getVmBakId TODO 方法
     * @return TODO
     */
    public String getVmBakId() {
        return vmBakId;
    }

    /**
     * 
     * setVmBakId TODO 方法
     * @param vmBakId TODO
     */
    public void setVmBakId(String vmBakId) {
        this.vmBakId = vmBakId;
    }

    /**
     * 
     * getVmBakName TODO 方法
     * @return TODO
     */
    public String getVmBakName() {
        return vmBakName;
    }

    /**
     * 
     * setVmBakName TODO 方法
     * @param vmBakName TODO
     */
    public void setVmBakName(String vmBakName) {
        this.vmBakName = vmBakName;
    }

    /**
     * 
     * getAcceptTime TODO 方法
     * @return TODO
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     * 
     * setAcceptTime TODO 方法
     * @param acceptTime TODO
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 
     * getResPoolId TODO 方法
     * @return TODO
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 
     * setResPoolId TODO 方法
     * @param resPoolId TODO
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 
     * getResPoolPartId TODO 方法
     * @return TODO
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 
     * setResPoolPartId TODO 方法
     * @param resPoolPartId TODO
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 
     * getStatus TODO 方法
     * @return TODO
     */
    public VmBakStatus getStatus() {
        return status;
    }

    /**
     * 
     * setStatus TODO 方法
     * @param status TODO
     */
    public void setStatus(VmBakStatus status) {
        this.status = status;
    }

    /**
     * 
     * getDescription TODO 方法
     * @return TODO
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * setDescription TODO 方法
     * @param description TODO
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * getOrderId TODO 方法
     * @return TODO
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * setOrderId TODO 方法
     * @param orderId TODO
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * getVmId TODO 方法
     * @return TODO
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 
     * setVmId TODO 方法
     * @param vmId TODO
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 
     * getCreateTime TODO 方法
     * @return TODO
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * setCreateTime TODO 方法
     * @param createTime TODO
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * getCreateUser TODO 方法
     * @return TODO
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * setCreateUser TODO 方法
     * @param createUser TODO
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * getUpdateTime TODO 方法
     * @return TODO
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * setUpdateTime TODO 方法
     * @param updateTime TODO
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * getUpdateUser TODO 方法
     * @return TODO
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * setUpdateUser TODO 方法
     * @param updateUser TODO
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * getStandardId TODO 方法
     * @return TODO
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 
     * setStandardId TODO 方法
     * @param standardId TODO
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 
     * toString 虚拟机备份实体信息
     * @return 虚拟机备份实体信息
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机备份编号为：").append(this.vmBakId).append("\n");
        sb.append("虚拟机备份名称为：").append(this.vmBakName).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("虚拟机备份被接受时间为：").append(this.acceptTime).append("\n");
        sb.append("虚拟机备份规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟机备份状态为：").append(this.status).append("\n");
        sb.append("虚拟机备份备注信息为：").append(this.description).append("\n");
        sb.append("虚拟机备份订单变化为：").append(this.orderId).append("\n");
        sb.append("虚拟机备份创建时间为：").append(this.createTime).append("\n");
        sb.append("虚拟机备份创建人为：").append(this.createUser).append("\n");
        sb.append("虚拟机备份虚拟机ID：").append(this.vmId).append("\n");
        return sb.toString();
    }

	/**
	 * @return the backupCyc
	 */
	public String getBackupCyc() {
		return backupCyc;
	}

	/**
	 * @param backupCyc the backupCyc to set
	 */
	public void setBackupCyc(String backupCyc) {
		this.backupCyc = backupCyc;
	}

	/**
	 * @return the backStoreTime
	 */
	public String getBackStoreTime() {
		return backStoreTime;
	}

	/**
	 * @param backStoreTime the backStoreTime to set
	 */
	public void setBackStoreTime(String backStoreTime) {
		this.backStoreTime = backStoreTime;
	}

	/**
	 * @return the backupStartTime
	 */
	public String getBackupStartTime() {
		return backupStartTime;
	}

	/**
	 * @param backupStartTime the backupStartTime to set
	 */
	public void setBackupStartTime(String backupStartTime) {
		this.backupStartTime = backupStartTime;
	}

	/**
	 * @return the resPoolPartName
	 */
	public String getResPoolPartName() {
		return resPoolPartName;
	}

	/**
	 * @param resPoolPartName the resPoolPartName to set
	 */
	public void setResPoolPartName(String resPoolPartName) {
		this.resPoolPartName = resPoolPartName;
	}

	/**
	 * @return the vmName
	 */
	public String getVmName() {
		return vmName;
	}

	/**
	 * @param vmName the vmName to set
	 */
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

}
