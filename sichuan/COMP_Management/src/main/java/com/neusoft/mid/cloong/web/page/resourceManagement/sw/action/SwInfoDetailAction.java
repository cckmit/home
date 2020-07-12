/*******************************************************************************
 * @(#)MiniPmInfoDetailAction.java 2015年2月11日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.sw.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.SwInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 交换机详情
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年2月11日 上午9:52:01
 */
public class SwInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(SwInfoDetailAction.class);

    /**
     * rep data
     */
    private SwInfo swInfo = new SwInfo();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
            this.swInfo = (SwInfo) this.ibatisDAO.getSingleRecord("querySwInfo", swInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台交换机数据时错误！原因：查询交换机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public SwInfo getSwInfo() {
        return swInfo;
    }

    public void setSwInfo(SwInfo swInfo) {
        this.swInfo = swInfo;
    }

}
