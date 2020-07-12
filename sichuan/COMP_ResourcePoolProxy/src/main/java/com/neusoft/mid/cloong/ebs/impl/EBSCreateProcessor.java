/*******************************************************************************
 * @(#)EBSCreateProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.StandardResPoolIdInfo;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.ebs.EBSCreate;
import com.neusoft.mid.cloong.ebs.EBSCreateReq;
import com.neusoft.mid.cloong.ebs.EBSQuery;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.BsParam;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘创建处理器
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-26 下午04:05:05
 */
public class EBSCreateProcessor extends BaseProcessor {

    @Value("${bs.create.defaultVal.TIER}")
    private int defaultTier;

    @Value("${bs.create.defaultVal.RAID}")
    private int defaultRaid;

    @Value("${bs.create.defaultVal.STORAGE_NET}")
    private int defaultStorageNet;

    @Value("${bs.create.defaultVal.VOL_NUM}")
    private int defaultVolNum;

    @Value("${bs.create.defaultVal.ResourceType}")
    private int defaultResourceType;

    @Value("${bs.create.defaultVal.TierRule}")
    private int defaultTierRule;

    @Value("${bs.create.defaultVal.TierOpen}")
    private int defaultTierOpen;

    private static LogService logger = LogService.getLogger(EBSCreateProcessor.class);

    private static final String SUCCESS = "ok";

    private static final String FAILURE = "failure";

    /**
     * 创建VM发送到资源池后查询资源状态及IP、带宽线程池
     */
    private ThreadPoolTaskExecutor ebsCreateStatQueryTask;

    /**
     * 资源池应答码
     */
    private static final String SUCCESS_CODE = "00000000";

    private IbatisDAO ibatisDAO;

    /**
     * 和资源池交互的虚拟硬盘创建接口
     */
    private EBSCreate ebsCreator;

    /**
     * 和资源池交互的虚拟硬盘创建接口
     */
    private EBSQuery ebsQuery;

    /**
     * 块存储创建结果轮询间隔
     */
    private int ebsCreateResultQueryInterval;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPEBSCreateReq req = (RPPEBSCreateReq) reqCxt.getAttribute(RPPEBSCreateReq.REQ_BODY);
        RPPEBSCreateResp errorResp = new RPPEBSCreateResp();

        // 校验输入
        if (!validateInputPara(req)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        EBSCreateReq ebsReq = null;
        try {
            ebsReq = assembleEBSReq(req);
        } catch (ServiceStopException e1) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e1);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        } catch (UnmatchException e2) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e2);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        } catch (Exception e3) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e3);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        if (ebsReq.getResourceUrl() == null || ebsReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, errorResp);
            return FAILURE;
        }
        // 发送创建请求
        RPPEBSCreateResp ebsResp = ebsCreator.createEBS(ebsReq);

        // 判断请求是否成功，如果没有成功，则不再查询状态
        startQueryCreateProgress(ebsReq, respCxt, ebsResp);

        assebleRes(ebsResp, respCxt);

        return SUCCESS;
    }

    /**
     * 开始启动块存储创建进度查询线程
     * @author fengj<br>
     *         2015年3月3日 下午2:02:36
     * @param respCxt
     * @param ebsResp
     */
    private void startQueryCreateProgress(EBSCreateReq ebsReq, RuntimeContext respCxt, RPPEBSCreateResp ebsResp) {
        if (ebsResp.getResultCode().equals(SUCCESS_CODE)) {

            int numThread = 0;
            // 根据要创建的块存储数量，启动N个线程，将该线程放入线程池
            for (String ebsId : ebsResp.getBSIDs()) {
                try {

                    numThread++;
                    EBSCreateStatQuery ebsCreateStatQuery = new EBSCreateStatQuery();
                    ebsCreateStatQuery.setIbatisDAO(ibatisDAO);
                    ebsCreateStatQuery.setInterval(ebsCreateResultQueryInterval);
                    ebsCreateStatQuery.setEbsQuery(ebsQuery);
                    ebsCreateStatQuery.assembleQueryReq(ebsId, ebsReq);
                    ebsCreateStatQueryTask.execute(ebsCreateStatQuery);
                    if (logger.isDebugEnable()) {
                        logger.debug("启动线程第" + numThread + "个...");
                    }
                } catch (TaskRejectedException e1) {
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e1);
                    assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_THREAD_ERROR, respCxt, ebsResp);
                    return;
                } catch (Exception e2) {
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e2);
                    assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_THREAD_ERROR, respCxt, ebsResp);
                    return;
                }
            }
            logger.info("线程执行完毕，共执行" + numThread + "个线程！");
        }
    }

    /**
     * 校验请求
     * @param req
     * @return
     */
    private boolean validateInputPara(RPPEBSCreateReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (req.getCreateModel() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建模式属性为空", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建业务ID属性appId", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppName())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建业务name属性appName", null);
            return false;
        }
        if (EBSCreateModel.template.equals(req.getCreateModel())) {
            if (StringUtils.isBlank(req.getStandardId()) || (!P_STRING.matcher(req.getStandardId()).matches())) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建规格属性standardId", null);
                return false;
            }
        }
        if (EBSCreateModel.paramArray.equals(req.getCreateModel())) {
            if (req.getBSParamArray() == null) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建自定义属性bsParamArray", null);
                return false;
            }
            if (req.getBSParamArray().getVolNum() <= 0) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建个数属性volNum", null);
                return false;
            }
            if (req.getBSParamArray().getVolSize() <= 0) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查块存储创建个数属性volSize", null);
                return false;
            }

        }

        return true;
    }

    private EBSCreateReq assembleEBSReq(RPPEBSCreateReq req) throws ServiceStopException, UnmatchException {
        EBSCreateReq ebsCreateReq = new EBSCreateReq();

        ebsCreateReq.setCreateModel(req.getCreateModel());
        ebsCreateReq.setAppId(req.getAppId());
        ebsCreateReq.setAppName(req.getAppName());
        ebsCreateReq.setResourceID(req.getResourceID());
        ebsCreateReq.setEbsName(req.getEbsName());
        ebsCreateReq.setStandardId(req.getStandardId());

        ebsCreateReq.setResourcePoolId(req.getResourcePoolId());
        ebsCreateReq.setResourcePoolPartId(req.getResourcePoolPartId());
        ebsCreateReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        ebsCreateReq.setTransactionID(this.getTransactonGen().generateTransactionId(ebsCreateReq.getResourcePoolId(),
                ebsCreateReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                ebsCreateReq.getResourcePoolId());
        ebsCreateReq.setPassword(resourceInfo.getUserPassword());
        ebsCreateReq.setResourceUrl(resourceInfo.getResourcePoolUrl());

        if (EBSCreateModel.template.equals(req.getCreateModel())) {
            // 通过规格ID获取对应资源池模板ID.
            StandardResPoolIdInfo standardResPoolIdInfo = new StandardResPoolIdInfo();
            standardResPoolIdInfo.setResPoolId(ebsCreateReq.getResourcePoolId());
            standardResPoolIdInfo.setResPoolPartId(ebsCreateReq.getResourcePoolPartId());
            standardResPoolIdInfo.setStandardId(ebsCreateReq.getStandardId());
            acquireStandardResPoolId.setIbatisDAO(ibatisDAO);
            String templateId = acquireStandardResPoolId.getStandardResPoolId(standardResPoolIdInfo);
            if (null == templateId || templateId.isEmpty()) {
                if (logger.isDebugEnable()) {
                    logger.debug("规格对应资源池ID不存在，无法创建块存储！");
                }
                return null;
            } else {
                // 设置对应资源池ID
                ebsCreateReq.setStandardId(templateId);
            }
        } else if (EBSCreateModel.paramArray.equals(req.getCreateModel())) {
            BsParam bsCreateParam = new BsParam();
            ebsCreateReq.setBSParamArray(bsCreateParam);

            if (req.getBSParamArray().getTier() != null && req.getBSParamArray().getTier() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setTier(req.getBSParamArray().getTier());
            } else {
                bsCreateParam.setTier(this.defaultTier);
            }
            if (req.getBSParamArray().getRaid() != null && req.getBSParamArray().getRaid() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setRaid(req.getBSParamArray().getRaid());
            } else {
                bsCreateParam.setRaid(this.defaultRaid);
            }
            if (req.getBSParamArray().getStorageNet() != null
                    && req.getBSParamArray().getStorageNet() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setStorageNet(req.getBSParamArray().getStorageNet());
            } else {
                bsCreateParam.setStorageNet(this.defaultStorageNet);
            }
            if (req.getBSParamArray().getResourceType() != null
                    && req.getBSParamArray().getResourceType() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setResourceType(req.getBSParamArray().getResourceType());
            } else {
                bsCreateParam.setResourceType(this.defaultResourceType);
            }
            if (req.getBSParamArray().getStripeWidth() != null
                    && req.getBSParamArray().getStripeWidth() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setStripeWidth(req.getBSParamArray().getStripeWidth());
            }
            bsCreateParam.setTierRule(req.getBSParamArray().getTierRule());
            if (req.getBSParamArray().getTierOpen() != null
                    && req.getBSParamArray().getTierOpen() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setTierOpen(req.getBSParamArray().getTierOpen());
            } else {
                bsCreateParam.setTierOpen(this.defaultTierOpen);
            }
            if (req.getBSParamArray().getVolNum() != null
                    && req.getBSParamArray().getVolNum() != RPPBaseReq.INT_DEFAULT_VAL) {
                bsCreateParam.setVolNum(req.getBSParamArray().getVolNum());
            } else {
                bsCreateParam.setVolNum(this.defaultVolNum);
            }
            bsCreateParam.setVolSize(req.getBSParamArray().getVolSize());
        }

        if (logger.isDebugEnable()) {
            logger.debug("获得创建虚拟硬盘的请求为" + ebsCreateReq.toString());
        }
        return ebsCreateReq;
    }

    /**
     * 设置ebsCreateStatQueryTask字段数据
     * @param ebsCreateStatQueryTask The ebsCreateStatQueryTask to set.
     */
    public void setEbsCreateStatQueryTask(ThreadPoolTaskExecutor ebsCreateStatQueryTask) {
        this.ebsCreateStatQueryTask = ebsCreateStatQueryTask;
    }

    private void assebleRes(RPPEBSCreateResp ebsResp, RuntimeContext respCxt) {
        respCxt.setAttribute(RPPEBSCreateResp.RESP_BODY, ebsResp);
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public void setEbsCreator(EBSCreate ebsCreator) {
        this.ebsCreator = ebsCreator;
    }

    /**
     * 设置ebsQuery字段数据
     * @param ebsQuery The ebsQuery to set.
     */
    public void setEbsQuery(EBSQuery ebsQuery) {
        this.ebsQuery = ebsQuery;
    }

    /**
     * 设置ebsCreateResultQueryInterval字段数据
     * @param ebsCreateResultQueryInterval The ebsCreateResultQueryInterval to set.
     */
    public void setEbsCreateResultQueryInterval(int ebsCreateResultQueryInterval) {
        this.ebsCreateResultQueryInterval = ebsCreateResultQueryInterval;
    }

}
