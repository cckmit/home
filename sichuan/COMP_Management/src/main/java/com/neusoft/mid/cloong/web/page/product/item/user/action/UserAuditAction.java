/*******************************************************************************
 * @(#)ItemAuditAction.java 2013-2-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.user.action;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.item.user.service.UserAuditService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月28日 上午10:24:51
 */
public class UserAuditAction extends BaseAction {

    private static final long serialVersionUID = 2919517611970721685L;

    private UserAuditService userAuditService;


    public UserAuditService getUserAuditService() {
        return userAuditService;
    }

    public void setUserAuditService(UserAuditService userAuditService) {
        this.userAuditService = userAuditService;
    }

    private static LogService logger = LogService.getLogger(UserAuditAction.class);


    /**
     * spring配置 插入审批日志sql的id 
     */
    private String logSqlKey;


    /**
     * 获取界面消息
     */
    private String jsonStr;
    
    /**
     * 审批的用户状态值. 0:业务变更待审；4：注册待审
     */
    private String userStatus;
    
    /**
     * 用户审批结果.
     */
    private String auditInfo;
    
    /**
     * 0:审批通过,1:审批不通过;
     */
    private String auditStatus;
    /**
     * 用于返回界面信息
     */
    private CreateResult resultMessage;

    /**
     * 用户计费ID.
     */
    private String chargesUserId;
    
    public CreateResult getResultMessage() {
        return resultMessage;
    }

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        resultMessage = new CreateResult();
        List<String> userIds = obtainJsonToList(jsonStr);// JSON字条串转换成List集合
        try {// 调用 审批方法
            userAuditService.userAudit(userIds, userId, userStatus,auditInfo, auditStatus,chargesUserId);
        } catch (SQLException e1) {// sql异常
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(getText("user.audit.db.operate.fail"), userId, userStatus), e1);
            this.addActionError(MessageFormat.format(getText("user.audit.db.operate.fail"), userId, userStatus));
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(getText("db.error.msg"));
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(getText("user.audit.db.operate.fail"), userId, userStatus), e);
            this.addActionError(MessageFormat.format(getText("user.audit.db.operate.fail"), userId, userStatus));
            resultMessage.setResultFlage(ConstantEnum.ERROR.toString());
            resultMessage.setResultMessage(getText("db.error.msg"));
            return ConstantEnum.SUCCESS.toString();
        }
        resultMessage.setResultFlage(ConstantEnum.SUCCESS.toString());
        resultMessage.setResultMessage(getText("user.audit.success"));
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 将前台JSON字符串 转换 List
     * @param itemIdStr 前台传入的JSON字符串
     * @return List<String> 返回转换后的List<条目id> 列表
     */
    @SuppressWarnings("unchecked")
    private List<String> obtainJsonToList(String jsonStr) {
        List<String> userIds = new ArrayList<String>();
        try {
            JSONArray json = JSONArray.fromObject(jsonStr);
            userIds = (List<String>) JSONArray.toCollection(json, String.class);
        } catch (JSONException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("user.audit.json.error"), e);
        }
        return userIds;
    }

    public void setLogSqlKey(String logSqlKey) {
        this.logSqlKey = logSqlKey;
    }


    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getLogSqlKey() {
        return logSqlKey;
    }

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return the auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return the chargesUserId
	 */
	public String getChargesUserId() {
		return chargesUserId;
	}

	/**
	 * @param chargesUserId the chargesUserId to set
	 */
	public void setChargesUserId(String chargesUserId) {
		this.chargesUserId = chargesUserId;
	}

}
