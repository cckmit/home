/*******************************************************************************
 * @(#)PMStateQueryProcessor.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

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
import com.neusoft.mid.cloong.host.pm.PMInfo;
import com.neusoft.mid.cloong.host.pm.PMStateQuery;
import com.neusoft.mid.cloong.host.pm.PMStateQueryErrorInfo;
import com.neusoft.mid.cloong.host.pm.PMStateQueryReq;
import com.neusoft.mid.cloong.host.pm.PMStateQueueItem;
import com.neusoft.mid.cloong.host.pm.PMStateQueueItemInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMStatus;
import com.neusoft.mid.grains.commons.route.core.IService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机状态查询服务
 * 从物理机状态查询队列中取出查询数据，然后调用状态查询接口向资源池请求状态信息,如果是预期状态则更新数据库中物理机状态，如果非预期状态则进行间隔重发，
 * 当达到最大重发次数则更新数据库中物理机状态为异常，并记录查询失败记录表。(更新物理机状态、最大重发次数6后更新状态异常和更新查询失败记录表）
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:46:45
 */
public class PMStateQueryService implements Runnable, IService {
    /**
     * 服务运行状态
     */
    private volatile boolean isRun = false;

    private static LogService logger = LogService.getLogger(PMStateQueryService.class);

    /**
     * 是否启动时处理指定状态的数据
     */
    private boolean isProcessOnBoot = false;

    /**
     * 物理机状态查询队列
     */
    private PMStateQueue pmStateQueue;

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
     * 物理机状态查询接口
     */
    private PMStateQuery pmStateQuery;

    /**
     * 请求最大发送次数
     */
    private int pmMaxTransTime = 6;

    /**
     * 从队列中取数据线程
     */
    private Thread thread;

    private IbatisDAO ibatisDao;

    /**
     * 重发请求时间间隔(ms)
     */
    private long pmRetransInterval = 5000l;

    /**
     * 预期状态列表
     */
    private Set<String> futureStatusSet;

    /**
     * 物理机当前状态
     */
    private Set<String> currentStatus;

    @Override
    public void run() {
        while (isRun) {
            PMStateQueueItem item = null;
            try {
                item = pmStateQueue.obtainWait();
            } catch (InterruptedException e) {
                logger.error(CommonStatusCode.THREAD_INTERRUPT, "当前线程" + Thread.currentThread().getName() + "被打断！", e);
            }
            logger.info("物理机编号为" + item.getPmId() + "的物理机进行第" + item.getTime() + "次状态查询，本次操作流水号为："
                    + item.getSerialNum());
            // 组装请求
            PMStateQueryReq req = assmbleReq(item);
            // 向资源池请求物理机状态
            RPPPMStatusQueryResp resp = pmStateQuery.queryPMState(req);
            // 当达到最大重发次数且请求响应码异常
            if (item.getTime() == pmMaxTransTime
                    && !resp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
                logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询请求已达到最大查询次数，查询响应码异常，异常码为[" + resp.getResultCode()
                        + "]，异常原因为[" + resp.getResultMessage() + "]，将更新该物理机状态为异常，并把错误信息插入物理机状态查询失败表,本次操作流水号为:"
                        + item.getSerialNum());
                updatePMStatus(assmbleErrorPMStatusInfo(req));
                // insertPMQueryErrorInfo(assmbleResultcodeErrorInfo(req, resp));
                continue;
            }
            // 当达到最大重发次数且请求状态为操作失败
            if (item.getTime() == pmMaxTransTime
                    && (PMStatus.OPERATE_FAIL.equals(resp.getPmStatus()) || PMStatus.STATUSERROR.equals(resp
                            .getPmStatus()))) {
                logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询请求已达到最大查询次数，查询响应状态非操作失败，将更新该物理机状态为"
                        + resp.getPmStatus().getCode() + "，并把错误信息插入物理机状态查询失败表，本次操作流水号为：" + item.getSerialNum());
                updatePMStatus(assmblePMStatusInfo(req, resp));
                // insertPMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            // 当达到最大重发次数且请求状态非预期
            if (item.getTime() == pmMaxTransTime && !futureStatusSet.contains(resp.getPmStatus().getCode())) {
                logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询请求已达到最大查询次数，查询响应状态非预期状态，将更新该物理机状态为"
                        + resp.getPmStatus().getCode() + "，并把错误信息插入物理机状态查询失败表，本次操作流水号为：" + item.getSerialNum());
                updatePMStatus(assmblePMStatusInfo(req, resp));
                // insertPMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }

            // 查询响应码异常，重新组装查询请求，根据设定时间延迟查询
            if (!resp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
                logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询响应码异常，异常码为[" + resp.getResultCode() + "]，异常原因为["
                        + resp.getResultMessage() + "，将重新查询该物理机状态，本次操作流水号为：" + item.getSerialNum());
                pmStateQueue.add(assmbleDelayItem(item));
                continue;
            }
            // 物理机查询状态为操作失败，更新数据库状态为操作失败
            if (PMStatus.OPERATE_FAIL.equals(resp.getPmStatus()) || PMStatus.STATUSERROR.equals(resp.getPmStatus())) {
                logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询响应状态为异常状态，将重新查询该物理机状态，本次操作流水号为：" + item.getSerialNum());
                pmStateQueue.add(assmbleDelayItem(item));
                // insertPMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            // 查询状态不是预期状态，重新组装查询请求，根据设定时间延迟查询
            if (!futureStatusSet.contains(resp.getPmStatus().getCode())) {
                logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询响应状态非预期状态，将重新查询该物理机状态，本次操作流水号为：" + item.getSerialNum());
                pmStateQueue.add(assmbleDelayItem(item));
                continue;
            }
            logger.info("物理机编号为" + req.getPmId() + "的物理机状态查询成功，将更新物理机状态，本次操作流水号为：" + item.getSerialNum());
            updatePMStatus(assmblePMStatusInfo(req, resp));
        }
    }

    private PMStateQueueItem assmbleDelayItem(PMStateQueueItem item) {
        PMStateQueueItem newItem = new PMStateQueueItem(pmRetransInterval);
        newItem.setPassword(item.getPassword());
        newItem.setResourcePoolId(item.getResourcePoolId());
        newItem.setResourceUrl(item.getResourceUrl());
        newItem.setPmId(item.getPmId());
        newItem.setResourcePoolPartId(item.getResourcePoolPartId());
        newItem.setTime(item.getTime() + 1);
        newItem.setSerialNum(item.getSerialNum());
        return newItem;
    }

    private PMStateQueryReq assmbleReq(PMStateQueueItem req) {
        PMStateQueryReq pmReq = new PMStateQueryReq();
        pmReq.setPmId(req.getPmId());
        pmReq.setPassword(req.getPassword());
        pmReq.setResourcePoolPartId(req.getResourcePoolPartId());
        pmReq.setResourcePoolId(req.getResourcePoolId());
        pmReq.setResourceUrl(req.getResourceUrl());
        pmReq.setTimestamp(timeStampgen.generateTimeStamp());
        pmReq.setTransactionID(transactonGen.generateTransactionId(pmReq.getResourcePoolId(), pmReq.getTimestamp()));
        pmReq.setSerialNum(req.getSerialNum());
        return pmReq;
    }

    public void setTransactonGen(TransactionIdGenerator transactonGen) {
        this.transactonGen = transactonGen;
    }

    public void setTimeStampgen(TimeStampGenerator timeStampgen) {
        this.timeStampgen = timeStampgen;
    }

    public void setPmStateQuery(PMStateQuery pmStateQuery) {
        this.pmStateQuery = pmStateQuery;
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
        logger.info("物理机状态查询服务启动成功！");
        return true;
    }

    /**
     * initDate 初始化查询状态信息
     */
    private void initDate() {
        List<PMStateQueueItemInfo> pmStateQueueItemInfos = null;
        try {
            pmStateQueueItemInfos = (List<PMStateQueueItemInfo>) ibatisDao.getData("queryPMByStatus",
                    new ArrayList<String>(currentStatus));
            logger.info("重启中查询成功,长度为" + pmStateQueueItemInfos.size());
            for (PMStateQueueItemInfo pmStateQueueItemInfo : pmStateQueueItemInfos) {
                PMStateQueueItem pmStateQueueItem = new PMStateQueueItem(pmRetransInterval);
                ;
                pmStateQueueItem.setPassword(pmStateQueueItemInfo.getPassword());
                pmStateQueueItem.setResourcePoolId(pmStateQueueItemInfo.getResourcePoolId());
                pmStateQueueItem.setResourcePoolPartId(pmStateQueueItemInfo.getResourcePoolPartId());
                pmStateQueueItem.setResourceUrl(pmStateQueueItemInfo.getResourceUrl());
                pmStateQueueItem.setPmId(pmStateQueueItemInfo.getPmId());
                pmStateQueueItem.setSerialNum(sequenceGenerator.generatorPMSerialNum(SERIALNUM));
                pmStateQueue.add(pmStateQueueItem);
            }
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为重启中的物理机数据库异常", e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为重启中的物理机数据库异常", e);
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
        while (pmStateQueue.obtainCapacity() > 0) {
            PMStateQueueItem item = pmStateQueue.obtain();
            if (item == null) {
                continue;
            }
            PMStateQueryReq req = assmbleReq(item);
            RPPPMStatusQueryResp resp = pmStateQuery.queryPMState(req);

            // 如果查询失败，则直接更新物理机状态
            if (!resp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
                updatePMStatus(assmbleErrorPMStatusInfo(req));
            // insertPMQueryErrorInfo(assmbleResultcodeErrorInfo(req, resp));
                continue;
            }
            // 如果查询
            if (PMStatus.OPERATE_FAIL.equals(resp.getPmStatus()) || PMStatus.STATUSERROR.equals(resp.getPmStatus())) {
                updatePMStatus(assmblePMStatusInfo(req, resp));
                // insertPMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            if (!futureStatusSet.contains(resp.getPmStatus().getCode())) {
                updatePMStatus(assmblePMStatusInfo(req, resp));
                // insertPMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
                continue;
            }
            updatePMStatus(assmblePMStatusInfo(req, resp));
        }
        logger.info("物理机状态查询服务关闭成功！");
        return true;
    }

    @Override
    public boolean ini() {
        thread = new Thread(this);
        logger.info("物理机状态查询服务初始化成功！");
        return true;
    }

    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    private void updatePMStatus(PMInfo pmInfo) {
        try {
            ibatisDao.updateData("updatePMStatus", pmInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + pmInfo.getPmId()
                    + "】的物理机状态更新失败，物理机状态为【" + pmInfo.getPmState() + "】，本次操作流水号为：" + pmInfo.getSerialNum(), e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + pmInfo.getPmId()
                    + "】的物理机状态更新失败，物理机状态为【" + pmInfo.getPmState() + "】，本次操作流水号为：" + pmInfo.getSerialNum(), e);
        }
    }

    private void insertPMQueryErrorInfo(PMStateQueryErrorInfo errorInfo) {
        try {
            LogProcessor.process(errorInfo);
            // ibatisDao.insertData("insertPmStateQueryErrorInfo", errorInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "向日志中插入编号为【" + errorInfo.getPmId()
                    + "】的物理机状态查询错误信息错误，插入信息为：" + errorInfo.toString(), e);
        }
    }

    private PMInfo assmblePMStatusInfo(PMStateQueryReq req, RPPPMStatusQueryResp resp) {
        PMInfo pmInfo = new PMInfo();
        pmInfo.setPmId(req.getPmId());
        pmInfo.setPmState(resp.getPmStatus().getCode());
        pmInfo.setOperationIP(resp.getOperationIp());
        pmInfo.setUserName(resp.getUserName());
        pmInfo.setPassword(resp.getPassWord());
        return pmInfo;
    }

    private PMInfo assmbleErrorPMStatusInfo(PMStateQueryReq req) {
        PMInfo pmInfo = new PMInfo();
        pmInfo.setPmId(req.getPmId());
        pmInfo.setPmState(PMStatus.STATUSERROR.getCode());
        return pmInfo;
    }

    private PMStateQueryErrorInfo assmbleResultcodeErrorInfo(PMStateQueryReq req, RPPPMStatusQueryResp resp) {
        PMStateQueryErrorInfo errorInfo = new PMStateQueryErrorInfo();
        errorInfo.setPmId(req.getPmId());
        errorInfo.setFailCause(resp.getResultMessage());
        errorInfo.setFailCode(resp.getResultCode());
        errorInfo.setFutureState(futureStatusSet.toString());
        errorInfo.setResourcePoolId(req.getResourcePoolId());
        errorInfo.setResourcePoolPartId(req.getResourcePoolPartId());
        errorInfo.setCreateTime(timeStampgen.generateTimeStampYYYYMMddHHmmss());
        errorInfo.setSerialNum(req.getSerialNum());
        return errorInfo;
    }

    private PMStateQueryErrorInfo assmbleStateErrorInfo(PMStateQueryReq req, RPPPMStatusQueryResp resp) {
        PMStateQueryErrorInfo errorInfo = new PMStateQueryErrorInfo();
        errorInfo.setPmId(req.getPmId());
        errorInfo.setFailCause("物理机状态查询响应结果非预期异常，预期状态为【" + futureStatusSet.toString() + "】，实际状态为【"
                + resp.getPmStatus().getCode() + "】");
        errorInfo.setFailCode(resp.getResultCode());
        errorInfo.setFutureState(futureStatusSet.toString());
        errorInfo.setResourcePoolId(req.getResourcePoolId());
        errorInfo.setResourcePoolPartId(req.getResourcePoolPartId());
        errorInfo.setCreateTime(timeStampgen.generateTimeStampYYYYMMddHHmmss());
        errorInfo.setSerialNum(req.getSerialNum());
        return errorInfo;
    }

    public void setPmRetransInterval(long pmRetransInterval) {
        this.pmRetransInterval = pmRetransInterval;
    }

    public void setPmMaxTransTime(int pmMaxTransTime) {
        this.pmMaxTransTime = pmMaxTransTime;
    }

    public void setPmStateQueue(PMStateQueue pmStateQueue) {
        this.pmStateQueue = pmStateQueue;
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
