/*******************************************************************************
 * @(#)RoleListService.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.service;

import java.sql.SQLException;
import java.util.List;



import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;

/**
 * 角色权限查询服务
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月14日 上午9:39:33
 */
public class PermissionSearchService extends IdentityBaseService {
    /**
     * 
     * queryRoleList 查询权限列表
     * @return 权限列表
     * @throws SQLException  数据库执行异常
     */
    @SuppressWarnings("unchecked")
    public List<PermissionBean> searchPermissionList() throws SQLException{
        return ibatisDAO.getData("queryPermission", null);
    }
}
