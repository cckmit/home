/*******************************************************************************
 * @(#)ResourceOperateAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.role;


import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.JSONUtils;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.service.RoleOperateService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 角色修改
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月23日 下午1:57:18
 */
public class RoleOperateAction extends BaseAction {

    private static final long serialVersionUID = -4167134476548539639L;

    private static LogService logger = LogService.getLogger(RoleOperateAction.class);

    private RoleOperateService roleOperateService;
    /**
     * 界面json字符串
     */
    private String jsonStr;

    public RoleOperateService getRoleOperateService() {
        return roleOperateService;
    }

    public void setRoleOperateService(RoleOperateService roleOperateService) {
        this.roleOperateService = roleOperateService;
    }

    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        result = new CreateResult();
        jsonStr = jsonStr.replace("\n", "\\r\\n");
        RoleBean roleInfo = (RoleBean) JSONUtils.formatJson(jsonStr, RoleBean.class);
        if (null==roleInfo) {
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("role.opt.resPoolId.null"));
            return ConstantEnum.FAILURE.toString();
        }
        try {
            roleOperateService.operate(roleInfo);
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

    public CreateResult getResult() {
        return result;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }


}
