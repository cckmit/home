/*******************************************************************************
 * @(#)PMStateQueueItem.java 2014-1-22
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 物理机状态查询请求，物理机操作成功时向物理机状态队列中添加
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-22 上午1:20:14
 */
public class PMStateQueueItem implements Delayed {

    /**
     * 元素到期时间
     */
    private final long trigger;

    /**
     * 物理操作流水号
     */
    private String serialNum;

    /**
     * 物理编码
     */
    private String pmId;

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

    public PMStateQueueItem(long millionSecond) {
        this.trigger = TimeUnit.NANOSECONDS.convert(millionSecond, TimeUnit.MILLISECONDS)
                + System.nanoTime();
    }

    @Override
    public int compareTo(Delayed o) {
        PMStateQueueItem that = (PMStateQueueItem) o;
        long d = (getDelay(TimeUnit.NANOSECONDS) - that.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PMStateQueueItem) {
            PMStateQueueItem that = (PMStateQueueItem) o;
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
     * 获取pmId字段数据
     * @return Returns the pmId.
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * 设置pmId字段数据
     * @param pmId The pmId to set.
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
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

}
