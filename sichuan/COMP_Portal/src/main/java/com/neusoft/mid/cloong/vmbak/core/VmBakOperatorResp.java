/*******************************************************************************
 * @(#)VmBakOperatorResp.java 2013-2-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

import java.util.List;

import com.neusoft.mid.cloong.host.vm.core.VMInfoSet;

/**
 * 虚拟机备份操作响应
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:40:55
 */
public class VmBakOperatorResp {
    /**
     * 响应码
     */
    private String resultCode;

    /**
     * 响应描述
     */
    private String resultMessage;
    
    /**
     * 返回的虚拟机信息
     */
    private List<VMInfoSet> vmInfo;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List<VMInfoSet> getVmInfo() {
        return vmInfo;
    }

    public void setVmInfo(List<VMInfoSet> vmInfo) {
        this.vmInfo = vmInfo;
    }

}
