/*******************************************************************************
 * @(#)VMOperatorReq.java 2013-2-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOperatorType;

/**
 * 虚拟机操作接口请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-4 下午05:05:45
 */
public class VMOperatorReq extends ReqBodyInfo {
    /**
     * 虚拟机编码
     */
    private String vmId;

    /**
     * 操作类型 1：启动2：暂停3：恢复4：停止5：重启
     */
    private VMOperatorType type;

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 获取type字段数据
     * @return Returns the type.
     */
    public VMOperatorType getType() {
        return type;
    }

    /**
     * 设置type字段数据
     * @param type The type to set.
     */
    public void setType(VMOperatorType type) {
        this.type = type;
    }

}
