/*******************************************************************************
 * @(#)ResourcePoolInfoPara.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

/**
 * 资源池信息查询参数
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 上午11:00:10
 */
public class ResourcePoolInfoPara {

    private String resourcePoolId;

    private String resourcePoolPartId;
    
    private String osId;

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

}
