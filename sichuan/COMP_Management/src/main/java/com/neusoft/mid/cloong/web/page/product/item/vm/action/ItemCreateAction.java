/*******************************************************************************
 * @(#)ItemCreateAction.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.text.MessageFormat;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemCreateService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用 创建服务条目
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-6 下午03:42:16
 */
public class ItemCreateAction extends ItemBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private ItemCreateService itemCreateService;

    public ItemCreateService getItemCreateService() {
        return itemCreateService;
    }

    public void setItemCreateService(ItemCreateService itemCreateService) {
        this.itemCreateService = itemCreateService;
    }

    private static LogService logger = LogService.getLogger(ItemCreateAction.class);

    /**
     * 通用服务条目保存（保存并提交）
     * @return
     */
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        // json转换bean
        ItemInfo itemInfo = obtainJsonToBean(itemJson,userId);
        resultMessage = new CreateResult();
        if (itemInfo == null) {
            resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
            resultMessage.setResultMessage(getText("item.create.fail"));
            return ConstantEnum.FAILURE.toString();
        }
        // 设置新建人Id
        itemInfo.setCreateUser(userId);
        // 设置更新人Id
        itemInfo.setUpdateUser(userId);
        if (logger.isDebugEnable()) {
            logger.debug(itemJson);
            logger.debug(itemInfo.toString());
        }
        ItemType itemTypeEnum = itemCreateService.getItemTypeEnum(itemType);
        try {
	        //查询条目名称是否重复
	       	int nameNum = ibatisDAO.getCount("checkItemName", itemInfo);
	       	if(nameNum > 0 ){
	       		resultMessage.setResultFlage(ConstantEnum.FAILURE.toString());
                resultMessage.setResultMessage(getText("item.name.isHave"));
                return ConstantEnum.FAILURE.toString();
	       	}
	       	
            itemCreateService.itemCreate(itemInfo, itemType, statusType, itemIdType);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("item.create.option.fail"), userId, itemTypeEnum.getDesc()), e);
            this.addActionError(MessageFormat.format(getText("item.create.option.fail"), userId,
                    itemTypeEnum.getDesc()));
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(getText("db.error.msg"));
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(itemTypeEnum.getDesc());
            sb.append("条目创建成功！");
            logger.debug(sb.toString());
        }
        resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());
        resultMessage.setResultMessage(getText("item.create.success"));
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 界面json字符串转换成bean
     * @param itemJson
     * @return
     */
    private ItemInfo obtainJsonToBean(String itemJson,String userId) {
        // 将前台传入的json字符串转换成对象
        if (logger.isDebugEnable()) {
            logger.debug("JSON转换Bean");
        }
        ItemInfo item = null;
        try {
            JSONObject json = JSONObject.fromObject(itemJson);
            item = (ItemInfo) JSONObject.toBean(json, ItemInfo.class);
        } catch (Exception e) {
            ItemType itemTypeEnum = itemCreateService.getItemTypeEnum(itemType);
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("item.create.json.fail"), userId, itemTypeEnum.getDesc()), e);
        }
        if (logger.isDebugEnable()) {
            logger.debug("ItemInfo:\n" + item.toString());
        }
        return item;
    }

}
