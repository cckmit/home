package com.neusoft.mid.cloong.web.page.system.config.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;

/**
 * 资源池删除
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 下午02:32:35
 */
public class ResourceDeleteService extends BaseService {

    /**
     * 资源池删除操作
     * @param selectSqlKey 查询验证是否存在分区sql
     * @param resPoolId 操作资源池id
     * @param userId 操作用户id
     * @throws SQLException
     */
    public int resourceDelete(String selectSqlKey, String resPoolId, String userId)
            throws SQLException {
        int num = (Integer) ibatisDAO.getSingleRecord(selectSqlKey, resPoolId);
        if (num != 0) {
            return -1;
        }
        ResourceInfo resourceInfo = new ResourceInfo();
        resourceInfo.setResPoolId(resPoolId);// 资源id
        resourceInfo.setUpdateUser(userId);// 更新人id
        resourceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 更新时间
        resourceInfo.setStatus(ResPoolStatus.DELTE);// 更新状态
        return ibatisDAO.updateData("updateResourceStatus", resourceInfo);
    }

}
