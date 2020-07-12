package com.neusoft.mid.cloong.host.vm.impl;

import java.util.List;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.VMCreate;
import com.neusoft.mid.cloong.host.vm.VMCreateReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp.VMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOSDiskType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMStatus;
import com.neusoft.mid.cloong.rpws.private1072.vm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vm.CreateVMReq;
import com.neusoft.mid.cloong.rpws.private1072.vm.CreateVMResp;
import com.neusoft.mid.cloong.rpws.private1072.vm.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaIPInfoSet;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaIPInfoSetList;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaNICIPInfoSet;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaNICIPInfoSetList;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMInfoSet;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMInfoSetList;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送创建虚拟机操作请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午02:33:06
 */
public class VMCreateImpl implements VMCreate {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<VMServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    private IbatisDAO ibatisDAO;

    private static LogService logger = LogService.getLogger(VMCreateImpl.class);

    public RPPVMCreateResp createVM(VMCreateReq req) {
        RPPVMCreateResp rppResp = new RPPVMCreateResp();
        try {
            // 打开发送客户端
            VMServicePort client = common.openClient(req, timeout, VMServicePort.class);

            // 拼装请求报文
            CreateVMReq createVMReq = new CreateVMReq();
            RPPVMCreateResp errorResp = genWSReq(req, createVMReq);
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

            // 执行创建虚拟机
            CreateVMResp webserviceResp = client.createVM(createVMReq, authInfo, respHeader);

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
     * @param createVMReq 要生成的webservice请求Bean
     * @return 处理结果,如果为null,说明请求成功
     */
    private RPPVMCreateResp genWSReq(VMCreateReq req, CreateVMReq createVMReq) {
        createVMReq.setCount((Integer.valueOf(req.getNum())));
        createVMReq.setAppID(req.getAppId());
        createVMReq.setAppName(req.getAppName());
        createVMReq.setSecurity(req.getSecurity());
        createVMReq.setPmId(req.getPmId());
        setEthInfo(req, createVMReq);

        if (VMCreateModel.TemplateModel.equals(req.getCreateModel())) {
            createVMReq.setParamFlag("0");
            createVMReq.setResourceTemplateID(req.getStandardId());
        } else if (VMCreateModel.CustomModel.equals(req.getCreateModel())) {
            createVMReq.setParamFlag("1");
            VMResourceInfo vmResourceInfo = new VMResourceInfo();
            vmResourceInfo.setCPUNum(req.getCpuNum());
            vmResourceInfo.setMemorySize(req.getMemorySizsMB());
            vmResourceInfo.setOSSize(req.getOsSizeGB());
            vmResourceInfo.setVFCHBANum(req.getvFCHBANum());
            vmResourceInfo.setVMName(req.getVmName());
            vmResourceInfo.setVMOS(req.getOsId());
            vmResourceInfo.setVSCSIAdaNum(req.getvSCSIAdaNum());
            
            if (VMOSDiskType.LOCAL.equals(req.getOsDiskType())) {
                vmResourceInfo.setOSDiskType("0");
            } else if (VMOSDiskType.FC_SAN.equals(req.getOsDiskType())) {
                vmResourceInfo.setOSDiskType("1");
            } else if (VMOSDiskType.OTHER.equals(req.getOsDiskType())) {
                vmResourceInfo.setOSDiskType("99");
            } else {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "接收到的VM创建方式标示符错误:" + req.getCreateModel().getValue());
                return assmbleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR);
            }

            createVMReq.setVMResourceInfo(vmResourceInfo);
        } else {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "接收到的VM创建方式标示符错误:" + req.getCreateModel().getValue());
            return assmbleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR);
        }
        return null;
    }

    /**
     * 设置网卡信息
     * @author fengj<br>
     *         2015年2月25日 下午12:03:11
     * @param req 原始请求Bean
     * @param createVMReq webservice请求Bean
     */
    private void setEthInfo(VMCreateReq req, CreateVMReq createVMReq) {
        VEthAdaIPInfoSetList vEthAdaIPInfoSetList = new VEthAdaIPInfoSetList();
        createVMReq.setVEthAdaIPInfo(vEthAdaIPInfoSetList);

        for (List<EthInfo> vmEthList : req.getEthList()) {
            VEthAdaIPInfoSet vmEthInfoSet = new VEthAdaIPInfoSet();
            vEthAdaIPInfoSetList.getVEthAdaIPInfoSet().add(vmEthInfoSet);
            VEthAdaNICIPInfoSetList vEthAdaNICIPInfoSetList = new VEthAdaNICIPInfoSetList();
            vmEthInfoSet.setVEthAdaIPNicInfo(vEthAdaNICIPInfoSetList);
            for (EthInfo ethInfo : vmEthList) {
                VEthAdaNICIPInfoSet vEthAdaNICIPInfoSet = new VEthAdaNICIPInfoSet();
                vEthAdaNICIPInfoSet.setEth(ethInfo.getName());
                vEthAdaNICIPInfoSet.setIP(ethInfo.getIp());
                vEthAdaNICIPInfoSet.setIPSubnetmask(ethInfo.getSubNetMask());
                vEthAdaNICIPInfoSet.setIPDefaultGateway(ethInfo.getDefaultGateWay());
                vEthAdaNICIPInfoSet.setVlanID(ethInfo.getVlanId());
                vEthAdaNICIPInfoSetList.getVEthAdaNICIPInfoSet().add(vEthAdaNICIPInfoSet);

                if (VMEthPurpose.Business.equals(ethInfo.getPurpose())) {
                    vEthAdaNICIPInfoSet.setPurPose("2");
                } else if (VMEthPurpose.IPStorage.equals(ethInfo.getPurpose())) {
                    vEthAdaNICIPInfoSet.setPurPose("3");
                }
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
    private void assembleResp(RPPVMCreateResp rppResp, CreateVMResp webserviceRespBody,
            Holder<Response> webserviceRespHeader) {
        rppResp.setFaultstring(webserviceRespBody.getFaultString());
        rppResp.setResultCode(webserviceRespHeader.value.getResultCode());
        VMInfoSetList vmInfoList = webserviceRespBody.getVMInfo();

        if (vmInfoList != null) {
            for (VMInfoSet webserviceVMInfo : vmInfoList.getVMInfoSet()) {
                VMInfo rppVMInfo = new VMInfo();
                rppVMInfo.setOperationIP(webserviceVMInfo.getOperationIP());
                rppVMInfo.setPassWord(webserviceVMInfo.getPassword());
                rppVMInfo.setUserName(webserviceVMInfo.getUserName());
                rppVMInfo.setVlanId(webserviceVMInfo.getVlanIDs().getVlanID());
                rppVMInfo.setVmId(webserviceVMInfo.getVMID());
                rppVMInfo.setVmName(webserviceVMInfo.getVMName());
                rppVMInfo.setUrl(webserviceVMInfo.getOperationURL());

                switch (webserviceVMInfo.getStatus()) {
                case VM_STATUS_INSTALLING:
                    rppVMInfo.setStatus(VMStatus.CREATING);
                    break;
                case VM_STATUS_STARTING:
                    rppVMInfo.setStatus(VMStatus.PROCESSING);
                    break;
                case VM_STATUS_RUNNING:
                    rppVMInfo.setStatus(VMStatus.RUNNING);
                    break;
                case VM_STATUS_STOP:
                    rppVMInfo.setStatus(VMStatus.STOP);
                    break;
                case VM_STATUS_PAUSE:
                    rppVMInfo.setStatus(VMStatus.PAUSE);
                    break;
                case VM_STATUS_UNAVAILABLE:
                    rppVMInfo.setStatus(VMStatus.STATUSERROR);
                    break;
                default:
                    rppVMInfo.setStatus(VMStatus.STATUSERROR);
                    break;
                }

                rppResp.getVmInfoList().add(rppVMInfo);
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
    private RPPVMCreateResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVMCreateResp resp = new RPPVMCreateResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    /**
     * 虚拟机状态——安装中
     */
    public static final int VM_STATUS_INSTALLING = 0;

    /**
     * 虚拟机状态——启动中
     */
    public static final int VM_STATUS_STARTING = 1;

    /**
     * 虚拟机状态——运行
     */
    public static final int VM_STATUS_RUNNING = 2;

    /**
     * 虚拟机状态——关机
     */
    public static final int VM_STATUS_STOP = 3;

    /**
     * 虚拟机状态——挂起/暂停
     */
    public static final int VM_STATUS_PAUSE = 4;

    /**
     * 虚拟机状态——不可用
     */
    public static final int VM_STATUS_UNAVAILABLE = 5;

    public WebServiceClientFactory<VMServicePort> getCommon() {
        return common;
    }

    public void setCommon(WebServiceClientFactory<VMServicePort> common) {
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
