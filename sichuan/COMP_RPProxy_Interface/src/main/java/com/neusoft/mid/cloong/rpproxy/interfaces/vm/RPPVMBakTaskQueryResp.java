/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟机备份任务状态查询应答
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakTaskQueryResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 虚拟机备份任务的状态
     */
    private VMBakTaskStat vmBakTaskStat;

    /**
     * 获取vmBakTaskStat字段数据
     * @return Returns the vmBakTaskStat.
     */
    public VMBakTaskStat getVmBakTaskStat() {
        return vmBakTaskStat;
    }

    /**
     * 设置vmBakTaskStat字段数据
     * @param vmBakTaskStat The vmBakTaskStat to set.
     */
    public void setVmBakTaskStat(VMBakTaskStat vmBakTaskStat) {
        this.vmBakTaskStat = vmBakTaskStat;
    }

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

}
