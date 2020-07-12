/*******************************************************************************
 * @(#)QueryBusiness.java 2014年2月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business.bean;

import java.util.List;

import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;

/**
 * 业务信息查询条件
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月15日 下午1:44:13
 */
public class QueryBusiness extends BusinessInfo {

    /**
     * 从哪个日期开始
     */
    private String afterCreateTime;

    /**
     * 到哪个日期结束
     */
    private String beforeCreateTime;

    /**
     * 业务IDlist
     */
    private List<String> businessList;

    /**
     * 获取afterCreateTime字段数据
     * @return Returns the afterCreateTime.
     */
    public String getAfterCreateTime() {
        return afterCreateTime;
    }

    /**
     * 设置afterCreateTime字段数据
     * @param afterCreateTime The afterCreateTime to set.
     */
    public void setAfterCreateTime(String afterCreateTime) {
        this.afterCreateTime = afterCreateTime;
    }

    /**
     * 获取beforeCreateTime字段数据
     * @return Returns the beforeCreateTime.
     */
    public String getBeforeCreateTime() {
        return beforeCreateTime;
    }

    /**
     * 设置beforeCreateTime字段数据
     * @param beforeCreateTime The beforeCreateTime to set.
     */
    public void setBeforeCreateTime(String beforeCreateTime) {
        this.beforeCreateTime = beforeCreateTime;
    }

    /**
     * 获取businessList字段数据
     * @return Returns the businessList.
     */
    public List<String> getBusinessList() {
        return businessList;
    }

    /**
     * 设置businessList字段数据
     * @param businessList The businessList to set.
     */
    public void setBusinessList(List<String> businessList) {
        this.businessList = businessList;
    }

}
