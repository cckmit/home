/*******************************************************************************
 * @(#)EBSDelete.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;

/**
 * 删除云硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午10:29:29
 */
public interface EBSDelete {
    /**
     * 删除云硬盘
     * @param req
     * @return 当resultCode为"00000000"时删除成功
     */
    RPPEBSDeleteResp delete(RPPEBSDeleteReq req);
}
