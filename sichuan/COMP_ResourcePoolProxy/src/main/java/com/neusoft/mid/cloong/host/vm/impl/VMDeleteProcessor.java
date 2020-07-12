/*******************************************************************************
 * @(#)VMDeleteProcessor.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.VMDelete;
import com.neusoft.mid.cloong.host.vm.VMDeleteReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机删除处理器
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:33:00
 */
public class VMDeleteProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMDeleteProcessor.class);

    /**
     * 和资源池交互的虚拟机删除接口
     */
    private VMDelete vmDelete;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMDeleteReq req = (RPPVMDeleteReq) reqCxt.getAttribute(RPPVMDeleteReq.REQ_BODY);
        RPPVMDeleteResp resp = new RPPVMDeleteResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        VMDeleteReq vmReq = null;
        try {
            vmReq = assembleVMReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机删除请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + req.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟机删除请求失败，资源池代理系统内部错误，无法通过给定的资源池ID" + req.getResourcePoolId() + "获取到资源池信息，本次操作流水号为:"
                            + req.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vmReq.getResourceUrl() == null || vmReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机删除请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:"
                    + vmReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVMDeleteResp vmResp = vmDelete.deleteVM(vmReq);
        assebleRes(vmResp, respCxt);
        if (vmResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("虚拟机编码为[" + vmReq.getVmId() + "]的虚拟机删除成功,本次操作流水号为:" + vmReq.getSerialNum());
        } else {
            logger.info("虚拟机编码为[" + vmReq.getVmId() + "]的虚拟机删除失败,接口响应码为[" + vmResp.getResultCode() + "]，接口响应消息为["
                    + vmResp.getResultMessage() + "],本次操作流水号为:" + vmReq.getSerialNum());
        }
        return SUCCESS;
    }

    private VMDeleteReq assembleVMReq(RPPVMDeleteReq req) throws ServiceStopException, UnmatchException {
        VMDeleteReq vmReq = new VMDeleteReq();
        vmReq.setVmId(req.getVmId());
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

    private void assebleRes(RPPVMDeleteResp vmResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPVMDeleteResp.RESP_BODY, vmResp);
    }

    public void setVmDelete(VMDelete vmDelete) {
        this.vmDelete = vmDelete;
    }

    private boolean validateInputPara(RPPVMDeleteReq req) {
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
}
