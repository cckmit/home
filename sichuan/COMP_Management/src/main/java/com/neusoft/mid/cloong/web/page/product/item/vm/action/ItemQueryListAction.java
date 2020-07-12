/*******************************************************************************
 * @(#)ItemQueryListAction.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.service.ItemQueryListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询服务条目列表Action
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-5 下午2:46:45
 */
public class ItemQueryListAction extends PageAction {

    private static final long serialVersionUID = -309553214096416609L;

    private ItemQueryListService itemQueryListService;

    public ItemQueryListService getItemQueryListService() {
        return itemQueryListService;
    }

    public void setItemQueryListService(ItemQueryListService itemQueryListService) {
        this.itemQueryListService = itemQueryListService;
    }

    private static LogService logger = LogService.getLogger(ItemQueryListAction.class);

    /**
     * 界面返回
     */
    private List<ItemInfo> itemInfoList;
    /**
     * item信息
     */
    private Map<String,Object> itemInfos;

    /**
     * 查询服务条目类型 spring配置 0虚拟机 1物理机 详细参照ItemIype枚举
     */
    private String itemType;

    /**
     * spring 配置sql中的Id
     */
    private String sqlKey;

    /**
     * 查询条目数量
     */
    private String itemCount = "";

    /**
     * 不查询条目状态 spring配置 0已保存 1条目待审 详细参照ItemStatus枚举
     */
    private String noItemStatus;
    
    /**
     * 同步标志位 0为异步  非0为同步(1) sprin配置 默认异步可以不配置
     */
    private String syncFlage = "0";

    /**
     * 查询条件
     */
    private String itemCode;

    private String status;

    private String itemName;

    private String beginCreateTime;

    private String endCreateTime;

    private String recommend;
    /**
     * 判断是否发布
     */
    private String publish = "0";
    
    /**
     * 调用同步翻页状态码 0为异步  非0为同步(1)
     */
    private static final String SYNCCODE = "0";
    
    /**
     * 发布审批状态码 1为发布审批  非1为审批
     */
    private static final String PUBLICAUDITCODE = "1";

    /**
     * 服务条目列表查询
     * @return
     */
    public String execute() {

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        ItemType itemTypeEnum = itemQueryListService.getItemTypeEnum(itemType);
        ItemInfo queryItemInfo = new ItemInfo();
        initQuery(queryItemInfo);
        try {
            // itemInfos = itemQueryListService.queryItemsByItemType(queryItemInfo, itemCount);
            if(SYNCCODE.equals(syncFlage)){
                itemInfoList = getPage(itemCount, sqlKey, queryItemInfo,PageTransModel.ASYNC);
            }else{
                itemInfoList = getPage(itemCount, sqlKey, queryItemInfo);
            }
            int itemInfoListCount = ibatisDAO.getCount(itemCount, queryItemInfo);
            itemInfos = new HashMap<String, Object>();
            itemInfos.put("listCount", itemInfoListCount);
            itemInfos.put("list", itemInfoList);
            itemInfos.put("page", getPageBar());
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("item.query.fail"), userId, itemTypeEnum.getDesc()), e);
            this.addActionError(MessageFormat.format(getText("item.query.fail"), userId,
                    itemTypeEnum.getDesc()));
            return ConstantEnum.ERROR.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            if(null == itemTypeEnum){
                if(PUBLICAUDITCODE.equals(status)){
                    sb.append("发布审批");
                }else{
                    sb.append("审批");
                }
            }else{
                sb.append(itemTypeEnum.getDesc());
            }
            sb.append("条目列表长度为:");
            sb.append(itemInfoList.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    // 初始化数据
    private void initQuery(ItemInfo queryItemInfo) {
        queryItemInfo.setItemType(itemQueryListService.getItemTypeEnum(itemType));
        queryItemInfo.setNoItemStatus(itemQueryListService.getItemStatusEnum(noItemStatus));
        if (!"".equals(status == null ? "" : status)) {
            queryItemInfo.setStatus(itemQueryListService.getItemStatusEnum(status));
        }
        if (!"".equals(itemName == null ? "" : itemName)) {
            queryItemInfo.setItemName(itemName);
        }
        if (!"".equals(beginCreateTime == null ? "" : beginCreateTime)) {
            queryItemInfo.setBeginCreateTime(beginCreateTime);
        }
        if (!"".equals(endCreateTime == null ? "" : endCreateTime)) {
            queryItemInfo.setEndCreateTime(endCreateTime);
        }
        if (!"".equals(recommend == null ? "" : recommend)) {
            queryItemInfo.setRecommend(itemQueryListService.getRecommendStatusEnum(recommend));
        }
        queryItemInfo.setPublish(publish);
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getNoItemStatus() {
        return noItemStatus;
    }

    public void setNoItemStatus(String noItemStatus) {
        this.noItemStatus = noItemStatus;
    }

    public void setSqlKey(String sqlKey) {
        this.sqlKey = sqlKey;
    }

    public Map<String, Object> getItemInfos() {
        return itemInfos;
    }
    
    public List<ItemInfo> getItemInfoList() {
        return itemInfoList;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }
    
    /**
     * setPublish 方法 
     * @param publish 方法
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * setSyncFlage 方法 
     * @param syncFlage 方法
     */
    public void setSyncFlage(String syncFlage) {
        this.syncFlage = syncFlage;
    }

}
