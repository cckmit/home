package com.neusoft.mid.cloong.pm.core;

/**
 * 向发送资源池请求时所需的公共信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-5 上午10:14:52
 */
public class ReqBodyInfo {

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 资源池密码
     */
    private String password;

    /**
     * 资源池URL
     */
    private String resourceUrl;

    /**
     * 消息序列号
     */
    private String transactionID;

    /**
     * 系统时间戳
     */
    private String timestamp;

    /**
     * 
     * getResourceUrl TODO 方法
     * @return TODO
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * 
     * setResourceUrl TODO 方法
     * @param resourceUrl TODO
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    /**
     * 
     * getResourcePoolId TODO 方法
     * @return TODO
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 
     * setResourcePoolId TODO 方法
     * @param resourcePoolId TODO
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * 
     * getResourcePoolPartId TODO 方法
     * @return TODO
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 
     * setResourcePoolPartId TODO 方法
     * @param resourcePoolPartId TODO
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 
     * getPassword TODO 方法
     * @return TODO
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * setPassword TODO 方法
     * @param password TODO
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * getTransactionID TODO 方法
     * @return TODO
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * 
     * setTransactionID TODO 方法
     * @param transactionID TODO
     */
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * 
     * getTimestamp TODO 方法
     * @return TODO
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * setTimestamp TODO 方法
     * @param timestamp TODO
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
