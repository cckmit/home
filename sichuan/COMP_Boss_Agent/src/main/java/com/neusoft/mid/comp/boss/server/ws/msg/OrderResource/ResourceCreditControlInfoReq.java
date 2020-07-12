package com.neusoft.mid.comp.boss.server.ws.msg.OrderResource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceCreditControlInfoReqType", propOrder = { "productOrderNumber", "operateType", "reasonType" })
public class ResourceCreditControlInfoReq {

	@XmlElement(name = "productOrderNumber", required = true)
	public String productOrderNumber;
	
	@XmlElement(name = "operateType", required = true)
	public String operateType;

	@XmlElement(name = "reasonType", required = false)
	public String reasonType;

	public String getProductOrderNumber() {
		return productOrderNumber;
	}

	public void setProductOrderNumber(String productOrderNumber) {
		this.productOrderNumber = productOrderNumber;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

}
