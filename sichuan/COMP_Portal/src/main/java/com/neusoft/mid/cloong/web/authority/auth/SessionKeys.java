/*******************************************************************************
 * @(#)SessionKeys.java 2013-3-6
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.authority.auth;

/**sessin关键字
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-6 下午03:14:32
 */
public enum SessionKeys {
    /**
     * 正常登录用户的用户信息对象
     */
    userInfo("userInfo"),

    /**
     * 登录时错误的提示信息
     */
    invalid("invalid"),

    /**
     * 登录时验证码
     */
    random("random");

    private String strValue;

    SessionKeys(String str) {
        strValue = str;
    }

    public String toString() {
        return strValue;
    }
}
