/*******************************************************************************
 * @(#)RPPVMOperatorReq.java 2015年2月26日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟机操作请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月26日 上午11:20:24
 */
public class RPPVMOperatorReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2541103703050994058L;

    /**
     * 要操作的虚拟机ID
     */
    private String vmId;

    /**
     * 操作类型,枚举
     */
    private VMOperatorType vmOperatorType;

    /**
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 获取vmOperatorType字段数据
     * @return Returns the vmOperatorType.
     */
    public VMOperatorType getVmOperatorType() {
        return vmOperatorType;
    }

    /**
     * 设置vmOperatorType字段数据
     * @param vmOperatorType The vmOperatorType to set.
     */
    public void setVmOperatorType(VMOperatorType vmOperatorType) {
        this.vmOperatorType = vmOperatorType;
    }

}
