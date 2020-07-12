/*******************************************************************************
 * @(#)FirewallPerfPo.java 2015年11月4日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao;

import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMROUTERINFO;

/**
 * 路由器性能PO
 * @author <a href="mailto:wengcz@neusoft.com">wengcz</a>
 * @version $Revision 1.0 $ 2015年11月4日 上午10:32:11
 */
public class RouterPerfPo extends PMROUTERINFO {

    private String collectTime;
    
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

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

}
