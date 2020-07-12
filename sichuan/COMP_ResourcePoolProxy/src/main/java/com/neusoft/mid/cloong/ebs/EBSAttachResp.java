/*******************************************************************************
 * @(#)EBSAttachResp.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 虚拟硬盘挂载响应
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午01:52:46
 */
public class EBSAttachResp extends RespBodyInfo {
    /**
     * 挂载时间
     */
    private String attachTime;

    public String getAttachTime() {
        return attachTime;
    }

    public void setAttachTime(String attachTime) {
        this.attachTime = attachTime;
    }
}
