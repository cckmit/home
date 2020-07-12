/*******************************************************************************
 * @(#)ResourceDeleteAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.config.action;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.system.config.service.ResourceDeleteService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池删除
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 下午02:32:50
 */
public class ResourceDeleteAction extends BaseAction {

    private static final long serialVersionUID = 1544447736517052347L;

    private static LogService logger = LogService.getLogger(ResourceDeleteAction.class);

    private ResourceDeleteService resourceDeleteService;

    public ResourceDeleteService getResourceDeleteService() {
        return resourceDeleteService;
    }

    public void setResourceDeleteService(ResourceDeleteService resourceDeleteService) {
        this.resourceDeleteService = resourceDeleteService;
    }

    /**
     * spring 配置
     */
    private String selectSqlKey;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        result = new CreateResult();
        if ("".equals(resPoolId)) {
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("resource.opt.resPoolId.null"));
            return ConstantEnum.FAILURE.toString();
        }
        try {
            int flg = resourceDeleteService.resourceDelete(selectSqlKey, resPoolId, userId);
            if (flg == -1) {
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("resource.have.resPoolPart"));
                return ConstantEnum.FAILURE.toString();
            } else if (flg == 0) {
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("resource.opt.fail"));
                return ConstantEnum.FAILURE.toString();
            } else {
                result.setResultFlage(ConstantEnum.SUCCESS.toString());
                result.setResultMessage(getText("resource.opt.success"));
                return ConstantEnum.SUCCESS.toString();

            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.db.error"), e);
            this.addActionError(getText("resource.db.error"));
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("resource.db.error"));
            return ConstantEnum.ERROR.toString();
        }
    }

    public CreateResult getResult() {
        return result;
    }

    public void setSelectSqlKey(String selectSqlKey) {
        this.selectSqlKey = selectSqlKey;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

}
