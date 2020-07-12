/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vlan;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IP释放处理类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class VlanCancelProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VlanCancelProcessor.class);

    /**
     * IP释放接口
     */
    private VlanCancel vlanCancel;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVlanCancelReq req = (RPPVlanCancelReq) reqCxt.getAttribute(RPPVlanCancelReq.REQ_BODY);
        RPPVlanCancelResp resp = new RPPVlanCancelResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        VlanCancelReq vlanCancelReq = null;
        try {
            vlanCancelReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送Vlan取消操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送Vlan取消操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (vlanCancelReq.getResourceUrl() == null || vlanCancelReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送Vlan取消操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + vlanCancelReq.getSerialNum(),
                    null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVlanCancelResp vlanCancelResp = vlanCancel.cancel(vlanCancelReq);
        assebleRes(vlanCancelResp, respCxt);
        if (vlanCancelResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("取消Vlan[" + req.getVlanId() + "]操作成功");
        } else {
            logger.info("取消Vlan[" + req.getVlanId() + "]操作失败,接口响应码为["
                    + vlanCancelResp.getResultCode() + "]，接口响应消息为[" + vlanCancelResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPVlanCancelReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
//  vlan跨分区，所以讲分区id验证操作取消
//        if (StringUtils.isBlank(req.getResourcePoolPartId())
//                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
//            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
//            return false;
//        }
        if (StringUtils.isBlank(req.getVlanId()) && StringUtils.isBlank(req.getVlanId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，需要保证VLAN信息 参数不为空VLanID", null);
            return false;
        }
        return true;
    }

    private VlanCancelReq assembleEBSReq(RPPVlanCancelReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        VlanCancelReq vlanCancelReq = new VlanCancelReq();
        vlanCancelReq.setInfo((RPPVlanCancelReq) BeanUtils.cloneBean(req));
        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), vlanCancelReq);
        return vlanCancelReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置vlanCancel字段数据
     * @param vlanCancel The vlanCancel to set.
     */
    public void setVlanCancel(VlanCancel vlanCancel) {
        this.vlanCancel = vlanCancel;
    }

}
