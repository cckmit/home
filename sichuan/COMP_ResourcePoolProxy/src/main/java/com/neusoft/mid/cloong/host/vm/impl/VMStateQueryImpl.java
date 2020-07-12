/*******************************************************************************
 * @(#)VMStateQueryImpl.java 2013-2-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.VMStateQuery;
import com.neusoft.mid.cloong.host.vm.VMStateQueryReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMStatus;
import com.neusoft.mid.cloong.rpws.private1072.vm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vm.QueryVMStatusReq;
import com.neusoft.mid.cloong.rpws.private1072.vm.QueryVMStatusResp;
import com.neusoft.mid.cloong.rpws.private1072.vm.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaIPInfoQry;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送虚拟机状态查询请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-22 上午11:12:55
 */
public class VMStateQueryImpl implements VMStateQuery {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<VMServicePort> common;

    private static LogService logger = LogService.getLogger(VMStateQueryImpl.class);

    private long timeout;

    @Override
    public RPPVMStatusQueryResp queryVMState(VMStateQueryReq req) {
        // 打开发送客户端
        VMServicePort client = common.openClient(req, timeout, VMServicePort.class);

        // 　生成请求报文体
        QueryVMStatusReq webserviceReq = new QueryVMStatusReq();
        webserviceReq.setVMID(req.getVmId());

        // 生成请求报文头
        Authorization authInfo = new Authorization();
        authInfo.setTimestamp(req.getTimestamp());
        authInfo.setTransactionID(req.getTransactionID());
        authInfo.setZoneID(req.getResourcePoolPartId());

        // 声明应答报文头
        Holder<Response> respHeader = new Holder<Response>();

        QueryVMStatusResp webserviceResp = null;
        String resultCode = null;
        try {
            webserviceResp = client.queryVMStatus(webserviceReq, authInfo, respHeader);
            resultCode = respHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送虚拟机编码为[" + req.getVmId()
                        + "]的虚拟机状态查询请求失败，资源池认证失败，本次操作流水号为：" + req.getSerialNum(), e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机编码为[" + req.getVmId()
                    + "]的虚拟机状态查询请求失败，资源池系统网络异常或XML解析异常，本次操作流水号为：" + req.getSerialNum(), e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        // 组装响应
        RPPVMStatusQueryResp resp = genRPPResp(webserviceResp, resultCode);

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送虚拟机编码为[" + req.getVmId() + "]的虚拟机状态请求成功，获取的响应码为[").append(resp.getResultCode())
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
    RPPVMStatusQueryResp genRPPResp(QueryVMStatusResp webserviceResp, String resultCode) {
        RPPVMStatusQueryResp resp = new RPPVMStatusQueryResp();
        resp.setResultCode(resultCode);
        resp.setResultMessage(webserviceResp.getFaultString());

        resp.setFaultstring(webserviceResp.getFaultString());
        resp.setOperationURL(webserviceResp.getOperationURL());
        resp.setPassWord(webserviceResp.getPassWord());
        resp.setUserName(webserviceResp.getUserName());
        resp.setVmId(webserviceResp.getVMID());
        resp.setVmName(webserviceResp.getVMName());

        if (webserviceResp.getVEthAdaIPInfo() != null
                && webserviceResp.getVEthAdaIPInfo().getVEthAdaIPInfoQry() != null) {
            for (VEthAdaIPInfoQry webserviceEthInfo : webserviceResp.getVEthAdaIPInfo().getVEthAdaIPInfoQry()) {
                EthInfo ethInfo = new EthInfo();
                ethInfo.setIp(webserviceEthInfo.getIP());
                ethInfo.setName(webserviceEthInfo.getEth());
                ethInfo.setDefaultGateWay(webserviceEthInfo.getIPDefaultGateway());
                ethInfo.setSubNetMask(webserviceEthInfo.getIPSubnetmask());
                ethInfo.setVlanId(webserviceEthInfo.getVlanID());

                if (ETH_PURPOSE_BUSINESS.equals(webserviceEthInfo.getPurPose())) {
                    ethInfo.setPurpose(VMEthPurpose.Business);
                } else if (ETH_PURPOSE_IPSTORAGE.equals(webserviceEthInfo.getPurPose())) {
                    ethInfo.setPurpose(VMEthPurpose.IPStorage);
                } else {
                    ethInfo.setPurpose(VMEthPurpose.Other);
                }
            }
        }

        if (!resultCode.equals(InterfaceResultCode.SUCCESS.getResultCode()) || webserviceResp.getStatus() == null) {
            resp.setVmStatus(VMStatus.STATUSERROR);
        } else {
            switch (webserviceResp.getStatus()) {
            case VM_STATUS_UNAVAILABLE:
                resp.setVmStatus(VMStatus.STATUSERROR);
                break;
            case VM_STATUS_POWER:
                resp.setVmStatus(VMStatus.RUNNING);
                break;
            case VM_STATUS_RUNNING:
                resp.setVmStatus(VMStatus.RUNNING);
                break;
            case VM_STATUS_POWEROFF:
                resp.setVmStatus(VMStatus.STOP);
                break;
            case VM_STATUS_PAUSE:
                resp.setVmStatus(VMStatus.PAUSE);
                break;
            case VM_STATUS_PROCESSING:
                resp.setVmStatus(VMStatus.PROCESSING);
                break;
            case VM_STATUS_ALLOCATION_FAIL:
                resp.setVmStatus(VMStatus.STATUSERROR);
                break;
            default:
                resp.setVmStatus(VMStatus.STATUSERROR);
                break;
            }
        }

        return resp;
    }

    private RPPVMStatusQueryResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVMStatusQueryResp resp = new RPPVMStatusQueryResp();
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
     * 虚拟机状态——不可用
     */
    public static final int VM_STATUS_UNAVAILABLE = 0;

    /**
     * 虚拟机状态——已加电
     */
    public static final int VM_STATUS_POWER = 1;

    /**
     * 虚拟机状态——运行
     */
    public static final int VM_STATUS_RUNNING = 2;

    /**
     * 虚拟机状态——关机
     */
    public static final int VM_STATUS_POWEROFF = 3;

    /**
     * 虚拟机状态——挂起
     */
    public static final int VM_STATUS_PAUSE = 4;

    /**
     * 虚拟机状态——处理中
     */
    public static final int VM_STATUS_PROCESSING = 5;

    /**
     * 虚拟机状态——处理中
     */
    public static final int VM_STATUS_ALLOCATION_FAIL = 6;

    public void setCommon(WebServiceClientFactory<VMServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
