package com.neusoft.mid.cloong.host.vmbak;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakMode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakType;
import com.neusoft.mid.cloong.rpws.private1072.vm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vm.CreateVMBackupReq;
import com.neusoft.mid.cloong.rpws.private1072.vm.CreateVMBackupResp;
import com.neusoft.mid.cloong.rpws.private1072.vm.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份创建
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class VMBakCreate {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<VMServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    /**
     * logger
     */
    private static LogService logger = LogService.getLogger(VMBakCreate.class);

    /**
     * modifyVM 修改虚拟机
     * @param req
     * @return VMModifyResp
     */
    public RPPVMBakCreateResp create(VMBakCreateReq req) {
        RPPVMBakCreateResp resp = new RPPVMBakCreateResp();
        try {
            // 打开发送客户端
            VMServicePort client = common.openClient(req, timeout, VMServicePort.class);

            // 生成请求报文体
            CreateVMBackupReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            CreateVMBackupResp webserviceRespBody = client.createVMBackup(webserviceReqBody, webserviceReqHeader,
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
    private CreateVMBackupReq genWebserviceReqBody(VMBakCreateReq req) {

        CreateVMBackupReq webserviceReqBody = new CreateVMBackupReq();
        webserviceReqBody.setVMID(req.getInfo().getVmId());
        webserviceReqBody.setBackupCyc(req.getInfo().getBackupCyc());
        webserviceReqBody.setBackStoreTime(req.getInfo().getBackStoreTime());

        webserviceReqBody.setBackupStartTime(DateParse.generateDateFormatLong(req.getInfo().getBackupStartTime()));

        if (VMBakMode.CYCLE_BACK.equals(req.getInfo().getVmBackupMode())) {
            webserviceReqBody.setBackupMode(2);
        } else if (VMBakMode.SINGLE_BACK.equals(req.getInfo().getVmBackupMode())) {
            webserviceReqBody.setBackupMode(1);
        }

        if (VMBakType.FULL_BACK.equals(req.getInfo().getVmBackupType())) {
            webserviceReqBody.setBackuptype(1);
        } else if (VMBakType.INCREMENT_BACK.equals(req.getInfo().getVmBackupType())) {
            webserviceReqBody.setBackuptype(2);
        }

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPVMBakCreateResp resp, CreateVMBackupResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());

        if (webserviceRespBody.getBackupSize() != null) {
            resp.setBackupSize(Float.valueOf(webserviceRespBody.getBackupSize()));
        }
        resp.setVmBackupId(webserviceRespBody.getVMBackupId());
        if (webserviceRespBody.getBackupTime() != null) {
            resp.setBackupTime(DateParse.generateDateFromLongString(webserviceRespBody.getBackupTime()));
        }
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPVMBakCreateResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVMBakCreateResp resp = new RPPVMBakCreateResp();
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
    public void setCommon(WebServiceClientFactory<VMServicePort> common) {
        this.common = common;
    }

}
