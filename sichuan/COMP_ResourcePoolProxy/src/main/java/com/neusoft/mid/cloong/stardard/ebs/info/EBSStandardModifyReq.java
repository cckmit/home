/*******************************************************************************
 * @(#)VMstandardCreateReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.ebs.info;

import com.neusoft.mid.cloong.stardard.StandardReqCommon;

/**
 * 虚拟硬盘资源规格修改请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:43:39
 */
public class EBSStandardModifyReq extends StandardReqCommon {

    /**
     * 硬盘容量(GB)
     */
    private String storageSize;

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

}
