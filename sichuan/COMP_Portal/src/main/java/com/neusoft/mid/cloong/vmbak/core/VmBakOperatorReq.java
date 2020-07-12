/*******************************************************************************
 * @(#)VmBakOperatorReq.java 2013-2-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

/**
 * 虚拟机备份操作请求
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:39:27
 */
public class VmBakOperatorReq {
    /**
     * 虚拟机备份ID
     */
    private String vmBakId;

    /**
     * 操作类型包括：恢复
     */
    private VmBakOperatorType type;

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

    public String getVmBakId() {
        return vmBakId;
    }

    public void setVmBakId(String vmBakId) {
        this.vmBakId = vmBakId;
    }

    public VmBakOperatorType getType() {
        return type;
    }

    public void setType(VmBakOperatorType type) {
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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
    
}
