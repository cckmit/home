package com.neusoft.mid.cloong.resourceProxy.standard.pm;

import java.util.List;

/**
 * 同步资源规格返回结果列表，包括成功列表和失败列表
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 下午02:11:39
 */
public class PMStandardSynchronizeResp {

    private List<PMStandardSynchronizeResult> success;
    
    private List<PMStandardSynchronizeResult> failure;

    public List<PMStandardSynchronizeResult> getSuccess() {
        return success;
    }

    public void setSuccess(List<PMStandardSynchronizeResult> success) {
        this.success = success;
    }

    public List<PMStandardSynchronizeResult> getFailure() {
        return failure;
    }

    public void setFailure(List<PMStandardSynchronizeResult> failure) {
        this.failure = failure;
    }

   

}
