package com.neusoft.mid.cloong.web.fourA.login;


import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.RoleSearchService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.login.LoginStatusCode;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;
import com.ultrapower.casp.client.LoginUtil;
import com.ultrapower.casp.common.code.ResultCode;
import com.ultrapower.casp.common.datatran.data.ticket.TransferTicket;
import com.ultrapower.casp.common.datatran.data.user.UserInfo;


public class FourALoginAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	private static final LogService logger = LogService.getLogger(FourALoginAction.class);
    
    private RoleSearchService roleSearchService;
    
	private static final String RET_FAILURE = "FAILURE";

	private static final String RET_SUCCESS = "SUCCESS";

	@Override
	public String execute() {
		logger.info("********4A单点登录开始*********");
		try {
			URL url = Constants.class.getResource("/comm_conf/other/casp_client_config.properties");
			logger.info("************url###########"+url.getPath().toString());
			//URL xmlurl = Constants.class.getResource("/comm_conf/other/casp_client_config.xml");
            //logger.info("************xmlurl###########"+xmlurl.getPath().toString());
			//LoginUtil.getInstance().init(url.getPath().toString(), xmlurl.getPath().toString());
			UserInfo userInfo = new UserInfo();
			//LoginUtil.getInstance().init("/comm_conf/other/casp_client_config.properties", "/comm_conf/other/casp_client_config.xml");
            //LoginUtil.getInstance().initCustom(url.getPath().toString(), xmlurl.getPath().toString());

            //LoginUtil.getInstance().initCustom("/home/cm/4A/COMP/webapps/COMP_Management/WEB-INF/classes/comm_conf/other/casp_client_config.properties", "/home/cm/4A/COMP/webapps/COMP_Management/WEB-INF/classes/comm_conf/other/casp_client_config.xml");

            //LoginUtil.getInstance().init("/home/cm/4A/COMP/webapps/COMP_Management/WEB-INF/classes/comm_conf/other/casp_client_config.properties");
            LoginUtil.getInstance().init(url.getPath().toString());
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			/*String ticket_lhx = request.getParameter("iamcaspticket");
			logger.info("ticket_lhx"+ticket_lhx);*/

			logger.info("LoginUtil.getInstance().isEnable()" + LoginUtil.getInstance().isEnable());
			if (LoginUtil.getInstance().isEnable()) {
				if (LoginUtil.getInstance().checkTicket(request)) {
					String strTic = LoginUtil.getInstance().getTicket(request);
					logger.info("strTic:" + strTic);
					TransferTicket ticket = LoginUtil.getInstance().analysTicket(strTic);

					if (ticket != null && ticket.getRetCode() != null && ResultCode.RESULT_OK.equals(ticket.getRetCode())) {
						userInfo = LoginUtil.getInstance().qryUserByTicket(ticket);
						//ticket.getRootTicket();
						userInfo.setTempKey(ticket.getRootTicket());
						logger.info("userInfo***********************:" + userInfo);
						logger.info("userInfo(AccountID):" + userInfo.getAccountID());
						ActionContext.getContext().getSession()
								.put(SessionKeys.fourAUserInfo.toString(), userInfo);

						if (!ResultCode.RESULT_OK.equals(userInfo.getRetCode())) {
							// 跳转到错误页面，显示错误码；
							errMsg = getText("user.cannot.login");
							logger.error(LoginStatusCode.LOGIN_FAILURE, errMsg);
							return RET_FAILURE;
						} else {
							// 应用资源根据帐号信息做登录后业务处理；
							// 查询并解析票据信息.
			                String userId = userInfo.getAccountID();
			                UserBean user = queryUserInfo(userId);

			                return check4ALoginUserInfo(user,userId);
						}
					} else {
						// 跳转到错误页面，显示错误码；
						errMsg = getText("user.cannot.login");
						logger.error(LoginStatusCode.LOGIN_FAILURE, errMsg);
						return RET_FAILURE;
					}
					
				}
				if (LoginUtil.getInstance().hasAliveServer()) {
					LoginUtil.getInstance().redirectToServer(request, response);
				} else {
					// 使用应用资源本地认证；
					errMsg="4A登录时,无可用的4A系统！";
					logger.error(LoginStatusCode.LOGIN_FAILURE, errMsg);
					return RET_FAILURE;
				}
			} else {
				// 使用应用资源本地认证；
				errMsg = "4A登录时,未启用集中认证";
				logger.error(LoginStatusCode.LOGIN_FAILURE, errMsg);
				return RET_FAILURE;
			}

		} catch (Exception e) {
			logger.info("4A登录出现问题", e);
			return RET_FAILURE;
		}
		
		return RET_SUCCESS;
	}

	/**
	 * 获取用户基本信息，角色信息，应用信息
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
    private UserBean queryUserInfo(String userId)throws SQLException {
        logger.info("*****queryUserInfo的用户ID="+userId);
        UserBean userInfo = null;
        if (!StringUtils.isEmpty(userId)) {
            userInfo = (UserBean) this.ibatisDAO.getSingleRecord("getSingleUser", userId);
            if (userInfo!=null){
                List<RoleBean> roleList = this.roleSearchService.searchRolePermRelation(userId);
                userInfo.setRoles(roleList);
                List<UserAppBean> app = (List<UserAppBean>) ibatisDAO.getData("queryBingAppsbyUserId", userId);
                List<String> appList = new ArrayList<String>();
                if (app.size() > 0) {
                    for (UserAppBean appInfo : app) {
                        appList.add(appInfo.getAppId());
                    }
                }
                userInfo.setAppIdList(appList);
            }
        }
        return userInfo;
    }
    
    /**
     * 校验用户信息并登录.
     * @param userInfo UserBean
     * @param  userId 4A返回的用户ID.
     * @return 验证结果.
     */
    private String check4ALoginUserInfo(UserBean userInfo,String userId){
        if (userInfo == null) { // 用户密码错误
            this.errMsg =MessageFormat.format(getText("4user.name.psw.error"),userId);
            logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, this.errMsg);
            ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), this.errMsg);
            return RET_FAILURE;
        } else {
            if (userInfo.getAppIdList()==null || userInfo.getAppIdList().isEmpty()) { // 4A账号未绑定业务
                this.errMsg =MessageFormat.format(getText("4user.noApp.cannot.login"),userId);
                ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), this.errMsg);
                logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, this.errMsg);
                return RET_FAILURE;
            }
            if (userInfo.getRoles()==null || userInfo.getRoles().isEmpty()) {// 验证用户是否有权限.
                this.errMsg =MessageFormat.format(getText("4user.noPer.cannot.login"),userId);
                ActionContext.getContext().getSession().put(SessionKeys.invalid.toString(), this.errMsg);
                logger.error(LoginStatusCode.LOGIN_VALID_PARA_EXCEPTION, this.errMsg);
                return RET_FAILURE;
            }
            ActionContext.getContext().getSession().put(SessionKeys.userInfo.toString(), userInfo);
            String operation = MessageFormat.format(getText("4user.login.success"),userInfo.getUserId());
            logger.info(operation);
        }
        return RET_SUCCESS;
    }


    public RoleSearchService getRoleSearchService() {
        return roleSearchService;
    }

    public void setRoleSearchService(RoleSearchService roleSearchService) {
        this.roleSearchService = roleSearchService;
    }
}
