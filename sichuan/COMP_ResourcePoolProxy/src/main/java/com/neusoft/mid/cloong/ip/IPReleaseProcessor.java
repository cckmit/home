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
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IP释放处理类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午02:30:14
 */
public class IPReleaseProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(IPReleaseProcessor.class);

    /**
     * IP释放接口
     */
    private IPRelease ipRelease;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPIPReleaseReq req = (RPPIPReleaseReq) reqCxt.getAttribute(RPPIPReleaseReq.REQ_BODY);
        RPPIPReleaseResp resp = new RPPIPReleaseResp();

        // 校验
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
            return FAILURE;
        }

        IPRelaseReq ipReleaseReq = null;
        try {
            ipReleaseReq = assembleEBSReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送IP释放操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        } catch (Exception e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送IP释放操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        if (ipReleaseReq.getResourceUrl() == null || ipReleaseReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送IP释放操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + ipReleaseReq.getSerialNum(), null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
            return FAILURE;
        }
        RPPIPReleaseResp ipReleaseResp = ipRelease.query(ipReleaseReq);
        assebleRes(ipReleaseResp, respCxt);
        if (ipReleaseResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("释放IP" + req.getIpSegmentURI() + "/" + req.getIp() + "操作成功");
        } else {
            logger.info("释放IP" + req.getIpSegmentURI() + "/" + req.getIp() + "操作失败,接口响应码为["
                    + ipReleaseResp.getResultCode() + "]，接口响应消息为[" + ipReleaseResp.getResultMessage() + "]");
        }
        return SUCCESS;
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPIPReleaseReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
//      创建ip段跨分区
//    if (StringUtils.isBlank(req.getResourcePoolPartId())
//            || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
//        logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
//        return false;
//    }
        if (StringUtils.isBlank(req.getIp()) && StringUtils.isBlank(req.getIpSegmentURI())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，需要保证IP/IPSegmentURI参数其中一个不为空", null);
            return false;
        }
        return true;
    }

    private IPRelaseReq assembleEBSReq(RPPIPReleaseReq req) throws ServiceStopException, UnmatchException,
            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        IPRelaseReq ipReleaseReq = new IPRelaseReq();
        ipReleaseReq.setInfo((RPPIPReleaseReq) BeanUtils.cloneBean(req));
        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), ipReleaseReq);
        return ipReleaseReq;
    }

    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
    }

    /**
     * 设置ipRelease字段数据
     * @param ipRelease The ipRelease to set.
     */
    public void setIpRelease(IPRelease ipRelease) {
        this.ipRelease = ipRelease;
    }

}
