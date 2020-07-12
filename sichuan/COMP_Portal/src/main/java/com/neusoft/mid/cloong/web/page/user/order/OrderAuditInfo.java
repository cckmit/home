/*******************************************************************************
 * @(#)OrderAuditInfo.java 2014-1-28
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.io.Serializable;

/**订单审批记录表实体
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-28 下午04:00:53
 */
public class OrderAuditInfo implements Serializable{
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * ORDER_ID 订单id
     */
	private String orderId;  
	/**
	 * STATUS    审批状态 1通过 0 不通过
	 */
	private String status;
	/**
	 * AUDIT_TIME 审批时间
	 */
	private String auditTime; 
	/**
	 * AUDIT_USER  审批人
	 */
	private String auditUser; 
	/**
	 * AUDIT_INFO  审批意见
	 */
	private String auditInfo; 
	
	/**
	 * 
	 * getOrderId TODO 方法
	 * @return TODO
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 
	 * setOrderId TODO 方法
	 * @param orderId TODO
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 
	 * getStatus TODO 方法
	 * @return TODO
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * setStatus TODO 方法
	 * @param status TODO
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * getAuditTime TODO 方法
	 * @return TODO
	 */
	public String getAuditTime() {
		return auditTime;
	}
	/**
	 * 
	 * setAuditTime TODO 方法
	 * @param auditTime TODO
	 */
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 
	 * getAuditUser TODO 方法
	 * @return TODO
	 */
	public String getAuditUser() {
		return auditUser;
	}
	/**
	 * 
	 * setAuditUser TODO 方法
	 * @param auditUser TODO
	 */
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	/**
	 * 
	 * getAuditInfo TODO 方法
	 * @return TODO
	 */
	public String getAuditInfo() {
		return auditInfo;
	}
	/**
	 * 
	 * setAuditInfo TODO 方法
	 * @param auditInfo TODO
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}
	
}
