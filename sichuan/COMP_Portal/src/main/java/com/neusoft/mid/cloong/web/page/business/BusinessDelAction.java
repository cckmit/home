/*******************************************************************************
 * @(#)BusinessDelAction.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.business.service.BusinessDelService;
import com.neusoft.mid.cloong.web.page.common.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理删除业务功能
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class BusinessDelAction extends PageAction {

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(BusinessDelAction.class);

    /**
     * serialVersionUID : 序列化版本号
     */
    private static final long serialVersionUID = 7278321721961855694L;

    /**
     * 服务类
     */
    private BusinessDelService service;

    /**
     * 创建结果
     */
    private String delBusinessId = new String();

    /**
     * 创建结果
     */
    private CreateResult result;

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
            this.service.delBusiness(this.delBusinessId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), userId));
            result = new CreateResult("fail", "业务删除失败！" + e.getMessage());
            return ERROR;
        }
        result = new CreateResult("success", "业务删除成功！");
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 设置delBusinessId字段数据
     * @param delBusinessId The delBusinessId to set.
     */
    public void setDelBusinessId(String delBusinessId) {
        this.delBusinessId = delBusinessId;
    }

    /**
     * 设置service字段数据
     * @param service The service to set.
     */
    public void setService(BusinessDelService service) {
        this.service = service;
    }

    /**
     * 获取result字段数据
     * @return Returns the result.
     */
    public CreateResult getResult() {
        return result;
    }

}
