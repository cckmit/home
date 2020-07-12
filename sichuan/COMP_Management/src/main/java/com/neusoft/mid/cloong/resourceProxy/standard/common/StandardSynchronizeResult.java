/*******************************************************************************
 * @(#)VMStandardSynchronizeResult.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.common;

/**
 * 同步规格返回结果
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午02:19:18
 */
public class StandardSynchronizeResult {
    /**
     * 同步状态
     */
    private String synchronizedState;

    /**
     * 资源池信息
     */
    private ResourcePoolInfo resourcePool;

    public String getSynchronizedState() {
        return synchronizedState;
    }

    public void setSynchronizedState(String synchronizedState) {
        this.synchronizedState = synchronizedState;
    }

    public ResourcePoolInfo getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(ResourcePoolInfo resourcePool) {
        this.resourcePool = resourcePool;
    }
}
