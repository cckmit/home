package com.neusoft.mid.cloong.resourcePool.webservice.privateCloud;

import java.util.Map;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Authorization;

/**
 * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-16 下午03:44:16
 */
public class WebServiceClientFactory<T> {

    private Map<String, String> serviceURL;

    /**
     * 打开向资源池进行请求的客户端类
     * @param req 向发送资源池请求时所需的公共信息 timeout 接收超时时间ms
     * @param timeout 超时时间
     * @param interfaceClass 接口类
     * @return
     */
    public T openClient(ReqBodyInfo req, long timeout, Class<T> interfaceClass) {
    	System.out.println("req.url:="+req.getResourceUrl());
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(interfaceClass);
        factory.setAddress(req.getResourceUrl() + "/" + serviceURL.get(interfaceClass.getSimpleName()));
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        T client = (T) factory.create();
        Conduit conduit = (ClientProxy.getClient(client).getConduit());
        HTTPConduit hc = (HTTPConduit) conduit;
        HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
        clientPolicy.setReceiveTimeout(timeout);
        hc.setClient(clientPolicy);
        return client;
    }

    /**
     * 生成私有云请求报文头
     * @author fengj<br>
     *         2015年2月12日 下午12:40:44
     * @param reqBody 请求信息
     * @return 生成好的报文头
     */
    public Authorization genReqHeader(ReqBodyInfo reqBody) {

        Authorization auth = new Authorization();
        auth.setTimestamp(reqBody.getTimestamp());
        auth.setTransactionID(reqBody.getTransactionID());
        auth.setZoneID(reqBody.getResourcePoolPartId());

        return auth;
    }

    /**
     * 设置serviceURL字段数据
     * @param serviceURL The serviceURL to set.
     */
    public void setServiceURL(Map<String, String> serviceURL) {
        this.serviceURL = serviceURL;
    }

}
