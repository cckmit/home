/*******************************************************************************
 * @(#)EBSDetach.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorResp;

/**
 * 从虚拟机卸载云硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午10:27:38
 */
public interface EBSDetach {
    /**
     * 卸载云硬盘
     * @param req
     * @return 当resultCode为"00000000"时卸载成功
     */
    RPPEBSOperatorResp detach(RPPEBSOperatorReq req);

}
