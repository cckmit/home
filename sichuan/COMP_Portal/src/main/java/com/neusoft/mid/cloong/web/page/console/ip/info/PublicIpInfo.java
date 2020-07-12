/*******************************************************************************
 * @(#)PublicIpInfo.java 2018年4月27日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.ip.info;

import java.util.List;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年4月27日 下午4:28:43
 */
public class PublicIpInfo {
    
    //实例id
    private String caseId;
    
    //公网ip
    private String publicIp;
    
    //状态0：待创建1：创建中2：已创建3：创建失败4：状态异常5:已删除
    private String status;
    
    //订单id
    private String orderId;
    
    //业务id
    private String appId;
    
    //业务名称
    private String appName;
    
    //资源池id
    private String resPoolId;
 
    //资源池名称
    private String resPoolName;
    
    //分区id
    private String resPoolPartId;
 
    //分区名称
    private String resPoolPartName;
    
    //主机id
    private String hostId;
    
    //主机名称
    private String hostName;
    
    //描述
    private String description;
    
    //创建时间
    private String createTime;
    
    //创建人
    private String createUser;
    
    //更新时间
    private String updateTime;
    
    //更新人
    private String updateUser;
    
    private String resourceType;
    
    private String expireTime;
    
    /**
     * 当前用户绑定的业务ID
     */
    private List<String> appIdList;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAppIdList() {
        return appIdList;
    }

    public void setAppIdList(List<String> appIdList) {
        this.appIdList = appIdList;
    }

}
