/*******************************************************************************
 * @(#)VMStandardSynchronizeResp.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vm;

import java.util.List;

/**
 * 同步资源规格返回结果列表，包括成功列表和失败列表
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午08:43:40
 */
public class VMStandardSynchronizeResp {

    private List<VMStandardSynchronizeResult> success;
    
    private List<VMStandardSynchronizeResult> failure;

    public List<VMStandardSynchronizeResult> getSuccess() {
        return success;
    }

    public void setSuccess(List<VMStandardSynchronizeResult> success) {
        this.success = success;
    }

    public List<VMStandardSynchronizeResult> getFailure() {
        return failure;
    }

    public void setFailure(List<VMStandardSynchronizeResult> failure) {
        this.failure = failure;
    }

   

}
