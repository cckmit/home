/*******************************************************************************
 * @(#)VMStateQueryProcessor.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.logger.LogProcessor;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.host.vm.VMInfo;
import com.neusoft.mid.cloong.host.vm.VMStateQuery;
import com.neusoft.mid.cloong.host.vm.VMStateQueryErrorInfo;
import com.neusoft.mid.cloong.host.vm.VMStateQueryReq;
import com.neusoft.mid.cloong.host.vm.VMStateQueueItem;
import com.neusoft.mid.cloong.host.vm.VMStateQueueItemInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMStatus;
import com.neusoft.mid.grains.commons.route.core.IService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机状态查询服务，从虚拟机状态查询队列中取出查询数据，然后调用状态查询接口向资源池请求状态信息,如果是预期状态则更新数据库中虚拟机状态，如果非预期状态则进行间隔重发，
 * 当达到最大重发次数则更新数据库中虚拟机状态为异常，并记录查询失败记录表。
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:46:45
 */
public class VMStateQueryService implements Runnable, IService {
    /**
     * 服务运行状态
     */
    private volatile boolean isRun = false;

    private static LogService logger = LogService.getLogger(VMStateQueryService.class);

    /**
     * 是否启动时处理指定状态的数据
     */
    private boolean isProcessOnBoot = false;

    /**
     * 虚拟机状态查询队列
     */
    private VMStateQueue vmStateQueue;

    /**
     * 消息序列号生成器
     */
    private TransactionIdGenerator transactonGen;

    /**
     * 操作码生成器
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * 生成操作码标识
     */
    private final String SERIALNUM = "I";

    /**
     * 时间戳生成器
     */
    private TimeStampGenerator timeStampgen;

    /**
     * 虚拟机状态查询接口
     */
    private VMStateQuery vmStateQuery;

    /**
     * 请求最大发送次数
     */
    private int maxTransTime = 6;

    /**
     * 从队列中取数据线程
     */
    private Thread thread;

    private IbatisDAO ibatisDao;

    /**
     * 重发请求时间间隔(ms)
     */
    private long retransInterval = 5000l;

    /**
     * 预期状态列表
     */
    private Set<String> futureStatusSet;

    /**
     * 虚拟机当前状态
     */
    private Set<String> currentStatus;

    @Override
    public void run() {
        while (isRun) {
            VMStateQueueItem item = null;
            try {
                item = vmStateQueue.obtainWait();
            } catch (InterruptedException e) {
                logger.error(CommonStatusCode.THREAD_INTERRUPT, "当前线程" + Thread.currentThread().getName() + "被打断！", e);
            }
            logger.info("虚拟机编号为" + item.getVmId() + "的虚拟机进行第" + item.getTime() + "次状态查询，本次操作流水号为："
                    + item.getSerialNum());
            // 组装请求
            VMStateQueryReq req = assmbleReq(item);
            // 向资源池请求虚拟机状态
            RPPVMStatusQueryResp resp = vmStateQuery.queryVMState(req);
            // 当达到最大重发次数且请求响应码异常
            if (item.getTime() == maxTransTime
                    && !resp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
                logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询请求已达到最大查询次数，查询响应码异常，异常码为[" + resp.getResultCode()
                        + "]，异常原因为[" + resp.getResultMessage() + "]，将更新该虚拟机状态为异常，并把错误信息插入虚拟机状态查询失败表,本次操作流水号为:"
                        + item.getSerialNum());
                updateVMStatus(assmbleErrorVMStatusInfo(req));
                // insertVMQueryErrorInfo(assmbleResultcodeErrorInfo(req, resp));
                continue;
            }
            // 当达到最大重发次数且请求状态为操作失败
            if (item.getTime() == maxTransTime
                    && (VMStatus.OPERATE_FAIL.equals(resp.getVmStatus()) || VMStatus.STATUSERROR.equals(resp
                            .getVmStatus()))) {
                logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询请求已达到最大查询次数，查询响应状态非操作失败，将更新该虚拟机状态为"
                        + resp.getVmStatus().getCode() + "，并把错误信息插入虚拟机状态查询失败表，本次操作流水号为：" + item.getSerialNum());
                updateVMStatus(assmbleVMStatusInfo(req, resp));
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            // 当达到最大重发次数且请求状态非预期
            if (item.getTime() == maxTransTime && !futureStatusSet.contains(resp.getVmStatus().getCode())) {
                logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询请求已达到最大查询次数，查询响应状态非预期状态，将更新该虚拟机状态为"
                        + resp.getVmStatus().getCode() + "，并把错误信息插入虚拟机状态查询失败表，本次操作流水号为：" + item.getSerialNum());
                updateVMStatus(assmbleVMStatusInfo(req, resp));
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }

            // 查询响应码异常，重新组装查询请求，根据设定时间延迟查询
            if (!resp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
                logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询响应码异常，异常码为[" + resp.getResultCode() + "]，异常原因为["
                        + resp.getResultMessage() + "，将重新查询该虚拟机状态，本次操作流水号为：" + item.getSerialNum());
                vmStateQueue.add(assmbleDelayItem(item));
                continue;
            }
            // 虚拟机查询状态为操作失败，更新数据库状态为操作失败
            if (VMStatus.OPERATE_FAIL.equals(resp.getVmStatus()) || VMStatus.STATUSERROR.equals(resp.getVmStatus())) {
                logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询响应状态为异常状态，将重新查询该虚拟机状态，本次操作流水号为：" + item.getSerialNum());
                vmStateQueue.add(assmbleDelayItem(item));
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            // 查询状态不是预期状态，重新组装查询请求，根据设定时间延迟查询
            if (!futureStatusSet.contains(resp.getVmStatus().getCode())) {
                logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询响应状态非预期状态，将重新查询该虚拟机状态，本次操作流水号为：" + item.getSerialNum());
                vmStateQueue.add(assmbleDelayItem(item));
                continue;
            }
            logger.info("虚拟机编号为" + req.getVmId() + "的虚拟机状态查询成功，将更新虚拟机状态，本次操作流水号为：" + item.getSerialNum());
            updateVMStatus(assmbleVMStatusInfo(req, resp));
        }
    }

    private VMStateQueueItem assmbleDelayItem(VMStateQueueItem item) {
        VMStateQueueItem newItem = new VMStateQueueItem(retransInterval);
        newItem.setPassword(item.getPassword());
        newItem.setResourcePoolId(item.getResourcePoolId());
        newItem.setResourceUrl(item.getResourceUrl());
        newItem.setVmId(item.getVmId());
        newItem.setResourcePoolPartId(item.getResourcePoolPartId());
        newItem.setTime(item.getTime() + 1);
        newItem.setSerialNum(item.getSerialNum());
        return newItem;
    }

    private VMStateQueryReq assmbleReq(VMStateQueueItem req) {
        VMStateQueryReq vmReq = new VMStateQueryReq();
        vmReq.setVmId(req.getVmId());
        vmReq.setPassword(req.getPassword());
        vmReq.setResourcePoolPartId(req.getResourcePoolPartId());
        vmReq.setResourcePoolId(req.getResourcePoolId());
        vmReq.setResourceUrl(req.getResourceUrl());
        vmReq.setTimestamp(timeStampgen.generateTimeStamp());
        vmReq.setTransactionID(transactonGen.generateTransactionId(vmReq.getResourcePoolId(), vmReq.getTimestamp()));
        vmReq.setSerialNum(req.getSerialNum());
        return vmReq;
    }

    public void setTransactonGen(TransactionIdGenerator transactonGen) {
        this.transactonGen = transactonGen;
    }

    public void setTimeStampgen(TimeStampGenerator timeStampgen) {
        this.timeStampgen = timeStampgen;
    }

    public void setVmStateQuery(VMStateQuery vmStateQuery) {
        this.vmStateQuery = vmStateQuery;
    }

    @Override
    public void pause() {

    }

    @Override
    public boolean start() {
        thread.start();
        isRun = true;
        if (isProcessOnBoot) {
            initDate();
        }
        logger.info("虚拟机状态查询服务启动成功！");
        return true;
    }

    /**
     * initDate 初始化查询状态信息
     */
    private void initDate() {
        List<VMStateQueueItemInfo> vmStateQueueItemInfos = null;
        try {
            vmStateQueueItemInfos = (List<VMStateQueueItemInfo>) ibatisDao.getData("queryVMByStatus",
                    new ArrayList<String>(currentStatus));
            logger.info("重启中查询成功,长度为" + vmStateQueueItemInfos.size());
            for (VMStateQueueItemInfo vmStateQueueItemInfo : vmStateQueueItemInfos) {
                VMStateQueueItem vmStateQueueItem = new VMStateQueueItem(retransInterval);
                ;
                vmStateQueueItem.setPassword(vmStateQueueItemInfo.getPassword());
                vmStateQueueItem.setResourcePoolId(vmStateQueueItemInfo.getResourcePoolId());
                vmStateQueueItem.setResourcePoolPartId(vmStateQueueItemInfo.getResourcePoolPartId());
                vmStateQueueItem.setResourceUrl(vmStateQueueItemInfo.getResourceUrl());
                vmStateQueueItem.setVmId(vmStateQueueItemInfo.getVmId());
                vmStateQueueItem.setSerialNum(sequenceGenerator.generatorVMSerialNum(SERIALNUM));
                vmStateQueue.add(vmStateQueueItem);
            }
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为重启中的虚拟机数据库异常", e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为重启中的虚拟机数据库异常", e);
        }
    }

    @Override
    public boolean stop() {
        isRun = false;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.error(CommonStatusCode.THREAD_INTERRUPT, "当前线程" + Thread.currentThread().getName() + "被打断！", e);
        }
        while (vmStateQueue.obtainCapacity() > 0) {
            VMStateQueueItem item = vmStateQueue.obtain();
            if (item == null) {
                continue;
            }
            VMStateQueryReq req = assmbleReq(item);
            RPPVMStatusQueryResp resp = vmStateQuery.queryVMState(req);

            // 如果查询失败，则直接更新虚拟机状态
            if (!resp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
                updateVMStatus(assmbleErrorVMStatusInfo(req));
            // insertVMQueryErrorInfo(assmbleResultcodeErrorInfo(req, resp));
                continue;
            }
            // 如果查询
            if (VMStatus.OPERATE_FAIL.equals(resp.getVmStatus()) || VMStatus.STATUSERROR.equals(resp.getVmStatus())) {
                updateVMStatus(assmbleVMStatusInfo(req, resp));
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            if (!futureStatusSet.contains(resp.getVmStatus().getCode())) {
                updateVMStatus(assmbleVMStatusInfo(req, resp));
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            updateVMStatus(assmbleVMStatusInfo(req, resp));
        }
        logger.info("虚拟机状态查询服务关闭成功！");
        return true;
    }

    @Override
    public boolean ini() {
        thread = new Thread(this);
        logger.info("虚拟机状态查询服务初始化成功！");
        return true;
    }

    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    private void updateVMStatus(VMInfo vmInfo) {
        try {
            ibatisDao.updateData("updateVMStatus", vmInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + vmInfo.getVmId()
                    + "】的虚拟机状态更新失败，虚拟机状态为【" + vmInfo.getVmState() + "】，本次操作流水号为：" + vmInfo.getSerialNum(), e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + vmInfo.getVmId()
                    + "】的虚拟机状态更新失败，虚拟机状态为【" + vmInfo.getVmState() + "】，本次操作流水号为：" + vmInfo.getSerialNum(), e);
        }
    }

    private void insertVMQueryErrorInfo(VMStateQueryErrorInfo errorInfo) {
        try {
            LogProcessor.process(errorInfo);
            // ibatisDao.insertData("insertVmStateQueryErrorInfo", errorInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "向日志中插入编号为【" + errorInfo.getVmId()
                    + "】的虚拟机状态查询错误信息错误，插入信息为：" + errorInfo.toString(), e);
        }
    }

    private VMInfo assmbleVMStatusInfo(VMStateQueryReq req, RPPVMStatusQueryResp resp) {
        VMInfo vmInfo = new VMInfo();
        vmInfo.setVmId(req.getVmId());
        vmInfo.setVmState(resp.getVmStatus().getCode());
        vmInfo.setOperationURL(resp.getOperationURL());
        vmInfo.setUserName(resp.getUserName());
        vmInfo.setPassword(resp.getPassWord());
        return vmInfo;
    }

    private VMInfo assmbleErrorVMStatusInfo(VMStateQueryReq req) {
        VMInfo vmInfo = new VMInfo();
        vmInfo.setVmId(req.getVmId());
        vmInfo.setVmState(VMStatus.STATUSERROR.getCode());
        return vmInfo;
    }

    private VMStateQueryErrorInfo assmbleResultcodeErrorInfo(VMStateQueryReq req, RPPVMStatusQueryResp resp) {
        VMStateQueryErrorInfo errorInfo = new VMStateQueryErrorInfo();
        errorInfo.setVmId(req.getVmId());
        errorInfo.setFailCause(resp.getResultMessage());
        errorInfo.setFailCode(resp.getResultCode());
        errorInfo.setFutureState(futureStatusSet.toString());
        errorInfo.setResourcePoolId(req.getResourcePoolId());
        errorInfo.setResourcePoolPartId(req.getResourcePoolPartId());
        errorInfo.setCreateTime(timeStampgen.generateTimeStampYYYYMMddHHmmss());
        errorInfo.setSerialNum(req.getSerialNum());
        return errorInfo;
    }

    private VMStateQueryErrorInfo assmbleStateErrorInfo(VMStateQueryReq req, RPPVMStatusQueryResp resp) {
        VMStateQueryErrorInfo errorInfo = new VMStateQueryErrorInfo();
        errorInfo.setVmId(req.getVmId());
        errorInfo.setFailCause("虚拟机状态查询响应结果非预期异常，预期状态为【" + futureStatusSet.toString() + "】，实际状态为【"
                + resp.getVmStatus().getCode() + "】");
        errorInfo.setFailCode(resp.getResultCode());
        errorInfo.setFutureState(futureStatusSet.toString());
        errorInfo.setResourcePoolId(req.getResourcePoolId());
        errorInfo.setResourcePoolPartId(req.getResourcePoolPartId());
        errorInfo.setCreateTime(timeStampgen.generateTimeStampYYYYMMddHHmmss());
        errorInfo.setSerialNum(req.getSerialNum());
        return errorInfo;
    }

    public void setRetransInterval(long retransInterval) {
        this.retransInterval = retransInterval;
    }

    public void setMaxTransTime(int maxTransTime) {
        this.maxTransTime = maxTransTime;
    }

    public void setVmStateQueue(VMStateQueue vmStateQueue) {
        this.vmStateQueue = vmStateQueue;
    }

    /**
     * setCurrentStatus 设置 currentStatus
     * @param currentStatus
     */
    public void setCurrentStatus(Set<String> currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * 设置sequenceGenerator字段数据
     * @param sequenceGenerator The sequenceGenerator to set.
     */
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * 设置isProcessOnBoot字段数据
     * @param isProcessOnBoot The isProcessOnBoot to set.
     */
    public void setProcessOnBoot(boolean isProcessOnBoot) {
        this.isProcessOnBoot = isProcessOnBoot;
    }

    /**
     * 设置futureStatusSet字段数据
     * @param futureStatusSet The futureStatusSet to set.
     */
    public void setFutureStatusSet(Set<String> futureStatusSet) {
        this.futureStatusSet = futureStatusSet;
    }

}
