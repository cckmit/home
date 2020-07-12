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

/**
 * 告警阈值信息Bean MALS_NM_THRESHOLD_VALVE_T
 * @author <a href="mailto:wang-rongguang@neusoft.com">wang-rongguang</a>
 * @version 1.0.0 18 Mar 2012
 */
public class ThresholdDomain {
    // 告警阈值ID
    private String id;
    /*告警唯一标识*/
    private String alarmIdentityID;
    /*告警的影响描述*/
    private String alarmImpact;
    // 阈值名称
    private String eventName;

    // 告警类型
    private String warnType;

    // 告警等级
    private String level;

    // 阈值符号
    private String perCondition;

    // 阈值符号描述
    private String perConditionName;

    // 阈值
    private String value;

    // 协议类型
    private String dealType;

    // 是否发送邮件
    private String isEmail;

    // 是否发送短信
    private String isPhone;

    // 描述
    private String eventTypeDesc;

    // 告警等级名称
    private String levelName;

    // 告警类型名称
    private String typeName;

    // MIB主键
    private String mibId;

    // 性能指标名称
    private String mibName;

    // 所属设备类型
    private String agentType;

    // 类型
    private String typeId;

    // 创建时间
    private String createTime;

    // 内容标题
    private String tcTitle;

    // 内容id
    private String tcId;

    // oid
    private String oid;

    // 阈值
    private String value2;

    // 区间
    private String interval;

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

    private String query;

    private String qtype;

    /**
     * 业务实例标识
     */
    private String systemId;
    
    /**
     * 业务实例名称
     */
    private String systemName;
    
    /**
     * 查询增加
     */
    private String rowId;

    private String agentName;

    private String levelId;
    
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    public String getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(String isPhone) {
        this.isPhone = isPhone;
    }

    public String getEventTypeDesc() {
        return eventTypeDesc;
    }

    public void setEventTypeDesc(String eventTypeDesc) {
        this.eventTypeDesc = eventTypeDesc;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMibId() {
        return mibId;
    }

    public void setMibId(String mibId) {
        this.mibId = mibId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public void setPerCondition(String perCondition) {
        this.perCondition = perCondition;
    }

    public String getPerCondition() {
        return perCondition;
    }

    public void setPerConditionName(String perConditionName) {
        this.perConditionName = perConditionName;
    }

    public String getPerConditionName() {
        return perConditionName;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setTcTitle(String tcTitle) {
        this.tcTitle = tcTitle;
    }

    public String getTcTitle() {
        return tcTitle;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcId() {
        return tcId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getInterval() {

        String value1 = this.getValue();
        String value2 = this.getValue2();
        String perCondition = this.getPerCondition();
        
        if(perCondition.equals("1") || perCondition.equals("2")){
            interval = value1;
        }else if(perCondition.equals("3")){
            if(null != value1 && !value1.equals("")){
                interval = "大于等于" + value1;
            }
            if(null != value2 && !value2.equals("")){
                interval += "  小于等于" + value2;
            }
        }
        return interval;
    }

    public String getMibName() {
        return mibName;
    }

    public void setMibName(String mibName) {
        this.mibName = mibName;
    }

    public String getAlarmIdentityID() {
        return alarmIdentityID;
    }

    public void setAlarmIdentityID(String alarmIdentityID) {
        this.alarmIdentityID = alarmIdentityID;
    }

    public String getAlarmImpact() {
        return alarmImpact;
    }

    public void setAlarmImpact(String alarmImpact) {
        this.alarmImpact = alarmImpact;
    }

}
