/*******************************************************************************
 * @(#)UserUpdateSaveAction.java 2014年1月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.userInfo;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.login.RegisterAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户自信息修改保存
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月11日 下午1:31:29
 */
public class UserUpdateSaveAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7687399023069706043L;
    
    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(RegisterAction.class);
    
    /**
     * 用户bean对象
     */
    private UserBean user = new UserBean();
    
    /**
     * 用户绑定的业务IDs.
     */
    private String appIds;
    
    /**
     * 修改之前用户绑定的业务IDs.
     */
    private String oldAppIds;
    
    /**
     * 记录是否可以更改业务  0表示可以更改.
     */
    private String count;
    
	/**
     * 
     * execute 修改用户信息保存DB
     * @return success
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        user.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        /*if(isMoblie()){
            errMsg = "手机号已存在,请更换其他手机号!";
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }
        if(isEmail()){
            errMsg = "邮箱已存在,请更新其他邮箱地址!";
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }*/
    	List<BatchVO> batchVOs = new ArrayList<BatchVO>();
    	//判断是否更改用户业务
    	if(Integer.valueOf(count)<1){
    		//更改用户绑定的业务
    		if(!compareAppIds()){
    			//更新业务
    			for (String appId : appIds.split(", ")) {
                    UserAppBean userAppBean = new UserAppBean();
                    userAppBean.setUserId(user.getUserId());
                    userAppBean.setAppId(appId);
                    //ibatisDAO.insertData("addUserApp", userAppBean);//添加用户业务绑定信息
                    BatchVO batchAppInfo = new BatchVO("ADD", "addUserApp", userAppBean);
                    batchVOs.add(batchAppInfo);
                }
    			msg="用户信息更改成功！用户业务绑定信息变更申请已提交，请等待审核!";
    		}
    	}
        //ibatisDAO.updateData("updateUser", user);
        BatchVO batchUserInfo = new BatchVO("MOD", "updateUser", user);
		batchVOs.add(batchUserInfo);
		try {	
			ibatisDAO.updateBatchData(batchVOs);
            if(StringUtils.isEmpty(msg)){
            	msg = MessageFormat.format(getText("user.update.success"), user.getUserId());
            }
        } catch (SQLException e) {
            errMsg = MessageFormat.format(getText("user.update.failed"), user.getUserId());
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } catch (Exception e) {
            errMsg = MessageFormat.format(getText("user.update.failed"), user.getUserId());
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }
        return ConstantEnum.SUCCESS.toString(); // 返回成功页面
    }
    
    /**
     * 比较业务是否发生变化.
     * @return
     */
    private boolean compareAppIds(){
    	String[] appIdArray = appIds.split(", ");
    	String[] oldAppIdArray = oldAppIds.split(", ");
    	List<String> oldAppList = Arrays.asList(oldAppIdArray);
    	if(appIdArray.length==oldAppIdArray.length){
    		for(String appId:appIdArray){
    			//UserAppBean app = new UserAppBean();
    			//app.setAppId(appId);
    			if(!oldAppList.contains(appId)){
    				return false;
    			}
    		}
    		return true;
    	}
    	return false;
    }
    
    /**
     * isMoblie 判断是否存在重复电话号
     * @return 如果不存在返回false,如果存在，或数据库异常返回true
     */
	private boolean isMoblie(){
        int num = 0;
        try {
            num = ibatisDAO.getCount("getCountMobile", user);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        }
        if(num==0){
          return false;  
        }else{
            return true;
        }
    }
    /**
     * isEmail 判断是否存在重复email
     * @return 如果不存在返回false,如果存在，或数据库异常返回true
     */
	private boolean isEmail(){
        int num = 0;
        try {
            num = ibatisDAO.getCount("getCountEmail", user);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        }
        if(num==0){
          return false;  
        }else{
            return true;
        }
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
	/**
	 * @return the appIds
	 */
	public String getAppIds() {
		return appIds;
	}
	/**
	 * @param appIds the appIds to set
	 */
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	/**
	 * @return the oldAppIds
	 */
	public String getOldAppIds() {
		return oldAppIds;
	}
	/**
	 * @param oldAppIds the oldAppIds to set
	 */
	public void setOldAppIds(String oldAppIds) {
		this.oldAppIds = oldAppIds;
	}
	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}
    
}
