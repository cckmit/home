package com.neusoft.mid.cloong.web.page.product.standard.pm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;

/**
 * 
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:08:29
 */
public class PMStandardListService extends BaseService {

    /**
     * 
     * queryPMStandard 
     * @param queryPMStandardInfo
     * @param querySqlKey
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<PMStandardInfo> queryPMStandard(PMStandardInfo queryPMStandardInfo,String querySqlKey)
            throws SQLException {
        List<PMStandardInfo> pmStandardInfos = null;
        pmStandardInfos = (List<PMStandardInfo>) ibatisDAO.getData(querySqlKey,
                queryPMStandardInfo);
        return pmStandardInfos;
    }
}
