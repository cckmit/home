package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.RecommendStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;

/**
 * 条目通用更新推荐状态
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-27 上午09:57:09
 */
public class ItemCommendService extends BaseService {

    /**
     * 更新条目推荐状态
     * @param itemInfo
     * @param recommendType
     * @return 
     * @throws SQLException
     */
    public int itemCommend(ItemInfo itemInfo, String recommendType) throws SQLException {
        itemInfo.setRecommend(obtainRecommendStatusEunm(recommendType));
        itemInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        //sql语句中有更新条目：status!='8' AND status!='7' 删除及下架的不能设置推荐
        return ibatisDAO.updateData("updateItemRecommendByItemId", itemInfo);
    }

    /**
     * 获取条目状态枚举
     * @param statusType
     * @return 存在返回 ItemStatus 枚举类型.否则返回null
     */
    public RecommendStatus obtainRecommendStatusEunm(String recommendType) {
        for (RecommendStatus status : RecommendStatus.values()) {
            if (recommendType.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }
}
