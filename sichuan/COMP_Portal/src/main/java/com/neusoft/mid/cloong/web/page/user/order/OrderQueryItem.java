/*******************************************************************************
 * @(#)OrderQueryItem.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.util.List;

/**
 * 用户订单查询条件
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-7 上午10:38:50
 */
public class OrderQueryItem {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单状态
     */
    private List<String> status;

    /**
     * 规格类型
     */
    private String caseType;

    /**
     * 从页面获取查询条件开始时间
     */
    private String startTime;

    /**
     * 从页面获取查询条件结束时间
     */
    private String endTime;

    /**
     * 子订单ID
     */
    private String orderId;

    /**
     * 父订单ID
     */
    private String parentId;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池名称
     */
    private String resPoolName;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * 申请人
     */
    private String createUser;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取caseType字段数据
     * @return Returns the caseType.
     */
    public String getCaseType() {
        return caseType;
    }

    /**
     * 设置caseType字段数据
     * @param caseType The caseType to set.
     */
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the resPoolId
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * @param resPoolId the resPoolId to set
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * @return the resPoolName
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * @param resPoolName the resPoolName to set
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

}
