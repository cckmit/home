/*******************************************************************************
 * @(#)PMDeleteImpl.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.pm.PMDelete;
import com.neusoft.mid.cloong.host.pm.PMDeleteReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMDeleteResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.pm.DeleteSRVReq;
import com.neusoft.mid.cloong.rpws.private1072.pm.DeleteSRVResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.Response;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-12 上午09:32:07
 */
public class PMDeleteImpl implements PMDelete {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<SRVServicePort> common;

    private static LogService logger = LogService.getLogger(PMDeleteImpl.class);

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    /*
     * (non-Javadoc)
     * @see
     * com.neusoft.mid.cloong.host.pm.PMDelete#deletePM(com.neusoft.mid.cloong.host.pm.PMDeleteReq)
     */
    @Override
    public RPPPMDeleteResp deletePM(PMDeleteReq req) {
        // 打开发送客户端
        SRVServicePort client = common.openClient(req, timeout, SRVServicePort.class);
        RPPPMDeleteResp resp = new RPPPMDeleteResp();

        DeleteSRVReq webserviceReqBody = new DeleteSRVReq();
        webserviceReqBody.setSRVID(req.getPmId());

        DeleteSRVResp webserviceRespBody = null;
        String resultCode = null;
        try {
            Authorization auth = genWebserviceReqHeader(req);
            Holder<Response> webserviceRespHeader = new Holder<Response>();
            webserviceRespBody = client.deleteSRV(webserviceReqBody, auth, webserviceRespHeader);
            resultCode = webserviceRespHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送物理机编码为[" + req.getPmId()
                        + "]的物理机删除失败，资源池认证失败，本次操作流水号为:" + req.getSerialNum(), e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机编码为[" + req.getPmId()
                    + "]的物理机删除失败,内部错误，网络异常或xml解析异常，本次操作流水号为:" + req.getSerialNum(), e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        // 组装响应
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送物理机编码为[" + req.getPmId() + "]的物理机删除请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]，本次操作流水号为:").append(req.getSerialNum());
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 生成webservice请求头
     * @param req
     * @return
     */
    Authorization genWebserviceReqHeader(PMDeleteReq req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    private RPPPMDeleteResp assmbleErrorResp(InterfaceResultCode code) {
        RPPPMDeleteResp resp = new RPPPMDeleteResp();
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
