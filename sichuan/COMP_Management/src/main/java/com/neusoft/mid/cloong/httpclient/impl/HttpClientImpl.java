package com.neusoft.mid.cloong.httpclient.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.neusoft.mid.cloong.httpclient.HttpClient;
import com.neusoft.mid.cloong.httpclient.info.HttpRequestConfig;
import com.neusoft.mid.cloong.httpclient.info.Response;
import com.neusoft.mid.iamp.logger.LogService;

public class HttpClientImpl implements HttpClient {
	
	private static LogService log = LogService.getLogger(HttpClientImpl.class);
    
    private static final String CONTENT_TYPE = HTTP.CONTENT_TYPE;

    /**
     * 设置实例
     */
    private RequestConfig config;

    /**
     * 字符集
     */
    private String encoding = "UTF-8";

	/**
     * public constructor
     */
    public HttpClientImpl() {
    	if(log.isDebugEnable()){
    		log.debug("Create the HttpClientImpl");
    	}
    }

    public void destory() {
    	if(log.isDebugEnable()){
    		log.debug("Destory the HttpClientImpl");
    	}
    }
    
    private CloseableHttpClient getCloseableHttpClient(){
    	return HttpClients.createDefault();
    }

    
    /**
     * 生成Header数组
     *
     * @param headMap 请求头name、value对
     * @return Header数组
     */
    private Header[] generateHeaders(Map<String, String> headMap) {

        Header[] headers = new Header[headMap.size()];

        int i = 0;
        for (String key : headMap.keySet()) {
            headers[i] = new BasicHeader(key, headMap.get(key));
            i++;
        }

        return headers;
    }

    /**
     * 生成HeaderMap
     *
     * @param headers Header数组
     * @return HeaderMap
     */
    private Map<String, String> generateHeaderMap(Header[] headers) {

        Map<String, String> headMap = new HashMap<String, String>();

        for (Header header : headers) {
            headMap.put(header.getName(), header.getValue());
        }

        return headMap;
    }

    /**
     * 响应转换为字符串
     *
     * @param resp     http响应
     * @param encoding 字符集编码
     * @return 响应消息体字符串
     * @throws IOException IO异常
     */
    private String fmt2String(HttpResponse resp, String encoding) throws IOException {
        String body = "";
        if (resp.getEntity() != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(resp.getEntity(), encoding);
        }
        EntityUtils.consume(resp.getEntity());
        return body;
    }

    /**
     * 整合Header数组为可打印的字符串
     *
     * @param headers Header数组
     * @return 字符串
     */
    private String convertHeaders2String(Header[] headers) {

        StringBuilder sb = new StringBuilder();

        for (Header header : headers) {
            sb.append(header.getName()).append(":").append(header.getValue()).append("\n");
        }

        return sb.toString();
    }
    
    private void setHeaderConfig(Map<String, String> head,HttpRequestBase httpRequest){
    	 httpRequest.setConfig(config);
         Header[] reqHeaders = generateHeaders(head);
         httpRequest.setHeaders(reqHeaders);
    }
    
    private void printReuqestInfo(HttpRequestBase httpRequest,HttpEntity entity){
    	//if(log.isDebugEnabled()){
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("\n**********打印HttpClient请求消息开始*********\n");
	        sb.append("method: "+httpRequest.getMethod());
	        sb.append("\n");
	        sb.append("url: "+httpRequest.getURI().toString());
	        sb.append("\n");
	        sb.append("Request Header: " + convertHeaders2String(httpRequest.getAllHeaders()));
	        sb.append("\n");
	        if(null != entity){
	            try {
	            	for (Header headers : httpRequest.getAllHeaders()) {
						if(headers.getName().equalsIgnoreCase(CONTENT_TYPE)){
							String contentType = headers.getValue().toLowerCase();
			            	if(ContentType.APPLICATION_OCTET_STREAM.getMimeType().equalsIgnoreCase(contentType)){
								sb.append("Request body: 不是文本，不打印body体，contentType 为：" +contentType);
							}else{
								sb.append("Request Body: "+ EntityUtils.toString(entity));
							}
						}
					}
	            	
	    		} catch (Exception e) {
	    			log.warn("print request infomation exception",e);
	    		}
	        }
        	//log.debug(sb.toString());
        //}
	    	sb.append("\n**********打印HttpClient请求消息结束*********\n");
	        log.info(sb.toString());
    }
    
    private void printReuqestInfo(HttpRequestBase httpRequest){
    	printReuqestInfo(httpRequest,null);
    }
    
    private void printResponseInfo(int statusCode,Header[] respHeaders,String bodyStr){
    	//if(log.isDebugEnabled()){
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("\n**********打印HttpClient响应消息开始*********\n");
	    	sb.append("Response Code: "+ statusCode);
	    	sb.append("\n");
	    	sb.append("Response Header: "+ convertHeaders2String(respHeaders));
	    	sb.append("\n");
			if(null!=bodyStr){
				sb.append("Response Body: "+ bodyStr);
			}else{
				sb.append("Response body: 不是文本，不打印body体");
			}
			/*
	    	for (Header headers : respHeaders) {
	    		if(headers.getName().equalsIgnoreCase(CONTENT_TYPE)){
					String contentType = headers.getValue().toLowerCase();
					if(ContentType.APPLICATION_OCTET_STREAM.getMimeType().equalsIgnoreCase(contentType)){
						sb.append("Response body: 不是文本，不打印body体，contentType 为：" +contentType);
					}else{
						sb.append("Response Body: "+ bodyStr);
					}
				}
			}*/
	    	sb.append("\n**********打印HttpClient响应消息结束*********\n");
        	//log.debug(sb.toString());
        	log.info(sb.toString());
        //}
    }
    
    private void printResponseInfo(int statusCode,Header[] respHeaders){
    	printResponseInfo(statusCode,respHeaders,null);
    }

    /**
     * 封装发送请求部分代码
     *
     * @param client      httpClient
     * @param httpRequest http请求
     * @return 封装好的http响应
     * @throws IOException IO异常
     */
    private Response getResponse(CloseableHttpClient client, HttpRequestBase httpRequest) throws IOException {

        CloseableHttpResponse resp = null;

        Response returnResp = null;
       
        try {
            resp = client.execute(httpRequest);
            
            Header[] respHeaders = resp.getAllHeaders();
            
            int statusCode = resp.getStatusLine().getStatusCode();
            
            Header contentTypeHeader = resp.getFirstHeader(CONTENT_TYPE);
            if(null != contentTypeHeader && ContentType.APPLICATION_OCTET_STREAM.getMimeType().equalsIgnoreCase(contentTypeHeader.getValue())){
            	 printResponseInfo(statusCode,respHeaders);
            	 ByteArrayInputStream  inputStream = new ByteArrayInputStream(EntityUtils.toByteArray(resp.getEntity()));
            	 returnResp = new Response(statusCode, inputStream, generateHeaderMap(respHeaders));
            }else{
                 String bodyStr = fmt2String(resp, encoding);
                 printResponseInfo(statusCode,respHeaders,bodyStr);
                 returnResp = new Response(statusCode, bodyStr, generateHeaderMap(respHeaders));
            }
            
        } catch (Exception e) {
            log.error("Send Http Request cause a exception", e);
            throw new IOException(e);
        }finally{
        	  if (resp != null) {
                  resp.close();
              }
        	  if(client != null){
        		  client.close();
        	  }
        }

        return returnResp;
    }

    @Override
    public Response get(Map<String, String> head, String url) throws IOException {

        log.info("Sending get request to "+url);

        CloseableHttpClient client = getCloseableHttpClient();

        HttpGet httpget = new HttpGet(url);
        
        setHeaderConfig(head,httpget);
        
        printReuqestInfo(httpget);
        
        return getResponse(client, httpget);
    }

    @Override
    public Response post(Map<String, String> head, String body, String url) throws IOException {

    	log.info("Sending post request to "+url);

        CloseableHttpClient client = getCloseableHttpClient();

        HttpPost httpPost = new HttpPost(url);

        String contentType = getContentType(head);

        StringEntity entity = new StringEntity(body, ContentType.create(contentType, encoding));

        httpPost.setEntity(entity);
        
        setHeaderConfig(head,httpPost);
        
        printReuqestInfo(httpPost,entity);

        return getResponse(client, httpPost);
    }
    
    @Override
    public Response post(Map<String, String> head, InputStream body, String url) throws IOException {

    	log.info("Sending post request to "+url);

        CloseableHttpClient client = getCloseableHttpClient();

        HttpPost httpPost = new HttpPost(url);

        String contentType = getContentType(head);

        InputStreamEntity entity = new InputStreamEntity(body, ContentType.create(contentType, encoding));

        httpPost.setEntity(entity);
        
        setHeaderConfig(head,httpPost);
        
        printReuqestInfo(httpPost);

        return getResponse(client, httpPost);
    }

    @Override
    public Response head(Map<String, String> head, String url) throws IOException {
    	
    	log.info("Sending head request to "+url);

    	CloseableHttpClient client = getCloseableHttpClient();

        HttpHead httpHead = new HttpHead(url);

        setHeaderConfig(head,httpHead);
        
        printReuqestInfo(httpHead);
        
        return getResponse(client, httpHead);

    }

    @Override
    public Response options(Map<String, String> head, String url) throws IOException {
    	
    	log.info("Sending options request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpOptions httpOptions = new HttpOptions(url);

        setHeaderConfig(head,httpOptions);
        
        printReuqestInfo(httpOptions);
        
        return getResponse(client, httpOptions);
    }

    @Override
    public Response put(Map<String, String> head, String body, String url) throws IOException {
    	
    	log.info("Sending put request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpPut httpPut = new HttpPut(url);

        String contentType = getContentType(head);

        StringEntity entity = new StringEntity(body, ContentType.create(contentType, encoding));

        httpPut.setEntity(entity);
        
        setHeaderConfig(head,httpPut);
        
        printReuqestInfo(httpPut,entity);

        return getResponse(client, httpPut);
    }
    
    @Override
    public Response put(Map<String, String> head, InputStream body, String url) throws IOException {
    	
    	log.info("Sending put request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpPut httpPut = new HttpPut(url);

        String contentType = getContentType(head);

        InputStreamEntity entity = new InputStreamEntity(body, ContentType.create(contentType, encoding));

        httpPut.setEntity(entity);
        
        setHeaderConfig(head,httpPut);
        
        printReuqestInfo(httpPut);

        return getResponse(client, httpPut);
    }
    
    @Override
    public Response patch(Map<String, String> head, String body, String url) throws IOException {
    	
    	log.info("Sending patch request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpPatch httpPatch = new HttpPatch(url);

        String contentType = getContentType(head);

        StringEntity entity = new StringEntity(body, ContentType.create(contentType, encoding));

        httpPatch.setEntity(entity);
        
        setHeaderConfig(head,httpPatch);
        
        printReuqestInfo(httpPatch,entity);

        return getResponse(client, httpPatch);
    }
    
    @Override
    public Response patch(Map<String, String> head, InputStream body, String url) throws IOException {
    	
    	log.info("Sending patch request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpPatch httpPatch = new HttpPatch(url);

        String contentType = getContentType(head);

        InputStreamEntity entity = new InputStreamEntity(body, ContentType.create(contentType, encoding));

        httpPatch.setEntity(entity);
        
        setHeaderConfig(head,httpPatch);
        
        printReuqestInfo(httpPatch);

        return getResponse(client, httpPatch);
    }

    @Override
    public Response trace(Map<String, String> head, String url) throws IOException {
    	
    	log.info("Sending trace request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpTrace httpTrace = new HttpTrace(url);

        setHeaderConfig(head,httpTrace);
        
        printReuqestInfo(httpTrace);
        
        return getResponse(client, httpTrace);
    }

    @Override
    public Response delete(Map<String, String> head, String url) throws IOException {
    	
    	log.info("Sending delete request to "+url);
    	
    	CloseableHttpClient client = getCloseableHttpClient();

        HttpDelete httpDelete = new HttpDelete(url);

        setHeaderConfig(head,httpDelete);
        
        printReuqestInfo(httpDelete);
        
        return getResponse(client, httpDelete);
    }

    @Override
    public void setConfig(HttpRequestConfig config) {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(config.getConnectionReqTimeout());
        builder.setConnectTimeout(config.getConnectTimeout());
        builder.setSocketTimeout(config.getSocketTimeout());
        this.config = builder.build();
        this.encoding = config.getContentEncoding();
    }
    
    private String getContentType(Map<String, String> head){
        /* 内容的类型从header中取 */
        String contentType = head.get(CONTENT_TYPE);
        
        if (contentType == null) {
            contentType = head.get(CONTENT_TYPE.toLowerCase());
        }

        /* 如果没有设置，默认为json */
        if (contentType == null) {
            contentType = ContentType.APPLICATION_JSON.getMimeType();
        }
        
        return contentType;
    }
}
