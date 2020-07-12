/*******************************************************************************
 * @(#)BusinessListAction.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.business.bean.QueryBusiness;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理功能展示业务列表
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class BusinessListAction extends PageAction {

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(BusinessListAction.class);

    /**
     * serialVersionUID : 序列化版本号
     */
    private static final long serialVersionUID = 7278321721961855694L;

    /**
     * 业务列表
     */
    private List<BusinessInfo> businessInfoList;

    /**
     * 业务查询条件
     */
    private QueryBusiness queryBusiness = new QueryBusiness();

    /**
     * 是否为同步方式获取列表
     */
    private String pageSycn;

    /**
     * 业务param传参
     */
    private String businessparam;

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {

        if (queryBusiness.getAfterCreateTime() != null && queryBusiness.getAfterCreateTime().length() == 10) {

            String afterCreateTime = queryBusiness.getAfterCreateTime().replaceAll("-", "");
            queryBusiness.setAfterCreateTime(afterCreateTime);
        }
        if (queryBusiness.getBeforeCreateTime() != null && queryBusiness.getBeforeCreateTime().length() == 10) {
            String beforeCreateTime = queryBusiness.getBeforeCreateTime().replaceAll("-", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(beforeCreateTime));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                beforeCreateTime = sdf.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            queryBusiness.setBeforeCreateTime(beforeCreateTime);
        }

        // 用户可以看到所有业务信息
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        queryBusiness.setBusinessList(user.getAppIdList());
        // String userId = this.getAuthId();
        /*queryBusiness.setCreateUserId(userId);*/
        queryBusiness.setName("".equals(queryBusiness.getName()) ? null : queryBusiness.getName());
        ActionContext.getContext().getParameters().put("param",getBusinessparam());
        pageSycn="false";
        if ("false".equals(pageSycn)) {
            businessInfoList = this.getPage("countBusinessByQueryObj", "queryBusinessByQueryObj", queryBusiness,
                    PageTransModel.ASYNC);
        } else {
            businessInfoList = this.getPage("countBusinessByQueryObj", "queryBusinessByQueryObj", queryBusiness);
        }

        if (queryBusiness.getAfterCreateTime() != null && queryBusiness.getAfterCreateTime().length() == 8) {
            queryBusiness.setAfterCreateTime(queryBusiness.getAfterCreateTime().substring(0, 4) + "-"
                    + queryBusiness.getAfterCreateTime().substring(4, 6) + "-"
                    + queryBusiness.getAfterCreateTime().substring(6));
        }
        if (queryBusiness.getBeforeCreateTime() != null && queryBusiness.getBeforeCreateTime().length() == 8) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(queryBusiness.getBeforeCreateTime()));
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                queryBusiness.setBeforeCreateTime(sdf.format(calendar.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            queryBusiness.setBeforeCreateTime(queryBusiness.getBeforeCreateTime().substring(0, 4) + "-"
                    + queryBusiness.getBeforeCreateTime().substring(4, 6) + "-"
                    + queryBusiness.getBeforeCreateTime().substring(6));
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取businessInfoList字段数据
     * @return Returns the businessInfoList.
     */
    public List<BusinessInfo> getBusinessInfoList() {
        return businessInfoList;
    }

    /**
     * 获取queryBusiness字段数据
     * @return Returns the queryBusiness.
     */
    public QueryBusiness getQueryBusiness() {
        return queryBusiness;
    }

    /**
     * 设置queryBusiness字段数据
     * @param queryBusiness The queryBusiness to set.
     */
    public void setQueryBusiness(QueryBusiness queryBusiness) {
        this.queryBusiness = queryBusiness;
    }

    /**
     * 设置sycn字段数据
     * @param sycn The sycn to set.
     */
    public void setPageSycn(String sycn) {
        this.pageSycn = sycn;
    }

    public String getBusinessparam() {
        return businessparam;
    }

    public void setBusinessparam(String businessparam) {
        this.businessparam = businessparam;
    }
}
