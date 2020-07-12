/*******************************************************************************
 * @(#)ImgAllAction.java 2013-1-23
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.common.vm.image;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取镜像
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.1 $ 2013-1-23 上午11:19:27
 */
public class ImageAction extends BaseAction {

    private static final long serialVersionUID = -989392324274036288L;

    private static LogService logger = LogService.getLogger(ImageAction.class);

    private String SUCCESS = "success";

    private List<VmImage> vmImages;

    private String osId;

    private String os;

    private String osVer;

    private String osType;

    private String osName;

    private String osDesc;

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {
        VmImage vmImage = new VmImage();
        vmImage.setOsId(osId);
        vmImage.setOs(os);
        vmImage.setOsVer(osVer);
        vmImage.setOsType(osType);
        vmImage.setOsName(osName);
        vmImage.setOsDesc(osDesc);
        try {
            vmImages = ibatisDAO.getData("getVmImages", vmImage);
        } catch (SQLException e) {
            logger.error("获取镜像信息失败！", e);
        } catch (Exception e2) {
            logger.error("获取镜像信息失败！", e2);
        }
        return SUCCESS;
    }

    /**
     * @return the vmImages
     */
    public List<VmImage> getVmImages() {
        return vmImages;
    }

    /**
     * @param osId the osId to set
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * @param os the os to set
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * @param osVer the osVer to set
     */
    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @param osDesc the osDesc to set
     */
    public void setOsDesc(String osDesc) {
        this.osDesc = osDesc;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

}
