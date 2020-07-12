/*******************************************************************************
 * @(#)PmListAction.java 2014-12-30
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.pm.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.core.RoleStatus;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryRoleInfo;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.CabinetInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MachinerRoomInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.PmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-物理机列表Action
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class PmListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(PmListAction.class);

    private List<PmInfo> pmInfoList = new ArrayList<PmInfo>();
    
    private PmInfo queryPmInfo = new PmInfo();
    
    private List<MachinerRoomInfo> machinerRoomList = new ArrayList<MachinerRoomInfo>();
    
    private List<CabinetInfo> cabinetList = new ArrayList<CabinetInfo>();
    
 //   private QueryPmInfo queryPmInfos = new QueryPmInfo();
    
    /**
     * 物理机列表
     */
    public String execute() {
    	if ("zyst".equals(curFun)) { // 资源视图
    		
    	} else { // 业务视图
    	    // 根据appId过滤虚拟机，appId这里直接用，已在父类中将值传过来了
    	    queryPmInfo.setAppId(appId);
    	}
    	try {
    	    this.machinerRoomList = this.ibatisDAO.getData("queryMachinerRoomInfos", null);
    	    if(queryPmInfo.getMachinerRoomId() !=null && !queryPmInfo.getMachinerRoomId().equals("")){
    	    	CabinetInfo cabinetInfo = new CabinetInfo();
    			cabinetInfo.setMachinerRoomId(queryPmInfo.getMachinerRoomId());
    	    	this.cabinetList = this.ibatisDAO.getData("queryCabinetInfos", cabinetInfo);
    	    	
    	    	
    	    	
    	    }
    	    
    	 
//    	  
//    	    if(StringUtils.isNotEmpty(queryPmInfo.getQuerypmState())){
//    	    	queryPmInfo.setPmState(PmState.obtainItemStatusEunm(queryPmInfo.getQuerypmState()));     
//            }
//    	    
//    	    if(StringUtils.isNotEmpty(queryPmInfos.getQuerycurStatus())){
//    	    	queryPmInfo.setCurStatus(CurStatus.obtainItemStatusEunm(queryPmInfo.getQuerycurStatus()));     
//            }
//    	    
    	
    	    
    	    
            this.pmInfoList = this.getPage("countQueryPm", "queryPmInfos", queryPmInfo);
            for(PmInfo pmTmp:pmInfoList){
            	pmTmp.setMemorySize(SubZero.subZero(pmTmp.getMemorySize()));
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询物理机列表数据时错误！原因：查询物理机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }
    

    /**
     * 获取pmInfoList字段数据
     * @return Returns the pmInfoList.
     */
    public List<PmInfo> getPmInfoList() {
        return pmInfoList;
    }

    /**
     * 设置pmInfoList字段数据
     * @param pmInfoList The pmInfoList to set.
     */
    public void setPmInfoList(List<PmInfo> pmInfoList) {
        this.pmInfoList = pmInfoList;
    }

    /**
     * 获取queryPmInfo字段数据
     * @return Returns the queryPmInfo.
     */
    public PmInfo getQueryPmInfo() {
        return queryPmInfo;
    }

    /**
     * 设置queryPmInfo字段数据
     * @param queryPmInfo The queryPmInfo to set.
     */
    public void setQueryPmInfo(PmInfo queryPmInfo) {
        this.queryPmInfo = queryPmInfo;
    }

    /**
     * 获取machinerRoomList字段数据
     * @return Returns the machinerRoomList.
     */
    public List<MachinerRoomInfo> getMachinerRoomList() {
        return machinerRoomList;
    }

    /**
     * 设置machinerRoomList字段数据
     * @param machinerRoomList The machinerRoomList to set.
     */
    public void setMachinerRoomList(List<MachinerRoomInfo> machinerRoomList) {
        this.machinerRoomList = machinerRoomList;
    }

    /**
     * 获取cabinetList字段数据
     * @return Returns the cabinetList.
     */
    public List<CabinetInfo> getCabinetList() {
        return cabinetList;
    }

    /**
     * 设置cabinetList字段数据
     * @param cabinetList The cabinetList to set.
     */
    public void setCabinetList(List<CabinetInfo> cabinetList) {
        this.cabinetList = cabinetList;
    }


	/**
	 * @return the queryPmInfos
	 */
/*	public QueryPmInfo getQueryPmInfos() {
		return queryPmInfos;
	}*/


	/**
	 * @param queryPmInfos the queryPmInfos to set
	 */
	/*public void setQueryPmInfos(QueryPmInfo queryPmInfos) {
		this.queryPmInfos = queryPmInfos;
	}*/
    
}