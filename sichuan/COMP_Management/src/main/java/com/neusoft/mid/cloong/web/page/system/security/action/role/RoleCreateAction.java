/*******************************************************************************
 * @(#)QueryUserListAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.role;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.JSONUtils;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.RoleCreateService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 
 * 浏览角色列表
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月10日 上午10:53:45
 */
public class RoleCreateAction extends BaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;
    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(RoleCreateAction.class);
    /**
     * 界面json字符串
     */
    private String jsonStr;
    private CreateResult result;
    
    private String selectSqlKey;
    
    private String optSqlKey;
    
    private RoleCreateService roleCreateService;
    

    public void setRoleCreateService(RoleCreateService roleCreateService) {
        this.roleCreateService = roleCreateService;
    }

    /**
     * 
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        RoleBean roleInfo = (RoleBean) JSONUtils.formatJson(jsonStr, RoleBean.class);
        result = new CreateResult();
        if (roleInfo == null) {// 对象为空，返回界面提示
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("role.opt.fail"));
            return ConstantEnum.FAILURE.toString();
        }
        // session中获取用户ID
        String userId = getCurrentUserId();
        // session中获取用户
        UserBean nowUser = (UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString());
        roleInfo.setCreateUserId(userId);
        try {
            RoleBean temp = roleCreateService.queryRoleInfoByRoleName(roleInfo, selectSqlKey);// 查询角色名是否存在
            if (temp != null) {// 如果资源池id存在返回提示信息
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("role.name.being"));
                return ConstantEnum.ERROR.toString();
            }
            roleCreateService.saveOrUpdate(roleInfo, optSqlKey, nowUser);
            result.setResultFlage(ConstantEnum.SUCCESS.toString());
            result.setResultMessage(getText("role.opt.success"));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("role.db.error"), e);
            this.addActionError(getText("role.db.error"));
            result.setResultFlage(ConstantEnum.ERROR.toString());
            result.setResultMessage(getText("role.db.error"));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }
    
    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getSelectSqlKey() {
        return selectSqlKey;
    }

    public void setSelectSqlKey(String selectSqlKey) {
        this.selectSqlKey = selectSqlKey;
    }

    public String getOptSqlKey() {
        return optSqlKey;
    }

    public void setOptSqlKey(String optSqlKey) {
        this.optSqlKey = optSqlKey;
    }

    public CreateResult getResult() {
        return result;
    }

    public void setResult(CreateResult result) {
        this.result = result;
    }

}
