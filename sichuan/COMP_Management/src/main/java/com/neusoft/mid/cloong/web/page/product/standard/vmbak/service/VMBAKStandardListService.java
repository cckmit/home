package com.neusoft.mid.cloong.web.page.product.standard.vmbak.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.info.VMBAKStandardInfo;

/**
 * 通过条件查询虚拟机备份资源规格列表Service（条件包括：容量大小，是否条目使用）
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-2-19 下午08:07:18
 */
public class VMBAKStandardListService extends BaseService {

    /**
     * 
     * queryVMBAKStandard 查询虚拟机备份资源规格
     * @param queryVMBAKStandardInfo 查询条件
     * @param querySqlKey 查询语句
     * @return List<VMBAKStandardInfo> vmBakStandardInfos
     * @throws SQLException 
     */
    @SuppressWarnings("unchecked")
    public List<VMBAKStandardInfo> queryVMBAKStandard(VMBAKStandardInfo queryVMBAKStandardInfo,
            String querySqlKey) throws SQLException {
        List<VMBAKStandardInfo> vmBakStandardInfos = null;
        vmBakStandardInfos = (List<VMBAKStandardInfo>) ibatisDAO.getData(querySqlKey,
                queryVMBAKStandardInfo);
        return vmBakStandardInfos;
    }
}
