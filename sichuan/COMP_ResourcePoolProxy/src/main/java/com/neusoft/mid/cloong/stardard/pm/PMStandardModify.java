/*******************************************************************************
 * @(#)PMStandardModify.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.pm;

import com.neusoft.mid.cloong.stardard.pm.info.PMStandardModifyReq;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardModifyResp;

/**
 * 物理机模板修改接口
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:40:27
 */
public interface PMStandardModify {
    /**
     * 修改虚拟硬盘资源规格
     * @param req 请求
     * @return
     */
    PMStandardModifyResp modifyPMStandard(PMStandardModifyReq req);
}
