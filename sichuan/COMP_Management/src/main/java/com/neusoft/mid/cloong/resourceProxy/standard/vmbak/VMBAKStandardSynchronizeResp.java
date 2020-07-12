/*******************************************************************************
 * @(#)VMBAKStandardSynchronizeResp.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vmbak;

import java.util.List;

/**
 * 同步资源规格返回结果列表，包括成功列表和失败列表
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 下午08:43:40
 */
public class VMBAKStandardSynchronizeResp {

    /**
     * 同步成功列表
     */
    private List<VMBAKStandardSynchronizeResult> success;
    
    /**
     * 同步失败列表
     */
    private List<VMBAKStandardSynchronizeResult> failure;

    public List<VMBAKStandardSynchronizeResult> getSuccess() {
        return success;
    }

    public void setSuccess(List<VMBAKStandardSynchronizeResult> success) {
        this.success = success;
    }

    public List<VMBAKStandardSynchronizeResult> getFailure() {
        return failure;
    }

    public void setFailure(List<VMBAKStandardSynchronizeResult> failure) {
        this.failure = failure;
    }

   

}
