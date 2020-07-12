package com.neusoft.mid.cloong.web.page.product.standard.vmbak.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.generator.SequenceGenerator;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeResult;
import com.neusoft.mid.cloong.web.page.product.standard.vm.service.SynchroResPoolBaseService;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.info.VMBAKStandardInfo;

/**
 * 虚拟机备份规格同步资源池
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-20 上午08:55:10
 */
public class VMBAKSynchroResPoolService extends SynchroResPoolBaseService {
    
    /**
     * 序列号生成工具类
     */
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
     * @throws SQLException SQL异常
     */
    public VMBAKStandardSynchronizeReq initReq(String standardId,
            List<ResourcePoolInfo> resourcePoolInfos) throws SQLException {
        VMBAKStandardSynchronizeReq req = new VMBAKStandardSynchronizeReq();
        
        Map<String, String> queryDBInfo = genQueryDBInfoByStandardId(standardId);
        
        VMBAKStandardInfo vmbakStandardInfo = (VMBAKStandardInfo) ibatisDAO.getSingleRecord(
                "queryVMBAKStandardDetail", queryDBInfo);// 查询规格信息

        String templateId = (String) ibatisDAO.getSingleRecord("queryHaveTemplateIdByStandardId",
                standardId);// 查询规格信息
        
        if(null == templateId || templateId.isEmpty()){
            sequenceGenerator.setIbatisDAO(ibatisDAO);
            templateId = sequenceGenerator.generatorVMItemId("VMBK");//生成虚拟块与资源池对应ID
        }
        //判断templateId是否为空
        if(null == templateId ||templateId.isEmpty()){
            return null;
        }
        req.setTemplateId(templateId);
        req.setStandardName(vmbakStandardInfo.getStandardName());//规格名称
        req.setCreateTime(vmbakStandardInfo.getCreateTimeStr());// 创建时间
        req.setCreator(vmbakStandardInfo.getCreateUser());// 创建者
        req.setMeasureMode("Duration");// TODO 计量方式
        req.setStandardDesc(vmbakStandardInfo.getDescription());// 规格描述
        req.setStandardId(vmbakStandardInfo.getStandardId());// 规格id
        req.setSpiceSize(String.valueOf(vmbakStandardInfo.getSpaceSize()));// 备份存储空间
        req.setResourceInfos(resourcePoolInfos);
        return req;
    }

    /**
     * genQueryDBInfoByStandardId 生成数据库查询条件
     * @param standardId    规格ID
     * @return 返回查询条件信息
     */
    private Map<String, String> genQueryDBInfoByStandardId(String standardId) {
        Map<String,String> queryDBInfo = new HashMap<String,String>();
        queryDBInfo.put("standardId", standardId);
        return queryDBInfo;
    }

    /**
     * 根据返回值更新虚拟机规格同步状态表状态
     * @param resourcePoolInfos 要同步的资源池分区
     * @param resp 资源池响应
     * @param standardId 规格id
     * @return 接口返回值不为空返回发送失败资源池分区列表 接口返回值为空，返回null
     * @throws SQLException SQL异常
     */
    public List<VMBAKStandardSynchronizeResult> callSynImpl(List<ResourcePoolInfo> resourcePoolInfos,
            VMBAKStandardSynchronizeResp resp, String standardId) throws SQLException {
        List<VMBAKStandardSynchronizeResult> results;
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
                ibatisDAO.updateData("updateStandardSynSuccess", insertLimit);// 插入同步表
                // ibatisDAO.updateData("insertVmStandardSynLog", insertLimit);// TODO 缺少记录表 插入记录表
            }
            if (resp.getFailure().size() > 0) {
                ibatisDAO.updateData("updateStandardSynFail", updateLimit);// 更新同步表
                
                ibatisDAO.updateData("updateStandardModifySynFail", updateLimit);// 更新同步表状态为修改： 等待再同步
                // ibatisDAO.updateData("insertVmStandardSynLog", updateLimit);// TODO 缺少记录表 插入记录表
            }
            results = resp.getFailure();
        } else {
            // 全部失败
            Map<String, Object> updateLimit = new HashMap<String, Object>();
            updateLimit.put("standardId", standardId);
            updateLimit.put("VMStandardSynchronizeResult", resourcePoolInfos);
            ibatisDAO.updateData("updateAllStandardSynFail", updateLimit);// 更新同步表
            
            ibatisDAO.updateData("updateAllStandardModifySynFail", updateLimit);// 更新同步表状态为修改： 等待再同步
            results = null;
        }
        return results;
    }
}
