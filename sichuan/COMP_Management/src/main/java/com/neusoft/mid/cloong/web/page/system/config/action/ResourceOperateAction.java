/*******************************************************************************
 * @(#)ResourceOperateAction.java 2013-3-7
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
import com.neusoft.mid.cloong.web.page.system.config.service.ResourceOperateService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池 操作 （暂停、恢复、终止）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 下午02:10:27
 */
public class ResourceOperateAction extends BaseAction {

    private static final long serialVersionUID = -4167134476548539639L;

    private static LogService logger = LogService.getLogger(ResourceOperateAction.class);

    private ResourceOperateService resourceOperateService;

    public ResourceOperateService getResourceOperateService() {
        return resourceOperateService;
    }

    public void setResourceOperateService(ResourceOperateService resourceOperateService) {
        this.resourceOperateService = resourceOperateService;
    }

    /**
     * spring配置 设置状态
     */
    private String status;

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
            resourceOperateService.operate(resPoolId, status, userId);
            result.setResultFlage(ConstantEnum.SUCCESS.toString());
            result.setResultMessage(getText("resource.opt.success"));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.db.error"), e);
            this.addActionError(getText("resource.db.error"));
            result.setResultFlage(ConstantEnum.ERROR.toString());
            result.setResultMessage(getText("resource.db.error"));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public CreateResult getResult() {
        return result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolId() {
        return resPoolId;
    }

}
