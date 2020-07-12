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
public class VMResCountBean extends BaseResCountBean {

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 周期内运行时间
     */
    private float runTime;

    /**
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
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
