/*******************************************************************************
 * @(#)ResourceCreateAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.config.action;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;
import com.neusoft.mid.cloong.web.page.system.config.service.ResourceCreateService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池 创建or更新
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 下午02:55:08
 */
public class ResourceCreateAction extends BaseAction {

    private static final long serialVersionUID = -1989730075072494365L;

    private static LogService logger = LogService.getLogger(ResourceCreateAction.class);

    private ResourceCreateService resourceCreateService;

    public ResourceCreateService getResourceCreateService() {
        return resourceCreateService;
    }

    public void setResourceCreateService(ResourceCreateService resourceCreateService) {
        this.resourceCreateService = resourceCreateService;
    }

    /**
     * spring 配置 新增insertResourceInfo 更新updateResourceInfo
     */
    private String optSqlKey;

    /**
     * spring 配置 查询重复资源池编码 新增 queryResourceInfoByResPoolId 更新queryResourceInfoByNull
     */
    private String selectSqlKey;

    /**
     * 界面json字符串
     */
    private String jsonStr;

    private CreateResult result;

    public String execute() {
        ResourceInfo resourceInfo = formatJson(jsonStr);// 前台json转换成JavaBean
        result = new CreateResult();
        if (resourceInfo == null) {// 对象为空，返回界面提示
            result.setResultFlage(ConstantEnum.FAILURE.toString());
            result.setResultMessage(getText("resource.opt.fail"));
            return ConstantEnum.FAILURE.toString();
        }
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        resourceInfo.setCreateUser(userId);// 设置创建人Id
        resourceInfo.setUpdateUser(userId);// 设置修改人id
        try {
            ResourceInfo temp = resourceCreateService.queryResourceInfoByResPoolId(resourceInfo,
                    selectSqlKey);// 查询资源池编码是否存在
            if (temp != null) {// 如果资源池id存在返回提示信息
                result.setResultFlage(ConstantEnum.FAILURE.toString());
                result.setResultMessage(getText("resource.code.being"));
                return ConstantEnum.SUCCESS.toString();
            }
            resourceCreateService.saveOrUpdate(resourceInfo, optSqlKey);
            result.setResultFlage(ConstantEnum.SUCCESS.toString());
            result.setResultMessage(getText("resource.opt.success"));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.db.error"), e);
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
    private ResourceInfo formatJson(String jsonStr) {
        if (logger.isDebugEnable()) {
            logger.debug("JSON转换Bean");
        }
        ResourceInfo item = null;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            item = (ResourceInfo) JSONObject.toBean(json, ResourceInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.json.error"), e);
        }
        if (logger.isDebugEnable()) {
            logger.debug("ResourceInfo:\n" + item.toString());
        }
        return item;
    }

    private String resPoolId;

    private String resPoolName;

    private String resPoolZone;

    private String resPoolUrl;

    private String userCode;

    private String userPwd;

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

    public String getResPoolZone() {
        return resPoolZone;
    }

    public void setResPoolZone(String resPoolZone) {
        this.resPoolZone = resPoolZone;
    }

    public String getResPoolUrl() {
        return resPoolUrl;
    }

    public void setResPoolUrl(String resPoolUrl) {
        this.resPoolUrl = resPoolUrl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setOptSqlKey(String optSqlKey) {
        this.optSqlKey = optSqlKey;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public void setSelectSqlKey(String selectSqlKey) {
        this.selectSqlKey = selectSqlKey;
    }

}
