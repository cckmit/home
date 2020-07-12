/*******************************************************************************
 * @(#)IPApplyReq.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vlan;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelReq;

/**
 * 取消Vlan请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午2:34:43
 */
public class VlanCancelReq extends ReqBodyInfo {

    private RPPVlanCancelReq info = new RPPVlanCancelReq();

    /**
     * 获取info字段数据
     * @return Returns the info.
     */
    public RPPVlanCancelReq getInfo() {
        return info;
    }

    /**
     * 设置info字段数据
     * @param info The info to set.
     */
    public void setInfo(RPPVlanCancelReq info) {
        this.info = info;
    }

}
