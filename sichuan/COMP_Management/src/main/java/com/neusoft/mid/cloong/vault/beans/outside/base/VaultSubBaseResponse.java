package com.neusoft.mid.cloong.vault.beans.outside.base;

import com.neusoft.mid.cloong.vault.beans.outside.enums.ApplyStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class VaultSubBaseResponse  extends VaultBaseResponse{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对于本次申请操作的id用于区分多个申请
	 */
	@XmlElement(required = true)
	private String requestID;

	/**
	 * 表示该申请必须在以上时间前完成审批和认证操作
	 */
	@XmlElement(required = true)
	private String authEndTime;

	/**
	 * 对于本次敏感操作，目前金库认证的状态
	 */
	@XmlElement(required = true)
	private ApplyStatus applyStatus;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}


	public String getAuthEndTime() {
		return authEndTime;
	}

	public void setAuthEndTime(String authEndTime) {
		this.authEndTime = authEndTime;
	}

	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}


}
