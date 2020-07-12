package com.neusoft.mid.cloong.httpclient.info;

import java.io.InputStream;
import java.util.Map;

public class Response {

    public Response(int statusCode, String body, Map<String, String> head) {
        this.statusCode = statusCode;
        this.body = body;
        this.head = head;
    }
    
    public Response(int statusCode, InputStream body, Map<String, String> head) {
        this.statusCode = statusCode;
        this.bodyStream = body;
        this.head = head;
    }

    /**
     * http响应状态码
     */
    private int statusCode;

    /**
     * http响应消息体
     */
    private String body;
    

    /**
     * http响应消息体流
     */
    private InputStream bodyStream;


	/**
     * http响应头
     */
    private Map<String, String> head;

	/**
	 * 获取statusCode字段数据
	 * @return Returns the statusCode.
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 设置statusCode字段数据
	 * @param statusCode The statusCode to set.
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 获取body字段数据
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * 设置body字段数据
	 * @param body The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
    public InputStream getBodyStream() {
		return bodyStream;
	}

	public void setBodyStream(InputStream bodyStream) {
		this.bodyStream = bodyStream;
	}

	/**
	 * 获取head字段数据
	 * @return Returns the head.
	 */
	public Map<String, String> getHead() {
		return head;
	}

	/**
	 * 设置head字段数据
	 * @param head The head to set.
	 */
	public void setHead(Map<String, String> head) {
		this.head = head;
	}


}
