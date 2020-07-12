/*******************************************************************************
 * @(#)CreateVMStateQueryService.java 2014-3-21
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.ebs.EBSCreateReq;
import com.neusoft.mid.cloong.ebs.EBSQuery;
import com.neusoft.mid.cloong.ebs.EBSStateQueueItemInfo;
import com.neusoft.mid.cloong.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.grains.commons.route.core.IService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建中的定时任务 启动时，需要把数据库中创建中的添加到定时任务线程中. <br>
 * 此类仅管理启动时，数据库中为中间状态的虚拟机信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-3-21 下午1:47:08
 */
public class CreateEBSStateQueryService implements IService {

    private static LogService logger = LogService.getLogger(CreateEBSStateQueryService.class);

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
    private EBSQuery ebsQuery;

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
    private ThreadPoolTaskExecutor ebsCreateStatQueryTask;

    @Override
    public boolean ini() {
        logger.info("块存储创建中查询服务初始化成功！");
        return true;
    }

    @Override
    public void pause() {
    }

    @Override
    public boolean start() {
        // 根据要创建的虚拟机数量，启动N个线程，将该线程放入线程池
        int numThread = 0;

        List<EBSStateQueueItemInfo> ebsStateQueueItems = null;
        try {
            ebsStateQueueItems = (List<EBSStateQueueItemInfo>) ibatisDAO.getData("queryCreateingEBSState", null);
            logger.info("查询处于\"创建中\"状态的块存储完毕,数量" + ebsStateQueueItems.size());
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为创建中的块存储数据库异常", e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为创建中的块存储数据库异常", e);
        }

        for (EBSStateQueueItemInfo ebsStateQueueItem : ebsStateQueueItems) {
            try {
                
                if (ebsStateQueueItem.getEbsId() == null){
                    logger.warn("数据库中存在状态为\"创建中\"但ID为NULL的记录,不做处理!");
                    continue;
                }
                
                numThread++;
                EBSCreateStatQuery ebsCreateStatQuery = new EBSCreateStatQuery();
                ebsCreateStatQuery.setIbatisDAO(ibatisDAO);
                ebsCreateStatQuery.setInterval(interval);
                ebsCreateStatQuery.setEbsQuery(ebsQuery);

                EBSCreateReq createReq = new EBSCreateReq();
                createReq.setResourcePoolId(ebsStateQueueItem.getResourcePoolId());
                createReq.setResourcePoolPartId(ebsStateQueueItem.getResourcePoolPartId());
                createReq.setResourceUrl(ebsStateQueueItem.getResourceUrl());
                createReq.setTimestamp(timeStampgen.generateTimeStamp());
                createReq.setTransactionID(transactonGen.generateTransactionId(ebsStateQueueItem.getResourcePoolId(),
                        createReq.getTimestamp()));

                ebsCreateStatQuery.assembleQueryReq(ebsStateQueueItem.getEbsId(), createReq);
                ebsCreateStatQueryTask.execute(ebsCreateStatQuery);

                if (logger.isDebugEnable()) {
                    logger.debug("启动块存储创建结果查询线程第" + numThread + "个...");
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
        return true;
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
     * 设置interval字段数据
     * @param interval The interval to set.
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 设置ebsQuery字段数据
     * @param ebsQuery The ebsQuery to set.
     */
    public void setEbsQuery(EBSQuery ebsQuery) {
        this.ebsQuery = ebsQuery;
    }

    /**
     * 设置ebsCreateStatQueryTask字段数据
     * @param ebsCreateStatQueryTask The ebsCreateStatQueryTask to set.
     */
    public void setEbsCreateStatQueryTask(ThreadPoolTaskExecutor ebsCreateStatQueryTask) {
        this.ebsCreateStatQueryTask = ebsCreateStatQueryTask;
    }

}
