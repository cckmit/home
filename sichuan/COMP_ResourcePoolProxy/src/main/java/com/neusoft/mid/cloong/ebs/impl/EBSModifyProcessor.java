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
import com.neusoft.mid.cloong.ebs.EBSModify;
import com.neusoft.mid.cloong.ebs.EBSModifyReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘修改处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class EBSModifyProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(EBSModifyProcessor.class);

    /**
     * 挂载接口
     */
    private EBSModify ebsModify;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPEBSModifyReq req = (RPPEBSModifyReq) reqCxt.getAttribute(RPPEBSModifyReq.REQ_BODY);
        RPPEBSModifyResp errorResp = new RPPEBSModifyResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        EBSModifyReq modifyReq = null;
        try {
            modifyReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘修改操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟硬盘修改操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        if (modifyReq.getResourceUrl() == null || modifyReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟硬盘修改操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + modifyReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        RPPEBSModifyResp modifyResp = ebsModify.modify(modifyReq);
        assebleRes(modifyResp, respCxt);
        if (modifyResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("将编码为[" + modifyReq.getEbsId() + "]的虚拟硬盘修改操作操作成功");
        } else {
            logger.info("将编码为[" + modifyReq.getEbsId() + "]的虚拟硬盘修改操作失败,接口响应码为[" + modifyResp.getResultCode()
                    + "]，接口响应消息为[" + modifyResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPEBSModifyReq req) {
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
        if (req.getDiskSize() == 0) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储容量diskSize", null);
            return false;
        }
        return true;
    }

    private EBSModifyReq assembleEBSReq(RPPEBSModifyReq req) throws ServiceStopException, UnmatchException {
        EBSModifyReq modifyReq = new EBSModifyReq();
        modifyReq.setEbsId(req.getEbsId());
        modifyReq.setDiskSize(req.getDiskSize());
        modifyReq.setEbsName(req.getEbsName());

        modifyReq.setResourcePoolId(req.getResourcePoolId());
        modifyReq.setResourcePoolPartId(req.getResourcePoolPartId());
        modifyReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        modifyReq.setTransactionID(this.getTransactonGen().generateTransactionId(modifyReq.getResourcePoolId(),
                modifyReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                modifyReq.getResourcePoolId());
        modifyReq.setPassword(resourceInfo.getUserPassword());
        modifyReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return modifyReq;
    }

    private void assebleRes(RPPEBSModifyResp modifyResp, RuntimeContext resp) {
        resp.setAttribute(RPPEBSModifyResp.RESP_BODY, modifyResp);
    }

    /**
     * 设置ebsModify字段数据
     * @param ebsModify The ebsModify to set.
     */
    public void setEbsModify(EBSModify ebsModify) {
        this.ebsModify = ebsModify;
    }

}
