package com.neusoft.mid.comp.boss.server.ws.msg.OrderResource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelCorporationInfoReqInput", propOrder = { "corpId", "operateType", "message" })
public class CancelCorporationInfoReq {

	@XmlElement(name = "corpId", required = true)
	public String corpId;

	@XmlElement(name = "operateType", required = true)
	public String operateType;

	@XmlElement(name = "message", required = false)
	public String message;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
