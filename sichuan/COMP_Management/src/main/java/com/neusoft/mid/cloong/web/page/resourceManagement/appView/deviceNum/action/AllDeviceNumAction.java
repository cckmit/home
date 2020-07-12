/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-2-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.appView.deviceNum.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.DeviceNum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 设备数统计
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version 创建时间：2015-1-6 下午4:18:30
 */
public class AllDeviceNumAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -4965007863408723033L;

    private static LogService logger = LogService.getLogger(AllDeviceNumAction.class);
    
    private List<DeviceNum> allDeviceNumList;

    private DeviceNum deviceNum = new DeviceNum();

    /**
     * 查询所有业务设备数
     * @return
     */
    public String execute() {
     
    	try {
    		allDeviceNumList = this.getPage("queryAllDeviceNumCount", "queryAllDeviceNumList", deviceNum);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("app.query.fail"), e);
            this.addActionError(getText("app.query.fail"));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

	public List<DeviceNum> getAllDeviceNumList() {
		return allDeviceNumList;
	}

	public void setAllDeviceNumList(List<DeviceNum> allDeviceNumList) {
		this.allDeviceNumList = allDeviceNumList;
	}

	public DeviceNum getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(DeviceNum deviceNum) {
		this.deviceNum = deviceNum;
	}

}
