/*******************************************************************************
 * @(#)BusinessListService.java 2014年2月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.web.page.business.bean.QueryBusiness;
import com.neusoft.mid.cloong.web.page.business.exception.BusinessOperationException;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.business.info.ResourceInfo;
import com.neusoft.mid.cloong.web.page.console.business.service.QueryResourceService;

/**
 * 业务列表Serivce服务类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月15日 下午1:57:45
 */
public class BusinessDetailService {

    /**
     * ibatisDAO;
     */
    private IbatisDAO dao;

    /**
     * 查询资源服务
     */
    private QueryResourceService service;

    /**
     * 设置dao字段数据
     * @param dao The dao to set.
     */
    public void setDao(IbatisDAO dao) {
        this.dao = dao;
    }

    /**
     * getBusinessDetail 获得一个业务的详情信息
     * @param businessId 业务ID
     * @param userId 用户ID
     * @return 业务详情
     * @throws BusinessOperationException 业务操作异常
     */
    public BusinessInfo getBusinessDetail(String businessId)
            throws BusinessOperationException {
        try {
            QueryBusiness qb = new QueryBusiness();
            qb.setBusinessId(businessId);
            BusinessInfo businessInfo = (BusinessInfo) this.dao.getSingleRecord(
                    "queryBusinessByQueryObj", qb);
            Map<String,List<ResourceInfo>> resourceInfos = service.queryResourceList(businessId);
            
            List<ResourceInfo> resList = resourceInfos.get("vm");
            List<ResourceInfo> pmList = resourceInfos.get("pm");
            if(pmList!=null){
                for(ResourceInfo resourceInfo : resourceInfos.get("pm")){
                    resList.add(resourceInfo);
                }
            }
            
            businessInfo.setResourceList(resList);
            return businessInfo;
        } catch (SQLException e) {
            throw new BusinessOperationException("删除业务失败！");
        }
    }

    /**
     * 设置service字段数据
     * @param service The service to set.
     */
    public void setService(QueryResourceService service) {
        this.service = service;
    }

}
