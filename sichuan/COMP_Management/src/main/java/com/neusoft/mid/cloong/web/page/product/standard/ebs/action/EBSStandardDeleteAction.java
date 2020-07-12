/*******************************************************************************
 * @(#)VMStandardDeleteAction.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.ebs.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.resourceProxy.standard.common.StandardSynchronizeDeleteResp;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.base.BaseStandardDeleteAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘规格删除
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-12 下午10:12:48
 */
public class EBSStandardDeleteAction extends BaseStandardDeleteAction {

    private static final long serialVersionUID = -1441697968480674248L;

    private static LogService logger = LogService.getLogger(EBSStandardDeleteAction.class);

    /**
     * 规格ID
     */
    private String standardId;
    
    /**
     * 可用的模板是否可以删除   0:不可删除    1:可以删除.
     */
    private String templateIsUseDel;

    /**
     * 设置templateIsUseDel字段数据
     * @param templateIsUseDel The templateIsUseDel to set.
     */
    public void setTemplateIsUseDel(String templateIsUseDel) {
        this.templateIsUseDel = templateIsUseDel;
    }

    public String execute() {
        int standardNumInUse = 0;
        try {
            standardNumInUse = (Integer) ibatisDAO.getSingleRecord("selectItembystandardId",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "删除虚拟机备份规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "删除虚拟机备份规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        }
        if (standardNumInUse > 0) {
            resultPath = ConstantEnum.INUSE.toString();
            logger.info("ID为" + standardId + "的规格正在使用，不允许删除");
            return ConstantEnum.SUCCESS.toString();
        }

        if (isIntermediateState(standardId)) {
            return ConstantEnum.SUCCESS.toString();
        }
        
        //查询规格同步状态为：可用的数量
        int usableNum = getCountSynUsable(standardId);
        if(usableNum==-1){
            resultPath = ConstantEnum.ERROR.toString();
            logger.info("ID为" + standardId + "的规格查询规格可用状态数量失败！");
            return ConstantEnum.SUCCESS.toString();
        }
        if("0".equals(templateIsUseDel)){
            if(usableNum>0){
                resultPath = ConstantEnum.ENABLED.toString();
                logger.info("ID为" + standardId + "的规格资源池是可用状态，不允许删除");
                return ConstantEnum.SUCCESS.toString();
            }
        }
        
        // 调用统计是否需要调用同步删除接口
        boolean flage = false;
        try {
            flage = isCallSynchro(standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟机备份规格失败！原因：查询规格是否被同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟机备份规格失败！原因：查询规格是否被同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        }

        // 判断是否需要调用同步删除接口
        if (flage) {
            StandardSynchronizeDeleteResp standardSynchronizeDeleteResps = standardSynchronizeDel
                    .synchronizeDeleteStandard(standardId);
            if (standardSynchronizeDeleteResps == null) {
                // 调用删除失败！
                resultPath = ConstantEnum.ERROR.toString();
            } else {
                // 接口删除成功，调用更新删除方法！
                updateDataBatch(standardId, standardSynchronizeDeleteResps);
            }
        } else {
            updateDataStandard(standardId);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * updateData 更新数据(删除虚拟硬盘规格信息\删除同步表信息)
     * @param standardId 规格ID
     * @param standardSynchronizeDeleteResps 调用删除规格接口返回信息
     */
    private void updateDataBatch(String standardId,
            StandardSynchronizeDeleteResp standardSynchronizeDeleteResps) {

        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        batchVOs.add(new BatchVO("MOD", "deleteEbsStandard", standardId));
        if (standardSynchronizeDeleteResps.getFailure().size() > 0) {
            Map<String, Object> updateMaps = new HashMap<String, Object>();
            updateMaps.put("StandardSynchronizeResult", standardSynchronizeDeleteResps
                    .getFailure());// 资源池 资源池分区
            batchVOs.add(new BatchVO("MOD", "deleteSynFailure", updateMaps));
        }
        if (standardSynchronizeDeleteResps.getSuccess().size() > 0) {
            Map<String, Object> updateMaps = new HashMap<String, Object>();
            updateMaps.put("StandardSynchronizeResult", standardSynchronizeDeleteResps
                    .getSuccess());// 资源池 资源池分区
            batchVOs.add(new BatchVO("MOD", "deleteSynSuccess", updateMaps));
        }
        
        // 判断是否需要调用删除同步表数据
        boolean flage = false;
        try {
            flage = isCallSynchroUnSynchroData(standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟硬盘规格失败！原因：调用同步删除接口后，查询规格是否存在未同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟硬盘规格失败！原因：调用同步删除接口后，查询规格是否存在未同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
        //判断是否需要调用 删除同步表中的数据
        if(flage){
            batchVOs.add(new BatchVO("MOD", "deleteStandardSynSuccess", standardId));
        }

        try {
            ibatisDAO.updateBatchData(batchVOs);
            logger.info("删除虚拟硬盘规格成功！");
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "删除虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "删除虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
    }

    /***
     * updateDataStandard 未同步过,删除虚拟硬盘规格
     * @param standardId 规格ID
     */
    public void updateDataStandard(String standardId) {
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        batchVOs.add(new BatchVO("MOD", "deleteEbsStandard", standardId));
        
        // 判断是否需要调用删除同步表数据
        boolean flage = false;
        try {
            flage = isCallSynchroUnSynchroData(standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟硬盘规格失败！原因：查询规格是否存在未同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟硬盘规格失败！原因：查询规格是否存在未同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
        //判断是否需要调用 删除同步表中的数据
        if(flage){
            batchVOs.add(new BatchVO("MOD", "deleteStandardSynSuccess", standardId));
        }
        
        try {
            ibatisDAO.updateBatchData(batchVOs);
            logger.info("删除虚拟硬盘规格成功！");
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "删除虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "删除虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
}
