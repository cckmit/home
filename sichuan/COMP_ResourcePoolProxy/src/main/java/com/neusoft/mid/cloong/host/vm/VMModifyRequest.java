package com.neusoft.mid.cloong.host.vm;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.idc.blend.idc.idc_wsdl.Idc;
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
public class VMModifyRequest {

    /**
     * 判断使用配置文件URL
     */
    private String vmModifyUseOthUrl = "false";

    /**
     * 配置文件URL
     */
    private String vmModifyUrl;

    /**
     * 日志log
     */
    private static LogService logger = LogService.getLogger(VMModifyRequest.class);

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
        // 判断虚拟机修改请求是否单独使用配置文件URL
        if (("true").equals(vmModifyUseOthUrl) && req instanceof VMModifyReq) {
            factory.setAddress(vmModifyUrl);
            if (logger.isDebugEnable()) {
                logger.debug("虚拟机修改请求资源池地址开关开启，变更为使用配置文件vmModifyUrl：" + vmModifyUrl);
            }
        } else {
            factory.setAddress(req.getResourceUrl());
        }
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

    /**
     * 设置vmModifyUseOthUrl字段数据
     * @param vmModifyUseOthUrl The vmModifyUseOthUrl to set.
     */
    public void setVmModifyUseOthUrl(String vmModifyUseOthUrl) {
        this.vmModifyUseOthUrl = vmModifyUseOthUrl;
    }

    /**
     * 设置vmModifyUrl字段数据
     * @param vmModifyUrl The vmModifyUrl to set.
     */
    public void setVmModifyUrl(String vmModifyUrl) {
        this.vmModifyUrl = vmModifyUrl;
    }

}
