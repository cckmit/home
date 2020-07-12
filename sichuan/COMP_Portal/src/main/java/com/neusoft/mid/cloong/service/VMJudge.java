/*******************************************************************************
 * @(#)VMRebootjudge.java 2013-2-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.service;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机操作状态判断者
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-19 下午03:57:38
 */
public class VMJudge {

    private static LogService logger = LogService.getLogger(VMJudge.class);

    /**
     * 判断是否可用进行虚拟机重启操作
     * @param status 虚拟机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeReboot(VMStatus status, Holder<VMOperatorErrorInfo> holder) {
        if (status.equals(VMStatus.RUNNING) || status.equals(VMStatus.PAUSE)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机只有在运行中，暂停状态可以进行重启操作，当前虚拟机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行虚拟机开机操作
     * @param status 虚拟机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeStart(VMStatus status, Holder<VMOperatorErrorInfo> holder) {
        if (status.equals(VMStatus.STOP)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机只有在关机状态可以进行开机操作，当前虚拟机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行虚拟机暂停操作
     * @param status 虚拟机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgePause(VMStatus status, Holder<VMOperatorErrorInfo> holder) {
        if (status.equals(VMStatus.RUNNING)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机只有在运行中状态可以进行暂停操作，当前虚拟机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行虚拟机恢复操作
     * @param status 虚拟机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeResume(VMStatus status, Holder<VMOperatorErrorInfo> holder) {
        if (status.equals(VMStatus.PAUSE)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机只有在暂停状态可以进行恢复操作，当前虚拟机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行虚拟机关机操作
     * @param status 虚拟机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeStop(VMStatus status, Holder<VMOperatorErrorInfo> holder) {
        if (status.equals(VMStatus.RUNNING) || status.equals(VMStatus.PAUSE)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机只有在运行中，暂停状态可以进行停止操作，当前虚拟机的状态为[").append(status.getDesc()).append("]");
        holder.value.setErrorMessage(sb.toString());
        holder.value.setErrorCode("1");
        logger.info(sb.toString());
        return false;
    }

    /**
     * 判断是否可用进行虚拟机删除操作
     * @param status 虚拟机状态
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeDelete(VMStatus status, Holder<VMOperatorErrorInfo> holder) {
        if (status.equals(VMStatus.RUNNING) || status.equals(VMStatus.PAUSE)
                || status.equals(VMStatus.STOP) || status.equals(VMStatus.OPERATE_FAIL)
                || status.equals(VMStatus.SENDERROR) || status.equals(VMStatus.STATUSERROR)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        holder.value.setErrorCode("1");
        sb.append("虚拟机只有在运行中,暂停,关机，操作失败，发送失败，状态异常时可以进行删除操作，当前虚拟机的状态为[").append(status.getDesc())
                .append("]");
        holder.value.setErrorMessage(sb.toString());
        logger.info(sb.toString());
        return false;
    }
}
