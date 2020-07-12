/*******************************************************************************
 * @(#)QueryResourcesListAction.java 2014年1月28日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.info.ResourceInfo;
import com.neusoft.mid.cloong.web.page.console.business.service.QueryResourceService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取资源列表列表信息
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月28日 下午2:29:25
 */
public class QueryResourcesListAction extends ResourceManagementBaseAction {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 4094313569994857016L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(QueryResourcesListAction.class);

    /**
     * 概览信息List
     */
    private Map<String, List<ResourceInfo>> resourceList = new HashMap<String, List<ResourceInfo>>();

    /**
     * 服务类
     */
    private QueryResourceService service;

    /**
     * 查询条件 业务ID
     */
    private String queryBusinessId;
    
    /**
     * 分类
     */
    private String type;
    
    /**
     * 获取type字段数据
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * 设置type字段数据
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * execute Action启动方法
     * @return Action状态码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
            resourceList = this.service.queryResourceList(queryBusinessId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("resouce.query.list.fail"), userId), e);
            this.addActionError(getText("resouce.query.list.fail"));
            return ERROR;
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("resouce.query.list.fail"), userId), e);
            this.addActionError(getText("resouce.query.list.fail"));
            return ERROR;
        }

        return ConstantEnum.SUCCESS.toString();

    }

    /**
     * 设置queryBusinessId字段数据
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    /**
     * 获取resourceList字段数据
     * @return Returns the resourceList.
     */
    public Map<String, List<ResourceInfo>> getResourceList() {
        return resourceList;
    }

    /**
     * 设置service字段数据
     * @param service The service to set.
     */
    public void setService(QueryResourceService service) {
        this.service = service;
    }

}
