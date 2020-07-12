/*******************************************************************************
 * @(#)UserSaveAction.java 2014年1月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.user;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.AppStatus;
import com.neusoft.mid.cloong.identity.exception.UserOperationException;
import com.neusoft.mid.cloong.identity.service.UserService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 保存用户信息Action
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月13日 下午2:17:02
 */
public class UserOperateAction extends BaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576847173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(UserOperateAction.class);

    // ~~~~~~~~~~~ Spring area START ~~~~~~~~~~~//
    /**
     * 用户操作服务service
     */
    private UserService userService;

    /**
     * 操作结果
     */
    private CreateResult result;

    // ^^^^^^^^^^^ Spring area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ request parameter area START ~~~~~~~~~~~//

    /**
     * 用户信息----rep data
     */
    private UserBean user = new UserBean();

    /**
     * 用户绑定的权限ID列表
     */
    private String[] roleIds;
    
    /**
     * 用户绑定业务Id列表.
     */
    private String[] appIds;
    

    // ^^^^^^^^^^^ request parameter area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ response data area START ~~~~~~~~~~~~~//

    // NOTHINE

    // ^^^^^^^^^^^ response data area END ^^^^^^^^^^^^^^^^^^//
    //

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
            // 对象为空，返回界面提示
            if (user == null) {
                result = new CreateResult(ConstantEnum.FAILURE.toString(), getText("user.opt.fail"));
                return ConstantEnum.FAILURE.toString();
            }

            //更新角色信息
            if (roleIds != null) {
                for (String roleId : roleIds) {
                    RoleBean role = new RoleBean();
                    role.setRoleId(roleId);
                    user.getRoles().add(role);
                }
            }
            //removeOldBindApp();解决事务问题，移到service内处理
            if(appIds!=null){
        		for(String appId:appIds){
        			UserAppBean appBean = new UserAppBean();
        			appBean.setUserId(user.getUserId());
        			appBean.setAppId(appId);
        			appBean.setAppBind_status(AppStatus.AUDITPASS.getCode());
        			user.getApps().add(appBean);
        		}
            }
            this.userService.updateUser(user);
            result = new CreateResult(ConstantEnum.SUCCESS.toString(), getText("user.opt.success"));
            logger.info("成功保存用户信息!");
        } catch (UserOperationException e) {
            logger.info( e.getMessage());
            result = new CreateResult(ConstantEnum.ERROR.toString(), e.getMessage());
            return ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"修改用户信息失败",e);
            this.addActionError(getText("user.opt.error"));
            result = new CreateResult(ConstantEnum.ERROR.toString(), getText("user.opt.error"));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    private void removeOldBindApp() throws SQLException{
    	UserAppBean app =new UserAppBean();
    	app.setUserId(user.getUserId());
    	ibatisDAO.deleteData("delUserApp", app);
    }
    
   /* *//**
     * 比较用户绑定的业务是否发生变化,true：没有发生变化,false：发生变化
     * @param userId
     * @return
     *//*
    @SuppressWarnings("unchecked")
	private boolean compareAppIds(String userId){
    	try {
			 List<UserAppBean> userAppList = ibatisDAO.getData("queryAppsbyUserId", userId);
			 if(appIds.length==userAppList.size()){
		    		for(String appId:appIds){
		    			UserAppBean app = new UserAppBean();
		    			app.setAppId(appId);
		    			if(!userAppList.contains(app)){
		    				return false;
		    			}
		    		}
		    		return true;
		    }
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询用户绑定业务失败", e);
		}
    	return false;
    }*/
    
    /**
     * 获取user字段数据
     * @return Returns the user.
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * 设置user字段数据
     * @param user The user to set.
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * 设置userService字段数据
     * @param userService The userService to set.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取roleIds字段数据
     * @return Returns the roleIds.
     */
    public String[] getRoleIds() {
        return roleIds;
    }

    /**
     * 设置roleIds字段数据
     * @param roleIds The roleIds to set.
     */
    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 获取result字段数据
     * @return Returns the result.
     */
    public CreateResult getResult() {
        return result;
    }

    /**
     * 设置result字段数据
     * @param result The result to set.
     */
    public void setResult(CreateResult result) {
        this.result = result;
    }

	/**
	 * @return the appIds
	 */
	public String[] getAppIds() {
		return appIds;
	}

	/**
	 * @param appIds the appIds to set
	 */
	public void setAppIds(String[] appIds) {
		this.appIds = appIds;
	}

}
