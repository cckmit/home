/*******************************************************************************
 * @(#)EBSDeleteImpl.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.impl;

import java.util.ArrayList;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.ebs.EBSQuery;
import com.neusoft.mid.cloong.ebs.EBSQueryReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSStatus;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSQueryResp;
import com.neusoft.mid.cloong.rpws.private1072.ebs.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.ebs.BSServicePort;
import com.neusoft.mid.cloong.rpws.private1072.ebs.QueryBSReq;
import com.neusoft.mid.cloong.rpws.private1072.ebs.QueryBSResp;
import com.neusoft.mid.cloong.rpws.private1072.ebs.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改虚拟硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午03:11:39
 */
public class EBSQueryImpl implements EBSQuery {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<BSServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    private static LogService logger = LogService.getLogger(EBSQueryImpl.class);

    /*
     * (non-Javadoc)
     * @see com.neusoft.mid.cloong.ebs.EBSDelete#delete(com.neusoft.mid.cloong.ebs.EBSDeleteReq)
     */
    @Override
    public RPPEBSQueryResp query(EBSQueryReq req) {
        // 打开发送客户端
        BSServicePort client = common.openClient(req, timeout, BSServicePort.class);
        RPPEBSQueryResp resp = new RPPEBSQueryResp();

        QueryBSReq webserviceReqBody = genWebserviceReqBody(req);

        QueryBSResp webserviceRespBody = null;
        String resultCode = null;
        try {
            Authorization auth = genWebserviceReqHeader(req);
            Holder<Response> webserviceRespHeader = new Holder<Response>();
            webserviceRespBody = client.queryBS(webserviceReqBody, auth, webserviceRespHeader);
            resultCode = webserviceRespHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "将编码为[" + req.getEbsId() + "]的虚拟硬盘查询操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "将编码为[" + req.getEbsId() + "]的虚拟硬盘查询操作失败,内部错误，网络异常或xml解析异常",
                    e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        // 组装响应

        assembleResp(resp, webserviceRespBody, resultCode);

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送编码为[" + req.getEbsId() + "]的虚拟硬盘查询操作请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 组装应答
     * @param resp
     * @param webserviceRespBody
     * @param resultCode
     */
    void assembleResp(RPPEBSQueryResp resp, QueryBSResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultString(webserviceRespBody.getFaultString());

        resp.setBsName(webserviceRespBody.getBSName());
        resp.setBsSize(webserviceRespBody.getBSSize());
        resp.setTier(webserviceRespBody.getTier());
        resp.setHostIdList(new ArrayList<String>());

        if (webserviceRespBody.getResourceIDs() != null && webserviceRespBody.getResourceIDs().getResourceID() != null) {
            resp.getHostIdList().addAll(webserviceRespBody.getResourceIDs().getResourceID());
        }

        if (webserviceRespBody.getBSState() != null){
            resp.setBsStatus(EBSStatus.fromValue(webserviceRespBody.getBSState()));
        }
        
    }

    /**
     * 生成webservice请求报文体
     * @param req
     * @return
     */
    QueryBSReq genWebserviceReqBody(EBSQueryReq req) {
        QueryBSReq webserviceReqBody = new QueryBSReq();
        webserviceReqBody.setBSID(req.getEbsId());
        return webserviceReqBody;
    }

    /**
     * 生成webservice请求头
     * @param req
     * @return
     */
    Authorization genWebserviceReqHeader(EBSQueryReq req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    private RPPEBSQueryResp assmbleErrorResp(InterfaceResultCode code) {
        RPPEBSQueryResp resp = new RPPEBSQueryResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    public void setCommon(WebServiceClientFactory<BSServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
