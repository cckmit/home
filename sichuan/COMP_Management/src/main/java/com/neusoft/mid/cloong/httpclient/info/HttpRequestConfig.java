package com.neusoft.mid.cloong.httpclient.info;

public class HttpRequestConfig {

    private int connectionReqTimeout;

    private int connectTimeout;

    private int socketTimeout;
    
    private String contentEncoding = "UTF-8";

    public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public void setConnectionRequestTimeout(int timeout) {
        connectionReqTimeout = timeout;
    }

    public void setConnectTimeout(int timeout) {
        connectTimeout = timeout;
    }

    public void setSocketTimeout(int timeout) {
        socketTimeout = timeout;
    }

    public int getConnectionReqTimeout() {
        return connectionReqTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }
}
