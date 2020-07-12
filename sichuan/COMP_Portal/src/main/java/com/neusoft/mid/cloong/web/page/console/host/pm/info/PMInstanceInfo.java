/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.host.pm.info;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.host.pm.core.PMStatus;

/**
 * 物理机实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午07:52:44
 */
public class PMInstanceInfo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String caseId;

    /**
     * 创建方式 0使用模板 1用户自定义
     */
    private String paramFlag;

    private String pmId;

    private String pmName;

    private String cpuType;

    private String ramSize;

    private String discSize;

    private String isoId;

    private String isoName;
    
    private String isoDescription;

    private String serverType;

    private String resPoolId;

    private String resPoolName;

    private String resPoolPartId;

    private String resPoolPartName;

    private String operationIP;

    private String pmPassword;

    private String pmUser;

    private PMStatus status;

    private String description;

    private String orderId;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    private String expireTime;

    private String standardId;

    private int hours;

    /**
     * 条目ID
     */
    private String itemId;

    private String itemName;

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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getParamFlag() {
        return paramFlag;
    }

    public void setParamFlag(String paramFlag) {
        this.paramFlag = paramFlag;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
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

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

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

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getResPoolPartName() {
        return resPoolPartName;
    }

    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    public String getOperationIP() {
        return operationIP;
    }

    public void setOperationIP(String operationIP) {
        this.operationIP = operationIP;
    }

    public String getPmPassword() {
        return pmPassword;
    }

    public void setPmPassword(String pmPassword) {
        this.pmPassword = pmPassword;
    }

    public String getPmUser() {
        return pmUser;
    }

    public void setPmUser(String pmUser) {
        this.pmUser = pmUser;
    }

    public PMStatus getStatus() {
        return status;
    }

    public void setStatus(PMStatus status) {
        this.status = status;
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

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public List<NetInfo> getNetList() {
        return netList;
    }

    public void setNetList(List<NetInfo> netList) {
        this.netList = netList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("物理机编号为：").append(this.pmId).append("\n");
        sb.append("物理机名称为：").append(this.pmName).append("\n");
        sb.append("物理机内存为：").append(this.ramSize).append("\n");
        sb.append("物理机存储空间为：").append(this.discSize).append("\n");
        sb.append("物理机系统类型为：").append(this.isoId).append("\n");
        sb.append("物理机系统名称为：").append(this.isoName).append("\n");
        sb.append("物理机系统描述为：").append(this.isoDescription).append("\n");
        sb.append("物理机类型为：").append(this.serverType).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池名称为：").append(this.resPoolName).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("资源池分区名称为：").append(this.resPoolPartName).append("\n");
        sb.append("用户名：").append(this.pmUser).append("\n");
        sb.append("用户口令：").append(this.pmPassword).append("\n");
        sb.append("物理机规格ID为：").append(this.standardId).append("\n");
        sb.append("物理机状态为：").append(this.status).append("\n");
        sb.append("物理机备注信息为：").append(this.description).append("\n");
        sb.append("物理机订单变化为：").append(this.orderId).append("\n");
        sb.append("物理机创建时间为：").append(this.createTime).append("\n");
        sb.append("物理机到期时间为：").append(this.expireTime).append("\n");
        sb.append("物理机时长：").append(this.hours).append("\n");
        return sb.toString();
    }

	public String getIsoDescription() {
		return isoDescription;
	}

	public void setIsoDescription(String isoDescription) {
		this.isoDescription = isoDescription;
	}

}
