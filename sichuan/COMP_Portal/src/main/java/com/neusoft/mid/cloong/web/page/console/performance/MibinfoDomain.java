/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import java.util.List;

/**
 * MIB信息Bean MALS_NM_MIB_T
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class MibinfoDomain {

    // MIB库ID
    private String mibId;

    // 采集对象OID
    private String oid;

    // 采集对象名称
    private String mibName;

    // 采集对象类型
    private String typeId;

    // 采集对象类型名称
    private String typeName;

    // 描述
    private String description;

    private String createTime;

    // 阈值对象
    private List<ThresholdDomain> ruleBeanList;

    private String isCollection;

    private String isCollectionName;

    private String parentId;

    private String parentName;

    private String isUpdate;

    /**
     * 2012-07-17 zhaoc 添加 排序名称
     * @return
     */
    private String sortName;

    /**
     * 2012-07-17 zhaoc 添加 排序规则 升序/降序 asc/desc
     * @return
     */
    private String sortOrder;

    private String qtype;

    private String query;

    private String acquisitionValue;
    
    /**
     * 指标改造新增字段
     * @return
     */
    /**
     * 指标单位
     */
    private String indexUnit;
    /**
     * 指标分组
     */
    private String indexGroup;
    /**
     * 是否显示在折线图上
     */
    private String ifShowLine;
    /**
     * 生成列名
     */
    private String colomeName;
    //标识功耗和进程指标是否配置
    private String indexGroupP;
    private String indexGroupC;
    
    /**
     * 业务实例标识
     */
    private String systemId;
    /**
     * 业务实例名称
     */
    private String systemName;
    
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getIndexUnit() {
        return indexUnit;
    }

    public void setIndexUnit(String indexUnit) {
        this.indexUnit = indexUnit;
    }

    public String getIndexGroup() {
        return indexGroup;
    }

    public void setIndexGroup(String indexGroup) {
        this.indexGroup = indexGroup;
    }

    public String getIfShowLine() {
        return ifShowLine;
    }

    public void setIfShowLine(String ifShowLine) {
        this.ifShowLine = ifShowLine;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getMibId() {
        return mibId;
    }

    public void setMibId(String mibId) {
        this.mibId = mibId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMibName() {
        return mibName;
    }

    public void setMibName(String mibName) {
        this.mibName = mibName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuleBeanList(List<ThresholdDomain> ruleBeanList) {
        this.ruleBeanList = ruleBeanList;
    }

    public List<ThresholdDomain> getRuleBeanList() {
        return ruleBeanList;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsCollectionName(String isCollectionName) {
        this.isCollectionName = isCollectionName;
    }

    public String getIsCollectionName() {
        return isCollectionName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getAcquisitionValue() {
        return acquisitionValue;
    }

    public void setAcquisitionValue(String acquisitionValue) {
        this.acquisitionValue = acquisitionValue;
    }

    public String getColomeName() {
        return colomeName;
    }

    public void setColomeName(String colomeName) {
        this.colomeName = colomeName;
    }

    public String getIndexGroupP() {
        return indexGroupP;
    }

    public void setIndexGroupP(String indexGroupP) {
        this.indexGroupP = indexGroupP;
    }

    public String getIndexGroupC() {
        return indexGroupC;
    }

    public void setIndexGroupC(String indexGroupC) {
        this.indexGroupC = indexGroupC;
    }

}
