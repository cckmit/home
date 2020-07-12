/*******************************************************************************
 * @(#)StandardSynchronizeDelImpl.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardSynInfo;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用向资源池同步删除资源规格信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 上午10:57:32
 */
public class StandardSynchronizeDeleteImpl implements StandardSynchronizeDelete {

    private static LogService logger = LogService.getLogger(StandardSynchronizeDeleteImpl.class);

    /**
     * http发送实体对象
     */
    private HttpSyncSendHelper senderEntry;
    
    /** 返回同步发送删除失败 **/
    private static final String FAILURECODE = "7";
    
    /** 返回同步发送删除成功 **/
    private static final String SUCCESSCODE = "6";

    /**
     * 资源池代理系统URL
     */
    private String url;

    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 接收超时
     */
    private int recieveTimeOut;

    /**
     * socket超时
     */
    private int socketTimeOut;

    public void setRecieveTimeOut(int recieveTimeOut) {
        this.recieveTimeOut = recieveTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }
    
    @Override
    public StandardSynchronizeDeleteResp synchronizeDeleteStandard(String STANDARD_ID) {
        RuntimeContext reqStandard = new RuntimeContext();
        reqStandard.setAttribute("STANDARD_ID", STANDARD_ID);
        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(reqStandard, url, socketTimeOut, recieveTimeOut);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            return null;
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            return null;
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误", e);
            return null;
        }
        RuntimeContext rc = resp.getRuntimeContext();
        String resultCode = (String) rc.getAttribute("RESULTCODE");
        if (resultCode.equals("FAIL")) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统系统内部错误，无法查询出资源规格相关信息", null);
            return null;
        }else if (resultCode.equals("OK")) {
            logger.info("调用资源池代理系统发送删除同步请求成功！");
            return assembleResp(resp);
        }else{
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    private StandardSynchronizeDeleteResp assembleResp(HttpSyncRespData resp) {
        StandardSynchronizeDeleteResp respStandards = new StandardSynchronizeDeleteResp();
        RuntimeContext rc = resp.getRuntimeContext();
        List<Map<String, String>> list = (List<Map<String, String>>) rc.getAttribute("RESULT");
        List<StandardSynInfo> success = new ArrayList<StandardSynInfo>();
        List<StandardSynInfo> failure = new ArrayList<StandardSynInfo>();
        for (Map<String, String> map : list) {
            StandardSynInfo result = new StandardSynInfo();
            result.setStatus(map.get("STATE"));
            result.setTemplateId(map.get("TEMPLATE_ID"));
            result.setResPoolId(map.get("RESOURCE_POOL_ID"));
            result.setResPoolPartId(map.get("RESOURCE_POOL_PART_ID"));
            if(FAILURECODE.equals(result.getStatus())){
                failure.add(result);
            }else if(SUCCESSCODE.equals(result.getStatus())){
                success.add(result);
            }
        }
        respStandards.setFailure(failure);
        respStandards.setSuccess(success);
        return respStandards;
    }
    
}
