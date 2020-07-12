/*******************************************************************************
 * @(#)CreateVMStateQueryService.java 2014-3-21
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.host.vm.VMCreateReq;
import com.neusoft.mid.cloong.host.vm.VMStateQuery;
import com.neusoft.mid.cloong.host.vm.VMStateQueueItemInfo;
import com.neusoft.mid.grains.commons.route.core.IService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建中的定时任务 启动时，需要把数据库中创建中的添加到定时任务线程中. <br>
 * 此类仅管理启动时，数据库中为中间状态的虚拟机信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-3-21 下午1:47:08
 */
public class CreateVMStateQueryService implements IService {

    private static LogService logger = LogService.getLogger(CreateVMStateQueryService.class);

    /**
     * 消息序列号生成器
     */
    private TransactionIdGenerator transactonGen;

    /**
     * 时间戳生成器
     */
    private TimeStampGenerator timeStampgen;

    /**
     * 虚拟机状态查询接口
     */
    private VMStateQuery vmStateQuery;

    /**
     * 数据库操作dao
     */
    private IbatisDAO ibatisDAO;

    /**
     * 间隔时间
     */
    private int interval = 5;

    /**
     * 创建VM发送到资源池后查询资源状态及IP、带宽线程池
     */
    private ThreadPoolTaskExecutor senderCreateVMTask;

    @Override
    public boolean ini() {
        logger.info("虚拟机创建中查询服务初始化成功！");
        return true;
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean start() {
        // 根据要创建的虚拟机数量，启动N个线程，将该线程放入线程池
        int numThread = 0;

        List<VMStateQueueItemInfo> vmStateQueueItems = null;
        try {
            vmStateQueueItems = (List<VMStateQueueItemInfo>) ibatisDAO.getData("createVMStateQuery", null);
            logger.info("创建中查询成功,长度为" + vmStateQueueItems.size());
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为创建中的虚拟机数据库异常", e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为创建中的虚拟机数据库异常", e);
        }
        VMCreateReq vmReq = null;// 资源池信息url,pool_id,pwd,
        Map<String, String> tempVMInfo = null;

        for (VMStateQueueItemInfo vmStateQueueItem : vmStateQueueItems) {
            try {
                numThread++;
                CreateVMSenderImp createVMSenderImp = new CreateVMSenderImp();
                createVMSenderImp.setIbatisDAO(ibatisDAO);
                createVMSenderImp.setInterval(interval);
                createVMSenderImp.setVmStateQuery(vmStateQuery);
                vmReq = new VMCreateReq();
                tempVMInfo = new HashMap<String, String>();
                vmReq.setPassword(vmStateQueueItem.getPassword());
                vmReq.setResourcePoolId(vmStateQueueItem.getResourcePoolId());
                vmReq.setResourcePoolPartId(vmStateQueueItem.getResourcePoolPartId());
                vmReq.setResourceUrl(vmStateQueueItem.getResourceUrl());
                vmReq.setTimestamp(timeStampgen.generateTimeStamp());
                vmReq.setTransactionID(transactonGen.generateTransactionId(vmStateQueueItem.getResourcePoolId(),
                        vmReq.getTimestamp()));
                createVMSenderImp.assembleVMStateReq(vmStateQueueItem.getVmId(), vmReq);
                senderCreateVMTask.execute(createVMSenderImp);
                if (logger.isDebugEnable()) {
                    logger.debug("启动线程第" + numThread + "个...");
                }
            } catch (TaskRejectedException e1) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e1);
            } catch (Exception e2) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e2);
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("线程执行完毕，共执行" + numThread + "个线程！");
        }
        return true;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 获取ibatisDAO字段数据
     * @return Returns the ibatisDAO.
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
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
     * 设置vmStateQuery字段数据
     * @param vmStateQuery The vmStateQuery to set.
     */
    public void setVmStateQuery(VMStateQuery vmStateQuery) {
        this.vmStateQuery = vmStateQuery;
    }

    /**
     * 设置interval字段数据
     * @param interval The interval to set.
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 设置senderCreateVMTask字段数据
     * @param senderCreateVMTask The senderCreateVMTask to set.
     */
    public void setSenderCreateVMTask(ThreadPoolTaskExecutor senderCreateVMTask) {
        this.senderCreateVMTask = senderCreateVMTask;
    }
}
