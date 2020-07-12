/*******************************************************************************
 * @(#)LogoutAction.java 2009-6-6
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户登出系统,清空session中的所有内容
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-6 下午03:50:58
 */
public class LogoutAction extends BaseAction {

    private static LogService logger = LogService.getLogger(LogoutAction.class);

    private static final long serialVersionUID = 1L;

    private static final String RET_SUCCESS = "SUCCESS";

    public String execute() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        String operationInfo = null;
        if (null != session) {
            UserBean userInfo = (UserBean) session.getAttribute(SessionKeys.userInfo.toString());

            if (null != userInfo) {
                String userId = userInfo.getUserId();
                String userName = userInfo.getUserName();
                operationInfo = MessageFormat.format(getText("user.logout.system"), userId,
                        userName);

                session.removeAttribute(SessionKeys.userInfo.toString());
                session.removeAttribute(SessionKeys.invalid.toString());
                session.removeAttribute(SessionKeys.random.toString());
                session.invalidate();

            }
        }
        return RET_SUCCESS;
    }

}
