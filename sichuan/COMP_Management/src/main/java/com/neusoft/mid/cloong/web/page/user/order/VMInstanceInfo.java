/**
 * 
 */
package com.neusoft.mid.cloong.web.page.user.order;

import java.util.List;

import com.neusoft.mid.cloong.vm.core.VMStatus;

/**
 * 
 * 虚拟机实例信息
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2014-2-13 下午05:19:42
 */
public class VMInstanceInfo {

	/**
	 * 实例ID
	 */
	private String caseId;

	/**
	 * pm id
	 */
	private String pmId;
	/**
	 * pm name
	 */
	private String pmName;
	/**
	 * VM id
	 */
	private String vmId;

	/**
	 * VM 名称
	 */
	private String vmName;

	/**
	 * cpu 数量
	 */
	private String cpuNum;

	/**
	 * 内存大小
	 */
	private String ramSize;

	/**
	 * 磁盘大小
	 */
	private String discSize;

	/**
	 * 镜像ID
	 */
	private String isoId;

	/**
	 * 镜像名称
	 */
	private String isoName;

	/**
	 * 镜像描述
	 */
	private String isoDescription;

	/**
	 * 资源池ID
	 */
	private String resPoolId;

	/**
	 * 资源池名称
	 */
	private String resPoolName;

	/**
	 * 资源池分区ID
	 */
	private String resPoolPartId;

	/**
	 * 资源池分区名称
	 */
	private String resPoolPartName;

	/**
	 * VM 密码
	 */
	private String vmPassword;

	/**
	 * ip
	 */
	private String privateIP;

	/**
	 * 状态
	 */
	private VMStatus status;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 更新人
	 */
	private String updateUser;

	/**
	 * 拥有人
	 */
	private String ownUser;

	/**
	 * 到期时间
	 */
	private String expireTime;

	/**
	 * 子网
	 */
	private String subNetwork;

	/**
	 * serverId
	 */
	private String serverId;

	/**
	 * 接收时间
	 */
	private String acceptTime;

	/**
	 * 规格id
	 */
	private String standardId;

	/**
	 * 克隆时，备份ID
	 */
	private String vmBackupId;

	/**
	 * 时长
	 */
	private int hours;

	/**
	 * 条目名称
	 */
	private String itemName;

	/**
	 * 条目ID
	 */
	private String itemId;

	/**
	 * 修改增加的磁盘容量
	 */
	private String discSizeAdd;

	/**
	 * 网卡列表
	 */
	private List<NetInfo> netList;

	/**
	 * 创建方式 0使用模板 1用户自定义
	 */
	private String paramFlag;

	/**
	 * 最大时间
	 */
	private String maxTime;

	/**
	 * 业务ID
	 */
	private String appId;

	/**
	 * 业务名称
	 */
	private String appName;

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 计费时长.
	 */
	private String lengthTime;
	
	/**
	 * 计费方式. m表示包月，h表示小时.
	 */
	private String lengthUnit;

	/**
	 * 计费用户ID.
	 */
	private String chargesUserId;
	
	/**
	 * 用户访问虚拟机进行管理和操作的URL
	 */
	private String operationURL;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDiscSizeAdd() {
		return discSizeAdd;
	}

	public void setDiscSizeAdd(String discSizeAdd) {
		this.discSizeAdd = discSizeAdd;
	}

	/**
	 * 获取vmBackupId字段数据
	 * 
	 * @return Returns the vmBackupId.
	 */
	public String getVmBackupId() {
		return vmBackupId;
	}

	/**
	 * 设置vmBackupId字段数据
	 * 
	 * @param vmBackupId
	 *            The vmBackupId to set.
	 */
	public void setVmBackupId(String vmBackupId) {
		this.vmBackupId = vmBackupId;
	}

	/**
	 * 
	 * getItemName TODO 方法
	 * 
	 * @return TODO
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 
	 * setItemName TODO 方法
	 * 
	 * @param itemName
	 *            TODO
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 
	 * getHours TODO 方法
	 * 
	 * @return TODO
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * 
	 * setHours TODO 方法
	 * 
	 * @param hours
	 *            TODO
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * 
	 * getCpuNum TODO 方法
	 * 
	 * @return TODO
	 */
	public String getCpuNum() {
		return cpuNum;
	}

	/**
	 * 
	 * setCpuNum TODO 方法
	 * 
	 * @param cpuNum
	 *            TODO
	 */
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	/**
	 * 
	 * getVmName TODO 方法
	 * 
	 * @return TODO
	 */
	public String getVmName() {
		return vmName;
	}

	/**
	 * 
	 * setVmName TODO 方法
	 * 
	 * @param vmName
	 *            TODO
	 */
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * 
	 * getRamSize TODO 方法
	 * 
	 * @return TODO
	 */
	public String getRamSize() {
		return ramSize;
	}

	/**
	 * 
	 * setRamSize TODO 方法
	 * 
	 * @param ramSize
	 *            TODO
	 */
	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	/**
	 * 
	 * getDiscSize TODO 方法
	 * 
	 * @return TODO
	 */
	public String getDiscSize() {
		return discSize;
	}

	/**
	 * 
	 * setDiscSize TODO 方法
	 * 
	 * @param discSize
	 *            TODO
	 */
	public void setDiscSize(String discSize) {
		this.discSize = discSize;
	}

	/**
	 * 
	 * getIsoId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getIsoId() {
		return isoId;
	}

	/**
	 * 
	 * setIsoId TODO 方法
	 * 
	 * @param isoId
	 *            TODO
	 */
	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	/**
	 * 
	 * getResPoolId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * 
	 * setResPoolId TODO 方法
	 * 
	 * @param resPoolId
	 *            TODO
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * 
	 * getResPoolPartId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getResPoolPartId() {
		return resPoolPartId;
	}

	/**
	 * 
	 * setResPoolPartId TODO 方法
	 * 
	 * @param resPoolPartId
	 *            TODO
	 */
	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	/**
	 * 
	 * getPrivateIP TODO 方法
	 * 
	 * @return TODO
	 */
	public String getPrivateIP() {
		return privateIP;
	}

	/**
	 * 
	 * setPrivateIP TODO 方法
	 * 
	 * @param privateIP
	 *            TODO
	 */
	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
	}

	/**
	 * 
	 * getDescription TODO 方法
	 * 
	 * @return TODO
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * setDescription TODO 方法
	 * 
	 * @param description
	 *            TODO
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * getOrderId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 
	 * setOrderId TODO 方法
	 * 
	 * @param orderId
	 *            TODO
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 * getCreateTime TODO 方法
	 * 
	 * @return TODO
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * setCreateTime TODO 方法
	 * 
	 * @param createTime
	 *            TODO
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * getOwnUser TODO 方法
	 * 
	 * @return TODO
	 */
	public String getOwnUser() {
		return ownUser;
	}

	/**
	 * 
	 * setOwnUser TODO 方法
	 * 
	 * @param ownUser
	 *            TODO
	 */
	public void setOwnUser(String ownUser) {
		this.ownUser = ownUser;
	}

	/**
	 * 
	 * getExpireTime TODO 方法
	 * 
	 * @return TODO
	 */
	public String getExpireTime() {
		return expireTime;
	}

	/**
	 * 
	 * setExpireTime TODO 方法
	 * 
	 * @param expireTime
	 *            TODO
	 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * 
	 * getStatus TODO 方法
	 * 
	 * @return TODO
	 */
	public VMStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * setStatus TODO 方法
	 * 
	 * @param status
	 *            TODO
	 */
	public void setStatus(VMStatus status) {
		this.status = status;
	}

	/**
	 * 
	 * getCaseId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * 
	 * setCaseId TODO 方法
	 * 
	 * @param caseId
	 *            TODO
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * 
	 * getCreateUser TODO 方法
	 * 
	 * @return TODO
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 
	 * setCreateUser TODO 方法
	 * 
	 * @param createUser
	 *            TODO
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 
	 * getUpdateTime TODO 方法
	 * 
	 * @return TODO
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 
	 * setUpdateTime TODO 方法
	 * 
	 * @param updateTime
	 *            TODO
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 
	 * getUpdateUser TODO 方法
	 * 
	 * @return TODO
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 
	 * setUpdateUser TODO 方法
	 * 
	 * @param updateUser
	 *            TODO
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 
	 * getResPoolName TODO 方法
	 * 
	 * @return TODO
	 */
	public String getResPoolName() {
		return resPoolName;
	}

	/**
	 * 
	 * setResPoolName TODO 方法
	 * 
	 * @param resPoolName
	 *            TODO
	 */
	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
	}

	/**
	 * 
	 * getResPoolPartName TODO 方法
	 * 
	 * @return TODO
	 */
	public String getResPoolPartName() {
		return resPoolPartName;
	}

	/**
	 * 
	 * setResPoolPartName TODO 方法
	 * 
	 * @param resPoolPartName
	 *            TODO
	 */
	public void setResPoolPartName(String resPoolPartName) {
		this.resPoolPartName = resPoolPartName;
	}

	/**
	 * 
	 * getIsoName TODO 方法
	 * 
	 * @return TODO
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * 
	 * setIsoName TODO 方法
	 * 
	 * @param isoName
	 *            TODO
	 */
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	/**
	 * 
	 * getIsoDescription TODO 方法
	 * 
	 * @return TODO
	 */
	public String getIsoDescription() {
		return isoDescription;
	}

	/**
	 * 
	 * setIsoDescription TODO 方法
	 * 
	 * @param isoDescription
	 *            TODO
	 */
	public void setIsoDescription(String isoDescription) {
		this.isoDescription = isoDescription;
	}

	/**
	 * 
	 * getVmId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getVmId() {
		return vmId;
	}

	/**
	 * 
	 * setVmId TODO 方法
	 * 
	 * @param vmId
	 *            TODO
	 */
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	/**
	 * 
	 * getVmPassword TODO 方法
	 * 
	 * @return TODO
	 */
	public String getVmPassword() {
		return vmPassword;
	}

	/**
	 * 
	 * setVmPassword TODO 方法
	 * 
	 * @param vmPassword
	 *            TODO
	 */
	public void setVmPassword(String vmPassword) {
		this.vmPassword = vmPassword;
	}

	/**
	 * 
	 * getSubNetwork TODO 方法
	 * 
	 * @return TODO
	 */
	public String getSubNetwork() {
		return subNetwork;
	}

	/**
	 * 
	 * setSubNetwork TODO 方法
	 * 
	 * @param subNetwork
	 *            TODO
	 */
	public void setSubNetwork(String subNetwork) {
		this.subNetwork = subNetwork;
	}

	/**
	 * 
	 * getServerId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * 
	 * setServerId TODO 方法
	 * 
	 * @param serverId
	 *            TODO
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * 
	 * getAcceptTime TODO 方法
	 * 
	 * @return TODO
	 */
	public String getAcceptTime() {
		return acceptTime;
	}

	/**
	 * 
	 * setAcceptTime TODO 方法
	 * 
	 * @param acceptTime
	 *            TODO
	 */
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	/**
	 * 
	 * getStandardId TODO 方法
	 * 
	 * @return TODO
	 */
	public String getStandardId() {
		return standardId;
	}

	/**
	 * 
	 * setStandardId TODO 方法
	 * 
	 * @param standardId
	 *            TODO
	 */
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	/**
	 * 
	 * toString 虚拟机信息
	 * 
	 * @return 虚拟机信息
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("虚拟机编号为：").append(this.vmId).append("\n");
		sb.append("虚拟机名称为：").append(this.vmName).append("\n");
		sb.append("虚拟机CPU为：").append(this.cpuNum).append("\n");
		sb.append("虚拟机内存为：").append(this.ramSize).append("\n");
		sb.append("虚拟机存储空间为：").append(this.discSize).append("\n");
		sb.append("虚拟机系统类型为：").append(this.isoId).append("\n");
		sb.append("虚拟机系统名称为：").append(this.isoName).append("\n");
		sb.append("虚拟机系统描述为：").append(this.isoDescription).append("\n");
		sb.append("资源池ID为：").append(this.resPoolId).append("\n");
		sb.append("资源池名称为：").append(this.resPoolName).append("\n");
		sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
		sb.append("资源池分区名称为：").append(this.resPoolPartName).append("\n");
		sb.append("用户口令：").append(this.vmPassword).append("\n");
		sb.append("虚拟机内网IP为：").append(this.privateIP).append("\n");
		sb.append("虚拟机子网为：").append(this.subNetwork).append("\n");
		sb.append("虚拟机所在物理机编号为：").append(this.serverId).append("\n");
		sb.append("虚拟机被接受时间为：").append(this.acceptTime).append("\n");
		sb.append("虚拟机规格ID为：").append(this.standardId).append("\n");
		sb.append("虚拟机内网IP为：").append(this.privateIP).append("\n");
		sb.append("虚拟机状态为：").append(this.status).append("\n");
		sb.append("虚拟机备注信息为：").append(this.description).append("\n");
		sb.append("虚拟机订单变化为：").append(this.orderId).append("\n");
		sb.append("虚拟机创建时间为：").append(this.createTime).append("\n");
		sb.append("虚拟机到期时间为：").append(this.expireTime).append("\n");
		sb.append("虚拟机时长：").append(this.hours).append("\n");
		sb.append("虚拟机所有者").append(this.ownUser).append("\n");
		return sb.toString();
	}

	/**
	 * @return the netList
	 */
	public List<NetInfo> getNetList() {
		return netList;
	}

	/**
	 * @param netList
	 *            the netList to set
	 */
	public void setNetList(List<NetInfo> netList) {
		this.netList = netList;
	}

	/**
	 * @return the paramFlag
	 */
	public String getParamFlag() {
		return paramFlag;
	}

	/**
	 * @param paramFlag
	 *            the paramFlag to set
	 */
	public void setParamFlag(String paramFlag) {
		this.paramFlag = paramFlag;
	}

	/**
	 * @return the maxTime
	 */
	public String getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime
	 *            the maxTime to set
	 */
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the operationURL
	 */
	public String getOperationURL() {
		return operationURL;
	}

	/**
	 * @param operationURL
	 *            the operationURL to set
	 */
	public void setOperationURL(String operationURL) {
		this.operationURL = operationURL;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	/**
	 * @return the pmName
	 */
	public String getPmName() {
		return pmName;
	}

	/**
	 * @param pmName the pmName to set
	 */
	public void setPmName(String pmName) {
		this.pmName = pmName;
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

}
