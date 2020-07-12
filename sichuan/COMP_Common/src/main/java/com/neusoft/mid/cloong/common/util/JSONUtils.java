/*******************************************************************************
 * @(#)DateParse.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.util;

import net.sf.json.JSONObject;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

public final class JSONUtils {
    private static LogService logger = LogService.getLogger(JSONUtils.class);
    /**
     * 用户json字符串转换成bean
     * @param jsonStr 前台获取的json字符串
     * @return 
     * @return ResourceInfo 转换成JavaBean
     */
    public static <T> Object formatJson(String jsonStr,Class c) {
        if (logger.isDebugEnable()) {
            logger.debug("JSON转换Bean");
        }
        T item = null;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            item =  (T)JSONObject.toBean(json,  c);
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    "json转换失败", e);
        }
        if (logger.isDebugEnable()) {
            logger.debug("ResourceInfo:\n" + item.toString());
        }
        return item;
    }
}
