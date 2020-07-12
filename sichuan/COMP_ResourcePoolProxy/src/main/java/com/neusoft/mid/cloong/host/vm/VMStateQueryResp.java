/*******************************************************************************
 * @(#)VMStateQueryResp.java 2013-2-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 虚拟机状态查询接口响应
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-22 上午09:09:30
 */
public class VMStateQueryResp extends RespBodyInfo {
    /**
     * 虚拟机状态
     */
    private String vmState;

    /**
     * 虚拟机所在物理机ID
     */
    private String servrId;

    /**
     * 虚拟机内网IP
     */
    private String privateIp;

    public String getVmState() {
        return vmState;
    }

    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    public String getServrId() {
        return servrId;
    }

    public void setServrId(String servrId) {
        this.servrId = servrId;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

}
