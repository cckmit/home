package com.neusoft.mid.cloong.host.pm.impl;

import java.util.List;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.pm.PMCreate;
import com.neusoft.mid.cloong.host.pm.PMCreateReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthAdaType;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp.PMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMStatus;

import com.neusoft.mid.cloong.rpws.private1072.pm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.pm.CreateSRVReq;
import com.neusoft.mid.cloong.rpws.private1072.pm.CreateSRVResp;
import com.neusoft.mid.cloong.rpws.private1072.pm.Response;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaIPInfoSet;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaIPInfoSetList;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaNICIPInfoSet;
import com.neusoft.mid.cloong.rpws.private1072.pm.EthAdaNICIPInfoSetList;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVInfoSet;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVInfoSetList;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.pm.SRVServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送创建物理机操作请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午02:33:06
 */
public class PMCreateImpl implements PMCreate {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<SRVServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    private IbatisDAO ibatisDAO;

    private static LogService logger = LogService.getLogger(PMCreateImpl.class);

    public RPPPMCreateResp createPM(PMCreateReq req) {
    	RPPPMCreateResp rppResp = new RPPPMCreateResp();
        try {
            // 打开发送客户端
            SRVServicePort client = common.openClient(req, timeout, SRVServicePort.class);

            // 拼装请求报文
            CreateSRVReq createSRVReq = new CreateSRVReq();
            RPPPMCreateResp errorResp = genWSReq(req, createSRVReq);
            if (errorResp != null) {
                return errorResp;
            }

            // 拼装请求报文头
            Authorization authInfo = new Authorization();
            authInfo.setTimestamp(req.getTimestamp());
            authInfo.setTransactionID(req.getTransactionID());
            authInfo.setZoneID(req.getResourcePoolPartId());

            // 声明应答报文头
            Holder<Response> respHeader = new Holder<Response>();

            // 执行创建物理机
            CreateSRVResp webserviceResp = client.createSRV(createSRVReq, authInfo, respHeader);

            // 组装响应
            assembleResp(rppResp, webserviceResp, respHeader);

        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.SENDMSG_ERROR, "向资源池发送消息异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_SENDMSG_ERROR);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送请求成功，获取的响应码为[").append(rppResp.getResultCode()).append("]，响应描述为[")
                .append(rppResp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return rppResp;
    }

    /**
     * 生成webservice请求
     * @author fengj<br>
     *         2015年2月25日 下午2:15:00
     * @param req 请求内容
     * @param createSRVReq 要生成的webservice请求Bean
     * @return 处理结果,如果为null,说明请求成功
     */
    private RPPPMCreateResp genWSReq(PMCreateReq req, CreateSRVReq createSRVReq) {
    	createSRVReq.setCount((Integer.valueOf(req.getNum())));
    	createSRVReq.setAppID(req.getAppId());
    	createSRVReq.setAppName(req.getAppName());
    	createSRVReq.setSecurity(req.getSecurity());
        setEthInfo(req, createSRVReq);

        if (PMCreateModel.TemplateModel.equals(req.getCreateModel())) {//模板创建
            createSRVReq.setParamFlag("0");
            createSRVReq.setResourceTemplateID(req.getStandardId());
        } else if (PMCreateModel.CustomModel.equals(req.getCreateModel())) {//资源创建
        	createSRVReq.setParamFlag("1");
            SRVResourceInfo srvResourceInfo = new SRVResourceInfo();
            srvResourceInfo.setServerType(req.getServerType());
            srvResourceInfo.setSRVName(req.getPmName());
            srvResourceInfo.setCPUType(req.getCPUType());
            srvResourceInfo.setMemorySize(req.getMemorySizsMB());
            srvResourceInfo.setOSSize(req.getOsSizeGB());
            srvResourceInfo.setEthAdaNum(req.getEthAdaNum());
            
            if (EthAdaType.QIAN_MB.equals(req.getEthAdaType())) {
            	srvResourceInfo.setEthAdaType("1");
            } else if (EthAdaType.WAN_MB.equals(req.getEthAdaType())) {
            	srvResourceInfo.setEthAdaType("2");
            }else {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "接收到的PM创建方式标示符错误:" + req.getCreateModel().getValue());
                return assmbleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR);
            }
            
            srvResourceInfo.setSCSIAdaNum(req.getSCSIAdaNum());
            srvResourceInfo.setHBANum(req.getHBANum());
            srvResourceInfo.setHBAType(req.getHBAType());
            srvResourceInfo.setOS(req.getOsId());
            
            createSRVReq.setSRVResourceInfo(srvResourceInfo);
        } else {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "接收到的PM创建方式标示符错误:" + req.getCreateModel().getValue());
            return assmbleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR);
        }
        return null;
    }

    /**
     * 设置网卡信息 EthAdaIPNicInfo--List<EthAdaIPInfoSet>--EthAdaNICIPInfoSet
     * @author fengj<br>
     *         2015年2月25日 下午12:03:11
     * @param req 原始请求Bean
     * @param createPMReq webservice请求Bean
     */
    private void setEthInfo(PMCreateReq req, CreateSRVReq createSRVReq) {
        EthAdaIPInfoSetList ethAdaIPInfoSetList = new EthAdaIPInfoSetList();
        createSRVReq.setEthAdaIPInfo(ethAdaIPInfoSetList);

        for (List<EthInfo> pmEthListTemp : req.getEthList()) {
        	
            EthAdaIPInfoSet pmEthInfoSetTemp = new EthAdaIPInfoSet();
            ethAdaIPInfoSetList.getEthAdaIPInfoSet().add(pmEthInfoSetTemp);
            
            EthAdaNICIPInfoSetList ethAdaNICIPInfoSetList = new EthAdaNICIPInfoSetList();
            pmEthInfoSetTemp.setEthAdaIPNicInfo(ethAdaNICIPInfoSetList);
            
            for (EthInfo ethInfo : pmEthListTemp) {
                EthAdaNICIPInfoSet ethAdaNICIPInfoSet = new EthAdaNICIPInfoSet();
                ethAdaNICIPInfoSet.setIP(ethInfo.getIp());
                ethAdaNICIPInfoSet.setIPSubnetmask(ethInfo.getSubNetMask());
                ethAdaNICIPInfoSet.setIPDefaultGateway(ethInfo.getDefaultGateWay());
                ethAdaNICIPInfoSet.setVlanID(ethInfo.getVlanId());
                
                if (PMEthPurpose.Business.equals(ethInfo.getPurpose())) {
                	ethAdaNICIPInfoSet.setPurPose("2");
                } else if (PMEthPurpose.IPStorage.equals(ethInfo.getPurpose())) {
                	ethAdaNICIPInfoSet.setPurPose("3");
                }
                
                ethAdaNICIPInfoSetList.getEthAdaNICIPInfoSet().add(ethAdaNICIPInfoSet);
            }
        }

    }

    /**
     * 将webservice应答报文转换为内部应答报文
     * @author fengj<br>
     *         2015年2月25日 下午4:24:16
     * @param rppResp 要转换的内部结果应答
     * @param webserviceRespBody 资源池返回的应答报文
     * @param webserviceRespHeader 资源池返回的应答报文头
     */
    private void assembleResp(RPPPMCreateResp rppResp, CreateSRVResp webserviceRespBody,
            Holder<Response> webserviceRespHeader) {
        rppResp.setFaultstring(webserviceRespBody.getFaultString());
        rppResp.setResultCode(webserviceRespHeader.value.getResultCode());
        SRVInfoSetList pmInfoList = webserviceRespBody.getSRVInfo();

        if (pmInfoList != null) {
            for (SRVInfoSet webservicePMInfo : pmInfoList.getSRVInfoSet()) {
                PMInfo rppPMInfo = new PMInfo();
                rppPMInfo.setPmId(webservicePMInfo.getSRVID());
                rppPMInfo.setOperationIP(webservicePMInfo.getOperationIP());
                rppPMInfo.setUserName(webservicePMInfo.getUserName());
                rppPMInfo.setPassWord(webservicePMInfo.getPassWord());
                switch (webservicePMInfo.getStatus()) {
                case PM_STATUS_UNAVAILABLE:
                	rppPMInfo.setStatus(PMStatus.STATUSERROR);
                    break;
                case PM_STATUS_POWER:
                	rppPMInfo.setStatus(PMStatus.RUNNING);
                    break;
                case PM_STATUS_RUNNING:
                	rppPMInfo.setStatus(PMStatus.RUNNING);
                    break;
                case PM_STATUS_POWEROFF:
                	rppPMInfo.setStatus(PMStatus.STOP);
                    break;
                case PM_STATUS_SLEEPING:
                	rppPMInfo.setStatus(PMStatus.PAUSE);
                    break;
                case PM_STATUS_PROCESSING:
                	rppPMInfo.setStatus(PMStatus.PROCESSING);
                    break;
                default:
                	rppPMInfo.setStatus(PMStatus.STATUSERROR);
                    break;
                }

                rppResp.getPmInfoList().add(rppPMInfo);
            }
        }

    }

    /**
     * 生成反映异常的应答bean
     * @author fengj<br>
     *         2015年2月25日 下午4:30:14
     * @param code 错误码
     * @return 生成的异常应答
     */
    private RPPPMCreateResp assmbleErrorResp(InterfaceResultCode code) {
        RPPPMCreateResp resp = new RPPPMCreateResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    /**
     * 物理机状态——不可用（安装失败）
     */
    public static final int PM_STATUS_UNAVAILABLE = 0;

    /**
     * 物理机状态——已加电（资源池查询物理机如果未被申请）
     */
    public static final int PM_STATUS_POWER = 1;

    /**
     * 物理机状态——运行
     */
    public static final int PM_STATUS_RUNNING = 2;

    /**
     * 物理机状态——关机
     */
    public static final int PM_STATUS_POWEROFF = 3;

    /**
     * 物理机状态——休眠（资源池不支持此状态对应暂停）
     */
    public static final int PM_STATUS_SLEEPING = 4;

    /**
     * 物理机状态——处理中(OS系统正在安装)
     */
    public static final int PM_STATUS_PROCESSING = 5;

    public WebServiceClientFactory<SRVServicePort> getCommon() {
        return common;
    }

    public void setCommon(WebServiceClientFactory<SRVServicePort> common) {
        this.common = common;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }
}
