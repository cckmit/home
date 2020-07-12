/*******************************************************************************
 * @(#)EBSAttach.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorResp;

/**
 * 挂载虚拟硬盘到虚拟机
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午01:50:58
 */
public interface EBSAttach {
    /**
     * 挂载
     * @param req
     * @return
     */
    RPPEBSOperatorResp attach(EBSAttachReq req);
}
