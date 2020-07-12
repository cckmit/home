package com.neusoft.mid.cloong.host.pm;

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
 * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息 - 物理机修改
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014-5-19 上午8:50:00
 */
public class PMModifyRequest {

    /**
     * 判断使用配置文件URL
     */
    private String pmModifyUseOthUrl = "false";

    /**
     * 配置文件URL
     */
    private String pmModifyUrl;

    /**
     * 日志log
     */
    private static LogService logger = LogService.getLogger(PMModifyRequest.class);

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
        // 判断物理机修改请求是否单独使用配置文件URL
        if (("true").equals(pmModifyUseOthUrl) && req instanceof PMModifyReq) {
            factory.setAddress(pmModifyUrl);
            if (logger.isDebugEnable()) {
                logger.debug("物理机修改请求资源池地址开关开启，变更为使用配置文件pmModifyUrl：" + pmModifyUrl);
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
     * 设置pmModifyUseOthUrl字段数据
     * @param pmModifyUseOthUrl The pmModifyUseOthUrl to set.
     */
    public void setPmModifyUseOthUrl(String pmModifyUseOthUrl) {
        this.pmModifyUseOthUrl = pmModifyUseOthUrl;
    }

    /**
     * 设置pmModifyUrl字段数据
     * @param pmModifyUrl The pmModifyUrl to set.
     */
    public void setPmModifyUrl(String pmModifyUrl) {
        this.pmModifyUrl = pmModifyUrl;
    }

}
