/*******************************************************************************
 * @(#)ItemUpdateAction.java 2013-2-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.text.MessageFormat;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemUpdateService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用条目修改
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-21 下午05:08:04
 */
public class ItemUpdateAction extends ItemBaseAction {

    private static final long serialVersionUID = 4277166088330348495L;

    private ItemUpdateService itemUpdateService;

    public ItemUpdateService getItemUpdateService() {
        return itemUpdateService;
    }

    public void setItemUpdateService(ItemUpdateService itemUpdateService) {
        this.itemUpdateService = itemUpdateService;
    }

    private static LogService logger = LogService.getLogger(ItemUpdateAction.class);

    private ItemType itemTypeEnum;
    
    private String statusCode;

    /**
     * 通用服务条目保存（保存并提交）
     * @return
     */
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        // json转换bean
        ItemInfo itemInfo = formatJson(itemJson, userId);
        resultMessage = new CreateResult();
        itemTypeEnum = itemUpdateService.getItemTypeEnum(itemType);
        if (itemInfo == null) {
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            resultMessage.setResultMessage(getText("item.create.fail"));
            return ConstantEnum.FAILURE.toString();
        }
        if ("".equals(itemInfo.getItemId() == null ? "" : itemInfo.getItemId())) {
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            resultMessage.setResultMessage(getText("item.update.isNull"));
            return ConstantEnum.FAILURE.toString();
        }
        // 设置更新人Id
        itemInfo.setUpdateUser(userId);
        if (logger.isDebugEnable()) {
            logger.debug(itemJson);
            logger.debug(itemInfo.toString());
        }
        String resultCode;
        try {
	        //查询条目名称是否重复
	    	int nameNum = ibatisDAO.getCount("checkItemName", itemInfo);
	    	if(nameNum > 0 ){
	    		 resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
	             resultMessage.setResultMessage(getText("item.name.isHave"));
	             return ConstantEnum.FAILURE.toString();
	    	}
	    	
	    	resultCode = itemUpdateService
                    .itemUpdate(itemInfo, itemType, statusType, oldStandardId, statusCode);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("item.update.option.fail"), userId, itemTypeEnum.getDesc()
                            + itemInfo.getItemId()), e);
            this.addActionError(MessageFormat.format(getText("item.update.option.fail"), userId,
                    itemTypeEnum.getDesc() + itemInfo.getItemId()));
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(getText("db.error.msg"));
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(itemTypeEnum.getDesc());
            sb.append(getText("item.update.success"));
            logger.debug(sb.toString());
        }
        // getText("item.create.success")
        resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());
        resultMessage.setResultMessage(getPromptText(resultCode));
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 用户json字符串转换成bean
     * @param itemJson
     * @return
     */
    private ItemInfo formatJson(String itemJson, String userId) {
        // 将前台传入的json字符串转换成对象
        if (logger.isDebugEnable()) {
            logger.debug("JSON转换Bean");
        }
        ItemInfo item = null;
        try {
            JSONObject json = JSONObject.fromObject(itemJson);
            item = (ItemInfo) JSONObject.toBean(json, ItemInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("item.update.json.fail"), userId, itemTypeEnum.getDesc()), e);
        }
        if (logger.isDebugEnable()) {
            logger.debug("ItemInfo:\n" + item.toString());
        }
        return item;
    }

    /***
     * 获取对应提示信息
     * @param statusCode
     * @return
     */
    private String getPromptText(String statusCode) {
        if (ItemStatus.DELETED.getCode().equals(statusCode)) {
            return getText("item.update.isDel");
        }
        if (ItemStatus.PENDING.getCode().equals(statusCode)) {
            return getText("item.update.isSubmit");
        }
        if (ItemStatus.PUBLISHPASS.getCode().equals(statusCode)) {
            return getText("item.update.isPublic");
        }
        if (ItemStatus.PUBLISH.getCode().equals(statusCode)) {
            return getText("item.update.isPublicSubmit");
        }
        if (ItemStatus.PULLED.getCode().equals(statusCode)) {
            return getText("item.update.isDown");
        }
        if (ConstantEnum.DELETE.toString().equals(statusCode)) {
            return getText("item.update.isNull");
        }
        return getText("item.update.success");
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
