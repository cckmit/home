package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemAuditLogInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;

/**
 * 条目审批 通用 （发布审批 条目审批）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-19 上午10:44:15
 */
public class ItemAuditService extends BaseService {

    /**
     * 条目审批（可多条目）并插入记录数据
     * @param itemIds 条目id集合
     * @param userId 用户id
     * @param statusType 改变状态
     * @param auditInfo 审批意见
     * @param logSqlKey 日志插入语句key
     * @param operableStatus 可操作状态
     * @return  返回不可操作条目集合
     * @throws SQLException
     */
    public List<ItemInfo> itemAudit(List<String> itemIds, String userId, String statusType,
            String auditInfo, String logSqlKey, String operableStatus) throws SQLException {
        ItemStatus itemStatus = obtainItemStatusEunm(statusType);
        List<ItemInfo> result = new ArrayList<ItemInfo>();
        ItemInfo itemInfo;
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        for (String itemId : itemIds) {
            itemInfo = new ItemInfo();
            itemInfo.setItemId(itemId);
            ItemInfo temp = getItemByItemId(itemInfo);
            if (null == temp) {
                temp = new ItemInfo();
                temp.setItemId(itemId);
                result.add(temp);
                continue;
            }
            if (operableStatus.equals(temp.getStatus().getCode())) {
                itemInfo.setStatus(itemStatus);
                itemInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                itemInfo.setUpdateUser(userId);
                initVmBatchVOs(batchVOs, itemInfo, auditInfo, logSqlKey);
            } else {
                result.add(temp);
            }
        }
        ibatisDAO.updateBatchData(batchVOs);
        return result;
    }

    /**
     * 生成batchVOs （条目审批及审批记录）
     * @param batchVOs
     * @param itemInfo
     * @param auditInfo
     */
    private void initVmBatchVOs(List<BatchVO> batchVOs, ItemInfo itemInfo, String auditInfo,
            String logSqlKey) {
        // 保存虚拟机条目
        BatchVO batchVO = new BatchVO("MOD", "auditItemStatusByItemId", itemInfo);
        batchVOs.add(batchVO);
        ItemAuditLogInfo itemAuditLogInfo = new ItemAuditLogInfo();
        itemAuditLogInfo.setItemId(itemInfo.getItemId());
        itemAuditLogInfo.setAuditInfo(auditInfo);
        itemAuditLogInfo.setAuditTime(itemInfo.getUpdateTime());
        itemAuditLogInfo.setStatus(itemInfo.getStatus().getCode());
        itemAuditLogInfo.setAuditUser(itemInfo.getUpdateUser());
        batchVO = new BatchVO("ADD", logSqlKey, itemAuditLogInfo);// "inserItemAuditLog"
        batchVOs.add(batchVO);
    }

    /**
     * 获取条目状态
     * @param statusType 状态字符串
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

    /**
     * 查询服务条目
     * @param itemInfo
     * @return ItemInfo
     * @throws SQLException
     */
    private ItemInfo getItemByItemId(ItemInfo itemInfo) throws SQLException {
        return (ItemInfo) ibatisDAO.getSingleRecord("getItemInfoByItemId", itemInfo);
    }
}
