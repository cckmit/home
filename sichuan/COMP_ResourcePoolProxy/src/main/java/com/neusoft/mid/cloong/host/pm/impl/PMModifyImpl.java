package com.neusoft.mid.cloong.host.pm.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.pm.PMModify;
import com.neusoft.mid.cloong.host.pm.PMModifyReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMModifyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMStatus;
import com.neusoft.mid.cloong.rpws.private1072.pm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.pm.ModifySRVReq;
//import com.neusoft.mid.cloong.rpws.private1072.pm.ModifySRVResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.pm.ModifySRVResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.Response;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaIPInfoModifySet;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaIPInfoModifySetList;
//import com.neusoft.mid.cloong.rpws.private1072.pm.PMConfigInfo;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送修改物理机操作请求
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.1 $ 2014-5-13 下午02:33:06
 */
public class PMModifyImpl implements PMModify {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<SRVServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    /**
     * ibatisDAO
     */
    private IbatisDAO ibatisDAO;

    /**
     * logger
     */
    private static LogService logger = LogService.getLogger(PMModifyImpl.class);

    /**
     * modifyPM 修改物理机
     * @param req
     * @return PMModifyResp
     */
    public RPPPMModifyResp modifyPM(PMModifyReq req) {
        RPPPMModifyResp resp = new RPPPMModifyResp();
        try {
            // 打开发送客户端
            SRVServicePort client = common.openClient(req, timeout, SRVServicePort.class); // 和快照用的是融合支撑云的接口

            // 生成请求报文体
            ModifySRVReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            ModifySRVResp webserviceRespBody = client.modifySRV(webserviceReqBody, webserviceReqHeader,
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
    Authorization genWebserviceReqHeader(PMModifyReq req) {
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
    ModifySRVReq genWebserviceReqBody(PMModifyReq req) {
        ModifySRVReq webserviceReq = new ModifySRVReq();
        webserviceReq.setSRVID(req.getPmId());
        webserviceReq.setSRVName(req.getPmName());
        EthAdaIPInfoModifySetList ethAdaIPInfoModifySetList = new EthAdaIPInfoModifySetList();
        webserviceReq.setEthAdaIPInfoModify(ethAdaIPInfoModifySetList);
        for (EthInfo ethInfo : req.getEthList()) {
            EthAdaIPInfoModifySet ethAdaIPInfoModifySet = new EthAdaIPInfoModifySet();
            ethAdaIPInfoModifySet.setEth(ethInfo.getName());
            ethAdaIPInfoModifySet.setIP(ethInfo.getIp());
            ethAdaIPInfoModifySet.setIPDefaultGateway(ethInfo.getDefaultGateWay());
            ethAdaIPInfoModifySet.setIPSubnetmask(ethInfo.getSubNetMask());
            if (ethInfo.getPurpose() != null) {
            	ethAdaIPInfoModifySet.setPurPose(ethInfo.getPurpose().getValue());
            }
            ethAdaIPInfoModifySet.setVlanID(ethInfo.getVlanId());
            ethAdaIPInfoModifySetList.getEthAdaIPInfoModifySet().add(ethAdaIPInfoModifySet);
        }
        return webserviceReq;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyPMResp
     */
    private void assembleResp(RPPPMModifyResp resp, ModifySRVResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);
    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPPMModifyResp assmbleErrorResp(InterfaceResultCode code) {
        RPPPMModifyResp resp = new RPPPMModifyResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }
    
    /**
     * 设置common字段数据
     * @param common The common to set.
     */
    public void setCommon(WebServiceClientFactory<SRVServicePort> common) {
        this.common = common;
    }

    /**
     * 设置timeout字段数据
     * @param timeout The timeout to set.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
