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
public class RPPIPReleaseReq extends RPPBaseReq implements Serializable {

    private static final long serialVersionUID = -4592576402763320376L;

    /**
     * 选择传参的标识符
     */
    private String ip;

    /**
     * 模板ID
     */
    private String ipSegmentURI;

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取ipSegmentURI字段数据
     * @return Returns the ipSegmentURI.
     */
    public String getIpSegmentURI() {
        return ipSegmentURI;
    }

    /**
     * 设置ipSegmentURI字段数据
     * @param ipSegmentURI The ipSegmentURI to set.
     */
    public void setIpSegmentURI(String ipSegmentURI) {
        this.ipSegmentURI = ipSegmentURI;
    }

}
