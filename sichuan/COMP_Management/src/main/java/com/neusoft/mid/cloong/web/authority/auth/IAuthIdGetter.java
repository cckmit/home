/*******************************************************************************
 * @(#)IAuthIdGetter.java 2009-6-24
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.authority.auth;

/**
 * 此接口是所有需要鉴权的aciton都要实现的接口，在action中返回一个字符串，即该action的权限点ID， 鉴权时只需得到该返回值，不必在uri中截取，方便准确
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version $Revision 1.1 $ 2009-6-24 上午09:37:46
 */
public interface IAuthIdGetter {

    /**
     * 获取action的权限点ID
     * @return
     */
    String getAuthId();
}
