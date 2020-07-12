/*******************************************************************************
 * @(#)IPApplyReq.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakDeleteReq;

/**
 * 虚拟机备份删除Req
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午2:34:43
 */
public class VMBakDeleteReq extends ReqBodyInfo {

    private RPPVMBakDeleteReq info = new RPPVMBakDeleteReq();

    /**
     * 获取info字段数据
     * @return Returns the info.
     */
    public RPPVMBakDeleteReq getInfo() {
        return info;
    }

    /**
     * 设置info字段数据
     * @param info The info to set.
     */
    public void setInfo(RPPVMBakDeleteReq info) {
        this.info = info;
    }

}
