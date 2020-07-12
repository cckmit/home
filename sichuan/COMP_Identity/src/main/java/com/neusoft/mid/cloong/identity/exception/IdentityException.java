/*******************************************************************************
 * @(#)IdentityException.java 2014年1月17日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.exception;

/**
 * 身份安全验证模块的异常基类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月17日 下午1:29:08
 */
public class IdentityException extends Exception {
    
    /**
     * serialVersionUID : 系列号标识
     */
    private static final long serialVersionUID = 2365202902632061419L;

    /**
     * 
     * 创建一个新的实例 IdentityException 
     */
    public IdentityException(){
        super();
    }
    
    /**
     * 
     * 创建一个新的实例 IdentityException
     * @param message 异常消息
     */
    public IdentityException(String message){
        super(message);
    }
    

}
