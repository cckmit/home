package com.neusoft.mid.cloong.ebs.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.ebs.EBSCreate;
import com.neusoft.mid.cloong.ebs.EBSCreateReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.cloong.rpws.private1072.ebs.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.ebs.BSParamArray;
import com.neusoft.mid.cloong.rpws.private1072.ebs.BSServicePort;
import com.neusoft.mid.cloong.rpws.private1072.ebs.CreateBSReq;
import com.neusoft.mid.cloong.rpws.private1072.ebs.CreateBSResp;
import com.neusoft.mid.cloong.rpws.private1072.ebs.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送创建虚拟机操作请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午02:33:06
 */
public class EBSCreateImpl implements EBSCreate {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<BSServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    private static LogService logger = LogService.getLogger(EBSCreateImpl.class);

    public RPPEBSCreateResp createEBS(EBSCreateReq req) {
        RPPEBSCreateResp resp = new RPPEBSCreateResp();
        try {
            // 打开发送客户端
            BSServicePort client = common.openClient(req, timeout, BSServicePort.class);

            // 拼装请求
            CreateBSReq webserviceReqBody = new CreateBSReq();
            RPPEBSCreateResp errorResp = genWebserviceReqBody(req, webserviceReqBody);
            if (errorResp != null) {
                return errorResp;
            }

            // 发送
            Authorization auth = genWebserviceReqHeader(req);
            Holder<Response> webserviceRespHeader = new Holder<Response>();
            CreateBSResp webserviceRespBody = client.createBS(webserviceReqBody, auth, webserviceRespHeader);

            // 组装响应
            assembleResp(resp, webserviceRespBody, webserviceRespHeader.value.getResultCode());
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.SENDMSG_ERROR, "向资源池发送消息异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_SENDMSG_ERROR);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送请求成功，获取的响应码为[").append(resp.getResultCode()).append("]，响应描述为[")
                .append(resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 生成webservice请求报文体
     * @author fengj<br>
     *         2015年2月27日 下午5:05:13
     * @return
     */
    RPPEBSCreateResp genWebserviceReqBody(EBSCreateReq req, CreateBSReq webserviceReqBody) {

        webserviceReqBody.setResourceID(req.getResourceID());
        webserviceReqBody.setBSName(req.getEbsName());
        webserviceReqBody.setAppID(req.getAppId());
        webserviceReqBody.setAppName(req.getAppName());

        if (EBSCreateModel.template.equals(req.getCreateModel())) {
            webserviceReqBody.setParamFlag("0");
            webserviceReqBody.setBSTemplateId(req.getStandardId());
        } else if (EBSCreateModel.paramArray.equals(req.getCreateModel())) {
            webserviceReqBody.setParamFlag("1");
            BSParamArray paramArray = new BSParamArray();
            webserviceReqBody.setBSParamArray(paramArray);
            paramArray.setTier(req.getBSParamArray().getTier());
            paramArray.setRAID(req.getBSParamArray().getRaid());
            paramArray.setStorageNet(req.getBSParamArray().getStorageNet());
            paramArray.setResourceType(req.getBSParamArray().getResourceType());
            paramArray.setStripeWidth(req.getBSParamArray().getStripeWidth());
            paramArray.setTierRule(req.getBSParamArray().getTierRule());
            paramArray.setTierOpen(req.getBSParamArray().getTierOpen());
            paramArray.setVolNum(req.getBSParamArray().getVolNum());
            paramArray.setVolSize(req.getBSParamArray().getVolSize());
        } else {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "接收到的块存储创建方式标示符错误:" + req.getCreateModel().getValue());
            return assmbleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR);
        }

        return null;
    }

    /**
     * 生成webservice请求头
     * @param req
     * @return
     */
    Authorization genWebserviceReqHeader(EBSCreateReq req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    private void assembleResp(RPPEBSCreateResp resp, CreateBSResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());
        if (webserviceRespBody.getBSIDs() != null && webserviceRespBody.getBSIDs().getBSID() != null) {
            resp.getBSIDs().addAll(webserviceRespBody.getBSIDs().getBSID());
        }
    }

    private RPPEBSCreateResp assmbleErrorResp(InterfaceResultCode code) {
        RPPEBSCreateResp resp = new RPPEBSCreateResp();
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
