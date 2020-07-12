package com.neusoft.mid.cloong.web.page.resourceManagement.realTime.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RealTime;

/**
 * 资源视图->实时性能->折线图
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version Revision 1.0 2015年1月8日 上午9:11:22
 */
public class RealTimeReportAction extends ResourceManagementBaseAction {
	private static final long serialVersionUID = -5024421508278442852L;

	private static Logger logger = Logger.getLogger(RealTimeReportAction.class);

	private String deviceId;// 设备Id
	
	private String deviceName;//设备名称
	
	private String deviceType;// 设备类型：VM-虚拟机、PM-物理机

	private String staType;// 统计类型：CPU-CPU、MEM-内存
	
	private List<RealTime> realTimeList = new ArrayList<RealTime>();
	
	private RealTime realTime = new RealTime();

	/**
	 * 跳转Action，里面传递了chart图标的数据
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("实时性能报表统计开始");
		try {
			realTime.setDeviceId(deviceId);
			if("VM".equals(deviceType)){
				deviceName = (String)ibatisDAO.getSingleRecord("getVmName" , realTime);
				if("CPU".equals(staType)){
					realTimeList = ibatisDAO.getData("getCpuUsedPerVm" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getCpuVm" , realTime);
				}else if("MEM".equals(staType)){
					realTimeList = ibatisDAO.getData("getMemUsedPerVm" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getMemVm" , realTime);
				}
			}else if("PM".equals(deviceType)){
				deviceName = (String)ibatisDAO.getSingleRecord("getPmName" , realTime);
				if("CPU".equals(staType)){
					realTimeList = ibatisDAO.getData("getCpuUsedPerPm" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getCpuPm" , realTime);
				}else if("MEM".equals(staType)){
					realTimeList = ibatisDAO.getData("getMemUsedPerPm" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getMemPm" , realTime);
				}
			}else if("MINIPM".equals(deviceType)){
				deviceName = (String)ibatisDAO.getSingleRecord("getMiniPmName" , realTime);
				if("CPU".equals(staType)){
					realTimeList = ibatisDAO.getData("getCpuUsedPerMiniPm" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getCpuMiniPm" , realTime);
				}else if("MEM".equals(staType)){
					realTimeList = ibatisDAO.getData("getMemUsedPerMiniPm" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getMemMiniPm" , realTime);
				}
			}else{
				deviceName = (String)ibatisDAO.getSingleRecord("getMiniPmParName" , realTime);
				if("CPU".equals(staType)){
					realTimeList = ibatisDAO.getData("getCpuUsedPerMiniPmPar" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getCpuMiniPmPar" , realTime);
				}else if("MEM".equals(staType)){
					realTimeList = ibatisDAO.getData("getMemUsedPerMiniPmPar" , realTime);
					realTime = (RealTime)ibatisDAO.getSingleRecord("getMemMiniPmPar" , realTime);
				}
			}
			RealTime realTimeTemp = (RealTime)ibatisDAO.getSingleRecord("getMinMaxTime" , realTime);
			if(realTime != null){
				realTime.setMinyear(realTimeTemp.getMinyear());
				realTime.setMinmonth(realTimeTemp.getMinmonth());
				realTime.setMinday(realTimeTemp.getMinday());
				realTime.setMinhour(realTimeTemp.getMinhour());
				realTime.setMaxyear(realTimeTemp.getMaxyear());
				realTime.setMaxmonth(realTimeTemp.getMaxmonth());
				realTime.setMaxday(realTimeTemp.getMaxday());
				realTime.setMaxhour(realTimeTemp.getMaxhour());
			}else{
				realTime = realTimeTemp;
			}
			if(realTimeList.size()==0){
				realTimeList = ibatisDAO.getData("getAllPoint" , realTime);
			}
		}catch(SQLException e) {
			logger.error("实时性能报表，操作数据库异常" , e);
			e.printStackTrace();
		}
		logger.info("实时性能报表统计完成");
		return "success";
	}

	/**
	 * @return the staType
	 */
	public String getStaType() {
		return staType;
	}

	/**
	 * @param staType the staType to set
	 */
	public void setStaType(String staType) {
		this.staType = staType;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the realTime
	 */
	public RealTime getRealTime() {
		return realTime;
	}

	/**
	 * @param realTime the realTime to set
	 */
	public void setRealTime(RealTime realTime) {
		this.realTime = realTime;
	}

	/**
	 * @return the realTimeList
	 */
	public List<RealTime> getRealTimeList() {
		return realTimeList;
	}

	/**
	 * @param realTimeList the realTimeList to set
	 */
	public void setRealTimeList(List<RealTime> realTimeList) {
		this.realTimeList = realTimeList;
	}

}
