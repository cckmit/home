/*******************************************************************************
 * @(#)VMStandardSynchronizeReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vm;

import com.neusoft.mid.cloong.resourceProxy.standard.common.StandardSynchronizeReq;

/**
 * 向资源池同步规格信息请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午04:47:31
 */
public class VMStandardSynchronizeReq extends StandardSynchronizeReq {

    /**
     * cpu单元数量
     */
    private String cpuNum;

    /**
     * 内存大小
     */
    private String memorySize;

    /**
     * 硬盘空间
     */
    private String storageSize;
    
    /**
     * 镜像id
     */
    private String osId;
    
    /**
     * 同步人
     */
    private String synchronizePerson;
    
    /**
     * 同步时间
     */
    private String synchronizeTime;

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
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

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String getSynchronizePerson() {
		return synchronizePerson;
	}

	public void setSynchronizePerson(String synchronizePerson) {
		this.synchronizePerson = synchronizePerson;
	}

	public String getSynchronizeTime() {
		return synchronizeTime;
	}

	public void setSynchronizeTime(String synchronizeTime) {
		this.synchronizeTime = synchronizeTime;
	}
	
}
