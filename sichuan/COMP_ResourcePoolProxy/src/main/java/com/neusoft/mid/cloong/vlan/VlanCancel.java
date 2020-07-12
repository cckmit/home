package com.neusoft.mid.cloong.vlan;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelResp;
import com.neusoft.mid.cloong.rpws.private1072.vlan.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vlan.CancelVLANServiceReq;
import com.neusoft.mid.cloong.rpws.private1072.vlan.CancelVLANServiceResp;
import com.neusoft.mid.cloong.rpws.private1072.vlan.Response;
import com.neusoft.mid.cloong.rpws.private1072.vlan.VLANServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 释放IP
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class VlanCancel {

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
    private static LogService logger = LogService.getLogger(VlanCancel.class);

    /**
     * 释放IP
     * @param vlanCancelReq
     * @return VMModifyResp
     */
    public RPPVlanCancelResp cancel(VlanCancelReq vlanCancelReq) {
        RPPVlanCancelResp resp = new RPPVlanCancelResp();
        try {
            // 打开发送客户端
            VLANServicePort client = common.openClient(vlanCancelReq, timeout, VLANServicePort.class);

            // 生成请求报文体
            CancelVLANServiceReq webserviceReqBody = genWebserviceReqBody(vlanCancelReq);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(vlanCancelReq);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            CancelVLANServiceResp webserviceRespBody = client.cancelVLANService(webserviceReqBody, webserviceReqHeader,
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
    private CancelVLANServiceReq genWebserviceReqBody(VlanCancelReq req) {

        CancelVLANServiceReq webserviceReqBody = new CancelVLANServiceReq();

        webserviceReqBody.setVLANID(req.getInfo().getVlanId());

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPVlanCancelResp resp, CancelVLANServiceResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPVlanCancelResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVlanCancelResp resp = new RPPVlanCancelResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    /**
     * 虚拟机状态——安装中
     */
    public static final int VM_STATUS_INSTALLING = 0;

    /**
     * 虚拟机状态——启动中
     */
    public static final int VM_STATUS_STARTING = 1;

    /**
     * 虚拟机状态——运行
     */
    public static final int VM_STATUS_RUNNING = 2;

    /**
     * 虚拟机状态——关机
     */
    public static final int VM_STATUS_STOP = 3;

    /**
     * 虚拟机状态——挂起/暂停
     */
    public static final int VM_STATUS_PAUSE = 4;

    /**
     * 虚拟机状态——不可用
     */
    public static final int VM_STATUS_UNAVAILABLE = 5;

    /**
     * 设置common字段数据
     * @param common The common to set.
     */
    public void setCommon(WebServiceClientFactory<VLANServicePort> common) {
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
