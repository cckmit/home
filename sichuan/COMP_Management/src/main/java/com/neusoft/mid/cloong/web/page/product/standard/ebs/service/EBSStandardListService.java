package com.neusoft.mid.cloong.web.page.product.standard.ebs.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.standard.ebs.info.EBSStandardInfo;

/**
 * 通过条件查询虚拟硬盘资源规格列表Service（条件包括：硬盘大小，是否条目使用,资源规格名称,资源规格ID）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 上午11:17:21
 */
public class EBSStandardListService extends BaseService {

    /**
     * 查询虚拟硬盘资源规格
     * @param queryVMStandardInfo
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<EBSStandardInfo> queryEBSStandard(EBSStandardInfo queryVMStandardInfo,String querySqlKey)
            throws SQLException {
        List<EBSStandardInfo> ebsStandardInfos = null;
        ebsStandardInfos = (List<EBSStandardInfo>) ibatisDAO.getData(querySqlKey,
                queryVMStandardInfo);
        return ebsStandardInfos;
    }
}
