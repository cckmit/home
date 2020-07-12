/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.dataBackup;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询登录用户下云硬盘列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class DataBackupListAction extends PageAction {

    private static final long serialVersionUID = -5471800875775849067L;

    private static LogService logger = LogService.getLogger(DataBackupListAction.class);
    	
    /**
     * 云硬盘所属的业务ID
     */
    private String businessId;
    
	private String businessName;
    
	private String optState;

    @SuppressWarnings("unchecked")
    public String execute() {

    
    	return ConstantEnum.SUCCESS.toString();
    
    }

	public String getOptState() {
		return optState;
	}

	public void setOptState(String optState) {
		this.optState = optState;
	}

   

}
