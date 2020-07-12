/*******************************************************************************
 * @(#)SoapBodyThreadLcoal.java 2013-2-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 用于存储请求报文头的本地线程存储器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午01:10:53
 */
public class ReqBodyThreadLcoal {

    private static final ThreadLocal<ReqBodyInfo> REQBODY_THREADLOCAL = new ThreadLocal<ReqBodyInfo>();

    private ReqBodyThreadLcoal() {

    }

    public static void set(ReqBodyInfo reqBodyInfo) {
        REQBODY_THREADLOCAL.set(reqBodyInfo);
    }

    public static void unset() {
        REQBODY_THREADLOCAL.remove();
    }

    public static ReqBodyInfo get() {
        return REQBODY_THREADLOCAL.get();
    }
}
