/**
 * 
 */
package com.neusoft.mid.comp.boss.server.ws.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Administrator
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderResourceInfoReq.CorporationInfoType", propOrder = { "corpId", "corpName", "phone", "email", "address", "telephone", "departmentName", "description" })
public class CorporationInfo {

	@XmlElement(name = "corpId", required = true)
	public String corpId;

	@XmlElement(name = "corpName", required = true)
	public String corpName;

	@XmlElement(name = "phone", required = true)
	public String phone;

	@XmlElement(name = "email", required = true)
	public String email;

	@XmlElement(name = "address", required = false)
	public String address;

	@XmlElement(name = "telephone", required = false)
	public String telephone;

	@XmlElement(name = "departmentName", required = false)
	public String departmentName;

	@XmlElement(name = "description", required = false)
	public String description;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
