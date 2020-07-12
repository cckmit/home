/*******************************************************************************
 * @(#)PMResCountBean.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean;

/**
 * 备份存储计量信息Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 上午9:22:20
 */
public class VMBAKResCountBean extends BaseResCountBean {

    /**
     * 备份存储ID
     */
    private String vmbakId;

    /**
     * 存储空间
     */
    private float sizeUsed;

    /**
     * 获取bkId字段数据
     * @return Returns the bkId.
     */
    public String getBkId() {
        return vmbakId;
    }

    /**
     * 设置bkId字段数据
     * @param bkId The bkId to set.
     */
    public void setVMBAKId(String bkId) {
        this.vmbakId = bkId;
    }

    /**
     * 获取sizeUsed字段数据
     * @return Returns the sizeUsed.
     */
    public float getSizeUsed() {
        return sizeUsed;
    }

    /**
     * 设置sizeUsed字段数据
     * @param sizeUsed The sizeUsed to set.
     */
    public void setSizeUsed(float sizeUsed) {
        this.sizeUsed = sizeUsed;
    }

}
