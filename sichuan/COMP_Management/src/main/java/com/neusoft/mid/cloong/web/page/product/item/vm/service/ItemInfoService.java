package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemTypeInfo;

/**
 * 服务条目 查询详细及条目分类
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-21 下午04:47:17
 */
public class ItemInfoService extends BaseService {

    /**
     * 查询条目分类
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<ItemTypeInfo> vmItemType(String itemType) throws SQLException {
        List<ItemTypeInfo> itemTypeInfos = (List<ItemTypeInfo>) ibatisDAO.getData("queryItemType",
                itemType);
        return itemTypeInfos;
    }
    /**
     * 获取ItemInfo通过itemId
     * @param itemId 条目id
     * @return ItemInfo 条目对象
     * @throws SQLException
     */
    public ItemInfo getItemInfoByItemId(String itemId) throws SQLException{
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setItemId(itemId);
        return (ItemInfo)ibatisDAO.getSingleRecord("getItemInfoByItemId", itemInfo);
    }
    /**
     * 获取itemType枚举
     * @param itemType
     * @return
     */
    public ItemType getItemTypeEnum(String itemType){
        for (ItemType status : ItemType.values()) {
            if (itemType.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }

}
