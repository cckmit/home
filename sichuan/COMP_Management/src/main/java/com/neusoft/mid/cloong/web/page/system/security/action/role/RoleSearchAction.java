/*******************************************************************************
 * @(#)ResourceOperateAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.role;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.core.RoleStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryRoleInfo;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 角色查询
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月23日 下午1:57:50
 */
public class RoleSearchAction extends PageAction  {

    private static final long serialVersionUID = -4167134476548539639L;

    private static LogService logger = LogService.getLogger(RoleSearchAction.class);
    /**
     * 角色列表
     */
    private List<RoleBean> roleInfos;
    private QueryRoleInfo queryRoleInfo = new QueryRoleInfo();
    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        result = new CreateResult();
        try {
            if(StringUtils.isNotEmpty(queryRoleInfo.getQueryStatus())){
                queryRoleInfo.setStatus(RoleStatus.obtainItemStatusEunm(queryRoleInfo.getQueryStatus()));     
            }
            roleInfos =  this.getPage("countRole", "searchRoleList", queryRoleInfo);
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


    public QueryRoleInfo getQueryRoleInfo() {
        return queryRoleInfo;
    }

    public void setQueryRoleInfo(QueryRoleInfo queryRoleInfo) {
        this.queryRoleInfo = queryRoleInfo;
    }

    public List<RoleBean> getRoleInfos() {
        return roleInfos;
    }




}
