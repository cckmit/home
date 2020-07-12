/*******************************************************************************
 * @(#)DymanicCode.java 2014年2月8日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.core;

/**
 * 动态密码对象
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月8日 上午9:23:29
 */
public class DymanicCode {
    /**
     * 动态密码
     */
    private String code;
    /**
     * 时间戳
     */
    private long timestamp;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
}
