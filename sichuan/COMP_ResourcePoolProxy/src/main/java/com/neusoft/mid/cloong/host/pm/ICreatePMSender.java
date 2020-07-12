/*******************************************************************************
 * @(#)ICreatePMSender.java 2014-1-15
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

/**
 * 发送创建物理机请求后查询状态的请求
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-15 下午3:25:22
 */
public interface ICreatePMSender extends Runnable {
    void send();
}
