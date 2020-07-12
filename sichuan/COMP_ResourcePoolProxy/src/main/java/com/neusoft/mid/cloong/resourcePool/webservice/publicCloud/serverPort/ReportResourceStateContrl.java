/*******************************************************************************
 * @(#)VMStandardReport.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.serverPort;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.cloudmaster.cmp.service.lifeCycle.huawei.ResourceUsedSet;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.info.ReportResourceState;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池容量上报处理
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-9 上午10:19:57
 */
public class ReportResourceStateContrl {

    private static LogService logger = LogService.getLogger(ReportResourceStateContrl.class);

    private IbatisDAO ibatisDao;

    /**
     * 资源池容量上报处理
     * @param resourcePoolId 资源池ID
     * @param resourceType   资源类型
     * @param resourceTotal  资源总量
     * @param resourceUsed   已用资源占用百分数
     * @return InterfaceResultCode
     */
    public InterfaceResultCode process(String resourcePoolId, String resourcePoolPartId, List<ResourceUsedSet> resourceStateList) {
        
        try {
        	for(Iterator<ResourceUsedSet> it = resourceStateList.iterator(); it.hasNext();){
           	 
           	    ResourceUsedSet resourceUsedSet = it.next();
           	 
                ReportResourceState reprotResourceState = new ReportResourceState();
                reprotResourceState.setResourcePoolId(resourcePoolId);
                reprotResourceState.setResourcePoolPartId(resourcePoolPartId);
                reprotResourceState.setResourceType(typeParse(String.valueOf(resourceUsedSet.getResourceType())));
                reprotResourceState.setResourceTotal(String.valueOf(resourceUsedSet.getResourceTotal()));
                reprotResourceState.setResourceUsed(String.valueOf(resourceUsedSet.getResourceUsed()));
                
                ibatisDao.insertData("insertReportResourceStates", reprotResourceState);
            }
        	
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "添加资源池容量上报信息出错", e);
            return InterfaceResultCode.RESOURCE_POOL_ROXY_DATABASE_ERROR;
        }catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "添加资源池容量上报信息出错", e);
            return InterfaceResultCode.RESOURCE_POOL_ROXY_DATABASE_ERROR;
        }
        return InterfaceResultCode.SUCCESS;
    }

    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    /**
     * 资源类型转换
     * @param type
     * @return
     */
    private String typeParse(String type) {
    	
        String returnType = "0";
        
        if (type.equals("6")) {//虚拟化平台虚拟CPU
            returnType = "0";
        }else if (type.equals("7")) {//虚拟化平台虚拟内存
            returnType = "1";
        }else if (type.equals("11")) {//虚拟化平台存储
            returnType = "2";
        }else if (type.equals("0")) {//物理机
            returnType = "3";
        }
        return returnType;
    }

}
