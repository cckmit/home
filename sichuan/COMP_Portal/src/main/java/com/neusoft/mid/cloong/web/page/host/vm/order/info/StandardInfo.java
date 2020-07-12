package com.neusoft.mid.cloong.web.page.host.vm.order.info;

import java.io.Serializable;

/**
 * 规格信息
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-18 下午04:36:49
 */
public class StandardInfo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	private String standardId;

	private String templateId;

	private String cpuNum;

	private String ramSize;

	private String discSize;

	private String resourceType;

	private String description;

	private String createTime;

	private String createUser;

	private String updateTime;

	private String updateUser;

	private String hourPrice;

	private String monthPrice;

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	public String getRamSize() {
		return ramSize;
	}

	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	public String getDiscSize() {
		return discSize;
	}

	public void setDiscSize(String discSize) {
		this.discSize = discSize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the hourPrice
	 */
	public String getHourPrice() {
		return hourPrice;
	}

	/**
	 * @param hourPrice
	 *            the hourPrice to set
	 */
	public void setHourPrice(String hourPrice) {
		this.hourPrice = hourPrice;
	}

	/**
	 * @return the monthPrice
	 */
	public String getMonthPrice() {
		return monthPrice;
	}

	/**
	 * @param monthPrice
	 *            the monthPrice to set
	 */
	public void setMonthPrice(String monthPrice) {
		this.monthPrice = monthPrice;
	}

}
