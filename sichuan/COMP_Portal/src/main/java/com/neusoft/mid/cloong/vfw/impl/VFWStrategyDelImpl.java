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
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPDeleteVirFWStrategyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPDeleteVirFWStrategyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPDelVirfwSrategyOperationResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPDelVirfwStrategyOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceReq;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceResp;
import com.neusoft.mid.cloong.vfw.core.DeleteVirFWStrategyReq;
import com.neusoft.mid.cloong.vfw.core.DeleteVirFWStrategyResp;
import com.neusoft.mid.cloong.vfw.core.VFWDel;
import com.neusoft.mid.cloong.vfw.core.VFWStrategyDel;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * ???????????????????????????????????????
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-13 ??????04:20:13
 */
public class VFWStrategyDelImpl implements VFWStrategyDel , Serializable {

    /**
     * ?????????
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(VFWStrategyDelImpl.class);

    /**
     * http??????????????????
     */
    private HttpSyncSendHelper senderEntry;

    /**
     * ??????????????????
     */
    private int receiveTimeout = 5000;

    /**
     * ??????????????????
     */
    private int httpTimeOut = 50;

    /**
     * ?????????????????????URL
     */
    private String url;

    @Override
    public DeleteVirFWStrategyResp delVFWStrategy(DeleteVirFWStrategyReq vfwStrategyReq) {
    	RuntimeContext reqCxt = new RuntimeContext();
    	RPPDelVirfwStrategyOperationRequst req = new RPPDelVirfwStrategyOperationRequst();
    	req.setResourcePoolId(vfwStrategyReq.getResourcePoolId());
    	req.setResourcePoolPartId(vfwStrategyReq.getResourcePoolPartId());
    	req.setSerialNum(vfwStrategyReq.getSerialNum());
    	req.setFWStrategyID(vfwStrategyReq.getFwStrategyID());
        reqCxt.setAttribute(RPPDelVirfwStrategyOperationRequst.REQ_BODY, req);
		HttpSyncRespData respCxt = null;
        try {
        	respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "?????????????????????????????????????????????IO????????????????????????????????????"
                    + vfwStrategyReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "?????????????????????????????????????????????IO????????????????????????????????????" + vfwStrategyReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "??????????????????????????????????????????????????????http????????????????????????????????????" + vfwStrategyReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "??????????????????????????????????????????????????????http??????");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "??????????????????????????????????????????????????????????????????????????????????????????????????????"
                    + vfwStrategyReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "????????????????????????????????????????????????????????????????????????");
        }
        return assembleResp(respCxt);
    }

    private DeleteVirFWStrategyResp assembleResp(HttpSyncRespData resp) {
    	RPPDelVirfwSrategyOperationResponse vfwDeleteResp = (RPPDelVirfwSrategyOperationResponse) resp
				.getRuntimeContext().getAttribute(
						RPPDelVirfwSrategyOperationResponse.RESP_BODY);
        DeleteVirFWStrategyResp vfwStrategyResp = new DeleteVirFWStrategyResp();
        vfwStrategyResp.setResultCode(vfwDeleteResp.getResultCode());
        vfwStrategyResp.setResultDesc(vfwDeleteResp.getResultMessage());
        return vfwStrategyResp;
    }

    private DeleteVirFWStrategyResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        DeleteVirFWStrategyResp vfwStrategyResp = new DeleteVirFWStrategyResp();
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
