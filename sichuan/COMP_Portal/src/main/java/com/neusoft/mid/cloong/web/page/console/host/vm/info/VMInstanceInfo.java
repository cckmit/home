/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.host.vm.core.VMStatus;

/**
 * 虚拟机实例信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午09:13:23
 */
public class VMInstanceInfo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String caseId;

    /**
     * 创建方式 0使用模板 1用户自定义
     */
    private String paramFlag;

    private String vmId;

    private String vmName;
    
    private String pmId;

    private String cpuNum;

    private String ramSize;

    private String discSize;

    private String isoId;

    private String isoName;

    private String isoDescription;

    private String resPoolId;

    private String resPoolName;

    private String resPoolPartId;

    private String resPoolPartName;

    /**
     * 资源池分区CPU总数
     */
    private String cpuNumTotal;

    /**
     * 资源池分区内存总数
     */
    private String ramSizeTotal;

    /**
     * 资源池分区磁盘总数
     */
    private String discSizeTotal;

    private String vmPassword;

    /**
     * 内网IP
     */
    private String privateIp;

    private VMStatus status;

    private String description;

    private String orderId;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    private String expireTime;

    private String standardId;

    private int hours;

    private String itemName;

    /**
     * 条目ID
     */
    private String itemId;

    /**
     * 增加的磁盘大小
     */
    private String discSizeAdd;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * 网卡列表
     */
    private List<NetInfo> netList;
    
    private String userName;
    
    private String operationURL;
    
    /**
     * 带宽
     */
    private String bandWidth;
    
    /**
     * 公网IP
     */
    private String networkIp;
    
    private String os;
    
    private String vmBossOrderId;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDiscSizeAdd() {
        return discSizeAdd;
    }

    public void setDiscSizeAdd(String discSizeAdd) {
        this.discSizeAdd = discSizeAdd;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public String getDiscSize() {
        return discSize;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    public String getIsoId() {
        return isoId;
    }

    public void setIsoId(String isoId) {
        this.isoId = isoId;
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

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(String bandWidth) {
		this.bandWidth = bandWidth;
	}

	public String getNetworkIp() {
		return networkIp;
	}

	public void setNetworkIp(String networkIp) {
		this.networkIp = networkIp;
	}

	public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public VMStatus getStatus() {
        return status;
    }

    public void setStatus(VMStatus status) {
        this.status = status;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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

    public String getResPoolName() {
        return resPoolName;
    }

    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    public String getResPoolPartName() {
        return resPoolPartName;
    }

    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public String getIsoDescription() {
        return isoDescription;
    }

    public void setIsoDescription(String isoDescription) {
        this.isoDescription = isoDescription;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmPassword() {
        return vmPassword;
    }

    public void setVmPassword(String vmPassword) {
        this.vmPassword = vmPassword;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取itemId字段数据
     * @return Returns the itemId.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置itemId字段数据
     * @param itemId The itemId to set.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

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
        sb.append("虚拟机内网IP为：").append(this.privateIp).append("\n");
        sb.append("虚拟机规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟机内网IP为：").append(this.privateIp).append("\n");
        sb.append("虚拟机状态为：").append(this.status).append("\n");
        sb.append("虚拟机带宽信息为：").append(this.bandWidth).append("\n");
        sb.append("虚拟机公网IP为：").append(this.networkIp).append("\n");
        sb.append("虚拟机备注信息为：").append(this.description).append("\n");
        sb.append("虚拟机订单变化为：").append(this.orderId).append("\n");
        sb.append("虚拟机创建时间为：").append(this.createTime).append("\n");
        sb.append("虚拟机到期时间为：").append(this.expireTime).append("\n");
        sb.append("虚拟机时长：").append(this.hours).append("\n");
        sb.append("条目ID").append(this.itemId).append("\n");
        sb.append("新增磁盘大小").append(this.discSizeAdd).append("\n");
        return sb.toString();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<NetInfo> getNetList() {
        return netList;
    }

    public void setNetList(List<NetInfo> netList) {
        this.netList = netList;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }

    public String getCpuNumTotal() {
        return cpuNumTotal;
    }

    public void setCpuNumTotal(String cpuNumTotal) {
        this.cpuNumTotal = cpuNumTotal;
    }

    public String getRamSizeTotal() {
        return ramSizeTotal;
    }

    public void setRamSizeTotal(String ramSizeTotal) {
        this.ramSizeTotal = ramSizeTotal;
    }

    public String getDiscSizeTotal() {
        return discSizeTotal;
    }

    public void setDiscSizeTotal(String discSizeTotal) {
        this.discSizeTotal = discSizeTotal;
    }

    /**
     * 获取userName字段数据
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName字段数据
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取operationURL字段数据
     * @return Returns the operationURL.
     */
    public String getOperationURL() {
        return operationURL;
    }

    /**
     * 设置operationURL字段数据
     * @param operationURL The operationURL to set.
     */
    public void setOperationURL(String operationURL) {
        this.operationURL = operationURL;
    }

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

	public String getVmBossOrderId() {
		return vmBossOrderId;
	}

	public void setVmBossOrderId(String vmBossOrderId) {
		this.vmBossOrderId = vmBossOrderId;
	}

}
