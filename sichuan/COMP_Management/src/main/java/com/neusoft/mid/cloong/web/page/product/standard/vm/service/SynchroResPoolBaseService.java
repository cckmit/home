package com.neusoft.mid.cloong.web.page.product.standard.vm.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardResListInfo;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardSynInfo;

/**
 * 规格同步资源池共通方法
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-20 上午08:55:10
 */
public class SynchroResPoolBaseService extends BaseService {


    /**
     * 生成插入batchVo
     * @param standardSynInfos 要插入虚拟机规格同步资源池对象集合
     * @param templateId 规格对应资源池模板ID
     * @return 要插入 List<BatchVO>
     */
    private List<BatchVO> initBatchVo(List<StandardSynInfo> standardSynInfos,String templateId) {
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        for (StandardSynInfo standardSynInfo : standardSynInfos) {
            standardSynInfo.setTemplateId(templateId);
            BatchVO batchVO = new BatchVO("ADD", "insertSyn", standardSynInfo);
            batchVOs.add(batchVO);
        }
        return batchVOs;
    }

    /**
     * 生成插入对象 （StandardSynInfo 虚拟机规格同步表）
     * @param vmStandardResListInfos 要同步的资源池、资源池分区
     * @param userId 用户id
     * @param standardType 规格类型
     * @return
     */
    private List<StandardSynInfo> initInserVmSyn(List<StandardResListInfo> vmStandardResListInfos,
            String userId, String standardType) {
        List<StandardSynInfo> standardSynInfos = new ArrayList<StandardSynInfo>();
        if (vmStandardResListInfos != null) {
            for (StandardResListInfo vmStandardResListInfo : vmStandardResListInfos) {
                StandardSynInfo standardSynInfo = new StandardSynInfo();
                standardSynInfo.setResPoolId(vmStandardResListInfo.getResPoolId());
                standardSynInfo.setResPoolPartId(vmStandardResListInfo.getResPoolPartId());
                standardSynInfo.setStandardId(vmStandardResListInfo.getStandardId());
                standardSynInfo.setStandardType(standardType);// 规格类型
                standardSynInfo.setSynTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                standardSynInfo.setSynUser(userId);
                standardSynInfos.add(standardSynInfo);
            }
        }
        return standardSynInfos;
    }

    /**
     * 批量插入虚拟机规格资源池同步表
     * @param resourcePoolInfos 要同步的资源池、资源分区列表
     * @param queryInsertSql 查询出需要插入同步表的规格
     * @param userId 用户id
     * @param standardId 虚拟机规格id
     * @param standardType 规格类型(0虚拟机1物理机...5虚拟硬盘等[与同步表中的分类相同])
     * @param templateId 规格对应资源池模板ID
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public void saveStandardSynInfo(List<ResourcePoolInfo> resourcePoolInfos,
            String queryInsertSql, String userId, String standardId, String standardType,String templateId)
            throws SQLException {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("standardId", standardId);
        query.put("resourcePoolInfos", resourcePoolInfos);
        List<StandardResListInfo> vmStandardResListInfos = (List<StandardResListInfo>) ibatisDAO
                .getData(queryInsertSql, query);// 查询出需要插入同步表的规格
        List<StandardSynInfo> standardSynInfos = initInserVmSyn(vmStandardResListInfos, userId,
                standardType);// 初始化插入虚拟机规格对象
        List<BatchVO> batchVOs = initBatchVo(standardSynInfos,templateId);// 初始化插入数据batchVo
        ibatisDAO.updateBatchData(batchVOs);// 插入规格资源池同步表数据
    }
}
