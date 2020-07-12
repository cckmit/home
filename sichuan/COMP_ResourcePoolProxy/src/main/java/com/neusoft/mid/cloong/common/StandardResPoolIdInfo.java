/*******************************************************************************
 * @(#)StandardResPoolIdInfo.java 2014-1-14
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common;

/**
 * 用于查询规格对应资源池的ID实体
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-14 上午10:53:09
 */
public class StandardResPoolIdInfo {

    /** 资源池id **/
    private String resPoolId;

    /** 资源池分区id **/
    private String resPoolPartId;

    /** 规格id **/
    private String standardId;

    /** 镜像id **/
    private String osId;

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

}
