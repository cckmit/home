/*******************************************************************************
 * @(#)AuthenticateFilter.java 2009-6-3
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.authority.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 对用户请求的报表ID以及登出进行鉴权，若鉴权成功，则继续用户的请求。 如果用户没有相应的操作权限，则转向鉴权失败页面。
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version $Revision 1.1 $ 2009-6-3 下午04:38:46
 */
public class AuthenticateFilter implements Filter {

     private static LogService logger = LogService.getLogger(AuthenticateFilter.class);

    private FilterConfig config;

    private String relogin;

    public void destroy() {

    }

    /**
     * 获取用户请求的报表ID,若鉴权成功，则继续用户的请求；若鉴权失败，则转向失败页面
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (null == request.getSession()) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        UserBean userInfo = (UserBean) request.getSession().getAttribute(
                SessionKeys.userInfo.toString());
        if (null == userInfo) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // 鉴权成功，继续用户的请求
        if (isAccess(userInfo,"dbrw")) {
            chain.doFilter(req, res);
        } else if (isAccess(userInfo,"ddgl")) {
            response.sendRedirect(request.getContextPath() + "/userCenter/orderQuery.action");
        }else if (isAccess(userInfo,"cpgl")) {
            response.sendRedirect(request.getContextPath() + "/product/VMStandardListQuery.action");
        }else if (isAccess(userInfo,"xtgl")) {
            response.sendRedirect(request.getContextPath() + "/system/resource.action");
        } else {
            response.sendRedirect(request.getContextPath());
            return;
        }

    }
    private boolean isAccess(UserBean userInfo,String permissionname){
        for(RoleBean roleInfo:userInfo.getRoles()){
            for(PermissionBean perm:roleInfo.getPermList()){
                if(perm.getEnglishName().equals(permissionname)){
                    return true;
                }
            }
        }
        return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        relogin = config.getInitParameter("relogin");
    }

}
