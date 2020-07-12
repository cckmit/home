/*******************************************************************************
 * @(#)VMStatus.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.core;

/**
 * 服务条目类型
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-5 下午04:52:38
 */
public enum ItemType {
    /**
     *虚拟机
     */
    VMTYPE("0", "虚拟机"),
    /**
     * 物理机
     */
    PHTYPE("1", "物理机"),
    /**
     * 小型机
     */
    BKTYPE("2", "小型机"),
    /**
     * 小型机分区
     */
    MINTYPE("3", "小型机分区"),
    /**
     * 虚拟机备份
     */
    BSTYPE("4", "虚拟机备份"),
    /**
     * 虚拟硬盘
     */
    OSTYPE("5", "云硬盘"),
    /**
     * 云储存
     */
    BWTYPE("6", "云储存"),
    /**
     * 公网IP
     */
    IPTYPE("7", "公网IP"),
    /**
     * 带宽
     */
    SGTYPE("8", "带宽"),
    /**
     * 安全组
     */
    CMTYPE("9", "安全组"),
    /**
     * 系统镜像
     */
    ISOTYPE("10", "系统镜像");

    private String code;

    private String desc;

    ItemType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
