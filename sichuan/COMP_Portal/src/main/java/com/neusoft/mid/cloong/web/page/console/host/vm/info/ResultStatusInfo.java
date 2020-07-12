/*******************************************************************************
 * @(#)ResultStatusInfo.java 2013-1-16
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import java.io.Serializable;

/**
 * 用于json字符串转换成java对象
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-16 下午01:08:50
 */
public class ResultStatusInfo implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 状态标识
     */
    private String status;

    /**
     * 状态汉字
     */
    private String statusText;

    /**
     * 开始生效时间（计费时间）
     */
    private String effectiveTime;

    /**
     * 到期日期
     */
    private String overTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

}
