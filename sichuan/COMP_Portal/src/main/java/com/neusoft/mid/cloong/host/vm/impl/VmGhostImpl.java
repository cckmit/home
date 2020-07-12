/*******************************************************************************
 * @(#)VmGhostImpl.java 2014-5-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMCreate;
import com.neusoft.mid.cloong.host.vm.core.VMCreateReq;
import com.neusoft.mid.cloong.host.vm.core.VMCreateResp;
import com.neusoft.mid.cloong.host.vm.core.VMCustomCreateReq;
import com.neusoft.mid.cloong.host.vm.core.VMCustomCreateResp;
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
 * @version $Revision 1.0 $ 2014-5-8 上午9:31:43
 */
public class VmGhostImpl implements VMCreate,Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(VmGhostImpl.class);

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
     * 
     * assembleRuntimeContext 组装请求消息
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
    @SuppressWarnings("unchecked")
    private VMCreateResp assembleResp(HttpSyncRespData resp) {
        VMCreateResp vmResp = new VMCreateResp();
        vmResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
        vmResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        ArrayList<Map<String,String>> vmInfo = (ArrayList<Map<String,String>>)resp.getRuntimeContext().getAttribute("VM_INFO");
        vmResp.setVmInfo(vmInfo);
        return vmResp;
    }

    /**
     * 
     * assmbleErrorResp 组装响应错误消息
     * @param code 错误码
     * @param errorMessage  错误消息
     * @return VMCreateResp
     */
    private VMCreateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        VMCreateResp vmResp = new VMCreateResp();
        vmResp.setResultCode(code.toString());
        vmResp.setResultMessage(errorMessage);
        return vmResp;
    }

    /**
     * 获取senderEntry字段数据
     * @return Returns the senderEntry.
     */
    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }

    /**
     * 设置senderEntry字段数据
     * @param senderEntry The senderEntry to set.
     */
    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    /**
     * 获取receiveTimeout字段数据
     * @return Returns the receiveTimeout.
     */
    public int getReceiveTimeout() {
        return receiveTimeout;
    }

    /**
     * 设置receiveTimeout字段数据
     * @param receiveTimeout The receiveTimeout to set.
     */
    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    /**
     * 获取httpTimeOut字段数据
     * @return Returns the httpTimeOut.
     */
    public int getHttpTimeOut() {
        return httpTimeOut;
    }

    /**
     * 设置httpTimeOut字段数据
     * @param httpTimeOut The httpTimeOut to set.
     */
    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }

    /**
     * 获取url字段数据
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url字段数据
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /** 
     * createCustomVM TODO 这里请补充overriding方法的简要说明
     * @param req
     * @return TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     * @see com.neusoft.mid.cloong.host.vm.core.VMCreate#createCustomVM(com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq)
     */
    @Override
    public RPPVMCreateResp createCustomVM(RPPVMCreateReq req) {
        // TODO Auto-generated method stub
        return null;
    }
}
