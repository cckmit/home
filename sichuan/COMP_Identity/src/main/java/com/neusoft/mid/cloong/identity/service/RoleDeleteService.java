/*******************************************************************************
 * @(#)RoleListService.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;

/**
 * 角色删除服务
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月14日 上午9:39:33
 */
public class RoleDeleteService extends IdentityBaseService {
    /**
     * 角色池删除操作
     * @param selectSqlKey 删除关键字
     * @param roleId 操作资源池id
     * @throws SQLException 数据库异常
     * @return 返回删除操作结果
     */
    public int roleDelete(String selectSqlKey, String roleId)throws SQLException {
        return ibatisDAO.deleteData(selectSqlKey, roleId);
    }
}
