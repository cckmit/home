/*******************************************************************************
 * @(#)StandardSynchronizeDeleteResp.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.common;

import java.util.List;

import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardSynInfo;

/**
 * 调用接口返回结果信息实体
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午04:32:44
 */
public class StandardSynchronizeDeleteResp {

    /** 发送成功条数 **/
    private List<StandardSynInfo> success;

    /** 发送失败条数 **/
    private List<StandardSynInfo> failure;

    public List<StandardSynInfo> getSuccess() {
        return success;
    }

    public void setSuccess(List<StandardSynInfo> success) {
        this.success = success;
    }

    public List<StandardSynInfo> getFailure() {
        return failure;
    }

    public void setFailure(List<StandardSynInfo> failure) {
        this.failure = failure;
    }
    
    

}
