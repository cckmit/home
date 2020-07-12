/*******************************************************************************
 * @(#)VMStandardCreate.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.ebs;

import com.neusoft.mid.cloong.stardard.ebs.info.EBSStandardCreateReq;
import com.neusoft.mid.cloong.stardard.ebs.info.EBSStandardCreateResp;

/**
 * 虚拟机模板创建接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:40:27
 */
public interface EBSStandardCreate {
    /**
     * 创建虚拟机资源模板
     * @param req 请求
     * @return
     */
    EBSStandardCreateResp createEBSStandard(EBSStandardCreateReq req);
}
