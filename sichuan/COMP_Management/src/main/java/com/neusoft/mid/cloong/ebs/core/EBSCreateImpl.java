/*******************************************************************************
 * @(#)EBSCreateImpl.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core;

import java.io.IOException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**创建虚拟硬盘接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-27 上午10:43:34
 */
public class EBSCreateImpl implements EBSCreate {
    
    /**
     * 日志文件
     */
    private static LogService logger = LogService.getLogger(EBSCreateImpl.class);

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
    
   /****
    * 
    * createEBS 申请创建虚拟硬盘订单
    * @param ebsReq ebs请求消息
    * @return TODO ebs响应消息
    * @see com.neusoft.mid.cloong.ebs.core.EBSCreate#createEBS(com.neusoft.mid.cloong.ebs.core.EBSCreateReq)
    */
    @Override
    public RPPEBSCreateResp createEBS(RPPEBSCreateReq ebsReq) {

        //TODO：判断审批开关。默认为自动审批

        if (logger.isDebugEnable()) {
            logger.debug("创建虚拟硬盘信息接口开始组装");
        }
        RuntimeContext req = assembleRuntimeContext(ebsReq);

        if (logger.isDebugEnable()) {
            logger.debug("创建虚拟硬盘信息接口组装成功");
        }
        HttpSyncRespData resp = null;
        try {
            if (logger.isDebugEnable()) {
                logger.debug("创建虚拟硬盘信息接口准备发送");
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
            logger.debug("创建虚拟硬盘信息接口发送成功");
        }
        return assembleResp(resp);
    }
    
    /**
     * 
     * assembleRuntimeContext 组装请求消息
     * @param ebsReq ebsReq
     * @return RuntimeContext
     */
    private RuntimeContext assembleRuntimeContext(RPPEBSCreateReq ebsReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, ebsReq);
        return req;
    }
    
    /**
     * 
     * assembleResp 组装响应消息
     * @param resp resp
     * @return RPPEBSCreateResp
     */
    private RPPEBSCreateResp assembleResp(HttpSyncRespData resp) {
        RPPEBSCreateResp ebsResp = new RPPEBSCreateResp();
        ebsResp = (RPPEBSCreateResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
        return ebsResp;
    }
    
    /**
     * 
     * assmbleErrorResp 组装错误消息
     * @param code code
     * @param errorMessage errorMessage
     * @return 
     */
    private RPPEBSCreateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        RPPEBSCreateResp ebsResp = new RPPEBSCreateResp();
        ebsResp.setResultCode(code.toString());
        ebsResp.setResultMessage(errorMessage);
        return ebsResp;
    }
    
    /**
     * 
     * getSenderEntry getSenderEntry
     * @return HttpSyncSendHelper
     */
    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }
    
    /**
     * 
     * setSenderEntry setSenderEntry
     * @param senderEntry senderEntry
     */
    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }
    
    /**
     * 
     * getReceiveTimeout getReceiveTimeout
     * @return receiveTimeout
     */
    public int getReceiveTimeout() {
        return receiveTimeout;
    }
    
    /**
     * 
     * setReceiveTimeout TODO setReceiveTimeout
     * @param receiveTimeout TODO receiveTimeout
     */
    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }
    
    /**
     * 
     * getHttpTimeOut TODO getHttpTimeOut
     * @return TODO httpTimeOut
     */
    public int getHttpTimeOut() {
        return httpTimeOut;
    }
    
    /**
     * 
     * setHttpTimeOut TODO setHttpTimeOut
     * @param httpTimeOut TODO httpTimeOut
     */
    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }
    
    /**
     * 
     * getUrl TODO getUrl
     * @return TODO url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * 
     * setUrl TODO setUrl
     * @param url TODO url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
}
