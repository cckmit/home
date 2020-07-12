package com.neusoft.mid.cloong.web.page.product.standard.ebs.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.generator.SequenceGenerator;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.ebs.EBSStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.ebs.EBSStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.ebs.EBSStandardSynchronizeResult;
import com.neusoft.mid.cloong.web.page.product.standard.ebs.info.EBSStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.vm.service.SynchroResPoolBaseService;

/**
 * 虚拟硬盘规格同步资源池
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-20 上午08:55:10
 */
public class EBSSynchroResPoolService extends SynchroResPoolBaseService {

    private SequenceGenerator sequenceGenerator;

    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * 初始化接口数据信息
     * @param standardId 规格id
     * @param resourcePoolInfos 要同步的资源池、资源池分区列表
     * @return 返回req（组装后接口数据）
     * @throws SQLException
     */
    public EBSStandardSynchronizeReq initReq(String standardId,
            List<ResourcePoolInfo> resourcePoolInfos) throws SQLException {
        EBSStandardSynchronizeReq req = new EBSStandardSynchronizeReq();
        EBSStandardInfo ebsStandardInfo = (EBSStandardInfo) ibatisDAO.getSingleRecord(
                "queryEBSStandInfo", standardId);// 查询规格信息

        String templateId = (String) ibatisDAO.getSingleRecord("queryHaveTemplateIdByStandardId",
                standardId);// 查询规格信息

        if (null == templateId || templateId.isEmpty()) {
            sequenceGenerator.setIbatisDAO(ibatisDAO);
            templateId = sequenceGenerator.generatorVMItemId("BS");// 生成虚拟块与资源池对应ID
        }
        // 判断templateId是否为空
        if (null == templateId || templateId.isEmpty()) {
            return null;
        }
        req.setTemplateId(templateId);
        req.setStandardName(ebsStandardInfo.getStandardName());// 模板名称
        req.setCreateTime(ebsStandardInfo.getCreateTime());// 创建时间
        req.setCreator(ebsStandardInfo.getCreateUser());// 创建者
        req.setMeasureMode("Duration");// TODO 计量方式
        req.setStandardDesc(ebsStandardInfo.getDescription());// 规格描述
        req.setStandardId(ebsStandardInfo.getStandardId());// 规格id
        req.setStorageSize(String.valueOf(ebsStandardInfo.getDiscSize()));// 硬盘空间
        req.setResourceType(ebsStandardInfo.getResourceType());// 硬盘类型
        req.setResourceInfos(resourcePoolInfos);
        return req;
    }

    /**
     * 根据返回值更新虚拟机规格同步状态表状态
     * @param resourcePoolInfos 要同步的资源池分区
     * @param resp 资源池响应
     * @param standardId 规格id
     * @return 接口返回值不为空返回发送失败资源池分区列表 接口返回值为空，返回null
     * @throws SQLException
     */
    public List<EBSStandardSynchronizeResult> callSynImpl(List<ResourcePoolInfo> resourcePoolInfos,
            EBSStandardSynchronizeResp resp, String standardId) throws SQLException {
        List<EBSStandardSynchronizeResult> results;

        List<BatchVO> batchList = new ArrayList<BatchVO>();

        if (resp != null) {
            // 发送成功更新条件
            Map<String, Object> insertLimit = new HashMap<String, Object>();
            insertLimit.put("standardId", standardId);// 规格id
            insertLimit.put("VMStandardSynchronizeResult", resp.getSuccess());// 资源池 资源池分区
            // 发送失败更新条件
            Map<String, Object> updateLimit = new HashMap<String, Object>();
            updateLimit.put("standardId", standardId);// 规格id
            updateLimit.put("VMStandardSynchronizeResult", resp.getFailure());// 资源池 资源池分区

            if (resp.getSuccess().size() > 0) {
                batchList.add(new BatchVO(BatchVO.OPERATION_MOD, "updateStandardSynSuccess",
                        insertLimit));
                // ibatisDAO.updateData("updateStandardSynSuccess", insertLimit);// 插入同步表
                // ibatisDAO.updateData("insertVmStandardSynLog", insertLimit);// TODO 缺少记录表 插入记录表
            }
            if (resp.getFailure().size() > 0) {
                batchList.add(new BatchVO(BatchVO.OPERATION_MOD, "updateStandardSynFail",
                        updateLimit));
                // ibatisDAO.updateData("updateStandardSynFail", updateLimit);// 更新同步表
                batchList.add(new BatchVO(BatchVO.OPERATION_MOD, "updateStandardModifySynFail",
                        updateLimit));
                // ibatisDAO.updateData("updateStandardModifySynFail", updateLimit);// 更新同步表状态为修改：
                // 等待再同步

                // ibatisDAO.updateData("insertVmStandardSynLog", updateLimit);// TODO 缺少记录表 插入记录表
            }
            results = resp.getFailure();
        } else {
            // 全部失败
            Map<String, Object> updateLimit = new HashMap<String, Object>();
            updateLimit.put("standardId", standardId);
            updateLimit.put("VMStandardSynchronizeResult", resourcePoolInfos);
            batchList.add(new BatchVO(BatchVO.OPERATION_MOD, "updateAllStandardSynFail",
                    updateLimit));
            // ibatisDAO.updateData("updateAllStandardSynFail", updateLimit);// 更新同步表

            batchList.add(new BatchVO(BatchVO.OPERATION_MOD, "updateAllStandardModifySynFail",
                    updateLimit));
            // ibatisDAO.updateData("updateAllStandardModifySynFail", updateLimit);// 更新同步表状态为修改：
            // 等待再同步
            results = null;
        }

        ibatisDAO.updateBatchData(batchList);

        return results;
    }
}
