/*******************************************************************************
 * @(#)ResouceOperator.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;

/**
 * 虚拟机删除接口
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:11:14
 */
public interface VMDelete {
    /**
     * 删除虚拟机
     * @param req 请求参数
     * @return
     */
    RPPVMDeleteResp deleteVM(VMDeleteReq req);
}
