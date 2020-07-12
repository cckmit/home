package com.neusoft.mid.cloong.identity.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;

/**
 * 角色更新服务
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 上午11:06:06
 */
public class RoleOperateService extends IdentityBaseService {

    /**
     * 通用更新 状态（暂停、恢复、终止）
     * @param roleInfo  角色信息
     * @throws SQLException 数据库异常
     */
    public void operate(RoleBean roleInfo) throws SQLException {
        ibatisDAO.updateData("updateRoleInfo", roleInfo);
    }
    
}
