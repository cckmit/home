/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vlan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.VlanType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.ZoneType;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * Vlan申请处理类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class VlanApplyProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(VlanApplyProcessor.class);

    @Value("${vlan.create.defaultVal.VlanType}")
    private int defaultVlanType;

    @Value("${vlan.create.defaultVal.ZoneType}")
    private Integer defaultZoneType;

    /**
     * Vlan申请接口
     */
    private VlanApply vlanApply;

    private IbatisDAO ibatisDAO;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPVlanApplyReq req = (RPPVlanApplyReq) reqCxt.getAttribute(RPPVlanApplyReq.REQ_BODY);
        RPPVlanApplyResp resp = new RPPVlanApplyResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        VlanApplyReq applyReq = null;
        try {
            applyReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送VLAN申请操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(
                    CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送VLAN申请操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (applyReq.getResourceUrl() == null || applyReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟VLAN申请操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + applyReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPVlanApplyResp vlanApplyResp = vlanApply.apply(applyReq);
        assebleRes(vlanApplyResp, respCxt);
        if (vlanApplyResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            updateOrderInfo(applyReq, vlanApplyResp);
            logger.info("为业务" + req.getAppId() + "申请VLAN段 " + vlanApplyResp.getVlanIdList() + "操作成功");
        } else {
            logger.info("为业务" + req.getAppId() + "申请VLAN段 操作失败,接口响应码为[" + vlanApplyResp.getResultCode() + "]，接口响应消息为["
                    + vlanApplyResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 更新订单信息
     * @author fengj<br>
     *         2015年3月5日 下午9:54:32
     * @param applyReq
     * @param vlanApplyResp
     */
    void updateOrderInfo(VlanApplyReq applyReq, RPPVlanApplyResp vlanApplyResp) {

        try {
            List<BatchVO> voList = new ArrayList<BatchVO>();
            for (String vlanId : vlanApplyResp.getVlanIdList()) {
                // 更新EBS的订单状态、订单到期时间
                OrderInfo orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryVlanOrderInfo", vlanId);

                if (orderInfo != null) {
                    BatchVO updateOrderVO = OrderProcessor.genOrderToEffectiveVO(applyReq.getTimestamp(), orderInfo);
                    voList.add(updateOrderVO);
                }
            }
            ibatisDAO.updateBatchData(voList);
        } catch (Exception e) {
            logger.error("更新Vlan申请订单状态时发生异常,Vlan标示为：" + vlanApplyResp.getVlanIdList());
        }
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPVlanApplyReq req) {
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
        if (StringUtils.isBlank(req.getAppId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查VLAN申请务ID属性appId", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppName())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查VLAN申请业务name属性appName", null);
            return false;
        }
        if (req.getCount() == null || req.getCount().intValue() == RPPVlanApplyReq.INT_DEFAULT_VAL) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查VLAN申请业务个数属性count", null);
            return false;
        }

        return true;
    }

    private VlanApplyReq assembleEBSReq(RPPVlanApplyReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        VlanApplyReq vlanApplyReq = new VlanApplyReq();
        RPPVlanApplyReq info = (RPPVlanApplyReq) BeanUtils.cloneBean(req);
        vlanApplyReq.setInfo(info);

        // 设置默认值
        if (info.getVlanType() == null) {
            info.setVlanType(VlanType.fromValue(this.defaultVlanType));
        }
        if (info.getZoneType() == null && this.defaultZoneType != null) {
            info.setZoneType(ZoneType.fromValue(this.defaultZoneType));
        }
        //测试将资源池id改为01，正式环境要改为获取到的资源池id
        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), vlanApplyReq);
        //setCommAttribute("CPC-RP-01", req.getResourcePoolPartId(), vlanApplyReq);
        
        return vlanApplyReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置vlanApply字段数据
     * @param vlanApply The vlanApply to set.
     */
    public void setVlanApply(VlanApply vlanApply) {
        this.vlanApply = vlanApply;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
