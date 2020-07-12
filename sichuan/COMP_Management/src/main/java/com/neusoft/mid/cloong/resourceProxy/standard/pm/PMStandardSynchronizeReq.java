package com.neusoft.mid.cloong.resourceProxy.standard.pm;

import com.neusoft.mid.cloong.resourceProxy.standard.common.StandardSynchronizeReq;

/**
 * 向资源池同步物理机规格信息请求
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 下午02:10:26
 */
public class PMStandardSynchronizeReq extends StandardSynchronizeReq {

    /** 物理机类型 **/
    private String serverType;

    /**  cpu单元数量 **/
    private String cpuType;

    /** 内存大小 **/
    private String memorySize;

    /**  硬盘空间 **/
    private String storageSize;
    
    /**
     * 同步人
     */
    private String synchronizePerson;
    
    /**
     * 同步时间
     */
    private String synchronizeTime;
    
    /**
     * 镜像列表
     */
    private String[] osInfos;
    
    
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
    

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
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
	 * @return the synchronizePerson
	 */
	public String getSynchronizePerson() {
		return synchronizePerson;
	}

	/**
	 * @param synchronizePerson the synchronizePerson to set
	 */
	public void setSynchronizePerson(String synchronizePerson) {
		this.synchronizePerson = synchronizePerson;
	}

	/**
	 * @return the synchronizeTime
	 */
	public String getSynchronizeTime() {
		return synchronizeTime;
	}

	/**
	 * @param synchronizeTime the synchronizeTime to set
	 */
	public void setSynchronizeTime(String synchronizeTime) {
		this.synchronizeTime = synchronizeTime;
	}

	/**
	 * @return the osInfos
	 */
	public String[] getOsInfos() {
		return osInfos;
	}

	/**
	 * @param osInfos the osInfos to set
	 */
	public void setOsInfos(String[] osInfos) {
		this.osInfos = osInfos;
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
