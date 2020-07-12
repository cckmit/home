/*******************************************************************************
 * @(#)VMStandardSynchronizeProcessor.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.stardard.StandardBaseProcessor;
import com.neusoft.mid.cloong.stardard.StandardQueryPara;
import com.neusoft.mid.cloong.stardard.StandardSynchronizedState;
import com.neusoft.mid.cloong.stardard.vm.VMStandardCreate;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateReq;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateResp;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机模板同步处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午11:03:14
 */
public class VMStandardSynchronizeProcessor extends StandardBaseProcessor {

    private static LogService logger = LogService.getLogger(VMStandardSynchronizeProcessor.class);

    /**
     * 虚拟机规格创建接口
     */
    private VMStandardCreate create;

    /**
     * 资源类型
     */
    private String resourceType = "CIDC-RT-VM";

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        String standardId = (String) req.getAttribute("STANDARD_ID");
        String measureMode = (String) req.getAttribute("MEASUREMODE");
        String standardDesc = (String) req.getAttribute("STANDARD_DESC");
        String creator = (String) req.getAttribute("CREATOR");
        String createTime = (String) req.getAttribute("CREATE_TIME");
        String cpuNum = (String) req.getAttribute("CPU_NUM");
        String memorySize = (String) req.getAttribute("MEMORY_SIZE");
        String storageSize = (String) req.getAttribute("STORAGE_SIZE");
        StandardQueryPara para = assmbleStandardPara(req, standardId);
        List<StandardSynchronizedState> standardStates = null;
        try {
            standardStates = ibatisDao.getData("queryVMSynchronizedState", para);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机规格同步状态异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        for (StandardSynchronizedState state : standardStates) {
            Map<String, String> resultMap = new HashMap<String, String>();
            ResourcePoolInfo resourceInfo = queryResourceInfo(state.getResourcePoolId());
            if (resourceInfo == null || resourceInfo.getResourcePoolUrl() == null
                    || resourceInfo.getUserPassword() == null) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机操作请求失败，资源池代理系统内部错误，资源池配置错误", null);
                resultMap.put("STATE", "1");
                resultMap.put("RESOURCE_POOL_ID", state.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", state.getResourcePoolPartId());
                results.add(resultMap);
                continue;
            }
            if (CREATE_REGEX.matcher(state.getSynchronizedState()).matches()) {
                VMstandardCreateReq vmReq = new VMstandardCreateReq();
                vmReq.setStandardId(standardId);
                vmReq.setResourceType(resourceType);
                vmReq.setStandardDesc(standardDesc);
                vmReq.setCpuNum(cpuNum);
                vmReq.setMemorySize(memorySize);
                vmReq.setStorageSize(storageSize);
                vmReq.setCreateTime(createTime);
                vmReq.setCreator(creator);
                vmReq.setStatus(state.getStandardState() == null ? "1" : state.getStandardState());
                vmReq.setMesureMode(measureMode);
                vmReq.setResourcePoolId(state.getResourcePoolId());
                vmReq.setResourcePoolPartId(state.getResourcePoolPartId());
                vmReq.setPassword(resourceInfo.getUserPassword());
                vmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
                vmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
                vmReq.setTransactionID(this.getTransactonGen().generateTransactionId(vmReq.getResourcePoolId(),
                        vmReq.getTimestamp()));
                VMstandardCreateResp vmResp = create.createVMStandard(vmReq);
                resultMap.put("STATE", vmResp.getResultCode().equals("00000000") ? "0" : "1");
                resultMap.put("RESOURCE_POOL_ID", vmReq.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", vmReq.getResourcePoolPartId());
            }
            results.add(resultMap);
        }
        assmbleResult(resp, results, "OK");
        return SUCCESS;
    }

    public void setCreate(VMStandardCreate create) {
        this.create = create;
    }


}
