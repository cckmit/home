/*******************************************************************************
 * @(#)TurnPageUrlInterceptor.java Oct 29, 2007
 *
 * Copyright 2007 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author <a href="mailto:hegq@neusoft.com">puras.he </a>
 * @version $Revision 1.1 $ Oct 29, 2007 11:23:34 AM
 */
public class TurnPageInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(TurnPageInterceptor.class);

    private String paramFilter;

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    public void init() {

    }

    /*
     * (non-Javadoc)
     * @seecom.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.
     * ActionInvocation)
     */
    public String intercept(ActionInvocation invo) throws Exception {
        HttpServletRequest request = (HttpServletRequest) invo.getInvocationContext().get(
                ServletActionContext.HTTP_REQUEST);

        String url = request.getRequestURI();
        String pageParam = "";
        String param = "";
        StringBuilder params = new StringBuilder();
        if ("get".equalsIgnoreCase(request.getMethod())) {
            pageParam = request.getQueryString();
            // 过滤注入参数paramFilter
            if (null != pageParam) {
                for (String paramVal : pageParam.split("&")) {
                    boolean contains = true;
                    for (String removeVal : paramFilter.split(";")) {
                        if (paramVal.startsWith(removeVal + "=")) {
                            contains = false;
                            break;
                        }
                    }
                    if (contains) {
                        params.append(paramVal + "&");
                    }
                }
            }

        } else if ("post".equalsIgnoreCase(request.getMethod())) {
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String name = (String) paramNames.nextElement();
                String value = request.getParameter(name);
                params.append(name + "=" + value + "&");
            }
        }
        param = params.toString();
        if (param.endsWith("&")) {
            param = param.substring(0, param.length() - 1);
        }

        if (log.isDebugEnabled()) {
            log.debug("URL ==>" + url);
            log.debug("Param ==>" + param);
            log.debug("Method ==>" + request.getMethod());
        }
        ActionContext.getContext().getParameters().put("url", url);
        ActionContext.getContext().getParameters().put("param", param);

        return invo.invoke();
    }

    /**
     * @param paramFilter the paramFilter to set
     */
    public void setParamFilter(String paramFilter) {
        this.paramFilter = paramFilter;
    }

}
