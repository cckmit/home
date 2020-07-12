package com.neusoft.mid.cloong.host.vm;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.idc.om.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.ReqBodyThreadLcoal;
import com.neusoft.mid.cloong.ReqSoapHeaderInteceptor;
import com.neusoft.mid.cloong.RespBodyThreadLcoal;
import com.neusoft.mid.cloong.RespSoapHeaderInteceptor;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息 - 虚拟机修改
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014-5-19 上午8:50:00
 */
public class VlanIpRequest {

    /**
     * 日志log
     */
    private static LogService logger = LogService.getLogger(VlanIpRequest.class);

    /**
     * 打开向资源池进行请求的客户端类
     * @param req 向发送资源池请求时所需的公共信息
     * @param timeout 接收超时时间ms
     * @return client
     */
    public Idc openClient(ReqBodyInfo req, long timeout) {
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
     * @return resultCode
     */
    public String obtainResultCode() {
        return RespBodyThreadLcoal.get().getResultCode();
    }

    /**
     * 关闭向资源池发送请求的客户端
     */
    public void closeClient() {
        ReqBodyThreadLcoal.unset();
        RespBodyThreadLcoal.unset();
    }

}
