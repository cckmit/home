/*******************************************************************************
 * @(#)VMStandardSynchronizeImpl.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vm.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronize;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeResult;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池同步资源规格信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午09:05:38
 */
public class VMStandardSynchronizeImpl implements VMStandardSynchronize {

    private static LogService logger = LogService.getLogger(VMStandardSynchronizeImpl.class);

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
    public VMStandardSynchronizeResp synchronizeStandard(VMStandardSynchronizeReq req) {
        RuntimeContext reqStandard = new RuntimeContext();
        reqStandard.setAttribute("STANDARD_ID", req.getStandardId());
        reqStandard.setAttribute("TEMPLATE_ID", req.getTemplateId());
        reqStandard.setAttribute("STANDARD_NAME", req.getStandardName());
        reqStandard.setAttribute("MEASUREMODE", req.getMeasureMode());
        reqStandard.setAttribute("STANDARD_DESC", req.getStandardDesc());
        reqStandard.setAttribute("CPU_NUM", req.getCpuNum());
        reqStandard.setAttribute("MEMORY_SIZE", req.getMemorySize());
        reqStandard.setAttribute("STORAGE_SIZE", req.getStorageSize());
        reqStandard.setAttribute("CREATOR", req.getCreator());
        reqStandard.setAttribute("CREATE_TIME", req.getCreateTime());
        // 如果是同步虚拟机模板时下面的SYNCHRONIZE_PERSON,SYNCHRONIZE_TIME字段有用，其他无用
        reqStandard.setAttribute("SYNCHRONIZE_PERSON", req.getSynchronizePerson());
        reqStandard.setAttribute("SYNCHRONIZE_TIME", req.getSynchronizeTime());

        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        for (ResourcePoolInfo info : req.getResourceInfos()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("RES_POOL_ID", info.getResourcePoolId());
            map.put("RES_POOL_PART_ID", info.getResourcePoolPart());
            // 如果是同步虚拟机模板时下面的OS_ID字段有用，其他无用
            map.put("OS_ID", info.getOsId());
            lists.add(map);
        }
        reqStandard.setAttribute("RESOURCE_POOLS", lists);
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
        return assembleResp(resp);
    }

    @SuppressWarnings("unchecked")
    private VMStandardSynchronizeResp assembleResp(HttpSyncRespData resp) {
        VMStandardSynchronizeResp respStandards = new VMStandardSynchronizeResp();
        RuntimeContext rc = resp.getRuntimeContext();
        String resultCode = (String) rc.getAttribute("RESULTCODE");
        if (resultCode.equals("FAIL")) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统系统内部错误，无法查询出资源规格相关信息", null);
            return null;
        }
        List<Map<String, String>> list = (List<Map<String, String>>) rc.getAttribute("RESULT");
        List<VMStandardSynchronizeResult> success = new ArrayList<VMStandardSynchronizeResult>();
        List<VMStandardSynchronizeResult> failure = new ArrayList<VMStandardSynchronizeResult>();
        for (Map<String, String> map : list) {
            VMStandardSynchronizeResult result = new VMStandardSynchronizeResult();
            result.setSynchronizedState(map.get("STATE"));
            ResourcePoolInfo resourceInfo = new ResourcePoolInfo();
            resourceInfo.setResourcePoolId(map.get("RESOURCE_POOL_ID"));
            resourceInfo.setResourcePoolPart(map.get("RESOURCE_POOL_PART_ID"));
            // 只在同步虚拟机模板时OS_ID字段有用
            resourceInfo.setOsId(map.get("OS_ID"));
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
