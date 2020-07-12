/*******************************************************************************
 * @(#)VMStandardSynchronizedState.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.info;

/**
 * 资源池容量信息的同步
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-9 上午10:23:36
 */
public class ReportResourceState {
	
	 /**
     * 资源池ID
     */
    private String resourcePoolId;
    
    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;
    
    /**
     * 资源类型
     */
    private String resourceType;
    
    /**
     * 资源总量
     */
    private String ResourceTotal;

    /**
     * 已用资源占用百分数
     */
    private String resourceUsed;

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
	 * @return the resourceTotal
	 */
	public String getResourceTotal() {
		return ResourceTotal;
	}

	/**
	 * @param resourceTotal the resourceTotal to set
	 */
	public void setResourceTotal(String resourceTotal) {
		ResourceTotal = resourceTotal;
	}

	/**
	 * @return the resourceUsed
	 */
	public String getResourceUsed() {
		return resourceUsed;
	}

	/**
	 * @param resourceUsed the resourceUsed to set
	 */
	public void setResourceUsed(String resourceUsed) {
		this.resourceUsed = resourceUsed;
	}

	/**
	 * @return the resourcePoolId
	 */
	public String getResourcePoolId() {
		return resourcePoolId;
	}

	/**
	 * @param resourcePoolId the resourcePoolId to set
	 */
	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}

    /**
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    
}
