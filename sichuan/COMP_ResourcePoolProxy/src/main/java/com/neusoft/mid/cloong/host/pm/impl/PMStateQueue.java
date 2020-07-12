/*******************************************************************************
 * @(#)PMStateQueue.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.util.concurrent.DelayQueue;

import com.neusoft.mid.cloong.host.pm.PMStateQueueItem;

/**
 * 物理机状态查询队列，队列中的元素具有到期时间，当元素到期时方可从队列中取出。
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:51:46
 */
public class PMStateQueue {

    private DelayQueue<PMStateQueueItem> pmStateRequest = new DelayQueue<PMStateQueueItem>();

    public void setPmStateRequest(DelayQueue<PMStateQueueItem> pmStateRequest) {
        this.pmStateRequest = pmStateRequest;
    }

    /**
     * 向队列中添加物理机状态查询请求
     */
    public void add(PMStateQueueItem item) {
        pmStateRequest.offer(item);
    }

    /**
     * 从队列中获取物理机状态查询请求，当队列为空或者没有到期元素时阻塞
     * @throws InterruptedException
     */
    public PMStateQueueItem obtainWait() throws InterruptedException {
        return pmStateRequest.take();
    }

    /**
     * 从队列中获取物理机状态查询请求，当队列为空或者没有到期元素时返回null
     * @return
     */
    public PMStateQueueItem obtain() {
        return pmStateRequest.poll();
    }

    /**
     * 获取当前队列容量
     * @return
     */
    public int obtainCapacity() {
        return pmStateRequest.size();
    }
}
