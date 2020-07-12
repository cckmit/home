/*******************************************************************************
 * @(#)RoleListService.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;

/**
 * 角色查询服务
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月14日 上午9:39:33
 */
public class RoleAuthService extends IdentityBaseService {
    /**
     * 
     * queryRoleList 查询角色列表
     * @param role 查询条件
     * @throws SQLException  数据库执行异常
     */
    @SuppressWarnings("unchecked")
    public void roleAuth(RoleBean role) throws SQLException{

        List<BatchVO> voList = new ArrayList<BatchVO>();

        // 删除角色所有的绑定关系
        BatchVO delUserRoleRelationVO = new BatchVO(BatchVO.OPERATION_DEL,
                "deleteRolePermissionRelation", role);
        voList.add(delUserRoleRelationVO);

        // 将角色和权限的新绑定关系入库
        for (PermissionBean perm : role.getPermList()) {
            Map<String, String> daoParameter = new HashMap<String, String>();
            daoParameter.put("roleId", role.getRoleId());
            daoParameter.put("permissionId", perm.getPermissionId());
            BatchVO vo = new BatchVO(BatchVO.OPERATION_ADD, "insertRolePermissionRelation", daoParameter);
            voList.add(vo);
        }
        this.ibatisDAO.updateBatchData(voList);
    }
}
