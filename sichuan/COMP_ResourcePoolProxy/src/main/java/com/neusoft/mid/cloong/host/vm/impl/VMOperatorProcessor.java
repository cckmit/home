/*******************************************************************************
 * @(#)VMOperatorProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.VMOperator;
import com.neusoft.mid.cloong.host.vm.VMOperatorReq;
import com.neusoft.mid.cloong.host.vm.VMStateQueueItem;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMOperatorResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机操作处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-5 上午10:22:30
 */
public class VMOperatorProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMOperatorProcessor.class);

    /**
     * 和资源池交互的虚拟机操作接口
     */
    private VMOperator vmOperator;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 虚拟机状态查询队列
     */
    private VMStateQueue vmStateQueue;

    /**
     * 虚拟机状态查询延时时间
     */
    @Value("${operatorVMToQueryDelay}")
    private Integer operationQueryDelay = 5000;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMOperatorReq req = (RPPVMOperatorReq) reqCxt.getAttribute(RPPVMOperatorReq.REQ_BODY);
        RPPVMOperatorResp resp = new RPPVMOperatorResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        VMOperatorReq vmReq = null;
        try {
            vmReq = assembleVMOperatorReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vmReq.getResourceUrl() == null || vmReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:"
                    + vmReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVMOperatorResp vmResp = vmOperator.operateVM(vmReq);
        assebleRes(vmResp, respCxt);
        // 如果操作成功，则把状态查询请求加入状态查询队列
        if (vmResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("虚拟机编码为[" + vmReq.getVmId() + "]的虚拟机操作成功,本次操作流水号为:" + vmReq.getSerialNum());

            addVMStateQueryToQueue(vmReq);

        } else {
            logger.info("虚拟机编码为[" + vmReq.getVmId() + "]的虚拟机操作失败,接口响应码为[" + vmResp.getResultCode() + "]，接口响应消息为["
                    + vmResp.getResultMessage() + "],本次操作流水号为:" + vmReq.getSerialNum());
        }
        return SUCCESS;
    }

    /**
     * 将虚拟机状态查询任务放入排队队列
     * @param vmReq 虚拟机操作请求bean
     */
    private void addVMStateQueryToQueue(VMOperatorReq vmReq) {

        final String password = vmReq.getPassword();
        final String resourcePoolId = vmReq.getResourcePoolId();
        final String resourcePoolPartId = vmReq.getResourcePoolPartId();
        final String resourceUrl = vmReq.getResourceUrl();
        final String vmId = vmReq.getVmId();
        final String serialNum = vmReq.getSerialNum();

        new Thread(new Runnable() {
            public void run() {
                try {
                    logger.info(operationQueryDelay + "毫秒后开始查询虚拟机[" + vmId + "]操作的结果状态,本次操作流水号为:" + serialNum);
                    Thread.currentThread().sleep(operationQueryDelay);
                    VMStateQueueItem item = new VMStateQueueItem(0);
                    item.setPassword(password);
                    item.setResourcePoolId(resourcePoolId);
                    item.setResourcePoolPartId(resourcePoolPartId);
                    item.setResourceUrl(resourceUrl);
                    item.setVmId(vmId);
                    item.setSerialNum(serialNum);
                    vmStateQueue.add(item);
                } catch (InterruptedException e) {
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机" + vmId + "操作后，将查询最终状态任务加入队列失败，本次操作流水号为:"
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
    private VMOperatorReq assembleVMOperatorReq(RPPVMOperatorReq req) throws ServiceStopException, UnmatchException {
        VMOperatorReq vmReq = new VMOperatorReq();
        vmReq.setVmId(req.getVmId());
        vmReq.setType(req.getVmOperatorType());
        vmReq.setResourcePoolId(req.getResourcePoolId());
        vmReq.setResourcePoolPartId(req.getResourcePoolPartId());
        vmReq.setSerialNum(req.getSerialNum());
        vmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        vmReq.setTransactionID(this.getTransactonGen().generateTransactionId(vmReq.getResourcePoolId(),
                vmReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                vmReq.getResourcePoolId());
        vmReq.setPassword(resourceInfo.getUserPassword());
        vmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return vmReq;
    }

    private void assebleRes(RPPVMOperatorResp vmResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPVMOperatorResp.RESP_BODY, vmResp);
    }

    public void setVmOperator(VMOperator vmOperator) {
        this.vmOperator = vmOperator;
    }

    private boolean validateInputPara(RPPVMOperatorReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getVmId()) || (!P_STRING.matcher(req.getVmId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机编号VM_ID", null);
            return false;
        }
        if (req.getVmOperatorType() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机操作类型是否设置vmOperatorType", null);
            return false;
        }
        return true;
    }

    /**
     * 设置vmStateQueue字段数据
     * @param vmStateQueue The vmStateQueue to set.
     */
    public void setVmStateQueue(VMStateQueue vmStateQueue) {
        this.vmStateQueue = vmStateQueue;
    }

}
