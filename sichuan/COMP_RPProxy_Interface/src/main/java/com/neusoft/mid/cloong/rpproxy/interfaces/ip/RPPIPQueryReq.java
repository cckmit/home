/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ip;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 块存储创建请求类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月25日 下午4:31:14
 */
public class RPPIPQueryReq extends RPPBaseReq implements Serializable {

    private static final long serialVersionUID = -4592576402763320376L;

    /**
     * 选择传参的标识符:默认为私网IP
     */
    private IPType ipType = IPType.PRIVATE_IP;

    /**
     * 获取ipType字段数据
     * @return Returns the ipType.
     */
    public IPType getIpType() {
        return ipType;
    }

    /**
     * 设置ipType字段数据
     * @param ipType The ipType to set.
     */
    public void setIpType(IPType ipType) {
        this.ipType = ipType;
    }

}
