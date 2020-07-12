/**
 * 
 */
package com.neusoft.mid.cloong.web.page.user.order;

import java.util.List;

import com.neusoft.mid.cloong.pm.core.PMStatus;


/**
 * 物理机实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午07:52:44
 */
public class PMInstanceInfo {
    
    /**
     * 实例id
     */
    private String caseId;

    /**
     * PM ID
     */
    private String pmId;

    /**
     * PM 名字
     */
    private String pmName;

    /**
     * cpu 数量
     */
    private String cpuType;

    /**
     * 内存大小
     */
    private String ramSize;

    /**
     * 磁盘大小
     */
    private String discSize;

    /**
     * 服务类型
     */
    private String serverType;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池名字
     */
    private String resPoolName;

    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     *  资源池分区名字
     */
    private String resPoolPartName;

    /**
     * PM 密码
     */
    private String pmPassword;
    
    /**
     * PM 使用者
     */
    private String pmUser;

    /**
     * ip
     */
    private String ip;

    /**
     * 状态
     */
    private PMStatus status;

    /**
     * 描述
     */
    private String description;

    /**
     * 订单id
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
     * 拥有者
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
     * 
     */
    private String appName;

    
    /**
     * 创建方式 0使用模板 1用户自定义
     */
    private String paramFlag;
    
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
     * 业务ID
     */
    private String appId;
    
    
    /**
     * 最大时间
     */
    private String maxTime;
    
    /**
     * 网卡列表
     */
    private List<NetInfo> netList;
    
    private String operationIP;
    
    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取pmId字段数据
     * @return Returns the pmId.
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * 设置pmId字段数据
     * @param pmId The pmId to set.
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    /**
     * 获取pmName字段数据
     * @return Returns the pmName.
     */
    public String getPmName() {
        return pmName;
    }

    /**
     * 设置pmName字段数据
     * @param pmName The pmName to set.
     */
    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    /**
     * 获取ramSize字段数据
     * @return Returns the ramSize.
     */
    public String getRamSize() {
        return ramSize;
    }

    /**
     * 设置ramSize字段数据
     * @param ramSize The ramSize to set.
     */
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    /**
     * 获取discSize字段数据
     * @return Returns the discSize.
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * 设置discSize字段数据
     * @param discSize The discSize to set.
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    /**
     * 获取serverType字段数据
     * @return Returns the serverType.
     */
    public String getServerType() {
        return serverType;
    }

    /**
     * 设置serverType字段数据
     * @param serverType The serverType to set.
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    /**
     * 获取resPoolId字段数据
     * @return Returns the resPoolId.
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 设置resPoolId字段数据
     * @param resPoolId The resPoolId to set.
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 获取resPoolName字段数据
     * @return Returns the resPoolName.
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * 设置resPoolName字段数据
     * @param resPoolName The resPoolName to set.
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    /**
     * 获取resPoolPartId字段数据
     * @return Returns the resPoolPartId.
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 设置resPoolPartId字段数据
     * @param resPoolPartId The resPoolPartId to set.
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 获取resPoolPartName字段数据
     * @return Returns the resPoolPartName.
     */
    public String getResPoolPartName() {
        return resPoolPartName;
    }

    /**
     * 设置resPoolPartName字段数据
     * @param resPoolPartName The resPoolPartName to set.
     */
    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    /**
     * 获取pmPassword字段数据
     * @return Returns the pmPassword.
     */
    public String getPmPassword() {
        return pmPassword;
    }

    /**
     * 设置pmPassword字段数据
     * @param pmPassword The pmPassword to set.
     */
    public void setPmPassword(String pmPassword) {
        this.pmPassword = pmPassword;
    }

    /**
     * 获取pmUser字段数据
     * @return Returns the pmUser.
     */
    public String getPmUser() {
        return pmUser;
    }

    /**
     * 设置pmUser字段数据
     * @param pmUser The pmUser to set.
     */
    public void setPmUser(String pmUser) {
        this.pmUser = pmUser;
    }

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取status字段数据
     * @return Returns the status.
     */
    public PMStatus getStatus() {
        return status;
    }

    /**
     * 设置status字段数据
     * @param status The status to set.
     */
    public void setStatus(PMStatus status) {
        this.status = status;
    }

    /**
     * 获取description字段数据
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description字段数据
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取orderId字段数据
     * @return Returns the orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId字段数据
     * @param orderId The orderId to set.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取createTime字段数据
     * @return Returns the createTime.
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime字段数据
     * @param createTime The createTime to set.
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取createUser字段数据
     * @return Returns the createUser.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置createUser字段数据
     * @param createUser The createUser to set.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取updateTime字段数据
     * @return Returns the updateTime.
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime字段数据
     * @param updateTime The updateTime to set.
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取updateUser字段数据
     * @return Returns the updateUser.
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置updateUser字段数据
     * @param updateUser The updateUser to set.
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取ownUser字段数据
     * @return Returns the ownUser.
     */
    public String getOwnUser() {
        return ownUser;
    }

    /**
     * 设置ownUser字段数据
     * @param ownUser The ownUser to set.
     */
    public void setOwnUser(String ownUser) {
        this.ownUser = ownUser;
    }

    /**
     * 获取expireTime字段数据
     * @return Returns the expireTime.
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * 设置expireTime字段数据
     * @param expireTime The expireTime to set.
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取subNetwork字段数据
     * @return Returns the subNetwork.
     */
    public String getSubNetwork() {
        return subNetwork;
    }

    /**
     * 设置subNetwork字段数据
     * @param subNetwork The subNetwork to set.
     */
    public void setSubNetwork(String subNetwork) {
        this.subNetwork = subNetwork;
    }

    /**
     * 获取serverId字段数据
     * @return Returns the serverId.
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * 设置serverId字段数据
     * @param serverId The serverId to set.
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * 获取acceptTime字段数据
     * @return Returns the acceptTime.
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     * 设置acceptTime字段数据
     * @param acceptTime The acceptTime to set.
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取hours字段数据
     * @return Returns the hours.
     */
    public int getHours() {
        return hours;
    }

    /**
     * 设置hours字段数据
     * @param hours The hours to set.
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * 获取itemName字段数据
     * @return Returns the itemName.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置itemName字段数据
     * @param itemName The itemName to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * toString 物理机信息
     * @return 物理机信息
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("物理机编号为：").append(this.pmId).append("\n");
        sb.append("物理机名称为：").append(this.pmName).append("\n");
        sb.append("物理机CPU类型为：").append(this.cpuType).append("\n");
        sb.append("物理机内存为：").append(this.ramSize).append("\n");
        sb.append("物理机存储空间为：").append(this.discSize).append("\n");
        sb.append("物理机型号为：").append(this.serverType).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池名称为：").append(this.resPoolName).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("资源池分区名称为：").append(this.resPoolPartName).append("\n");
        sb.append("用户名：").append(this.pmUser).append("\n");
        sb.append("用户口令：").append(this.pmPassword).append("\n");
        sb.append("物理机内网IP为：").append(this.ip).append("\n");
        sb.append("物理机子网为：").append(this.subNetwork).append("\n");
        sb.append("物理机所在物理机编号为：").append(this.serverId).append("\n");
        sb.append("物理机被接受时间为：").append(this.acceptTime).append("\n");
        sb.append("物理机规格ID为：").append(this.standardId).append("\n");
        sb.append("物理机内网IP为：").append(this.ip).append("\n");
        sb.append("物理机状态为：").append(this.status).append("\n");
        sb.append("物理机备注信息为：").append(this.description).append("\n");
        sb.append("物理机订单变化为：").append(this.orderId).append("\n");
        sb.append("物理机创建时间为：").append(this.createTime).append("\n");
        sb.append("物理机到期时间为：").append(this.expireTime).append("\n");
        sb.append("物理机时长：").append(this.hours).append("\n");
        sb.append("物理机所有者").append(this.ownUser).append("\n");
        return sb.toString();
    }

	/**
	 * @return the cpuType
	 */
	public String getCpuType() {
		return cpuType;
	}

	/**
	 * @param cpuType the cpuType to set
	 */
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the paramFlag
	 */
	public String getParamFlag() {
		return paramFlag;
	}

	/**
	 * @param paramFlag the paramFlag to set
	 */
	public void setParamFlag(String paramFlag) {
		this.paramFlag = paramFlag;
	}

	/**
	 * @return the isoId
	 */
	public String getIsoId() {
		return isoId;
	}

	/**
	 * @param isoId the isoId to set
	 */
	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	/**
	 * @return the isoName
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * @param isoName the isoName to set
	 */
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	/**
	 * @return the isoDescription
	 */
	public String getIsoDescription() {
		return isoDescription;
	}

	/**
	 * @param isoDescription the isoDescription to set
	 */
	public void setIsoDescription(String isoDescription) {
		this.isoDescription = isoDescription;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the maxTime
	 */
	public String getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the netList
	 */
	public List<NetInfo> getNetList() {
		return netList;
	}

	/**
	 * @param netList the netList to set
	 */
	public void setNetList(List<NetInfo> netList) {
		this.netList = netList;
	}

	/**
	 * @return the operationIP
	 */
	public String getOperationIP() {
		return operationIP;
	}

	/**
	 * @param operationIP the operationIP to set
	 */
	public void setOperationIP(String operationIP) {
		this.operationIP = operationIP;
	}

}
