/*******************************************************************************
 * @(#)MORecieve.java 2011-7-23
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.neusoft.mid.grains.modules.http.api.GrainsRecvServlet;
import com.neusoft.mid.iamp.api.RuntimeContext;

/**
 * 接收用户自服务门户的请求，并调用相应的请求处理器，处理请求
 * @author <a href="mailto:miq@neusoft.com">miq </a>
 * @version $Revision 1.1 $ 2011-7-23 下午05:35:42
 */
public class RequestRecieve extends GrainsRecvServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected String doReceive(RuntimeContext req, RuntimeContext resp) {
    	System.out.println("进来进来");
        ServletContext sc = getServletContext();
        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(sc);
        String processorId = (String) getInitParameter("processorId");
        RequestProcessor processor = (RequestProcessor) wc.getBean(processorId);
        return processor.doProcess(req, resp);
    }
}
