/*******************************************************************************
 * @(#)VmBakDelImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.vmbak.core.VmBakDel;
import com.neusoft.mid.cloong.vmbak.core.VmBakDeleteReq;
import com.neusoft.mid.cloong.vmbak.core.VmBakDeleteResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除虚拟机备份实现类
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-23 下午02:42:37
 */
public class VmBakDelImpl implements VmBakDel ,Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(VmBakDelImpl.class);

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
    public VmBakDeleteResp delVmBak(VmBakDeleteReq vmBakReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute("VMBAK_ID", vmBakReq.getVmBakId());
        req.setAttribute("RES_POOL_ID", vmBakReq.getResourcePoolId());
        req.setAttribute("RES_POOL_PART_ID", vmBakReq.getResourcePoolPartId());
        req.setAttribute("SERIAL_NUM", vmBakReq.getSerialNum());

        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为："
                    + vmBakReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + vmBakReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + vmBakReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + vmBakReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误");
        }
        if (logger.isDebugEnable()) {
            logger.debug("删除虚拟机备份信息接口发送成功");
            logger.debug("接收资源池响应码："+(String) resp.getRuntimeContext().getAttribute("ResultCode"));
            logger.debug("接收资源池响应信息："+(String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        }
        return assembleResp(resp);
    }

    private VmBakDeleteResp assembleResp(HttpSyncRespData resp) {
        VmBakDeleteResp vmResp = new VmBakDeleteResp();
        vmResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
        vmResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        return vmResp;
    }

    private VmBakDeleteResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        VmBakDeleteResp vmResp = new VmBakDeleteResp();
        vmResp.setResultCode(code.toString());
        vmResp.setResultMessage(errorMessage);
        return vmResp;
    }
    
    /**构造返回消息
     * @return 删除返回消息
     */
//    private VmBakDeleteResp assembleResp() {
//        VmBakDeleteResp vmBakResp = new VmBakDeleteResp();
//        vmBakResp.setResultCode("00000000");
//        vmBakResp.setResultMessage("虚拟机备份删除接口接收成功！");
//        return vmBakResp;
//    }

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
