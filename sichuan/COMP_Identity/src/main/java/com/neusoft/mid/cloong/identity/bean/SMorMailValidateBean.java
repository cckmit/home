/*******************************************************************************
 * @(#)SMorMailValidateInfo.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean;

/**
 * 短信或者邮件找回密码时生成的动态密钥信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月14日 下午3:09:53
 */
public class SMorMailValidateBean {

    /**
     * 用户id
     */
    private String userId;
    
    /**
     * 生成的短信验证码或者url密钥
     */
    private String keyWord;
    
    /**
     * 时间戳
     */
    private String timeStamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
}
