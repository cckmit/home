package com.neusoft.mid.cloong.web.page.system.config.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourcePartInfo;

/**
 * 资源池删除
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 下午02:32:35
 */
public class ResourcePartDeleteService extends BaseService {

    /**
     * 资源池分区删除
     * @param resPoolId
     * @param resPoolPartId
     * @param userId
     * @return
     * @throws SQLException
     */
    public void resPartDel(String resPoolId, String resPoolPartId, String userId)
            throws SQLException {
        ResourcePartInfo resourcePartInfo = new ResourcePartInfo();
        resourcePartInfo.setResPoolId(resPoolId);// 资源池编码
        resourcePartInfo.setResPoolPartId(resPoolPartId);// 资源池分区编码
        resourcePartInfo.setUpdateUser(userId);// 更新人id
        resourcePartInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 更新时间
        resourcePartInfo.setStatus(ResPoolStatus.DELTE);// 更新状态

        ibatisDAO.updateData("updateResPartStatusDel", resourcePartInfo);
    }

    /**
     * 查询资源池编码 资源池分区编码 是否被同步规格使用
     * @param resPoolId 资源池编码
     * @param resPoolPartId 资源池分区编码
     * @return 如果被使用 返回被使用数量 如果不存在返回 0
     * @throws SQLException
     */
    public int countCaseByResIdAndResPartId(String resPoolId, String resPoolPartId)
            throws SQLException {
        ResourcePartInfo resourcePartInfo = new ResourcePartInfo();
        resourcePartInfo.setOldResPoolId(resPoolId);// 查询时使用 资源池编码
        resourcePartInfo.setResPoolPartId(resPoolPartId);// 资源池分区编码
        //带有查询条件 status!=1 status!=3  状态不等于发送失败 和 不可用 的
        return ((Integer) ibatisDAO.getSingleRecord("countStandardSynByResIdAndPartId", resourcePartInfo))
                .intValue();
    }

}
