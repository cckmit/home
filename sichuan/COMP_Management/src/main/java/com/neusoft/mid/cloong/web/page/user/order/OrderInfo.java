package com.neusoft.mid.cloong.web.page.user.order;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 申请虚拟机订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午05:15:12
 */
public class OrderInfo extends BaseInfo{

	/**
     * 父订单ID
     */
    private String parentId;
    
    private String orderId;

    private String caseType;
    
    private String status;
    
    private String vmModifyBatchId;

    /**
     * 资源池名称
     */
    private String resPoolId;
    
    /**
     * 资源池名称
     */
    private String resPoolName;
    
    /**
     * 业务ID
     */
    private String appId;
    
    /**
     * 业务名称
     */
    private String appName;
    
    private String lengthTime;

    private String lengthUnit;
    
    private String effectiveTime;

    private String expireTime;

    private String caseId;
    
    /**
     * 资源池分区
     */
    private String resPoolPartId;
    
    /**
     * 资源池分区名称
     */
    private String resPoolPartName;
    
    /**
     * 规格ID，移到每个资源对象中，订单中不使用
     */
    private String standardId;
    
    /**
     * 条目ID，移到每个资源对象中，订单中不使用
     */
    private String itemId;
    
    /**
     * OSID，移到每个资源对象中，订单中不使用
     */
    private String osId;
    
    /**
     * VDC_ID
     */
    private String vdcId;
    
    /**
     * 获取parentId字段数据
     * @return Returns the parentId.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置parentId字段数据
     * @param parentId The parentId to set.
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getLengthTime() {
        return lengthTime;
    }

    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(String lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

	/**
	 * @return the resPoolName
	 */
	public String getResPoolName() {
		return resPoolName;
	}

	/**
	 * @param resPoolName the resPoolName to set
	 */
	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
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
	 * @return the resPoolId
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * @param resPoolId the resPoolId to set
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * @return the resPoolPartId
	 */
	public String getResPoolPartId() {
		return resPoolPartId;
	}

	/**
	 * @param resPoolPartId the resPoolPartId to set
	 */
	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	/**
	 * @return the standardId
	 */
	public String getStandardId() {
		return standardId;
	}

	/**
	 * @param standardId the standardId to set
	 */
	public void setStandardId(String standardId) {
		this.standardId = standardId;
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
	 * @return the osId
	 */
	public String getOsId() {
		return osId;
	}

	/**
	 * @param osId the osId to set
	 */
	public void setOsId(String osId) {
		this.osId = osId;
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

	public String getVdcId() {
		return vdcId;
	}

	public void setVdcId(String vdcId) {
		this.vdcId = vdcId;
	}

	public String getVmModifyBatchId() {
		return vmModifyBatchId;
	}

	public void setVmModifyBatchId(String vmModifyBatchId) {
		this.vmModifyBatchId = vmModifyBatchId;
	}
}
