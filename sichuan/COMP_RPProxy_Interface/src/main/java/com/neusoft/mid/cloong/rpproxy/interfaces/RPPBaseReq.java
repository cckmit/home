/*******************************************************************************
 * @(#)BaseReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces;

import java.io.Serializable;

/**
 * 前台请求资源池代理的基类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 下午1:30:50
 */
public abstract class RPPBaseReq implements Serializable {

    public static final int INT_DEFAULT_VAL = -1;

    /**
     * 请求Bean的attribute key
     */
    public static final String REQ_BODY = "request_body";

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1419769305523571526L;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 请求流水号
     */
    private String serialNum;

    /**
     * 获取ressourcePoolId字段数据
     * @return Returns the ressourcePoolId.
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 设置resourcePoolId字段数据
     * @param resourcePoolId The resourcePoolId to set.
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * 获取resourcePoolPartId字段数据
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 设置resourcePoolPartId字段数据
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 获取serialNum字段数据
     * @return Returns the serialNum.
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 设置serialNum字段数据
     * @param serialNum The serialNum to set.
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

}
