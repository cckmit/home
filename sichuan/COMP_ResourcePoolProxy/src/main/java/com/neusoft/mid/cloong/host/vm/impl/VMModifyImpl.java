package com.neusoft.mid.cloong.host.vm.impl;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.VMModify;
import com.neusoft.mid.cloong.host.vm.VMModifyReq;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMModifyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMStatus;
import com.neusoft.mid.cloong.rpws.private1072.vm.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.vm.ModifyVMReq;
import com.neusoft.mid.cloong.rpws.private1072.vm.ModifyVMResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.ModifyVMResp;
import com.neusoft.mid.cloong.rpws.private1072.vm.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaIPInfoModifySet;
import com.neusoft.mid.cloong.rpws.private1072.vm.VEthAdaIPInfoModifySetList;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMConfigInfo;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送修改虚拟机操作请求
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.1 $ 2014-5-13 下午02:33:06
 */
public class VMModifyImpl implements VMModify {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<VMServicePort> common;

    /**
     * 接收超时时间ms
     */
    private long timeout = 10000;

    /**
     * ibatisDAO
     */
    private IbatisDAO ibatisDAO;

    /**
     * logger
     */
    private static LogService logger = LogService.getLogger(VMModifyImpl.class);

    /**
     * modifyVM 修改虚拟机
     * @param req
     * @return VMModifyResp
     */
    public RPPVMModifyResp modifyVM(VMModifyReq req) {
        RPPVMModifyResp resp = new RPPVMModifyResp();
        try {
            // 打开发送客户端
            VMServicePort client = common.openClient(req, timeout, VMServicePort.class); // 和快照用的是融合支撑云的接口

            // 生成请求报文体
            ModifyVMReq webserviceReqBody = genWebserviceReqBody(req);

            // 生成请求报文头
            Authorization webserviceReqHeader = genWebserviceReqHeader(req);

            // 声明应答报文头
            Holder<Response> webserviceRespHeader = new Holder<Response>();

            // 发送
            ModifyVMResp webserviceRespBody = client.modifyVM(webserviceReqBody, webserviceReqHeader,
                    webserviceRespHeader);

            // 组装响应
            assembleResp(resp, webserviceRespBody, webserviceRespHeader.value.getResultCode());
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.SENDMSG_ERROR, "向资源池发送消息异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_SENDMSG_ERROR);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送请求成功，获取的响应码为[").append(resp.getResultCode()).append("]，响应描述为[")
                .append(resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }

    /**
     * 生成webservice请求头
     * @param req
     * @return
     */
    Authorization genWebserviceReqHeader(VMModifyReq req) {
        Authorization auth = new Authorization();
        auth.setTimestamp(req.getTimestamp());
        auth.setTransactionID(req.getTransactionID());
        auth.setZoneID(req.getResourcePoolPartId());
        return auth;
    }

    /**
     * 根据请求的内容，生成webservice请求报文体
     * @param req
     * @return
     */
    ModifyVMReq genWebserviceReqBody(VMModifyReq req) {
        ModifyVMReq webserviceReq = new ModifyVMReq();
        webserviceReq.setVMID(req.getVmId());
        ModifyVMResourceInfo modifyVMResourceInfo = new ModifyVMResourceInfo();
        webserviceReq.setVMResourceInfo(modifyVMResourceInfo);
        modifyVMResourceInfo.setCPUNum(req.getCpuNum());
        modifyVMResourceInfo.setMemorySize(req.getRamSize());
        modifyVMResourceInfo.setOSSize(req.getDiscSize());
        modifyVMResourceInfo.setVEthAdaNum(req.getVEthAdaNum());
        modifyVMResourceInfo.setVSCSIAdaNum(req.getvSCSIAdaNum());
        modifyVMResourceInfo.setVMOS(req.getOsId());
        if (req.getOsDiskType() != null) {
            modifyVMResourceInfo.setOSDiskType(req.getOsDiskType().getValue());
        }

        VMConfigInfo vmConfigInfo = new VMConfigInfo();
        modifyVMResourceInfo.setVMConfig(vmConfigInfo);
        vmConfigInfo.setVMName(req.getVmName());
        VEthAdaIPInfoModifySetList vEthAdaIPInfoModifySetList = new VEthAdaIPInfoModifySetList();
        vmConfigInfo.setVEthAdaIPInfoModify(vEthAdaIPInfoModifySetList);
        for (EthInfo ethInfo : req.getEthList()) {
            VEthAdaIPInfoModifySet vEthAdaIPInfoModifySet = new VEthAdaIPInfoModifySet();
            vEthAdaIPInfoModifySet.setEth(ethInfo.getName());
            vEthAdaIPInfoModifySet.setIP(ethInfo.getIp());
            vEthAdaIPInfoModifySet.setIPDefaultGateway(ethInfo.getDefaultGateWay());
            vEthAdaIPInfoModifySet.setIPSubnetmask(ethInfo.getSubNetMask());
            if (ethInfo.getPurpose() != null) {
                vEthAdaIPInfoModifySet.setPurPose(ethInfo.getPurpose().getValue());
            }
            vEthAdaIPInfoModifySet.setVlanID(ethInfo.getVlanId());
            vEthAdaIPInfoModifySetList.getVEthAdaIPInfoModifySet().add(vEthAdaIPInfoModifySet);
        }
        return webserviceReq;
    }

    /**
     * assembleResp 返回应答
     * @param resp
     * @param resultCode
     * @param modifyVMResp
     */
    private void assembleResp(RPPVMModifyResp resp, ModifyVMResp webserviceRespBody, String resultCode) {
        resp.setResultMessage(webserviceRespBody.getFaultString());
        resp.setResultCode(resultCode);

        if (webserviceRespBody.getVMInfoSet() != null){
            resp.setOperationIP(webserviceRespBody.getVMInfoSet().getOperationIP());
            resp.setVmId(webserviceRespBody.getVMInfoSet().getVMName());
            resp.setVmName(webserviceRespBody.getVMInfoSet().getVMName());
            
            switch (webserviceRespBody.getVMInfoSet().getStatus()) {
            case VM_STATUS_INSTALLING:
                resp.setStatus(VMStatus.CREATING);
                break;
            case VM_STATUS_STARTING:
                resp.setStatus(VMStatus.PROCESSING);
                break;
            case VM_STATUS_RUNNING:
                resp.setStatus(VMStatus.RUNNING);
                break;
            case VM_STATUS_STOP:
                resp.setStatus(VMStatus.STOP);
                break;
            case VM_STATUS_PAUSE:
                resp.setStatus(VMStatus.PAUSE);
                break;
            case VM_STATUS_UNAVAILABLE:
                resp.setStatus(VMStatus.STATUSERROR);
                break;
            default:
                resp.setStatus(VMStatus.STATUSERROR);
                break;
            }
        }
        

    }

    /**
     * assmbleErrorResp返回应答错误信息
     * @param code
     * @return resp
     */
    private RPPVMModifyResp assmbleErrorResp(InterfaceResultCode code) {
        RPPVMModifyResp resp = new RPPVMModifyResp();
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

    /**
     * 设置common字段数据
     * @param common The common to set.
     */
    public void setCommon(WebServiceClientFactory<VMServicePort> common) {
        this.common = common;
    }

    /**
     * 设置timeout字段数据
     * @param timeout The timeout to set.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
