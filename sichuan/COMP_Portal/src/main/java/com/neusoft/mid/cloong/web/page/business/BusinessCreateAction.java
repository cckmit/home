/*******************************************************************************
 * @(#)BusinessListAction.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.business.service.BusinessCreateService;
import com.neusoft.mid.cloong.web.page.common.CreateResult;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.business.info.ResourceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理创建业务功能
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class BusinessCreateAction extends PageAction {

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(BusinessCreateAction.class);

    /**
     * serialVersionUID : 序列化版本号
     */
    private static final long serialVersionUID = 7278321721961855694L;

    /**
     * 新建的业务信息Bean
     */
    private BusinessInfo createBusiness = new BusinessInfo();

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务描述
     */
    private String businessDes;

    /**
     * 业务查询条件
     */
    private String[] bindHost;

    /**
     * 服务类
     */
    private BusinessCreateService service;

    /**
     * 创建结果
     */
    private CreateResult result = new CreateResult();

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
            List<ResourceInfo> hostList = new ArrayList<ResourceInfo>();
            if (bindHost != null) {
                for (String host : bindHost) {
                    ResourceInfo info = new ResourceInfo();
                    info.setResourceId(host.split("\\|\\|\\|")[0]);
                    info.setType(host.split("\\|\\|\\|")[1]);
                    hostList.add(info);
                }
            }
            this.createBusiness.setName(businessName);
            this.createBusiness.setDescription(businessDes);
            service.createBusiness(userId, this.createBusiness, hostList);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), userId));
            result = new CreateResult("fail", e.getMessage());
            return ERROR;
        }
        result = new CreateResult("success", "业务创建成功！");
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 设置createBusiness字段数据
     * @param createBusiness The createBusiness to set.
     */
    public void setCreateBusiness(BusinessInfo createBusiness) {
        this.createBusiness = createBusiness;
    }

    /**
     * 获取bindHost字段数据
     * @return Returns the bindHost.
     */
    public String[] getBindHost() {
        return bindHost;
    }

    /**
     * 设置bindHost字段数据
     * @param bindHost The bindHost to set.
     */
    public void setBindHost(String[] bindHost) {
        this.bindHost = bindHost;
    }

    /**
     * 设置service字段数据
     * @param service The service to set.
     */
    public void setService(BusinessCreateService service) {
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
     * 设置businessName字段数据
     * @param businessName The businessName to set.
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * 设置businessDes字段数据
     * @param businessDes The businessDes to set.
     */
    public void setBusinessDes(String businessDes) {
        this.businessDes = businessDes;
    }

}
