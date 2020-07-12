package com.neusoft.mid.cloong.vm.core;

import java.util.List;
import java.util.Map;

/**
 * 创建虚拟机接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class VMCreateResp extends RespBodyInfo {

    /**
     * 实例id
     */
    private String caseId;

    /**
     * VM信息集合
     */
    private List<Map<String, String>> vmInfo;

    /**
     * 
     * getVmInfo TODO 方法
     * @return TODO
     */
    public List<Map<String, String>> getVmInfo() {
        return vmInfo;
    }

    /**
     * 
     * setVmInfo TODO 方法
     * @param vmInfo TODO
     */
    public void setVmInfo(List<Map<String, String>> vmInfo) {
        this.vmInfo = vmInfo;
    }

    /**
     * 
     * getCaseId TODO 方法
     * @return TODO
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 
     * setCaseId TODO 方法
     * @param caseId TODO
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
