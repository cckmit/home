/*******************************************************************************
 * @(#)ItemAuditAction.java 2013-2-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemAuditService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用条目审批(审批\发布审批)
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-28 下午03:23:57
 */
public class ItemAuditAction extends BaseAction {

    private static final long serialVersionUID = 2919517611970721685L;

    private ItemAuditService itemAuditService;

    public ItemAuditService getItemAuditService() {
        return itemAuditService;
    }

    public void setItemAuditService(ItemAuditService itemAuditService) {
        this.itemAuditService = itemAuditService;
    }

    private static LogService logger = LogService.getLogger(ItemAuditAction.class);

    /**
     * spring配置 状态类型：2待发布（即审批通过） 3审批不通过
     */
    private String statusType;

    /**
     * spring配置 插入审批日志sql的id //inserItemAuditLog inserItemReleaseLog
     */
    private String logSqlKey;

    /**
     * spring配置 可操作状态
     */
    private String operableStatus;

    /**
     * 获取界面消息
     */
    private String itemId;

    private String auditInfo;

    /**
     * 用于返回界面信息
     */
    private CreateResult resultMessage;

    public CreateResult getResultMessage() {
        return resultMessage;
    }

    public String execute() {
        String itemIsNullMsg = getText("item.audit.isnull");// 条目不存在！ 提示
        String dbIsErrorMsg = getText("db.error.msg");// 数据库操作异常！提示
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("操作的条目ID:").append(itemId);
            logger.debug(sb.toString());
        }
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        resultMessage = new CreateResult();
        List<String> itemIds = obtainJsonToList(itemId);// JSON字条串转换成List集合
        if (itemIds.size() == 0) {// 如果条目不存在返回 请正确选择要操作的条目
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            resultMessage.setResultMessage(itemIsNullMsg);
            return ConstantEnum.FAILURE.toString();
        }
        // 获取条目状态
        ItemStatus itemStatus = itemAuditService.obtainItemStatusEunm(statusType);
        if (null == itemStatus) {// 如果操作不存在返回非法操作
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            resultMessage.setResultMessage(getText("item.audit.operate.illegality"));
            return ConstantEnum.FAILURE.toString();
        }
        String dbIsErrorLogMsg = MessageFormat.format(getText("item.audit.db.operate.fail"),
                userId, itemId, itemStatus.getDesc());// 数据库异常 日志输出信息
        try {// 调用 审批方法
            List<ItemInfo> intemInfos = itemAuditService.itemAudit(itemIds, userId, statusType,
                    auditInfo, logSqlKey, operableStatus);
            if (intemInfos.size() == itemIds.size()) {
                resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
                resultMessage.setResultMessage(itemIsNullMsg);
                return ConstantEnum.FAILURE.toString();
            }
            if (intemInfos.size() > 0) {
                resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());
                resultMessage.setResultMessage(MessageFormat.format(
                        getText("item.audit.operate.part.success"), itemIds.size(), intemInfos
                                .size()));
                return ConstantEnum.SUCCESS.toString();
            }
        } catch (SQLException e1) {// sql异常
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, dbIsErrorLogMsg, e1);
            this.addActionError(dbIsErrorLogMsg);
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(dbIsErrorMsg);
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, dbIsErrorLogMsg, e);
            this.addActionError(dbIsErrorLogMsg);
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(dbIsErrorMsg);
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(itemId).append("条目设置状态为：");
            sb.append(itemStatus.getDesc()).append("成功！");
            logger.debug(sb.toString());
        }
        resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());
        resultMessage.setResultMessage(getText("item.audit.success"));
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 将前台JSON字符串 转换 List
     * @param itemIdStr 前台传入的JSON字符串
     * @return List<String> 返回转换后的List<条目id> 列表
     */
    @SuppressWarnings("unchecked")
    private List<String> obtainJsonToList(String itemIdStr) {
        List<String> itemIds = new ArrayList<String>();
        try {
            JSONArray json = JSONArray.fromObject(itemIdStr);
            itemIds = (List<String>) JSONArray.toCollection(json, String.class);
        } catch (JSONException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("item.audit.json.error"), e);
        }
        return itemIds;
    }

    public void setOperableStatus(String operableStatus) {
        this.operableStatus = operableStatus;
    }

    public void setLogSqlKey(String logSqlKey) {
        this.logSqlKey = logSqlKey;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
