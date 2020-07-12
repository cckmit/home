/*******************************************************************************
 * @(#)VMStandardState.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

import java.util.List;

/**
 * 虚拟机规格同步状态，用来区分是同步还是创建
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午03:55:09
 */
public class StandardQueryPara {
    /**
     * 规格ID
     */
    private String standardId;

    private List<ResourcePoolInfoPara> ResourceInfos;

    private List<ResourcePoolInfoPara> poolInfos;

    private List<ResourcePoolInfoPara> osInfos;

    public List<ResourcePoolInfoPara> getPoolInfos() {
        return poolInfos;
    }

    public void setPoolInfos(List<ResourcePoolInfoPara> poolInfos) {
        this.poolInfos = poolInfos;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public List<ResourcePoolInfoPara> getOsInfos() {
        return osInfos;
    }

    public void setOsInfos(List<ResourcePoolInfoPara> osInfos) {
        this.osInfos = osInfos;
    }

    public List<ResourcePoolInfoPara> getResourceInfos() {
        return ResourceInfos;
    }

    public void setResourceInfos(List<ResourcePoolInfoPara> resourceInfos) {
        ResourceInfos = resourceInfos;
    }

}
