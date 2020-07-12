/*******************************************************************************
 * @(#)PMStateQueryReq.java 2014-1-20
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 物理机状态查询接口请求
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-20 上午10:09:41
 */
public class PMStateQueryReq extends ReqBodyInfo {

    private String pmId;

    /**
     * 获取pmId字段数据
     * @return Returns the pmId.
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * 设置pmId字段数据
     * @param pmId The pmId to set.
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("物理机pmID为：").append(this.pmId).append("\n");
        return sb.toString();
    }
    
    /**
     * 到期时间
     */
    private String expireTime;

    /**
     * 获取expireTime字段数据
     * @return Returns the expireTime.
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * 设置expireTime字段数据
     * @param expireTime The expireTime to set.
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
    
}
