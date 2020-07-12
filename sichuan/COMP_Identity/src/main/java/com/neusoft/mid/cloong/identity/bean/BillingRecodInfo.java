package com.neusoft.mid.cloong.identity.bean;

 /**
  * 计费话单实例.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0    2016年10月19日   下午2:48:51
 */
public class BillingRecodInfo {
	
	/**
	 * 主键ID.
	 */
	 private String id;
	 
	 /**
	  * 用户计费ID.
	  */
	 private String chargesUserId;
	
	 /**
	  * 资源ID.
	  */
	 private String resourceId;
	 
	 /**
	  * 计费方式.
	  */
	 private String lengthUnit;
	 /**
	  * 计费时长.
	  */
	 private String lengthTime;
	 
	 /**
	  * 价格.
	  */
	 private String price;
	 
	 /**
	  * 资源实例类型:0虚拟机,5虚拟硬盘
	  */
	 private String resourceType;
	 
	 /**
	  * 资费主键.
	  */
	 private String chargesId;
	 
	 /**
	  * 用户ID.
	  */
	 private String userId;
	 
	 /**
	  * 创建时间.
	  */
	 private String createTime;
	 
	 /**
	  * 同步时间.
	  */
	 private String synchTime;

	 /**
	  * 订单状态,0表示为同步，1表示已同步.
	  */
	 private String billingStatus;
	 
	 /**
	  * cpu核数.
	  */
	 private String cpu;
	 
	 /**
	  * 内存大小.
	  */
	 private String memory;
	 
	 /**
	  * 硬盘大小.
	  */
	 private String diskSize;
	 
	/**
	 * @return the billingStatus
	 */
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 * @param billingStatus the billingStatus to set
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the chargesUserId
	 */
	public String getChargesUserId() {
		return chargesUserId;
	}

	/**
	 * @param chargesUserId the chargesUserId to set
	 */
	public void setChargesUserId(String chargesUserId) {
		this.chargesUserId = chargesUserId;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the lengthUnit
	 */
	public String getLengthUnit() {
		return lengthUnit;
	}

	/**
	 * @param lengthUnit the lengthUnit to set
	 */
	public void setLengthUnit(String lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	/**
	 * @return the lengthTime
	 */
	public String getLengthTime() {
		return lengthTime;
	}

	/**
	 * @param lengthTime the lengthTime to set
	 */
	public void setLengthTime(String lengthTime) {
		this.lengthTime = lengthTime;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the chargesId
	 */
	public String getChargesId() {
		return chargesId;
	}

	/**
	 * @param chargesId the chargesId to set
	 */
	public void setChargesId(String chargesId) {
		this.chargesId = chargesId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the synchTime
	 */
	public String getSynchTime() {
		return synchTime;
	}

	/**
	 * @param synchTime the synchTime to set
	 */
	public void setSynchTime(String synchTime) {
		this.synchTime = synchTime;
	}

	/**
	 * @return the cpu
	 */
	public String getCpu() {
		return cpu;
	}

	/**
	 * @param cpu the cpu to set
	 */
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	/**
	 * @return the memory
	 */
	public String getMemory() {
		return memory;
	}

	/**
	 * @param memory the memory to set
	 */
	public void setMemory(String memory) {
		this.memory = memory;
	}

	/**
	 * @return the diskSize
	 */
	public String getDiskSize() {
		return diskSize;
	}

	/**
	 * @param diskSize the diskSize to set
	 */
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
	 
}
