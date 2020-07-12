/*******************************************************************************
 * @(#)OrderAuditInfo.java 2014-1-28
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

/**订单审批记录表实体
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-28 下午04:00:53
 */
public class OrderAuditInfo {
	
	private String orderId;  //ORDER_ID 订单id
	private String status;   //STATUS    审批状态 1通过 0 不通过
	private String auditTime; //AUDIT_TIME 审批时间
	private String auditUser; //AUDIT_USER  审批人
	private String auditInfo; //AUDIT_INFO  审批意见
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}
	
}
