package com.neusoft.mid.cloong.web.page.snapshots.core;

import java.io.IOException;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPQuerySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPQuerySnapshotResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotRecoveryImpl implements SnapshotRecovery{
	private static LogService logger = LogService.getLogger(SnapshotCreateImpl.class);
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
    public RPPRecoverySnapshotResp recovery(RPPRecoverySnapshotReq snapshotRecoveryReq) {
    	RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, snapshotRecoveryReq);

        HttpSyncRespData resp = null;
        try {
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

        return (RPPRecoverySnapshotResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);		

    }
  private RPPRecoverySnapshotResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
	  RPPRecoverySnapshotResp snapshotRecoveryResp = new RPPRecoverySnapshotResp();
	  snapshotRecoveryResp.setResultCode(code.toString());
	  snapshotRecoveryResp.setResultMessage(errorMessage);
        return snapshotRecoveryResp;
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
