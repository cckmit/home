/*******************************************************************************
 * @(#)EBSAttachResp.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午10:01:31
 */
public class RPPEBSOperatorResp extends RPPBaseResp {

    private static final long serialVersionUID = -7897954506003989251L;

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
