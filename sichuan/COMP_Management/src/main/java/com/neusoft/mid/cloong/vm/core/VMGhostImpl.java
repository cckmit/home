/*******************************************************************************
 * @(#)VMGhostImpl.java 2014-5-7 
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vm.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 克隆虚拟机接口
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-5-7 下午4:45:59
 */
public class VMGhostImpl implements VMCreate {

    private static LogService logger = LogService.getLogger(VMGhostImpl.class);

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
    
    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";
    
    /*
     * 申请克隆虚拟机订单
     * @see
     * com.neusoft.mid.cloong.web.page.vm.VMOperator#createVM(com.neusoft.mid.cloong.web.page.vm
     * .CreateVMReq)
     */
    @Override
    public VMCreateResp createVM(VMCreateReq vmReq) {

        if (logger.isDebugEnable()) {
            logger.debug("克隆虚拟机信息接口开始组装");
        }
        RuntimeContext req = assembleRuntimeContext(vmReq);

        if (logger.isDebugEnable()) {
            logger.debug("克隆虚拟机信息接口组装成功");
        }
        HttpSyncRespData resp = null;
        try {
            if (logger.isDebugEnable()) {
                logger.debug("克隆虚拟机信息接口准备发送");
                logger.debug("url:" + url);
            }

            resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);
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
            logger.debug("克隆虚拟机信息接口发送成功");
        }
        return assembleResp(resp);
    }

    /**
     * assembleRuntimeContext 组装RuntimeContext请求消息
     * @param vmReq VMCreateReq
     * @return RuntimeContext
     */
    private RuntimeContext assembleRuntimeContext(VMCreateReq vmReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute("VM_BACKUP_ID", vmReq.getVmBackupId());
        req.setAttribute("NUM", vmReq.getNum());
        req.setAttribute("SUBNETWORK", vmReq.getSubNetwork());
        req.setAttribute("RES_POOL_ID", vmReq.getResourcePoolId());
        req.setAttribute("RES_POOL_PART_ID", vmReq.getResourcePoolPartId());
        req.setAttribute("VM_NAME_INFO", vmReq.getVmNameInfo());
        return req;
    }

    /**
     * assembleResp 组装响应消息
     * @param resp HttpSyncRespData
     * @return VMCreateResp
     */
    private VMCreateResp assembleResp(HttpSyncRespData resp) {
        VMCreateResp vmResp = new VMCreateResp();
        if(!String.valueOf(resp.getRuntimeContext().getAttribute("ResultCode")).equals(SUCCEESS_CODE)){
            vmResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
            vmResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        }else{
            vmResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
            vmResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
            ArrayList<Map<String,String>> vmInfo = (ArrayList<Map<String,String>>)resp.getRuntimeContext().getAttribute("VM_INFO");
            vmResp.setVmInfo(vmInfo);
        }
        return vmResp;
    }

    /**
     * assmbleErrorResp 组装错误消息
     * @param code 错误码
     * @param errorMessage 错误信息
     * @return VMCreateResp 
     */
    private VMCreateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        VMCreateResp vmResp = new VMCreateResp();
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

    /*public VMCustomCreateResp createCustomVM(VMCustomCreateReq req) {
        // TODO Auto-generated method stub
        return null;
    }*/

	/* (non-Javadoc)
	 * @see com.neusoft.mid.cloong.vm.core.VMCreate#createCustomVM(com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq)
	 */
	@Override
	public RPPVMCreateResp createCustomVM(RPPVMCreateReq req) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
