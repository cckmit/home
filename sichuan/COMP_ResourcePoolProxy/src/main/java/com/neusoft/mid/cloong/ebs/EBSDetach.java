/*******************************************************************************
 * @(#)EBSDetach.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorResp;

/**
 * 虚拟硬盘卸载
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:21:10
 */
public interface EBSDetach {
    /**
     * 卸载
     * @param req
     * @return
     */
    RPPEBSOperatorResp detach(EBSDetachReq req);
}
