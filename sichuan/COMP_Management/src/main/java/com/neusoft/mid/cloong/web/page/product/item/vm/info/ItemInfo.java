package com.neusoft.mid.cloong.web.page.product.item.vm.info;

import com.neusoft.mid.cloong.common.core.ItemStatus;
import com.neusoft.mid.cloong.common.core.ItemType;
import com.neusoft.mid.cloong.common.core.RecommendStatus;
import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 条目实例
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-5 下午04:24:45
 */
public class ItemInfo extends BaseInfo {

    private String itemId;

    private String itemName;

    private ItemType itemType;

    private String standardId;

    private String standardType;

    private String templateId;

    private String catalogId;

    private String sellStartTime;

    private String sellEndTime;

    private Long maxNum;

    private Long minNum;

    private RecommendStatus recommend;

    private ItemStatus status;

    private ItemStatus noItemStatus;

    private String description;

    private String beginCreateTime;

    private String endCreateTime;
    /**
	 *判断是否发布
	 */
    private String publish;

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

    public ItemStatus getNoItemStatus() {
        return noItemStatus;
    }

    public void setNoItemStatus(ItemStatus noItemStatus) {
        this.noItemStatus = noItemStatus;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
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

    public Long getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Long maxNum) {
        this.maxNum = maxNum;
    }

    public Long getMinNum() {
        return minNum;
    }

    public void setMinNum(Long minNum) {
        this.minNum = minNum;
    }

    public RecommendStatus getRecommend() {
        return recommend;
    }

    public void setRecommend(RecommendStatus recommend) {
        this.recommend = recommend;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
    
}
