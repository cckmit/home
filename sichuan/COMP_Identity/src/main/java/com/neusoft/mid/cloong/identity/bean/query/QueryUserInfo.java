/*******************************************************************************
 * @(#)QueryUserInfo.java 2014
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean.query;

import com.neusoft.mid.cloong.identity.bean.UserBean;

/**
 * 用户信息查询条件Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014-1-10 下午2:50:08
 */
public class QueryUserInfo extends UserBean {

    /**
     * 要查询的用户信息状态
     */
    private String queryStatus;

    /**
     * 创建在此日期之前的用户...
     */
    private String beforeCreateTime;

    /**
     * 创建在此日期之后的用户...
     */
    private String afterCreateTime;
    
    /**
     * 角色ID.
     * @author zhangyunfeng
     */
    private String roleId;
    
    /**
     * 用户绑定业务的状态.1:业务待审;2审批通过,3审批部通过.
     */
    private String appBindStatus;
    
    /**
     * 获取beforeCreateTime字段数据
     * @return Returns the beforeCreateTime.
     */
    public String getBeforeCreateTime() {
        return beforeCreateTime;
    }

    /**
     * 设置beforeCreateTime字段数据
     * @param beforeCreateTime The beforeCreateTime to set.
     */
    public void setBeforeCreateTime(String beforeCreateTime) {
        this.beforeCreateTime = beforeCreateTime;
    }

    /**
     * 获取afterCreateTime字段数据
     * @return Returns the afterCreateTime.
     */
    public String getAfterCreateTime() {
        return afterCreateTime;
    }

    /**
     * 设置afterCreateTime字段数据
     * @param afterCreateTime The afterCreateTime to set.
     */
    public void setAfterCreateTime(String afterCreateTime) {
        this.afterCreateTime = afterCreateTime;
    }

    /**
     * 获取queryStatus字段数据
     * @return Returns the queryStatus.
     */
    public String getQueryStatus() {
        return queryStatus;
    }

    /**
     * 设置queryStatus字段数据
     * @param queryStatus The queryStatus to set.
     */
    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the appBindStatus
	 */
	public String getAppBindStatus() {
		return appBindStatus;
	}

	/**
	 * @param appBindStatus the appBindStatus to set
	 */
	public void setAppBindStatus(String appBindStatus) {
		this.appBindStatus = appBindStatus;
	}

}
