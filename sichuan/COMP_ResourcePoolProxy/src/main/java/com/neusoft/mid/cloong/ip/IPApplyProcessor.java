/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ip;

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
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPProType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IP申请处理类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class IPApplyProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(IPApplyProcessor.class);

    @Value("${ip.create.defaultVal.IPProType}")
    private String defaultIPProType;

    /**
     * IP申请接口
     */
    private IPApply ipApply;

    private IbatisDAO ibatisDAO;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPIPApplyReq req = (RPPIPApplyReq) reqCxt.getAttribute(RPPIPApplyReq.REQ_BODY);
        RPPIPApplyResp resp = new RPPIPApplyResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        IPApplyReq applyReq = null;
        try {
            applyReq = assembleReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送IP申请操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送IP申请操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (applyReq.getResourceUrl() == null || applyReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送虚拟IP申请操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + applyReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPIPApplyResp ipApplyResp = ipApply.apply(applyReq);
        assebleRes(ipApplyResp, respCxt);
        if (ipApplyResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {

            updateOrderInfo(applyReq, ipApplyResp);

            logger.info("申请IP段" + ipApplyResp.getIpSubNet() + "操作成功");
        } else {
            logger.info("申请IP段" + ipApplyResp.getIpSubNet() + "操作失败,接口响应码为[" + ipApplyResp.getResultCode()
                    + "]，接口响应消息为[" + ipApplyResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 更新订单信息
     * @author fengj<br>
     *         2015年3月5日 下午9:54:32
     * @param applyReq
     * @param ipApplyResp
     */
    void updateOrderInfo(IPApplyReq applyReq, RPPIPApplyResp ipApplyResp) {
        try {
            // 更新EBS的订单状态、订单到期时间
            OrderInfo orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryIPOrderInfo",
                    ipApplyResp.getIpSegmentURI());

            if (orderInfo != null) {

                BatchVO updateOrderVO = OrderProcessor.genOrderToEffectiveVO(applyReq.getTimestamp(), orderInfo);
                ArrayList<BatchVO> updateVOList = new ArrayList<BatchVO>();
                updateVOList.add(updateOrderVO);
                ibatisDAO.updateBatchData(updateVOList);
                if (logger.isDebugEnable()) {
                    logger.debug("更新订单成功, 订单ID为" + orderInfo.getOrderId());
                }
            }
        } catch (Exception e) {
            logger.error("更新IP申请订单状态时发生异常,IP段标示为：" + ipApplyResp.getIpSegmentURI(),e);
        }
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPIPApplyReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
//      创建ip段跨分区
//      if (StringUtils.isBlank(req.getResourcePoolPartId())
//              || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
//          logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
//          return false;
//      }
        if (req.getCreateModel() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请设置IP创建标示符CreateModel", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查IP申请务ID属性appId", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppName())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查IP申请业务name属性appName", null);
            return false;
        }

        if (IPCreateModel.TemplateModel.equals(req.getCreateModel())) {
            if (StringUtils.isBlank(req.getStandardId()) || (!P_STRING.matcher(req.getStandardId()).matches())) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查IP申请的规格属性standardId", null);
                return false;
            }
        } else if (IPCreateModel.CustomModel.equals(req.getCreateModel())) {
            if (req.getIpType() == null) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查IP申请的IP类型IpType", null);
                return false;
            }
            if (IPType.PRIVATE_IP.equals(req.getIpType()) && StringUtils.isBlank(req.getIpSegmentURI())) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查IP申请的私网IpSegmentURI", null);
                return false;
            }
        }
        return true;
    }

    private IPApplyReq assembleReq(RPPIPApplyReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        IPApplyReq ipApplyReq = new IPApplyReq();
        RPPIPApplyReq info = (RPPIPApplyReq) BeanUtils.cloneBean(req);
        ipApplyReq.setInfo(info);

        if (info.getCount() == RPPIPApplyReq.INT_DEFAULT_VAL) {
            info.setCount(1);
        }
        if (IPCreateModel.CustomModel.equals(info.getCreateModel())) {
            if (info.getIpProType() == null) {
                info.setIpProType(IPProType.fromValue(this.defaultIPProType));
            }
        } else if (IPCreateModel.TemplateModel.equals(info.getCreateModel())) {
            // TODO 湖北本版本的IP申请，直接走自定义流程，不按照模板来实现.因此不设置模板ID
        }

        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), ipApplyReq);
        return ipApplyReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置ipApply字段数据
     * @param ipApply The ipApply to set.
     */
    public void setIpApply(IPApply ipApply) {
        this.ipApply = ipApply;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
