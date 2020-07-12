/*******************************************************************************
 * @(#)DeleteFileStorageResp.java 2018年5月9日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.fileStore.core;

/**分布式文件存储删除相应
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月9日 上午9:14:56
 */
public class DeleteFileStorageResp {

    private String faultString;
    
    /**
     * 响应码
     */
    private String resultCode;

    /**
     * 响应描述
     */
    private String resultMessage;

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
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
    
}
