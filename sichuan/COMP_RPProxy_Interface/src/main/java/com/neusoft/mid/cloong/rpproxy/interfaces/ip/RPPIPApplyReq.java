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
public class RPPIPApplyReq extends RPPBaseReq implements Serializable {

    private static final long serialVersionUID = -4592576402763320376L;

    /**
     * 选择传参的标识符
     */
    private IPCreateModel createModel;

    /**
     * 模板ID
     */
    private String standardId;

    /**
     * 要创建的IP数量，仅针对公网IP有效
     */
    private int count = RPPBaseReq.INT_DEFAULT_VAL;

    /**
     * 业务编码
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * IP段唯一标识
     */
    private String ipSegmentURI;

    /**
     * IP类型
     */
    private IPProType ipProType;

    /**
     * IP用途类型
     */
    private IPType ipType;

    /**
     * 获取createModel字段数据
     * @return Returns the createModel.
     */
    public IPCreateModel getCreateModel() {
        return createModel;
    }

    /**
     * 设置createModel字段数据
     * @param createModel The createModel to set.
     */
    public void setCreateModel(IPCreateModel createModel) {
        this.createModel = createModel;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取count字段数据
     * @return Returns the count.
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置count字段数据
     * @param count The count to set.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
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

    /**
     * 获取ipProType字段数据
     * @return Returns the ipProType.
     */
    public IPProType getIpProType() {
        return ipProType;
    }

    /**
     * 设置ipProType字段数据
     * @param ipProType The ipProType to set.
     */
    public void setIpProType(IPProType ipProType) {
        this.ipProType = ipProType;
    }

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
