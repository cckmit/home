/*******************************************************************************
 * @(#)BusinessDetailAction.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.business.service.BusinessDetailService;
import com.neusoft.mid.cloong.web.page.common.CreateResult;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理删除业务功能
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class BusinessDetailAction extends PageAction {

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(BusinessDetailAction.class);

    /**
     * serialVersionUID : 序列化版本号
     */
    private static final long serialVersionUID = 7278321721961855694L;

    /**
     * 服务类
     */
    private BusinessDetailService service;

    /**
     * 创建结果
     */
    private String businessId = new String();

    /**
     * resp中的业务详情信息
     */
    private BusinessInfo businessInfo;

    /**
     * 创建结果
     */
    private CreateResult result;

    /**
     * 结果的类型——null 跳转到详情页面，edit 跳转到编辑页面
     */
    private String resultType;

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
            businessInfo = this.service.getBusinessDetail(businessId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), userId));
            result = new CreateResult("fail", "业务删除失败！" + e.getMessage());
            return ERROR;
        }
        result = new CreateResult("success", "业务删除成功！");
        if (!"edit".equals(resultType)) {
            return ConstantEnum.SUCCESS.toString();
        } else {
            return "edit";
        }
    }

    /**
     * 设置businessId字段数据
     * @param businessId The businessId to set.
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取businessInfo字段数据
     * @return Returns the businessInfo.
     */
    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    /**
     * 设置service字段数据
     * @param service The service to set.
     */
    public void setService(BusinessDetailService service) {
        this.service = service;
    }

    /**
     * 获取result字段数据
     * @return Returns the result.
     */
    public CreateResult getResult() {
        return result;
    }

    /**
     * 设置resultType字段数据
     * @param resultType The resultType to set.
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

}
