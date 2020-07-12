package com.neusoft.mid.cloong.web.page.product.standard.vm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeReq;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;

/**
 * 虚拟机规格同步资源池
 * @author <a href="mailto:he.jf@neusoft.com">hejunfeng </a>
 * @version $Revision 1.1 $ 2014-1-15 上午08:55:10
 */
public class VMSynchroResPoolTemplateService extends SynchroResPoolBaseService {

    /**
     * 初始化接口数据信息
     * @param standardId 规格id
     * @param resourcePoolInfos 要同步的资源池、资源池分区列表
     * @return 返回req（组装后接口数据）
     * @throws SQLException
     */
    public VMStandardSynchronizeReq initReq(String standardId,List<ResourcePoolInfo> resourcePoolInfos,String synchronizePerson, String synchronizeTime) throws SQLException {
        VMStandardSynchronizeReq req = new VMStandardSynchronizeReq();
        VMStandardInfo vmStandardInfo = (VMStandardInfo) ibatisDAO.getSingleRecord(
                "queryVmStandInfo", standardId);// 查询规格信息
        req.setCpuNum(String.valueOf(vmStandardInfo.getCpuNum()));// cpu数量
        req.setCreateTime(vmStandardInfo.getCreateTime());// 创建时间
        req.setCreator(vmStandardInfo.getCreateUser());// 创建者
        req.setMeasureMode("Duration");// TODO 计量方式未确定默认按时长计量
        req.setMemorySize(String.valueOf(vmStandardInfo.getRamSize()));// 内存大小
        req.setStandardDesc(vmStandardInfo.getDescription());// 规格描述
        req.setStandardId(vmStandardInfo.getStandardId());// 规格id
        req.setStandardName(vmStandardInfo.getStandardName());// 规格名称
        req.setStorageSize(String.valueOf(vmStandardInfo.getDiscSize()));// 硬盘空间
        req.setSynchronizePerson(synchronizePerson);// 同步人
        req.setSynchronizeTime(synchronizeTime);// 同步时间
        req.setResourceInfos(resourcePoolInfos);
        return req;
    }
}
