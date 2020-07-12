/*******************************************************************************
 * @(#)IPApplyReq.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryReq;

/**
 * 虚拟机备份任务查询Req
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午2:34:43
 */
public class VMBakTaskQueryReq extends ReqBodyInfo {

    private RPPVMBakTaskQueryReq info = new RPPVMBakTaskQueryReq();

    /**
     * 获取info字段数据
     * @return Returns the info.
     */
    public RPPVMBakTaskQueryReq getInfo() {
        return info;
    }

    /**
     * 设置info字段数据
     * @param info The info to set.
     */
    public void setInfo(RPPVMBakTaskQueryReq info) {
        this.info = info;
    }

}
