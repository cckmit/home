package com.neusoft.mid.cloong.host.vmbak;

import java.util.ArrayList;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryResp.VMBakInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vm.BackupInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.QueryVMBackupReq;
import com.neusoft.mid.cloong.rpws.private1072.vm.QueryVMBackupResp;
import com.neusoft.mid.cloong.rpws.private1072.vm.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份列表查询
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class VMBakListQuery {

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
    private static LogService logger = LogService.getLogger(VMBakListQuery.class);

    /**
     * 虚拟机备份列表查询
     * @param req
     * @return VMModifyResp
     */
    public RPPVMBakListQueryResp query(VMBakListQueryReq req) {
        RPPVMBakListQueryResp resp = new RPPVMBakListQueryResp();
        try {
            // 打开发送客户端
            VMServicePort client = common.openClient(req, timeout, VMServicePort.class);

            // 生成请求报文体
            QueryVMBackupReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            QueryVMBackupResp webserviceRespBody = client.queryVMBackup(webserviceReqBody, webserviceReqHeader,
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
    private QueryVMBackupReq genWebserviceReqBody(VMBakListQueryReq req) {
        QueryVMBackupReq webserviceReqBody = new QueryVMBackupReq();
        webserviceReqBody.setVMBackupId(req.getInfo().getVmBackupId());
        return webserviceReqBody;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     * @throws Exception
     */
    private void assembleResp(RPPVMBakListQueryResp resp, QueryVMBackupResp webserviceRespBody, String resultCode)
            throws Exception {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
        resp.setFaultstring(webserviceRespBody.getFaultString());

        if (!resultCode.equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            return;
        }

        resp.setBackupList(new ArrayList<RPPVMBakListQueryResp.VMBakInfo>());
        if (webserviceRespBody.getBackups() != null && webserviceRespBody.getBackups().getBackupInfo() != null) {
            for (BackupInfo backupInfo : webserviceRespBody.getBackups().getBackupInfo()) {
                VMBakInfo vmBakInfo = new VMBakInfo();
                vmBakInfo.setVmBakInternalId(backupInfo.getVMBackupInternalID());
                vmBakInfo.setStoreSize(backupInfo.getStoreSize());
                if (backupInfo.getGenerationTime() != null) {
                    vmBakInfo.setGenerationTime(DateParse.generateDateFromLongString(backupInfo.getGenerationTime()));
                }
                resp.getBackupList().add(vmBakInfo);
            }
        }

    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPVMBakListQueryResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVMBakListQueryResp resp = new RPPVMBakListQueryResp();
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
