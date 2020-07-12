/*******************************************************************************
 * @(#)StandardReqCommon.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 规格请求公有属性
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午10:42:41
 */
public class StandardReqCommon extends ReqBodyInfo {
    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 计量方式
     */
    private String mesureMode;

    /**
     * 规格描述
     */
    private String standardDesc;

    /**
     * 规格状态
     */
    private String status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 模板名称
     */
    private String standardName;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getMesureMode() {
        return mesureMode;
    }

    public void setMesureMode(String mesureMode) {
        this.mesureMode = mesureMode;
    }

    public String getStandardDesc() {
        return standardDesc;
    }

    public void setStandardDesc(String standardDesc) {
        this.standardDesc = standardDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取standardName字段数据
     * @return Returns the standardName.
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     * 设置standardName字段数据
     * @param standardName The standardName to set.
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }
}
