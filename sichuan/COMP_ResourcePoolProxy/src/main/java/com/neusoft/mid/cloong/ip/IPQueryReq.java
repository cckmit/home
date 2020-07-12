/*******************************************************************************
 * @(#)IPApplyReq.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ip;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryReq;

/**
 * IP申请Req
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午2:34:43
 */
public class IPQueryReq extends ReqBodyInfo {

    private RPPIPQueryReq info = new RPPIPQueryReq();

    /**
     * 获取info字段数据
     * @return Returns the info.
     */
    public RPPIPQueryReq getInfo() {
        return info;
    }

    /**
     * 设置info字段数据
     * @param info The info to set.
     */
    public void setInfo(RPPIPQueryReq info) {
        this.info = info;
    }

}
