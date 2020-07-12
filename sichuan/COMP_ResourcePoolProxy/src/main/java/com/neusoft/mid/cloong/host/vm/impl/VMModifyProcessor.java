/*******************************************************************************
 * @(#)VMCreateProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.VMModify;
import com.neusoft.mid.cloong.host.vm.VMModifyReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMModifyResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机修改处理器
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.1 $ 2014-5-13 下午04:05:05
 */
public class VMModifyProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMModifyProcessor.class);

    private IbatisDAO ibatisDAO;

    /**
     * 和资源池交互的虚拟机创建接口
     */
    private VMModify vmModify;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 请求中虚拟机操作流水号最大长度
     */
    private static final int SERIAL_NUM_LENGTH = 30;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMModifyReq req = (RPPVMModifyReq) reqCxt.getAttribute(RPPVMModifyReq.REQ_BODY);
        RPPVMModifyResp resp = new RPPVMModifyResp();

        // 校验输入
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }
        VMModifyReq vmModifyReq = null;
        try {
            vmModifyReq = assembleVMReq(req);
        } catch (ServiceStopException e1) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e1);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (UnmatchException e2) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID["+req.getResourcePoolId()+"]获取到资源池信息", e2);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e3) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID["+req.getResourcePoolId()+"]获取到资源池信息", e3);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vmModifyReq.getResourceUrl() == null || vmModifyReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        // 发送创建请求
        RPPVMModifyResp vmResp = vmModify.modifyVM(vmModifyReq);

        assebleRes(vmResp, respCxt);
        if (vmResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("虚拟机编码为[" + vmModifyReq.getVmId() + "]的虚拟机修改成功,本次操作流水号为:" + vmModifyReq.getSerialNum());
        } else {
            logger.info("虚拟机编码为[" + vmModifyReq.getVmId() + "]的虚拟机修改失败,接口响应码为[" + vmResp.getResultCode() + "]，接口响应消息为["
                    + vmResp.getResultMessage() + "],本次操作流水号为:" + vmModifyReq.getSerialNum());
        }
        return SUCCESS;
    }

    /**
     * assembleVMReq 组装请求
     * @param req
     * @return
     * @throws ServiceStopException
     * @throws UnmatchException
     */
    private VMModifyReq assembleVMReq(RPPVMModifyReq req) throws ServiceStopException, UnmatchException {
        VMModifyReq vmModifyReq = new VMModifyReq();
        vmModifyReq.setVmId(req.getVmId());
        vmModifyReq.setCpuNum(req.getCpuNum());
        vmModifyReq.setRamSize(req.getMemorySizsMB());
        vmModifyReq.setDiscSize(req.getOsSizeGB());
        vmModifyReq.setSerialNum(req.getSerialNum());
        vmModifyReq.setVmName(req.getVmName());
        vmModifyReq.setOsDiskType(req.getOsDiskType());
        vmModifyReq.setVEthAdaNum(req.getVEthAdaNum());
        vmModifyReq.setvSCSIAdaNum(req.getvSCSIAdaNum());
        vmModifyReq.setOsId(req.getOsId());
        vmModifyReq.getEthList().addAll(req.getEthList());

        vmModifyReq.setResourcePoolId(req.getResourcePoolId());
        vmModifyReq.setResourcePoolPartId(req.getResourcePoolPartId());
        vmModifyReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        vmModifyReq.setTransactionID(this.getTransactonGen().generateTransactionId(vmModifyReq.getResourcePoolId(),
                vmModifyReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                vmModifyReq.getResourcePoolId());
        vmModifyReq.setPassword(resourceInfo.getUserPassword());
        vmModifyReq.setResourceUrl(resourceInfo.getResourcePoolUrl());

        if (logger.isDebugEnable()) {
            logger.debug("获得修改虚拟机的请求为" + vmModifyReq.toString());
        }
        return vmModifyReq;
    }

    /**
     * assebleRes
     * @param vmResp
     * @param respCxt
     */
    private void assebleRes(RPPVMModifyResp vmResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPVMModifyResp.RESP_BODY, vmResp);
    }

    /**
     * validateInputPara 校验输入参数
     * @param req
     * @return boolean
     */
    private boolean validateInputPara(RPPVMModifyReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getVmId()) || (!P_STRING.matcher(req.getVmId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查请求流水号SerialNum", null);
            return false;
        }
        return true;
    }

    /**
     * 获取ibatisDAO字段数据
     * @return Returns the ibatisDAO.
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * 获取vmModify字段数据
     * @return Returns the vmModify.
     */
    public VMModify getVmModify() {
        return vmModify;
    }

    /**
     * 设置vmModify字段数据
     * @param vmModify The vmModify to set.
     */
    public void setVmModify(VMModify vmModify) {
        this.vmModify = vmModify;
    }

}
