package com.neusoft.mid.cloong.web.page.common;

/**
 * 向前台传JSON串时，要做的数据转换
 * 
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li</a>
 * @version Revision 1.0 2014年12月30日 上午11:24:08
 */
public class JsonUtil
{
	public static String jsonTrans(String json)
	{
		String result = new String();

		result = json.replace("'" , "\'").replace('"' , '\'');

		return result;
	}
}
