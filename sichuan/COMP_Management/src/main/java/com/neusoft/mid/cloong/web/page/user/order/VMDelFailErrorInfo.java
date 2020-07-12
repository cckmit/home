/*******************************************************************************
 * @(#)VMStateQueryErrorInfo.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

/**
 * 虚拟机删除失败信息，用于记录日志
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 下午04:49:26
 */
public class VMDelFailErrorInfo {
    /**
     * 虚拟机编码
     */
    private String vmId;

    /**
     * 失败原因
     */
    private String failCause;

    /**
     * 错误码
     */
    private String failCode;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 虚拟机操作流水号
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

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机编号为：").append(this.vmId).append(", ");
        sb.append("失败原因为：").append(this.failCause).append(", ");
        sb.append("错误码为：").append(this.failCode).append(", ");
        sb.append("资源池ID为：").append(this.resourcePoolId).append(", ");
        sb.append("资源池分区ID为：").append(this.resourcePoolPartId).append(", ");
        sb.append("创建时间为：").append(this.createTime).append(", ");
        sb.append("操作流水号为：").append(this.serialNum).append(", ");
        return sb.toString();
    }
}
