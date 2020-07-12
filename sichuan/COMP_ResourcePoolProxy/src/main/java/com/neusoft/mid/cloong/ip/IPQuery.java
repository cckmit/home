package com.neusoft.mid.cloong.ip;

import java.util.ArrayList;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.impl.VMModifyImpl;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPApplyState;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPProType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp;
import com.neusoft.mid.cloong.rpws.private1072.ip.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.ip.IPSegmentInfo;
import com.neusoft.mid.cloong.rpws.private1072.ip.IPServicePort;
import com.neusoft.mid.cloong.rpws.private1072.ip.QueryIPSegmentReq;
import com.neusoft.mid.cloong.rpws.private1072.ip.QueryIPSegmentResp;
import com.neusoft.mid.cloong.rpws.private1072.ip.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询IP
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class IPQuery {

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
    private static LogService logger = LogService.getLogger(IPQuery.class);

    /**
     * IP查询
     * @param req
     * @return VMModifyResp
     */
    public RPPIPQueryResp query(IPQueryReq req) {
        RPPIPQueryResp resp = new RPPIPQueryResp();
        try {
            // 打开发送客户端
            IPServicePort client = common.openClient(req, timeout, IPServicePort.class);

            // 生成请求报文体
            QueryIPSegmentReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            QueryIPSegmentResp webserviceRespBody = client.queryIPSegment(webserviceReqBody, webserviceReqHeader,
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
    private QueryIPSegmentReq genWebserviceReqBody(IPQueryReq req) {

        QueryIPSegmentReq webserviceReqBody = new QueryIPSegmentReq();

        if (IPType.PUBLIC_IP.equals(req.getInfo().getIpType())) {
            webserviceReqBody.setIPType(0);
        } else if (IPType.PRIVATE_IP.equals(req.getInfo().getIpType())) {
            webserviceReqBody.setIPType(2);
        } else if (IPType.BEARER_IP.equals(req.getInfo().getIpType())) {
            webserviceReqBody.setIPType(1);
        }

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPIPQueryResp resp, QueryIPSegmentResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());

        if (webserviceRespBody.getIPSegmentInfo() != null
                && webserviceRespBody.getIPSegmentInfo().getIPSegmentInfo() != null) {
            resp.setIpSegmentInfoList(new ArrayList<RPPIPQueryResp.IPSegmentInfo>());
            for (IPSegmentInfo webserviceIPSegmentInfo : webserviceRespBody.getIPSegmentInfo().getIPSegmentInfo()) {
                RPPIPQueryResp.IPSegmentInfo info = new RPPIPQueryResp.IPSegmentInfo();
                resp.getIpSegmentInfoList().add(info);

                info.setIpSegmentURI(webserviceIPSegmentInfo.getIPSegmentURI());
                info.setIpSubNet(webserviceIPSegmentInfo.getIPSubNet());

                // 设置IP类型
                if ("IPV4".equals(webserviceIPSegmentInfo.getIpProType())) {
                    info.setIpProType(IPProType.IPV4);
                } else if ("IPV6".equals(webserviceIPSegmentInfo.getIpProType())) {
                    info.setIpProType(IPProType.IPV6);
                } else {
                    // TODO 增加异常处理
                }

                // 设置申请状态
                if ("0".equals(webserviceIPSegmentInfo.getApplyState())) {
                    info.setApplyState(IPApplyState.NO_APPLY);
                } else if ("1".equals(webserviceIPSegmentInfo.getApplyState())) {
                    info.setApplyState(IPApplyState.PART_APPLY);
                } else if ("2".equals(webserviceIPSegmentInfo.getApplyState())) {
                    info.setApplyState(IPApplyState.ALL_APPLY);
                } else {
                    // TODO 增加异常处理
                }

                if ("0".equals(webserviceIPSegmentInfo.getIPType())) {
                    info.setIpType(IPType.PUBLIC_IP);
                } else if ("1".equals(webserviceIPSegmentInfo.getIPType())) {
                    info.setIpType(IPType.BEARER_IP);
                } else if ("2".equals(webserviceIPSegmentInfo.getIPType())) {
                    info.setIpType(IPType.PRIVATE_IP);
                } else {
                    // TODO 增加异常处理
                }

            }
        }

    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPIPQueryResp assmbleErrorResp(InterfaceResultCode code) {
        RPPIPQueryResp resp = new RPPIPQueryResp();
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
