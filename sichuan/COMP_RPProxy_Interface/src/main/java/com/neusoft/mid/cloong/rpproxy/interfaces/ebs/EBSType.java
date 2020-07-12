/*******************************************************************************
 * @(#)BSType.java 2015年3月3日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

/**
 * 块存储的类型
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月3日 下午4:02:45
 */
public enum EBSType {

    /**
     * 通用块存储
     */
    GENERAL_EBS(0),
    /**
     * 虚拟机块存储
     */
    VM_EBS(1),
    /**
     * 物理机块存储
     */
    PM_EBS(2);

    private int value;

    private EBSType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    /**
     * 更具给定的int类型码值，获取对应的枚举实例
     * @author fengj<br>
     *         2015年3月2日 上午9:55:45
     * @param v 给定的码值
     * @return 码值对应的枚举实例
     */
    public static EBSType fromValue(int v) {
        for (EBSType c : EBSType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    /**
     * 更具给定的String类型码值，获取对应的枚举实例
     * @author fengj<br>
     *         2015年3月2日 上午9:55:45
     * @param v 给定的码值
     * @return 码值对应的枚举实例
     */
    public static EBSType fromValue(String v) {
        for (EBSType c : EBSType.values()) {
            if (c.value == Integer.valueOf(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
