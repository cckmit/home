/*******************************************************************************
 * @(#)PMOperatorProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.pm.PMOperator;
import com.neusoft.mid.cloong.host.pm.PMOperatorReq;
import com.neusoft.mid.cloong.host.pm.PMStateQueueItem;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMOperatorResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机操作处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-5 上午10:22:30
 */
public class PMOperatorProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(PMOperatorProcessor.class);

    /**
     * 和资源池交互的物理机操作接口
     */
    private PMOperator pmOperator;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 物理机状态查询队列
     */
    private PMStateQueue pmStateQueue;

    /**
     * 物理机状态查询延时时间
     */
    @Value("${operatorPMToQueryDelay}")
    private Integer operationQueryDelay = 5000;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPPMOperatorReq req = (RPPPMOperatorReq) reqCxt.getAttribute(RPPPMOperatorReq.REQ_BODY);
        RPPPMOperatorResp resp = new RPPPMOperatorResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        PMOperatorReq pmReq = null;
        try {
            pmReq = assemblePMOperatorReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (pmReq.getResourceUrl() == null || pmReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:"
                    + pmReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPPMOperatorResp pmResp = pmOperator.operatePM(pmReq);
        assebleRes(pmResp, respCxt);
        // 如果操作成功，则把状态查询请求加入状态查询队列
        if (pmResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("物理机编码为[" + pmReq.getPmId() + "]的物理机操作成功,本次操作流水号为:" + pmReq.getSerialNum());

            addPMStateQueryToQueue(pmReq);

        } else {
            logger.info("物理机编码为[" + pmReq.getPmId() + "]的物理机操作失败,接口响应码为[" + pmResp.getResultCode() + "]，接口响应消息为["
                    + pmResp.getResultMessage() + "],本次操作流水号为:" + pmReq.getSerialNum());
        }
        return SUCCESS;
    }

    /**
     * 将物理机状态查询任务放入排队队列
     * @param pmReq 物理机操作请求bean
     */
    private void addPMStateQueryToQueue(PMOperatorReq pmReq) {

        final String password = pmReq.getPassword();
        final String resourcePoolId = pmReq.getResourcePoolId();
        final String resourcePoolPartId = pmReq.getResourcePoolPartId();
        final String resourceUrl = pmReq.getResourceUrl();
        final String pmId = pmReq.getPmId();
        final String serialNum = pmReq.getSerialNum();

        new Thread(new Runnable() {
            public void run() {
                try {
                    logger.info(operationQueryDelay + "毫秒后开始查询物理机[" + pmId + "]操作的结果状态,本次操作流水号为:" + serialNum);
                    Thread.currentThread().sleep(operationQueryDelay);
                    PMStateQueueItem item = new PMStateQueueItem(0);
                    item.setPassword(password);
                    item.setResourcePoolId(resourcePoolId);
                    item.setResourcePoolPartId(resourcePoolPartId);
                    item.setResourceUrl(resourceUrl);
                    item.setPmId(pmId);
                    item.setSerialNum(serialNum);
                    pmStateQueue.add(item);
                } catch (InterruptedException e) {
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机" + pmId + "操作后，将查询最终状态任务加入队列失败，本次操作流水号为:"
                            + serialNum, null);
                }
            }
        }).start();
    }

    /**
     * 做请求bean转换. 没有太大意义
     * @param req
     * @return
     * @throws ServiceStopException
     * @throws UnmatchException
     */
    private PMOperatorReq assemblePMOperatorReq(RPPPMOperatorReq req) throws ServiceStopException, UnmatchException {
        PMOperatorReq pmReq = new PMOperatorReq();
        pmReq.setPmId(req.getPmId());
        pmReq.setType(req.getPmOperatorType());
        pmReq.setResourcePoolId(req.getResourcePoolId());
        pmReq.setResourcePoolPartId(req.getResourcePoolPartId());
        pmReq.setSerialNum(req.getSerialNum());
        pmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        pmReq.setTransactionID(this.getTransactonGen().generateTransactionId(pmReq.getResourcePoolId(),
                pmReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                pmReq.getResourcePoolId());
        pmReq.setPassword(resourceInfo.getUserPassword());
        pmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return pmReq;
    }

    private void assebleRes(RPPPMOperatorResp pmResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPPMOperatorResp.RESP_BODY, pmResp);
    }

    public void setPmOperator(PMOperator pmOperator) {
        this.pmOperator = pmOperator;
    }

    private boolean validateInputPara(RPPPMOperatorReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getPmId()) || (!P_STRING.matcher(req.getPmId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机编号PM_ID", null);
            return false;
        }
        if (req.getPmOperatorType() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机操作类型是否设置pmOperatorType", null);
            return false;
        }
        return true;
    }

    /**
     * 设置pmStateQueue字段数据
     * @param pmStateQueue The pmStateQueue to set.
     */
    public void setPmStateQueue(PMStateQueue pmStateQueue) {
        this.pmStateQueue = pmStateQueue;
    }

}
