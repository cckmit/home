/*******************************************************************************
 * @(#)HostListAction.java 2014年1月28日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.business.info.ResourceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取主机列表Action
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月28日 下午2:29:25
 */
public class HostListAction extends ResourceManagementBaseAction {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 2094313569994857016L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(HostListAction.class);

    /**
     * 主机列表
     */
    private List<ResourceInfo> resourceList = new ArrayList<ResourceInfo>();

    /**
     * 主机名称
     */
    private String queryHostName = null;

    /**
     * execute 执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        String userId = this.getCurrentUserId();
        System.out.println(nodeId);
        try {
            Map<String, String> queryMap = new HashMap<String, String>();
            if (StringUtils.isBlank(queryHostName)){
                queryHostName = null;
            }
            queryMap.put("name", queryHostName);
            queryMap.put("userId", userId);
            resourceList = this.getPage("countHostByUserId", "getHostByUserId", queryMap,
                    PageTransModel.ASYNC);
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), userId));
            return ERROR;
        }
        return ConstantEnum.SUCCESS.toString();

    }

    /**
     * 获取resourceList字段数据
     * @return Returns the resourceList.
     */
    public List<ResourceInfo> getResourceList() {
        return resourceList;
    }

    /**
     * 设置resourceList字段数据
     * @param resourceList The resourceList to set.
     */
    public void setResourceList(List<ResourceInfo> resourceList) {
        this.resourceList = resourceList;
    }

    /**
     * 获取queryHostName字段数据
     * @return Returns the queryHostName.
     */
    public String getQueryHostName() {
        return queryHostName;
    }

    /**
     * 设置queryHostName字段数据
     * @param queryHostName The queryHostName to set.
     */
    public void setQueryHostName(String queryHostName) {
        this.queryHostName = queryHostName;
    }

}
