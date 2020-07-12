/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakDeleteResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份删除处理
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class VMBakDeleteProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMBakDeleteProcessor.class);

    /**
     * 虚拟机备份删除接口
     */
    private VMBakDelete vmBakDelete;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMBakDeleteReq req = (RPPVMBakDeleteReq) reqCxt.getAttribute(RPPVMBakDeleteReq.REQ_BODY);
        RPPVMBakDeleteResp resp = new RPPVMBakDeleteResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        VMBakDeleteReq vmBakDeleteReq = null;
        try {
            vmBakDeleteReq = assembleReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送VM备份[" + req.getVmBackupId()
                    + "]删除操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:" + (String) reqCxt.getAttribute("SERIAL_NUM"),
                    null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VM备份[" + req.getVmBackupId() + "]删除操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vmBakDeleteReq.getResourceUrl() == null || vmBakDeleteReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送VM备份[" + req.getVmBackupId()
                    + "]删除操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + vmBakDeleteReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVMBakDeleteResp vmBakDeleteResp = vmBakDelete.delete(vmBakDeleteReq);
        assebleRes(vmBakDeleteResp, respCxt);
        if (vmBakDeleteResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("向资源池发送VM备份[" + req.getVmBackupId() + "]删除操作请求操作成功");
        } else {
            logger.info("向资源池发送VM备份[" + req.getVmBackupId() + "]删除操作请求失败，接口响应消息为[" + vmBakDeleteResp.getResultMessage()
                    + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPVMBakDeleteReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getVmBackupId()) || (!P_STRING.matcher(req.getVmBackupId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机备份删除属性vmBackupId", null);
            return false;
        }

        return true;
    }

    private VMBakDeleteReq assembleReq(RPPVMBakDeleteReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        VMBakDeleteReq vmBakDeleteReq = new VMBakDeleteReq();

        // 直接从请求中，拷贝需要的参数值
        RPPVMBakDeleteReq info = (RPPVMBakDeleteReq) BeanUtils.cloneBean(req);
        vmBakDeleteReq.setInfo(info);

        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), vmBakDeleteReq);
        return vmBakDeleteReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置vmBakDelete字段数据
     * @param vmBakDelete The vmBakDelete to set.
     */
    public void setVmBakDelete(VMBakDelete vmBakDelete) {
        this.vmBakDelete = vmBakDelete;
    }

}
