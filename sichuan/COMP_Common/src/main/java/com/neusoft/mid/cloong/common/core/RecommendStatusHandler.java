/*******************************************************************************
 * @(#)VMStatusHandler.java 2013-2-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.core;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * 枚举类型RecommendStatus映射处理类，存储时把RecommendStatus映射成数据库需要的字段值，读取时把数据库中的字段值映射成RecommendStatus
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-6 上午09:08:11
 */
public class RecommendStatusHandler implements TypeHandlerCallback {

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        if (null == getter.getObject()) {
            throw new IllegalArgumentException("Count not convert null" + " to enum RecommendStatus!");
        }
        return getType(getter.getString());

    }

    private Object getType(String value) {
        Object result = null;
        for (RecommendStatus status : RecommendStatus.values()) {
            if (value.equals(status.getCode())) {
                result = status;
                break;
            }
        }
        if (null == result) {
            throw new IllegalArgumentException("Count not convert" + value
                    + " to enum RecommendStatus!");
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#setParameter(com.ibatis.sqlmap.client
     * .extensions.ParameterSetter, java.lang.Object)
     */
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (null == parameter) {
            throw new IllegalArgumentException("Count not convert null" + " to enum RecommendStatus!");
        } else {
            setter.setString(((RecommendStatus) parameter).getCode());
        }

    }

    /*
     * (non-Javadoc)
     * @see com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#valueOf(java.lang.String)
     */
    @Override
    public Object valueOf(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
