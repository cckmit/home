/*******************************************************************************
 * @(#)VMStateQueue.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.concurrent.DelayQueue;

import com.neusoft.mid.cloong.host.vm.VMStateQueueItem;

/**
 * 虚拟机状态查询队列，队列中的元素具有到期时间，当元素到期时方可从队列中取出。
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:51:46
 */
public class VMStateQueue {

    private DelayQueue<VMStateQueueItem> vmStateRequest = new DelayQueue<VMStateQueueItem>();

    public void setVmStateRequest(DelayQueue<VMStateQueueItem> vmStateRequest) {
        this.vmStateRequest = vmStateRequest;
    }

    /**
     * 向队列中添加虚拟机状态查询请求
     */
    public void add(VMStateQueueItem item) {
        vmStateRequest.offer(item);
    }

    /**
     * 从队列中获取虚拟机状态查询请求，当队列为空或者没有到期元素时阻塞
     * @throws InterruptedException
     */
    public VMStateQueueItem obtainWait() throws InterruptedException {
        return vmStateRequest.take();
    }

    /**
     * 从队列中获取虚拟机状态查询请求，当队列为空或者没有到期元素时返回null
     * @return
     */
    public VMStateQueueItem obtain() {
        return vmStateRequest.poll();
    }

    /**
     * 获取当前队列容量
     * @return
     */
    public int obtainCapacity() {
        return vmStateRequest.size();
    }
}
