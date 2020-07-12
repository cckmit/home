package com.neusoft.mid.cloong.identity.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.UUID;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;

/**
 * 角色创建服务
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月13日 下午2:28:38
 */
public class RoleCreateService extends IdentityBaseService {
    /**
     * 
     * saveOrUpdate 保存或更新角色
     * @param roleInfo 角色信息
     * @param optSqlKey 创建关键字
     * @throws SQLException 数据库异常
     * @throws UnsupportedEncodingException 编码异常
     */
    public void saveOrUpdate(RoleBean roleInfo, String optSqlKey, UserBean nowUser) throws SQLException, UnsupportedEncodingException {
        roleInfo.setRoleId(UUID.randomUUID().toString());
        roleInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置创建时间
        ibatisDAO.updateData(optSqlKey, roleInfo);
    }
    /**
     * 
     * queryRoleInfoByRoleName 按角色名称查询角色
     * @param roleInfo  角色信息
     * @param selectSqlKey  查询关键字
     * @return  返回角色查询结果
     * @throws SQLException 数据库异常
     */
    public RoleBean queryRoleInfoByRoleName(RoleBean roleInfo,String selectSqlKey) throws SQLException {
        RoleBean temp = (RoleBean) ibatisDAO.getSingleRecord(
                selectSqlKey, roleInfo);
        return temp;
    }
}
