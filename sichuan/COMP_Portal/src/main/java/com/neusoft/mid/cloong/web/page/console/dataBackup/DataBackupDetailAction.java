/*******************************************************************************
 * @(#)DiskDetailAction.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.dataBackup;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:16:45
 */
public class DataBackupDetailAction extends BaseAction {

    private static final long serialVersionUID = 2501512361728019512L;

    private static LogService logger = LogService.getLogger(DataBackupDetailAction.class);


    
	private String optState;

    public String execute() {
    	
    	String str = optState;
    	if("1".equals(str)){
    		return ConstantEnum.SUCCESS.toString();
    	}else if ("2".equals(str)){
    		return ConstantEnum.FAILURE.toString();
    	}else if ("3".equals(str)){
    		return ConstantEnum.DELETE.toString();
    	}else if ("4".equals(str)){
    		return ConstantEnum.NOPERMISSION.toString();
    	}else if ("5".equals(str)){
    		return ConstantEnum.INTERMEDIATE.toString();
    	}            
        return ConstantEnum.SUCCESS.toString();
    }

	public String getOptState() {
		return optState;
	}

	public void setOptState(String optState) {
		this.optState = optState;
	}

}
