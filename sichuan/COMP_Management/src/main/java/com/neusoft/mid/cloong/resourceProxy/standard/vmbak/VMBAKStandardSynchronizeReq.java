/*******************************************************************************
 * @(#)VMBAKStandardSynchronizeReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vmbak;

import com.neusoft.mid.cloong.resourceProxy.standard.common.StandardSynchronizeReq;

/**
 * 向资源池同步虚拟机备份规格信息请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 下午04:47:31
 */
public class VMBAKStandardSynchronizeReq extends StandardSynchronizeReq {

    /**
     * 备份空间大小
     */
    private String spiceSize;

    /**
     * 获取spiceSize字段数据
     * @return Returns the spiceSize.
     */
    public String getSpiceSize() {
        return spiceSize;
    }

    /**
     * 设置spiceSize字段数据
     * @param spiceSize The spiceSize to set.
     */
    public void setSpiceSize(String spiceSize) {
        this.spiceSize = spiceSize;
    }

}
