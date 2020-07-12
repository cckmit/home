/*******************************************************************************
 * @(#)VMOperatorReq.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 虚拟机删除接口请求
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:15:28
 */
public class VMDeleteReq extends ReqBodyInfo {
    /**
     * 虚拟机编码
     */
    private String vmId;


    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

}
