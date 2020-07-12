/*******************************************************************************
 * @(#)PageAction.java 2009-6-15
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.page.common.Page;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageHelper;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

public abstract class PageAction extends BaseAction
{

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(PageAction.class);

	private int page = 1;

	private int pageSize = Page.DEFAULT_PAGE_SIZE;

	private String url;

	private String param;

	private String pageBar;

	private String asyncJSPageMethod = "getPageData";

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getUrl()
	{
		// 临时获取
		this.url = (String)ActionContext.getContext().getParameters().get("url");
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getParam()
	{
		// 临时获取
		this.param = (String)ActionContext.getContext().getParameters().get("param");
		return param;
	}

	public String getAsyncJSPageMethod()
	{
		// 临时获取
		Object asyncJSPageMethodObj = ActionContext.getContext().getParameters().get("asyncJSPageMethod");
		if(asyncJSPageMethodObj != null)
		{
			String[] asyncJSPageMethodArray = (String[])asyncJSPageMethodObj;
			this.asyncJSPageMethod = asyncJSPageMethodArray[0];
		}
		return asyncJSPageMethod;
	}

	public void setParam(String param)
	{
		this.param = param;
	}

	public String getPageBar()
	{
		return pageBar;
	}

	public void setPageBar(String pageBar)
	{
		this.pageBar = pageBar;
	}

	/**
	 * 有条件查询的分页方法 参数countStatement为获取记录数SQL的ID 参数resultSetStatement为获取结果集SQL的ID
	 * 参数obj为查询条件
	 */
	public final List getPage(String countStatement , String resultSetStatement , Object obj)
	{
		List list = null;
		try
		{
			int totalCount = ibatisDAO.getCount(countStatement , obj);
			Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam(), getAsyncJSPageMethod());
			this.pageBar = PageHelper.getPageBar(pageObj);
			list = ibatisDAO.getObjectsByPage(resultSetStatement , obj , pageObj.getStartOfPage() , getPageSize());
		}
		catch(SQLException e)
		{
			logger.error(null , "分页出错！" + e.getMessage() , e);
		}
		return list;
	}

	/**
	 * (设置同步异步方式)有条件查询的分页方法 参数countStatement为获取记录数SQL的ID
	 * 参数resultSetStatement为获取结果集SQL的ID 参数obj为查询条件
	 * 
	 */
	public final List getPage(String countStatement , String resultSetStatement , Object obj , PageTransModel model)
	{
		List list = null;
		try
		{
			int totalCount = ibatisDAO.getCount(countStatement , obj);
			Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam(), getAsyncJSPageMethod());
			this.pageBar = PageHelper.getPageBar(pageObj , model);
			list = ibatisDAO.getObjectsByPage(resultSetStatement , obj , pageObj.getStartOfPage() , getPageSize());
		}
		catch(SQLException e)
		{
			logger.error(null , "分页出错！" + e.getMessage() , e);
		}
		return list;
	}

	/**
	 * 无条件查询的分页方法 参数countStatement为获取记录数SQL的ID 参数resultSetStatement为获取结果集SQL的ID
	 */
	protected final List getPage(String countStatement , String resultSetStatement)
	{
		List list = null;
		try
		{
			int totalCount = ibatisDAO.getCount(countStatement , null);
			Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam(), getAsyncJSPageMethod());
			this.pageBar = PageHelper.getPageBar(pageObj);
			list = ibatisDAO.getObjectsByPage(resultSetStatement , null , pageObj.getStartOfPage() , getPageSize());
		}
		catch(SQLException e)
		{
			logger.error(null , "分页出错！" + e.getMessage() , e);
		}
		return list;
	}

	/**
	 * 设置不显示---"每页显示X条"，且页面固定显示10条 (设置同步异步方式)有条件查询的分页方法
	 * 参数countStatement为获取记录数SQL的ID 参数resultSetStatement为获取结果集SQL的ID 参数obj为查询条件
	 * 
	 */
	public final List getPage(String countStatement , String resultSetStatement , Object obj , PageTransModel model ,
			PageToDisplayPerModel displayPerModel)
	{
		List list = null;
		try
		{
			int totalCount = ibatisDAO.getCount(countStatement , obj);
			Page pageObj = new Page(getPage(), totalCount, getPageSize(), getUrl(), getParam(), getAsyncJSPageMethod());
			this.pageBar = PageHelper.getPageBar(pageObj , model , displayPerModel);
			list = ibatisDAO.getObjectsByPage(resultSetStatement , obj , pageObj.getStartOfPage() , getPageSize());
		}
		catch(SQLException e)
		{
			logger.error(null , "分页出错！" + e.getMessage() , e);
		}
		return list;
	}
}
