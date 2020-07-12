/*******************************************************************************
 * @(#)ItemInfoAction.java 2013-2-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemTypeInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemInfoService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用条目跳转Action
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-21 下午04:41:33
 */
public class ItemInfoAction extends BaseAction {

    private static final long serialVersionUID = 6846575534886217608L;

    private static LogService logger = LogService.getLogger(ItemInfoAction.class);

    private ItemInfoService itemInfoService;

    public ItemInfoService getItemInfoService() {
        return itemInfoService;
    }

    public void setItemInfoService(ItemInfoService itemInfoService) {
        this.itemInfoService = itemInfoService;
    }

    /**
     * 返回条目分类
     */
    private List<ItemTypeInfo> itemTypeInfos;

    private ItemInfo itemInfo;
    

    private String itemId;

    /**
     * spring 配置 条目类型
     */
    private String itemType;

    public String execute() {
        itemType = itemType.trim();//去除空格
        ItemType itemTypeEnum = itemInfoService.getItemTypeEnum(itemType);
        try {
            itemTypeInfos = itemInfoService.vmItemType(itemType);
            itemInfo = itemInfoService.getItemInfoByItemId(itemId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("item.update.info.error"), e);
            errMsg = getText("item.update.info.error");
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(itemTypeEnum.getDesc());
            sb.append(getText("item.update.info.success"));
            logger.debug(sb.toString());
        }
        if(itemTypeInfos==null || itemTypeInfos.size()==0){
            msg = getText("item.type.isNull");
            return ConstantEnum.FAILURE.toString();
        }
        if(itemInfo==null){
            msg = getText("item.isDel");
            return ConstantEnum.FAILURE.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<ItemTypeInfo> getItemTypeInfos() {
        return itemTypeInfos;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
