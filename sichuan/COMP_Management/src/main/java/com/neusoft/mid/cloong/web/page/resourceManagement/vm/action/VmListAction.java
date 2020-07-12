/*******************************************************************************
 * @(#)VmListAction.java 2014-12-30
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.vm.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.core.RoleStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryRoleInfo;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.VmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-虚拟机列表Action
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class VmListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(VmListAction.class);
    
    private List<VmInfo> vmInfoList = new ArrayList<VmInfo>();
    
    private VmInfo queryVmInfo = new VmInfo();
    
   // private QueryVmInfo queryVmInfos = new QueryVmInfo();
    
    /**
     * 虚拟机列表
     */
    public String execute() {
    	if ("zyst".equals(curFun)) { // 资源视图
    	    
    	} else { // 业务视图
    		// 根据appId过滤虚拟机，appId这里直接用，已在父类中将值传过来了
    	    queryVmInfo.setAppId(appId);
    	}
    	try {
    		
    		/* if(StringUtils.isNotEmpty(queryVmInfos.getQuerycurStatus())){
    			 queryVmInfos.setCurStatus(VmStatus.obtainItemStatusEunm(queryVmInfos.getQuerycurStatus()));     
             }*/
    		
    		
            this.vmInfoList = this.getPage("countQueryVm", "queryVmInfos", queryVmInfo);
            for(VmInfo vmTmp:vmInfoList){
            	vmTmp.setMemorySize(SubZero.subZero(vmTmp.getMemorySize()));
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询虚拟机列表数据时错误！原因：查询虚拟机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取vmInfoList字段数据
     * @return Returns the vmInfoList.
     */
    public List<VmInfo> getVmInfoList() {
        return vmInfoList;
    }

    /**
     * 设置vmInfoList字段数据
     * @param vmInfoList The vmInfoList to set.
     */
    public void setVmInfoList(List<VmInfo> vmInfoList) {
        this.vmInfoList = vmInfoList;
    }

    /**
     * 获取queryVmInf字段数据
     * @return Returns the queryVmInf.
     */
    public VmInfo getQueryVmInfo() {
        return queryVmInfo;
    }

    /**
     * 设置queryVmInf字段数据
     * @param queryVmInf The queryVmInf to set.
     */
    public void setQueryVmInfo(VmInfo queryVmInfo) {
        this.queryVmInfo = queryVmInfo;
    }

}