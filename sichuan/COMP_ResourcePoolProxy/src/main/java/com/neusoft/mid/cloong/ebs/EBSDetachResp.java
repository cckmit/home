/*******************************************************************************
 * @(#)EBSDetachResp.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 虚拟硬盘卸载响应
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:20:58
 */
public class EBSDetachResp extends RespBodyInfo {
    /**
     * 卸载时间
     */
    private String detachTime;

    public String getDetachTime() {
        return detachTime;
    }

    public void setDetachTime(String detachTime) {
        this.detachTime = detachTime;
    }

}
