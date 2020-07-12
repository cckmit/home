package com.neusoft.mid.cloong.web.page.product.standard.vm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;

/**
 * 通过条件查询虚拟机资源规格列表Service（条件包括：cpu数量，内存容量，硬盘大小，是否条目使用）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 下午02:23:22
 */
public class VMStandardListService extends BaseService {

    /**
     * 查询虚拟机资源规格
     * @param queryVMStandardInfo
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<VMStandardInfo> queryVMStandard(VMStandardInfo queryVMStandardInfo,String querySqlKey)
            throws SQLException {
        List<VMStandardInfo> vmStandardInfos = null;
        vmStandardInfos = (List<VMStandardInfo>) ibatisDAO.getData(querySqlKey,
                queryVMStandardInfo);
        return vmStandardInfos;
    }
}
