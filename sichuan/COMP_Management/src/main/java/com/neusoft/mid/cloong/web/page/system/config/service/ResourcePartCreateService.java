package com.neusoft.mid.cloong.web.page.system.config.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourcePartInfo;

/**
 * 资源池分区 保存or更新 相关操作 （1、查询是否被资源池 资源池分区 使用。 2、查询资源池编码下的资源池分区编码是否重复。）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-8 上午09:06:56
 */
public class ResourcePartCreateService extends BaseService {

    /**
     * 保存or更新
     * @param resourcePartInfo 要操作的对象
     * @param optSqlKey 保存or更新操作sqlkey
     * @throws SQLException
     */
    public void saveOrUpdate(ResourcePartInfo resourcePartInfo, String optSqlKey)
            throws SQLException {
        resourcePartInfo.setStatus(ResPoolStatus.NORMAL);// 设置创建时候的状态为正常
        resourcePartInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置创建时间
        resourcePartInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置更新时间
        ibatisDAO.updateData(optSqlKey, resourcePartInfo);
    }

    /**
     * 查询查询重复资源池分区编码和资源池编码方法 （查询资源池编码下的资源池分区编码是否被占用） queryResPartSaveById
     * @param resourcePartInfo 查询条件
     * @return 如果被占用返回 ResourcePartInfo对象 如果未被占用返回 null
     * @throws SQLException
     */
    public ResourcePartInfo queryResPartInfoByResIdAndResPartId(ResourcePartInfo resourcePartInfo)
            throws SQLException {
        // 是否被占
        ResourcePartInfo temp = (ResourcePartInfo) ibatisDAO.getSingleRecord(
                "queryResPartSaveById", resourcePartInfo);
        return temp;
    }

    /**
     * 查询 资源池 资源池分区 是否被使用(被关联资源规格)
     * @param resourcePartInfo 查询条件
     * @return 如果使用返回使用数量 如果未被使用返回 0
     * @throws SQLException
     */
    public int countStandardSynByResIdAndPartId(ResourcePartInfo resourcePartInfo)
            throws SQLException {
        // 资源池 资源池分区是否被使用(被关联资源规格)
        // 带有查询条件 status!=1 status!=3 状态不等于发送失败 和 不可用 的
        return ((Integer) ibatisDAO.getSingleRecord("countStandardSynByResIdAndPartId",
                resourcePartInfo)).intValue();
    }

}
