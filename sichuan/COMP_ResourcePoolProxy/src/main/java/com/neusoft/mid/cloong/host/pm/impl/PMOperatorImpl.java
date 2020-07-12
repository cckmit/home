/*******************************************************************************
 * @(#)PMOperatorImpl.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.pm.PMOperator;
import com.neusoft.mid.cloong.host.pm.PMOperatorReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMOperatorResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.ActSRVReq;
import com.neusoft.mid.cloong.rpws.private1072.pm.ActSRVResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.pm.Response;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送操作物理机操作请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-5 下午04:35:45
 */
public class PMOperatorImpl implements PMOperator {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<SRVServicePort> common;

    private static LogService logger = LogService.getLogger(PMOperatorImpl.class);

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    /*
     * (non-Javadoc)
     * @see
     * com.neusoft.mid.cloong.comp.PMOperator#operatePM(com.neusoft.mid.cloong.comp.PMOperatorReq)
     */
    @Override
    public RPPPMOperatorResp operatePM(PMOperatorReq req) {
        // 打开发送客户端
        SRVServicePort client = common.openClient(req, timeout, SRVServicePort.class);

        ActSRVReq webserviceReq = new ActSRVReq();
        webserviceReq.setSRVID(req.getPmId());
        // 这里应该加入switch，根据不同操作类型填值.但枚举是按照私有云接口值定义的,因此不会有问题，但不严谨
        webserviceReq.setActType(req.getType().getValue());

        RPPPMOperatorResp resp = new RPPPMOperatorResp();
        ActSRVResp webserviceRespBody = null;
        String resultCode = null;
        try {
            Authorization auth = genWebserviceReqHeader(req);
            Holder<Response> webserviceRespHeader = new Holder<Response>();
            webserviceRespBody = client.actSRV(webserviceReq, auth, webserviceRespHeader);
            resultCode = webserviceRespHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送物理机编码为[" + req.getPmId()
                        + "]的物理机操作失败，资源池认证失败，本次操作流水号为:" + req.getSerialNum(), e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机编码为[" + req.getPmId()
                    + "]的物理机操作失败,内部错误，网络异常或xml解析异常，本次操作流水号为:" + req.getSerialNum(), e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }

        // 组装响应
        resp.setResultCode(resultCode);
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setFaultstring(webserviceRespBody.getFaultString());

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送物理机编码为[" + req.getPmId() + "]的物理机操作请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]，本次操作流水号为:").append(req.getSerialNum());
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 生成webservice请求头
     * @param req
     * @return
     */
    Authorization genWebserviceReqHeader(PMOperatorReq req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    private RPPPMOperatorResp assmbleErrorResp(InterfaceResultCode code) {
        RPPPMOperatorResp resp = new RPPPMOperatorResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    public void setCommon(WebServiceClientFactory<SRVServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
