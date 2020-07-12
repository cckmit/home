package com.neusoft.mid.cloong.web.page.resourceManagement.pm.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.CabinetInfo;

/**
 * 物理机通过机房ID获取机柜列表
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-7 下午1:59:24
 */
public class PmGetCabinetListAction extends ResourceManagementBaseAction
{

	private static final long serialVersionUID = -5024421508278442852L;

	private static Logger logger = Logger.getLogger(PmGetCabinetListAction.class);

	private List<CabinetInfo> cabinetList;//机柜列表
	
	private String machinerRoomId; 

	

	@SuppressWarnings("unchecked")
	public String execute()
	{
	
		try {
			CabinetInfo cabinetInfo = new CabinetInfo();
			cabinetInfo.setMachinerRoomId(machinerRoomId);
			this.cabinetList = this.ibatisDAO.getData("queryCabinetInfos", cabinetInfo);
		} catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION);
        }
		
		return "success";
	}



	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}



	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) {
		PmGetCabinetListAction.logger = logger;
	}



	/**
	 * @return the machinerRoomId
	 */
	public String getMachinerRoomId() {
		return machinerRoomId;
	}



	/**
	 * @param machinerRoomId the machinerRoomId to set
	 */
	public void setMachinerRoomId(String machinerRoomId) {
		this.machinerRoomId = machinerRoomId;
	}



	/**
	 * @return the cabinetByMIDList
	 */
	public List<CabinetInfo> getCabinetList() {
		return cabinetList;
	}



	/**
	 * @param cabinetByMIDList the cabinetByMIDList to set
	 */
	public void setCabinetList(List<CabinetInfo> cabinetList) {
		this.cabinetList = cabinetList;
	}

	
	
	
}
