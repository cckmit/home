/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.topo;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;

/**
 * 查询登录用户下云硬盘列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class TopoAction extends PageAction {

    private static final long serialVersionUID = -5471800875775849067L;
    
    private String userId;

    @SuppressWarnings("unchecked")
    public String execute() {
        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = getCurrentUser();
        userId = user.getUserId();
       
        return ConstantEnum.SUCCESS.toString();
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
