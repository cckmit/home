/*******************************************************************************
 * @(#)PMCreateProcessor.java 2013-2-5
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
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.pm.PMModify;
import com.neusoft.mid.cloong.host.pm.PMModifyReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMModifyResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机修改处理器
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.1 $ 2014-5-13 下午04:05:05
 */
public class PMModifyProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(PMModifyProcessor.class);

    private IbatisDAO ibatisDAO;

    /**
     * 和资源池交互的物理机创建接口
     */
    private PMModify pmModify;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 请求中物理机操作流水号最大长度
     */
    private static final int SERIAL_NUM_LENGTH = 30;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPPMModifyReq req = (RPPPMModifyReq) reqCxt.getAttribute(RPPPMModifyReq.REQ_BODY);
        RPPPMModifyResp resp = new RPPPMModifyResp();

        // 校验输入
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        PMModifyReq pmModifyReq = null;
        try {
            pmModifyReq = assemblePMReq(req);
        } catch (ServiceStopException e1) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e1);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e2) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID["+req.getResourcePoolId()+"]获取到资源池信息", e2);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e3) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID["+req.getResourcePoolId()+"]获取到资源池信息", e3);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (pmModifyReq.getResourceUrl() == null || pmModifyReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        // 发送创建请求
        RPPPMModifyResp pmResp = pmModify.modifyPM(pmModifyReq);

        assebleRes(pmResp, respCxt);
        if (pmResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("物理机编码为[" + pmModifyReq.getPmId() + "]的物理机修改成功,本次操作流水号为:" + pmModifyReq.getSerialNum());
        } else {
            logger.info("物理机编码为[" + pmModifyReq.getPmId() + "]的物理机修改失败,接口响应码为[" + pmResp.getResultCode() + "]，接口响应消息为["
                    + pmResp.getResultMessage() + "],本次操作流水号为:" + pmModifyReq.getSerialNum());
        }
        return SUCCESS;
    }

    /**
     * assemblePMReq 组装请求
     * @param req
     * @return
     * @throws ServiceStopException
     * @throws UnmatchException
     */
    private PMModifyReq assemblePMReq(RPPPMModifyReq req) throws ServiceStopException, UnmatchException {
        PMModifyReq pmModifyReq = new PMModifyReq();
        pmModifyReq.setPmId(req.getPmId());
        pmModifyReq.setSerialNum(req.getSerialNum());
        pmModifyReq.setPmName(req.getPmName());
        pmModifyReq.getEthList().addAll(req.getEthList());
        pmModifyReq.setResourcePoolId(req.getResourcePoolId());
        pmModifyReq.setResourcePoolPartId(req.getResourcePoolPartId());
        pmModifyReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        pmModifyReq.setTransactionID(this.getTransactonGen().generateTransactionId(pmModifyReq.getResourcePoolId(),
                pmModifyReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                pmModifyReq.getResourcePoolId());
        pmModifyReq.setPassword(resourceInfo.getUserPassword());
        pmModifyReq.setResourceUrl(resourceInfo.getResourcePoolUrl());

        if (logger.isDebugEnable()) {
            logger.debug("获得修改物理机的请求为" + pmModifyReq.toString());
        }
        return pmModifyReq;
    }

    /**
     * assebleRes
     * @param pmResp
     * @param respCxt
     */
    private void assebleRes(RPPPMModifyResp pmResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPPMModifyResp.RESP_BODY, pmResp);
    }

    /**
     * validateInputPara 校验输入参数
     * @param req
     * @return boolean
     */
    private boolean validateInputPara(RPPPMModifyReq req) {
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
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查请求流水号SerialNum", null);
            return false;
        }
        return true;
    }

    /**
     * 获取ibatisDAO字段数据
     * @return Returns the ibatisDAO.
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * 获取pmModify字段数据
     * @return Returns the pmModify.
     */
    public PMModify getPmModify() {
        return pmModify;
    }

    /**
     * 设置pmModify字段数据
     * @param pmModify The pmModify to set.
     */
    public void setPmModify(PMModify pmModify) {
        this.pmModify = pmModify;
    }

}
