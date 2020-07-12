/*******************************************************************************
 * @(#)VMStandardSynchronizeProcessor.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.stardard.StandardSynInfo;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 规格同步删除处理器
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午01:56:35
 */
public class StandardSynchronizeDeleteProcessor extends BaseProcessor {

    private static LogService logger = LogService
            .getLogger(StandardSynchronizeDeleteProcessor.class);

    /**
     * 机规格删除接口
     */
    private StandardDelete delete;

    private IbatisDAO ibatisDao;

    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        String standardId = (String) req.getAttribute("STANDARD_ID");
        List<StandardSynInfo> deleteStandardSynInfos = null;
        try {
            deleteStandardSynInfos = ibatisDao.getData("querySynchronizedDelete", standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询机规格同步删除状态异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }

        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        // 循环调用资源池【 删除规格】请求
        for (StandardSynInfo standardSynInfo : deleteStandardSynInfos) {
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("RESOURCE_POOL_ID", standardSynInfo.getResPoolId());
            resultMap.put("RESOURCE_POOL_PART_ID", standardSynInfo.getResPoolPartId());
            resultMap.put("TEMPLATE_ID", standardSynInfo.getTemplateId());
            resultMap.put("STATE", "7");
            results.add(resultMap);
            ResourcePoolInfo  resourceInfo = queryResourceInfo(standardSynInfo.getResPoolId());
            if (resourceInfo == null || resourceInfo.getResourcePoolUrl() == null
                    || resourceInfo.getUserPassword() == null) {
                logger.error(CommonStatusCode.INTERNAL_ERROR,
                        "向资源池发送虚拟机操作请求失败，资源池代理系统内部错误，资源池配置错误", null);
                resultMap.put("STATE", "7");
                continue;
            }
            StandardDeleteReq standardReq = new StandardDeleteReq();
            standardReq.setStandardId(standardSynInfo.getTemplateId());// 资源池对应ID
            standardReq.setResourcePoolId(standardSynInfo.getResPoolId());
            standardReq.setResourcePoolPartId(standardSynInfo.getResPoolPartId());
            standardReq.setPassword(resourceInfo.getUserPassword());
            standardReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
            standardReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
            standardReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                    standardReq.getResourcePoolId(), standardReq.getTimestamp()));
            StandardDeleteResp vmResp = delete.deleteStandard(standardReq);

            // 如果返回非00000000直接退出调用接口请求.
            if (vmResp.getResultCode().equals("00000000")) {
                logger.info("向资源池发送模板["+standardSynInfo.getTemplateId()+"]删除请求成功!");
                resultMap.put("STATE", "6");
            } else {
                logger.info("向资源池发送模板["+standardSynInfo.getTemplateId()+"]删除请求失败!");
                resultMap.put("STATE", "7");
            }
        }
        assmbleResult(resp, results, "OK");
        return SUCCESS;
    }

    /**
     * assmbleResult 组装返回值
     * @param resp RuntimeContext
     * @param results 结果集合
     * @param resultCode 结果码
     */
    private void assmbleResult(RuntimeContext resp, List<Map<String, String>> results,
            String resultCode) {
        resp.setAttribute("RESULTCODE", resultCode);
        resp.setAttribute("RESULT", results);
    }

    /**
     * queryResourceInfo 获取资源池信息
     * @param resourcePoolId 资源池ID
     * @return 资源池信息
     */
    private ResourcePoolInfo queryResourceInfo(String resourcePoolId) {
        ResourcePoolInfo resourceInfo = null;
        try {
            resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                    resourcePoolId);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送规格删除请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e);
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送规格删除请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e);
        }
        return resourceInfo;
    }

    public void setDelete(StandardDelete delete) {
        this.delete = delete;
    }

}
