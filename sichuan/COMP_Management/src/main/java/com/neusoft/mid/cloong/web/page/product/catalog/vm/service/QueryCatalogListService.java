package com.neusoft.mid.cloong.web.page.product.catalog.vm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.catalog.vm.info.CatalogInfo;

/**查询服务目录列表Service
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-11 下午04:25:18
 */
public class QueryCatalogListService extends BaseService {

    /**
     * 查询服务目录
     * @param String catalogType 服务目录类型
     * @return List<CatalogInfo> 服务目录列表
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<CatalogInfo> queryCatalogList(String catalogType)
            throws SQLException {
        List<CatalogInfo> catalogInfos = null;
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setCatalogType(catalogType);
        catalogInfos = (List<CatalogInfo>) ibatisDAO.getData("queryCatalogInfo",
                catalogInfo);
        return catalogInfos;
    }

    /**创建服务目录
     * @param catalogInfo
     * @throws SQLException
     */
    public void createCatalog(CatalogInfo catalogInfo) throws SQLException {

        ibatisDAO.insertData("createCatalogInfo", catalogInfo);
    }

    /**创建服务目录前判读是否已存在
     * @param catalogInfo
     * @return true 已存在；false 未存在；
     * @throws SQLException
     */
    public boolean createCatalogIsUsed(CatalogInfo catalogInfo) throws SQLException {

        int isUsed = (Integer)ibatisDAO.getSingleRecord("createCatalogIsUsed", catalogInfo);
        if(isUsed > 0){
            return true;
        }
        return false;
    }

    /**更新服务目录
     * @param CatalogInfo 服务目录
     * @throws SQLException
     */
    public void updateCatalog(CatalogInfo catalogInfo)
            throws SQLException {
        ibatisDAO.updateData("updateCatalogInfo",catalogInfo);
    }
    /**删除服务目录
     * @param CatalogInfo 服务目录
     * @throws SQLException
     */
    public void deleteCatalog(String catalogId)
            throws SQLException {
        ibatisDAO.deleteData("deleteCatalog",catalogId);
    }

    /**查询该服务目录下是否有条目
     * @param catalogId
     * @return true 有；false 没有；
     * @throws SQLException
     */
    public boolean queryCatalogIsUsed(String catalogId)
    throws SQLException {
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setCatalogId(catalogId);
        int count = (Integer)ibatisDAO.getCount("queryCatalogIsUsed", catalogInfo);
        if(count > 0){
            return true;
        }
        return false;
    }
}
