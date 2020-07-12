/*******************************************************************************
 * @(#)EBSDeleteReq.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 虚拟硬盘删除请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午03:09:08
 */
public class EBSDeleteReq extends ReqBodyInfo {
    /**
     * EBS编码
     */
    private String ebsId;

    public String getEbsId() {
        return ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

}
