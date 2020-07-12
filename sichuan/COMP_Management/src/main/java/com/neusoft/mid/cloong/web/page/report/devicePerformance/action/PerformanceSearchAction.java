package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;

/**
 * 设备性能统计-性能统计查询
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version Revision 1.0 2015年11月10日 下午3:55:29
 */
public class PerformanceSearchAction extends DeviceBaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(PerformanceSearchAction.class);

	/** ajax传递的用来异步分页的数据 */
	private List<DevicePerformanceInfo> performanceInfolist = new ArrayList<DevicePerformanceInfo>();

	/** 设备下拉列表 */
	private List<DevicePerformanceInfo> deviceList = null;

	/** 业务下拉列表  - 没用*/
	private List<HistoryPerformanceInfo> appList = new ArrayList<HistoryPerformanceInfo>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("设备性能统计-性能统计查询，开始");

		/* 设置页面标题 */
		setPageTitle();

		try {
			String sqlId = "";
			if ("0".equals(devicePerformanceInfo.getDeviceType())) {
				sqlId = "getPmList"; //物理机
			} else if ("1".equals(devicePerformanceInfo.getDeviceType())) {
				sqlId = "getVmList"; //虚拟机
			} else if ("2".equals(devicePerformanceInfo.getDeviceType())) {
				sqlId = "getFwList"; //防火墙
			} else if ("3".equals(devicePerformanceInfo.getDeviceType())) {
				sqlId = "getRaidList"; //阵列
			} else if ("4".equals(devicePerformanceInfo.getDeviceType())) {
				sqlId = "getSwList"; //交换机
	        } else if ("5".equals(devicePerformanceInfo.getDeviceType())) {
	        	sqlId = "getRtList"; //路由器
	        } else if ("41".equals(devicePerformanceInfo.getDeviceType())) {
				sqlId = "getSwIfList"; //交换机端口
	        } else if ("51".equals(devicePerformanceInfo.getDeviceType())) {
	        	sqlId = "getRtIfList"; //路由器端口
	        }
			deviceList = ibatisDAO.getData(sqlId, null);
			String deviceId = "";
			String deviceName = "";
			if(deviceList.size()>0){
				deviceId = (deviceList.get(0)).getDeviceId();
				deviceName = (deviceList.get(0)).getDeviceName();
			}
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	        String day = df.format(new Date());
	        String startDate = day+"000000";
	        String endDate = day+"235959";
			devicePerformanceInfo.setDeviceId(deviceId);
			devicePerformanceInfo.setDeviceName(deviceName);
			devicePerformanceInfo.setStartDate(startDate);
			devicePerformanceInfo.setEndDate(endDate);
		} catch (SQLException e) {
			logger.error("设备性能统计-性能统计查询，操作数据库异常", e);
			e.printStackTrace();
		}
		logger.info("设备性能统计-性能统计查询，结束");
		return "success";
	}


	/**
	 * 通过Ajax获取个数表格
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPerformanceByAjax() {
		/* 查询日期处理 */
		String listSqlId = "";
		String countSqlId = "";
		if ("0".equals(devicePerformanceInfo.getDeviceType())) {//物理机
			listSqlId = "getPmPerformance";
			countSqlId = "getPmPerformanceCount";
		} else if ("1".equals(devicePerformanceInfo.getDeviceType())) {//虚拟机
			listSqlId = "getVmPerformance"; 
			countSqlId = "getVmPerformanceCount";
		} else if ("2".equals(devicePerformanceInfo.getDeviceType())) {//防火墙
			listSqlId = "getFwPerformance"; 
			countSqlId = "getFwPerformanceCount";
		} else if ("3".equals(devicePerformanceInfo.getDeviceType())) {//阵列
			listSqlId = "getRaidPerformance";
			countSqlId = "getRaidPerformanceCount";
		} else if ("4".equals(devicePerformanceInfo.getDeviceType())) {//交换机
			listSqlId = "getSwPerformance"; 
			countSqlId = "getSwPerformanceCount";
        } else if ("5".equals(devicePerformanceInfo.getDeviceType())) {//路由器
        	listSqlId = "getRtPerformance"; 
        	countSqlId = "getRtPerformanceCount";
        } else if ("41".equals(devicePerformanceInfo.getDeviceType())) {//交换机端口
			listSqlId = "getSwIfPerformance"; 
			countSqlId = "getSwIfPerformanceCount";
        } else if ("51".equals(devicePerformanceInfo.getDeviceType())) {//路由器端口
        	listSqlId = "getRtIfPerformance"; 
        	countSqlId = "getRtIfPerformanceCount";
        }
		performanceInfolist = getPage(countSqlId, listSqlId, devicePerformanceInfo,
				PageTransModel.ASYNC, PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		return "success";
	}


	/**
	 * @return the performanceInfolist
	 */
	public List<DevicePerformanceInfo> getPerformanceInfolist() {
		return performanceInfolist;
	}


	/**
	 * @param performanceInfolist the performanceInfolist to set
	 */
	public void setPerformanceInfolist(
			List<DevicePerformanceInfo> performanceInfolist) {
		this.performanceInfolist = performanceInfolist;
	}


	/**
	 * @return the deviceList
	 */
	public List<DevicePerformanceInfo> getDeviceList() {
		return deviceList;
	}


	/**
	 * @param deviceList the deviceList to set
	 */
	public void setDeviceList(List<DevicePerformanceInfo> deviceList) {
		this.deviceList = deviceList;
	}


	/**
	 * @return the appList
	 */
	public List<HistoryPerformanceInfo> getAppList() {
		return appList;
	}


	/**
	 * @param appList the appList to set
	 */
	public void setAppList(List<HistoryPerformanceInfo> appList) {
		this.appList = appList;
	}
}
