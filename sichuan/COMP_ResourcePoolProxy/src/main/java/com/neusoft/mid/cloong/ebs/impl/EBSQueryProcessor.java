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
import com.neusoft.mid.cloong.ebs.EBSQuery;
import com.neusoft.mid.cloong.ebs.EBSQueryReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSQueryResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘查询处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class EBSQueryProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(EBSQueryProcessor.class);

    /**
     * 挂载接口
     */
    private EBSQuery ebsQuery;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPEBSQueryReq req = (RPPEBSQueryReq) reqCxt.getAttribute(RPPEBSQueryReq.REQ_BODY);
        RPPEBSQueryResp errorResp = new RPPEBSQueryResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        EBSQueryReq queryReq = null;
        try {
            queryReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘" + req.getEbsId()
                    + "查询操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:" + (String) reqCxt.getAttribute("SERIAL_NUM"),
                    null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟硬盘" + req.getEbsId() + "查询操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        if (queryReq.getResourceUrl() == null || queryReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘" + req.getEbsId()
                    + "查询操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + queryReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        RPPEBSQueryResp queryResp = ebsQuery.query(queryReq);
        assebleRes(queryResp, respCxt);
        if (queryResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("向资源池发送虚拟硬盘" + req.getEbsId() + "查询操作操作成功");
        } else {
            logger.info("向资源池发送虚拟硬盘" + req.getEbsId() + "查询操作失败,接口响应码为[" + queryResp.getResultCode() + "]，接口响应消息为["
                    + queryResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPEBSQueryReq req) {
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
        return true;
    }

    private EBSQueryReq assembleEBSReq(RPPEBSQueryReq req) throws ServiceStopException, UnmatchException {
        EBSQueryReq queryReq = new EBSQueryReq();
        queryReq.setEbsId(req.getEbsId());

        queryReq.setResourcePoolId(req.getResourcePoolId());
        queryReq.setResourcePoolPartId(req.getResourcePoolPartId());
        queryReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        queryReq.setTransactionID(this.getTransactonGen().generateTransactionId(queryReq.getResourcePoolId(),
                queryReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                queryReq.getResourcePoolId());
        queryReq.setPassword(resourceInfo.getUserPassword());
        queryReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return queryReq;
    }

    private void assebleRes(RPPEBSQueryResp queryResp, RuntimeContext resp) {
        resp.setAttribute(RPPEBSQueryResp.RESP_BODY, queryResp);
    }

    /**
     * 设置ebsQuery字段数据
     * @param ebsQuery The ebsQuery to set.
     */
    public void setEbsQuery(EBSQuery ebsQuery) {
        this.ebsQuery = ebsQuery;
    }

}
