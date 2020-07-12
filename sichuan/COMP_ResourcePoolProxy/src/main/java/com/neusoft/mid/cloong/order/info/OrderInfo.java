package com.neusoft.mid.cloong.order.info;

/**
 * 订单信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-3-31 上午9:45:12
 */
public class OrderInfo {

    /**
     * 订单状态——生效
     */
    public static final String ORDER_STATUS_EFFECT = "3";

    /** 订单ID */
    private String orderId;

    /** 订单状态 */
    private String status;

    /** 总价格 */
    private String allPrice;

    /** 生效日期 */
    private String effectiveTime;

    /** 计费时长 */
    private String lengthTime;

    /** 计费方式 */
    private String lengthUnit;

    /** 条目ID */
    private String itemId;

    /** 规格ID */
    private String standardId;

    /** 规格类型 */
    private String standardType;

    /** 操作系统ID */
    private String osId;

    /** 订单数量 */
    private String num;

    /** 服务ID */
    private String serviceId;

    /** 实例ID */
    private String caseId;

    /** 实例类型 */
    private String caseType;

    /** 到期时间 */
    private String expireTime;

    /** 创建时间 */
    private String createTime;

    /** 创建人 */
    private String createUser;

    /**
     * 获取orderId字段数据
     * @return Returns the orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId字段数据
     * @param orderId The orderId to set.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取status字段数据
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status字段数据
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取allPrice字段数据
     * @return Returns the allPrice.
     */
    public String getAllPrice() {
        return allPrice;
    }

    /**
     * 设置allPrice字段数据
     * @param allPrice The allPrice to set.
     */
    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    /**
     * 获取effectiveTime字段数据
     * @return Returns the effectiveTime.
     */
    public String getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * 设置effectiveTime字段数据
     * @param effectiveTime The effectiveTime to set.
     */
    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * 获取lengthTime字段数据
     * @return Returns the lengthTime.
     */
    public String getLengthTime() {
        return lengthTime;
    }

    /**
     * 设置lengthTime字段数据
     * @param lengthTime The lengthTime to set.
     */
    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    /**
     * 获取lengthUnit字段数据
     * @return Returns the lengthUnit.
     */
    public String getLengthUnit() {
        return lengthUnit;
    }

    /**
     * 设置lengthUnit字段数据
     * @param lengthUnit The lengthUnit to set.
     */
    public void setLengthUnit(String lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    /**
     * 获取itemId字段数据
     * @return Returns the itemId.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置itemId字段数据
     * @param itemId The itemId to set.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取standardType字段数据
     * @return Returns the standardType.
     */
    public String getStandardType() {
        return standardType;
    }

    /**
     * 设置standardType字段数据
     * @param standardType The standardType to set.
     */
    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    /**
     * 获取osId字段数据
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 设置osId字段数据
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 获取num字段数据
     * @return Returns the num.
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置num字段数据
     * @param num The num to set.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取serviceId字段数据
     * @return Returns the serviceId.
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * 设置serviceId字段数据
     * @param serviceId The serviceId to set.
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取caseType字段数据
     * @return Returns the caseType.
     */
    public String getCaseType() {
        return caseType;
    }

    /**
     * 设置caseType字段数据
     * @param caseType The caseType to set.
     */
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    /**
     * 获取expireTime字段数据
     * @return Returns the expireTime.
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * 设置expireTime字段数据
     * @param expireTime The expireTime to set.
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取createTime字段数据
     * @return Returns the createTime.
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime字段数据
     * @param createTime The createTime to set.
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取createUser字段数据
     * @return Returns the createUser.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置createUser字段数据
     * @param createUser The createUser to set.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

}
