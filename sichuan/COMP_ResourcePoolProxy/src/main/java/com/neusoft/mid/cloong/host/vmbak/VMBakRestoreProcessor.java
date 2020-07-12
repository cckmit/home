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
import org.springframework.beans.factory.annotation.Value;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.VMStateQueueItem;
import com.neusoft.mid.cloong.host.vm.impl.VMStateQueue;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份恢复处理
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class VMBakRestoreProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMBakRestoreProcessor.class);

    /**
     * 虚拟机备份恢复接口
     */
    private VMBakRestore vmBakRestore;

    /**
     * 虚拟机状态查询队列
     */
    private VMStateQueue vmStateQueue;

    /**
     * 查询虚拟机、虚拟机备份任务状态的重发请求时间间隔(ms)
     */
    private long retransInterval = 5000l;

    /**
     * 虚拟机状态查询延时时间
     */
    @Value("${operatorVMToQueryDelay}")
    private Integer operationQueryDelay = 5000;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMBakRestoreReq req = (RPPVMBakRestoreReq) reqCxt.getAttribute(RPPVMBakRestoreReq.REQ_BODY);
        RPPVMBakRestoreResp resp = new RPPVMBakRestoreResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        VMBakRestoreReq vmBakRestoreReq = null;
        try {
            vmBakRestoreReq = assembleReq(req);
        } catch (ServiceStopException e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VM备份[" + req.getVmBackupId() + "][" + req.getVmBackupInternalId()
                            + "]恢复申请操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VM备份[" + req.getVmBackupId() + "][" + req.getVmBackupInternalId()
                            + "]恢复申请操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vmBakRestoreReq.getResourceUrl() == null || vmBakRestoreReq.getPassword() == null) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VM备份[" + req.getVmBackupId() + "][" + req.getVmBackupInternalId()
                            + "]恢复申请操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:"
                            + vmBakRestoreReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVMBakRestoreResp vmBakRestoreResp = vmBakRestore.restore(vmBakRestoreReq);

        if (InterfaceResultCode.SUCCESS.equals(vmBakRestoreResp.getResultCode())) {

            startQueryVMResotreProgress(vmBakRestoreReq, vmBakRestoreResp);

        }

        assebleRes(vmBakRestoreResp, respCxt);
        if (vmBakRestoreResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("向资源池发送VM备份[" + req.getVmBackupId() + "][" + req.getVmBackupInternalId() + "]恢复申请操作请求操作成功");
        } else {
            logger.info("向资源池发送VM备份[" + req.getVmBackupId() + "][" + req.getVmBackupInternalId()
                    + "]恢复申请操作请求失败，接口响应消息为[" + vmBakRestoreResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 开始查询虚拟机备份恢复进度
     * @author fengj<br>
     *         2015年3月5日 上午10:56:37
     * @param vmBakRestoreReq
     * @param vmBakRestoreResp
     */
    void startQueryVMResotreProgress(VMBakRestoreReq vmBakRestoreReq, RPPVMBakRestoreResp vmBakRestoreResp) {

        final String password = vmBakRestoreReq.getPassword();
        final String resourcePoolId = vmBakRestoreReq.getResourcePoolId();
        final String resourcePoolPartId = vmBakRestoreReq.getResourcePoolPartId();
        final String resourceUrl = vmBakRestoreReq.getResourceUrl();
        final String vmId = vmBakRestoreResp.getVmId();
        final String serialNum = vmBakRestoreReq.getSerialNum();

        new Thread(new Runnable() {
            public void run() {
                try {
                    logger.info(operationQueryDelay + "毫秒后开始查询虚拟机[" + vmId + "]操作的结果状态,本次操作流水号为:" + serialNum);
                    Thread.currentThread().sleep(operationQueryDelay);
                    VMStateQueueItem item = new VMStateQueueItem(retransInterval);
                    item.setPassword(password);
                    item.setResourcePoolId(resourcePoolId);
                    item.setResourcePoolPartId(resourcePoolPartId);
                    item.setResourceUrl(resourceUrl);
                    item.setVmId(vmId);
                    item.setSerialNum(serialNum);
                    vmStateQueue.add(item);
                } catch (InterruptedException e) {
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送虚拟机" + vmId + "操作后，将查询最终状态任务加入队列失败，本次操作流水号为:"
                            + serialNum, null);
                }
            }
        }).start();
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPVMBakRestoreReq req) {
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
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机备份恢复属性vmBackupId", null);
            return false;
        }
        if (StringUtils.isBlank(req.getVmBackupInternalId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机备份恢复属性VMBackupInternalID", null);
            return false;
        }

        return true;
    }

    private VMBakRestoreReq assembleReq(RPPVMBakRestoreReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        VMBakRestoreReq vmBakRestoreReq = new VMBakRestoreReq();

        // 直接从请求中，拷贝需要的参数值
        RPPVMBakRestoreReq info = (RPPVMBakRestoreReq) BeanUtils.cloneBean(req);
        vmBakRestoreReq.setInfo(info);

        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), vmBakRestoreReq);
        return vmBakRestoreReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置vmBakRestore字段数据
     * @param vmBakRestore The vmBakRestore to set.
     */
    public void setVmBakRestore(VMBakRestore vmBakRestore) {
        this.vmBakRestore = vmBakRestore;
    }

    /**
     * 设置retransInterval字段数据
     * @param retransInterval The retransInterval to set.
     */
    public void setRetransInterval(long retransInterval) {
        this.retransInterval = retransInterval;
    }

    /**
     * 设置vmStateQueue字段数据
     * @param vmStateQueue The vmStateQueue to set.
     */
    public void setVmStateQueue(VMStateQueue vmStateQueue) {
        this.vmStateQueue = vmStateQueue;
    }

}
