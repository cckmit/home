/*******************************************************************************
 * @(#)ResourceOperateAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.role;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.service.RoleAuthService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 授权
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月23日 下午1:56:44
 */
public class RoleAuthAction extends BaseAction  {

    private static final long serialVersionUID = -4167134476548539639L;

    private static LogService logger = LogService.getLogger(RoleAuthAction.class);
    /**
     * 角色列表
     */
    private String roleId;
    private String checkedNodesStr;
    private RoleAuthService roleAuthService;
    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        result = new CreateResult();
        try {
            if (StringUtils.isEmpty(roleId)) {// 对象为空，返回界面提示
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("role.opt.fail"));
                return ConstantEnum.FAILURE.toString();
            }
            
            RoleBean roleinfo = new RoleBean();
            roleinfo.setRoleId(roleId);
            if(StringUtils.isNotEmpty(checkedNodesStr)){
                String[] nodes = checkedNodesStr.split(";");
                for(int i=0 ;i<nodes.length;i++){
                    PermissionBean permInfo = new PermissionBean();
                    permInfo.setPermissionId(nodes[i]);
                    roleinfo.getPermList().add(permInfo);
                }
            }
           
            roleAuthService.roleAuth(roleinfo);
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

    public void setCheckedNodesStr(String checkedNodesStr) {
        this.checkedNodesStr = checkedNodesStr;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleAuthService(RoleAuthService roleAuthService) {
        this.roleAuthService = roleAuthService;
    }
    

}
