package com.neusoft.mid.comp.boss.server.ws.msg.OrderResource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.neusoft.mid.comp.boss.server.ws.msg.CorporationInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderResourceInfoReqType", propOrder = { "corporationInfo", "productOrderNumber", "productType" })
public class OrderResourceInfoReq {

	@XmlElement(name = "corporationInfo", required = true)
	public CorporationInfo corporationInfo;

	@XmlElement(name = "productOrderNumber", required = true)
	public String productOrderNumber;

	@XmlElement(name = "productType", required = true)
	public String productType;

	public CorporationInfo getCorporationInfo() {
		return corporationInfo;
	}

	public void setCorporationInfo(CorporationInfo corporationInfo) {
		this.corporationInfo = corporationInfo;
	}

	public String getProductOrderNumber() {
		return productOrderNumber;
	}

	public void setProductOrderNumber(String productOrderNumber) {
		this.productOrderNumber = productOrderNumber;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}


}
