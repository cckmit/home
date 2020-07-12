/*******************************************************************************
 * @(#)BaseAction.java 2012-3-31
 *
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.action;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author <a href="mailto:lj.ljun@neusoft.com">lj.ljun </a>
 * @version $Revision 1.1 $ 2012-3-31 下午03:58:43
 */
public abstract class BaseAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    protected IbatisDAO ibatisDAO;

    protected String msg;

    /**
     * 错误信息，已使用者：网管站告警配置页面;Action错误提示；
     */
    protected String errMsg;

    // 用于鉴权的权限点ID
    protected String authId;

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        if (null != msg && !"".equals(msg)) {
            this.addActionMessage(msg);
        }
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    /**
     * 
     * getCurrentUserId 获取当前登录用户的ID
     * @return 用户ID
     */
    protected String getCurrentUserId(){
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        return userId;
    }
}
