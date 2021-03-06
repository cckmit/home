/*******************************************************************************
 * @(#)VMStateQueueItem.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 虚拟机状态查询请求，虚拟机操作成功时向虚拟机状态队列中添加
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:54:53
 */
public class VMStateQueueItem implements Delayed {

    /**
     * 元素到期时间
     */
    private final long trigger;

    /**
     * 虚拟机操作流水号
     */
    private String serialNum;

    /**
     * 虚拟机编码
     */
    private String vmId;

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

    public VMStateQueueItem(long millionSecond) {
        this.trigger = TimeUnit.NANOSECONDS.convert(millionSecond, TimeUnit.MILLISECONDS)
                + System.nanoTime();
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public int compareTo(Delayed o) {
        VMStateQueueItem that = (VMStateQueueItem) o;
        long d = (getDelay(TimeUnit.NANOSECONDS) - that.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VMStateQueueItem) {
            VMStateQueueItem that = (VMStateQueueItem) o;
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
