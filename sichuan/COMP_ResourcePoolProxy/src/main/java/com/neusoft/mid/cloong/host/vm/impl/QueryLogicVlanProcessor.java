/*******************************************************************************
 * @(#)QueryLogicVlanProcessor.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.regex.Pattern;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanAndIp;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanReq;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanResp;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询逻辑vlan
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 上午8:49:51
 */
public class QueryLogicVlanProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(QueryLogicVlanProcessor.class);

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
        QueryLogicVlanReq queryLogicVlanReq = null;
        try {
            queryLogicVlanReq = assembleQueryLogicVlanReq(req);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送查询vlan请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, null);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送查询vlan请求失败，资源池代理系统内部错误，无法通过给定的资源池ID" + (String) req.getAttribute("RES_POOL_ID")
                            + "获取到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, null);
            return FAILURE;
        }
        if (queryLogicVlanReq.getResourceUrl() == null || queryLogicVlanReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送查询vlan请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空！", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, null);
            return FAILURE;
        }
        QueryLogicVlanResp queryLogicVlanResp = queryLogicVlanAndIp.queryLogicVlan(queryLogicVlanReq);
        assebleRes(queryLogicVlanResp, resp);
        if (queryLogicVlanResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("查询逻辑vlan信息成功！");
        } else {
            logger.info("查询逻辑vlan信息失败,接口响应码为[" + queryLogicVlanResp.getResultCode() + "]，接口响应消息为["
                    + queryLogicVlanResp.getResultMessage());
        }
        return SUCCESS;
    }

    private QueryLogicVlanReq assembleQueryLogicVlanReq(RuntimeContext req) throws ServiceStopException,
            UnmatchException {
        QueryLogicVlanReq queryLogicVlanReq = new QueryLogicVlanReq();
        queryLogicVlanReq.setResourcePoolId((String) req.getAttribute("RES_POOL_ID"));
        queryLogicVlanReq.setResourcePoolPartId((String) req.getAttribute("RES_POOL_PART_ID"));
        queryLogicVlanReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        queryLogicVlanReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                queryLogicVlanReq.getResourcePoolId(), queryLogicVlanReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                queryLogicVlanReq.getResourcePoolId());
        queryLogicVlanReq.setPassword(resourceInfo.getUserPassword());
        queryLogicVlanReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return queryLogicVlanReq;
    }

    private void assebleRes(QueryLogicVlanResp queryLogicVlanResp, RuntimeContext resp) {
        resp.setAttribute("ResultCode", queryLogicVlanResp.getResultCode());
        resp.setAttribute("ResultMessage", queryLogicVlanResp.getResultMessage());
        resp.setAttribute("LOGIC_VLAN_SET", queryLogicVlanResp.getLogicVlanSet());
    }

    private boolean validateInputPara(RuntimeContext req) {
        if (req.getAttribute("RES_POOL_ID") == null
                || (!P_STRING.matcher((String) req.getAttribute("RES_POOL_ID")).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池IDRES_POOL_ID", null);
            return false;
        }
        if (req.getAttribute("RES_POOL_PART_ID") == null
                || (!P_STRING.matcher((String) req.getAttribute("RES_POOL_PART_ID")).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区IDRES_POOL_PART_ID", null);
            return false;
        }
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
