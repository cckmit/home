package com.neusoft.mid.cloong;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.idc.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-16 下午03:44:16
 */
public class RequestCommon {

    /**
     * 打开向资源池进行请求的客户端类
     * @param req 向发送资源池请求时所需的公共信息 timeout 接收超时时间ms
     * @param timeout TODO
     * @return
     */
    public Idc openClient(ReqBodyInfo req,long timeout) {
        ReqBodyThreadLcoal.set(req);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(Idc.class);
        factory.setAddress(req.getResourceUrl());
        factory.getInInterceptors().add(new RespSoapHeaderInteceptor());
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new ReqSoapHeaderInteceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        Idc client = (Idc) factory.create();
        Conduit conduit = (ClientProxy.getClient(client).getConduit());
        HTTPConduit hc = (HTTPConduit) conduit;
        HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
        clientPolicy.setReceiveTimeout(timeout);
        hc.setClient(clientPolicy);
        return client;
    }

    /**
     * 获取请求响应码
     * @return
     */
    public String obtainResultCode() {
        return RespBodyThreadLcoal.get().getResultCode();
    }

    /**
     * 关闭向资源池发送请求的客户端
     * @param resp 资源池返回响应的公用信息
     */
    public void closeClient() {
        ReqBodyThreadLcoal.unset();
        RespBodyThreadLcoal.unset();
    }
}
