/*******************************************************************************
 * @(#)EBSDeleteResp.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟硬盘删除响应
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午10:30:37
 */
public class RPPEBSDeleteResp extends RPPBaseResp {

    private static final long serialVersionUID = 8841454670391755186L;

    /**
     * 故障描述
     */
    private String faultString;

    /**
     * 获取faultString字段数据
     * @return Returns the faultString.
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * 设置faultString字段数据
     * @param faultString The faultString to set.
     */
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}
