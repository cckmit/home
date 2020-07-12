/*******************************************************************************
 * @(#)IAuthenticater.java 2009-6-18
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.authority.auth;

/**
 * 鉴权接口，用于判断用户是否有操作权限
 * @author <a href="mailto:ren.ll@neusoft.com">renll</a>
 * @version $Revision 1.1 $ 2009-6-18 上午09:44:10
 */
public interface IAuthenticater {
    /**
     * 用于判断用户是否有查看报表的权限
     * @param reportId 报表Id
     * @return false 鉴权失败，true 鉴权成功
     */
    boolean authenticateReport(String reportId);

    /**
     * 用于判断用户是否有其他操作功能的权限
     * @param 权限点ID
     * @return false 鉴权失败，true 鉴权成功
     */
    boolean authenticateUrl(String authId);
}
