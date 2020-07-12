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
import com.neusoft.mid.cloong.ebs.EBSDelete;
import com.neusoft.mid.cloong.ebs.EBSDeleteReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘删除处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class EBSDeleteProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(EBSDeleteProcessor.class);

    /**
     * 挂载接口
     */
    private EBSDelete delete;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPEBSDeleteReq req = (RPPEBSDeleteReq) reqCxt.getAttribute(RPPEBSDeleteReq.REQ_BODY);
        RPPEBSDeleteResp resp = new RPPEBSDeleteResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        EBSDeleteReq deleteReq = null;
        try {
            deleteReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘删除操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟硬盘删除操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (deleteReq.getResourceUrl() == null || deleteReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟硬盘删除操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + deleteReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPEBSDeleteResp deleteResp = delete.delete(deleteReq);
        assebleRes(deleteResp, respCxt);
        if (deleteResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("将编码为[" + deleteReq.getEbsId() + "]的虚拟硬盘删除操作操作成功");
        } else {
            logger.info("将编码为[" + deleteReq.getEbsId() + "]的虚拟硬盘删除操作失败,接口响应码为[" + deleteResp.getResultCode()
                    + "]，接口响应消息为[" + deleteResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPEBSDeleteReq req) {
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
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储编号ebsId", null);
            return false;
        }
        return true;
    }

    private EBSDeleteReq assembleEBSReq(RPPEBSDeleteReq req) throws ServiceStopException, UnmatchException {
        EBSDeleteReq deleteReq = new EBSDeleteReq();
        deleteReq.setResourcePoolId(req.getResourcePoolId());
        deleteReq.setResourcePoolPartId(req.getResourcePoolPartId());
        deleteReq.setEbsId(req.getEbsId());
        deleteReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        deleteReq.setTransactionID(this.getTransactonGen().generateTransactionId(deleteReq.getResourcePoolId(),
                deleteReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                deleteReq.getResourcePoolId());
        deleteReq.setPassword(resourceInfo.getUserPassword());
        deleteReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return deleteReq;
    }

    private void assebleRes(RPPEBSDeleteResp deleteResp, RuntimeContext resp) {
        resp.setAttribute(RPPEBSDeleteResp.RESP_BODY, deleteResp);
    }

    public void setDelete(EBSDelete delete) {
        this.delete = delete;
    }
}
