/*******************************************************************************
 * @(#)VmBakRestore.java 2015-3-10
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreResp;


/**
 * 虚拟机备份恢复接口
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.0 $ 2015-3-10 下午04:08:11
 */
public interface VmBakRestore {

	RPPVMBakRestoreResp restoreVmBak(RPPVMBakRestoreReq vmBakReq);

}
