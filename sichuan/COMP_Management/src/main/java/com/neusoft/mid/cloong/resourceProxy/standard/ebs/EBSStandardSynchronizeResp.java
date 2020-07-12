/*******************************************************************************
 * @(#)VMStandardSynchronizeResp.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.ebs;

import java.util.List;

/**
 * 同步资源规格返回结果列表，包括成功列表和失败列表
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午08:43:40
 */
public class EBSStandardSynchronizeResp {

    private List<EBSStandardSynchronizeResult> success;
    
    private List<EBSStandardSynchronizeResult> failure;

    public List<EBSStandardSynchronizeResult> getSuccess() {
        return success;
    }

    public void setSuccess(List<EBSStandardSynchronizeResult> success) {
        this.success = success;
    }

    public List<EBSStandardSynchronizeResult> getFailure() {
        return failure;
    }

    public void setFailure(List<EBSStandardSynchronizeResult> failure) {
        this.failure = failure;
    }

   

}
