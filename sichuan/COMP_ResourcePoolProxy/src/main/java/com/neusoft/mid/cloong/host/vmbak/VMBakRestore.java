package com.neusoft.mid.cloong.host.vmbak;

import java.util.ArrayList;

import javax.xml.ws.Holder;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreResp.VlanIpInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vm.NetInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.RestoreVMBackupReq;
import com.neusoft.mid.cloong.rpws.private1072.vm.RestoreVMBackupResp;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份恢复
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class VMBakRestore {

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
    private static LogService logger = LogService.getLogger(VMBakRestore.class);

    /**
     * modifyVM 修改虚拟机
     * @param req
     * @return VMModifyResp
     */
    public RPPVMBakRestoreResp restore(VMBakRestoreReq req) {
        RPPVMBakRestoreResp resp = new RPPVMBakRestoreResp();
        try {
            // 打开发送客户端
            VMServicePort client = common.openClient(req, timeout, VMServicePort.class);

            // 生成请求报文体
            RestoreVMBackupReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            RestoreVMBackupResp webserviceRespBody = client.restoreVMBackup(webserviceReqBody, webserviceReqHeader,
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
    private RestoreVMBackupReq genWebserviceReqBody(VMBakRestoreReq req) {

        RestoreVMBackupReq webserviceReqBody = new RestoreVMBackupReq();
        webserviceReqBody.setVMBackupId(req.getInfo().getVmBackupId());
        webserviceReqBody.setVMBackupInternalID(req.getInfo().getVmBackupInternalId());

        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPVMBakRestoreResp resp, RestoreVMBackupResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());

        if (!resultCode.equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            return;
        }

        if (webserviceRespBody.getVMInfo() != null) {

            resp.setVmId(webserviceRespBody.getVMInfo().getVMID());
            if (!StringUtils.isBlank(webserviceRespBody.getVMInfo().getAcceptTime())) {
                resp.setAcceptTime(DateParse.generateDateFromLongString(webserviceRespBody.getVMInfo().getAcceptTime()));
            }
            resp.setVmName(webserviceRespBody.getVMInfo().getVMName());
            resp.setVlanIpInfo(new ArrayList<RPPVMBakRestoreResp.VlanIpInfo>());

            if (webserviceRespBody.getVMInfo().getVlanIPInfo() != null
                    && webserviceRespBody.getVMInfo().getVlanIPInfo().getNetInfo() != null) {
                for (NetInfo netInfo : webserviceRespBody.getVMInfo().getVlanIPInfo().getNetInfo()) {
                    VlanIpInfo vlanIpInfo = new VlanIpInfo();
                    vlanIpInfo.setVlanId(netInfo.getVLanID());
                    vlanIpInfo.setPrivateIp(netInfo.getPrivateIP());
                    resp.getVlanIpInfo().add(vlanIpInfo);
                }
            }
        }

    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPVMBakRestoreResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVMBakRestoreResp resp = new RPPVMBakRestoreResp();
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
