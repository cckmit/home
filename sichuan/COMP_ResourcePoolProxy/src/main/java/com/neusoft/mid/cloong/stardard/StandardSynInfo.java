/*******************************************************************************
 * @(#)StandardSynInfo.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

/**
 * 规格同步表实体
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午02:22:54
 */
public class StandardSynInfo {

    /** 规格ID **/
    private String standardId;// STANDARD_ID

    /** 资源池分区ID **/
    private String resPoolPartId;// RES_POOL_PART_ID;

    /** 资源池ID **/
    private String resPoolId;// RES_POOL_ID;

    /** 资源池对应ID **/
    private String templateId;// TEMPLATE_ID;

    /** 规格类型 **/
    private String standardType;// STANDARD_TYPE;

    /** 同步状态 **/
    private String status;// STATUS;

    /** 同步时间 **/
    private String synTime;// SYN_TIME;

    /** 同步人 **/
    private String synUser;// SYN_USER;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSynTime() {
        return synTime;
    }

    public void setSynTime(String synTime) {
        this.synTime = synTime;
    }

    public String getSynUser() {
        return synUser;
    }

    public void setSynUser(String synUser) {
        this.synUser = synUser;
    }

}
