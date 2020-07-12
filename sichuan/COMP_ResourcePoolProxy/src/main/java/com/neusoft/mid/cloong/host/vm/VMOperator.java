/*******************************************************************************
 * @(#)ResouceOperator.java 2013-2-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMOperatorResp;

/**
 * 虚拟机操作接口，包括：启动，暂停，恢复，停止，重启
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-4 下午05:04:44
 */
public interface VMOperator {
    /**
     * 操作虚拟机，包括：启动，暂停，恢复，停止，重启
     * @param req 请求参数
     * @return
     */
    RPPVMOperatorResp operateVM(VMOperatorReq req);
}
