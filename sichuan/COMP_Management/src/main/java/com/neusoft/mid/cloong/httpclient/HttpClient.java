package com.neusoft.mid.cloong.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.neusoft.mid.cloong.httpclient.info.HttpRequestConfig;
import com.neusoft.mid.cloong.httpclient.info.Response;

public interface HttpClient {

    /**
     * 执行get方法
     *
     * @param head 请求头
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response get(Map<String, String> head, String url) throws IOException;

    /**
     * 执行post方法
     *
     * @param head 请求头
     * @param body 请求体
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response post(Map<String, String> head, String body, String url) throws IOException;
    
    /**
     * 执行post方法
     *
     * @param head 请求头
     * @param body 请求体
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response post(Map<String, String> head, InputStream body, String url) throws IOException;

    /**
     * 执行head方法
     * <p>p.s. head方法请求和get方法请求基本相同，只是返回响应没有消息体</p>
     *
     * @param head 请求头
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response head(Map<String, String> head, String url) throws IOException;

    /**
     * 执行options方法
     * <p>p.s. OPTIONS方法是用于请求获得由Request-URI标识的资源在请求/响应的通信过程中可以使用的功能选项。通过这个方法，客户端可以在采取具体资源请求之前，决定对该资源采取何种必要措施，或者了解服务器的性能。</p>
     *
     * @param head 请求头
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response options(Map<String, String> head, String url) throws IOException;

    /**
     * 执行put方法
     *
     * @param head 请求头
     * @param body 请求体
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response put(Map<String, String> head, String body, String url) throws IOException;
    
    /**
     * 执行put方法
     *
     * @param head 请求头
     * @param body 请求体
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response put(Map<String, String> head, InputStream body, String url) throws IOException;

    /**
     * 执行trace方法
     * <p>p.s. 用于远程诊断服务器</p>
     *
     * @param head 请求头
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response trace(Map<String, String> head, String url) throws IOException;

    /**
     * 执行delete方法
     *
     * @param head 请求头
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response delete(Map<String, String> head, String url) throws IOException;

    /**
     * 执行patch方法
     * <p>p.s. PATCH操作主要用来更新部分资源</p>
     *
     * @param head 请求头
     * @param body 请求体
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response patch(Map<String, String> head, String body, String url) throws IOException;
    
    /**
     * 执行patch方法
     * <p>p.s. PATCH操作主要用来更新部分资源</p>
     *
     * @param head 请求头
     * @param body 请求体
     * @param url  请求地址
     * @return 响应对象
     * @throws IOException IO异常
     */
    Response patch(Map<String, String> head, InputStream body, String url) throws IOException;

    /**
     * httpClient配置
     * 包括读超时、连接超时、代理、缓存等配置
     *
     * @param config 配置对象
     */
    void setConfig(HttpRequestConfig config);
    
    /***
     * 
     * destory 销毁方法
     */
    void destory() ;

}
