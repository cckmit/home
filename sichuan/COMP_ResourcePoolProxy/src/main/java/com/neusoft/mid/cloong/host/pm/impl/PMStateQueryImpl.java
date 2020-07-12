/*******************************************************************************
 * @(#)VMStateQueryImpl.java 2013-2-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.pm.PMStateQuery;
import com.neusoft.mid.cloong.host.pm.PMStateQueryReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMStatus;
import com.neusoft.mid.cloong.rpws.private1072.pm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.pm.QuerySRVStatusReq;
import com.neusoft.mid.cloong.rpws.private1072.pm.QuerySRVStatusResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.Response;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaIPInfoQry;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送物理机状态查询请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-22 上午11:12:55
 */
public class PMStateQueryImpl implements PMStateQuery {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<SRVServicePort> common;

    private static LogService logger = LogService.getLogger(PMStateQueryImpl.class);

    private long timeout;

    @Override
    public RPPPMStatusQueryResp queryPMState(PMStateQueryReq req) {
        // 打开发送客户端
    	SRVServicePort client = common.openClient(req, timeout, SRVServicePort.class);

        // 　生成请求报文体
        QuerySRVStatusReq webserviceReq = new QuerySRVStatusReq();
        webserviceReq.setSRVID(req.getPmId());

        // 生成请求报文头
        Authorization authInfo = new Authorization();
        authInfo.setTimestamp(req.getTimestamp());
        authInfo.setTransactionID(req.getTransactionID());
        authInfo.setZoneID(req.getResourcePoolPartId());

        // 声明应答报文头
        Holder<Response> respHeader = new Holder<Response>();

        QuerySRVStatusResp webserviceResp = null;
        String resultCode = null;
        try {
            webserviceResp = client.querySRVStatus(webserviceReq, authInfo, respHeader);
            resultCode = respHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送物理机编码为[" + req.getPmId()
                        + "]的物理机状态查询请求失败，资源池认证失败，本次操作流水号为：" + req.getSerialNum(), e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送物理机编码为[" + req.getPmId()
                    + "]的物理机状态查询请求失败，资源池系统网络异常或XML解析异常，本次操作流水号为：" + req.getSerialNum(), e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        // 组装响应
        RPPPMStatusQueryResp resp = genRPPResp(webserviceResp, resultCode);

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送物理机编码为[" + req.getPmId() + "]的物理机状态请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]本次操作流水号为：").append(req.getSerialNum());
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 生成资源池代理向前台返回的应答Bean
     * @param webserviceResp
     * @param resultCode
     * @return 资源池代理向前台返回的应答Bean
     */
    RPPPMStatusQueryResp genRPPResp(QuerySRVStatusResp webserviceResp, String resultCode) {
    	RPPPMStatusQueryResp resp = new RPPPMStatusQueryResp();
        resp.setResultCode(resultCode);
        resp.setResultMessage(webserviceResp.getFaultString());

        resp.setFaultstring(webserviceResp.getFaultString());
        resp.setOperationIp(webserviceResp.getOperationIP());
        resp.setPassWord(webserviceResp.getPassWord());
        resp.setUserName(webserviceResp.getUserName());
        
        resp.setPmName(webserviceResp.getSRVName());

        if (webserviceResp.getEthAdaIPInfo() != null
                && webserviceResp.getEthAdaIPInfo().getEthAdaIPInfoQry() != null) {
            for (EthAdaIPInfoQry webserviceEthInfo : webserviceResp.getEthAdaIPInfo().getEthAdaIPInfoQry()) {
                EthInfo ethInfo = new EthInfo();
                ethInfo.setIp(webserviceEthInfo.getIP());
                ethInfo.setName(webserviceEthInfo.getEth());
                ethInfo.setDefaultGateWay(webserviceEthInfo.getIPDefaultGateway());
                ethInfo.setSubNetMask(webserviceEthInfo.getIPSubnetmask());
                ethInfo.setVlanId(webserviceEthInfo.getVlanID());

                if (ETH_PURPOSE_BUSINESS.equals(webserviceEthInfo.getPurPose())) {
                    ethInfo.setPurpose(PMEthPurpose.Business);
                } else if (ETH_PURPOSE_IPSTORAGE.equals(webserviceEthInfo.getPurPose())) {
                    ethInfo.setPurpose(PMEthPurpose.IPStorage);
                } else {
                    ethInfo.setPurpose(PMEthPurpose.Other);
                }
            }
        }

        if (!resultCode.equals(InterfaceResultCode.SUCCESS.getResultCode()) || webserviceResp.getStatus() == null) {
            resp.setPmStatus(PMStatus.STATUSERROR);
        } else {
            switch (webserviceResp.getStatus()) {
            case PM_STATUS_UNAVAILABLE:
                resp.setPmStatus(PMStatus.STATUSERROR);
                break;
            case PM_STATUS_POWER:
                resp.setPmStatus(PMStatus.RUNNING);
                break;
            case PM_STATUS_RUNNING:
                resp.setPmStatus(PMStatus.RUNNING);
                break;
            case PM_STATUS_POWEROFF:
                resp.setPmStatus(PMStatus.STOP);
                break;
            case PM_STATUS_SLEEPING:
                resp.setPmStatus(PMStatus.PAUSE);
                break;
            case PM_STATUS_PROCESSING:
                resp.setPmStatus(PMStatus.PROCESSING);
                break;
            default:
                resp.setPmStatus(PMStatus.STATUSERROR);
                break;
            }
        }

        return resp;
    }

    private RPPPMStatusQueryResp assmbleErrorResp(InterfaceResultCode code) {
        RPPPMStatusQueryResp resp = new RPPPMStatusQueryResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    /**
     * 网卡用途常量——业务平面
     */
    public static final String ETH_PURPOSE_BUSINESS = "2";

    /**
     * 网卡用途常量——IP存储平面
     */
    public static final String ETH_PURPOSE_IPSTORAGE = "3";

    /**
     * 物理机状态——不可用
     */
    public static final int PM_STATUS_UNAVAILABLE = 0;

    /**
     * 物理机状态——已加电
     */
    public static final int PM_STATUS_POWER = 1;

    /**
     * 物理机状态——运行
     */
    public static final int PM_STATUS_RUNNING = 2;

    /**
     * 物理机状态——关机
     */
    public static final int PM_STATUS_POWEROFF = 3;

    /**
     * 物理机状态——休眠（资源池不支持此状态）
     */
    public static final int PM_STATUS_SLEEPING = 4;

    /**
     * 物理机状态——处理中
     */
    public static final int PM_STATUS_PROCESSING = 5;

//    /**
//     * 物理机状态——分配失败
//     */
//    public static final int VM_STATUS_ALLOCATION_FAIL = 6;

    public void setCommon(WebServiceClientFactory<SRVServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
