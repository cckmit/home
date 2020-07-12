/*******************************************************************************
 * @(#)ItemBaseAction.java 2013-2-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.vm.action;

import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;

/**
 * 条目公共属性字段
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-26 上午10:23:15
 */
public class ItemBaseAction extends BaseAction {
    
    private static final long serialVersionUID = 8796432879488409124L;

    /**
     * spring配置 创建类型 0虚拟机 1物理机 详细参照ItemIype枚举
     */
    protected String itemType;

    /**
     * spring配置 状态类型：0、保存 1、保存并提交(待审批)
     */
    protected String statusType;

    /**
     * spring配置 生成条目Id 中的字符 （例如：vm,ph）
     */
    protected String itemIdType;

    /**
     * 用于返回界面信息
     */
    protected CreateResult resultMessage;

    /**
     * 获取界面保存的json字符串
     */
    protected String itemJson;

    // 用于validation验证
    /**
     * 条目ID
     */
    protected String itemId;

	/**
     * 条目名称
     */
    protected String itemName;

	/**
     * 服务目录ID
     */
    protected String catalogId;

	/**
     * 最大购买数量
     */
    protected String maxNum;

	/**
     * 最小购买数量
     */
    protected String minNum;

	/**
     * 销售开始时间
     */
    protected String sellStartTime;

	/**
     * 销售结束时间
     */
    protected String sellEndTime;

	/**
     * 描述
     */
    protected String description;

	/**
     * 规格ID
     */
    protected String standardId;

	/**
     * 旧规格ID
     */
    protected String oldStandardId;

    public String getOldStandardId() {
        return oldStandardId;
    }

    public void setOldStandardId(String oldStandardId) {
        this.oldStandardId = oldStandardId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getSellStartTime() {
        return sellStartTime;
    }

    public void setSellStartTime(String sellStartTime) {
        this.sellStartTime = sellStartTime;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public CreateResult getResultMessage() {
        return resultMessage;
    }

    public void setItemJson(String itemJson) {
        this.itemJson = itemJson;
    }

    public String getItemIdType() {
        return itemIdType;
    }

    public void setItemIdType(String itemIdType) {
        this.itemIdType = itemIdType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }
}
