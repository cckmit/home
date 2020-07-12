/*******************************************************************************
 * @(#)VMCreateProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.lb.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.AcquireStandardResPoolId;
import com.neusoft.mid.cloong.common.StandardResPoolIdInfo;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.VMCreateReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.lb.LBCreate;
import com.neusoft.mid.cloong.lb.LBCreateReq;
import com.neusoft.mid.cloong.lb.LBDemandReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 */
public class LBCreateProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(LBCreateProcessor.class);

    private IbatisDAO ibatisDAO;

    /**
     */
    private LBCreate lbCreator;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPLBCreateReq lbCreateReq = (RPPLBCreateReq) reqCxt.getAttribute(RPPBaseReq.REQ_BODY);
        RPPLBCreateResp lbResp = new RPPLBCreateResp();

        // 校验输入
        if (!validateInputPara(lbCreateReq)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, lbResp);
            return FAILURE;
        }
        LBCreateReq lbReq = null;
        try {
			lbReq = assembleLBReqInfo(lbCreateReq);
		}catch (ServiceStopException e1) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e1);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, lbResp);
            return FAILURE;
        } catch (UnmatchException e2) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e2);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, lbResp);
            return FAILURE;
        } catch (Exception e3) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e3);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, lbResp);
            return FAILURE;
        }
        
        // 发送创建请求
        lbResp = lbCreator.createLB(lbReq);


        assebleRes(lbResp, respCxt);

        return SUCCESS;
    }
    
    /**
     * 将业务层应答报文封装到传输层报文中
     * @param vmResp 业务层应答报文
     * @param resp 传输层应答报文
     */
    private void assebleRes(RPPLBCreateResp vmResp, RuntimeContext resp) {
        resp.setAttribute(RPPLBCreateResp.RESP_BODY, vmResp);
    }
    
    private LBCreateReq assembleLBReqInfo(RPPLBCreateReq lbCreateReq) throws ServiceStopException, UnmatchException {
    	LBCreateReq lbReq = new LBCreateReq();
    	LBDemandReq lbDemandReq = new LBDemandReq();
    	lbDemandReq.setConnectNum(lbCreateReq.getLbDemand().getConnectNum());
    	lbDemandReq.setNewConnectNum(lbCreateReq.getLbDemand().getNewConnectNum());
    	lbDemandReq.setThroughput(lbCreateReq.getLbDemand().getThroughput());
    	lbReq.setLbDemandReq(lbDemandReq);
    	lbReq.setAppID(lbCreateReq.getAppID());
    	lbReq.setParamFlag(lbCreateReq.getParamFlag());
    	lbReq.setProtocal(lbCreateReq.getProtocal());
    	lbReq.setLbip(lbCreateReq.getLbip());
    	lbReq.setLbPort(lbCreateReq.getLbPort());
    	lbReq.setAppName(lbCreateReq.getAppName());
    	lbReq.setLbName(lbCreateReq.getLbName());
    	lbReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
    	lbReq.setTransactionID(this.getTransactonGen().generateTransactionId(lbCreateReq.getResourcePoolId(),
    			lbReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
        		lbCreateReq.getResourcePoolId());
        lbReq.setPassword(resourceInfo.getUserPassword());
        lbReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        lbReq.setResourcePoolPartId(lbCreateReq.getResourcePoolPartId());
		return lbReq;
    }
    
    private boolean validateInputPara(RPPLBCreateReq req) {
    	
        if (req.getParamFlag() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查传参标识符属性paramFlag", null);
            return false;
        }
        if (req.getProtocal() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查流量协议类型属性Protocal", null);
            return false;
        }
        if (req.getLbip() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查负载均衡入口IP地址属性lbip", null);
            return false;
        }
        if (req.getAppID() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查业务系统编码属性appID", null);
            return false;
        }
        if (req.getAppName() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查业务系统名称属性appName", null);
            return false;
        }
        if (req.getLbName() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查负载均衡器的名称属性lbName", null);
            return false;
        }
        if (req.getLbDemand() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查负载均衡LbDemand", null);
            return false;
        }

        return true;
    }

	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

	public LBCreate getLbCreator() {
		return lbCreator;
	}

	public void setLbCreator(LBCreate lbCreator) {
		this.lbCreator = lbCreator;
	}

}
