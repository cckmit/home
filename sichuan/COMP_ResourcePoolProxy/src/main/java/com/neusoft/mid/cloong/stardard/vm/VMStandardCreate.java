/*******************************************************************************
 * @(#)VMStandardCreate.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm;

import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateReq;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateResp;

/**
 * 虚拟硬盘规格创建接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:40:27
 */
public interface VMStandardCreate {
    /**
     * 创建虚拟硬盘资源规格
     * @param req 请求
     * @return
     */
    VMstandardCreateResp createVMStandard(VMstandardCreateReq req);
}
