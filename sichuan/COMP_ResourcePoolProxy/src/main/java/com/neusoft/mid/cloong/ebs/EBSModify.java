/*******************************************************************************
 * @(#)EBSDelete.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyResp;

/**
 * 修改虚拟硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午03:08:58
 */
public interface EBSModify {
    /**
     * 删除
     * @param req
     * @return
     */
    RPPEBSModifyResp modify(EBSModifyReq req);

}
