/*******************************************************************************
 * @(#)VMStatus.java 2013-2-617
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.core;

/**
 * 服务条目状态码
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-6 上午09:42:15
 */
public enum ItemStatus {
    /**
     * 保存
     */
    CREATING("0", "已保存"),
    /**
     * 待审批
     */
    PENDING("1", "条目待审"),
    /**
     * 审批通过
     */
    PENDPASS("2", "审批通过"),
    /**
     * 审批不通过
     */
    UN_PENDPASS("3", "审批不通过"),
    /**
     * 发布待审批
     */
    PUBLISH("4", "发布待审"),
    /**
     * 发布审批通过
     */
    PUBLISHPASS("5", "已发布"),
    /**
     * 发布审批不通过
     */
    UN_PUBLISHPASS("6", "发布不通过"),
    /**
     * 下架
     */
    PULLED("7", "下架"),
    /**
     * 删除
     */
    DELETED("8", "删除");

    private String code;

    private String desc;

    ItemStatus(String code, String desc) {
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
