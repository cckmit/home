/*******************************************************************************
 * @(#)VmOperatorResp.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 虚拟机删除响应
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 下午03:47:29
 */
public class QueryLogicVlanResp extends RespBodyInfo {
    
    /**
     * 逻辑VLAN集合
     */
    private List<Map<String, String>> logicVlanSet;

    /**
     * 获取logicVlanSet字段数据
     * @return Returns the logicVlanSet.
     */
    public List<Map<String, String>> getLogicVlanSet() {
        return logicVlanSet;
    }

    /**
     * 设置logicVlanSet字段数据
     * @param logicVlanSet The logicVlanSet to set.
     */
    public void setLogicVlanSet(List<Map<String, String>> logicVlanSet) {
        this.logicVlanSet = logicVlanSet;
    }
    
}
