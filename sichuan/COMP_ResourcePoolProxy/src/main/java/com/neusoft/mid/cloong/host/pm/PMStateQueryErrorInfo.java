/*******************************************************************************
 * @(#)PMStateQueryErrorInfo.java 2013-2-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.common.logger.LogAttr;
import com.neusoft.mid.cloong.common.logger.LogColumn;

/**
 * 物理机状态查询失败信息，用于记录日志
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-26 下午05:07:02
 */
@LogAttr(logFileName="PmStateQueryError.log")
public class PMStateQueryErrorInfo {
    /**
     * 物理机编码
     */
    @LogColumn(0)
    private String pmId;

    /**
     * 失败原因
     */
    @LogColumn(1)
    private String failCause;

    /**
     * 错误码
     */
    @LogColumn(2)
    private String failCode;

    /**
     * 资源池ID
     */
    @LogColumn(3)
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    @LogColumn(4)
    private String resourcePoolPartId;

    /**
     * 查询预期状态
     */
    @LogColumn(5)
    private String futureState;

    /**
     * 创建时间
     */
    @LogColumn(6)
    private String createTime;

    /**
     * 物理机操作流水号
     */
    @LogColumn(7)
    private String serialNum;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
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

    public String getFutureState() {
        return futureState;
    }

    public void setFutureState(String futureState) {
        this.futureState = futureState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("物理机编号为：").append(this.pmId).append(", ");
        sb.append("失败原因为：").append(this.failCause).append(", ");
        sb.append("错误码为：").append(this.failCode).append(", ");
        sb.append("资源池ID为：").append(this.resourcePoolId).append(", ");
        sb.append("资源池分区ID为：").append(this.resourcePoolPartId).append(", ");
        sb.append("查询预期状态为：").append(this.futureState).append(", ");
        sb.append("创建时间为：").append(this.createTime).append(", ");
        sb.append("操作流水号为：").append(this.serialNum).append(", ");
        return sb.toString();
    }
}
