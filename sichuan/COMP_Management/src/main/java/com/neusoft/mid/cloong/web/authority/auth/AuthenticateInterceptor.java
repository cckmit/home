/*******************************************************************************
 * @(#)AuthenticateInterceptor.java 2009-6-6
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.authority.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.web.identify.ValidateStatusEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

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
        if (actionName.endsWith("Login")) {
            return invocation.invoke();
        }

        UserBean userInfo = (UserBean) session.get(SessionKeys.userInfo.toString());
        if (null == userInfo) {
            return "LOGIN";
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

        if(!IdentifyJudg(request.getRequestURL().toString(),userInfo)){
           return "LOGIN";
        }


        return invocation.invoke();
    }

    public boolean IdentifyJudg(String url,UserBean userInfo) throws JspException {
        if (null == userInfo) {
            logger.error(ValidateStatusEnum.AUTHENTICATERISNULL_EXCEPTION_CODE,
                    "session中的userInfo为空！");
        }
        //这里我只把权限校验精确到大类 “待办任务 订单管理 产品管理 资源管理 业务管理 等。。。”如有需要，可继续精确
        String permissionname="";
        if(url.indexOf("/task/") >= 0){
            permissionname="dbrw";
        }else if(url.indexOf("/userCenter/") >= 0){
            permissionname="ddgl";
        }else if(url.indexOf("/product/") >= 0){
            permissionname="cpgl";
        }else if(url.indexOf("/resourceManagement_resView/") >= 0){
            permissionname="zygl";
        }else if(url.indexOf("/resourceManagement_appView/") >= 0){
            permissionname="zygl";
        }else if(url.indexOf("/app/") >= 0){
            permissionname="ywgl";
        }else if(url.indexOf("/report/") >= 0){
            permissionname="tjfx";
        }else if(url.indexOf("/charges/") >= 0){
            permissionname="zfgl";
        }else if(url.indexOf("/system/") >= 0){
            permissionname="xtgl";
        }else if(url.indexOf("/meterage/") >= 0){
            permissionname="jlgl";
        }
        logger.info("所申请页面权限"+permissionname);

        if (StringUtils.isEmpty(permissionname)) {
            logger.error(ValidateStatusEnum.AUTHENTICATERISNULL_EXCEPTION_CODE,
                    "标签的permissionname为空！");
        }

        if (logger.isDebugEnable()) {
            //logger.debug("session = " + session);
            //logger.debug("authenticater = " + userInfo);
        }

        // 有权限，就显示包含的内容
        if (isAccess(userInfo,permissionname)) {
            return true;
        }
        // 没有权限，就不显示包含的内容
        return false;
    }
    private boolean isAccess(UserBean userInfo,String permissionname){
        for(RoleBean roleInfo:userInfo.getRoles()){
            for(PermissionBean perm:roleInfo.getPermList()){
                if(perm.getEnglishName().equals(permissionname)){
                    logger.info("所申请页面权限"+permissionname);
                    logger.info("数据库匹配到的用户权限"+perm.getEnglishName());
                    return true;
                }
            }
        }
        return false;
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
    

}
