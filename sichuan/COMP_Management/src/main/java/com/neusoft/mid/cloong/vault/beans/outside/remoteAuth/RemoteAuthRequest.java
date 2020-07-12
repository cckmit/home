package com.neusoft.mid.cloong.vault.beans.outside.remoteAuth;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "authParam")
public class RemoteAuthRequest extends VaultBaseRequest {
	/**
	 * 申请操作的id用于区分多个申请
	 */
	@XmlElement(required = true)
	private String requestID;

	/**
	 * 用户手机收到的金库短信口令
	 */
	@XmlElement(required = true)
	private String passCode;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

}
