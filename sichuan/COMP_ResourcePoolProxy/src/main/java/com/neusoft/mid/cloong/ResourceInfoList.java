/*******************************************************************************
 * @(#)ResourceInfo.java 2013-2-16
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.grains.commons.route.api.IRouteInfoSupplier;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 预读取资源池信息，放置到缓存中
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-16 下午02:20:45
 */
public class ResourceInfoList implements IRouteInfoSupplier {

    private static LogService logger = LogService.getLogger(ResourceInfoList.class);

    private IbatisDAO dao;

    @Override
    public Map<String, Object> supplyRouteInfo() {
        List<ResourcePoolInfo> resourcePoolInfos = null;
        try {
            resourcePoolInfos = dao.getData("queryResourcePoolInfo", null);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "从数据库中读取资源池信息失败", e);
            return null;
        }
        return convertListToMap(resourcePoolInfos);
    }

    private Map<String, Object> convertListToMap(List<ResourcePoolInfo> resourcePoolInfos) {
        if (null == resourcePoolInfos) {
            return null;
        }
        Map<String, Object> tempMap = new ConcurrentHashMap<String, Object>();
        for (ResourcePoolInfo tempDate : resourcePoolInfos) {
            tempMap.put(tempDate.getResourcePoolId(), tempDate);
        }
        return tempMap;
    }

    public void setDao(IbatisDAO dao) {
        this.dao = dao;
    }
}
