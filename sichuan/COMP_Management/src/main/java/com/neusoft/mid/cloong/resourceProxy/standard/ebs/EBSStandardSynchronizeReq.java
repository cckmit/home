/*******************************************************************************
 * @(#)VMStandardSynchronizeReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.ebs;

import com.neusoft.mid.cloong.resourceProxy.standard.common.StandardSynchronizeReq;

/**
 * 向资源池同步虚拟硬盘规格信息请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午04:47:31
 */
public class EBSStandardSynchronizeReq extends StandardSynchronizeReq {

    /**
     * 硬盘空间
     */
    private String storageSize;
    
    private String resourceType;

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
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

}
