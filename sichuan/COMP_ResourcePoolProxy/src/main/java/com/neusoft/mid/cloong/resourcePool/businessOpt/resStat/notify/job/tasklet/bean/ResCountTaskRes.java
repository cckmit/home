/*******************************************************************************
 * @(#)ResCountTaskRes.java 2014年2月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源上报任务结果Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月13日 下午3:16:11
 */
public class ResCountTaskRes {

    /**
     * 周期计量信息在运营平台已经存在的资源ID列表
     */
    private List<String> existCountInCompResId = new ArrayList<String>();

    /**
     * 运营平台不存在的资源ID列表
     */
    private List<String> noExistInCompResId = new ArrayList<String>();

    /**
     * 获取existCountInCompResId字段数据
     * @return Returns the existCountInCompResId.
     */
    public List<String> getExistCountInCompResId() {
        return existCountInCompResId;
    }

    /**
     * 设置existCountInCompResId字段数据
     * @param existCountInCompResId The existCountInCompResId to set.
     */
    public void setExistCountInCompResId(List<String> existCountInCompResId) {
        this.existCountInCompResId = existCountInCompResId;
    }

    /**
     * 获取noExistInCompResId字段数据
     * @return Returns the noExistInCompResId.
     */
    public List<String> getNoExistInCompResId() {
        return noExistInCompResId;
    }

    /**
     * 设置noExistInCompResId字段数据
     * @param noExistInCompResId The noExistInCompResId to set.
     */
    public void setNoExistInCompResId(List<String> noExistInCompResId) {
        this.noExistInCompResId = noExistInCompResId;
    }

}
