package com.neusoft.mid.cloong.web.page.system.config.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourcePartInfo;

/**
 * 查询资源池分区列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 下午03:36:33
 */
public class QueryResourcePartListService extends BaseService {

    /**
     * 查询资源池分区
     * @return 资源池分区列表
     * @throws SQLException  sql异常
     */
    @SuppressWarnings("unchecked")
    public List<ResourcePartInfo> queryResourcePartInfos() throws SQLException {
        //资源池分区与资源池两表关联查询。关联查询出资源池名称
        List<ResourcePartInfo> resourcePartInfos = (List<ResourcePartInfo>) ibatisDAO.getData(
                "queryResourcePartInfos", null);
        return resourcePartInfos;
    }
}
