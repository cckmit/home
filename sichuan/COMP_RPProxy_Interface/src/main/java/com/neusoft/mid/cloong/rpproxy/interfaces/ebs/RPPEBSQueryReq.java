/*******************************************************************************
 * @(#)EBSAttachReq.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟硬盘查询请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午09:56:14
 */
public class RPPEBSQueryReq extends RPPBaseReq {

    private static final long serialVersionUID = -2667598320051104081L;

    /**
     * 虚拟硬盘编码
     */
    private String ebsId;

    public String getEbsId() {
        return ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

}
