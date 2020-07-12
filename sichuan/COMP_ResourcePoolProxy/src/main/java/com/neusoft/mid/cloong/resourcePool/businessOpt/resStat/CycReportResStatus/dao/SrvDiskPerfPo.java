/*******************************************************************************
 * @(#)VMDiskPerfPo.java 2015年1月14日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao;

import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMDISKINFO;

/**
 * 虚拟机硬盘指标信息
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月14日 上午9:47:00
 */
public class SrvDiskPerfPo extends PMDISKINFO {

    private String collectTime;

    private String srvId;
    
    private String resPoolId;

    /**
     * 获取collectTime字段数据
     * @return Returns the collectTime.
     */
    public String getCollectTime() {
        return collectTime;
    }

    /**
     * 设置collectTime字段数据
     * @param collectTime The collectTime to set.
     */
    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * 获取srvId字段数据
     * @return Returns the srvId.
     */
    public String getSrvId() {
        return srvId;
    }

    /**
     * 设置srvId字段数据
     * @param srvId The srvId to set.
     */
    public void setSrvId(String srvId) {
        this.srvId = srvId;
    }

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

}
