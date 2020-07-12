/*******************************************************************************
 * @(#)ItemCommendAction.java 2013-2-27
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.RecommendStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemCommendService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用条目推荐与取消推荐
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-27 上午09:45:05
 */
public class ItemCommendAction extends BaseAction {

    private static final long serialVersionUID = -1036656292474458813L;

    private ItemCommendService itemCommendService;

    public ItemCommendService getItemCommendService() {
        return itemCommendService;
    }

    public void setItemCommendService(ItemCommendService itemCommendService) {
        this.itemCommendService = itemCommendService;
    }

    private static LogService logger = LogService.getLogger(ItemCommendAction.class);

    /**
     * spring配置 推荐：0、不推荐 1、推荐 参见RecommendStatus
     */
    private String recommendType;

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
            sb.append("操作的条目ID:");
            sb.append(itemId);
            logger.debug(sb.toString());
        }
        resultMessage = new CreateResult();
        if (null == itemId || "".equals(itemId)) {
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            resultMessage.setResultMessage(getText("item.commend.isNull"));
            return ConstantEnum.FAILURE.toString();
        }
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setUpdateUser(userId);
        itemInfo.setItemId(itemId);
        RecommendStatus recommendStatus = itemCommendService
                .obtainRecommendStatusEunm(recommendType);
        try {
            int count = itemCommendService.itemCommend(itemInfo, recommendType);
            if (count == 0) {// 操作失败
                resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
                resultMessage.setResultMessage(recommendStatus.getDesc());
                return ConstantEnum.FAILURE.toString();
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("tem.db.commend.fail"), userId, itemId, recommendStatus.getDesc()), e);
            this.addActionError(MessageFormat.format(getText("tem.db.commend.fail"), userId,
                    itemId, recommendStatus.getDesc()));
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(MessageFormat.format(getText("item.commend.error"),
                    recommendStatus.getDesc()));
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(itemId).append("条目设置是否推荐为：");
            sb.append(recommendStatus.getDesc()).append("成功！");
            logger.debug(sb.toString());
        }
        resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());
        resultMessage.setResultMessage(recommendStatus.getDesc());
        return ConstantEnum.SUCCESS.toString();
    }

    public String getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(String recommendType) {
        this.recommendType = recommendType;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
