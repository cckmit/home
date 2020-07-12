/*******************************************************************************
 * @(#)FileStoreInstanceInfo.java 2018年5月5日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import com.neusoft.mid.cloong.filestorage.FileStoreStatus;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月5日 下午1:38:22
 */
public class FileStoreInstanceInfo {

    private String caseId;

    private String orderId;

    private String fsTemplateId;

    private String fsQuotaSize;

    private String fsShareType;

    private String fsAdminUser;

    private String fsAdminPassword;

    private String fsUrl;

    private String resPoolId;
    
    private String resPoolName;
    
    private String resPoolPartId;
    
    private String resPoolPartName;

    private FileStoreStatus status;

    private String fsId;

    private String fsName;

    private String createType;

    private String description;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    private String expireTime;

    private String resourceType;

    private String appId;
    
    private String appName;

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

    public String getFsTemplateId() {
        return fsTemplateId;
    }

    public void setFsTemplateId(String fsTemplateId) {
        this.fsTemplateId = fsTemplateId;
    }

    public String getFsQuotaSize() {
        return fsQuotaSize;
    }

    public void setFsQuotaSize(String fsQuotaSize) {
        this.fsQuotaSize = fsQuotaSize;
    }


    public String getFsAdminUser() {
        return fsAdminUser;
    }

    public void setFsAdminUser(String fsAdminUser) {
        this.fsAdminUser = fsAdminUser;
    }

    public String getFsAdminPassword() {
        return fsAdminPassword;
    }

    public void setFsAdminPassword(String fsAdminPassword) {
        this.fsAdminPassword = fsAdminPassword;
    }

    public String getFsUrl() {
        return fsUrl;
    }

    public void setFsUrl(String fsUrl) {
        this.fsUrl = fsUrl;
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

    public FileStoreStatus getStatus() {
        return status;
    }

    public void setStatus(FileStoreStatus status) {
        this.status = status;
    }

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }

    public String getFsName() {
        return fsName;
    }

    public void setFsName(String fsName) {
        this.fsName = fsName;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFsShareType() {
        return fsShareType;
    }

    public void setFsShareType(String fsShareType) {
        this.fsShareType = fsShareType;
    }

}
