/*******************************************************************************
 * @(#)LoginAction.java 2009-5-27
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.RoleSearchService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com">zhang.ge</a>
 * @version $Revision 1.1 $ 2016-5-12 上午10:05:51
 */
public class VisitAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 失败页面
	 */
	private static final String RET_FAILURE = "FAILURE";

	/**
	 * 无效页面
	 */
	private static final String RET_INVALID = "INVALID";

	/**
	 * 成功页面
	 */
	private static final String RET_SUCCESS = "SUCCESS";

	/**
	 * 记录日志输出
	 */
	private static LogService logger = LogService.getLogger(VisitAction.class);

	/**
	 * 资源池访问统计分析token
	 */
	private String visitToken;

	/**
	 * 角色权限查询服务
	 */
	private RoleSearchService roleSearchService;

	/**
	 * 用户登录时,首先判断用户是否可用，然后判断id和password是否正确；如果不正确，则提示错误并返回登录页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String execute() {
		// 校验用户名密码
		UserBean userInfo;
		String tempUserId=null; 
		//判断token参数一致
		if (visitToken.equals(Constants.VISITTOKEN)) {
			tempUserId = Constants.VISITID;
		}

		// 从数据库中读取与用户输入的用户ID对应的用户信息
		try {
			userInfo = (UserBean) ibatisDAO.getSingleRecord("getSingleUser",
					tempUserId);
		} catch (SQLException e) {
			errMsg = MessageFormat.format(getText("read.error"), tempUserId);
			this.addActionError(errMsg);
			logger.error(LoginStatusCode.LOGIN_EXCEPTION,
					errMsg + e.getMessage(), e);
			return RET_FAILURE;
		} catch (Exception e1) {
			errMsg = MessageFormat.format(getText("read.error"), tempUserId);
			this.addActionError(errMsg);
			logger.error(LoginStatusCode.LOGIN_EXCEPTION,
					errMsg + e1.getMessage(), e1);
			return RET_FAILURE;
		}

		// 若当前用户已被禁用，或者用户输入的用户ID或密码错误，则在登录页面进行提示
		if (null != userInfo) { // 用户ID存在
			try {
				List<RoleBean> roleList = roleSearchService
						.searchRolePermRelation(userInfo.getUserId());
				Boolean isHavePer = false;
				if (roleList.size() > 0) {
					for (RoleBean roleInfo : roleList) {
						List perList = roleInfo.getPermList();
						if (perList.size() > 0) {
							isHavePer = true;
						}
					}
				}
				if (isHavePer) {
					userInfo.setRoles(roleList);
				} else {
					errMsg = getText("user.noPer.cannot.login");
					ActionContext.getContext().getSession()
							.put(SessionKeys.invalid.toString(), errMsg);
					logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION,
							errMsg);
					return RET_INVALID;
				}
				ActionContext.getContext().getSession()
						.put(SessionKeys.userInfo.toString(), userInfo);
				String operationInfo = MessageFormat.format(
						getText("user.login.success"), tempUserId,
						userInfo.getUserName());
				logger.info(operationInfo);

			} catch (SQLException e) {
				errMsg = MessageFormat
						.format(getText("read.error"), tempUserId);
				this.addActionError(errMsg);
				logger.error(LoginStatusCode.LOGIN_EXCEPTION,
						errMsg + e.getMessage(), e);
				return RET_FAILURE;
			}

		} else { // 用户不存在
			errMsg = getText("user.not.exist");
			logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, errMsg);
			ActionContext.getContext().getSession()
					.put(SessionKeys.invalid.toString(), errMsg);
			return RET_INVALID;
		}

		return RET_SUCCESS;
	}

	public void setRoleSearchService(RoleSearchService roleSearchService) {
		this.roleSearchService = roleSearchService;
	}

	public String getVisitToken() {
		return visitToken;
	}

	public void setVisitToken(String visitToken) {
		this.visitToken = visitToken;
	}

}
