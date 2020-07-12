/*******************************************************************************
 * @(#)PMDeleteProcessor.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.pm.PMDelete;
import com.neusoft.mid.cloong.host.pm.PMDeleteReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMDeleteResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机删除处理器
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:33:00
 */
public class PMDeleteProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(PMDeleteProcessor.class);

    /**
     * 和资源池交互的物理机删除接口
     */
    private PMDelete pmDelete;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPPMDeleteReq req = (RPPPMDeleteReq) reqCxt.getAttribute(RPPPMDeleteReq.REQ_BODY);
        RPPPMDeleteResp resp = new RPPPMDeleteResp();

        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        PMDeleteReq pmReq = null;
        try {
            pmReq = assemblePMReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机删除请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + req.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送物理机删除请求失败，资源池代理系统内部错误，无法通过给定的资源池ID" + req.getResourcePoolId() + "获取到资源池信息，本次操作流水号为:"
                            + req.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (pmReq.getResourceUrl() == null || pmReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机删除请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:"
                    + pmReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPPMDeleteResp pmResp = pmDelete.deletePM(pmReq);
        assebleRes(pmResp, respCxt);
        if (pmResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("物理机编码为[" + pmReq.getPmId() + "]的物理机删除成功,本次操作流水号为:" + pmReq.getSerialNum());
        } else {
            logger.info("物理机编码为[" + pmReq.getPmId() + "]的物理机删除失败,接口响应码为[" + pmResp.getResultCode() + "]，接口响应消息为["
                    + pmResp.getResultMessage() + "],本次操作流水号为:" + pmReq.getSerialNum());
        }
        return SUCCESS;
    }

    private PMDeleteReq assemblePMReq(RPPPMDeleteReq req) throws ServiceStopException, UnmatchException {
        PMDeleteReq pmReq = new PMDeleteReq();
        pmReq.setPmId(req.getPmId());
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

    private void assebleRes(RPPPMDeleteResp pmResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPPMDeleteResp.RESP_BODY, pmResp);
    }

    public void setPmDelete(PMDelete pmDelete) {
        this.pmDelete = pmDelete;
    }

    private boolean validateInputPara(RPPPMDeleteReq req) {
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
        return true;
    }
}
