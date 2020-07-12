package com.neusoft.mid.cloong.vault.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringEscapeUtils;
import com.neusoft.mid.iamp.logger.LogService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OtherUtil {

	private static LogService logger = LogService.getLogger(OtherUtil.class);

	public static ConcurrentMap<String, String> startTimeMap = new ConcurrentHashMap();

	public static ConcurrentMap<String, String> logTransMap = new ConcurrentHashMap();

	static {
		logTransMap.put("remoteAuth", "1");
		logTransMap.put("localAuth", "2");
		logTransMap.put("autoAuth", "3");
		logTransMap.put("remoteAuthModeWithoutPass", "3");
	}

	@SuppressWarnings("rawtypes")
	public static Map beanToMap(Object obj) {

		Map map = new HashMap();
		try {
			map = PropertyUtils.describe(obj);
		} catch (Exception e) {
			logger.error("OtherUtil-beanToMap-转换失败", e);
		}

		return map;
	}


	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static  <T> T mapToBean(Object obj, Map map, Class<T> t) {

		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			logger.error("OtherUtil-mapToBean-转换失败", e);
		}
		return (T) obj;
	}


	public static String getRequestXml(Object request, Class<?> t){

    	JaxbUtil requestBinder = new JaxbUtil(t);
        String requestXml = requestBinder.toXml(request, "utf-8");
        requestXml = StringEscapeUtils.unescapeXml(requestXml);
    	return requestXml;
    }

	public static String getTime(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;

    }

}
