package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao;

import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMSWITCHINFO;

/**
 * 交换机po
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年11月4日 上午10:23:00
 */
public class SwitchPerfPo extends PMSWITCHINFO {

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
