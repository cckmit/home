/*******************************************************************************
 * @(#)VMBakTaskStat.java 2015年3月2日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

/**
 * 虚拟机备份任务的状态
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月2日 下午2:41:42
 */
public enum VMBakTaskStat {

    /**
     * 未到任务执行时间点，任务休眠汇总
     */
    SLEEPING("0"),
    /**
     * 正在执行备份
     */
    BACKUPING("1"),
    /**
     * 备份执行成功
     */
    PROCESS_SUCC("2"),
    /**
     * 备份任务已经删除
     */
    DELETED("3"),
    /**
     * 备份任务正在恢复中
     */
    RESTORING("4"),
    /**
     * 备份任务恢复失败
     */
    RESTORE_FAIL("5"),
    /**
     * 备份任务恢复失败
     */
    BACKUP_FAIL("7"),
    /**
     * 任务正在等待中
     */
    BACKUP_WAITTING("10"),
    /**
     * 状态异常.通常是从资源池获取状态失败时显示
     */
    ERROR("9");

    private String value;

    private VMBakTaskStat(String value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

}
