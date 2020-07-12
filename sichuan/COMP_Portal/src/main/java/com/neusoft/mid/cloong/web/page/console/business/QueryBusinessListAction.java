/*******************************************************************************
 * @(#)QueryBusinessListActjion.java 2014年1月28日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.business.service.QueryBusinessService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取业务列表Action
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月28日 下午2:29:25
 */
public class QueryBusinessListAction extends ResourceManagementBaseAction {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 2094313569994857016L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(QueryBusinessListAction.class);

    /**
     * 服务类
     */
    private QueryBusinessService service;

    /**
     * 业务列表
     */
    private List<BusinessInfo> businessList = new ArrayList<BusinessInfo>();

    /**
     * 业务ID，从控制台业务视图业务节点跳转传递参数
     */
    private String appIdFromTree;

    /**
     * execute 执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
            // 从控制台业务视图业务节点跳转传递参数，显示当前业务节点概况；否则直接查询当前用户所有业务
            if (null != appIdFromTree && !("").equals(appIdFromTree)) {
                List<String> appIdList = new ArrayList<String>();
                appIdList.add(appIdFromTree);
                UserBean userTemp = new UserBean();
                userTemp.setAppIdList(appIdList);
                businessList = service.queryBusinessList(userTemp);
            } else {
                UserBean user = this.getCurrentUser();
                businessList = service.queryBusinessList(user);
            }
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), null), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), null));
            return ERROR;
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), null), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), null));
            return ERROR;
        }
        return ConstantEnum.SUCCESS.toString();

    }

    /**
     * 获取businessList字段数据
     * @return Returns the businessList.
     */
    public List<BusinessInfo> getBusinessList() {
        return businessList;
    }

    /**
     * 设置service字段数据
     * @param service The service to set.
     */
    public void setService(QueryBusinessService service) {
        this.service = service;
    }

    public String getAppIdFromTree() {
        return appIdFromTree;
    }

    public void setAppIdFromTree(String appIdFromTree) {
        this.appIdFromTree = appIdFromTree;
    }

}
