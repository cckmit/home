package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;

/**
 * 通用 条目操作
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-19 上午10:44:15
 */
public class ItemOperatorService extends BaseService {

    public int itemOperator(List<String> itemIds, String userId, String statusType)
            throws SQLException {
        ItemStatus itemStatus = obtainItemStatusEunm(statusType);
        if (itemStatus == null) {
            return 0;
        }
        // 设置相应操作 可操作条件
        Map<String, Object> itemInfos = new HashMap<String, Object>();
        // 可操作状态列表
        List<String> statusLs = initItemInfos(statusType);
        itemInfos.put("statusLs", statusLs);
        // 操作的条目ID
        itemInfos.put("itemIds", itemIds);
        // 更新人
        itemInfos.put("updateUser", userId);
        // 更新时间
        itemInfos.put("updateTime", DateParse.generateDateFormatyyyyMMddHHmmss());
        // 更新状态
        itemInfos.put("status", statusType);
        // sql语句有更新条件：
        return ibatisDAO.updateData("updateItemStatusByItemId", itemInfos);
    }

    /**
     * 通过更新状态判断相应可操作的状态.返回 可操作状态 列表。
     * @param statusType 要更新状态
     * @return List<String> 返回可操作的状态列表
     */
    private List<String> initItemInfos(String statusType) {
        List<String> statusLs = new ArrayList<String>();
        if (ItemStatus.PENDING.getCode().equals(statusType)) {
            // 设置 待审批
            // 只有 已保存 审批不通过 可操作 待审批
            statusLs.add(ItemStatus.CREATING.getCode());
            statusLs.add(ItemStatus.UN_PENDPASS.getCode());
        } else if (ItemStatus.PENDPASS.getCode().equals(statusType)) {
            // 设置 待发布（即条目审批通过）
            // 只有 条目待审 可操作 待发布
            statusLs.add(ItemStatus.PENDING.getCode());
        } else if (ItemStatus.UN_PENDPASS.getCode().equals(statusType)) {
            // 设置 审批不通过
            // 只有 条目待审 可操作 审批不通过
            statusLs.add(ItemStatus.PENDING.getCode());
        } else if (ItemStatus.PUBLISH.getCode().equals(statusType)) {
            // 设置 发布待审
            // 只有 条目审批通过 发布审批不通过 可操作 发布待审
            statusLs.add(ItemStatus.PENDPASS.getCode());
            statusLs.add(ItemStatus.UN_PUBLISHPASS.getCode());
        } else if (ItemStatus.PUBLISHPASS.getCode().equals(statusType)) {
            // 设置 已发布（即发布审批通过）
            // 只有 发布待审 可操作 已发布
            statusLs.add(ItemStatus.PUBLISH.getCode());
        } else if (ItemStatus.UN_PUBLISHPASS.getCode().equals(statusType)) {
            // 设置 发布不通过
            // 只有 发布待审 可操作 发布不通过
            statusLs.add(ItemStatus.PUBLISH.getCode());
        } else if (ItemStatus.PULLED.getCode().equals(statusType)) {
            // 设置 下架
            // 只有 已发布 可以操作 下架
            statusLs.add(ItemStatus.PUBLISHPASS.getCode());
        }
        return statusLs;
    }

    /**
     * 获取条目状态
     * @param statusType
     * @return 存在返回 ItemStatus 枚举类型.否则返回null
     */
    public ItemStatus obtainItemStatusEunm(String statusType) {
        for (ItemStatus status : ItemStatus.values()) {
            if (statusType.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }
}
