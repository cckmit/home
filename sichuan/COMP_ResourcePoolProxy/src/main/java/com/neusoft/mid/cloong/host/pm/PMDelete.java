/*******************************************************************************
 * @(#)ResouceOperator.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMDeleteResp;

/**
 * 物理机删除接口
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:11:14
 */
public interface PMDelete {
    /**
     * 删除物理机
     * @param req 请求参数
     * @return
     */
    RPPPMDeleteResp deletePM(PMDeleteReq req);
}
