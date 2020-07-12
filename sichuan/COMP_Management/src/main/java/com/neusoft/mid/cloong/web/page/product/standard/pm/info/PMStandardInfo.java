package com.neusoft.mid.cloong.web.page.product.standard.pm.info;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 物理机规格实体
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午09:59:26
 */
public class PMStandardInfo extends BaseInfo {

    /** 规格名称 **/
    private String standardName;

    /** 规格ID **/
    private String standardId;

    /** 物理机类型 **/
    private String serverType;

    /** 规格描述 **/
    private String description;

    /** 使用状态 **/
    private String useStatus;

    /** CPU数量 **/
    private String cpuType;

    /** 内存大小 **/
    private long ramSize;

    /** 硬盘大小 **/
    private long discSize;
    
    /** 物理机类型ID **/
    private String pmTypeId;
    
    /** 物理机类型名称 **/
    private String pmTypeName;
    
    /** 网卡个数 **/
    private String ethadaNum;
    
    /**网卡的规格 **/
    private String ethadaType;
    
    /**ISCSI卡个数 **/
    private String scsiAdaNum;
    
    /**HBA个数 **/
    private String hbaNum;
    
    /**HBA的规格 **/
    private String hbaType;
    
    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public long getRamSize() {
        return ramSize;
    }

    public void setRamSize(long ramSize) {
        this.ramSize = ramSize;
    }

    public long getDiscSize() {
        return discSize;
    }

    public void setDiscSize(long discSize) {
        this.discSize = discSize;
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
	 * @return the pmTypeId
	 */
	public String getPmTypeId() {
		return pmTypeId;
	}

	/**
	 * @param pmTypeId the pmTypeId to set
	 */
	public void setPmTypeId(String pmTypeId) {
		this.pmTypeId = pmTypeId;
	}

	/**
	 * @return the pmTypeName
	 */
	public String getPmTypeName() {
		return pmTypeName;
	}

	/**
	 * @param pmTypeName the pmTypeName to set
	 */
	public void setPmTypeName(String pmTypeName) {
		this.pmTypeName = pmTypeName;
	}

	/**
	 * @return the ethadaNum
	 */
	public String getEthadaNum() {
		return ethadaNum;
	}

	/**
	 * @param ethadaNum the ethadaNum to set
	 */
	public void setEthadaNum(String ethadaNum) {
		this.ethadaNum = ethadaNum;
	}

	/**
	 * @return the ethadaType
	 */
	public String getEthadaType() {
		return ethadaType;
	}

	/**
	 * @param ethadaType the ethadaType to set
	 */
	public void setEthadaType(String ethadaType) {
		this.ethadaType = ethadaType;
	}

	/**
	 * @return the scsiAdaNum
	 */
	public String getScsiAdaNum() {
		return scsiAdaNum;
	}

	/**
	 * @param scsiAdaNum the scsiAdaNum to set
	 */
	public void setScsiAdaNum(String scsiAdaNum) {
		this.scsiAdaNum = scsiAdaNum;
	}

	/**
	 * @return the hbaNum
	 */
	public String getHbaNum() {
		return hbaNum;
	}

	/**
	 * @param hbaNum the hbaNum to set
	 */
	public void setHbaNum(String hbaNum) {
		this.hbaNum = hbaNum;
	}

	/**
	 * @return the hbaType
	 */
	public String getHbaType() {
		return hbaType;
	}

	/**
	 * @param hbaType the hbaType to set
	 */
	public void setHbaType(String hbaType) {
		this.hbaType = hbaType;
	}
}
