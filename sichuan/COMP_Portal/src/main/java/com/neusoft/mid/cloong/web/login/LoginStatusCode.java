/*******************************************************************************
 * @(#)LoginStatusCode.java 2009-6-24
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-6 下午02:56:04
 */
public enum LoginStatusCode implements StatusCode {

    // 登录失败
    LOGIN_FAILURE("23301"),

    // 登录成功
    LOGIN_SUCCESS("23302"),

    // 登出成功
    LOGOUT_SUCCESS("23303"),

    // 从数据库中读取信息时异常
    LOGIN_EXCEPTION("23304"),

    // 校验输入信息时异常
    LOGIN_VALID_PARA_EXCEPTION("23305");

    private final String code;

    LoginStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
