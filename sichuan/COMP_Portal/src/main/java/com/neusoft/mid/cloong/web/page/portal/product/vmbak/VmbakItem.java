/*******************************************************************************
 * @(#)VmbakItem.java 2014-1-24
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.vmbak;

import java.io.Serializable;

/**
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-24 上午3:37:54
 */
public class VmbakItem implements Serializable{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String itemID;

    private String itemName;

    private String discSize;

    private String description;

    private String recommend;

    /**
     * 获取itemID字段数据
     * @return Returns the itemID.
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * 设置itemID字段数据
     * @param itemID The itemID to set.
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * 获取itemName字段数据
     * @return Returns the itemName.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置itemName字段数据
     * @param itemName The itemName to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取discSize字段数据
     * @return Returns the discSize.
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * 设置discSize字段数据
     * @param discSize The discSize to set.
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    /**
     * 获取description字段数据
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description字段数据
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取recommend字段数据
     * @return Returns the recommend.
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * 设置recommend字段数据
     * @param recommend The recommend to set.
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
