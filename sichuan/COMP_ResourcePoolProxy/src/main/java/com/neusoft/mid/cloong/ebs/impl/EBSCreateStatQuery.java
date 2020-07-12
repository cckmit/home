package com.neusoft.mid.cloong.ebs.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.ebs.EBSCreateReq;
import com.neusoft.mid.cloong.ebs.EBSInfo;
import com.neusoft.mid.cloong.ebs.EBSQuery;
import com.neusoft.mid.cloong.ebs.EBSQueryReq;
import com.neusoft.mid.cloong.order.info.OrderInfo;
import com.neusoft.mid.cloong.order.info.OrderProcessor;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSQueryResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 发送创建块存储请求后查询状态的请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class EBSCreateStatQuery implements Runnable {

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(EBSCreateStatQuery.class);

    /**
     * 订单状态——生效
     */
    private static final String ORDER_STATUS_EFFECT = "3";

    /**
     * EBS CASE状态——已创建
     */
    private static final String EBS_CASE_STATUS_CREATED = "2";

    /**
     * EBS CASE状态——已挂载
     */
    private static final String EBS_CASE_STATUS_MOUNTED = "4";

    /**
     * EBS CASE状态——创建失败
     */
    private static final String EBS_CASE_STATUS_CREATE_FAIL = "6";

    /**
     * EBS CASE状态——挂载失败
     */
    private static final String EBS_CASE_STATUS_MOUNT_FAIL = "7";

    /**
     * EBS CASE状态——状态异常
     */
    private static final String EBS_CASE_STATUS_EXCEPTION = "9";

    private IbatisDAO ibatisDAO;

    private EBSQueryReq ebsQueryReq;

    private EBSQuery ebsQuery;

    private int interval = 5;

    private int startQueryDelay = 5000;

    private long timeout = 3600000;

    @Override
    public void run() {
        synchronized (this) {
            query();
        }
    }

    /**
     * assembleVMStateReq 创建块存储组装查询request
     */
    public void assembleQueryReq(String ebsId, EBSCreateReq createReq) {
        ebsQueryReq = new EBSQueryReq();
        ebsQueryReq.setEbsId(ebsId);
        ebsQueryReq.setPassword(createReq.getPassword());
        ebsQueryReq.setResourcePoolId(createReq.getResourcePoolId());
        ebsQueryReq.setResourcePoolPartId(createReq.getResourcePoolPartId());
        ebsQueryReq.setResourceUrl(createReq.getResourceUrl());
        ebsQueryReq.setTimestamp(createReq.getTimestamp());
        ebsQueryReq.setTransactionID(createReq.getTransactionID());
        if (logger.isDebugEnable()) {
            logger.debug(createReq.toString());
        }

    }

    /**
     * 查询虚拟机状态
     */
    public boolean query() {

        if (logger.isDebugEnable()) {
            logger.debug(ebsQueryReq.toString());
        }

        try {
            logger.info("5秒钟后开始查询编号为[" + ebsQueryReq.getEbsId() + "]块存储的状态...");
            Thread.currentThread().sleep(startQueryDelay);
        } catch (InterruptedException e) {
            logger.error("等待5秒查询块存储状态异常,");
        }

        // 循环查询虚拟机状态次数
        long startMs = System.currentTimeMillis();

        while (true) {

            // 1.查询块状态
            RPPEBSQueryResp queryResp = ebsQuery.query(ebsQueryReq);

            // 2.查询失败,则直接返回
            if (!InterfaceResultCode.SUCCESS.getResultCode().equals(queryResp.getResultCode())) {
                logger.error("查询编号为" + ebsQueryReq.getEbsId() + "的块存储状态失败,将块的状态更新为异常[" + EBS_CASE_STATUS_EXCEPTION
                        + "]!");
                updateEBSStatusToDB(ebsQueryReq.getEbsId(), EBS_CASE_STATUS_EXCEPTION);
                return false;
            }

            // 3.如果查询成功，根据BS的状态，做不同处理
            switch (queryResp.getBsStatus()) {
            case CREATED:
                return processCreateSucc(EBS_CASE_STATUS_CREATED);
            case MOUNTED:
                return processCreateSucc(EBS_CASE_STATUS_MOUNTED);
            case MOUNT_FAIL:
                return processCreateSucc(EBS_CASE_STATUS_MOUNT_FAIL);
            case CREATE_FAIL:
                return processCreateFail();
                // 创建中，继续查询
            case CREATING: // 如果创建中,则继续执行循环
            default:
                if (!processCreating(startMs)) {
                    return false;
                }
                break;
            }
        }
    }

    /**
     * 处理仍处于创建中的状态
     * @param startMs 创建开始时间
     */
    private boolean processCreating(long startMs) {
        if ((System.currentTimeMillis() - startMs) > timeout) {
            // 超时异常
            logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + ebsQueryReq.getEbsId()
                    + "的块存储资源状态更新失败,失败原因：虚拟机状态查询超时！");
            updateEBSStatusToDB(ebsQueryReq.getEbsId(), EBS_CASE_STATUS_EXCEPTION);
            return false;
        }
        try {
            TimeUnit.SECONDS.sleep(interval);
        } catch (InterruptedException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + ebsQueryReq.getEbsId()
                    + "的块存储资源状态更新失败,失败原因：线程SLEEP异常！");
            return false;
        }
        return true;
    }

    /**
     * 处理创建失败
     * @return
     */
    boolean processCreateFail() {
        logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + ebsQueryReq.getEbsId() + "的块存储资源池返回创建操作失败！");
        updateEBSStatusToDB(ebsQueryReq.getEbsId(), EBS_CASE_STATUS_CREATE_FAIL);
        return false;
    }

    /**
     * 做块存储创建成功处理
     * @param ebsStatus
     * @return
     * @throws SQLException
     */
    boolean processCreateSucc(String ebsStatus) {
        try {
            TimeUnit.SECONDS.sleep(5);// 等待创建线程完成工作

            List<BatchVO> voList = new ArrayList<BatchVO>();

            // 更新EBS在数据库中的状态
            EBSInfo updateEBSInfo = new EBSInfo();
            updateEBSInfo.setEbsId(ebsQueryReq.getEbsId());
            updateEBSInfo.setEbsStatus(ebsStatus);
            voList.add(new BatchVO(BatchVO.OPERATION_MOD, "updateEBSStatus", updateEBSInfo));

            // 更新EBS的订单状态、订单到期时间
            OrderInfo orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryEBSOrderInfo", ebsQueryReq.getEbsId());

            if (orderInfo == null) {
                logger.error("编号为" + ebsQueryReq.getEbsId() + "的块存储没有对应的订单,更新状态失败!");
                return false;
            }

            BatchVO updateOrderVO = OrderProcessor.genOrderToEffectiveVO(ebsQueryReq.getTimestamp(), orderInfo);
            voList.add(updateOrderVO);

            ibatisDAO.updateBatchData(voList);
            if (logger.isDebugEnable()) {
                logger.debug("更新订单成功, vmId为" + ebsQueryReq.getEbsId());
            }

            if (logger.isDebugEnable()) {
                logger.debug("更新块存储实例状态成功，EBSId为" + ebsQueryReq.getEbsId() + "，状态为" + ebsStatus);
            }
            return true;
        } catch (Exception e) {
            logger.error("更新块存储实例" + ebsQueryReq.getEbsId() + "状态失败", e);
            updateEBSStatusToDB(ebsQueryReq.getEbsId(), EBS_CASE_STATUS_EXCEPTION);
            return false;
        }
    }

    /**
     * 更新块存储状态信息到数据库
     * @param ebsStatus
     * @return
     * @throws SQLException
     */
    private EBSInfo updateEBSStatusToDB(String ebsId, String ebsStatus) {
        EBSInfo updateEBSInfo = new EBSInfo();
        updateEBSInfo.setEbsId(ebsId);
        updateEBSInfo.setEbsStatus(ebsStatus);
        try {
            ibatisDAO.updateData("updateEBSStatus", updateEBSInfo);
        } catch (SQLException e) {
            logger.error("向数据库中更新EBS" + ebsId + "状态时发生异常!");
        }
        return updateEBSInfo;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置ebsQuery字段数据
     * @param ebsQuery The ebsQuery to set.
     */
    public void setEbsQuery(EBSQuery ebsQuery) {
        this.ebsQuery = ebsQuery;
    }

}
