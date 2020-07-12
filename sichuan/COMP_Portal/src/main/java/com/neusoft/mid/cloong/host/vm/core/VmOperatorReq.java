/*******************************************************************************
 * @(#)VmOperatorReq.java 2013-2-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.core;

/**
 * 虚拟机操作请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-17 下午04:59:13
 */
public class VmOperatorReq {
    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 操作类型包括：启动，暂停，恢复，停止，重启
     */
    private VMOperatorType type;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 操作流水号
     */
    private String serialNum;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public VMOperatorType getType() {
        return type;
    }

    public void setType(VMOperatorType type) {
        this.type = type;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

}
