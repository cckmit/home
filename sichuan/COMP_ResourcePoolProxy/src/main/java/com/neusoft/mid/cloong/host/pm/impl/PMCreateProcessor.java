/*******************************************************************************
 * @(#)PMCreateProcessor.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

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
import com.neusoft.mid.cloong.host.pm.PMCreate;
import com.neusoft.mid.cloong.host.pm.PMCreateReq;
import com.neusoft.mid.cloong.host.pm.PMStateQuery;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthAdaType;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp.PMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMCreateModel;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机创建处理器
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-26 下午04:05:05
 */
public class PMCreateProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(PMCreateProcessor.class);

    private IbatisDAO ibatisDAO;

    @Value("${pm.create.defaultVal.Security}")
    private String defaultSecurity;

    @Value("${pm.create.defaultVal.EthPurPose")
    private String defaultEthPurPose;
    
    /**
     * 创建PM发送到资源池后查询资源状态及IP、带宽线程池
     */
    private ThreadPoolTaskExecutor senderCreatePMTask;

    /**
     * 和资源池交互的物理机创建接口
     */
    private PMCreate pmCreator;

    /**
     * 请求长度正则表达式,判断长度是否<=31位
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 和资源池交互的物理机状态查询接口
     */
    private PMStateQuery pmStateQuery;

    private static final String IP_VLAN_BIND_STATE_VAILD = "0";

    private static final String IP_VLAN_BIND_STATE_TOVAILD = "2";

    private int interval = 5;

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPPMCreateReq pmCreateReq = (RPPPMCreateReq) reqCxt.getAttribute(RPPBaseReq.REQ_BODY);
        RPPPMCreateResp pmResp = new RPPPMCreateResp();

        // 校验输入
        if (!validateInputPara(pmCreateReq)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, pmResp);
            return FAILURE;
        }
        PMCreateReq pmReq = null;
        try {

            PMCreateModel createModel = pmCreateReq.getCreateModel();
            switch (createModel) {
            case TemplateModel:
                pmReq = assembleTemplateModel(pmCreateReq);
                break;
            case CustomModel:
            	pmReq = assembleCustomModel(pmCreateReq);
                break;
            default:
                break;
            }

        } catch (ServiceStopException e1) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e1);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmResp);
            return FAILURE;
        } catch (UnmatchException e2) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e2);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmResp);
            return FAILURE;
        } catch (Exception e3) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e3);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmResp);
            return FAILURE;
        }
        if (pmReq.getResourceUrl() == null || pmReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmResp);
            return FAILURE;
        }
        // 发送创建请求
        pmResp = pmCreator.createPM(pmReq);

        // 判断请求是否成功，如果没有成功，则不再查询状态
        if (pmResp.getResultCode().equals("00000000")) {
//             更新物理机网卡绑定Vlan同IP段的对应关系为生效
//            updateVlanIPsegmentBind(pmCreateReq, respCxt, pmResp);

            // 发起物理机状态查询任务
            if (!startQueryCreateProgress(respCxt, pmResp, pmReq)) {
                return FAILURE;
            }
        }

        assebleRes(pmResp, respCxt);

        return SUCCESS;
    }

//    /**
//     * 更新Vlan同IP段的绑定关系状态
//     * @author fengj<br>
//     *         2015年3月4日 下午5:21:55
//     * @param pmCreateReq
//     * @throws SQLException
//     */
//    private boolean updateVlanIPsegmentBind(RPPVMCreateReq pmCreateReq, RuntimeContext respCxt, RPPVMCreateResp pmResp) {
//
//        Set<String> vlanIdSet = new HashSet<String>();
//        try {
//            for (List<EthInfo> pmEthList : pmCreateReq.getEthList()) {
//                for (EthInfo ethInfo : pmEthList) {
//                    vlanIdSet.add(ethInfo.getVlanId());
//                }
//            }
//
//            if (vlanIdSet.size() > 0) {
//                Map<String, Object> dbUpdateParam = new HashMap<String, Object>();
//                dbUpdateParam.put("toBindState", IP_VLAN_BIND_STATE_VAILD);
//                dbUpdateParam.put("fromBindState", IP_VLAN_BIND_STATE_TOVAILD);
//                List<String> vlanList = new ArrayList<String>();
//                vlanList.addAll(vlanIdSet);
//                dbUpdateParam.put("vlanList", vlanList);
//                ibatisDAO.updateData("updateVlanIPsegmentBind", dbUpdateParam);
//            }
//
//        } catch (SQLException e) {
//            logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，更新VLAN和IP段绑定关系发生异常,Vlan信息为" + vlanIdSet, e);
//            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATABASE_ERROR, respCxt, pmResp);
//            return false;
//        }
//        return true;
//    }

    /**
     * 开始启动块存储创建进度查询线程
     * @author fengj<br>
     *         2015年3月4日 下午3:59:56
     * @param respCxt
     * @param pmResp
     * @param pmReq
     */
    private boolean startQueryCreateProgress(RuntimeContext respCxt, RPPPMCreateResp pmResp, PMCreateReq pmReq) {
        int numThread = 0;
        // 根据要创建的物理机数量，启动N个线程，将该线程放入线程池
        for (PMInfo tempPMInfo : pmResp.getPmInfoList()) {
            try {
                numThread++;
                CreatePMSenderImpl createPMSenderImp = new CreatePMSenderImpl();
                createPMSenderImp.setIbatisDAO(ibatisDAO);
                createPMSenderImp.setInterval(interval);
                createPMSenderImp.setPmStateQuery(pmStateQuery);
                createPMSenderImp.assemblePMStateReq(tempPMInfo.getPmId(), pmReq);
                senderCreatePMTask.execute(createPMSenderImp);
                if (logger.isDebugEnable()) {
                    logger.debug("启动线程第" + numThread + "个...");
                }
            } catch (TaskRejectedException e1) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e1);
                assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_THREAD_ERROR, respCxt, pmResp);
                return false;
            } catch (Exception e2) {
                logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，启动线程异常", e2);
                assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_THREAD_ERROR, respCxt, pmResp);
                return false;
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("线程执行完毕，共执行" + numThread + "个线程！");
        }
        return true;
    }

    /**
     * 生成通过模板方式创建物理机请求
     * @param pmCreateReq 物理机创建请求
     * @return 生成好的请求Bean
     */
    private PMCreateReq assembleTemplateModel(RPPPMCreateReq pmCreateReq) throws ServiceStopException, UnmatchException {

    	PMCreateReq pmReq = assembleCommonReq(pmCreateReq);

        // 通过物理机规格及镜像id获取对应资源池模板ID.
        StandardResPoolIdInfo standardResPoolIdInfo = new StandardResPoolIdInfo();
        standardResPoolIdInfo.setOsId(pmReq.getOsId());
        standardResPoolIdInfo.setResPoolId(pmReq.getResourcePoolId());
        standardResPoolIdInfo.setResPoolPartId(pmReq.getResourcePoolPartId());
        standardResPoolIdInfo.setStandardId(pmReq.getStandardId());
        acquireStandardResPoolId.setIbatisDAO(ibatisDAO);
        String templateId = acquireStandardResPoolId.getPMStandardResPoolId(standardResPoolIdInfo);
        if (null == templateId || templateId.isEmpty()) {
            if (logger.isDebugEnable()) {
                logger.debug("物理机规格对应模板ID不存在，无法创建物理机！");
            }
            return null;
        } else {
            // 设置对应模板ID
            pmReq.setStandardId(templateId);
        }

        if (logger.isDebugEnable()) {
            logger.debug("获得创建物理机的请求为" + pmReq.toString());
        }
        return pmReq;
    }

    /**
     * 生成通过自定义方式创建物理机请求
     * @param pmCreateReq 物理机创建请求
     * @return 生成好的请求Bean
     */
    private PMCreateReq assembleCustomModel(RPPPMCreateReq pmCreateReq) throws ServiceStopException, UnmatchException {

    	PMCreateReq pmReq = assembleCommonReq(pmCreateReq);

        // 通过物理机规格及镜像id获取对应资源池模板ID.
    	pmReq.setServerType(pmCreateReq.getServerType());
    	pmReq.setPmName(pmCreateReq.getPmName());
    	pmReq.setCPUType(pmCreateReq.getCPUType());
        pmReq.setMemorySizsMB(pmCreateReq.getMemorySizsMB());
        
        pmReq.setOsSizeGB(pmCreateReq.getOsSizeGB());
        if (pmCreateReq.getEthAdaNum() != RPPBaseReq.INT_DEFAULT_VAL) {
            pmReq.setEthAdaNum(pmCreateReq.getEthAdaNum());
        }
        
        
        pmReq.setEthAdaType(pmCreateReq.getEthAdaType());

        if (pmCreateReq.getSCSIAdaNum() != RPPBaseReq.INT_DEFAULT_VAL) {
            pmReq.setSCSIAdaNum(pmCreateReq.getSCSIAdaNum());
        }
        if (pmCreateReq.getHBANum() != RPPBaseReq.INT_DEFAULT_VAL) {
            pmReq.setHBANum(pmCreateReq.getHBANum());
        } 
        pmReq.setHBAType(pmCreateReq.getHBAType());
        
        if (logger.isDebugEnable()) {
            logger.debug("获得创建物理机的请求为" + pmReq.toString());
        }
        return pmReq;
    }

    /**
     * 生成请求Bean的公共部分
     * @param pmCreateReq
     * @return 返回已经填充完公共部分的请求Bean
     * @throws UnmatchException
     * @throws ServiceStopException
     */
    PMCreateReq assembleCommonReq(RPPPMCreateReq pmCreateReq) throws ServiceStopException, UnmatchException {
        PMCreateReq pmReq = new PMCreateReq();

        pmReq.setCreateModel(pmCreateReq.getCreateModel());
        pmReq.setStandardId(pmCreateReq.getStandardId());
        pmReq.setNum(pmCreateReq.getCount());
        pmReq.setOsId(pmCreateReq.getOsId());
        pmReq.setAppId(pmCreateReq.getAppId());
        pmReq.setAppName(pmCreateReq.getAppName());
        if (!StringUtils.isBlank(pmCreateReq.getSecurity())) {
        	pmReq.setSecurity(pmCreateReq.getSecurity());
        } else {
        	pmReq.setSecurity(this.defaultSecurity);
        }

        // 为每个网卡设置必选的用途属性
        for (List<EthInfo> pmEthInfoList : pmCreateReq.getEthList()) {
            for (EthInfo ethInfo : pmEthInfoList) {
                if (ethInfo.getPurpose() == null) {
                    ethInfo.setPurpose(PMEthPurpose.fromValue(this.defaultEthPurPose));
                }
            }
        }

        pmReq.setEthList(pmCreateReq.getEthList());

        pmReq.setResourcePoolId(pmCreateReq.getResourcePoolId());
        pmReq.setResourcePoolPartId(pmCreateReq.getResourcePoolPartId());
        pmReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        pmReq.setTransactionID(this.getTransactonGen().generateTransactionId(pmReq.getResourcePoolId(),
        		pmReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
        		pmReq.getResourcePoolId());
        pmReq.setPassword(resourceInfo.getUserPassword());
        pmReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return pmReq;
    }

    /**
     * 将业务层应答报文封装到传输层报文中
     * @param pmResp 业务层应答报文
     * @param resp 传输层应答报文
     */
    private void assebleRes(RPPPMCreateResp pmResp, RuntimeContext resp) {
        resp.setAttribute(RPPPMCreateResp.RESP_BODY, pmResp);
    }

    private boolean validateInputPara(RPPPMCreateReq req) {
        if (req.getCreateModel() == null) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机创建方式属性CREATE_MODEL", null);
            return false;
        }

        if (PMCreateModel.TemplateModel.equals(req.getCreateModel()) && StringUtils.isBlank(req.getStandardId())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机的规格属性STANDARD_ID", null);
            return false;
        }

        if (PMCreateModel.CustomModel.equals(req.getCreateModel())) {

            if (req.getMemorySizsMB() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机的内存容量属性MEMORY_SIZE", null);
                return false;
            }

            if (req.getOsSizeGB() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机的本地磁盘容量属性OS_SIZE", null);
                return false;
            }
            
            if (req.getEthAdaNum() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机的网卡个数属性EthAdaNum", null);
                return false;
            }
            
            if (req.getSCSIAdaNum() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机的SCSI卡个数属性SCSIAdaNum", null);
                return false;
            }

            if (req.getHBANum() < 1) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机的HBA个数属性HBANum", null);
                return false;
            }
        }

        if (StringUtils.isBlank(req.getOsId()) || (!P_STRING.matcher(req.getOsId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机镜像ID OS_ID", null);
            return false;
        }
        if (req.getCount() < 1) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机数量NUM", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppId()) || (!P_STRING.matcher(req.getAppId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机所属业务APP_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getAppName()) || (!P_STRING.matcher(req.getAppName()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机所属业务APP_NAME", null);
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

    public ThreadPoolTaskExecutor getSenderCreatePMTask() {
		return senderCreatePMTask;
	}

	public void setSenderCreatePMTask(ThreadPoolTaskExecutor senderCreatePMTask) {
		this.senderCreatePMTask = senderCreatePMTask;
	}

	public PMCreate getPmCreator() {
		return pmCreator;
	}

	public void setPmCreator(PMCreate pmCreator) {
		this.pmCreator = pmCreator;
	}

	public PMStateQuery getPmStateQuery() {
		return pmStateQuery;
	}

	public void setPmStateQuery(PMStateQuery pmStateQuery) {
		this.pmStateQuery = pmStateQuery;
	}

	public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setAcquireStandardResPoolId(AcquireStandardResPoolId acquireStandardResPoolId) {
        this.acquireStandardResPoolId = acquireStandardResPoolId;
    }

}
