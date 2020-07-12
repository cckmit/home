/*******************************************************************************
 * @(#)VMStateQueryReq.java 2013-2-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 虚拟机状态查询接口请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-22 上午09:08:13
 */
public class VMStateQueryReq extends ReqBodyInfo {
    /**
     * 虚拟机编码
     */
    private String vmId;

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机VMID为：").append(this.vmId).append("\n");
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
