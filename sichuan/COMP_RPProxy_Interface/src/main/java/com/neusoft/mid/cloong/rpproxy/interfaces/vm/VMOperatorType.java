/*******************************************************************************
 * @(#)VMOperatorType.java 2015年2月26日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

/**
 * 虚拟机操作类型枚举
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月26日 上午11:25:13
 */
public enum VMOperatorType {

    /**
     * 启动
     */
    START(1),
    /**
     * 暂停/挂起
     */
    PAUSE(2),
    /**
     * 恢复
     */
    RESUME(3),
    /**
     * 停止
     */
    STOP(4),
    /**
     * 重启
     */
    REBOOT(5);

    private int value;

    private VMOperatorType(int value) {
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
    public static VMOperatorType fromValue(int v) {
        for (VMOperatorType c : VMOperatorType.values()) {
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
    public static VMOperatorType fromValue(String v) {
        for (VMOperatorType c : VMOperatorType.values()) {
            if (c.value == Integer.valueOf(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
