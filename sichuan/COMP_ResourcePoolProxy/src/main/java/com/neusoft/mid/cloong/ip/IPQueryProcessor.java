/*******************************************************************************
 * @(#)EBSAttachProcessor.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ip;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IP查询处理类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class IPQueryProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(IPQueryProcessor.class);

    /**
     * IP查询接口
     */
    private IPQuery ipQuery;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPIPQueryReq req = (RPPIPQueryReq) reqCxt.getAttribute(RPPIPQueryReq.REQ_BODY);
        RPPIPQueryResp resp = new RPPIPQueryResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        IPQueryReq ipQueryReq = null;
        try {
            ipQueryReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送IP查询操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送IP查询操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (ipQueryReq.getResourceUrl() == null || ipQueryReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送IP查询操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + ipQueryReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPIPQueryResp ipQueryResp = ipQuery.query(ipQueryReq);
        assebleRes(ipQueryResp, respCxt);
        if (ipQueryResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("查询" + req.getIpType().getValue() + "的IP操作成功");
        } else {
            logger.info("申请" + req.getIpType().getValue() + "操作失败,接口响应码为[" + ipQueryResp.getResultCode() + "]，接口响应消息为["
                    + ipQueryResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPIPQueryReq req) {
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
        if (req.getIpType() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查要查询的IP类型ipType", null);
            return false;
        }
        return true;
    }

    private IPQueryReq assembleEBSReq(RPPIPQueryReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        IPQueryReq ipQueryReq = new IPQueryReq();
        ipQueryReq.setInfo((RPPIPQueryReq) BeanUtils.cloneBean(req));

        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), ipQueryReq);
        return ipQueryReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置ipQuery字段数据
     * @param ipQuery The ipQuery to set.
     */
    public void setIpQuery(IPQuery ipQuery) {
        this.ipQuery = ipQuery;
    }

}
