/*******************************************************************************
 * @(#)ErrorCode.java 2013-1-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.host;

import com.neusoft.mid.cloong.common.logger.StatusCode;

/**
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.1 $ 2013-1-22 下午2:03:13
 */
public enum HostStatusCode implements StatusCode {

    /**
     * 产品数据库查询失败
     */
    PORTAL_PRODUCT_SEARCH_DB_ERR("20001");

    private final String code;

    HostStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
