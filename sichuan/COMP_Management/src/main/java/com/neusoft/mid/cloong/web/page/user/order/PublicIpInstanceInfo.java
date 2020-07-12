package com.neusoft.mid.cloong.web.page.user.order;

/**
 * 公有ip实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-21 下午03:17:50
 */
public class PublicIpInstanceInfo {

    private String caseId;

    private String orderId;

  private String resPoolId;
    
    private String resPoolName;
    
    private String resPoolPartId;
    
    private String resPoolPartName;

    private PublicIpStatus status;

    private String hostId;

    private String description;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    private String expireTime;

    private String resourceType;
    
    private String publicIp;

    private String appId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public PublicIpStatus getStatus() {
        return status;
    }

    public void setStatus(PublicIpStatus status) {
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
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("虚拟订单编号为：").append(this.orderId).append("\n");
        sb.append("状态为：").append(this.status).append("\n");
        sb.append("备注信息为：").append(this.description).append("\n");
        sb.append("创建时间为：").append(this.createTime).append("\n");
        sb.append("创建人为：").append(this.createUser).append("\n");
        sb.append("更新时间为：").append(this.updateTime).append("\n");
        sb.append("更新人为：").append(this.updateUser).append("\n");
        sb.append("到期时间为：").append(this.expireTime).append("\n");
        return sb.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
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

}
