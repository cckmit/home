/*******************************************************************************
 * @(#)BusinessUnbindAction.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理删除业务功能
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class BusinessUnbindAction extends BaseAction {

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(BusinessUnbindAction.class);

    /**
     * serialVersionUID : 序列化版本号
     */
    private static final long serialVersionUID = 7278321721961855694L;

    /**
     * 创建结果
     */
    private CreateResult result;
    
    /**
     * 解绑的实例ID
     */
    private String caseId;

    /**
     * 解绑的业务ID
     */
    private String businessId;

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
            Map<String,String> query = new HashMap<String, String>();
            query.put("businessId", this.businessId);
            query.put("caseId", this.caseId);
            this.ibatisDAO.deleteData("delBusinessCaseByBidCid", query);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), userId));
            result = new CreateResult("fail", "业务解绑失败！" + e.getMessage());
            return ERROR;
        }
        result = new CreateResult("success", "业务解绑成功！");
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 设置businessId字段数据
     * @param businessId The businessId to set.
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取result字段数据
     * @return Returns the result.
     */
    public CreateResult getResult() {
        return result;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
