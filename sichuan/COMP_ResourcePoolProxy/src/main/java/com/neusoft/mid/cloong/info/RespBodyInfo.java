package com.neusoft.mid.cloong.info;

/**
 * 资源池返回的公共响应信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-5 上午10:17:36
 */
public class RespBodyInfo {

    /**
     * 响应码
     */
    private String resultCode;

    /**
     * 响应描述
     */
    private String resultMessage;

    /**
     * 资源池ID
     */
    private String resourcePoolId;
    
    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 消息序列号
     */
    private String transactionID;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

	/**
	 * @return the resourcePoolPartId
	 */
	public String getResourcePoolPartId() {
		return resourcePoolPartId;
	}

	/**
	 * @param resourcePoolPartId the resourcePoolPartId to set
	 */
	public void setResourcePoolPartId(String resourcePoolPartId) {
		this.resourcePoolPartId = resourcePoolPartId;
	}

}
