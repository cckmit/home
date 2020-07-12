/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.order.info.OrderInfo;
import com.neusoft.mid.cloong.order.info.OrderProcessor;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakMode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakType;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份创建处理
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class VMBakCreateProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VMBakCreateProcessor.class);

    /**
     * 虚拟机创建接口
     */
    private VMBakCreate vmBakCreate;

    private IbatisDAO ibatisDAO;
    

    @Value("${vmbak.create.defaultVal.BackupType}")
    private String defaultBackupType;

    @Value("${vmbak.create.defaultVal.BackupMode}")
    private int defaultBackupMode ;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVMBakCreateReq req = (RPPVMBakCreateReq) reqCxt.getAttribute(RPPVMBakCreateReq.REQ_BODY);
        RPPVMBakCreateResp resp = new RPPVMBakCreateResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        VMBakCreateReq vmBakCreateReq = null;
        try {
            vmBakCreateReq = assembleReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VM[" + req.getVmId() + "]备份创建申请操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VM[" + req.getVmId() + "]备份创建申请操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vmBakCreateReq.getResourceUrl() == null || vmBakCreateReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送VM[" + req.getVmId()
                    + "]备份创建申请操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + vmBakCreateReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVMBakCreateResp vmBakCreateResp = vmBakCreate.create(vmBakCreateReq);
        assebleRes(vmBakCreateResp, respCxt);
        if (vmBakCreateResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {

            updateOrderInfo(vmBakCreateReq, vmBakCreateResp, req.getCaseId());

            logger.info("向资源池发送VM[" + req.getVmId() + "]备份创建申请操作请求操作成功");
        } else {
            logger.info("向资源池发送VM[" + req.getVmId() + "]备份创建申请操作请求失败，接口响应消息为[" + vmBakCreateResp.getResultMessage()
                    + "]");
        }
        return SUCCESS;
    }

    /**
     * 更新订单信息
     * @author fengj<br>
     *         2015年3月5日 下午9:54:32
     * @param req
     * @param resp
     */
    void updateOrderInfo(VMBakCreateReq req, RPPVMBakCreateResp resp, String caseId) {
        try {
            // 更新EBS的订单状态、订单到期时间
            OrderInfo orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryVMBakOrderInfo", caseId);

            if (orderInfo != null) {
                BatchVO updateOrderVO = OrderProcessor.genOrderToEffectiveVO(req.getTimestamp(), orderInfo);
                ArrayList<BatchVO> updateVOList = new ArrayList<BatchVO>();
                updateVOList.add(updateOrderVO);
                ibatisDAO.updateBatchData(updateVOList);
                if (logger.isDebugEnable()) {
                    logger.debug("更新订单成功, 订单ID为" + orderInfo.getOrderId());
                }
            }
        } catch (Exception e) {
            logger.error("更新虚拟机备份申请订单状态时发生异常,备份任务实例ID为：" + caseId);
        }
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPVMBakCreateReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getVmId()) || (!P_STRING.matcher(req.getVmId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查备份虚拟机ID属性VMID", null);
            return false;
        }
        if (req.getBackupCyc() == null || req.getBackupCyc() == RPPBaseReq.INT_DEFAULT_VAL) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机备份的周期属性backupCyc", null);
            return false;
        }
        if (req.getBackStoreTime() == null || req.getBackStoreTime() == RPPBaseReq.INT_DEFAULT_VAL) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机备份的保留天数属性storeTime", null);
            return false;
        }
        if (req.getBackupStartTime() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查虚拟机备份的开始时间属性startTime", null);
            return false;
        }

        return true;
    }

    private VMBakCreateReq assembleReq(RPPVMBakCreateReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        VMBakCreateReq vmBakCreateReq = new VMBakCreateReq();
        RPPVMBakCreateReq info = (RPPVMBakCreateReq) BeanUtils.cloneBean(req);
        vmBakCreateReq.setInfo(info);

        // 设置默认值
        if (req.getVmBackupType() == null) {
            vmBakCreateReq.getInfo().setVmBackupType(VMBakType.fromValue(this.defaultBackupType));
        }
        if (req.getVmBackupMode() == null) {
            vmBakCreateReq.getInfo().setVmBackupMode(VMBakMode.fromValue(this.defaultBackupMode));
        }

        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), vmBakCreateReq);
        return vmBakCreateReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置vmBakCreate字段数据
     * @param vmBakCreate The vmBakCreate to set.
     */
    public void setVmBakCreate(VMBakCreate vmBakCreate) {
        this.vmBakCreate = vmBakCreate;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
