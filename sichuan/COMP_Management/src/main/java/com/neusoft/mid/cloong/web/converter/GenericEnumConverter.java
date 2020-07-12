/*******************************************************************************
 * @(#)GenericEnumConverter.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 枚举类型转换器
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月14日 下午4:10:28
 */
public class GenericEnumConverter extends StrutsTypeConverter {

    /**
     * 
     * convertFromString 将枚举转换为字符
     * @param context 上下文
     * @param values    参数
     * @param toClass   要转换的类类型
     * @return 返回枚举对象
     * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (!toClass.isEnum() || values == null) {
            return null;
        }
        
        if (values.length == 1) {// 大家都知道表单中的name是可以同名的,当然会有数个的情况
            return Enum.valueOf(toClass, values[0]);// toClass就是哪个枚举了比如我这的:SexType枚举,调用Enum.valueOf就可以根据String返回相应对象了.
        } else {
            Object[] oo = new Object[values.length];
            for (int i = 0; i < values.length; i++) {
                oo[i] = Enum.valueOf(toClass, values[i]);
            }
            return oo;
        }
    }

    @Override
    public String convertToString(Map context, Object o) {
        return null;
    }

}
