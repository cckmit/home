/*******************************************************************************
 * @(#)BSState.java 2015年3月3日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOperatorType;

/**
 * 块存储状态枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月3日 下午3:57:28
 */
public enum EBSStatus {

    /**
     * 创建成功
     */
    CREATED(1),
    /**
     * 已挂载
     */
    MOUNTED(2),
    /**
     * 创建失败
     */
    CREATE_FAIL(3),
    /**
     * 挂载失败
     */
    MOUNT_FAIL(4),
    /**
     * 创建中
     */
    CREATING(5);

    private int value;

    private EBSStatus(int value) {
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
    public static EBSStatus fromValue(int v) {
        for (EBSStatus c : EBSStatus.values()) {
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
    public static EBSStatus fromValue(String v) {
        for (EBSStatus c : EBSStatus.values()) {
            if (c.value == Integer.valueOf(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
