/*******************************************************************************
 * @(#)ItemCreateInfoAction.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemTypeInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemCreateInfoService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 跳转创建服务条目，包括：虚拟机规格条目
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 上午08:52:50
 */
public class ItemCreateInfoAction extends BaseAction {

    private static final long serialVersionUID = 1040082095916272231L;

    private static LogService logger = LogService.getLogger(ItemCreateInfoAction.class);

    /**
     * service
     */
    private ItemCreateInfoService itemCreateInfoService;

    public ItemCreateInfoService getItemCreateInfoService() {
        return itemCreateInfoService;
    }

    public void setItemCreateInfoService(ItemCreateInfoService itemCreateInfoService) {
        this.itemCreateInfoService = itemCreateInfoService;
    }

    /**
     * spring 配置  0虚拟机 5虚拟硬盘   （与服务目录中的catalog_type对应）
     */
    private String itemType;
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * 返回条目分类
     */
    private List<ItemTypeInfo> itemTypeInfos;
    

    public List<ItemTypeInfo> getItemTypeInfos() {
        return itemTypeInfos;
    }

    public String execute() {
        try {
            itemTypeInfos = itemCreateInfoService.vmItemType(itemType);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("item.type.vm.gocreate.fail"), e);
            errMsg = getText("item.type.vm.gocreate.fail");
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("虚拟机条目跳转创建页面！");
            logger.debug(sb.toString());
        }
        if(itemTypeInfos==null || itemTypeInfos.size()==0){
            msg = getText("item.type.isNull");
        }
        return ConstantEnum.SUCCESS.toString();
    }
    
}
