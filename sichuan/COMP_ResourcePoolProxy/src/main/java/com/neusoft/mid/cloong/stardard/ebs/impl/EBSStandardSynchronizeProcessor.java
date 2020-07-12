/*******************************************************************************
 * @(#)VMStandardSynchronizeProcessor.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.ebs.impl;

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
import com.neusoft.mid.cloong.stardard.ebs.EBSStandardCreate;
import com.neusoft.mid.cloong.stardard.ebs.info.EBSStandardCreateReq;
import com.neusoft.mid.cloong.stardard.ebs.info.EBSStandardCreateResp;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘规格同步处理器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午11:03:14
 */
public class EBSStandardSynchronizeProcessor extends StandardBaseProcessor {

    private static LogService logger = LogService.getLogger(EBSStandardSynchronizeProcessor.class);

    /**
     * 虚拟机规格创建接口
     */
    private EBSStandardCreate create;

    /**
     * 返回请求成功状态码
     */
    private String RESP_SUCCESS_CODE = "00000000";

    /**
     * 资源类型
     */
    private String resourceType = "CIDC-RT-BS";

    /**
     * 默认资源池模板状态：配置文件注入
     */
    private String rpTemplateDefalutState = "2";

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        String standardId = (String) req.getAttribute("STANDARD_ID");
        String standardName = (String) req.getAttribute("STANDARD_NAME");
        String templateId = (String) req.getAttribute("TEMPLATE_ID");
        String measureMode = (String) req.getAttribute("MEASUREMODE");
        String standardDesc = (String) req.getAttribute("STANDARD_DESC");
        String creator = (String) req.getAttribute("CREATOR");
        String createTime = (String) req.getAttribute("CREATE_TIME");
        String storageSize = (String) req.getAttribute("STORAGE_SIZE");
        StandardQueryPara para = assmbleStandardPara(req, standardId);
        List<StandardSynchronizedState> standardStates = null;
        try {
            standardStates = ibatisDao.getData("queryEBSSynchronizedState", para);
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
                logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟硬盘操作请求失败，资源池代理系统内部错误，资源池配置错误", null);
                resultMap.put("STATE", "1");
                resultMap.put("RESOURCE_POOL_ID", state.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", state.getResourcePoolPartId());
                results.add(resultMap);
                continue;
            }
            if (CREATE_REGEX.matcher(state.getSynchronizedState()).matches()) {
                EBSStandardCreateReq ebsReq = new EBSStandardCreateReq();
                ebsReq.setStandardId(templateId);
                ebsReq.setStandardName(standardName);
                ebsReq.setResourceType(resourceType);
                ebsReq.setStandardDesc(standardDesc);
                ebsReq.setStorageSize(storageSize);
                ebsReq.setCreateTime(createTime);
                ebsReq.setCreator(creator);
                ebsReq.setStatus(rpTemplateDefalutState);
                ebsReq.setMesureMode(measureMode);
                ebsReq.setResourcePoolId(state.getResourcePoolId());
                ebsReq.setResourcePoolPartId(state.getResourcePoolPartId());
                ebsReq.setPassword(resourceInfo.getUserPassword());
                ebsReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
                ebsReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
                ebsReq.setTransactionID(this.getTransactonGen().generateTransactionId(ebsReq.getResourcePoolId(),
                        ebsReq.getTimestamp()));
                EBSStandardCreateResp ebsResp = create.createEBSStandard(ebsReq);
                resultMap.put("STATE", ebsResp.getResultCode().equals(RESP_SUCCESS_CODE) ? "0" : "1");
                resultMap.put("RESOURCE_POOL_ID", ebsReq.getResourcePoolId());
                resultMap.put("RESOURCE_POOL_PART_ID", ebsReq.getResourcePoolPartId());
            }
            results.add(resultMap);
        }
        assmbleResult(resp, results, "OK");
        return SUCCESS;
    }

    public void setCreate(EBSStandardCreate create) {
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
