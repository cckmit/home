/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 块存储创建请求类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月25日 下午4:31:14
 */
public class RPPEBSCreateReq extends RPPBaseReq implements Serializable {

    private static final long serialVersionUID = -4592576402763320376L;

    /**
     * 选择传参的标识符
     */
    private EBSCreateModel createModel;

    /**
     * 模板ID
     */
    private String standardId;

    /**
     * 参数集合
     */
    private BsParam BSParamArray;

    /**
     * 资源的唯一标识，当ResourceType为1时有效
     */
    private String ResourceID;

    /**
     * 块存储名称
     */
    private String ebsName;

    /**
     * 业务编码
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

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
     * 获取ebsName字段数据
     * @return Returns the ebsName.
     */
    public String getEbsName() {
        return ebsName;
    }

    /**
     * 设置ebsName字段数据
     * @param ebsName The ebsName to set.
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    /**
     * 获取createModel字段数据
     * @return Returns the createModel.
     */
    public EBSCreateModel getCreateModel() {
        return createModel;
    }

    /**
     * 设置createModel字段数据
     * @param createModel The createModel to set.
     */
    public void setCreateModel(EBSCreateModel createModel) {
        this.createModel = createModel;
    }

    /**
     * 获取bSParamArray字段数据
     * @return Returns the bSParamArray.
     */
    public BsParam getBSParamArray() {
        return BSParamArray;
    }

    /**
     * 设置bSParamArray字段数据
     * @param bSParamArray The bSParamArray to set.
     */
    public void setBSParamArray(BsParam bSParamArray) {
        BSParamArray = bSParamArray;
    }

    /**
     * 获取resourceID字段数据
     * @return Returns the resourceID.
     */
    public String getResourceID() {
        return ResourceID;
    }

    /**
     * 设置resourceID字段数据
     * @param resourceID The resourceID to set.
     */
    public void setResourceID(String resourceID) {
        ResourceID = resourceID;
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

}
