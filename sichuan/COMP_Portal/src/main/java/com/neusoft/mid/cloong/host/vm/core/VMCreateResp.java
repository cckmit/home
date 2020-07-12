package com.neusoft.mid.cloong.host.vm.core;

import java.util.List;
import java.util.Map;

/**
 * 创建虚拟机接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class VMCreateResp extends RespBodyInfo {

    private String caseId;

    private List<Map<String, String>> vmInfo;

    public List<Map<String, String>> getVmInfo() {
        return vmInfo;
    }

    public void setVmInfo(List<Map<String, String>> vmInfo) {
        this.vmInfo = vmInfo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
