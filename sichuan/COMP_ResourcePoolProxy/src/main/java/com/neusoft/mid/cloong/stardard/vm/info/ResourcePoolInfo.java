/*******************************************************************************
 * @(#)ResourcePoolInfo.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm.info;

/**
 * 规格同步请求中的资源池信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午08:31:27
 */
public class ResourcePoolInfo {
    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPart;
    
    /**
     * 镜像ID
     */
    private String osId;

    public String getResourcePoolPart() {
        return resourcePoolPart;
    }

    public void setResourcePoolPart(String resourcePoolPart) {
        this.resourcePoolPart = resourcePoolPart;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}
    
}
