/*******************************************************************************
 * @(#)EBSDetachReq.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 虚拟机硬盘卸载请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:20:49
 */
public class EBSDetachReq extends ReqBodyInfo {
    /**
     * EBS编码
     */
    private String ebsId;

    /**
     * 虚拟机编码
     */
    private List<String> hostIds = new ArrayList<String>();

    public String getEbsId() {
        return ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    /**
     * 获取hostIds字段数据
     * @return Returns the hostIds.
     */
    public List<String> getHostIds() {
        return hostIds;
    }

    /**
     * 设置hostIds字段数据
     * @param hostIds The hostIds to set.
     */
    public void setHostIds(List<String> hostIds) {
        this.hostIds = hostIds;
    }

}
