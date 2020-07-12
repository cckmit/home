package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemTypeInfo;

/**
 * 查询条目分类
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 上午10:09:14
 */
public class ItemCreateInfoService extends BaseService {

    /**
     * 查询条目分类
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<ItemTypeInfo> vmItemType(String itemType) throws SQLException {
        List<ItemTypeInfo> itemTypeInfos = (List<ItemTypeInfo>) ibatisDAO.getData("queryItemType",
                itemType);
        return itemTypeInfos;
    }

}
