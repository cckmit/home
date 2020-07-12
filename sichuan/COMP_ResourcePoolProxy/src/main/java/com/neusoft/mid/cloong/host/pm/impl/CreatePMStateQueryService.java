/*******************************************************************************
 * @(#)CreatePMStateQueryService.java 2014-3-21
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

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
import com.neusoft.mid.cloong.host.pm.PMCreateReq;
import com.neusoft.mid.cloong.host.pm.PMStateQuery;
import com.neusoft.mid.cloong.host.pm.PMStateQueueItemInfo;
import com.neusoft.mid.grains.commons.route.core.IService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建中的定时任务 启动时，需要把数据库中创建中的添加到定时任务线程中. <br>
 * 此类仅管理启动时，数据库中为中间状态的物理机信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-3-21 下午1:47:08
 */
public class CreatePMStateQueryService implements IService {

    private static LogService logger = LogService.getLogger(CreatePMStateQueryService.class);

    /**
     * 消息序列号生成器
     */
    private TransactionIdGenerator transactonGen;

    /**
     * 时间戳生成器
     */
    private TimeStampGenerator timeStampgen;

    /**
     * 物理机状态查询接口
     */
    private PMStateQuery pmStateQuery;

    /**
     * 数据库操作dao
     */
    private IbatisDAO ibatisDAO;

    /**
     * 间隔时间
     */
    private int interval = 5;

    /**
     * 创建PM发送到资源池后查询资源状态及IP、带宽线程池
     */
    private ThreadPoolTaskExecutor senderCreatePMTask;

    @Override
    public boolean ini() {
        logger.info("物理机创建中查询服务初始化成功！");
        return true;
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean start() {
        // 根据要创建的物理机数量，启动N个线程，将该线程放入线程池
        int numThread = 0;

        List<PMStateQueueItemInfo> pmStateQueueItems = null;
        try {
            pmStateQueueItems = (List<PMStateQueueItemInfo>) ibatisDAO.getData("createPMStateQuery", null);
            logger.info("创建中查询成功,长度为" + pmStateQueueItems.size());
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为创建中的物理机数据库异常", e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询当前状态为创建中的物理机数据库异常", e);
        }
        PMCreateReq pmReq = null;// 资源池信息url,pool_id,pwd,
        Map<String, String> tempPMInfo = null;

        for (PMStateQueueItemInfo pmStateQueueItem : pmStateQueueItems) {
            try {
                numThread++;
                CreatePMSenderImpl createPMSenderImp = new CreatePMSenderImpl();
                createPMSenderImp.setIbatisDAO(ibatisDAO);
                createPMSenderImp.setInterval(interval);
                createPMSenderImp.setPmStateQuery(pmStateQuery);
                pmReq = new PMCreateReq();
                tempPMInfo = new HashMap<String, String>();
                pmReq.setPassword(pmStateQueueItem.getPassword());
                pmReq.setResourcePoolId(pmStateQueueItem.getResourcePoolId());
                pmReq.setResourcePoolPartId(pmStateQueueItem.getResourcePoolPartId());
                pmReq.setResourceUrl(pmStateQueueItem.getResourceUrl());
                pmReq.setTimestamp(timeStampgen.generateTimeStamp());
                pmReq.setTransactionID(transactonGen.generateTransactionId(pmStateQueueItem.getResourcePoolId(),
                        pmReq.getTimestamp()));
                createPMSenderImp.assemblePMStateReq(pmStateQueueItem.getPmId(), pmReq);
                senderCreatePMTask.execute(createPMSenderImp);
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
     * 设置pmStateQuery字段数据
     * @param pmStateQuery The pmStateQuery to set.
     */
    public void setPmStateQuery(PMStateQuery pmStateQuery) {
        this.pmStateQuery = pmStateQuery;
    }

    /**
     * 设置interval字段数据
     * @param interval The interval to set.
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 设置senderCreatePMTask字段数据
     * @param senderCreatePMTask The senderCreatePMTask to set.
     */
    public void setSenderCreatePMTask(ThreadPoolTaskExecutor senderCreatePMTask) {
        this.senderCreatePMTask = senderCreatePMTask;
    }
}
