/*******************************************************************************
 * @(#)VMStateQueue.java 2014-4-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import java.util.concurrent.DelayQueue;

/**
 * 虚拟机备份状态查询队列，队列中的元素具有到期时间，当元素到期时方可从队列中取出。
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-4-24 上午11:50:43
 */
public class VmBakStateQueue {

    /**
     * vmStateRequest
     */
    private DelayQueue<VmBakStateQueueItem> vmStateRequest = new DelayQueue<VmBakStateQueueItem>();

    /**
     * setVmStateRequest 
     * @param vmStateRequest DelayQueue<VmBakStateQueueItem>
     */
    public void setVmStateRequest(DelayQueue<VmBakStateQueueItem> vmStateRequest) {
        this.vmStateRequest = vmStateRequest;
    }

    /**
     * add 向队列中添加虚拟机备份状态查询请求
     * @param item VmBakStateQueueItem
     */
    public void add(VmBakStateQueueItem item) {
        vmStateRequest.offer(item);
    }

    /**
     * 从队列中获取虚拟机备份状态查询请求，当队列为空或者没有到期元素时阻塞
     * @return VmBakStateQueueItem
     * @throws InterruptedException 异常
     */
    public VmBakStateQueueItem obtainWait() throws InterruptedException {
        return vmStateRequest.take();
    }

    /**
     * 从队列中获取虚拟机备份状态查询请求，当队列为空或者没有到期元素时返回null
     * @return VmBakStateQueueItem
     */
    public VmBakStateQueueItem obtain() {
        return vmStateRequest.poll();
    }

    /**
     * 获取当前队列容量
     * @return int
     */
    public int obtainCapacity() {
        return vmStateRequest.size();
    }
}
