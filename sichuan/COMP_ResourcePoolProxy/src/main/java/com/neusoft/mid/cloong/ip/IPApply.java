package com.neusoft.mid.cloong.ip;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.impl.VMModifyImpl;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPProType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyResp;
import com.neusoft.mid.cloong.rpws.private1072.ip.ApplyIPReq;
import com.neusoft.mid.cloong.rpws.private1072.ip.ApplyIPResp;
import com.neusoft.mid.cloong.rpws.private1072.ip.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.ip.IPParamArray;
import com.neusoft.mid.cloong.rpws.private1072.ip.IPServicePort;
import com.neusoft.mid.cloong.rpws.private1072.ip.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请IP
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class IPApply {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<IPServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    /**
     * logger
     */
    private static LogService logger = LogService.getLogger(IPApply.class);

    /**
     * modifyVM 修改虚拟机
     * @param req
     * @return VMModifyResp
     */
    public RPPIPApplyResp apply(IPApplyReq req) {
        RPPIPApplyResp resp = new RPPIPApplyResp();
        try {
            // 打开发送客户端
            IPServicePort client = common.openClient(req, timeout, IPServicePort.class);

            // 生成请求报文体
            ApplyIPReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            ApplyIPResp webserviceRespBody = client.applyIP(webserviceReqBody, webserviceReqHeader,
                    webserviceRespHeader);

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
     * 生成webservice请求头
     * @param req
     * @return
     */
    private Authorization genWebserviceReqHeader(ReqBodyInfo req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    /**
     * 根据请求的内容，生成webservice请求报文体
     * @param req
     * @return
     */
    private ApplyIPReq genWebserviceReqBody(IPApplyReq req) {

        ApplyIPReq webserviceReqBody = new ApplyIPReq();
        webserviceReqBody.setAppID(req.getInfo().getAppId());
        webserviceReqBody.setAppName(req.getInfo().getAppName());
        req.getInfo();
        if (req.getInfo().getCount() != RPPBaseReq.INT_DEFAULT_VAL) {
            webserviceReqBody.setCount(req.getInfo().getCount() + "");
        }
        webserviceReqBody.setIPSegmentURI(req.getInfo().getIpSegmentURI());

        if (IPCreateModel.CustomModel.equals(req.getInfo().getCreateModel())) {
            webserviceReqBody.setParamFlag("1");
            IPParamArray ipParamArray = new IPParamArray();
            webserviceReqBody.setIPParamArray(ipParamArray);

            if (IPProType.IPV4.equals(req.getInfo().getIpProType())) {
                ipParamArray.setIpProType("IPV4");
            } else if (IPProType.IPV6.equals(req.getInfo().getIpProType())) {
                ipParamArray.setIpProType("IPV6");
            }

            if (IPType.PUBLIC_IP.equals(req.getInfo().getIpType())) {
                ipParamArray.setIPType(0);
            } else if (IPType.PRIVATE_IP.equals(req.getInfo().getIpType())) {
                ipParamArray.setIPType(2);
            } else if (IPType.BEARER_IP.equals(req.getInfo().getIpType())) {
                ipParamArray.setIPType(1);
            }
        } else if (IPCreateModel.TemplateModel.equals(req.getInfo().getCreateModel())) {
            webserviceReqBody.setParamFlag("0");
            webserviceReqBody.setIPTemplateId(req.getInfo().getStandardId());
        }

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPIPApplyResp resp, ApplyIPResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);

        if (webserviceRespBody.getIP() != null && webserviceRespBody.getIP().getIP() != null) {
            resp.setIp(webserviceRespBody.getIP().getIP());
        }
        resp.setIpSubNet(webserviceRespBody.getIPSubNet());
        resp.setIpSegmentURI(webserviceRespBody.getIPSegmentURI());
        resp.setFaultstring(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setResultMessage(webserviceRespBody.getFaultString());
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPIPApplyResp assmbleErrorResp(InterfaceResultCode code) {
        RPPIPApplyResp resp = new RPPIPApplyResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    /**
     * 设置common字段数据
     * @param common The common to set.
     */
    public void setCommon(WebServiceClientFactory<IPServicePort> common) {
        this.common = common;
    }

    /**
     * 设置timeout字段数据
     * @param timeout The timeout to set.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
