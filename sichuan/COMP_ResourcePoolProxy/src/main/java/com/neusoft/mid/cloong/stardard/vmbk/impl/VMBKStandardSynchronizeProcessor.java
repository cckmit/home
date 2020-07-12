/*******************************************************************************
 * @(#)VMStandardSynchronizeProcessor.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vmbk.impl;

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
import com.neusoft.mid.cloong.stardard.vmbk.VMBKStandardCreate;
import com.neusoft.mid.cloong.stardard.vmbk.VMBKStandardModify;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardCreateReq;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardCreateResp;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardModifyReq;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardModifyResp;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份规格同步处理器
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月22日 下午5:17:46
 */
public class VMBKStandardSynchronizeProcessor extends StandardBaseProcessor {

    private static LogService logger = LogService.getLogger(VMBKStandardSynchronizeProcessor.class);

    /**
     * 虚拟机备份规格创建接口
     */
    private VMBKStandardCreate create;

    /**
     * 虚拟机备份规格修改接口
     */
    private VMBKStandardModify modify;

    /**
     * 虚拟机备份资源类型
     */
    private String resourceType = "CIDC-RT-VMBK";
    
    /**
     * 默认资源池模板状态：配置文件注入
     */
    private String rpTemplateDefalutState = "2";
    
    /**
     * 返回请求成功状态码
     */
    private String RESP_SUCCESS_CODE = "00000000";

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        String standardId = (String) req.getAttribute("STANDARD_ID");
        String standardName = (String) req.getAttribute("STANDARD_NAME");
        String templateId = (String) req.getAttribute("TEMPLATE_ID");
        String measureMode = (String) req.getAttribute("MEASUREMODE");
        String standardDesc = (String) req.getAttribute("STANDARD_DESC");
        String creator = (String) req.getAttribute("CREATOR");
        String createTime = (String) req.getAttribute("CREATE_TIME");
        String vmBackupSize = (String) req.getAttribute("VMBACKUP_SIZE");
        StandardQueryPara para = assmbleStandardPara(req, standardId);
        List<StandardSynchronizedState> standardStates = null;
        try {
            standardStates = ibatisDao.getData("queryVMBKSynchronizedState", para);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机备份规格同步状态异常", e);
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
                        "向资源池发送虚拟机备份操作请求失败，资源池代理系统内部错误，资源池配置错误", null);
                resultMap.put("STATE", "1");
                resultMap.put("RESOURCE_POOL_ID", state.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", state.getResourcePoolPartId());
                results.add(resultMap);
                continue;
            }
            if (CREATE_REGEX.matcher(state.getSynchronizedState()).matches()) {
                VMBKStandardCreateReq vmbkReq = new VMBKStandardCreateReq();
                vmbkReq.setStandardId(templateId);
                vmbkReq.setStandardName(standardName);
                vmbkReq.setResourceType(resourceType);
                vmbkReq.setStandardDesc(standardDesc);
                vmbkReq.setVMBackupSize(vmBackupSize);
                vmbkReq.setCreateTime(createTime);
                vmbkReq.setCreator(creator);
                vmbkReq.setStatus(rpTemplateDefalutState);
                vmbkReq.setMesureMode(measureMode);
                vmbkReq.setResourcePoolId(state.getResourcePoolId());
                vmbkReq.setResourcePoolPartId(state.getResourcePoolPartId());
                vmbkReq.setPassword(resourceInfo.getUserPassword());
                vmbkReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
                vmbkReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
                vmbkReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                        vmbkReq.getResourcePoolId(), vmbkReq.getTimestamp()));
                VMBKStandardCreateResp ebsResp = create.createVMBKStandard(vmbkReq);
                resultMap.put("STATE", ebsResp.getResultCode().equals(RESP_SUCCESS_CODE) ? "0" : "1");
                resultMap.put("RESOURCE_POOL_ID", vmbkReq.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", vmbkReq.getResourcePoolPartId());
            }
            if (MODIFY_REGEX.matcher(state.getSynchronizedState()).matches()) {
                VMBKStandardModifyReq vmbkReq = new VMBKStandardModifyReq();
                vmbkReq.setStandardId(templateId);
                vmbkReq.setStandardName(standardName);
                vmbkReq.setResourceType(resourceType);
                vmbkReq.setStandardDesc(standardDesc);
                vmbkReq.setVMBackupSize(vmBackupSize);
                vmbkReq.setCreateTime(createTime);
                vmbkReq.setCreator(creator);
                vmbkReq.setMesureMode(measureMode);
                vmbkReq.setStatus(rpTemplateDefalutState);
                vmbkReq.setResourcePoolId(state.getResourcePoolId());
                vmbkReq.setResourcePoolPartId(state.getResourcePoolPartId());
                vmbkReq.setPassword(resourceInfo.getUserPassword());
                vmbkReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
                vmbkReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
                vmbkReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                        vmbkReq.getResourcePoolId(), vmbkReq.getTimestamp()));
                VMBKStandardModifyResp vmResp = modify.modifyVMBKStandard(vmbkReq);
                resultMap.put("STATE", vmResp.getResultCode().equals(RESP_SUCCESS_CODE) ? "0" : "5");
                resultMap.put("RESOURCE_POOL_ID", vmbkReq.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", vmbkReq.getResourcePoolPartId());
            }
            results.add(resultMap);
        }
        assmbleResult(resp, results, "OK");
        return SUCCESS;
    }

    public void setCreate(VMBKStandardCreate create) {
        this.create = create;
    }

    public void setModify(VMBKStandardModify modify) {
        this.modify = modify;
    }
    
    /**
     * 设置rpTemplateDefalutState字段数据
     * @param rpTemplateDefalutState The rpTemplateDefalutState to set.
     */
    public void setRpTemplateDefalutState(String rpTemplateDefalutState) {
        this.rpTemplateDefalutState = rpTemplateDefalutState;
    }
    
}
