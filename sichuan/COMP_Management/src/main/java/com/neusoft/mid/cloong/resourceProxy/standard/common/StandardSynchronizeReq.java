/*******************************************************************************
 * @(#)StandardSynchronizeReq.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.common;

import java.util.List;

/**
 * v 向资源池同步规格信息请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午09:17:06
 */
public class StandardSynchronizeReq {
    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 计量方式
     */
    private String measureMode;

    /**
     * 规格描述
     */
    private String standardDesc;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 规格ID与资源池对应ID
     */
    private String templateId;

    /**
     * 资源池信息列表
     */
    private List<ResourcePoolInfo> resourceInfos;

    /**
     * 规格名称
     */
    private String standardName;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getMeasureMode() {
        return measureMode;
    }

    public void setMeasureMode(String measureMode) {
        this.measureMode = measureMode;
    }

    public String getStandardDesc() {
        return standardDesc;
    }

    public void setStandardDesc(String standardDesc) {
        this.standardDesc = standardDesc;
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

    public List<ResourcePoolInfo> getResourceInfos() {
        return resourceInfos;
    }

    public void setResourceInfos(List<ResourcePoolInfo> resourceInfos) {
        this.resourceInfos = resourceInfos;
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
