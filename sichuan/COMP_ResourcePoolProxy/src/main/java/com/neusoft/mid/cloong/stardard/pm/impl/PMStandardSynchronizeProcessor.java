/*******************************************************************************
 * @(#)SRVStandardSynchronizeProcessor.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.pm.impl;

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
import com.neusoft.mid.cloong.stardard.pm.PMStandardCreate;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateReq;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateResp;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 
 * 物理服务器资源同步处理类
 * 方向:运营管理平台-->PROXY
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月17日 下午4:34:52
 */
public class PMStandardSynchronizeProcessor extends StandardBaseProcessor {

    private static LogService logger = LogService.getLogger(PMStandardSynchronizeProcessor.class);

    /**
     * 物理机规格创建接口
     */
    private PMStandardCreate create;

    /**
     * 物理机资源类型
     */
    private String resourceType = "CIDC-RT-SRV";
    
    /**
     * 返回请求成功状态码
     */
    private String RESP_SUCCESS_CODE = "00000000";
    
    /**
     * 默认资源池模板状态：配置文件注入
     */
    private String rpTemplateDefalutState = "2";

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        
        String standardId = (String) req.getAttribute("STANDARD_ID");
        String templateId = (String) req.getAttribute("TEMPLATE_ID");
        String standardName = (String) req.getAttribute("STANDARD_NAME");
        String measureMode = (String) req.getAttribute("MEASUREMODE");
        String standardDesc = (String) req.getAttribute("STANDARD_DESC");
        String cpuNum = (String) req.getAttribute("CPU_NUM");
        String memorySzie = (String) req.getAttribute("MEMORY_SIZE");
        String storageSize = (String) req.getAttribute("STORAGE_SIZE");
        String serverType = (String) req.getAttribute("SERVER_TYPE");
        String creator = (String) req.getAttribute("CREATOR");
        String createTime = (String) req.getAttribute("CREATE_TIME");
        
        
        //reqStandard.setAttribute("RESOURCE_POOLS", lists);
        StandardQueryPara para = assmbleStandardPara(req, standardId);
        List<StandardSynchronizedState> standardStates = null;
        try {
            standardStates = ibatisDao.getData("queryPMSynchronizedState", para);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机规格同步状态异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        for (StandardSynchronizedState state : standardStates) {
            Map<String, String> resultMap = new HashMap<String, String>();
            ResourcePoolInfo resourceInfo = queryResourceInfo(state.getResourcePoolId());
            if (resourceInfo == null || resourceInfo.getResourcePoolUrl() == null
                    || resourceInfo.getUserPassword() == null) {
                logger.error(CommonStatusCode.INTERNAL_ERROR,
                        "向资源池发送物理机规格同步操作请求失败，资源池代理系统内部错误，资源池配置错误", null);
                resultMap.put("STATE", "1");
                resultMap.put("RESOURCE_POOL_ID", state.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", state.getResourcePoolPartId());
                results.add(resultMap);
                continue;
            }
            if (CREATE_REGEX.matcher(state.getSynchronizedState()).matches()) {
                PMStandardCreateReq pmReq = new PMStandardCreateReq();
                pmReq.setStandardId(templateId);
                pmReq.setStandardName(standardName);
                pmReq.setResourceType(resourceType);
                pmReq.setStandardDesc(standardDesc);
                pmReq.setStorageSize(storageSize);
                pmReq.setCpuType(cpuNum);
                pmReq.setMemorySize(memorySzie);
                pmReq.setServerType(serverType);
                pmReq.setCreateTime(createTime);
                pmReq.setCreator(creator);
                pmReq.setStatus(rpTemplateDefalutState);
                pmReq.setMesureMode(measureMode);
                pmReq.setResourcePoolId(state.getResourcePoolId());
                pmReq.setResourcePoolPartId(state.getResourcePoolPartId());
                pmReq.setPassword(resourceInfo.getUserPassword());
                pmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
                pmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
                pmReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                        pmReq.getResourcePoolId(), pmReq.getTimestamp()));
                PMStandardCreateResp pmResp = create.createPMStandard(pmReq);
                resultMap.put("STATE", pmResp.getResultCode().equals(RESP_SUCCESS_CODE) ? "0" : "1");
                resultMap.put("RESOURCE_POOL_ID", pmReq.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", pmReq.getResourcePoolPartId());
            }
            results.add(resultMap);
        }
        assmbleResult(resp, results, "OK");
        return SUCCESS;
    }

    public void setCreate(PMStandardCreate create) {
        this.create = create;
    }

    /**
     * 设置rpTemplateDefalutState字段数据
     * @param rpTemplateDefalutState The rpTemplateDefalutState to set.
     */
    public void setRpTemplateDefalutState(String rpTemplateDefalutState) {
        this.rpTemplateDefalutState = rpTemplateDefalutState;
    }
}
