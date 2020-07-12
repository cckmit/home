/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vfw.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.fileStore.core.DeleteFileStorageReq;
import com.neusoft.mid.cloong.fileStore.core.DeleteFileStorageResp;
import com.neusoft.mid.cloong.fileStore.core.FSDel;
import com.neusoft.mid.cloong.host.vm.core.VMDel;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteReq;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPAddVirFWStrategyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPAddVirFWStrategyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPDeleteVirFWStrategyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPDeleteVirFWStrategyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPAddVirfwSrategyOperationResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPAddVirfwStrategyOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;
import com.neusoft.mid.cloong.vfw.core.AddVirFWStrategyReq;
import com.neusoft.mid.cloong.vfw.core.AddVirFWStrategyResp;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceReq;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceResp;
import com.neusoft.mid.cloong.vfw.core.DeleteVirFWStrategyReq;
import com.neusoft.mid.cloong.vfw.core.DeleteVirFWStrategyResp;
import com.neusoft.mid.cloong.vfw.core.VFWDel;
import com.neusoft.mid.cloong.vfw.core.VFWStrategyAdd;
import com.neusoft.mid.cloong.vfw.core.VFWStrategyDel;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * 虚拟防火墙策略请添加实现类
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-13 下午04:20:13
 */
public class VFWStrategyAddImpl implements VFWStrategyAdd , Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(VFWStrategyAddImpl.class);

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

    @Override
    public AddVirFWStrategyResp addVFWStrategy(AddVirFWStrategyReq vfwStrategyReq) {
    	RuntimeContext reqCxt = new RuntimeContext();
    	RPPAddVirfwStrategyOperationRequst req = new RPPAddVirfwStrategyOperationRequst();
    	req.setResourcePoolId(vfwStrategyReq.getResourcePoolId());
    	req.setResourcePoolPartId(vfwStrategyReq.getResourcePoolPartId());
    	req.setSerialNum(vfwStrategyReq.getSerialNum());
    	req.setFWID(vfwStrategyReq.getFwId());
    	req.setProtocol(vfwStrategyReq.getProtocol());
    	req.setSourceIP(vfwStrategyReq.getSourceIp());
    	req.setSourcePort(vfwStrategyReq.getSourcePort());
    	req.setDestinationIP(vfwStrategyReq.getDestinationIp());
    	req.setDestinationPort(vfwStrategyReq.getDestinationPort());
    	req.setActType(vfwStrategyReq.getActType());
    	
        reqCxt.setAttribute(RPPAddVirfwStrategyOperationRequst.REQ_BODY, req);
		HttpSyncRespData respCxt = null;
        try {
        	respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为："
                    + vfwStrategyReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + vfwStrategyReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + vfwStrategyReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + vfwStrategyReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误");
        }
        return assembleResp(respCxt);
    }

    private AddVirFWStrategyResp assembleResp(HttpSyncRespData resp) {
    	RPPAddVirfwSrategyOperationResponse vfwDeleteResp = (RPPAddVirfwSrategyOperationResponse) resp
				.getRuntimeContext().getAttribute(
						RPPAddVirfwSrategyOperationResponse.RESP_BODY);
        AddVirFWStrategyResp vfwStrategyResp = new AddVirFWStrategyResp();
        vfwStrategyResp.setResultCode(vfwDeleteResp.getResultCode());
        vfwStrategyResp.setResultDesc(vfwDeleteResp.getResultMessage());
        return vfwStrategyResp;
    }

    private AddVirFWStrategyResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        AddVirFWStrategyResp vfwStrategyResp = new AddVirFWStrategyResp();
        vfwStrategyResp.setResultCode(code.toString());
        vfwStrategyResp.setResultDesc(errorMessage);
        return vfwStrategyResp;
    }

    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
