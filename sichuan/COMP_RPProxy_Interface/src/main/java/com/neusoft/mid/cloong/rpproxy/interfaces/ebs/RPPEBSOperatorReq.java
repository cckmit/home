/*******************************************************************************
 * @(#)EBSAttachReq.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟硬盘挂载请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午09:56:14
 */
public class RPPEBSOperatorReq extends RPPBaseReq {

    private static final long serialVersionUID = -2667598320051104081L;

    /**
     * 虚拟硬盘编码
     */
    private String ebsId;

    /**
     * 虚拟机编码
     */
    private List<String> vmId = new ArrayList<String>();

    public String getEbsId() {
        return ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    /**
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public List<String> getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(List<String> vmId) {
        this.vmId = vmId;
    }

}
