/*******************************************************************************
 * @(#)PMResCountBean.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean;

/**
 * 物理机计量信息Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 上午9:22:20
 */
public class PMResCountBean extends BaseResCountBean {

    /**
     * 物理机ID
     */
    private String pmId;

    /**
     * 周期内运行时间
     */
    private float runTime;

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
     * 获取runTime字段数据
     * @return Returns the runTime.
     */
    public float getRunTime() {
        return runTime;
    }

    /**
     * 设置runTime字段数据
     * @param runTime The runTime to set.
     */
    public void setRunTime(float runTime) {
        this.runTime = runTime;
    }

}
