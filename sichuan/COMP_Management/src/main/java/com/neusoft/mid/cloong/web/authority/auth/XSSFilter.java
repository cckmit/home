package com.neusoft.mid.cloong.web.authority.auth;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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