/*******************************************************************************
 * @(#)ItemAuditLogAction.java 2013-2-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemAuditLogInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemAuditLogService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询条目发布审批 条目审批记录
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-26 下午04:21:34
 */
public class ItemAuditLogAction extends BaseAction {

    private static final long serialVersionUID = -3911579583600074482L;

    private static LogService logger = LogService.getLogger(ItemAuditLogAction.class);

    private ItemAuditLogService itemAuditLogService;

    public void setItemAuditLogService(ItemAuditLogService itemAuditLogService) {
        this.itemAuditLogService = itemAuditLogService;
    }

    // 界面返回
    private Map<String, ItemAuditLogInfo> itemAuditLogs;

    private String itemId;

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        if (logger.isDebugEnable()) {
            logger.debug("查询条目Id:" + itemId);
            logger.debug("审批记录信息！");
        }
        try {
            itemAuditLogs = itemAuditLogService.getItemAuditLog(itemId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("item.audit.option.fail"), userId, itemId), e);
            this.addActionError(MessageFormat.format(getText("item.audit.option.fail"), userId,
                    itemId));
            itemAuditLogs = null;
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            logger.debug("查询条目Id:" + itemId);
            logger.debug("审批记录信息成功！");
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Map<String, ItemAuditLogInfo> getItemAuditLogs() {
        return itemAuditLogs;
    }

}
