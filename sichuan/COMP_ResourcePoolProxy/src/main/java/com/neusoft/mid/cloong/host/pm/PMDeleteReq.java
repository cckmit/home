/*******************************************************************************
 * @(#)PMOperatorReq.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 物理机删除接口请求
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:15:28
 */
public class PMDeleteReq extends ReqBodyInfo {
    /**
     * 物理机编码
     */
    private String pmId;


    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

}
