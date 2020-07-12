/*******************************************************************************
 * @(#)MenuInterceptor.java 2014年2月8日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business.Interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.business.service.QueryBusinessService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 菜单拦截器
 * 
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月8日 下午2:10:08
 */
public class BusinessMenuInterceptor implements Interceptor {

	/**
	 * serialVersionUID : 序列化版本号
	 */
	private static final long serialVersionUID = -3609212229677081989L;

	/**
	 * 服务类
	 */
	private QueryBusinessService service;

	/**
	 * destroy 需要实现方法
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * init 需要实现方法
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	/**
	 * intercept 拦截器
	 * 
	 * @param invocation
	 *            拦截器对象
	 * @return 下一个拦截器
	 * @throws Exception
	 *             异常
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		String url = request.getRequestURI();

		if (url.indexOf("business_console") < 0) {
			return invocation.invoke();
		}

		UserBean user = (UserBean) ServletActionContext.getRequest()
				.getSession().getAttribute(SessionKeys.userInfo.toString());
		List<BusinessInfo> businessList = service.queryBusinessList(user);
		request.setAttribute("businessList", businessList);

		/*
		 * String referer = request.getHeader("referer"); if
		 * (referer.indexOf("businessId=") > -1){ String paramStr =
		 * referer.split("?")[0]; String[] params = paramStr.split("&"); for
		 * (String param : params) { String key = param.split("=")[0]; if
		 * ("businessId".equals(key)){ String value = param.split("=")[1];
		 * request. } } }
		 */

		return invocation.invoke();
	}

	/**
	 * 设置service字段数据
	 * 
	 * @param service
	 *            The service to set.
	 */
	public void setService(QueryBusinessService service) {
		this.service = service;
	}

}
