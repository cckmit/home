/*******************************************************************************
 * @(#)VmBakStateQueryProcessor.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakTaskQueryType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakTaskStat;
import com.neusoft.mid.grains.commons.route.core.IService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机状态查询服务，从虚拟机状态查询队列中取出查询数据，然后调用状态查询接口向资源池请求状态信息,如果是预期状态则更新数据库中虚拟机状态，如果非预期状态则进行间隔重发，
 * 当达到最大重发次数则更新数据库中虚拟机状态为异常，并记录查询失败记录表。
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:46:45
 */
public class VmBakStateQueryService implements Runnable, IService {
    /**
     * 服务运行状态
     */
    private volatile boolean isRun = false;

    /**
     * logger日志
     */
    private static LogService logger = LogService.getLogger(VmBakStateQueryService.class);

    /**
     * 虚拟机状态查询队列
     */
    private VmBakStateQueue vmBakStateQueue;

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
     * 虚拟机备份状态查询接口
     */
    private VMBakTaskQuery vmBakStateQuery;

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
     * 虚拟机查询的预期状态
     */
    private Map<String, String> futureWebserviceStatus;

    /**
     * 虚拟机当前状态
     */
    private String currentStatus;

    @Override
    public void pause() {

    }

    @Override
    public boolean start() {
        thread.start();
        isRun = true;
        initDate();
        logger.info("虚拟机备份状态查询服务启动成功！");
        return true;
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
        while (vmBakStateQueue.obtainCapacity() > 0) {
            VmBakStateQueueItem item = vmBakStateQueue.obtain();
            if (item == null) {
                continue;
            }
            VMBakTaskQueryReq req = assmbleReq(item);
            RPPVMBakTaskQueryResp resp = vmBakStateQuery.query(req);
            this.processResp(item, req, resp, true);
        }
        logger.info("虚拟机备份状态查询服务关闭成功！");
        return true;
    }

    @Override
    public boolean ini() {
        thread = new Thread(this);
        logger.info("虚拟机备份状态查询服务初始化成功！");
        return true;
    }

    @Override
    public void run() {
        while (isRun) {
            try {
                VmBakStateQueueItem item = vmBakStateQueue.obtainWait();

                logger.info("虚拟机备份编号为" + item.getVmBakId() + "的虚拟机备份进行第" + item.getTime() + "次状态查询，本次操作流水号为："
                        + item.getSerialNum());

                // 组装请求
                VMBakTaskQueryReq req = assmbleReq(item);

                // 向资源池请求虚拟机备份状态
                RPPVMBakTaskQueryResp resp = vmBakStateQuery.query(req);

                // 判断应答状态，做不同处理
                processResp(item, req, resp, false);
            } catch (Exception e) {
                logger.error("查询虚拟机备份任务中间状态时,发生异常!", e);
            }

        }
    }

    /**
     * 处理应答消息
     * @author fengj<br>
     *         2015年3月5日 下午2:53:59
     * @param item
     * @param req
     * @param resp
     */
    private void processResp(VmBakStateQueueItem item, VMBakTaskQueryReq req, RPPVMBakTaskQueryResp resp,
            boolean isStopProcess) {

        VMBakTaskStat queryResultState = null;

        // 如果查询失败,则设置状态为异常
        if (!InterfaceResultCode.SUCCESS.getResultCode().equals(resp.getResultCode())) {
            logger.info("虚拟机备份编号为" + item.getVmBakId() + "的虚拟机备份状态查询响应码异常，异常码为[" + resp.getResultCode() + "]，异常原因为["
                    + resp.getResultMessage() + "，将重新查询该虚拟机备份状态，本次操作流水号为：" + item.getSerialNum());
            queryResultState = VMBakTaskStat.ERROR;
        } else {
            queryResultState = resp.getVmBakTaskStat();
        }

        // 如果状态非预期
        if (futureWebserviceStatus.containsKey(queryResultState)) {
            logger.info("虚拟机备份编号为" + item.getVmBakId() + "的虚拟机备份状态查询响应状态非预期状态，将重新查询该虚拟机备份状态，本次操作流水号为："
                    + item.getSerialNum());
            // 如果超过最大查询次数，设置状态为异常
            if (item.getTime() >= maxTransTime) {
                updateVmBakStatus(item.getVmBakId(), queryResultState, req.getSerialNum());
                insertVMBAKQueryErrorInfo(null);
                return;
            } else if (!isStopProcess) { // 未超次数,继续查询
                vmBakStateQueue.add(assmbleDelayItem(item));
                return;
            }
        } else { // 状态入库
            updateVmBakStatus(item.getVmBakId(), queryResultState, req.getSerialNum());
            logger.info("虚拟机备份编号为" + item.getVmBakId() + "的虚拟机备份状态查询成功，将更新虚拟机备份状态，本次操作流水号为：" + item.getSerialNum());
            return;
        }

    }

    /**
     * assmbleDelayItem 创建一个新的实例 VmBakStateQueueItem
     * @param item 虚拟机备份状态实例
     * @return VmBakStateQueueItem
     */
    private VmBakStateQueueItem assmbleDelayItem(VmBakStateQueueItem item) {
        VmBakStateQueueItem newItem = new VmBakStateQueueItem(retransInterval);
        newItem.setPassword(item.getPassword());
        newItem.setResourcePoolId(item.getResourcePoolId());
        newItem.setResourceUrl(item.getResourceUrl());
        newItem.setVmBakId(item.getVmBakId());
        newItem.setResourcePoolPartId(item.getResourcePoolPartId());
        newItem.setTime(item.getTime() + 1);
        newItem.setSerialNum(item.getSerialNum());
        return newItem;
    }

    /**
     * assmbleReq 虚拟机备份状态查询接口实例
     * @param req 虚拟机备份状态实例
     * @return VmBakStateQueryReq 虚拟机备份状态查询接口实例
     */
    private VMBakTaskQueryReq assmbleReq(VmBakStateQueueItem req) {
        VMBakTaskQueryReq vmReq = new VMBakTaskQueryReq();
        vmReq.setInfo(new RPPVMBakTaskQueryReq());
        vmReq.getInfo().setVmBackupId(req.getVmBakId());
        vmReq.getInfo().setVmBakTaskQueryType(VMBakTaskQueryType.RESOTRE_TYPE);

        vmReq.setPassword(req.getPassword());
        vmReq.setResourcePoolPartId(req.getResourcePoolPartId());
        vmReq.setResourcePoolId(req.getResourcePoolId());
        vmReq.setResourceUrl(req.getResourceUrl());
        vmReq.setTimestamp(timeStampgen.generateTimeStamp());
        vmReq.setTransactionID(transactonGen.generateTransactionId(vmReq.getResourcePoolId(), vmReq.getTimestamp()));
        vmReq.setSerialNum(req.getSerialNum());
        return vmReq;
    }

    /**
     * 设置transactonGen字段数据
     * @param transactonGen The transactonGen to set.
     */
    public void setTransactonGen(TransactionIdGenerator transactonGen) {
        this.transactonGen = transactonGen;
    }

    /**
     * 设置timeStampgen字段数据
     * @param timeStampgen The timeStampgen to set.
     */
    public void setTimeStampgen(TimeStampGenerator timeStampgen) {
        this.timeStampgen = timeStampgen;
    }

    /**
     * initDate 初始化查询状态信息<br>
     * 系统启动后，将数据中中所有中间状态的虚拟机备份信息，加载到查询队列中
     */
    @SuppressWarnings("unchecked")
    private void initDate() {
        List<VmBakStateQueueItemInfo> vmStateQueueItemInfos = null;
        // 由于私有云接口备份任务创建是同步接口,且备份时机无法确定,因此不做查询
        if (VMBakTaskStat.RESTORING.getValue().equals(currentStatus)) {// 恢复中
            try {
                vmStateQueueItemInfos = (List<VmBakStateQueueItemInfo>) ibatisDao.getData("queryVMbakByState",
                        VMBakTaskStat.RESTORING.getValue());
                logger.info("查询到正在恢复的虚拟机备份任务共" + vmStateQueueItemInfos.size() + "条");

                for (VmBakStateQueueItemInfo vmStateQueueItemInfo : vmStateQueueItemInfos) {
                    VmBakStateQueueItem vmStateQueueItem = new VmBakStateQueueItem(retransInterval);
                    vmStateQueueItem.setPassword(vmStateQueueItemInfo.getPassword());
                    vmStateQueueItem.setResourcePoolId(vmStateQueueItemInfo.getResourcePoolId());
                    vmStateQueueItem.setResourcePoolPartId(vmStateQueueItemInfo.getResourcePoolPartId());
                    vmStateQueueItem.setResourceUrl(vmStateQueueItemInfo.getResourceUrl());
                    vmStateQueueItem.setVmBakId(vmStateQueueItemInfo.getVmBakId());
                    vmStateQueueItem.setSerialNum(sequenceGenerator.generatorVmBakSerialNum(SERIALNUM));
                    vmBakStateQueue.add(vmStateQueueItem);
                }
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为虚拟机恢复中的数据库异常", e);
            }
        }
    }

    /**
     * 设置ibatisDao字段数据
     * @param ibatisDao The ibatisDao to set.
     */
    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    /**
     * updateVmBakStatus 更新虚拟机备份状态
     * @param vmBakInfo 虚拟机备份实例信息
     */
    private void updateVmBakStatus(String vmBackTaskId, VMBakTaskStat bakState, String serialNum) {
        try {
            Map<String, String> updateDBParam = new HashMap<String, String>();
            updateDBParam.put("vmBakId", vmBackTaskId);
            updateDBParam.put("vmBakState", bakState.getValue());

            ibatisDao.updateData("updateVmBakStatus", updateDBParam);
            if (logger.isDebugEnable()) {
                logger.debug("更新备份状态成功, vmBakId为" + vmBackTaskId);
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + vmBackTaskId + "】的虚拟机状态更新失败，虚拟机状态为【"
                    + bakState + "】，本次操作流水号为：" + serialNum, e);
        }
    }

    /**
     * insertVMBAKQueryErrorInfo 插入虚拟机备份查询失败记录信息
     * @param errorInfo 虚拟机备份错误信息实例
     */
    private void insertVMBAKQueryErrorInfo(Object object) {
        // TODO 记录错误日志
    }

    /**
     * 设置maxTransTime字段数据
     * @param maxTransTime The maxTransTime to set.
     */
    public void setMaxTransTime(int maxTransTime) {
        this.maxTransTime = maxTransTime;
    }

    /**
     * setCurrentStatus 设置 currentStatus
     * @param currentStatus
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * 设置futureStatus字段数据
     * @param futureStatus The futureStatus to set.
     */
    public void setFutureStatus(Map<String, String> futureStatus) {
        this.futureWebserviceStatus = futureStatus;
    }

    /**
     * 设置vmBakStateQueue字段数据
     * @param vmBakStateQueue The vmBakStateQueue to set.
     */
    public void setVmBakStateQueue(VmBakStateQueue vmBakStateQueue) {
        this.vmBakStateQueue = vmBakStateQueue;
    }

    /**
     * 设置sequenceGenerator字段数据
     * @param sequenceGenerator The sequenceGenerator to set.
     */
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * 设置retransInterval字段数据
     * @param retransInterval The retransInterval to set.
     */
    public void setRetransInterval(long retransInterval) {
        this.retransInterval = retransInterval;
    }

    /**
     * 设置vmBakStateQuery字段数据
     * @param vmBakStateQuery The vmBakStateQuery to set.
     */
    public void setVmBakStateQuery(VMBakTaskQuery vmBakStateQuery) {
        this.vmBakStateQuery = vmBakStateQuery;
    }

}
