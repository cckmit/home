package com.neusoft.mid.cloong.web.page.ebs.order.info;

import com.neusoft.mid.cloong.ebs.core.EBSStatus;

/**
 * 云硬盘实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-21 下午03:17:50
 */
public class EBSInstanceInfo {

    private String caseId;

    private String ebsId;

    private String ebsName;

    private String orderId;

    private String itemId;

    private String standardId;

    private String acceptTime;

    private String resPoolId;

    private String resPoolPartId;

    private EBSStatus status;

    private String hostId;

    private String description;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    private String expireTime;

    private String diskSize;

    private String resourceType;

    /**
     * 业务ID
     */
    private String appId;
    
    private String ebsBossOrderId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getEbsId() {
        return ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    public String getEbsName() {
        return ebsName;
    }

    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
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

    public EBSStatus getStatus() {
        return status;
    }

    public void setStatus(EBSStatus status) {
        this.status = status;
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

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("实例ID为：").append(this.caseId).append("\n");
        sb.append("云硬盘编号为：").append(this.ebsId).append("\n");
        sb.append("云硬盘名称为：").append(this.ebsName).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("云硬盘被接受时间为：").append(this.acceptTime).append("\n");
        sb.append("云硬盘规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟订单编号为：").append(this.orderId).append("\n");
        sb.append("虚拟云硬盘受理时间为：").append(this.acceptTime).append("\n");
        sb.append("云硬盘状态为：").append(this.status).append("\n");
        sb.append("云硬盘备注信息为：").append(this.description).append("\n");
        sb.append("云硬盘创建时间为：").append(this.createTime).append("\n");
        sb.append("云硬盘创建人为：").append(this.createUser).append("\n");
        sb.append("云硬盘更新时间为：").append(this.updateTime).append("\n");
        sb.append("云硬盘更新人为：").append(this.updateUser).append("\n");
        sb.append("云硬盘到期时间为：").append(this.expireTime).append("\n");
        return sb.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    /**
     * 获取diskSize字段数据
     * @return Returns the diskSize.
     */
    public String getDiskSize() {
        return diskSize;
    }

    /**
     * 设置diskSize字段数据
     * @param diskSize The diskSize to set.
     */
    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

	public String getEbsBossOrderId() {
		return ebsBossOrderId;
	}

	public void setEbsBossOrderId(String ebsBossOrderId) {
		this.ebsBossOrderId = ebsBossOrderId;
	}

    
}
