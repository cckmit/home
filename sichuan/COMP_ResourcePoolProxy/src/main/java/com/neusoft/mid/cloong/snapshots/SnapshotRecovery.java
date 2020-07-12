package com.neusoft.mid.cloong.snapshots;

import java.util.ArrayList;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotResp;
import com.neusoft.mid.cloong.rpws.private1072.snapshot.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.snapshot.RecoverySnapshotServiceReq;
import com.neusoft.mid.cloong.rpws.private1072.snapshot.RecoverySnapshotServiceResp;
import com.neusoft.mid.cloong.rpws.private1072.snapshot.Response;
import com.neusoft.mid.cloong.rpws.private1072.snapshot.SnapshotServicePort;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotRecovery {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<SnapshotServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    /**
     * logger
     */
    private static LogService logger = LogService.getLogger(SnapshotApply.class);

    /**
     * modifyVM 修改虚拟机
     * @param req
     * @return VMModifyResp
     */
    public RPPRecoverySnapshotResp recovery(SnapshotRecoveryReq req) {
    	RPPRecoverySnapshotResp resp = new RPPRecoverySnapshotResp();
        try {
            // 打开发送客户端
        	SnapshotServicePort client = common.openClient(req, timeout, SnapshotServicePort.class);

            // 生成请求报文体
        	RecoverySnapshotServiceReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            RecoverySnapshotServiceResp webserviceRespBody = client.recoverySnapshotService(webserviceReqBody, webserviceReqHeader,
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
        sb.append("向资源池发送恢复请求成功，获取的响应码为[").append(resp.getResultCode()).append("]，响应描述为[")
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
    private RecoverySnapshotServiceReq genWebserviceReqBody(SnapshotRecoveryReq req) {

    	RecoverySnapshotServiceReq webserviceReqBody = new RecoverySnapshotServiceReq();
    	webserviceReqBody.setVmId(req.getInfo().getVmId());
        webserviceReqBody.setSnapshotId(req.getInfo().getSnapshot_id());
        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPRecoverySnapshotResp resp, RecoverySnapshotServiceResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPRecoverySnapshotResp assmbleErrorResp(InterfaceResultCode code) {
    	RPPRecoverySnapshotResp resp = new RPPRecoverySnapshotResp();
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
    public void setCommon(WebServiceClientFactory<SnapshotServicePort> common) {
        this.common = common;
    }

}
