/*******************************************************************************
 * @(#)VMOperator.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.core;

/**
 * 虚拟机管理接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午10:13:56
 */
public interface VMManager {
    /**
     * 操作虚拟机，包括：启动，暂停，恢复，停止，重启
     * @param req TODO
     * @return 成功返回true,失败返回false
     */
    VmOperatorResp operateVm(VmOperatorReq req);

}
