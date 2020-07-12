/*******************************************************************************
 * @(#)EBSDeleteImpl.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.ebs.EBSDelete;
import com.neusoft.mid.cloong.ebs.EBSDeleteReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.cloong.rpws.private1072.ebs.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.ebs.BSServicePort;
import com.neusoft.mid.cloong.rpws.private1072.ebs.DeleteBSReq;
import com.neusoft.mid.cloong.rpws.private1072.ebs.DeleteBSResp;
import com.neusoft.mid.cloong.rpws.private1072.ebs.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除虚拟硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午03:11:39
 */
public class EBSDeleteImpl implements EBSDelete {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<BSServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    private static LogService logger = LogService.getLogger(EBSDeleteImpl.class);

    /*
     * (non-Javadoc)
     * @see com.neusoft.mid.cloong.ebs.EBSDelete#delete(com.neusoft.mid.cloong.ebs.EBSDeleteReq)
     */
    @Override
    public RPPEBSDeleteResp delete(EBSDeleteReq req) {
        // 打开发送客户端
        BSServicePort client = common.openClient(req, timeout, BSServicePort.class);
        RPPEBSDeleteResp resp = new RPPEBSDeleteResp();

        DeleteBSReq webserviceReqBody = new DeleteBSReq();
        webserviceReqBody.setBSID(req.getEbsId());

        DeleteBSResp webserviceRespBody = null;
        String resultCode = null;
        try {
            Authorization auth = genWebserviceReqHeader(req);
            Holder<Response> webserviceRespHeader = new Holder<Response>();
            webserviceRespBody = client.deleteBS(webserviceReqBody, auth, webserviceRespHeader);
            resultCode = webserviceRespHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "将编码为[" + req.getEbsId() + "]的虚拟硬盘删除操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "将编码为[" + req.getEbsId() + "]的虚拟硬盘删除操作失败,内部错误，网络异常或xml解析异常",
                    e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        // 组装响应

        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送编码为[" + req.getEbsId() + "]的虚拟硬盘删除操作请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 生成webservice请求头
     * @param req
     * @return
     */
    Authorization genWebserviceReqHeader(EBSDeleteReq req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    private RPPEBSDeleteResp assmbleErrorResp(InterfaceResultCode code) {
        RPPEBSDeleteResp resp = new RPPEBSDeleteResp();
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
