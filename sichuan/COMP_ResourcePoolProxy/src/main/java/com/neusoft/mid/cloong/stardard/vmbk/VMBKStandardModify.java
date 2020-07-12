/*******************************************************************************
 * @(#)VMStandardCreate.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vmbk;

import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardModifyReq;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardModifyResp;

/**
 * 虚拟机备份修改接口
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月22日 下午5:20:31
 */
public interface VMBKStandardModify {
    /**
     * 修改虚拟硬盘资源规格
     * @param req 请求
     * @return
     */
    VMBKStandardModifyResp modifyVMBKStandard(VMBKStandardModifyReq req);
}
