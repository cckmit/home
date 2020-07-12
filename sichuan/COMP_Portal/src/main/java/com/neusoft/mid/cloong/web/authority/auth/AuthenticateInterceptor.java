/*******************************************************************************
 * @(#)AuthenticateInterceptor.java 2009-6-6
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.authority.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 对用户请求的uri进行鉴权，鉴权成功，则继续用户的请求，鉴权失败，则转向失败页面
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version $Revision 1.1 $ 2009-6-6 下午05:20:53
 */
public class AuthenticateInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;
    
    private static LogService logger = LogService.getLogger(AuthenticateInterceptor.class);
    
    private String requestReferer;
    
    private String requestHost;
    
    protected IbatisDAO ibatisDAO;

    public void destroy() {

    }

    public void init() {

    }

    @SuppressWarnings("unchecked")
    public String intercept(ActionInvocation invocation) throws Exception {
        Map session = invocation.getInvocationContext().getSession();
        
        HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext()
                .get(ServletActionContext.HTTP_REQUEST);
        
        // session为空时，返回到登录页面
        if (null == session) {
            return "LOGIN";
        }

        String actionName = invocation.getProxy().getActionName();
        if (actionName.endsWith("Login")||actionName.endsWith("dymanicCode")) {
            return invocation.invoke();
        }

        UserBean userInfo = (UserBean) session.get(SessionKeys.userInfo.toString());
        if (null == userInfo) {
            return "LOGIN";
        } else {
        	logger.info("****************************");
        	UserBean user = (UserBean) ibatisDAO.getSingleRecord(
					"getUserForPortalLogin", userInfo.getUserId());
        	if (!userInfo.getIfLogin().equals(user.getIfLogin())) { // session中存储的登录标志和用户表中存储的登录标志不同，则表示多地登录同一用户，当前用户要被踢掉
        		session.remove(SessionKeys.userInfo.toString());
        		session.remove(SessionKeys.invalid.toString());
        		session.remove(SessionKeys.random.toString());
        		return "LOGIN";
        	}
        }
        
      //判断是否CSRF攻击
        logger.info("*****************权限操作拦截器   Referer="+request.getHeader("Referer"));
        logger.info("*****************权限操作拦截器  URL="+request.getRequestURI());
        if(StringUtils.isEmpty(request.getHeader("Referer"))){
            if(!(request.getRequestURI().contains("commonIpModel.action")|| request.getRequestURI().contains("resourceList.action"))){
                return "error";
            }
        }else{
        	boolean flag = false;
        	String referers[] = requestReferer.split("\\|");
        	for (String referer : referers) {
				if (!request.getHeader("Referer").contains(referer)) {
//					return "NOAUTHORITY";
					flag = true;
				} else {
					flag = false;
					break;
				}
        	}
        	if (flag) {
        		return "NOAUTHORITY";
        	}
        }
        //判断host head attack
        logger.info("******************* HOST="+request.getHeader("host"));
        boolean hostFlag = false;
        String hosts[] = requestHost.split("\\|");
        for (String host : hosts) {
        	if(!host.equals(request.getHeader("Host"))){
//                return "NOAUTHORITY";
        		hostFlag = true;
            } else {
            	hostFlag = false;
            	break;
            }
        }
        if (hostFlag) {
        	return "NOAUTHORITY";
        }
        
        return invocation.invoke();
    }

	/**
	 * @return the requestReferer
	 */
	public String getRequestReferer() {
		return requestReferer;
	}

	/**
	 * @param requestReferer the requestReferer to set
	 */
	public void setRequestReferer(String requestReferer) {
		this.requestReferer = requestReferer;
	}

	/**
	 * @return the requestHost
	 */
	public String getRequestHost() {
		return requestHost;
	}

	/**
	 * @param requestHost the requestHost to set
	 */
	public void setRequestHost(String requestHost) {
		this.requestHost = requestHost;
	}

	/**
	 * @return the ibatisDAO
	 */
	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	/**
	 * @param ibatisDAO the ibatisDAO to set
	 */
	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}
	
}
