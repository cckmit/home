/*******************************************************************************
 * @(#)VmImage.java 2013-1-23
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.common.vm.image;

/**
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.1 $ 2013-1-23 下午2:26:17
 */
public class VmImage {
    
    private String osId;
    
    private String os;
    
    private String osVer;
    
    private String osType;
    
    private String osName;
    
    private String osDesc;

    /**
     * @return the osId
     */
    public String getOsId() {
        return osId;
    }

    /**
     * @param osId the osId to set
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * @return the os
     */
    public String getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * @return the osVer
     */
    public String getOsVer() {
        return osVer;
    }

    /**
     * @param osVer the osVer to set
     */
    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    /**
     * @return the osType
     */
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @return the osDesc
     */
    public String getOsDesc() {
        return osDesc;
    }

    /**
     * @param osDesc the osDesc to set
     */
    public void setOsDesc(String osDesc) {
        this.osDesc = osDesc;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }
    
}
