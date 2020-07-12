package com.neusoft.mid.cloong.ebs.core;

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
     * 
     * getResultCode TODO getResultCode
     * @return TODO resultCode
     */
    public String getResultCode() {
        return resultCode;
    }
    
    /**
     * 
     * setResultCode TODO setResultCode
     * @param resultCode TODO resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    
    /**
     * 
     * getResultMessage TODO getResultMessage
     * @return TODO resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }
    /**
     * 
     * setResultMessage TODO setResultMessage
     * @param resultMessage TODO resultMessage
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
