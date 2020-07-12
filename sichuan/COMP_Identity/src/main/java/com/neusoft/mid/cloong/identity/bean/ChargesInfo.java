
package com.neusoft.mid.cloong.identity.bean;

 /**
  * 资费实体类.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月10日   下午4:29:54
 */
public class ChargesInfo {
	
	/**
	 * 主键ID.
	 */
	private String id;
	
	/**
	 * 按小时收费价格.
	 */
	private String hourPrice;
	
	/**
	 * 包月收费价格.
	 */
	private String monthPrice;
	
	/**
	 * cpu个数.
	 */
	private String cpuNumber;
	
	/**
	 * 内容大小,单位是GB.
	 */
	private String memorySize;
	
	/**
	 * 计费类型:0表示虚拟机(云主机),1表示云硬盘.
	 */
	private String chargesType;
	
	/**
	 * 创建时间.
	 */
	private String createTime;
	
	/**
	 * 更新时间.
	 */
	private String updateTime;
	
	/**
	 * 创建人.
	 */
	private String createUser;
	
	/**
	 * 描述信息.
	 */
	private String desc;
	
	/**
	 * 结束时间,用户查询时使用.
	 */
	private String endTime;
	
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
	 * @return the hourPrice
	 */
	public String getHourPrice() {
		return hourPrice;
	}

	/**
	 * @param hourPrice the hourPrice to set
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
	 * @param monthPrice the monthPrice to set
	 */
	public void setMonthPrice(String monthPrice) {
		this.monthPrice = monthPrice;
	}

	/**
	 * @return the cpuNumber
	 */
	public String getCpuNumber() {
		return cpuNumber;
	}

	/**
	 * @param cpuNumber the cpuNumber to set
	 */
	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	

	/**
	 * @return the memorySize
	 */
	public String getMemorySize() {
		return memorySize;
	}

	/**
	 * @param memorySize the memorySize to set
	 */
	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	/**
	 * @return the chargesType
	 */
	public String getChargesType() {
		return chargesType;
	}

	/**
	 * @param chargesType the chargesType to set
	 */
	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
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
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
