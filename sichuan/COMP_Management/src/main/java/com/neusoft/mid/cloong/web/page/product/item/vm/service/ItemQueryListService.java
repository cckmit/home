package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.common.core.RecommendStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;

/**
 * 查询服务条目列表Service
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 上午09:25:08
 */
public class ItemQueryListService extends BaseService {

    /**
     * 查询服务条目列表
     * @param queryItemInfo
     * @return 服务条目list集合
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<ItemInfo> queryItemsByItemType(ItemInfo queryItemInfo, String sqlKey)
            throws SQLException {
        List<ItemInfo> itemInfos = (List<ItemInfo>) ibatisDAO.getData(sqlKey, queryItemInfo);
        formatDate(itemInfos);// 转换条目中的日期格式
        return itemInfos;
    }

    /**
     * 获取条目状态枚举
     * @param itemStatus
     * @return 存在返回 ItemStatus 枚举类型.否则返回null
     */
    public ItemStatus getItemStatusEnum(String itemStatus) {
        for (ItemStatus status : ItemStatus.values()) {
            if (itemStatus.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取条目推荐状态枚举
     * @param itemStatus
     * @return 存在返回 RecommendStatus 枚举类型.否则返回null
     */
    public RecommendStatus getRecommendStatusEnum(String recommendStatus) {
        for (RecommendStatus status : RecommendStatus.values()) {
            if (recommendStatus.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取条目类型枚举
     * @param itemType
     * @return 存在返回 ItemType 枚举类型.否则返回null
     */
    public ItemType getItemTypeEnum(String itemType) {
        for (ItemType status : ItemType.values()) {
            if (itemType.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }

    /**
     * 转换条目list集合中的时间格式（yyyy-MM-dd HH:mm:ss）
     * @param itemInfos
     */
    private void formatDate(List<ItemInfo> itemInfos) {
        if (itemInfos != null) {
            for (ItemInfo itemInfo : itemInfos) {
                itemInfo.setSellStartTime(DateParse.parse(itemInfo.getSellStartTime()));
                if (!"".equals(null == itemInfo.getSellEndTime() ? "" : itemInfo.getSellEndTime())) {
                    itemInfo.setSellEndTime(DateParse.parse(itemInfo.getSellEndTime()));
                }
                itemInfo.setCreateTime(DateParse.parse(itemInfo.getCreateTime()));
                itemInfo.setUpdateTime(DateParse.parse(itemInfo.getUpdateTime()));
            }
        }
    }
}
