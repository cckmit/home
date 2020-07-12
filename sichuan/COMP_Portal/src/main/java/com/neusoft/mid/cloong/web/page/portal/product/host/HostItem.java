/*******************************************************************************
 * @(#)iteamBean.java 2013-1-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.portal.product.host;

/**
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.1 $ 2013-1-22 上午10:17:18
 */
public class HostItem {

    private String itemID;

    private String itemName;

    private String cpuNum;

    private String cpuType;

    private String ramSize;

    private String discSize;

    private String description;

    private String recommend;

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
     * @return the cpuNum
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * @param cpuNum the cpuNum to set
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    /**
     * @return the ramSize
     */
    public String getRamSize() {
        return ramSize;
    }

    /**
     * @param ramSize the ramSize to set
     */
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
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

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

}
