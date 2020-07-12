package com.neusoft.mid.cloong.web.page.product.standard.vm.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeResult;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;

/**
 * 虚拟机规格同步资源池
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-20 上午08:55:10
 */
public class VMSynchroResPoolService extends SynchroResPoolBaseService {
    /**
     * 根据返回值更新虚拟机规格同步状态表状态
     * @param resourcePoolInfos 要同步的资源池分区
     * @param resp 资源池响应
     * @param standardId 规格id
     * @return 接口返回值不为空返回发送失败资源池分区列表 接口返回值为空，返回null
     * @throws SQLException
     */
    public List<VMStandardSynchronizeResult> callSynImpl(List<ResourcePoolInfo> resourcePoolInfos,
            VMStandardSynchronizeResp resp, String standardId) throws SQLException {
        List<VMStandardSynchronizeResult> results;
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        if (resp != null) {
            // 发送成功更新条件
            Map<String, Object> insertLimit = new HashMap<String, Object>();
            insertLimit.put("standardId", standardId);// 规格id
            insertLimit.put("VMStandardSynchronizeResult", resp.getSuccess());// 资源池 资源池分区
            // 发送失败更新条件
            Map<String, Object> updateLimit = new HashMap<String, Object>();
            updateLimit.put("standardId", standardId);// 规格id
            updateLimit.put("VMStandardSynchronizeResult", resp.getFailure());// 资源池 资源池分区
            if(resp.getSuccess().size()>0){
                batchVOs.add(new BatchVO("MOD", "updateStandardSynSuccess", insertLimit));
                // ibatisDAO.updateData("insertVmStandardSynLog", insertLimit);// TODO 缺少记录表 插入记录表
            }
            if(resp.getFailure().size()>0){
                batchVOs.add(new BatchVO("MOD", "updateStandardSynFail", updateLimit));// 更新同步表
                batchVOs.add(new BatchVO("MOD", "updateStandardModifySynFail", updateLimit));// 更新同步表状态为修改： 等待再同步
                // ibatisDAO.updateData("insertVmStandardSynLog", updateLimit);// TODO 缺少记录表 插入记录表
            }
            results = resp.getFailure();
        } else {
            // 全部失败
            Map<String, Object> updateLimit = new HashMap<String, Object>();
            updateLimit.put("standardId", standardId);
            updateLimit.put("VMStandardSynchronizeResult", resourcePoolInfos);
            batchVOs.add(new BatchVO("MOD", "updateAllStandardSynFail", updateLimit));// 更新同步表
            batchVOs.add(new BatchVO("MOD", "updateAllStandardModifySynFail", updateLimit));// 更新同步表状态为修改： 等待再同步
            results = null;
        }
        ibatisDAO.updateBatchData(batchVOs);
        return results;
    }

    /**
     * 初始化接口数据信息
     * @param standardId 规格id
     * @param resourcePoolInfos 要同步的资源池、资源池分区列表
     * @return 返回req（组装后接口数据）
     * @throws SQLException
     */
    public VMStandardSynchronizeReq initReq(String standardId,
            List<ResourcePoolInfo> resourcePoolInfos) throws SQLException {
        VMStandardSynchronizeReq req = new VMStandardSynchronizeReq();
        VMStandardInfo vmStandardInfo = (VMStandardInfo) ibatisDAO.getSingleRecord(
                "queryVmStandInfo", standardId);// 查询规格信息
        req.setCpuNum(String.valueOf(vmStandardInfo.getCpuNum()));// cpu数量
        req.setCreateTime(vmStandardInfo.getCreateTime());// 创建时间
        req.setCreator(vmStandardInfo.getCreateUser());// 创建者
        req.setMeasureMode("Duration");//计量方式
        req.setMemorySize(String.valueOf(vmStandardInfo.getRamSize()));// 内存大小
        req.setStandardDesc(vmStandardInfo.getDescription());// 规格描述
        req.setStandardId(vmStandardInfo.getStandardId());// 规格id
        req.setStorageSize(String.valueOf(vmStandardInfo.getDiscSize()));// 硬盘空间
        req.setResourceInfos(resourcePoolInfos);
        return req;
    }
}
