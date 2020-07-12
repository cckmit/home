/*******************************************************************************
 * @(#)QueryResourceService.java 2014年2月7日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.web.page.console.business.info.ResourceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务视图资源查询服务类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月7日 下午2:22:19
 */
public class QueryResourceService extends BusinessBaseService {

    /**
     * 日志logger
     */
    private static LogService logger = LogService.getLogger(QueryResourceService.class);
    
    /**
     * 虚拟机类型
     */
    private final String vmType="0";
    
    /**
     * 物理机类型
     */
    private final String pmType="1";
    
    /**
     * 
     * queryResourceList 查询资源列表
     * 
     * @param userId 资源所属的用户ID
     * @param businessId 业务ID
     * @return 资源列表MAP
     * @throws SQLException 
     */
    public Map<String,List<ResourceInfo>> queryResourceList(String businessId) throws SQLException{
        Map<String,List<ResourceInfo>> res = new HashMap<String,List<ResourceInfo>>();
        
        Map<String,String> query = new HashMap<String,String>();
        query.put("businessId", businessId);
        List<ResourceInfo> vmResource = this.ibatisDAO.getData("queryVMResourceByUserId", query);
        res.put("vm", vmResource);
        /*List<ResourceInfo> vmBakResource = this.ibatisDAO.getData("queryVmBakResourceByAppId", query);
        res.put("vmBak", vmBakResource);*/
        List<ResourceInfo> pmResource = this.ibatisDAO.getData("queryPMResourceByUserId", query);
        res.put("pm", pmResource);
        List<ResourceInfo> ebsResource = this.ibatisDAO.getData("queryEBSResourceByUserId", query);
        res.put("bs", ebsResource);
        List<ResourceInfo> vlanResource = this.ibatisDAO.getData("getVlanByBusinessId", query);
        res.put("vlan", vlanResource);
        List<ResourceInfo> vlanSdnResource = this.ibatisDAO.getData("getVlanSdnByBusinessId", query);
        res.put("vlanSdn", vlanSdnResource);
        List<ResourceInfo> ipSegmentResource = this.ibatisDAO.getData("getIpSegmentByBusinessId", query);
        res.put("ipSegment", ipSegmentResource);
        List<ResourceInfo> lbResource = this.ibatisDAO.getData("getlbByBusinessId", query);
        res.put("lb", lbResource);
        logger.info("获取业务视图资源概览信息成功！");
        logger.debug("业务ID"+businessId+" 虚拟机： "+vmResource.size()+"");
        logger.debug("业务ID"+businessId+" 物理机： "+pmResource.size()+"");
        logger.debug("业务ID"+businessId+" 云硬盘： "+ebsResource.size()+"");
        
        return res;
    }

}
