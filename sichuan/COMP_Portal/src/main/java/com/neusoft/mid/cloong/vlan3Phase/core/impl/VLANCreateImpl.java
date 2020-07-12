package com.neusoft.mid.cloong.vlan3Phase.core.impl;

import java.io.IOException;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;
import com.neusoft.mid.cloong.vlan3Phase.core.VLANCreate;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请vlan接口实现类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月3日 下午2:51:46
 */
public class VLANCreateImpl implements VLANCreate {

    private static LogService logger = LogService.getLogger(VLANCreateImpl.class);

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
     * 申请vlan接口实现
     * @param vlan参数对象
     * @return vlan相应对象
     * @see com.neusoft.mid.cloong.vlan3Phase.core.VLANCreate#create(com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq)
     */
    @Override
    public RPPVlanApplyResp create(RPPVlanApplyReq vlanReq) {

        RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, vlanReq);

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

        return (RPPVlanApplyResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
    }

    private RPPVlanApplyResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        RPPVlanApplyResp vlanResp = new RPPVlanApplyResp();
        vlanResp.setResultCode(code.toString());
        vlanResp.setResultMessage(errorMessage);
        return vlanResp;
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
}
