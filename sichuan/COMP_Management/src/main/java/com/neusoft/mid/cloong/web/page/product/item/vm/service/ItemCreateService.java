package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.common.core.RecommendStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;

/**
 * 通用 服务条目创建 Service
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 下午05:19:56
 */
public class ItemCreateService extends BaseService {

    // 生成条目id
    private SequenceGenerator sequenceGenerator;

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * 通用 创建条目
     * @param itemInfo 条目实体bean
     * @param itemType 条目类型
     * @param statusType 状态类型
     * @param itemIdType 生成Id中间字符串
     * @throws SQLException
     */
    public void itemCreate(ItemInfo itemInfo, String itemType, String statusType, String itemIdType)
            throws SQLException {
        initItem(itemInfo, itemType, statusType, itemIdType);
        //TODO 缺少关联资费信息
        ibatisDAO.updateData("itemInsert", itemInfo);
    }

    /**
     * 条目信息初始化设置
     * @param itemInfo 服务条目实例
     * @param itemType 条目类型
     * @param statusType 状态类型
     * @param itemIdType 生成Id中间字符串
     */
    private void initItem(ItemInfo itemInfo, String itemType, String statusType, String itemIdType) {
        ItemType itemTypeEnum = getItemTypeEnum(itemType);
        // 条目类型
        itemInfo.setItemType(itemTypeEnum);
        // 资源规格类型
        itemInfo.setStandardType(itemTypeEnum == null ? "" : itemTypeEnum.getCode());
        for (ItemStatus status : ItemStatus.values()) {
            if (statusType.equals(status.getCode())) {
                // 条目状态
                itemInfo.setStatus(status);
                break;
            }
        }
        // 推荐状态——不推荐
        itemInfo.setRecommend(RecommendStatus.UN_RECOMMENDATION);
        // 当前系统时间
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        // 创建时间
        itemInfo.setCreateTime(time);
        // 更新时间
        itemInfo.setUpdateTime(time);
        if("".equals(itemInfo.getSellStartTime())){
            itemInfo.setSellStartTime(time);
        }
        itemInfo.setItemId(sequenceGenerator.generatorItemId(itemIdType));
    }

    /**
     * 获取条目类型
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
}
