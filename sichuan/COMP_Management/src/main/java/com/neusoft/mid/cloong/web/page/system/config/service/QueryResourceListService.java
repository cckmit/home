package com.neusoft.mid.cloong.web.page.system.config.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;

/**
 * 查询资源池列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 上午10:07:54
 */
public class QueryResourceListService extends BaseService {

    /**
     * 查询资源池
     * @return 资源池列表
     * @throws SQLException
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    public List<ResourceInfo> queryResourceInfos(String selectSqlKey) throws SQLException, UnsupportedEncodingException {
        List<ResourceInfo> resourceInfos = (List<ResourceInfo>) ibatisDAO.getData(
                selectSqlKey, null);
        for (ResourceInfo resourceInfo : resourceInfos) {
            resourceInfo.setUserPwd(Base64.decode(resourceInfo.getUserPwd()));
        }
        return resourceInfos;
    }
}
