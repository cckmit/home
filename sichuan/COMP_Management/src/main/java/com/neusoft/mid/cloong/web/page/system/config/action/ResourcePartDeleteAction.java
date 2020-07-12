/*******************************************************************************
 * @(#)ResourcePartDeleteAction.java 2013-3-8
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
import com.neusoft.mid.cloong.web.page.system.config.service.ResourcePartDeleteService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池分区删除
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-8 下午01:57:48
 */
public class ResourcePartDeleteAction extends BaseAction {

    private static final long serialVersionUID = 5071542708857116968L;

    private static LogService logger = LogService.getLogger(ResourcePartDeleteAction.class);

    private ResourcePartDeleteService resourcePartDeleteService;

    public ResourcePartDeleteService getResourcePartDeleteService() {
        return resourcePartDeleteService;
    }

    public void setResourcePartDeleteService(ResourcePartDeleteService resourcePartDeleteService) {
        this.resourcePartDeleteService = resourcePartDeleteService;
    }

    /**
     * 资源池编码
     */
    private String resPoolId;

    /**
     * 资源池分区编码
     */
    private String resPoolPartId;

    /**
     * 返回信息
     */
    private CreateResult result;

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        result = new CreateResult();
        if ("".equals(resPoolId) || "".equals(resPoolPartId)) {
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("resource.part.opt.fial"));
            return ConstantEnum.FAILURE.toString();
        }
        try {

            int flg = resourcePartDeleteService.countCaseByResIdAndResPartId(resPoolId,
                    resPoolPartId);
            if (flg != 0) {
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("resource.part.using.not.del"));
                return ConstantEnum.FAILURE.toString();
            }
            resourcePartDeleteService.resPartDel(resPoolId, resPoolPartId, userId);
            result.setResultFlage(ConstantEnum.SUCCESS.toString());
            result.setResultMessage(getText("resource.part.del.success"));
            return ConstantEnum.SUCCESS.toString();

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

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

}
