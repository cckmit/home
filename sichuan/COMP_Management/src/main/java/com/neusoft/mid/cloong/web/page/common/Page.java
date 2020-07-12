/*******************************************************************************
 * @(#)Page.java Oct 26, 2007
 *
 * Copyright 2007 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.common;

/**
 * @author <a href="mailto:hegq@neusoft.com">puras.he </a>
 * @version $Revision 1.1 $ Oct 26, 2007 11:14:31 AM
 */
public class Page
{
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String PAGE_PREFIX = "page=";

	public static final String PAGE_SIZE_PREFIX = "pageSize=";

	private int curPage = 1;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private int totalCount;

	private int cNum = DEFAULT_PAGE_SIZE;

	private String url = "";

	private String param = "";

	private String asyncJSPageMethod = "getPageDate";

	private String pageTemplate = "page.ftl";

	private String aSyncPageTemplate = "asyncPage.ftl";

	private String aSyncDisplayPerPageTemplate = "aSyncDisplayPerPage.ftl";

	public Page()
	{
		this(1, 0, DEFAULT_PAGE_SIZE, "");
	}

	public Page(int curPage , int totalSize , String queryString)
	{
		this(curPage, totalSize, DEFAULT_PAGE_SIZE, queryString);
	}

	public Page(int curPage , int totalSize , int pageSize , String queryString)
	{
		this.pageSize = pageSize;
		this.totalCount = totalSize;
		this.curPage = setCurrentPage(curPage);
		parseQueryString(queryString);
	}

	public Page(int curPage , int totalSize , int pageSize , String url , String param,String asyncJSPageMethod)
	{
		this.pageSize = pageSize;
		this.totalCount = totalSize;
		this.curPage = setCurrentPage(curPage);
		this.url = url;
		this.param = parseParam(param , PAGE_PREFIX);
		this.asyncJSPageMethod = asyncJSPageMethod;
	}

	private int setCurrentPage(int currPage)
	{
		if(currPage > this.getTotalPageCount())
		{
			return this.getTotalPageCount();
		}
		else
		{
			return currPage;
		}
	}

	public String parseParam(String parameter , String filter)
	{
		StringBuffer sb = new StringBuffer();
		if(null != parameter)
		{
			String[] params = parameter.split("&");
			if(null != params)
			{
				for (int i = 0, len = params.length; i < len; i++)
				{
					if(params[i].indexOf(filter) != -1)
					{
						continue;
					}
					if(params[i].length() > 0)
					{
						sb.append(params[i]);
						if(i < len - 1)
						{
							sb.append("&");
						}
					}
				}
			}
		}
		String paramTmp = sb.toString();
		if(paramTmp.endsWith("&"))
		{
			paramTmp = paramTmp.substring(0 , paramTmp.length() - 1);
		}

		return paramTmp;
	}

	private void parseQueryString(String queryString)
	{
		if(queryString.indexOf("?") != -1)
		{
			String[] tmps = queryString.split("\\?");
			url = tmps[0];
			param = tmps[1];
		}
		else
		{
			url = queryString;
		}
	}

	public int getTotalCount()
	{
		return this.totalCount;
	}

	public int getTotalPageCount()
	{
		if(totalCount % pageSize == 0)
		{
			return totalCount / pageSize;
		}
		else
		{
			return totalCount / pageSize + 1;
		}
	}

	public int getPageSize()
	{
		return this.pageSize;
	}

	public boolean hasNextPage()
	{
		return this.getCurPage() < this.getTotalPageCount();
	}

	public boolean hasPreviousPage()
	{
		return this.getCurPage() > 1;
	}

	public int getStartOfPage()
	{
		return getStartOfPage(this.curPage , this.pageSize);
	}

	public int getStartOfPage(int pageNo)
	{
		return getStartOfPage(pageNo , DEFAULT_PAGE_SIZE);
	}

	public int getStartOfPage(int pageNo , int pgSize)
	{
		return ( pageNo - 1 ) * pgSize;
	}

	/**
	 * @return Returns the cNum.
	 */
	public int getCNum()
	{
		return cNum;
	}

	/**
	 * @param num
	 *            The cNum to set.
	 */
	public void setCNum(int num)
	{
		cNum = num;
	}

	/**
	 * @return Returns the pageTemplate.
	 */
	public String getPageTemplate()
	{
		return pageTemplate;
	}

	/**
	 * @return Returns the asyncPageTemplate.
	 */
	public String getAsyncPageTemplate()
	{
		return aSyncPageTemplate;
	}

	/**
	 * @param pageTemplate
	 *            The pageTemplate to set.
	 */
	public void setPageTemplate(String pageTemplate)
	{
		this.pageTemplate = pageTemplate;
	}

	/**
	 * @return Returns the curPage.
	 */
	public int getCurPage()
	{
		return curPage;
	}

	/**
	 * @param curPage
	 *            The curPage to set.
	 */
	public void setCurPage(int curPage)
	{
		this.curPage = curPage;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @return Returns the param.
	 */
	public String getParam()
	{
		return param;
	}

	public void setQueryString(String queryString)
	{
		parseQueryString(queryString);
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	/**
	 * @param totalCount
	 *            The totalCount to set.
	 */
	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	/**
	 * @return the aSyncDisplayPerPageTemplate
	 */
	public String getASyncDisplayPerPageTemplate()
	{
		return aSyncDisplayPerPageTemplate;
	}

	/**
	 * @param aSyncDisplayPerPageTemplate
	 *            the aSyncDisplayPerPageTemplate to set
	 */
	public void setASyncDisplayPerPageTemplate(String aSyncDisplayPerPageTemplate)
	{
		this.aSyncDisplayPerPageTemplate = aSyncDisplayPerPageTemplate;
	}

	/**
	 * @return the asyncJSPageMethod
	 */
	public String getAsyncJSPageMethod()
	{
		return asyncJSPageMethod;
	}

	/**
	 * @param asyncJSPageMethod
	 *            the asyncJSPageMethod to set
	 */
	public void setAsyncJSPageMethod(String asyncJSPageMethod)
	{
		this.asyncJSPageMethod = asyncJSPageMethod;
	}

}
