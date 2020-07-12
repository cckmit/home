/**
 * 
 */
package com.neusoft.mid.cloong.web.page.user.order;

import java.util.List;

import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;

/**
 * 
 * 虚拟机实例信息
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2014-2-13 下午05:19:42
 */
public class VMInstanceInfo {
    
    /**
     * 实例ID
     */
    private String caseId;
    
    /**
     * 物理机id
     */
    private String pmId;

    /**
     * VM id
     */
    private String vmId;
    

    /**
     * VM 名称
     */
    private String vmName;

    /**
     * cpu 数量
     */
    private String cpuNum;

    /**
     * 内存大小
     */
    private String ramSize;

    /**
     * 磁盘大小
     */
    private String discSize;

    /**
     * 镜像ID
     */
    private String isoId;

    /**
     * 镜像名称
     */
    private String isoName;

    /**
     * 镜像描述
     */
    private String isoDescription;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池名称
     */
    private String resPoolName;

    /**
     * 资源池分区ID
     */
    private String resPoolPartId;

    /**
     * 资源池分区名称
     */
    private String resPoolPartName;

    /**
     * VM 密码
     */
    private String vmPassword;

    /**
     * ip
     */
    private String privateIP;

    /**
     * 状态
     */
    private VMStatus status;

    /**
     * 描述
     */
    private String description;

    /**
     * 订单ID
     */
    private String orderId;

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
     * 拥有人
     */
    private String ownUser;

    /**
     * 到期时间
     */
    private String expireTime;

    /**
     * 子网
     */
    private String subNetwork;

    /**
     * serverId
     */
    private String serverId;

    /**
     * 接收时间
     */
    private String acceptTime;

    /**
     * 规格id
     */
    private String standardId;
    
    /**
     * 克隆时，备份ID
     */
    private String vmBackupId;

    /**
     * 时长
     */
    private int hours;

    /**
     * 条目名称
     */
    private String itemName;
    
    /**
     * 条目ID
     */
    private String itemId;
    
    /**
     * 修改增加的磁盘容量
     */
    private String discSizeAdd;
    
    /**
     * 网卡列表
     */
    private List<NetInfo> netList;
    
    /**
     * 创建方式 0使用模板 1用户自定义
     */
    private String paramFlag;
    
    
    /**
     * 最大时间
     */
    private String maxTime;
    
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDiscSizeAdd() {
        return discSizeAdd;
    }

    public void setDiscSizeAdd(String discSizeAdd) {
        this.discSizeAdd = discSizeAdd;
    }

    /**
     * 获取vmBackupId字段数据
     * @return Returns the vmBackupId.
     */
    public String getVmBackupId() {
        return vmBackupId;
    }

    /**
     * 设置vmBackupId字段数据
     * @param vmBackupId The vmBackupId to set.
     */
    public void setVmBackupId(String vmBackupId) {
        this.vmBackupId = vmBackupId;
    }

    /**
     * 
     * getItemName  方法
     * @return 
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * setItemName  方法
     * @param itemName 
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * getHours  方法
     * @return 
     */
    public int getHours() {
        return hours;
    }

    /**
     * 
     * setHours  方法
     * @param hours 
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * 
     * getCpuNum  方法
     * @return 
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * 
     * setCpuNum  方法
     * @param cpuNum 
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 
     * getVmName  方法
     * @return 
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 
     * setVmName  方法
     * @param vmName 
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    /**
     * 
     * getRamSize  方法
     * @return 
     */
    public String getRamSize() {
        return ramSize;
    }

    /**
     * 
     * setRamSize  方法
     * @param ramSize 
     */
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    /**
     * 
     * getDiscSize  方法
     * @return 
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * 
     * setDiscSize  方法
     * @param discSize 
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    /**
     * 
     * getIsoId  方法
     * @return 
     */
    public String getIsoId() {
        return isoId;
    }

    /**
     * 
     * setIsoId  方法
     * @param isoId 
     */
    public void setIsoId(String isoId) {
        this.isoId = isoId;
    }

    /**
     * 
     * getResPoolId  方法
     * @return 
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 
     * setResPoolId  方法
     * @param resPoolId 
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 
     * getResPoolPartId  方法
     * @return 
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 
     * setResPoolPartId  方法
     * @param resPoolPartId 
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 
     * getPrivateIP  方法
     * @return 
     */
    public String getPrivateIP() {
        return privateIP;
    }

    /**
     * 
     * setPrivateIP  方法
     * @param privateIP 
     */
    public void setPrivateIP(String privateIP) {
        this.privateIP = privateIP;
    }

    /**
     * 
     * getDescription  方法
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * setDescription  方法
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * getOrderId  方法
     * @return 
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * setOrderId  方法
     * @param orderId 
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * getCreateTime  方法
     * @return 
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * setCreateTime  方法
     * @param createTime 
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * getOwnUser  方法
     * @return 
     */
    public String getOwnUser() {
        return ownUser;
    }

    /**
     * 
     * setOwnUser  方法
     * @param ownUser 
     */
    public void setOwnUser(String ownUser) {
        this.ownUser = ownUser;
    }

    /**
     * 
     * getExpireTime  方法
     * @return 
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * 
     * setExpireTime  方法
     * @param expireTime 
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 
     * getStatus  方法
     * @return 
     */
    public VMStatus getStatus() {
        return status;
    }

    /**
     * 
     * setStatus  方法
     * @param status 
     */
    public void setStatus(VMStatus status) {
        this.status = status;
    }

    /**
     * 
     * getCaseId  方法
     * @return 
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 
     * setCaseId  方法
     * @param caseId 
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 
     * getCreateUser  方法
     * @return 
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * setCreateUser  方法
     * @param createUser 
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * getUpdateTime  方法
     * @return 
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * setUpdateTime  方法
     * @param updateTime 
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * getUpdateUser  方法
     * @return 
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * setUpdateUser  方法
     * @param updateUser 
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * getResPoolName  方法
     * @return 
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * 
     * setResPoolName  方法
     * @param resPoolName 
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    /**
     * 
     * getResPoolPartName  方法
     * @return 
     */
    public String getResPoolPartName() {
        return resPoolPartName;
    }

    /**
     * 
     * setResPoolPartName  方法
     * @param resPoolPartName 
     */
    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    /**
     * 
     * getIsoName  方法
     * @return 
     */
    public String getIsoName() {
        return isoName;
    }

    /**
     * 
     * setIsoName  方法
     * @param isoName 
     */
    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    /**
     * 
     * getIsoDescription  方法
     * @return 
     */
    public String getIsoDescription() {
        return isoDescription;
    }

    /**
     * 
     * setIsoDescription  方法
     * @param isoDescription 
     */
    public void setIsoDescription(String isoDescription) {
        this.isoDescription = isoDescription;
    }

    /**
     * 
     * getVmId  方法
     * @return 
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 
     * setVmId  方法
     * @param vmId 
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 
     * getVmPassword  方法
     * @return 
     */
    public String getVmPassword() {
        return vmPassword;
    }

    /**
     * 
     * setVmPassword  方法
     * @param vmPassword 
     */
    public void setVmPassword(String vmPassword) {
        this.vmPassword = vmPassword;
    }

    /**
     * 
     * getSubNetwork  方法
     * @return 
     */
    public String getSubNetwork() {
        return subNetwork;
    }

    /**
     * 
     * setSubNetwork  方法
     * @param subNetwork 
     */
    public void setSubNetwork(String subNetwork) {
        this.subNetwork = subNetwork;
    }

    /**
     * 
     * getServerId  方法
     * @return 
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * 
     * setServerId  方法
     * @param serverId 
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * 
     * getAcceptTime  方法
     * @return 
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     * 
     * setAcceptTime  方法
     * @param acceptTime 
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 
     * getStandardId  方法
     * @return 
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 
     * setStandardId  方法
     * @param standardId 
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 
     * toString 虚拟机信息
     * @return 虚拟机信息
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机编号为：").append(this.vmId).append("\n");
        sb.append("虚拟机名称为：").append(this.vmName).append("\n");
        sb.append("虚拟机CPU为：").append(this.cpuNum).append("\n");
        sb.append("虚拟机内存为：").append(this.ramSize).append("\n");
        sb.append("虚拟机存储空间为：").append(this.discSize).append("\n");
        sb.append("虚拟机系统类型为：").append(this.isoId).append("\n");
        sb.append("虚拟机系统名称为：").append(this.isoName).append("\n");
        sb.append("虚拟机系统描述为：").append(this.isoDescription).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池名称为：").append(this.resPoolName).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("资源池分区名称为：").append(this.resPoolPartName).append("\n");
        sb.append("用户口令：").append(this.vmPassword).append("\n");
        sb.append("虚拟机内网IP为：").append(this.privateIP).append("\n");
        sb.append("虚拟机子网为：").append(this.subNetwork).append("\n");
        sb.append("虚拟机所在物理机编号为：").append(this.serverId).append("\n");
        sb.append("虚拟机被接受时间为：").append(this.acceptTime).append("\n");
        sb.append("虚拟机规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟机内网IP为：").append(this.privateIP).append("\n");
        sb.append("虚拟机状态为：").append(this.status).append("\n");
        sb.append("虚拟机备注信息为：").append(this.description).append("\n");
        sb.append("虚拟机订单变化为：").append(this.orderId).append("\n");
        sb.append("虚拟机创建时间为：").append(this.createTime).append("\n");
        sb.append("虚拟机到期时间为：").append(this.expireTime).append("\n");
        sb.append("虚拟机时长：").append(this.hours).append("\n");
        sb.append("虚拟机所有者").append(this.ownUser).append("\n");
        return sb.toString();
    }

	/**
	 * @return the netList
	 */
	public List<NetInfo> getNetList() {
		return netList;
	}

	/**
	 * @param netList the netList to set
	 */
	public void setNetList(List<NetInfo> netList) {
		this.netList = netList;
	}

	/**
	 * @return the paramFlag
	 */
	public String getParamFlag() {
		return paramFlag;
	}

	/**
	 * @param paramFlag the paramFlag to set
	 */
	public void setParamFlag(String paramFlag) {
		this.paramFlag = paramFlag;
	}

	/**
	 * @return the maxTime
	 */
	public String getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

}
