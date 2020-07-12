package com.neusoft.mid.cloong.web.authority.auth;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XSSFilter implements Filter {
    private static Logger logger = Logger.getLogger(Filter.class);

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        logger.info("XSS过滤器开始过滤:{}", request.getRemoteAddr());
        logger.info("XSS过滤器开始过滤:{}");
        chain.doFilter(new XssRequestWrappers((HttpServletRequest) request), response);
    }


}