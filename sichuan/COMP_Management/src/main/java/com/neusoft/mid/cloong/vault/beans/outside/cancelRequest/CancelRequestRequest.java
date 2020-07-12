package com.neusoft.mid.cloong.vault.beans.outside.cancelRequest;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cancelParam")
public class CancelRequestRequest extends VaultBaseRequest {
	/**
	 * 申请操作返回的id
	 */
	@XmlElement(required = true)
	private String requestID;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

}
