/*******************************************************************************
 * @(#)QueryIpOfLogicVlanProcessor.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.regex.Pattern;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.QueryIpInfoOfLogicVlanReq;
import com.neusoft.mid.cloong.host.vm.QueryIpInfoOfLogicVlanResp;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanAndIp;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 根据vlan查询私网ip
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 上午9:31:42
 */
public class QueryIpOfLogicVlanProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(QueryIpOfLogicVlanProcessor.class);

    /**
     * 查询逻辑vlan和指定vlan中ip信息
     */
    private QueryLogicVlanAndIp queryLogicVlanAndIp;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    @Override
    public String process(RuntimeContext req, RuntimeContext resp) {
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, resp, null);
            return FAILURE;
        }
        QueryIpInfoOfLogicVlanReq queryIpInfoOfLogicVlanReq = null;
        try {
            queryIpInfoOfLogicVlanReq = assembleQueryLogicVlanReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送根据vlan查询私网ip请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, null);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送根据vlan查询私网ip请求失败，资源池代理系统内部错误，无法通过给定的资源池ID"
                    + (String) req.getAttribute("RES_POOL_ID") + "获取到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, null);
            return FAILURE;
        }
        if (queryIpInfoOfLogicVlanReq.getResourceUrl() == null || queryIpInfoOfLogicVlanReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送根据vlan查询私网ip请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空！",
                    null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, null);
            return FAILURE;
        }
        QueryIpInfoOfLogicVlanResp queryIpInfoOfLogicVlanResp = queryLogicVlanAndIp
                .queryIpInfoOfLogicVlan(queryIpInfoOfLogicVlanReq);
        assebleRes(queryIpInfoOfLogicVlanResp, resp);
        if (queryIpInfoOfLogicVlanResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("根据逻辑vlan查询私网ip信息成功！");
        } else {
            logger.info("根据逻辑vlan查询私网ip信息失败,接口响应码为[" + queryIpInfoOfLogicVlanResp.getResultCode() + "]，接口响应消息为["
                    + queryIpInfoOfLogicVlanResp.getResultMessage());
        }
        return SUCCESS;
    }

    private QueryIpInfoOfLogicVlanReq assembleQueryLogicVlanReq(RuntimeContext req) throws ServiceStopException,
            UnmatchException {
        QueryIpInfoOfLogicVlanReq queryIpInfoOfLogicVlanReq = new QueryIpInfoOfLogicVlanReq();
        queryIpInfoOfLogicVlanReq.setResourcePoolId((String) req.getAttribute("RES_POOL_ID"));
        queryIpInfoOfLogicVlanReq.setResourcePoolPartId((String) req.getAttribute("RES_POOL_PART_ID"));
        queryIpInfoOfLogicVlanReq.setLogicVlanId((String) req.getAttribute("LOGIC_VLANID"));
        queryIpInfoOfLogicVlanReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        queryIpInfoOfLogicVlanReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                queryIpInfoOfLogicVlanReq.getResourcePoolId(), queryIpInfoOfLogicVlanReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                queryIpInfoOfLogicVlanReq.getResourcePoolId());
        queryIpInfoOfLogicVlanReq.setPassword(resourceInfo.getUserPassword());
        queryIpInfoOfLogicVlanReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return queryIpInfoOfLogicVlanReq;
    }

    private void assebleRes(QueryIpInfoOfLogicVlanResp queryIpInfoOfLogicVlanResp, RuntimeContext resp) {
        resp.setAttribute("ResultCode", queryIpInfoOfLogicVlanResp.getResultCode());
        resp.setAttribute("ResultMessage", queryIpInfoOfLogicVlanResp.getResultMessage());
        resp.setAttribute("IP_SET", queryIpInfoOfLogicVlanResp.getIpSet());
    }

    private boolean validateInputPara(RuntimeContext req) {
        if (req.getAttribute("RES_POOL_ID") == null
                || (!P_STRING.matcher((String) req.getAttribute("RES_POOL_ID")).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
        if (req.getAttribute("RES_POOL_PART_ID") == null
                || (!P_STRING.matcher((String) req.getAttribute("RES_POOL_PART_ID")).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        // LOGIN_VLANID不校验了
        // if (req.getAttribute("LOGIC_VLANID") == null
        // || (!P_STRING.matcher((String) req.getAttribute("LOGIC_VLANID")).matches())) {
        // logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查逻辑VLANID LOGIC_VLANID", null);
        // return false;
        // }
        return true;
    }

    /**
     * 获取queryLogicVlanAndIp字段数据
     * @return Returns the queryLogicVlanAndIp.
     */
    public QueryLogicVlanAndIp getQueryLogicVlanAndIp() {
        return queryLogicVlanAndIp;
    }

    /**
     * 设置queryLogicVlanAndIp字段数据
     * @param queryLogicVlanAndIp The queryLogicVlanAndIp to set.
     */
    public void setQueryLogicVlanAndIp(QueryLogicVlanAndIp queryLogicVlanAndIp) {
        this.queryLogicVlanAndIp = queryLogicVlanAndIp;
    }
}
