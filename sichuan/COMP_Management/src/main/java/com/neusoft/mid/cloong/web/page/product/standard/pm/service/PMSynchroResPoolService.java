package com.neusoft.mid.cloong.web.page.product.standard.pm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.generator.SequenceGenerator;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.pm.PMStandardSynchronizeReq;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.vm.service.SynchroResPoolBaseService;

/**
 * 物理机规格同步资源池
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-20 上午08:55:10
 */
public class PMSynchroResPoolService extends SynchroResPoolBaseService {
    
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
    public PMStandardSynchronizeReq initReq(String standardId,
            List<ResourcePoolInfo> resourcePoolInfos, String synchronizePerson, String synchronizeTime,String[] osInfos) throws SQLException {
        PMStandardSynchronizeReq req = new PMStandardSynchronizeReq();
        PMStandardInfo pmStandardInfo = (PMStandardInfo) ibatisDAO.getSingleRecord(
                "queryPMStandInfo", standardId);// 查询规格信息

        /*String templateId = (String) ibatisDAO.getSingleRecord("queryHaveTemplateIdByStandardId",
                standardId);// 查询规格信息
        
        if(null == templateId || templateId.isEmpty()){
            sequenceGenerator.setIbatisDAO(ibatisDAO);
            templateId = sequenceGenerator.generatorVMItemId("SRV");//生成物理机与资源池对应ID
        }
        //判断templateId是否为空
        if(null == templateId ||templateId.isEmpty()){
            return null;
        }
        req.setTemplateId(templateId);*/
        req.setCreateTime(pmStandardInfo.getCreateTime());// 创建时间
        req.setCreator(pmStandardInfo.getCreateUser());// 创建者
        req.setMeasureMode("Duration");// TODO 计量方式未确定默认按时长计量
        req.setStandardDesc(pmStandardInfo.getDescription());// 规格描述
        req.setStandardId(pmStandardInfo.getStandardId());// 规格id
        req.setStandardName(pmStandardInfo.getStandardName());// 规格名称
        req.setServerType(pmStandardInfo.getPmTypeName());//物理机类型名称
        req.setCpuType(pmStandardInfo.getCpuType());// cpu类型
        req.setMemorySize(String.valueOf(pmStandardInfo.getRamSize()));// 内存大小
        req.setStorageSize(String.valueOf(pmStandardInfo.getDiscSize()));// 硬盘空间
        req.setEthadaNum(pmStandardInfo.getEthadaNum());//网卡个数
        req.setEthadaType(pmStandardInfo.getEthadaType());//网卡的规格 
        req.setScsiAdaNum(pmStandardInfo.getScsiAdaNum());//ISCSI卡个数
        req.setHbaNum(pmStandardInfo.getHbaNum());//HBA个数
        req.setHbaType(pmStandardInfo.getHbaType());//HBA规格
        req.setSynchronizePerson(synchronizePerson);// 同步人
        req.setSynchronizeTime(synchronizeTime);// 同步时间
        req.setResourceInfos(resourcePoolInfos);
        req.setOsInfos(osInfos);//镜像信息
        return req;
    }
}
