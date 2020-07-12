package com.neusoft.mid.cloong.web.page.product.item.vm.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemAuditLogInfo;

/**
 * 查询条目发布审批  条目审批记录
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-26 下午04:45:20
 */
public class ItemAuditLogService extends BaseService {

    /**
     * 查询条目发布审批 条目审批  记录表
     * @param itemId 条目id
     * @return Map<String, ItemAuditLogInfo> {release:发布审批记录内容,audit:条目审批记录内容}
     * @throws SQLException
     */
    public Map<String, ItemAuditLogInfo> getItemAuditLog(String itemId) throws SQLException {
        Map<String, ItemAuditLogInfo> itemAuditLogInfos = new HashMap<String, ItemAuditLogInfo>();

        ItemAuditLogInfo itemAuditLogInfo = (ItemAuditLogInfo) ibatisDAO.getSingleRecord(
                "queryItemReleaseLog", itemId);
        //日期格式转换
        if(itemAuditLogInfo!=null){
            itemAuditLogInfo.setAuditTime(DateParse.parse(itemAuditLogInfo.getAuditTime()));
        }
        itemAuditLogInfos.put("release", itemAuditLogInfo);
        itemAuditLogInfo = (ItemAuditLogInfo) ibatisDAO
                .getSingleRecord("queryItemAuditLog", itemId);
        //日期格式转换
        if(itemAuditLogInfo!=null){
            itemAuditLogInfo.setAuditTime(DateParse.parse(itemAuditLogInfo.getAuditTime()));
        }
        itemAuditLogInfos.put("audit", itemAuditLogInfo);
        return itemAuditLogInfos;
    }
}
