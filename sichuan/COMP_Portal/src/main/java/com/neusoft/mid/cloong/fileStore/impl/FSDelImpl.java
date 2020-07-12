/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.fileStore.impl;

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
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * 文件存储删除实现类
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-13 下午04:20:13
 */
public class FSDelImpl implements FSDel , Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(FSDelImpl.class);

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
    public DeleteFileStorageResp delFS(DeleteFileStorageReq fsReq) {
    	RuntimeContext reqCxt = new RuntimeContext();
    	RPPFileStorageDeleteReq req = new RPPFileStorageDeleteReq();
    	req.setResourcePoolId(fsReq.getResourcePoolId());
    	req.setResourcePoolPartId(fsReq.getResourcePoolPartId());
    	req.setSerialNum(fsReq.getSerialNum());
    	req.setFileStorageID(fsReq.getFileStorageID());
        reqCxt.setAttribute(RPPFileStorageDeleteReq.REQ_BODY, req);
		HttpSyncRespData respCxt = null;
        try {
        	respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为："
                    + fsReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + fsReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + fsReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + fsReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误");
        }
        return assembleResp(respCxt);
    }

    private DeleteFileStorageResp assembleResp(HttpSyncRespData resp) {
        RPPFileStorageDeleteResponse fsDeleteResp = (RPPFileStorageDeleteResponse) resp
				.getRuntimeContext().getAttribute(
				        RPPFileStorageDeleteResponse.RESP_BODY);
    	DeleteFileStorageResp fsResp = new DeleteFileStorageResp();
    	fsResp.setResultCode(fsDeleteResp.getResultCode());
    	fsResp.setResultMessage(fsDeleteResp.getResultMessage());
    	fsResp.setFaultString(fsDeleteResp.getFaultString());
        return fsResp;
    }

    private DeleteFileStorageResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        DeleteFileStorageResp fsResp = new DeleteFileStorageResp();
        fsResp.setResultCode(code.toString());
        fsResp.setResultMessage(errorMessage);
        return fsResp;
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
