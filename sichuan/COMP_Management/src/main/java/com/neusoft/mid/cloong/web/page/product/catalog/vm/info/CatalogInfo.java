package com.neusoft.mid.cloong.web.page.product.catalog.vm.info;

import com.neusoft.mid.cloong.web.BaseInfo;


/**
 * 服务目录
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-11 下午04:13:34
 */
public class CatalogInfo extends BaseInfo{
    private String catalogId;

    private String catalogName;

    private String status;

    private String description;

    private String catalogType;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("服务目录ID为：").append(this.catalogId).append("\n");
        sb.append("服务目录名称为：").append(this.catalogName).append("\n");
        sb.append("状态为：").append(this.status).append("\n");
        sb.append("描述为：").append(this.description).append("\n");
        sb.append("服务目录类型为：").append(this.catalogType).append("\n");
        sb.append("创建时间为：").append(getCreateTime()).append("\n");
        sb.append("创建人为：").append(getCreateUser()).append("\n");
        sb.append("更新时间为：").append(getUpdateTime()).append("\n");
        sb.append("更新人为：").append(getUpdateUser()).append("\n");
        return sb.toString();
    }
}
