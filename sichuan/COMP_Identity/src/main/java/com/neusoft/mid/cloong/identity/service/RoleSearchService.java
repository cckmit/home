/*******************************************************************************
 * @(#)RoleListService.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.core.RoleStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryRoleInfo;
import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;

/**
 * 角色查询服务
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月14日 上午9:39:33
 */
public class RoleSearchService extends IdentityBaseService {
    /**
     * 
     * queryRoleList 查询角色列表
     * @param qRoleInfo 查询条件
     * @return 角色列表
     * @throws SQLException  数据库执行异常
     */
    @SuppressWarnings("unchecked")
    public List<RoleBean> searchRoleList(QueryRoleInfo qRoleInfo) throws SQLException{
        if(StringUtils.isNotEmpty(qRoleInfo.getQueryStatus())){
            qRoleInfo.setStatus(RoleStatus.obtainItemStatusEunm(qRoleInfo.getQueryStatus()));     
        }
        return ibatisDAO.getData("searchRoleList", qRoleInfo);
    }
    /**
     * 
     * queryRoleList 查询角色列表
     * @param qRoleInfo 查询条件
     * @return 角色列表
     * @throws SQLException  数据库执行异常
     */
    @SuppressWarnings("unchecked")
    public RoleBean searchRoleAuth(QueryRoleInfo qRoleInfo) throws SQLException{
        RoleBean role = new RoleBean();
        role.setRoleId(qRoleInfo.getRoleId());
        role.setPermList(ibatisDAO.getData("queryRoleAuth", qRoleInfo));
        return role;
    }
    /**
     * 
     * queryRoleList 查询角色列表
     * @param userid 查询条件
     * @return 角色列表
     * @throws SQLException  数据库执行异常
     */
    @SuppressWarnings("unchecked")
    public List<RoleBean> searchRolePermRelation(String userid) throws SQLException{
        List<RoleBean>  roleList = ibatisDAO.getData("queryUserRoleByUserId", userid);
        for(RoleBean roleInfo:roleList){
            roleInfo.setPermList(ibatisDAO.getData("queryRoleAuth", roleInfo));
        }
        return roleList;
    }
}
