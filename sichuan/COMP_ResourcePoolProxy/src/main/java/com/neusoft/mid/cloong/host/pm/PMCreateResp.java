/*******************************************************************************
 * @(#)PMCreateResp.java 2014-1-15
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 创建物理机接口响应
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-15 下午3:59:22
 */
public class PMCreateResp extends RespBodyInfo {
private String caseId;
    
    private List<Map<String,String>> pmInfo;

    public List<Map<String,String>> getPmInfo() {
        return pmInfo;
    }

    public void setpmInfo(List<Map<String,String>> pmInfo) {
        this.pmInfo = pmInfo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("响应码为：").append(this.getResultCode()).append("\n");
        sb.append("响应描述为：").append(this.getResultMessage()).append("\n");
        sb.append("物理机实例ID为：").append(this.caseId).append("\n");
        sb.append("物理机信息为：").append("\n");
        for(Map<String,String> pmInfoMap :this.getPmInfo()){
            sb.append(pmInfoMap.toString()).append("\n");
        }
        return sb.toString();
    }
}
