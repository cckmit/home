/*******************************************************************************
 * @(#)Authorization.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 对资源池发送的上报请求进行鉴权
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午09:39:29
 */
public class Authorization {
    /**
     * 鉴别报文头信息
     * @param info
     * @return
     */
    public boolean authorize(ReqBodyInfo info) {
        return true;
    }

}
