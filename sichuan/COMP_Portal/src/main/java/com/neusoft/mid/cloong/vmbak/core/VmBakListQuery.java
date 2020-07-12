/*******************************************************************************
 * @(#)VmBakListQuery.java 2015-3-10
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryResp;


/**
 * 虚拟机备份查询接口
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.0 $ 2015-3-10 下午04:08:11
 */
public interface VmBakListQuery {

	RPPVMBakListQueryResp queryVmBakList(RPPVMBakListQueryReq vmBakReq);

}
