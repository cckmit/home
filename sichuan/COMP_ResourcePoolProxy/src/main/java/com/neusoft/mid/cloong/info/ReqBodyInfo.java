package com.neusoft.mid.cloong.info;

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
     * 操作流水号
     */
    private String serialNum;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("资源池ID为：").append(this.resourcePoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resourcePoolPartId).append("\n");
        sb.append("资源池密码为：").append(this.password).append("\n");
        sb.append("资源池URL为：").append(this.resourceUrl).append("\n");
        sb.append("消息序列号为：").append(this.transactionID).append("\n");
        sb.append("系统时间戳为：").append(this.timestamp).append("\n");
        sb.append("操作流水号为：").append(this.serialNum).append("\n");
        return sb.toString();
    }
}
