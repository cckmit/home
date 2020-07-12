package com.neusoft.mid.cloong.host.pm.core;

import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.host.vm.core.RespBodyInfo;

/**
 * 创建物理机接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class PMCreateResp extends RespBodyInfo {

    private String caseId;

    private List<Map<String, String>> pmInfo;


    public List<Map<String, String>> getPmInfo() {
        return pmInfo;
    }

    public void setPmInfo(List<Map<String, String>> pmInfo) {
        this.pmInfo = pmInfo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
