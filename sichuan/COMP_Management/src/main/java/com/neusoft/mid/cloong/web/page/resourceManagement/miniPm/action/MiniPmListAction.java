/*******************************************************************************
 * @(#)MiniPmListAction.java 2015-2-11
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.miniPm.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.CabinetInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MachinerRoomInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MiniPmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-小型机列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-2-11 上午09:45:16
 */
public class MiniPmListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(MiniPmListAction.class);

    private List<MiniPmInfo> miniPmInfoList = new ArrayList<MiniPmInfo>();
    
    private MiniPmInfo queryMiniPmInfo = new MiniPmInfo();
    
    private List<MachinerRoomInfo> machinerRoomList = new ArrayList<MachinerRoomInfo>();
    
    private List<CabinetInfo> cabinetList = new ArrayList<CabinetInfo>();
    
    /**
     * 小型机列表
     */
    public String execute() {
    	if ("zyst".equals(curFun)) { // 资源视图
    		
    	} else { // 业务视图
    	    // 根据appId过滤虚拟机，appId这里直接用，已在父类中将值传过来了
    	    queryMiniPmInfo.setAppId(appId);
    	}
    	try {
    	    this.machinerRoomList = this.ibatisDAO.getData("getMachinerRoomInfos", null);
    	    if(queryMiniPmInfo.getMachinerRoomId() !=null && !queryMiniPmInfo.getMachinerRoomId().equals("")){
    	    	CabinetInfo cabinetInfo = new CabinetInfo();
    			cabinetInfo.setMachinerRoomId(queryMiniPmInfo.getMachinerRoomId());
    	    	this.cabinetList = this.ibatisDAO.getData("getCabinetInfos", cabinetInfo);
    	    }
            this.miniPmInfoList = this.getPage("countQueryMiniPm", "queryMiniPmInfos", queryMiniPmInfo);
            for(MiniPmInfo miniPmTmp:miniPmInfoList){
            	miniPmTmp.setMemorySize(SubZero.subZero(miniPmTmp.getMemorySize()));
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询小型机列表数据时错误！原因：查询小型机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
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
	 * @return the miniPmInfoList
	 */
	public List<MiniPmInfo> getMiniPmInfoList() {
		return miniPmInfoList;
	}

	/**
	 * @param miniPmInfoList the miniPmInfoList to set
	 */
	public void setMiniPmInfoList(List<MiniPmInfo> miniPmInfoList) {
		this.miniPmInfoList = miniPmInfoList;
	}

	/**
	 * @return the queryMiniPmInfo
	 */
	public MiniPmInfo getQueryMiniPmInfo() {
		return queryMiniPmInfo;
	}

	/**
	 * @param queryMiniPmInfo the queryMiniPmInfo to set
	 */
	public void setQueryMiniPmInfo(MiniPmInfo queryMiniPmInfo) {
		this.queryMiniPmInfo = queryMiniPmInfo;
	}
    
}