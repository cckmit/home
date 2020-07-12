package com.neusoft.mid.cloong.web.page.console.business;

import com.neusoft.mid.cloong.web.ConstantEnum;

/**
 * 申请EBS跳转前的Action
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-19 下午05:08:14
 */
public class ResourceApplyAction extends ResourceManagementBaseAction {

    /**
     * 条目ID
     */
    private String itemId;

    /**
     * 业务ID
     */
    private String queryBusinessId;

    private String businessName;

    private static final long serialVersionUID = 5201828322749702209L;

    /**
     * execute 方法执行
     * @return Action状态码
     */
    public String execute() {
        return ConstantEnum.SUCCESS.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取queryBusinessId字段数据
     * @return Returns the queryBusinessId.
     */
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    /**
     * 设置queryBusinessId字段数据
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    /**
     * 获取businessName字段数据
     * @return Returns the businessName.
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * 设置businessName字段数据
     * @param businessName The businessName to set.
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

}
