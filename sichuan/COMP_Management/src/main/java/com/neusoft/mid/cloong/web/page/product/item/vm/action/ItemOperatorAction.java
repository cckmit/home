/*******************************************************************************
 * @(#)ItemOperatorAction.java 2013-2-19
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

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemOperatorService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-19 上午09:51:26
 */
public class ItemOperatorAction extends BaseAction {

    private static final long serialVersionUID = 2919517611970721685L;

    private ItemOperatorService itemOperatorService;

    public ItemOperatorService getItemOperatorService() {
        return itemOperatorService;
    }

    public void setItemOperatorService(ItemOperatorService itemOperatorService) {
        this.itemOperatorService = itemOperatorService;
    }

    private static LogService logger = LogService.getLogger(ItemOperatorAction.class);

    /**
     * spring配置 状态类型：0、保存 1、保存并提交(待审批) 详细参照ItemStatus枚举
     */
    private String statusType;

    /**
     * 获取界面消息
     */
    private String itemId;

    /**
     * 用于返回界面信息
     */
    private CreateResult resultMessage;

    public CreateResult getResultMessage() {
        return resultMessage;
    }

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("操作的条目ID:").append(itemId);
            logger.debug(sb.toString());
        }
        resultMessage = new CreateResult();
        ItemStatus itemStatus = itemOperatorService.obtainItemStatusEunm(statusType);
        List<String> itemIds = obtainJsonToList(itemId);// JSON字符串转换成List集合
        if (itemIds.size() == 0) {// 条目不存在！
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            return ConstantEnum.FAILURE.toString();
        }
        String dbIsErrorLogMsg = MessageFormat.format(getText("item.db.operator.fail"), userId,
                itemId, itemStatus.getDesc());
        try {
            int count = itemOperatorService.itemOperator(itemIds, userId, statusType);
            if (count == 0) {// 操作失败
                resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
                return ConstantEnum.FAILURE.toString();
            }
        } catch (SQLException e1) {// sql异常
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, dbIsErrorLogMsg, e1);
            this.addActionError(dbIsErrorLogMsg);
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());// 数据库操作异常！
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, dbIsErrorLogMsg, e);
            this.addActionError(dbIsErrorLogMsg);
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());// 数据库操作异常！
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(itemId).append("条目设置状态为：").append(itemStatus.getDesc()).append("成功！");
            logger.debug(sb.toString());
        }
        resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());// 操作成功！
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
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("item.operator.json.error"), e);
        }
        return itemIds;
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
