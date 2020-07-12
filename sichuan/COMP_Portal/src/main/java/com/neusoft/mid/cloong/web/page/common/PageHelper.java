/*******************************************************************************
 * @(#)PageHelper.java Oct 26, 2007
 *
 * Copyright 2007 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author <a href="mailto:hegq@neusoft.com">puras.he </a>
 * @version $Revision 1.1 $ Oct 26, 2007 11:55:57 AM
 */
public final class PageHelper
{
	private PageHelper()
	{
	}

	private static Log log = LogFactory.getLog(PageHelper.class);

	@SuppressWarnings("unchecked")
	public static String getPageBar(Page page)
	{
		return getPageBar(page , PageTransModel.SYNC);
	}

	@SuppressWarnings("unchecked")
	public static String getPageBar(Page page , PageTransModel model)
	{
		return getPageBar(page , model , PageToDisplayPerModel.ALL_DISPLAY);
	}

	@SuppressWarnings("unchecked")
	public static String getPageBar(Page page , PageTransModel model , PageToDisplayPerModel displayPerModel)
	{
		String firstUrl = "";
		String prevUrl = "";
		String nextUrl = "";
		String lastUrl = "";
		String numPage = "";
		String jumpPage = "";

		boolean isHasParam = true;

		if(null == page.getParam() || "".equals(page.getParam()))
		{
			isHasParam = false;
		}

		if(page.getTotalPageCount() > 1 && page.getCurPage() != 1)
		{
			firstUrl = page.getUrl() + "?page=1";
			if(isHasParam)
			{
				firstUrl += "&" + page.getParam();
			}
		}

		if(page.hasPreviousPage())
		{
			prevUrl = page.getUrl() + "?page=" + ( page.getCurPage() - 1 );
			if(isHasParam)
			{
				prevUrl += "&" + page.getParam();
			}
		}

		if(page.hasNextPage())
		{
			nextUrl = page.getUrl() + "?page=" + ( page.getCurPage() + 1 );
			if(isHasParam)
			{
				nextUrl += "&" + page.getParam();
			}
		}

		if(page.getTotalPageCount() > 1 && page.getCurPage() < page.getTotalPageCount())
		{
			lastUrl = page.getUrl() + "?page=" + page.getTotalPageCount();
			if(isHasParam)
			{
				lastUrl += "&" + page.getParam();
			}
		}

		numPage = getNumPage(page.getPageSize() , page.getCNum());
		jumpPage = getJumpPage(page.getTotalPageCount() , page.getCurPage());

		Map root = new HashMap();

		root.put("start" , Integer.valueOf(( page.getStartOfPage() )));
		root.put("pageSize" , Integer.valueOf(page.getPageSize()));
		root.put("total" , Integer.valueOf(page.getTotalCount()));
		root.put("page" , String.valueOf(page.getCurPage()));
		root.put("totalPageCount" , Integer.valueOf(page.getTotalPageCount()));
		root.put("url" , page.getUrl());
		root.put("param" , page.getParam());
		root.put("firstUrl" , firstUrl);
		root.put("prevUrl" , prevUrl);
		root.put("nextUrl" , nextUrl);
		root.put("lastUrl" , lastUrl);
		root.put("numPage" , numPage.toString());
		root.put("jumpPage" , jumpPage.toString());
		root.put("numParam" , page.parseParam(page.getParam() , Page.PAGE_SIZE_PREFIX));
		root.put("jsPageMethod" , page.getAsyncJSPageMethod());
		
		Configuration config = new Configuration();

		config.setClassForTemplateLoading(PageHelper.class , "");
		config.setEncoding(Locale.getDefault() , "UTF-8");

		StringWriter out = new StringWriter();

		Template template;
		try
		{
			if(PageTransModel.SYNC.equals(model))
			{// 同步模式
				template = config.getTemplate(page.getPageTemplate());
			}
			else
			{
				// TODO 未来需要优化
				if(PageToDisplayPerModel.PAGESIZE_10_DISPLAY.equals(displayPerModel))
				{
					template = config.getTemplate(page.getASyncDisplayPerPageTemplate());
				}
				else
				{// 异步模式，且显示“每页显示X条”
					template = config.getTemplate(page.getAsyncPageTemplate());
				}

			}
			template.setEncoding("UTF-8");
			template.process(root , out);
		}
		catch(IOException e)
		{
			log.error(e.getMessage());
		}
		catch(TemplateException e)
		{
			log.error(e.getMessage());
		}

		return out.toString();
	}

	private static String getNumPage(int pageSize , int cNum)
	{
		StringBuffer sb = new StringBuffer();
		final int defaultPageSize = 10;
		for (int i = 1; i <= cNum; i++)
		{
			int tmp = i * defaultPageSize;
			sb.append("<option value='").append(tmp).append("'");
			if(tmp == pageSize)
			{
				sb.append(" selected ");
			}
			sb.append(">").append(tmp).append("</option>");
		}
		return sb.toString();
	}

	private static String getJumpPage(long totalPageCount , int curPage)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= totalPageCount; i++)
		{
			sb.append("<option value='").append(i).append("'");
			if(i == curPage)
			{
				sb.append(" selected ");
			}
			sb.append(">").append(i).append("</option>");
		}
		return sb.toString();
	}

}
