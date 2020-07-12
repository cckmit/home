/*******************************************************************************
 * @(#)VMCreateProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.AcquireStandardResPoolId;
import com.neusoft.mid.cloong.common.StandardResPoolIdInfo;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.VMCreate;
import com.neusoft.mid.cloong.host.vm.VMCreateReq;
import com.neusoft.mid.cloong.host.vm.VMStateQuery;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp.VMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOSDiskType;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机创建处理器
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-26 下午04:05:05
 */
public class VMCreateProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMCreateProcessor.class);

    private IbatisDAO ibatisDAO;

    @Value("${vm.create.defaultVal.Security}")
    private String defaultSecurity;

    @Value("${vm.create.defaultVal.OsDiskType}")
    private int defaultOsDiskType;

    @Value("${vm.create.defaultVal.EthPurPose")
    private String defaultEthPurPose;

    /**
     * 创建VM发送到资源池后查询资源状态及IP、带宽线程池
     */
    private ThreadPoolTaskExecutor senderCreateVMTask;

    /**
     * 和资源池交互的虚拟机创建接口
     */
    private VMCreate vmCreator;

    /**
     * 请求长度正则表达式,判断长度是否<=31位
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 和资源池交互的虚拟机状态查询接口
     */
    private VMStateQuery vmStateQuery;

    private static final String IP_VLAN_BIND_STATE_VAILD = "0";

    private static final String IP_VLAN_BIND_STATE_TOVAILD = "2";

    private int interval = 5;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMCreateReq vmCreateReq = (RPPVMCreateReq) reqCxt.getAttribute(RPPBaseReq.REQ_BODY);
        RPPVMCreateResp vmResp = new RPPVMCreateResp();

        // 校验输入
        if (!validateInputPara(vmCreateReq)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, vmResp);
            return FAILURE;
        }
        VMCreateReq vmReq = null;
        try {

            VMCreateModel createModel = vmCreateReq.getCreateModel();
            switch (createModel) {
            case TemplateModel:
                vmReq = assembleTemplateModel(vmCreateReq);
                break;
            case CustomModel:
                vmReq = assembleCustomModel(vmCreateReq);
                break;
            default:
                break;
            }

        } catch (ServiceStopException e1) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e1);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmResp);
            return FAILURE;
        } catch (UnmatchException e2) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e2);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmResp);
            return FAILURE;
        } catch (Exception e3) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e3);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmResp);
            return FAILURE;
        }
        if (vmReq.getResourceUrl() == null || vmReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, vmResp);
            return FAILURE;
        }
        // 发送创建请求
        vmResp = vmCreator.createVM(vmReq);

        // 判断请求是否成功，如果没有成功，则不再查询状态
        if (vmResp.getResultCode().equals("00000000")) {
            // 更新虚拟机网卡绑定Vlan同IP段的对应关系为生效
            updateVlanIPsegmentBind(vmCreateReq, respCxt, vmResp);

            // 发起虚拟机状态查询任务
            if (!startQueryCreateProgress(respCxt, vmResp, vmReq)) {
                return FAILURE;
            }
        }

        assebleRes(vmResp, respCxt);

        return SUCCESS;
    }

    /**
     * 更新Vlan同IP段的绑定关系状态
     * @author fengj<br>
     *         2015年3月4日 下午5:21:55
     * @param vmCreateReq
     * @throws SQLException
     */
    private boolean updateVlanIPsegmentBind(RPPVMCreateReq vmCreateReq, RuntimeContext respCxt, RPPVMCreateResp vmResp) {

        Set<String> vlanIdSet = new HashSet<String>();
        try {
            for (List<EthInfo> vmEthList : vmCreateReq.getEthList()) {
                for (EthInfo ethInfo : vmEthList) {
                    vlanIdSet.add(ethInfo.getVlanId());
                }
            }

            if (vlanIdSet.size() > 0) {
                Map<String, Object> dbUpdateParam = new HashMap<String, Object>();
                dbUpdateParam.put("toBindState", IP_VLAN_BIND_STATE_VAILD);
                dbUpdateParam.put("fromBindState", IP_VLAN_BIND_STATE_TOVAILD);
                List<String> vlanList = new ArrayList<String>();
                vlanList.addAll(vlanIdSet);
                dbUpdateParam.put("vlanList", vlanList);
                ibatisDAO.updateData("updateVlanIPsegmentBind", dbUpdateParam);
            }

        } catch (SQLException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，更新VLAN和IP段绑定关系发生异常,Vlan信息为" + vlanIdSet, e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATABASE_ERROR, respCxt, vmResp);
            return false;
        }
        return true;
    }

    /**
     * 开始启动块存储创建进度查询线程
     * @author fengj<br>
     *         2015年3月4日 下午3:59:56
     * @param respCxt
     * @param vmResp
     * @param vmReq
     */
    private boolean startQueryCreateProgress(RuntimeContext respCxt, RPPVMCreateResp vmResp, VMCreateReq vmReq) {
        int numThread = 0;
        // 根据要创建的虚拟机数量，启动N个线程，将该线程放入线程池
        for (VMInfo tempVMInfo : vmResp.getVmInfoList()) {
            try {
                numThread++;
                CreateVMSenderImp createVMSenderImp = new CreateVMSenderImp();
                createVMSenderImp.setIbatisDAO(ibatisDAO);
                createVMSenderImp.setInterval(interval);
                createVMSenderImp.setVmStateQuery(vmStateQuery);
                createVMSenderImp.assembleVMStateReq(tempVMInfo.getVmId(), vmReq);
                senderCreateVMTask.execute(createVMSenderImp);
                if (logger.isDebugEnable()) {
                    logger.debug("启动线程第" + numThread + "个...");
                }
            } catch (TaskRejectedException e1) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e1);
                assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_THREAD_ERROR, respCxt, vmResp);
                return false;
            } catch (Exception e2) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e2);
                assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_THREAD_ERROR, respCxt, vmResp);
                return false;
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("线程执行完毕，共执行" + numThread + "个线程！");
        }
        return true;
    }

    /**
     * 生成通过模板方式创建虚拟机请求
     * @param vmCreateReq 虚拟机创建请求
     * @return 生成好的请求Bean
     */
    private VMCreateReq assembleTemplateModel(RPPVMCreateReq vmCreateReq) throws ServiceStopException, UnmatchException {

        VMCreateReq vmReq = assembleCommonReq(vmCreateReq);

        // 通过虚拟机规格及镜像id获取对应资源池模板ID.
        StandardResPoolIdInfo standardResPoolIdInfo = new StandardResPoolIdInfo();
        standardResPoolIdInfo.setOsId(vmReq.getOsId());
        standardResPoolIdInfo.setResPoolId(vmReq.getResourcePoolId());
        standardResPoolIdInfo.setResPoolPartId(vmReq.getResourcePoolPartId());
        standardResPoolIdInfo.setStandardId(vmReq.getStandardId());
        acquireStandardResPoolId.setIbatisDAO(ibatisDAO);
        String templateId = acquireStandardResPoolId.getVMStandardResPoolId(standardResPoolIdInfo);
        if (null == templateId || templateId.isEmpty()) {
            if (logger.isDebugEnable()) {
                logger.debug("虚拟机规格对应模板ID不存在，无法创建虚拟机！");
            }
            return null;
        } else {
            // 设置对应模板ID
            vmReq.setStandardId(templateId);
        }

        if (logger.isDebugEnable()) {
            logger.debug("获得创建虚拟机的请求为" + vmReq.toString());
        }
        return vmReq;
    }

    /**
     * 生成通过自定义方式创建虚拟机请求
     * @param vmCreateReq 虚拟机创建请求
     * @return 生成好的请求Bean
     */
    private VMCreateReq assembleCustomModel(RPPVMCreateReq vmCreateReq) throws ServiceStopException, UnmatchException {

        VMCreateReq vmReq = assembleCommonReq(vmCreateReq);

        // 通过虚拟机规格及镜像id获取对应资源池模板ID.
        vmReq.setVmName(vmCreateReq.getVmName());
        vmReq.setCpuNum(vmCreateReq.getCpuNum());
        vmReq.setMemorySizsMB(vmCreateReq.getMemorySizsMB());
        if (vmCreateReq.getOsDiskType() != null) {
            vmReq.setOsDiskType(VMOSDiskType.fromValue(vmCreateReq.getOsDiskType()));
        } else {
            vmReq.setOsDiskType(VMOSDiskType.fromValue(this.defaultOsDiskType));
        }
        vmReq.setOsSizeGB(vmCreateReq.getOsSizeGB());
        if (vmCreateReq.getvSCSIAdaNum() != RPPBaseReq.INT_DEFAULT_VAL) {
            vmReq.setvSCSIAdaNum(vmCreateReq.getvSCSIAdaNum());
        }
        if (vmCreateReq.getvFCHBANum() != RPPBaseReq.INT_DEFAULT_VAL) {
            vmReq.setvFCHBANum(vmCreateReq.getvFCHBANum());
        } 

        if (logger.isDebugEnable()) {
            logger.debug("获得创建虚拟机的请求为" + vmReq.toString());
        }
        return vmReq;
    }

    /**
     * 生成请求Bean的公共部分
     * @param vmCreateReq
     * @return 返回已经填充完公共部分的请求Bean
     * @throws UnmatchException
     * @throws ServiceStopException
     */
    VMCreateReq assembleCommonReq(RPPVMCreateReq vmCreateReq) throws ServiceStopException, UnmatchException {
        VMCreateReq vmReq = new VMCreateReq();

        vmReq.setCreateModel(vmCreateReq.getCreateModel());
        vmReq.setStandardId(vmCreateReq.getStandardId());
        vmReq.setNum(vmCreateReq.getCount());
        vmReq.setOsId(vmCreateReq.getOsId());
        vmReq.setAppId(vmCreateReq.getAppId());
        vmReq.setAppName(vmCreateReq.getAppName());
        vmReq.setPmId(vmCreateReq.getPmId());
        if (!StringUtils.isBlank(vmCreateReq.getSecurity())) {
            vmReq.setSecurity(vmCreateReq.getSecurity());
        } else {
            vmReq.setSecurity(this.defaultSecurity);
        }

        // 为每个网卡设置必选的用途属性
        for (List<EthInfo> vmEthInfoList : vmCreateReq.getEthList()) {
            for (EthInfo ethInfo : vmEthInfoList) {
                if (ethInfo.getPurpose() == null) {
                    ethInfo.setPurpose(VMEthPurpose.fromValue(this.defaultEthPurPose));
                }
            }
        }

        vmReq.setEthList(vmCreateReq.getEthList());

        vmReq.setResourcePoolId(vmCreateReq.getResourcePoolId());
        vmReq.setResourcePoolPartId(vmCreateReq.getResourcePoolPartId());
        vmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        vmReq.setTransactionID(this.getTransactonGen().generateTransactionId(vmReq.getResourcePoolId(),vmReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(vmReq.getResourcePoolId());
        vmReq.setPassword(resourceInfo.getUserPassword());
        vmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return vmReq;
    }

    /**
     * 将业务层应答报文封装到传输层报文中
     * @param vmResp 业务层应答报文
     * @param resp 传输层应答报文
     */
    private void assebleRes(RPPVMCreateResp vmResp, RuntimeContext resp) {
        resp.setAttribute(RPPVMCreateResp.RESP_BODY, vmResp);
    }

    private boolean validateInputPara(RPPVMCreateReq req) {
        if (req.getCreateModel() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机创建方式属性CREATE_MODEL", null);
            return false;
        }

        if (VMCreateModel.TemplateModel.equals(req.getCreateModel()) && StringUtils.isBlank(req.getStandardId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机的规格属性STANDARD_ID", null);
            return false;
        }

        if (VMCreateModel.CustomModel.equals(req.getCreateModel())) {
            if (req.getCpuNum() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机的VCPU个数属性CPU_NUM", null);
                return false;
            }

            if (req.getMemorySizsMB() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机的内存容量属性MEMORY_SIZE", null);
                return false;
            }

//            if (req.getOsSizeGB() < 1) {
//                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机的本地磁盘容量属性OS_SIZE", null);
//                return false;
//            }
        }

        if (StringUtils.isBlank(req.getOsId()) || (!P_STRING.matcher(req.getOsId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机镜像ID OS_ID", null);
            return false;
        }
        if (req.getCount() < 1) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机数量NUM", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppId()) || (!P_STRING.matcher(req.getAppId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机所属业务APP_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppName()) || (!P_STRING.matcher(req.getAppName()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机所属业务APP_NAME", null);
            return false;
        }
        return true;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public VMCreate getVmCreator() {
        return vmCreator;
    }

    public void setVmCreator(VMCreate vmCreator) {
        this.vmCreator = vmCreator;
    }

    public ThreadPoolTaskExecutor getSenderCreateVMTask() {
        return senderCreateVMTask;
    }

    public void setSenderCreateVMTask(ThreadPoolTaskExecutor senderCreateVMTask) {
        this.senderCreateVMTask = senderCreateVMTask;
    }

    public VMStateQuery getVmStateQuery() {
        return vmStateQuery;
    }

    public void setVmStateQuery(VMStateQuery vmStateQuery) {
        this.vmStateQuery = vmStateQuery;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setAcquireStandardResPoolId(AcquireStandardResPoolId acquireStandardResPoolId) {
        this.acquireStandardResPoolId = acquireStandardResPoolId;
    }

}
