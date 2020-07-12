/*******************************************************************************
 * @(#)PageTransModel.java 2014年1月16日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.common;

/**
 * 列表触发的方式
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-7 下午8:48:38
 */
public enum PageToDisplayPerModel {
    /**
     * 异步模式，显示全部组件
     */
    ALL_DISPLAY,
    
    /**
     * 异步模式，每页固定显示10条,且不显示"每页显示*条"
     */
    PAGESIZE_10_DISPLAY

}
