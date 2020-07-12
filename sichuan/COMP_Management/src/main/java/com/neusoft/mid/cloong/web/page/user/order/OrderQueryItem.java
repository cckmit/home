/*******************************************************************************
 * @(#)OrderQueryItem.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;


/**用户订单查询条件
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-25 下午03:49:54
 */
public class OrderQueryItem {

    /**
     * 订单状态
     */
    private String status;

    /**
     * 资源类型
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
     * 业务名称
     */
    private String appName;
    
    /**
     * 申请人
     */
    private String updateUser;
    
    /**
     * 获取orderId字段数据
     * @return Returns the orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId字段数据
     * @param orderId The orderId to set.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取parentId字段数据
     * @return Returns the parentId.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置parentId字段数据
     * @param parentId The parentId to set.
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * getStartTime 方法 
     * @return 方法
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * setStartTime 方法 
     * @param startTime 方法
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * getEndTime 方法 
     * @return 方法
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * setEndTime 方法 
     * @param endTime 方法
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the caseType
	 */
	public String getCaseType() {
		return caseType;
	}

	/**
	 * @param caseType the caseType to set
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
}
