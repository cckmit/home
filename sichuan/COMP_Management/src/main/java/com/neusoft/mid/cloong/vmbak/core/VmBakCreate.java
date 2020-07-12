/*******************************************************************************
 * @(#)VmBakCreate.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateResp;


/**
 * 虚拟机备份操作接口
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:08:11
 */
public interface VmBakCreate {
    /**
     * 虚拟机备份操作接口
     * createVmBak 虚拟机备份操作接口
     * @param req 虚拟机备份请求
     * @return 虚拟机备份响应
     */
	RPPVMBakCreateResp createVmBak(RPPVMBakCreateReq vmBakReq);

}
