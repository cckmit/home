/*******************************************************************************
 * @(#)ResourceOperateAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.role;


import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.query.QueryRoleInfo;
import com.neusoft.mid.cloong.identity.service.PermissionSearchService;
import com.neusoft.mid.cloong.identity.service.RoleSearchService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 根据角色查询权限
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月23日 下午1:58:05
 */
public class RoleSearchPermissionAction extends BaseAction  {

    private static final long serialVersionUID = -4167134476548539639L;

    private static LogService logger = LogService.getLogger(RoleSearchPermissionAction.class);
    /**
     * 角色列表
     */
    private String authTree;
    private RoleSearchService roleSearchService;
    private PermissionSearchService permissionSearchService;
    private String roleId;
    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        result = new CreateResult();
        try {
            QueryRoleInfo qRoleInfo = new QueryRoleInfo();
            if(StringUtils.isEmpty(roleId)){
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("role.opt.fail"));
                return ConstantEnum.FAILURE.toString();
            }
            qRoleInfo.setRoleId(roleId);
            RoleBean roleInfo = roleSearchService.searchRoleAuth(qRoleInfo); 
            List<PermissionBean> permList = permissionSearchService.searchPermissionList();
            authTree =  getTreeJson(roleInfo.getPermList(),permList);
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
    private String getTreeJson(List<PermissionBean> rolePermList,List<PermissionBean> allPermList){
        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        HashMap<String, String> rolePermMap = new HashMap<String, String>();
        for(PermissionBean rolePermBean : rolePermList){
            rolePermMap.put(rolePermBean.getPermissionId(), rolePermBean.getPermissionId());
        }
        for (PermissionBean permBean : allPermList){
            jsonObject = new JSONObject();
            jsonObject.put("id", permBean.getPermissionId());
            jsonObject.put("pId", permBean.getParentId());
            jsonObject.put("name", permBean.getName());
            if(rolePermMap.containsKey(permBean.getPermissionId())){
                jsonObject.put("checked", "true");
            }
            if("1".equals(permBean.getPermissionId())){
                jsonObject.put("open", "true");
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    public CreateResult getResult() {
        return result;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    public String getAuthTree() {
        return authTree;
    }
    public void setRoleSearchService(RoleSearchService roleSearchService) {
        this.roleSearchService = roleSearchService;
    }
    public void setPermissionSearchService(PermissionSearchService permissionSearchService) {
        this.permissionSearchService = permissionSearchService;
    }
    

}
