/*******************************************************************************
 * @(#)ResourceDeleteAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.role;


import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.service.RoleDeleteService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 角色删除
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月14日 下午2:17:51
 */
public class RoleDeleteAction extends BaseAction {

    private static final long serialVersionUID = 1544447736517052347L;

    private static LogService logger = LogService.getLogger(RoleDeleteAction.class);

    private RoleDeleteService roleDeleteService;


    public RoleDeleteService getRoleDeleteService() {
        return roleDeleteService;
    }

    public void setRoleDeleteService(RoleDeleteService roleDeleteService) {
        this.roleDeleteService = roleDeleteService;
    }

    /**
     * spring 配置
     */
    private String selectSqlKey;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        result = new CreateResult();
        if ("".equals(roleId)) {
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("role.opt.roleId.null"));
            return ConstantEnum.FAILURE.toString();
        }
        try {
            int flg = roleDeleteService.roleDelete(selectSqlKey, roleId);
            if (flg == 1) {
                result.setResultFlage(ConstantEnum.SUCCESS.toString());
                result.setResultMessage(getText("role.opt.success"));
                return ConstantEnum.SUCCESS.toString();
            } else {
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("role.opt.fail"));
                return ConstantEnum.FAILURE.toString();

            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("role.db.error"), e);
            this.addActionError(getText("role.db.error"));
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("role.db.error"));
            return ConstantEnum.ERROR.toString();
        }
    }

    public CreateResult getResult() {
        return result;
    }

    public void setSelectSqlKey(String selectSqlKey) {
        this.selectSqlKey = selectSqlKey;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }



}
