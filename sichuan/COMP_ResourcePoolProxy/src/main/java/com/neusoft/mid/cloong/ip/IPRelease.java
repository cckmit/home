package com.neusoft.mid.cloong.ip;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.impl.VMModifyImpl;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseResp;
import com.neusoft.mid.cloong.rpws.private1072.ip.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.ip.IPServicePort;
import com.neusoft.mid.cloong.rpws.private1072.ip.ReleaseIPReq;
import com.neusoft.mid.cloong.rpws.private1072.ip.ReleaseIPResp;
import com.neusoft.mid.cloong.rpws.private1072.ip.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 释放IP
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class IPRelease {

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
    private static LogService logger = LogService.getLogger(IPRelease.class);

    /**
     * 释放IP
     * @param ipReleaseReq
     * @return VMModifyResp
     */
    public RPPIPReleaseResp query(IPRelaseReq ipReleaseReq) {
        RPPIPReleaseResp resp = new RPPIPReleaseResp();
        try {
            // 打开发送客户端
            IPServicePort client = common.openClient(ipReleaseReq, timeout, IPServicePort.class);

            // 生成请求报文体
            ReleaseIPReq webserviceReqBody = genWebserviceReqBody(ipReleaseReq);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(ipReleaseReq);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            ReleaseIPResp webserviceRespBody = client.releaseIP(webserviceReqBody, webserviceReqHeader,
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
    private ReleaseIPReq genWebserviceReqBody(IPRelaseReq req) {

        ReleaseIPReq webserviceReqBody = new ReleaseIPReq();

        webserviceReqBody.setIP(req.getInfo().getIp());
        webserviceReqBody.setIPSegmentURI(req.getInfo().getIpSegmentURI());

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPIPReleaseResp resp, ReleaseIPResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPIPReleaseResp assmbleErrorResp(InterfaceResultCode code) {
        RPPIPReleaseResp resp = new RPPIPReleaseResp();
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
