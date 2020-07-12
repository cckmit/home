/*******************************************************************************
 * @(#)QueryPMStateReq.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

/**
 * 查询物理机状态请求
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 下午3:03:43
 */
public class QueryPMStateReq extends ReqBodyInfo {

    /**
     * 物理机id
     */
    private String pmId;

    /**
     * 获取pmId字段数据
     * @return Returns the pmId.
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * 设置pmId字段数据
     * @param vmId The pmId to set.
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

}
