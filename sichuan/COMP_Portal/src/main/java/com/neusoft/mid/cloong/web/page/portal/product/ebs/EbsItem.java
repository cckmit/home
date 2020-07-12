/*******************************************************************************
 * @(#)EbsItem.java 2013-3-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.ebs;

/**
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-28 上午10:18:38
 */
public class EbsItem {

    private String itemID;

    private String itemName;

    private String discSize;

    private String description;

    private String recommend;
    /**
	*服务目录名称
	**/
    private String catalogName;

    /**
     * @return the itemID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the discSize
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * @param discSize the discSize to set
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the recommend
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * @param recommend the recommend to set
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * @return the catalogName
     */
    public String getCatalogName() {
        return catalogName;
    }
    
    /**
     * @param catalogName the catalogName to set
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

}
