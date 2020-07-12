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
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.stardard.ResourcePoolInfoPara;
import com.neusoft.mid.cloong.stardard.StandardBaseProcessor;
import com.neusoft.mid.cloong.stardard.StandardQueryPara;
import com.neusoft.mid.cloong.stardard.StandardSynInfo;
import com.neusoft.mid.cloong.stardard.StandardSynchronizedState;
import com.neusoft.mid.cloong.stardard.vm.VMStandardCreate;
import com.neusoft.mid.cloong.stardard.vm.info.Template;
import com.neusoft.mid.cloong.stardard.vm.info.TemplateSynchronizeResult;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateReq;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateResp;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机模板同步处理器
 * @author <a href="mailto:he.jf@neusoft.com">hejunfeng </a>
 * @version $Revision 1.1 $ 2014-1-10 上午11:03:14
 */
/**
 * @author he.jf
 */
public class VMTemplateSynchronizeProcessor extends StandardBaseProcessor {

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(VMTemplateSynchronizeProcessor.class);

    /**
     * 虚拟机规格创建接口
     */
    private VMStandardCreate create;

    /**
     * 资源类型
     */
    private String resourceType = "CIDC-RT-VM";

    /**
     * 序列号生成器
     */
    private TransactionIdGenerator transactonGen;

    /**
     * 返回请求成功状态码
     */
    private static final String RESP_SUCCESS_CODE = "00000000";

    /**
     * 默认资源池模板状态：配置文件注入
     */
    private String rpTemplateDefalutState = "2";

    @SuppressWarnings("deprecation")
    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        String standardId = (String) req.getAttribute("STANDARD_ID");
        StandardQueryPara para = this.assmbleTemplatePara(req, standardId);
        // 返回的消息
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        // 要保存到数据库的信息集合
        List<StandardSynchronizedState> saveDbSynInfos;
        // 已经存在的模板信息集合
        List<Template> templates;
        // 将已经存在的模板信息集合转换成MAP
        Map<String, String> templateMap = new HashMap<String, String>();

        // 查询出要保存到数据库关联表和同步表的信息
        try {
            logger.info("开始查询出要保存到数据库关联表和同步表的信息!");
            saveDbSynInfos = ibatisDao.getData("querySaveDbVMSynchronized", para);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询出要保存到数据库关联表和同步表的信息时数据库异常!", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询出要保存到数据库关联表和同步表的信息时数据库异常!", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }

        // 根据规格id查询出所有存在模板的模板信息
        try {
            logger.info("开始根据规格id查询出所有存在模板的模板信息!");
            templates = ibatisDao.getData("queryTemplateMessage", standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "根据规格id查询出所有存在模板的模板信息时数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "根据规格id查询出所有存在模板的模板信息时数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }

        // 将模板信息list转换成map
        if (null != templates && templates.size() > 0) {
            for (Template template : templates) {
                String key = template.getStandardId() + template.getOsId();
                templateMap.put(key, template.getTemplateId());
            }
        }
        // 保存到同步表的集合
        List<StandardSynInfo> synchronizedInfos = new ArrayList<StandardSynInfo>();
        // 保存到关联表的集合
        List<Template> templateInfos = new ArrayList<Template>();
        // 拼装要保存到数据库的信息
        assmbleSaveDbInfo(saveDbSynInfos, templateMap, synchronizedInfos, templateInfos, req);
        // 保存数据到关联表和同步表
        try {
            logger.info("开始保存数据到关联表和同步表!");
            this.insertInfos(templateInfos, synchronizedInfos);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "保存数据到关联表和同步表时数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "保存数据到关联表和同步表时数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }

        // 查询出所有要同步的信息
        List<StandardSynchronizedState> standardStates;
        try {
            logger.info("开始查询出所有要同步的模板信息!");
            standardStates = ibatisDao.getData("queryVmTemplateSynchronizedState", para);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询出所有要同步的模板信息时数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询出所有要同步的模板信息时数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }
        // 向资源池发送消息
        logger.info("开始向资源池发送消息!");
        // 更新状态
        List<TemplateSynchronizeResult> templateSynState = new ArrayList<TemplateSynchronizeResult>();

        if (null != standardStates) {
            for (StandardSynchronizedState state : standardStates) {
                ResourcePoolInfo resourceInfo = queryResourceInfo(state.getResourcePoolId());
                if (resourceInfo == null || resourceInfo.getResourcePoolUrl() == null
                        || resourceInfo.getUserPassword() == null) {

                    logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机操作请求失败，资源池代理系统内部错误，资源池配置错误", null);
                    // 拼装要保存到数据库的同步信息，将同步状态设置1
                    TemplateSynchronizeResult templateSysResult = new TemplateSynchronizeResult();
                    templateSysResult.setResourcePoolId(state.getResourcePoolId());
                    templateSysResult.setResourcePoolPartId(state.getResourcePoolPartId());
                    templateSysResult.setStandardId(state.getStandardId());
                    templateSysResult.setTemplateId(templateMap.get(state.getStandardId() + state.getOsId()));
                    templateSysResult.setState("1");
                    templateSysResult.setOsId(state.getOsId());
                    templateSynState.add(templateSysResult);
                    continue;
                }
                if (CREATE_REGEX.matcher(state.getSynchronizedState()).matches()) {
                    vmTemplateCreate(templateMap, state, req, resourceInfo, templateSynState);
                }
            }
        }
        // 调用接口返回状态，更新数据库方法
        try {
            this.updateSynState(templateSynState, results);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "保存资源池返回同步状态时更新数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "保存资源池返回同步状态时更新数据库异常", e);
            assmbleResult(resp, null, "FAIL");
            return FAILURE;
        }
        assmbleResult(resp, results, "OK");
        return SUCCESS;
    }

    /***
     * 拼装要插入同步表及虚拟机镜像关联模板表数据
     * @param saveDbSynInfos 保存同步信息
     * @param templateMap 模板map
     * @param synchronizedInfos 保存到数据库同步表的信息集合
     * @param templateInfos 保存到数据库关联表的信息集合
     * @param req 请求信息
     */
    private void assmbleSaveDbInfo(List<StandardSynchronizedState> saveDbSynInfos, Map<String, String> templateMap,
            List<StandardSynInfo> synchronizedInfos, List<Template> templateInfos, RuntimeContext req) {
        String synchronizePerson = (String) req.getAttribute("SYNCHRONIZE_PERSON");
        String synchronizeTime = (String) req.getAttribute("SYNCHRONIZE_TIME");
        logger.info("开始拼装要保存到数据库的信息!");
        if (null != saveDbSynInfos) {
            for (StandardSynchronizedState save : saveDbSynInfos) {
                if ("".equals(templateMap.get(save.getStandardId() + save.getOsId()))
                        || null == templateMap.get(save.getStandardId() + save.getOsId())) {
                    transactonGen.setIbatisDAO(ibatisDao);
                    String templateId = transactonGen.generatorVMItemId();
                    Template template = new Template();
                    template.setStandardId(save.getStandardId());
                    template.setOsId(save.getOsId());
                    template.setTemplateId(templateId);
                    template.setCreateUser(synchronizePerson);
                    template.setCreateTime(synchronizeTime);
                    template.setStatus("1");
                    templateInfos.add(template);
                    templateMap.put(template.getStandardId() + template.getOsId(), template.getTemplateId());
                    // 掉用封装模板同步实体
                    StandardSynInfo standardSynInfo = this.saveStandardSynInfo(req, templateMap, save);
                    synchronizedInfos.add(standardSynInfo);
                } else {
                    StandardSynInfo standardSynInfo = this.saveStandardSynInfo(req, templateMap, save);
                    synchronizedInfos.add(standardSynInfo);
                }
            }
        }
    }

    /*****
     * 调用资源池创建接口发送消息
     * @param templateMap 模板map
     * @param state 模板同步实体
     * @param req 请求信息
     * @param resourceInfo 资源池实体
     * @param templateSynState 资源池返回状态集合
     */
    private void vmTemplateCreate(Map<String, String> templateMap, StandardSynchronizedState state, RuntimeContext req,
            ResourcePoolInfo resourceInfo, List<TemplateSynchronizeResult> templateSynState) {

        String measureMode = (String) req.getAttribute("MEASUREMODE");
        String standardName = (String) req.getAttribute("STANDARD_NAME");
        String standardDesc = (String) req.getAttribute("STANDARD_DESC");
        String creator = (String) req.getAttribute("CREATOR");
        String createTime = (String) req.getAttribute("CREATE_TIME");
        String cpuNum = (String) req.getAttribute("CPU_NUM");
        String memorySize = (String) req.getAttribute("MEMORY_SIZE");
        String storageSize = (String) req.getAttribute("STORAGE_SIZE");

        VMstandardCreateReq vmReq = new VMstandardCreateReq();
        String templateId = templateMap.get(state.getStandardId() + state.getOsId());
        vmReq.setStandardId(templateId);
        vmReq.setStandardName(standardName);
        vmReq.setResourceType(resourceType);
        vmReq.setStandardDesc(standardDesc);
        vmReq.setCpuNum(cpuNum);
        vmReq.setMemorySize(memorySize);
        vmReq.setStorageSize(storageSize);
        vmReq.setCreateTime(createTime);
        vmReq.setCreator(creator);
        vmReq.setStatus(rpTemplateDefalutState);
        vmReq.setMesureMode(measureMode);
        vmReq.setResourcePoolId(state.getResourcePoolId());
        vmReq.setResourcePoolPartId(state.getResourcePoolPartId());
        vmReq.setVmOs(state.getOsId());
        vmReq.setPassword(resourceInfo.getUserPassword());
        vmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        vmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        vmReq.setTransactionID(this.getTransactonGen().generateTransactionId(vmReq.getResourcePoolId(),
                vmReq.getTimestamp()));

        // 更新状态
        TemplateSynchronizeResult templateSysResult = new TemplateSynchronizeResult();
        templateSysResult.setResourcePoolId(vmReq.getResourcePoolId());
        templateSysResult.setResourcePoolPartId(vmReq.getResourcePoolPartId());
        templateSysResult.setStandardId(state.getStandardId());
        templateSysResult.setTemplateId(templateId);
        templateSysResult.setOsId(state.getOsId());
        templateSynState.add(templateSysResult);

        VMstandardCreateResp vmResp = create.createVMStandard(vmReq);

        templateSysResult.setState(vmResp.getResultCode().equals(RESP_SUCCESS_CODE) ? "0" : "1");

    }

    /****
     * 获取请求信息中的RESOURCE_POOLS，并封装到StandardQueryPara中
     * @param req 请求信息
     * @param standardId 规格id
     * @return StandardQueryPara 模板查询
     */
    private StandardQueryPara assmbleTemplatePara(RuntimeContext req, String standardId) {
        List<Map<String, String>> lists = (List<Map<String, String>>) req.getAttribute("RESOURCE_POOLS");
        StandardQueryPara para = new StandardQueryPara();
        para.setStandardId(standardId);
        List<ResourcePoolInfoPara> resourceInfos = new ArrayList<ResourcePoolInfoPara>();
        for (Map<String, String> map : lists) {
            ResourcePoolInfoPara resourceInfo = new ResourcePoolInfoPara();
            resourceInfo.setResourcePoolId(map.get("RES_POOL_ID"));
            resourceInfo.setResourcePoolPartId(map.get("RES_POOL_PART_ID"));
            resourceInfo.setOsId(map.get("OS_ID"));
            resourceInfos.add(resourceInfo);
        }
        para.setResourceInfos(resourceInfos);
        return para;
    }

    /**
     * 保存数据到同步表和关联表
     * @param templateInfos 要保存到关联表的集合
     * @param synchronizedInfos 要保存到同步表的集合
     * @throws SQLException
     */
    private void insertInfos(List<Template> templateInfos, List<StandardSynInfo> synchronizedInfos) throws SQLException {
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        // 插入到关联表的数据
        for (Template template : templateInfos) {
            BatchVO batchVO = new BatchVO("ADD", "insertTemplateInfos", template);
            batchVOs.add(batchVO);
        }
        // 插入到同步表的数据
        for (StandardSynInfo standardSynInfo : synchronizedInfos) {
            BatchVO batchVO = new BatchVO("ADD", "insertSynchronizedInfos", standardSynInfo);
            batchVOs.add(batchVO);
        }
        ibatisDao.updateBatchData(batchVOs);
    }

    /**
     * 将资源池返回的同步模板状态更新到数据库，拼装返回消息
     * @param templateSynState 资源池返回的状态集合
     * @param results 要返回到management的消息
     * @throws SQLException
     */
    private void updateSynState(List<TemplateSynchronizeResult> templateSynState, List<Map<String, String>> results)
            throws SQLException {
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        for (TemplateSynchronizeResult templateSynResult : templateSynState) {
            BatchVO batchVO = new BatchVO("MOD", "updateTemplateSynState", templateSynResult);
            batchVOs.add(batchVO);

            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("STATE", templateSynResult.getState());
            resultMap.put("RESOURCE_POOL_ID", templateSynResult.getResourcePoolId());
            resultMap.put("RESOURCE_POOL_PART_ID", templateSynResult.getResourcePoolPartId());
            resultMap.put("OS_ID", templateSynResult.getOsId());
            results.add(resultMap);
        }
        ibatisDao.updateBatchData(batchVOs);
    }

    /***
     * 封装同步模板info
     * @param req 请求信息
     * @param templateMap 模板map
     * @param save 模板同步状态
     * @return 模板同步信息
     */
    private StandardSynInfo saveStandardSynInfo(RuntimeContext req, Map<String, String> templateMap,
            StandardSynchronizedState save) {
        StandardSynInfo standardSynInfo = new StandardSynInfo();
        standardSynInfo.setResPoolId(save.getResourcePoolId());
        standardSynInfo.setResPoolPartId(save.getResourcePoolPartId());
        standardSynInfo.setStandardId(save.getStandardId());
        standardSynInfo.setStandardType("0");
        standardSynInfo.setStatus("1");
        standardSynInfo.setSynTime((String) req.getAttribute("SYNCHRONIZE_TIME"));
        standardSynInfo.setSynUser((String) req.getAttribute("SYNCHRONIZE_PERSON"));
        standardSynInfo.setTemplateId(templateMap.get(save.getStandardId() + save.getOsId()));
        return standardSynInfo;
    }

    public void setCreate(VMStandardCreate create) {
        this.create = create;
    }

    public TransactionIdGenerator getTransactonGen() {
        return transactonGen;
    }

    public void setTransactonGen(TransactionIdGenerator transactonGen) {
        this.transactonGen = transactonGen;
    }

    /**
     * 设置rpTemplateDefalutState字段数据
     * @param rpTemplateDefalutState The rpTemplateDefalutState to set.
     */
    public void setRpTemplateDefalutState(String rpTemplateDefalutState) {
        this.rpTemplateDefalutState = rpTemplateDefalutState;
    }

}
