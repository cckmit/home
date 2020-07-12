/*******************************************************************************
 * @(#)PMCreateImpl.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.pm.core;

import java.io.IOException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
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
public class PMCreateImpl implements PMCreate {

    private static LogService logger = LogService.getLogger(PMCreateImpl.class);

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
    
    public RPPPMCreateResp createCustomPM(RPPPMCreateReq req) {
        try {
            if (logger.isDebugEnable()) {
                logger.debug("创建高定制物理机信息接口开始组装");
            }
            RuntimeContext reqCxt = new RuntimeContext();
            reqCxt.setAttribute(RPPPMCreateReq.REQ_BODY, req);
            HttpSyncRespData respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut,
                    receiveTimeout);
            RPPPMCreateResp createResp = (RPPPMCreateResp) respCxt.getRuntimeContext()
                    .getAttribute(RPPPMCreateResp.RESP_BODY);

            return createResp;
        } catch (IOException e) {
            RPPPMCreateResp resp = new RPPPMCreateResp();
            logger.error(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum(), e);
            resp.setFaultstring("向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum());
            resp.setResultCode(CommonStatusCode.IO_OPTION_ERROR.toString());
            return resp;
        } catch (InvalidProtocolException e) {
            RPPPMCreateResp resp = new RPPPMCreateResp();
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + req.getSerialNum(), e);
            resp.setFaultstring("向资源池代理系统发送请求失败，无效的http协议");
            resp.setResultCode(CommonStatusCode.INVALID_HTTP_PROTOCOL.toString());
            return resp;
        } catch (Exception e) {
            RPPPMCreateResp resp = new RPPPMCreateResp();
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + req.getSerialNum(), e);
            resp.setFaultstring("向资源池代理系统发送请求失败，自服务系统内部错误");
            resp.setResultCode(CommonStatusCode.RUNTIME_EXCEPTION.toString());
            return resp;
        }
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
    
    /** 
     * createVM TODO 这里请补充overriding方法的简要说明
     * @param req
     * @return TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     * @see com.neusoft.mid.cloong.host.vm.core.VMCreate#createVM(com.neusoft.mid.cloong.host.vm.core.VMCreateReq)
     */
    @Override
    public PMCreateResp createPM(PMCreateReq req) {
        // TODO Auto-generated method stub
        return null;
    }

}
