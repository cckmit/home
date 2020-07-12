/*******************************************************************************
 * @(#)PMRebootjudge.java 2013-2-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.service;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.pm.core.PMStatus;
import com.neusoft.mid.cloong.web.page.user.order.PMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机操作状态判断者
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-19 下午03:57:38
 */
public class PMJudge {

    private static LogService logger = LogService.getLogger(PMJudge.class);

    /**
     * 判断是否可用进行物理机重启操作
     * @param status 物理机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeReboot(PMStatus status, Holder<PMOperatorErrorInfo> holder) {
        if (status.equals(PMStatus.RUNNING) ) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("物理机只有在运行中状态可以进行重启操作，当前物理机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行物理机开机操作
     * @param status 物理机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeStart(PMStatus status, Holder<PMOperatorErrorInfo> holder) {
        if (status.equals(PMStatus.STOP)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("物理机只有在停止状态可以进行开机操作，当前物理机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行物理机关机操作
     * @param status 物理机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeStop(PMStatus status, Holder<PMOperatorErrorInfo> holder) {
        if (status.equals(PMStatus.RUNNING)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("物理机只有在运行中状态可以进行停止操作，当前物理机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行物理机删除操作
     * @param status 物理机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeDelete(PMStatus status, Holder<PMOperatorErrorInfo> holder) {
        if (status.equals(PMStatus.RUNNING) 
                || status.equals(PMStatus.STOP) || status.equals(PMStatus.OPERATE_FAIL)
                || status.equals(PMStatus.SENDERROR) || status.equals(PMStatus.STATUSERROR)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        holder.value.setErrorCode("1");
        sb.append("物理机只有在运行中,关机，操作失败，发送失败，状态异常时可以进行删除操作，当前物理机的状态为[").append(status.getDesc())
                .append("]");
        holder.value.setErrorMessage(sb.toString());
        logger.info(sb.toString());
        return false;
    }
}
