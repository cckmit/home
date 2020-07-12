/*******************************************************************************
 * @(#)ResourcePartCreateAction.java 2013-3-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.config.action;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourcePartInfo;
import com.neusoft.mid.cloong.web.page.system.config.service.ResourcePartCreateService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池分区 创建or更新
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-8 上午09:06:56
 */
public class ResourcePartCreateAction extends BaseAction {

    private static final long serialVersionUID = -7474095597614901493L;

    private static LogService logger = LogService.getLogger(ResourcePartCreateAction.class);

    private ResourcePartCreateService resourcePartCreateService;

    public ResourcePartCreateService getResourcePartCreateService() {
        return resourcePartCreateService;
    }

    public void setResourcePartCreateService(ResourcePartCreateService resourcePartCreateService) {
        this.resourcePartCreateService = resourcePartCreateService;
    }

    /**
     * spring 配置 新增insertResourcePartInfo 更新updateResourcePartInfo
     */
    private String optSqlKey;

    /**
     * 界面json字符串
     */
    private String jsonStr;

    private CreateResult result;

    public String execute() {
        ResourcePartInfo resourcePartInfo = formatJson(jsonStr);// 前台json转换成JavaBean
        result = new CreateResult();
        if (resourcePartInfo == null) {// 对象为空，返回界面提示
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("resource.opt.fail"));
            return ConstantEnum.FAILURE.toString();
        }
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        resourcePartInfo.setCreateUser(userId);// 设置创建人Id
        resourcePartInfo.setUpdateUser(userId);// 设置修改人id
        try {
            ResourcePartInfo temp = resourcePartCreateService.queryResPartInfoByResIdAndResPartId(resourcePartInfo);// 查询资源分区
                                                                                                                    // 资源池是否存在。
            if (temp != null) {// 如果资源池 下 存在 资源池分区编码 返回提示信息
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("resource.part.have.code"));
                return ConstantEnum.SUCCESS.toString();
            }
            // 查询资源分区 资源池是否被使用
            int number = resourcePartCreateService.countStandardSynByResIdAndPartId(resourcePartInfo);
            if (number != 0) {// 如果被使用提示
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("resource.part.using"));
                return ConstantEnum.SUCCESS.toString();
            }

            resourcePartCreateService.saveOrUpdate(resourcePartInfo, optSqlKey);
            result.setResultFlage(ConstantEnum.SUCCESS.toString());
            result.setResultMessage(getText("resource.opt.success"));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("resource.db.error"), e);
            this.addActionError(getText("resource.db.error"));
            result.setResultFlage(ConstantEnum.ERROR.toString());
            result.setResultMessage(getText("resource.db.error"));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 用户json字符串转换成bean
     * @param jsonStr 前台获取的json字符串
     * @return ResourceInfo 转换成JavaBean
     */
    private ResourcePartInfo formatJson(String jsonStr) {
        if (logger.isDebugEnable()) {
            logger.debug("JSON转换Bean");
        }
        ResourcePartInfo item = null;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            item = (ResourcePartInfo) JSONObject.toBean(json, ResourcePartInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("resource.json.error"), e);
        }
        if (logger.isDebugEnable()) {
            logger.debug("ResourceInfo:\n" + item.toString());
        }
        return item;
    }

    private String cpuNumTotal;

    private String ramSizeTotal;

    private String discSizeTotal;

    private String resPoolPartId;

    private String resPoolPartName;

    private String resPoolId;

    private String resPoolName;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public CreateResult getResult() {
        return result;
    }

    public void setResult(CreateResult result) {
        this.result = result;
    }

    public String getResPoolName() {
        return resPoolName;
    }

    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getResPoolPartName() {
        return resPoolPartName;
    }

    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    public void setOptSqlKey(String optSqlKey) {
        this.optSqlKey = optSqlKey;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    /**
     * @return the cpuNumTotal
     */
    public String getCpuNumTotal() {
        return cpuNumTotal;
    }

    /**
     * @param cpuNumTotal the cpuNumTotal to set
     */
    public void setCpuNumTotal(String cpuNumTotal) {
        this.cpuNumTotal = cpuNumTotal;
    }

    /**
     * @return the ramSizeTotal
     */
    public String getRamSizeTotal() {
        return ramSizeTotal;
    }

    /**
     * @param ramSizeTotal the ramSizeTotal to set
     */
    public void setRamSizeTotal(String ramSizeTotal) {
        this.ramSizeTotal = ramSizeTotal;
    }

    /**
     * @return the discSizeTotal
     */
    public String getDiscSizeTotal() {
        return discSizeTotal;
    }

    /**
     * @param discSizeTotal the discSizeTotal to set
     */
    public void setDiscSizeTotal(String discSizeTotal) {
        this.discSizeTotal = discSizeTotal;
    }
}
