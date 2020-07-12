/*******************************************************************************
 * @(#)VMStateQueueItem.java 2014-4-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 虚拟机备份状态查询请求，虚拟机备份操作成功时向虚拟机备份状态队列中添加
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-4-24 上午11:47:22
 */
public class VmBakStateQueueItem implements Delayed {

    /**
     * 元素到期时间
     */
    private final long trigger;

    /**
     * 虚拟机操作流水号
     */
    private String serialNum;

    /**
     * 虚拟机备份编码
     */
    private String vmBakId;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 资源池密钥
     */
    private String password;

    /**
     * 资源池URL
     */
    private String resourceUrl;

    /**
     * 查询次数，默认为1
     */
    private int time = 1;

    /**
     * 
     * 创建一个新的实例 VmBakStateQueueItem
     * @param millionSecond 时长
     */
    public VmBakStateQueueItem(long millionSecond) {
        this.trigger = TimeUnit.NANOSECONDS.convert(millionSecond, TimeUnit.MILLISECONDS)
                + System.nanoTime();
    }

    /**
     * 获取vmBakId字段数据
     * @return Returns the vmBakId.
     */
    public String getVmBakId() {
        return vmBakId;
    }

    /**
     * 设置vmBakId字段数据
     * @param vmBakId The vmBakId to set.
     */
    public void setVmBakId(String vmBakId) {
        this.vmBakId = vmBakId;
    }

    /**
     * 获取serialNum字段数据
     * @return Returns the serialNum.
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 设置serialNum字段数据
     * @param serialNum The serialNum to set.
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取resourcePoolId字段数据
     * @return Returns the resourcePoolId.
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 设置resourcePoolId字段数据
     * @param resourcePoolId The resourcePoolId to set.
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * 获取resourcePoolPartId字段数据
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 设置resourcePoolPartId字段数据
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 获取password字段数据
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password字段数据
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取resourceUrl字段数据
     * @return Returns the resourceUrl.
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * 设置resourceUrl字段数据
     * @param resourceUrl The resourceUrl to set.
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    /**
     * 获取time字段数据
     * @return Returns the time.
     */
    public int getTime() {
        return time;
    }

    /**
     * 设置time字段数据
     * @param time The time to set.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * getDelay
     * @param unit TimeUnit
     * @return 等待时长
     */
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * compareTo 比较
     * @param o Delayed
     * @return  int
     */
    public int compareTo(Delayed o) {
        VmBakStateQueueItem that = (VmBakStateQueueItem) o;
        long d = (getDelay(TimeUnit.NANOSECONDS) - that.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VmBakStateQueueItem) {
            VmBakStateQueueItem that = (VmBakStateQueueItem) o;
            long d = (getDelay(TimeUnit.NANOSECONDS) - that.getDelay(TimeUnit.NANOSECONDS));
            if (d == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(trigger).intValue();
    }
}
