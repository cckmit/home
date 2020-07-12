/*******************************************************************************
 * @(#)CreatePMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.pm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 物理机更改请求类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPPMModifyReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 物理机ID
     */
    private String pmId;

    /**
     * 物理机名称
     */
    private String pmName;

    /**
     * 网卡信息列表
     */
    private List<EthInfo> ethList = new ArrayList<EthInfo>();

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

    /**
     * 获取pmName字段数据
     * @return Returns the pmName.
     */
    public String getPmName() {
        return pmName;
    }

    /**
     * 设置pmName字段数据
     * @param pmName The pmName to set.
     */
    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
    /**
     * 获取ethList字段数据
     * @return Returns the ethList.
     */
    public List<EthInfo> getEthList() {
        return ethList;
    }

    /**
     * 设置ethList字段数据
     * @param ethList The ethList to set.
     */
    public void setEthList(List<EthInfo> ethList) {
        this.ethList = ethList;
    }

    

}
