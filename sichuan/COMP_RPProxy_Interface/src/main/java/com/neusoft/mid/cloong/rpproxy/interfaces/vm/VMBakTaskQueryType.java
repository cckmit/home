/*******************************************************************************
 * @(#)VMBakTaskType.java 2015年3月2日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

/**
 * 要查询的虚拟机备份任务类型
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月2日 下午2:38:18
 */
public enum VMBakTaskQueryType {

    /**
     * 备份状态
     */
    BAK_TYPE(0),
    /**
     * 恢复状态
     */
    RESOTRE_TYPE(1);

    private int value;

    private VMBakTaskQueryType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

}
