/*******************************************************************************
 * @(#)PMOperatorReq.java 2013-2-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMOperatorType;

/**
 * 物理机操作接口请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-4 下午05:05:45
 */
public class PMOperatorReq extends ReqBodyInfo {
    /**
     * 物理机编码
     */
    private String pmId;

    /**
     * 操作类型 ----1：启动，2：暂停，3：恢复，4：停止，5：重启
     */
    private PMOperatorType type;
    
    
    

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    /**
     * 获取type字段数据
     * @return Returns the type.
     */
    public PMOperatorType getType() {
        return type;
    }

    /**
     * 设置type字段数据
     * @param type The type to set.
     */
    public void setType(PMOperatorType type) {
        this.type = type;
    }

}
