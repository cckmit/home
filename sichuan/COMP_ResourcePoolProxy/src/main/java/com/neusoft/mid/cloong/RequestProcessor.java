/*******************************************************************************
 * @(#)Dispathcer.java 2011-7-25
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import com.neusoft.mid.iamp.api.RuntimeContext;

/**
 * 用户自服务请求处理器的公用接口
 * @author <a href="mailto:miq@neusoft.com">miq </a>
 * @version $Revision 1.1 $ 2011-7-25 下午02:09:23
 */
public interface RequestProcessor {
    /**
     * 对用户自服务系统的请求进行处理
     * @param req 请求对象
     * @param resp 响应对象
     * @return 路由的路径
     */
    String doProcess(RuntimeContext req, RuntimeContext resp);
}
