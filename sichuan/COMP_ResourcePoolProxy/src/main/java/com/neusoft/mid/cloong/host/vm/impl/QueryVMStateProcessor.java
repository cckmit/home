/*******************************************************************************
 * @(#)QueryVMStateProcessor.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.VMStateQuery;
import com.neusoft.mid.cloong.host.vm.VMStateQueryReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 上午8:49:51
 */
public class QueryVMStateProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(QueryVMStateProcessor.class);

    /**
     * 虚拟机状态查询接口
     */
    private VMStateQuery vmStateQuery;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMStatusQueryReq rppVMStatusQueryReq = (RPPVMStatusQueryReq) reqCxt
                .getAttribute(RPPVMStatusQueryReq.REQ_BODY);
        RPPVMStatusQueryResp vmStateQueryResp = new RPPVMStatusQueryResp();

        if (!validateInputPara(rppVMStatusQueryReq)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, vmStateQueryResp);
            return FAILURE;
        }
        VMStateQueryReq vmStateQueryReq = null;
        try {
            vmStateQueryReq = assembleQueryVMStatusReq(rppVMStatusQueryReq);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送查询虚拟机请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmStateQueryResp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送查询虚拟机请求失败，资源池代理系统内部错误，无法通过给定的资源池ID[" + rppVMStatusQueryReq.getResourcePoolId()
                            + "]获取到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmStateQueryResp);
            return FAILURE;
        }
        if (vmStateQueryReq.getResourceUrl() == null || vmStateQueryReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送查询虚拟机请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空！", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmStateQueryResp);
            return FAILURE;
        }
        vmStateQueryResp = vmStateQuery.queryVMState(vmStateQueryReq);
        assebleRes(vmStateQueryResp, respCxt);
        if (vmStateQueryResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("查询虚拟机状态信息成功！");
        } else {
            logger.info("查询虚拟机状态信息失败,接口响应码为[" + vmStateQueryResp.getResultCode() + "]，接口响应消息为["
                    + vmStateQueryResp.getResultMessage());
        }
        return SUCCESS;
    }

    private VMStateQueryReq assembleQueryVMStatusReq(RPPVMStatusQueryReq req) throws ServiceStopException,
            UnmatchException {
        VMStateQueryReq vmStateQueryReq = new VMStateQueryReq();
        vmStateQueryReq.setResourcePoolId(req.getResourcePoolId());
        vmStateQueryReq.setResourcePoolPartId(req.getResourcePoolPartId());
        vmStateQueryReq.setSerialNum(req.getSerialNum());
        vmStateQueryReq.setVmId(req.getVmId());
        vmStateQueryReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        vmStateQueryReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                vmStateQueryReq.getResourcePoolId(), vmStateQueryReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                vmStateQueryReq.getResourcePoolId());
        vmStateQueryReq.setPassword(resourceInfo.getUserPassword());
        vmStateQueryReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return vmStateQueryReq;
    }

    private void assebleRes(RPPVMStatusQueryResp queryVMStateResp, RuntimeContext resp) {
        resp.setAttribute(RPPVMStatusQueryResp.RESP_BODY, queryVMStateResp);
    }

    private boolean validateInputPara(RPPVMStatusQueryReq req) {
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
        return true;
    }

    /**
     * 获取vmStateQuery字段数据
     * @return Returns the vmStateQuery.
     */
    public VMStateQuery getVmStateQuery() {
        return vmStateQuery;
    }

    /**
     * 设置vmStateQuery字段数据
     * @param vmStateQuery The vmStateQuery to set.
     */
    public void setVmStateQuery(VMStateQuery vmStateQuery) {
        this.vmStateQuery = vmStateQuery;
    }

}
