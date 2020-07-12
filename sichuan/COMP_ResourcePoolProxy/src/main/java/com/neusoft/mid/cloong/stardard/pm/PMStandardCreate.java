/*******************************************************************************
 * @(#)PMStandardCreate.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.pm;

import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateReq;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateResp;

/**
 * 物理机模板创建接口
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:40:27
 */
public interface PMStandardCreate {
    /**
     * 创建物理机资源模板
     * @param req 请求
     * @return
     */
    PMStandardCreateResp createPMStandard(PMStandardCreateReq req);
}
