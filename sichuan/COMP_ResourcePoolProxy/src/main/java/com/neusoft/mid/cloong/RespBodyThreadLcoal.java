/*******************************************************************************
 * @(#)RespBodyThreadLcoal.java 2013-2-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 用于存储响应报文头的本地线程存储器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午01:21:47
 */
public class RespBodyThreadLcoal {

    private static final ThreadLocal<RespBodyInfo> RESPBODY_THREADLOCAL = new ThreadLocal<RespBodyInfo>();

    private RespBodyThreadLcoal() {

    }

    public static void set(RespBodyInfo reqBodyInfo) {
        RESPBODY_THREADLOCAL.set(reqBodyInfo);
    }

    public static void unset() {
        RESPBODY_THREADLOCAL.remove();
    }

    public static RespBodyInfo get() {
        return RESPBODY_THREADLOCAL.get();
    }
}
