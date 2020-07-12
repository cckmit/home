/*******************************************************************************
 * @(#)QueryPMStateResp.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

/**
 * 查询物理机状态应答
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 下午3:05:29
 */
public class QueryPMStateResp extends RespBodyInfo {

    /**
     * 虚拟机状态
     */
    private String pmState;

    public String getPmState() {
        return pmState;
    }

    public void setPmState(String pmState) {
        this.pmState = pmState;
    }

}
