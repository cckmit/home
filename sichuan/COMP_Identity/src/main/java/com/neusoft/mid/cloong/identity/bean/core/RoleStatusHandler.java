/*******************************************************************************
 * @(#)RoleStatusHandler.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean.core;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;


/**
 * 枚举类型RoleStatus映射处理类，存储时把RoleStatus映射成数据库需要的字段值，读取时把数据库中的字段值映射成RoleStatus
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月10日 下午1:47:30
 */
public class RoleStatusHandler implements TypeHandlerCallback {

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        if (null == getter.getObject()) {
            throw new IllegalArgumentException("Count not convert null" + " to enum RoleStatus!");
        }
        return getType(getter.getString());

    }
    /**
     * 
     * getType 获取类型
     * @param value 获取值
     * @return 枚举
     */
    private Object getType(String value) {
        Object result = null;
        for (RoleStatus status : RoleStatus.values()) {
            if (value.equals(status.getCode())) {
                result = status;
                break;
            }
        }
        if (null == result) {
            throw new IllegalArgumentException("Count not convert" + value
                    + " to enum RoleStatus!");
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
            throw new IllegalArgumentException("Count not convert null" + " to enum RoleStatus!");
        } else {
            setter.setString(((RoleStatus) parameter).getCode());
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
