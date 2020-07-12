package com.neusoft.mid.cloong.web.page.user.order;

import com.neusoft.mid.cloong.ebs.core.EBSStatus;

/**
 * 虚拟硬盘实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-21 下午03:17:50
 */
public class EBSInstanceInfo {
    
    /**
     * 实例id
     */
    private String caseId;

    /**
     * ebs ID
     */
    private String ebsId;

    /**
     * ebs 名字
     */
    private String ebsName;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 规格id
     */
    private String standardId;

    /**
     * 收到时间
     */
    private String acceptTime;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池分区
     */
    private String resPoolPartId;
    
    /**
     * 资源池分区
     */
    private String resPoolPartName;

    /**
     * 业务ID
     */
    private String appId;
    
    /**
     * 业务名称
     */
    private String appName;
    
    /**
     * EBS状态
     */
    private EBSStatus status;

    /**
     * 主机id
     */
    private String hostId;

    /**
     * 描述信息
     */
    private String description;

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
     * 所有者
     */
    private String ownUser;

    /**
     * 到期时间
     */
    private String expireTime;

    /**
     * 硬盘空间(单位 G)
     */
    private String diskSize;
    
    /**
     * 条目ID
     */
    private String itemId;
    
    /**
     * 条目名称
     */
    private String itemName;
    
    /**
     * 硬盘类型
     */
    private String resourceType;
    
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
     * 
     * getCaseId TODO 方法
     * @return TODO
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 
     * setCaseId TODO 方法
     * @param caseId TODO
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 
     * getEbsId TODO 方法
     * @return TODO
     */
    public String getEbsId() {
        return ebsId;
    }

    /**
     * 
     * setEbsId TODO 方法
     * @param ebsId TODO
     */
    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    /**
     * 
     * getEbsName TODO 方法
     * @return TODO
     */
    public String getEbsName() {
        return ebsName;
    }

    /**
     * 
     * setEbsName TODO 方法
     * @param ebsName TODO
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    /**
     * 
     * getOrderId TODO 方法
     * @return TODO
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * setOrderId TODO 方法
     * @param orderId TODO
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * getStandardId TODO 方法
     * @return TODO
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 
     * setStandardId TODO 方法
     * @param standardId TODO
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 
     * getAcceptTime TODO 方法
     * @return TODO
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     * 
     * setAcceptTime TODO 方法
     * @param acceptTime TODO
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 
     * getResPoolId TODO 方法
     * @return TODO
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 
     * setResPoolId TODO 方法
     * @param resPoolId TODO
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 
     * getResPoolPartId TODO 方法
     * @return TODO
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 
     * setResPoolPartId TODO 方法
     * @param resPoolPartId TODO
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 
     * getStatus TODO 方法
     * @return TODO
     */
    public EBSStatus getStatus() {
        return status;
    }

    /**
     * 
     * setStatus TODO 方法
     * @param status TODO
     */
    public void setStatus(EBSStatus status) {
        this.status = status;
    }

    /**
     * 
     * getDescription TODO 方法
     * @return TODO
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * setDescription TODO 方法
     * @param description TODO
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * getCreateTime TODO 方法
     * @return TODO
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * setCreateTime TODO 方法
     * @param createTime TODO
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * getCreateUser TODO 方法
     * @return TODO
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * setCreateUser TODO 方法
     * @param createUser TODO
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * getUpdateTime TODO 方法
     * @return TODO
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * setUpdateTime TODO 方法
     * @param updateTime TODO
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * getUpdateUser TODO 方法
     * @return TODO
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * setUpdateUser TODO 方法
     * @param updateUser TODO
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * getOwnUser TODO 方法
     * @return TODO
     */
    public String getOwnUser() {
        return ownUser;
    }

    /**
     * 
     * setOwnUser TODO 方法
     * @param ownUser TODO
     */
    public void setOwnUser(String ownUser) {
        this.ownUser = ownUser;
    }

    /**
     * 
     * getExpireTime TODO 方法
     * @return TODO
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * 
     * setExpireTime TODO 方法
     * @param expireTime TODO
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 
     * getHostId TODO 方法
     * @return TODO
     */
    public String getHostId() {
        return hostId;
    }

    /**
     * 
     * setHostId TODO 方法
     * @param hostId TODO
     */
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    /**
     * 
     * toString 虚拟硬盘信息
     * @return  虚拟硬盘信息
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("实例ID为：").append(this.caseId).append("\n");
        sb.append("硬盘编号为：").append(this.ebsId).append("\n");
        sb.append("硬盘名称为：").append(this.ebsName).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("硬盘被接受时间为：").append(this.acceptTime).append("\n");
        sb.append("硬盘规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟订单编号为：").append(this.orderId).append("\n");
        sb.append("硬盘受理时间为：").append(this.acceptTime).append("\n");
        sb.append("硬盘状态为：").append(this.status).append("\n");
        sb.append("硬盘备注信息为：").append(this.description).append("\n");
        sb.append("硬盘创建时间为：").append(this.createTime).append("\n");
        sb.append("硬盘创建人为：").append(this.createUser).append("\n");
        sb.append("硬盘更新时间为：").append(this.updateTime).append("\n");
        sb.append("硬盘更新人为：").append(this.updateUser).append("\n");
        sb.append("硬盘到期时间为：").append(this.expireTime).append("\n");
        sb.append("硬盘所有者").append(this.ownUser).append("\n");
        return sb.toString();
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
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the resPoolPartName
	 */
	public String getResPoolPartName() {
		return resPoolPartName;
	}

	/**
	 * @param resPoolPartName the resPoolPartName to set
	 */
	public void setResPoolPartName(String resPoolPartName) {
		this.resPoolPartName = resPoolPartName;
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
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
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
