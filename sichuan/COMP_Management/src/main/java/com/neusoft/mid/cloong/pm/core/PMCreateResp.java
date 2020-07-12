package com.neusoft.mid.cloong.pm.core;

import java.util.List;
import java.util.Map;

/**
 * 创建物理机接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class PMCreateResp extends RespBodyInfo {

    /**
     * 实例id
     */
    private String caseId;
    
    /**
     * PM信息集合
     */
    private List<Map<String, String>> pmInfo;

    /**
     * 
     * getPmInfo TODO 方法
     * @return TODO
     */
    public List<Map<String, String>> getPmInfo() {
        return pmInfo;
    }
    
    /**
     * 
     * setPmInfo TODO 方法
     * @param pmInfo TODO
     */
    public void setPmInfo(List<Map<String, String>> pmInfo) {
        this.pmInfo = pmInfo;
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
