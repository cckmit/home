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
public class QueryIpInfoOfLogicVlanResp extends RespBodyInfo {
    
    /**
     * 逻辑VLAN集合
     */
    private List<Map<String, String>> ipSet;

    /**
     * 获取ipSet字段数据
     * @return Returns the ipSet.
     */
    public List<Map<String, String>> getIpSet() {
        return ipSet;
    }

    /**
     * 设置ipSet字段数据
     * @param ipSet The ipSet to set.
     */
    public void setIpSet(List<Map<String, String>> ipSet) {
        this.ipSet = ipSet;
    }

    
}
