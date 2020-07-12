/*******************************************************************************
 * @(#)VMBAKStandardSynchronizeImpl.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vmbak.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronize;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeResult;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池同步资源规格信息
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 下午09:05:38
 */
public class VMBAKStandardSynchronizeImpl implements VMBAKStandardSynchronize {

    /**
     * 日志记录logger
     */
    private static LogService logger = LogService.getLogger(VMBAKStandardSynchronizeImpl.class);

    /**
     * http发送实体对象
     */
    private HttpSyncSendHelper senderEntry;

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
    public VMBAKStandardSynchronizeResp synchronizeStandard(VMBAKStandardSynchronizeReq req) {
        RuntimeContext reqStandard = new RuntimeContext();
        reqStandard.setAttribute("STANDARD_ID", req.getStandardId());
        reqStandard.setAttribute("STANDARD_NAME", req.getStandardName());
        reqStandard.setAttribute("TEMPLATE_ID", req.getTemplateId());
        reqStandard.setAttribute("MEASUREMODE", req.getMeasureMode());
        reqStandard.setAttribute("STANDARD_DESC", req.getStandardDesc());
        reqStandard.setAttribute("VMBACKUP_SIZE", req.getSpiceSize());
        reqStandard.setAttribute("CREATOR", req.getCreator());
        reqStandard.setAttribute("CREATE_TIME", req.getCreateTime());
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        for (ResourcePoolInfo info : req.getResourceInfos()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("RES_POOL_ID", info.getResourcePoolId());
            map.put("RES_POOL_PART_ID", info.getResourcePoolPart());
            lists.add(map);
        }
        reqStandard.setAttribute("RESOURCE_POOLS", lists);
        HttpSyncRespData resp = null;

        boolean haveException = false;
        try {
            resp = senderEntry.sendHttpRequest(reqStandard, url, socketTimeOut, recieveTimeOut);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            haveException = true;
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            haveException = true;
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误", e);
            haveException = true;
        }
        if (haveException) {
            return null;
        }
        return assembleResp(resp);
    }

    /**
     * assembleResp 组装响应报文
     * @param 同资源池代理模块的响应报文
     * @return 数据同步响应结果报文
     */
    @SuppressWarnings("unchecked")
    private VMBAKStandardSynchronizeResp assembleResp(HttpSyncRespData resp) {
        VMBAKStandardSynchronizeResp respStandards = new VMBAKStandardSynchronizeResp();
        RuntimeContext rc = resp.getRuntimeContext();
        String resultCode = (String) rc.getAttribute("RESULTCODE");
        if (resultCode.equals("FAIL")) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统系统内部错误，无法查询出资源规格相关信息", null);
            return null;
        }
        List<Map<String, String>> list = (List<Map<String, String>>) rc.getAttribute("RESULT");
        List<VMBAKStandardSynchronizeResult> success = new ArrayList<VMBAKStandardSynchronizeResult>();
        List<VMBAKStandardSynchronizeResult> failure = new ArrayList<VMBAKStandardSynchronizeResult>();
        for (Map<String, String> map : list) {
            VMBAKStandardSynchronizeResult result = new VMBAKStandardSynchronizeResult();
            result.setSynchronizedState(map.get("STATE"));
            ResourcePoolInfo resourceInfo = new ResourcePoolInfo();
            resourceInfo.setResourcePoolId(map.get("RESOURCE_POOL_ID"));
            resourceInfo.setResourcePoolPart(map.get("RESOURCE_POOL_PART_ID"));
            result.setResourcePool(resourceInfo);
            if (result.getSynchronizedState().equals("0")) {
                success.add(result);
            } else {
                failure.add(result);
            }
        }
        respStandards.setFailure(failure);
        respStandards.setSuccess(success);
        return respStandards;
    }

}
