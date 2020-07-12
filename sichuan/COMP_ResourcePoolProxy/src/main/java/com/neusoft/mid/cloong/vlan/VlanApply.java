package com.neusoft.mid.cloong.vlan;

import java.util.ArrayList;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.VlanType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.ZoneType;
import com.neusoft.mid.cloong.rpws.private1072.vlan.ApplyVLANServiceReq;
import com.neusoft.mid.cloong.rpws.private1072.vlan.ApplyVLANServiceResp;
import com.neusoft.mid.cloong.rpws.private1072.vlan.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vlan.Response;
import com.neusoft.mid.cloong.rpws.private1072.vlan.VLANServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请IP
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class VlanApply {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<VLANServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    /**
     * logger
     */
    private static LogService logger = LogService.getLogger(VlanApply.class);

    /**
     * modifyVM 修改虚拟机
     * @param req
     * @return VMModifyResp
     */
    public RPPVlanApplyResp apply(VlanApplyReq req) {
        RPPVlanApplyResp resp = new RPPVlanApplyResp();
        try {
            // 打开发送客户端
            VLANServicePort client = common.openClient(req, timeout, VLANServicePort.class);

            // 生成请求报文体
            ApplyVLANServiceReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            ApplyVLANServiceResp webserviceRespBody = client.applyVLANService(webserviceReqBody, webserviceReqHeader,
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
    private ApplyVLANServiceReq genWebserviceReqBody(VlanApplyReq req) {

        ApplyVLANServiceReq webserviceReqBody = new ApplyVLANServiceReq();
        webserviceReqBody.setAppID(req.getInfo().getAppId());
        webserviceReqBody.setAppName(req.getInfo().getAppName());
        webserviceReqBody.setVlanNum(req.getInfo().getCount() + "");

        if (ZoneType.DMZ_ZONE.equals(req.getInfo().getZoneType())) {
            webserviceReqBody.setZoneType(1);
        } else if (ZoneType.TRUST_ZONE.equals(req.getInfo().getZoneType())) {
            webserviceReqBody.setZoneType(2);
        } else if (ZoneType.TEST_ZONE.equals(req.getInfo().getZoneType())) {
            webserviceReqBody.setZoneType(3);
        }

        if (VlanType.Business.equals(req.getInfo().getVlanType())) {
            webserviceReqBody.setVLANType("2");
        } else if (VlanType.IPStorage.equals(req.getInfo().getVlanType())) {
            webserviceReqBody.setVLANType("3");
        }

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPVlanApplyResp resp, ApplyVLANServiceResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());

        resp.setVlanIdList(new ArrayList<String>());
        if (webserviceRespBody.getVLANID() != null && webserviceRespBody.getVLANID().getVLANID() != null) {
            resp.getVlanIdList().addAll(webserviceRespBody.getVLANID().getVLANID());
        }
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPVlanApplyResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVlanApplyResp resp = new RPPVlanApplyResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    /**
     * 设置timeout字段数据
     * @param timeout The timeout to set.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置common字段数据
     * @param common The common to set.
     */
    public void setCommon(WebServiceClientFactory<VLANServicePort> common) {
        this.common = common;
    }

}
