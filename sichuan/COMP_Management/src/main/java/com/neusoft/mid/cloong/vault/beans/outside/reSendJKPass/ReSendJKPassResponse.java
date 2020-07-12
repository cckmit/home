package com.neusoft.mid.cloong.vault.beans.outside.reSendJKPass;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseResponse;
import com.neusoft.mid.cloong.vault.beans.outside.enums.ResendResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resendJKPass")
public class ReSendJKPassResponse extends VaultBaseResponse {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 需要重新获取金库口令的金库申请id
	 */
	@XmlElement(required = true)
	private String requestID;

	/**
	 * 口令重发结果
	 */
	@XmlElement(required = true)
	private ResendResult resendResult;



	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public ResendResult getResendResult() {
		return resendResult;
	}

	public void setResendResult(ResendResult resendResult) {
		this.resendResult = resendResult;
	}

}
