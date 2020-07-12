/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.impl;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.ebs.EBSDetach;
import com.neusoft.mid.cloong.ebs.EBSDetachReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘卸载处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class EBSDetachProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(EBSDetachProcessor.class);

    /**
     * 挂载接口
     */
    private EBSDetach detach;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPEBSOperatorReq req = (RPPEBSOperatorReq) reqCxt.getAttribute(RPPEBSOperatorReq.REQ_BODY);
        RPPEBSOperatorResp resp = new RPPEBSOperatorResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        EBSDetachReq detachReq = null;
        try {
            detachReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘卸载操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘卸载操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (detachReq.getResourceUrl() == null || detachReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘卸载操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPEBSOperatorResp detachResp = detach.detach(detachReq);
        assebleRes(detachResp, respCxt);
        if (detachResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("将编码为[" + detachReq.getEbsId() + "]的虚拟硬盘从编码为[" + detachReq.getHostIds() + "]的虚拟机卸载操作成功");
        } else {
            logger.info("将编码为[" + detachReq.getEbsId() + "]的虚拟硬盘从编码为[" + detachReq.getHostIds() + "]的虚拟机卸载操作失败,接口响应码为["
                    + detachResp.getResultCode() + "]，接口响应消息为[" + detachResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPEBSOperatorReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getEbsId()) || (!P_STRING.matcher(req.getEbsId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储编号EBS_ID", null);
            return false;
        }
        if (req.getVmId() == null || req.getVmId().isEmpty()) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储绑定的主机信息为空", null);
            return false;
        }
        return true;
    }

    private EBSDetachReq assembleEBSReq(RPPEBSOperatorReq req) throws ServiceStopException, UnmatchException {
        EBSDetachReq detachReq = new EBSDetachReq();
        detachReq.getHostIds().addAll(req.getVmId());
        detachReq.setEbsId(req.getEbsId());

        detachReq.setResourcePoolId(req.getResourcePoolId());
        detachReq.setResourcePoolPartId(req.getResourcePoolPartId());
        detachReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        detachReq.setTransactionID(this.getTransactonGen().generateTransactionId(detachReq.getResourcePoolId(),
                detachReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                detachReq.getResourcePoolId());
        detachReq.setPassword(resourceInfo.getUserPassword());
        detachReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return detachReq;
    }

    private void assebleRes(RPPEBSOperatorResp dettachResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPEBSOperatorResp.RESP_BODY, dettachResp);
    }

    public void setDetach(EBSDetach detach) {
        this.detach = detach;
    }
}
