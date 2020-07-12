/*******************************************************************************
 * @(#)InnerException.java 2016年8月22日
 *
 * Copyright 2016 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.H3C.exception;

/**
 * 内部异常类
 * @author <a href="mailto:he.jf@neusoft.com">hejf</a>
 * @version $Revision 1.0 $ 2016年8月22日 下午12:33:55
 */
public class H3CException extends Exception{

	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 7450893151502039594L;

	/***
	 * 操作返回码
	 */
	private int code = -1;

	/***
	 * 错误消息
	 */
	private String message;

	/***
	 * 操作标题
	 */
	private String title;

	/**
	 * 获取code字段数据
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置code字段数据
	 * @param code The code to set.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 获取message字段数据
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置message字段数据
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取title字段数据
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置title字段数据
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
