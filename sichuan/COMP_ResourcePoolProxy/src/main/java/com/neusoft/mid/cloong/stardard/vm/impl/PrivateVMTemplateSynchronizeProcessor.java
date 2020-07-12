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

import org.springframework.beans.factory.annotation.Value;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.stardard.ResourcePoolInfoPara;
import com.neusoft.mid.cloong.stardard.StandardBaseProcessor;
import com.neusoft.mid.cloong.stardard.StandardOperationException;
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
public class PrivateVMTemplateSynchronizeProcessor extends StandardBaseProcessor {

    @Value("${template.vm.defaultVal.OsDiskType}")
    private String defaultOsDiskType;
    

    /**
     * 获取defaultOsDiskType字段数据
     * @return Returns the defaultOsDiskType.
     */
    public String getDefaultOsDiskType() {
        return defaultOsDiskType;
    }

    /**
     * 设置defaultOsDiskType字段数据
     * @param defaultOsDiskType The defaultOsDiskType to set.
     */
    public void setDefaultOsDiskType(String defaultOsDiskType) {
        this.defaultOsDiskType = defaultOsDiskType;
    }

    @Value("${template.vm.defaultVal.VethAdaNum}")
    private String defaultVethAdaNum;

    @Value("${template.vm.defaultVal.VscsiAdaNum}")
    private String defaultVscsiAdaNum;

    @Value("${template.vm.defaultVal.VfcHbaNum}")
    private String defaultVfcHbaNum;

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(PrivateVMTemplateSynchronizeProcessor.class);

    /**
     * 虚拟机规格创建接口
     */
    private VMStandardCreate create;

    /**
     * 资源类型
     */
    private String resourceType = "CPC-RT-VM";

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

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {

        String standardId = (String) req.getAttribute("STANDARD_ID");

        try {
            // 1.准备初始化数据
            StandardQueryPara standardQueryPara = this.assmbleTemplatePara(req);

            // 要保存到数据库的信息集合
            List<StandardSynchronizedState> willSynInfoList = queryWillSyncInfos(resp, standardQueryPara);

            // 根据规格id查询出所有存在模板的模板信息
            Map<String, String> existTemplateMap = queryExsitTmplates(req, resp);

            // 拼装要保存到数据库的信息
            // 保存数据到关联表和同步表
            saveTmplateAndSyncInfoToDB(willSynInfoList, existTemplateMap, req, resp);

            // 查询出所有要同步的信息
            List<StandardSynchronizedState> standardStates = querySycnTemplateList(resp, standardQueryPara);

            // 向资源池发送消息
            List<TemplateSynchronizeResult> templateSynState = createTmeplateToResourcePool(req, existTemplateMap,
                    standardStates);

            // 调用接口返回状态，更新数据库方法
            // 返回的消息
            List<Map<String, String>> results = this.updateSynState(templateSynState, resp);

            assmbleResult(resp, results, "OK");

        } catch (StandardOperationException e) {
            logger.error("向资源池创建模板时发生异常,规格[" + standardId + "]", e);
            return FAILURE;
        }

        return SUCCESS;
    }

    /**
     * 创建模板到资源池
     * @author fengj<br>
     *         2015年2月11日 下午9:41:41
     */
    protected List<TemplateSynchronizeResult> createTmeplateToResourcePool(RuntimeContext req,
            Map<String, String> existTemplateMap, List<StandardSynchronizedState> standardStates) {

        logger.info("开始向资源池发送消息!");
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
                    templateSysResult.setTemplateId(existTemplateMap.get(state.getStandardId() + state.getOsId()));
                    templateSysResult.setState("1");
                    templateSysResult.setOsId(state.getOsId());
                    templateSynState.add(templateSysResult);
                    continue;
                }
                if (CREATE_REGEX.matcher(state.getSynchronizedState()).matches()) {
                    vmTemplateCreate(existTemplateMap, state, req, resourceInfo, templateSynState);
                }
                if (MODIFY_REGEX.matcher(state.getSynchronizedState()).matches()) {
                    // vmTemplateModify(existTemplateMap, state, req, resourceInfo,
                    // templateSynState);
                    logger.error("私有云接口不支持模板的修改,发送请求错误!");
                }
            }
        }
        return templateSynState;
    }

    /**
     * 从数据库再次加载要同步的模板信息。这里需要优化，再次读取数据库逻辑上难理解
     * @author fengj<br>
     *         2015年2月11日 下午5:28:22
     * @param resp 应答消息
     * @param standardQueryPara 规格对应的模板查询参数
     * @return 要同步的模板信息
     * @throws StandardOperationException 规格操作异常
     */
    protected List<StandardSynchronizedState> querySycnTemplateList(RuntimeContext resp,
            StandardQueryPara standardQueryPara) throws StandardOperationException {
        List<StandardSynchronizedState> standardStates;
        try {
            logger.info("开始查询出所有要同步的模板信息!");
            standardStates = ibatisDao.getData("queryVmTemplateSynchronizedState", standardQueryPara);
        } catch (SQLException e) {
            String errorMsg = "查询出所有要同步的模板信息时数据库异常!";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "查询出所有要同步的模板信息时数据库异常!";
            logger.error(CommonStatusCode.OTHER_EXCEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        }
        return standardStates;
    }

    /**
     * 查询规格同步信息中，对应的已经存在的模板信息
     * @author fengj<br>
     *         2015年2月11日 下午3:48:33
     * @param req 规格同步请求
     * @param resp 规格同步应答
     * @return 要同步的规格信息中已经存在的模板信息
     * @throws StandardOperationException 规格操作异常
     */
    protected Map<String, String> queryExsitTmplates(RuntimeContext req, RuntimeContext resp)
            throws StandardOperationException {
        Map<String, String> existTemplateMap = new HashMap<String, String>();
        try {
            logger.info("开始根据规格id查询出所有存在模板的模板信息!");
            String standardId = (String) req.getAttribute("STANDARD_ID");
            List<Template> existTemplateList = ibatisDao.getData("queryTemplateMessage", standardId);
            // 将模板信息list转换成map
            if (null != existTemplateList && existTemplateList.size() > 0) {
                for (Template existTemplate : existTemplateList) {
                    String key = existTemplate.getStandardId() + existTemplate.getOsId();
                    existTemplateMap.put(key, existTemplate.getTemplateId());
                }
            }
        } catch (SQLException e) {
            String errorMsg = "根据规格id查询出所有存在模板的模板信息时数据库异常!";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "根据规格id查询出所有存在模板的模板信息时发生异常!";
            logger.error(CommonStatusCode.OTHER_EXCEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        }
        return existTemplateMap;
    }

    /**
     * 查询需要进行模板同步的信息，包括资源池、分区、进行、规格信息
     * @author fengj<br>
     *         2015年2月11日 下午3:36:52
     * @param resp 应答消息
     * @param standardQueryPara 规格同步的请求参数
     * @return 从数据库中查询到需要同步的规格信息
     * @throws StandardOperationException 规格操作异常
     */
    private List<StandardSynchronizedState> queryWillSyncInfos(RuntimeContext resp, StandardQueryPara standardQueryPara)
            throws StandardOperationException {
        // 查询出要保存到数据库关联表和同步表的信息
        List<StandardSynchronizedState> willSynInfoList;
        try {
            logger.info("开始查询出要保存到数据库关联表和同步表的信息!");
            willSynInfoList = ibatisDao.getData("querySaveDbVMSynchronized", standardQueryPara);
        } catch (SQLException e) {
            String errorMsg = "查询出要保存到数据库关联表和同步表的信息时数据库异常!";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "查询出要保存到数据库关联表和同步表的信息时发生其他异常!";
            logger.error(CommonStatusCode.OTHER_EXCEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        }
        return willSynInfoList;
    }

    /**
     * 拼装要插入同步表及虚拟机镜像关联模板表数据
     * @author fengj<br>
     *         2015年2月11日 下午4:37:20
     * @param saveDbSynInfos
     * @param templateMap
     * @param req
     * @param resp
     * @throws StandardOperationException TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     */
    private void saveTmplateAndSyncInfoToDB(List<StandardSynchronizedState> willSynInfoList,
            Map<String, String> existTemplateMap, RuntimeContext req, RuntimeContext resp)
            throws StandardOperationException {

        // 保存到同步表的集合
        List<StandardSynInfo> standardSyncInsertPoList = new ArrayList<StandardSynInfo>();
        // 保存到关联表的集合
        List<Template> templateInertPoList = new ArrayList<Template>();

        String synchronizePerson = (String) req.getAttribute("SYNCHRONIZE_PERSON");
        String synchronizeTime = (String) req.getAttribute("SYNCHRONIZE_TIME");
        String standardId = (String) req.getAttribute("STANDARD_ID");

        if (null == willSynInfoList) {
            logger.info("规格" + standardId + "要同步的模板信息为NULL,请联系开发人员!");
            return;
        }

        logger.info("开始拼装要同步的模板信息!");
        for (StandardSynchronizedState willSyncTemplateInfo : willSynInfoList) {
            String willSyncTemplateKey = willSyncTemplateInfo.getStandardId() + willSyncTemplateInfo.getOsId();
            if ("".equals(existTemplateMap.get(willSyncTemplateKey))
                    || !existTemplateMap.containsKey(willSyncTemplateKey)) {
                transactonGen.setIbatisDAO(ibatisDao);
                String templateId = transactonGen.generatorVMItemId();
                Template newTemplate = new Template();
                newTemplate.setStandardId(willSyncTemplateInfo.getStandardId());
                newTemplate.setOsId(willSyncTemplateInfo.getOsId());
                newTemplate.setTemplateId(templateId);
                newTemplate.setCreateUser(synchronizePerson);
                newTemplate.setCreateTime(synchronizeTime);
                newTemplate.setResourcePoolId(willSyncTemplateInfo.getResourcePoolId());
                newTemplate.setStatus("1");
                templateInertPoList.add(newTemplate);
                existTemplateMap.put(newTemplate.getStandardId() + newTemplate.getOsId(), newTemplate.getTemplateId());
                // 掉用封装模板同步实体
                StandardSynInfo standardSynInfo = this.saveStandardSynInfo(req, existTemplateMap, willSyncTemplateInfo);
                standardSyncInsertPoList.add(standardSynInfo);
            } else {
                StandardSynInfo standardSynInfo = this.saveStandardSynInfo(req, existTemplateMap, willSyncTemplateInfo);
                standardSyncInsertPoList.add(standardSynInfo);
            }
        }

        try {
            logger.info("开始保存数据到关联表和同步表!");
            this.insertInfos(templateInertPoList, standardSyncInsertPoList);
        } catch (SQLException e) {
            String errorMsg = "保存数据到关联表和同步表时数据库异常!";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);

        } catch (Exception e) {
            String errorMsg = "保存数据到关联表和同步表时数据库异常!";
            logger.error(CommonStatusCode.OTHER_EXCEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
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

        // 这几项没有什么作用，暂时设置默认值
        String osDiskType = req.getAttribute("OS_DISK_TYPE") == null ? this.defaultOsDiskType : (String) req
                .getAttribute("OS_DISK_TYPE");
        String vEthAdaNum = req.getAttribute("VETH_ADA_NUM") == null ? this.defaultVethAdaNum : (String) req
                .getAttribute("VETH_ADA_NUM");
        String vSCSIAdaNum = req.getAttribute("VSCSI_ADA_NUM") == null ? this.defaultVscsiAdaNum : (String) req
                .getAttribute("VSCSI_ADA_NUM");
        String vFCHBANum = req.getAttribute("VFC_HBA_NUM") == null ? this.defaultVfcHbaNum : (String) req
                .getAttribute("VFC_HBA_NUM");

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
        vmReq.setOsDiskType(osDiskType);
        vmReq.setvEthAdaNum(vEthAdaNum);
        vmReq.setVSCSIAdaNum(vSCSIAdaNum);
        vmReq.setVFCHBANum(vFCHBANum);
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

    /**
     * 获取规格同步请求中的所有分区进行信息，并封装到StandardQueryPara中
     * @param req 请求信息
     * @return StandardQueryPara 模板查询
     */
    private StandardQueryPara assmbleTemplatePara(RuntimeContext req) {

        // 要同步的规格ID
        String standardId = (String) req.getAttribute("STANDARD_ID");

        // 要同步的资源池分区信息
        @SuppressWarnings("unchecked")
        List<Map<String, String>> syncOSlInfoMaps = (List<Map<String, String>>) req.getAttribute("RESOURCE_POOLS");

        StandardQueryPara para = new StandardQueryPara();
        para.setStandardId(standardId);
        List<ResourcePoolInfoPara> syncOSlInfoList = new ArrayList<ResourcePoolInfoPara>();
        for (Map<String, String> syncResourcePoolPartMap : syncOSlInfoMaps) {
            ResourcePoolInfoPara syncOSlInfo = new ResourcePoolInfoPara();
            syncOSlInfo.setResourcePoolId(syncResourcePoolPartMap.get("RES_POOL_ID"));
            syncOSlInfo.setResourcePoolPartId(syncResourcePoolPartMap.get("RES_POOL_PART_ID"));
            syncOSlInfo.setOsId(syncResourcePoolPartMap.get("OS_ID"));
            syncOSlInfoList.add(syncOSlInfo);
        }
        para.setResourceInfos(syncOSlInfoList);
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
     * @param 应答消息
     * @return 要返回到management的消息
     * @throws StandardOperationException 规格操作异常
     */
    private List<Map<String, String>> updateSynState(List<TemplateSynchronizeResult> templateSynState,
            RuntimeContext resp) throws StandardOperationException {

        List<Map<String, String>> results = new ArrayList<Map<String, String>>();

        try {
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
        } catch (SQLException e) {
            String errorMsg = "保存资源池返回同步状态时更新数据库异常!";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);

        } catch (Exception e) {
            String errorMsg = "保存资源池返回同步状态时更新数据库异常!";
            logger.error(CommonStatusCode.OTHER_EXCEPTION, errorMsg, e);
            assmbleResult(resp, null, "FAIL");
            throw new StandardOperationException(errorMsg, e);
        }

        return results;
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
