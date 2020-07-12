/*******************************************************************************
 * @(#)VMStandardCreate.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vmbk;

import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardCreateReq;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardCreateResp;

/**
 * 虚拟机备份创建接口
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月22日 下午5:20:53
 */
public interface VMBKStandardCreate {
    /**
     * 创建虚拟机备份资源模板
     * @param req 请求
     * @return
     */
    VMBKStandardCreateResp createVMBKStandard(VMBKStandardCreateReq req);
}
