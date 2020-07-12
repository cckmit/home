/*******************************************************************************
 * @(#)PMStateQueryResp.java 2014-1-20
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * 物理机状态查询接口响应
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-20 上午10:05:27
 */
public class PMStateQueryResp extends RespBodyInfo {

    /**
     * 物理机状态
     */
    private String PMStatus;

    /**
     * 物理机管理操作IP
     */
    private String OperationIP;

    /**
     * 所属Vlan号
     */
    private String vlanId;

    /**
     * 获取pMStatus字段数据
     * @return Returns the pMStatus.
     */
    public String getPMStatus() {
        return PMStatus;
    }

    /**
     * 设置pMStatus字段数据
     * @param pMStatus The pMStatus to set.
     */
    public void setPMStatus(String pMStatus) {
        PMStatus = pMStatus;
    }

    /**
     * 获取operationIP字段数据
     * @return Returns the operationIP.
     */
    public String getOperationIP() {
        return OperationIP;
    }

    /**
     * 设置operationIP字段数据
     * @param operationIP The operationIP to set.
     */
    public void setOperationIP(String operationIP) {
        OperationIP = operationIP;
    }

    /**
     * 获取vlanID字段数据
     * @return Returns the vlanID.
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param id The vlanId to set.
     */
    public void setVlanId(String id) {
        vlanId = id;
    }

}
