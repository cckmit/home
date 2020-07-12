/*******************************************************************************
 * @(#)VMCreateImpl.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMModify;
import com.neusoft.mid.cloong.host.pm.core.PMModifyReq;
import com.neusoft.mid.cloong.host.pm.core.PMModifyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMModifyResp;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.NetInfo;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建物理机接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-27 上午10:43:34
 */
public class PMModifyImpl implements PMModify, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PMModifyImpl.class);

    /**
     * http发送实体对象
     */
    private HttpSyncSendHelper senderEntry;

    /**
     * 接收超时时间
     */
    private int receiveTimeout = 5000;

    /**
     * 协议超时时间
     */
    private int httpTimeOut = 50;

    /**
     * 资源池代理系统URL
     */
    private String url;

    /*
     * 申请创建物理机订单
     * @see com.neusoft.mid.cloong.web.page.vm.VMOperator#createVM(com.neusoft.mid
     * .cloong.web.page.vm .CreateVMReq)
     */
    @Override
    public PMModifyResp modifyPM(PMModifyReq modifyPMReq) {

        // TODO：判断审批开关。默认为自动审批

        if (logger.isDebugEnable()) {
            logger.debug("修改物理机信息接口开始组装");
        }
        RuntimeContext reqCxt = new RuntimeContext();
        RPPPMModifyReq req = new RPPPMModifyReq();
        req.setResourcePoolId(modifyPMReq.getResourcePoolId());
        req.setResourcePoolPartId(modifyPMReq.getResourcePoolPartId());
        req.setPmId(modifyPMReq.getPmId());

        // 将门户中物理机网卡列表转换为资源池代理中网卡列表
        // 门户网卡列表
        List<NetInfo> netList = modifyPMReq.getNetList();
        if (null != netList && netList.size() > 0) {
            // 资源池代理网卡列表
            List<EthInfo> ehtList = new ArrayList<EthInfo>();
            for (NetInfo netInfo : netList) {
                EthInfo ethInfo = new EthInfo();
                ethInfo.setDefaultGateWay(netInfo.getGateway());
                ethInfo.setIp(netInfo.getIp());
                ethInfo.setName(netInfo.getEth());
                ethInfo.setPurpose(PMEthPurpose.Business);
                ethInfo.setVlanId(netInfo.getVlanId());
                ethInfo.setSubNetMask(netInfo.getSubNetMask());
                ehtList.add(ethInfo);
            }
            req.setEthList(ehtList);
        }

        req.setSerialNum(modifyPMReq.getSerialNum());
        reqCxt.setAttribute(RPPPMModifyReq.REQ_BODY, req);

        if (logger.isDebugEnable()) {
            logger.debug("修改物理机信息接口组装成功");
        }
        HttpSyncRespData respCxt = null;
        try {
            if (logger.isDebugEnable()) {
                logger.debug("修改物理机信息接口准备发送");
                logger.debug("url:" + url);
            }
            respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败", e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }
        if (logger.isDebugEnable()) {
            logger.debug("修改物理机信息接口发送成功");
        }
        return assembleResp(respCxt);
    }

    private PMModifyResp assembleResp(HttpSyncRespData resp) {
        RPPPMModifyResp PMModifyResp = (RPPPMModifyResp) resp.getRuntimeContext().getAttribute(
                RPPPMModifyResp.RESP_BODY);
        PMModifyResp vmResp = new PMModifyResp();
        vmResp.setResultCode(PMModifyResp.getResultCode());
        vmResp.setResultMessage(PMModifyResp.getResultMessage());
        return vmResp;
    }

    private PMModifyResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        PMModifyResp vmResp = new PMModifyResp();
        vmResp.setResultCode(code.toString());
        vmResp.setResultMessage(errorMessage);
        return vmResp;
    }

    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }

    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    public int getReceiveTimeout() {
        return receiveTimeout;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public int getHttpTimeOut() {
        return httpTimeOut;
    }

    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
