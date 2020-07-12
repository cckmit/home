package com.neusoft.mid.cloong.httpclient.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import com.neusoft.mid.cloong.httpclient.info.Response;

public class HttpClientH3CImpl extends  HttpClientImpl {

	/***
	 * HttpClient head
	 */
	private Map<String, String> head = new HashMap<String, String>();
	
	@PreDestroy
    public void destory() {
		super.destory();
    }

	public Response get(String url) throws IOException {
		return super.get(head, url);
	}

	public Response post(String body, String url) throws IOException {
		return super.post(head, body, url);
	}

	public Response head(String url) throws IOException {
		return super.head(head, url);
	}

	public Response put(String body, String url) throws IOException {
		return super.put(head, body, url);
	}

	public Response options(String url) throws IOException {
		return super.options(head, url);
	}

	public Response trace(String url) throws IOException {
		return super.trace(head, url);
	}

	public Response delete(String url) throws IOException {
		return super.delete(head, url);
	}

	public Response patch(String body, String url) throws IOException {
		return super.patch(head, body, url);
	}

	/**
	 * 获取head字段数据
	 * 
	 * @return Returns the head.
	 */
	public Map<String, String> getHead() {
		return head;
	}

	/**
	 * 设置head字段数据
	 * 
	 * @param head
	 *            The head to set.
	 */
	public void setHead(Map<String, String> head) {
		this.head = head;
	}

}
