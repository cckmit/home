package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;

/**
 * 设备性能统计-CPUTop10统计
 * 
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version Revision 1.0 2015年11月6日 下午6:55:29
 */
public class CpuDeviceTop10PerformanceAction extends DeviceBaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CpuDeviceTop10PerformanceAction.class);

	/** CPUTop10统计图表的横纵坐标数值 */
	private List<String> chartData = new ArrayList<String>();

	/** ajax传递的用来异步分页的数据 */
	private List<DevicePerformanceInfo> devicePerformanceInfolist = new ArrayList<DevicePerformanceInfo>();
	
	/** 业务下拉列表  - 没用*/
	private List<HistoryPerformanceInfo> appList = new ArrayList<HistoryPerformanceInfo>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("设备性能统计-CPU使用率统计，开始");

		/* 设置页面标题 */
		setPageTitle();

		/* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
		formatDateForSql();

		/* 取得图表数据 */
		List<DevicePerformanceInfo> chartDatalist = null; // 图表的横纵坐标数值List
		try {
		    if ("0".equals(devicePerformanceInfo.getDeviceType())) { // 统计物理机CPUtop10
				chartDatalist = ibatisDAO.getData("getPmCpuDeviceTop10Performance", devicePerformanceInfo);
			} else if ("1".equals(devicePerformanceInfo.getDeviceType())) { // 统计虚拟机CPUtop10
			    chartDatalist = ibatisDAO.getData("getVmCpuDeviceTop10Performance", devicePerformanceInfo);
			} else if ("2".equals(devicePerformanceInfo.getDeviceType())) { // 统计防火墙CPUtop10
                chartDatalist = ibatisDAO.getData("getFWCpuDeviceTop10Performance", devicePerformanceInfo);
            } else if ("3".equals(devicePerformanceInfo.getDeviceType())) { // 统计阵列CPUtop10
                chartDatalist = ibatisDAO.getData("getRaidCpuDeviceTop10Performance", devicePerformanceInfo);
            } else if ("4".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机CPUtop10
			    chartDatalist = ibatisDAO.getData("getSwitchCpuDeviceTop10Performance", devicePerformanceInfo);
			} else if ("5".equals(devicePerformanceInfo.getDeviceType())) { // 统计路由器CPUtop10
			    chartDatalist = ibatisDAO.getData("getRouterCpuDeviceTop10Performance", devicePerformanceInfo);
			} else if ("41".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机端口包总数top10
                chartDatalist = ibatisDAO.getData("getSwIfPktsDeviceTop10Performance", devicePerformanceInfo);
            } else if ("51".equals(devicePerformanceInfo.getDeviceType())) { // 统计路由器端口包总数top10
                chartDatalist = ibatisDAO.getData("getRtIfPktsDeviceTop10Performance", devicePerformanceInfo);
            }
			
		} catch (SQLException e) {
			logger.error("设备性能统计-CPU使用率统计，操作数据库异常", e);
			e.printStackTrace();
		}

		/* 设置CPUTop10使用率统计json */
		if (null != chartDatalist) {
			chartData = this.JsonPackaging(chartDatalist, devicePerformanceInfo.getDeviceType());
		}

		/* 设置查询日期，用于页面展示（格式：yyyy-MM-dd） */
		formatDateForDisplay();
		
		logger.info("设备性能统计-CPU使用率统计，结束");

		return "success";
	}

	/**
	 * 将查出的数据封装为json格式
	 * 
	 * @param 查出的数据list
	 * @return list，第一个是x坐标的json，第二个是数值的json
	 */
	private List<String> JsonPackaging(List<DevicePerformanceInfo> list, String deviceType) {
		JSONArray xAxisArr = new JSONArray(); // x轴坐标
		JSONArray top10CpuData = new JSONArray(); // 平均使用率

		JSONObject obj = null;
		for (DevicePerformanceInfo i : list) {
			/* 横坐标 */
			xAxisArr.add(i.getDeviceName());

			/* 平均使用率 */
			obj = new JSONObject();
			if ("3".equals(deviceType)) {// 阵列
			   obj.put("y", i.getHstDiskReadBytes());
			} else if("41".equals(deviceType) || "51".equals(deviceType)){// 交换机、路由器端口
			    obj.put("y", i.getPkts());
			} else {
			    obj.put("y", i.getCpuProcessorUtilization());
			}
			obj.put("设备名字", i.getDeviceName());
			top10CpuData.add(obj);

		}

		JSONObject top10CpuObj = new JSONObject();
		if ("3".equals(deviceType)) { // 阵列
		    top10CpuObj.put("name", "阵列读吞吐量平均值");
		} else if("41".equals(deviceType) || "51".equals(deviceType)){ // 交换机、路由器端口
		    top10CpuObj.put("name", "包总数平均值");
		} else {
		    top10CpuObj.put("name", devicePerformanceInfo.getDeviceTypeName()+ "cpu平均使用率");
		}
		
		top10CpuObj.put("data", top10CpuData);
		top10CpuObj.put("color", "#1E90FF");

		JSONArray seriesArr = new JSONArray();
		seriesArr.add(top10CpuObj);

		List<String> result = new ArrayList<String>();
		result.add(JsonUtil.jsonTrans(xAxisArr.toString()));
		result.add(JsonUtil.jsonTrans(seriesArr.toString()));

		return result;
	}
	
	/**
	 * 通过Ajax获取个数表格
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCpuDevicePerformanceByAjax() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		devicePerformanceInfo.setEndDate(sdf.format(cal.getTime()));

		if (StringUtils.isNotEmpty(devicePerformanceInfo.getTimeType()) &&"1".equals(devicePerformanceInfo.getTimeType())) {
			// 粒度是1小时,查询1小时内的数据
			cal.add(Calendar.HOUR, -1);
			devicePerformanceInfo.setStartDate(sdf.format(cal.getTime()));

		} else {
			// 粒度是24小时
			cal.add(Calendar.HOUR, -24);
			devicePerformanceInfo.setStartDate(sdf.format(cal.getTime()));
		}

		if (null != devicePerformanceInfo.getDeviceType()
				&& "0".equals(devicePerformanceInfo.getDeviceType())) {
			// 物理机性能数据列表
			devicePerformanceInfolist = getPage("getSrvDevicePerformanceCount",
					"getSrvDevicePerformance", devicePerformanceInfo,
					PageTransModel.ASYNC,
					PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if (null != devicePerformanceInfo.getDeviceType()
				&& "1".equals(devicePerformanceInfo.getDeviceType())) {
			// 虚拟机性能数据列表
			devicePerformanceInfolist = getPage("getVMDevicePerformanceCount",
					"getVMDevicePerformance", devicePerformanceInfo,
					PageTransModel.ASYNC,
					PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if (null != devicePerformanceInfo.getDeviceType()
				&& "2".equals(devicePerformanceInfo.getDeviceType())) {
			// 防火墙性能数据列表
			devicePerformanceInfolist = getPage("getFWDevicePerformanceCount",
					"getFWDevicePerformance", devicePerformanceInfo,
					PageTransModel.ASYNC,
					PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if (null != devicePerformanceInfo.getDeviceType()
				&& "3".equals(devicePerformanceInfo.getDeviceType())) {
			// 阵列性能数据列表
			devicePerformanceInfolist = getPage("getRAIDDevicePerformanceCount",
					"getRAIDDevicePerformance", devicePerformanceInfo,
					PageTransModel.ASYNC,
					PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if (null != devicePerformanceInfo.getDeviceType()
				&& "4".equals(devicePerformanceInfo.getDeviceType())) {
			// 交换机设备性能数据列表
			devicePerformanceInfolist = getPage("getSwitchDevicePerformanceCount",
					"getSwitchDevicePerformance", devicePerformanceInfo,
					PageTransModel.ASYNC,
					PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		} else if (null != devicePerformanceInfo.getDeviceType()
				&& "5".equals(devicePerformanceInfo.getDeviceType())) {
			// 路由器设备性能数据列表
			devicePerformanceInfolist = getPage("getRouterDevicePerformanceCount",
					"getRouterDevicePerformance", devicePerformanceInfo,
					PageTransModel.ASYNC,
					PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		} else if (null != devicePerformanceInfo.getDeviceType()
                && "41".equals(devicePerformanceInfo.getDeviceType())) {
            // 交换机端口设备性能数据列表
            devicePerformanceInfolist = getPage("getSwIfDevicePerformanceCount",
                    "getSwIfDevicePerformance", devicePerformanceInfo,
                    PageTransModel.ASYNC,
                    PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
        } else if (null != devicePerformanceInfo.getDeviceType()
                && "51".equals(devicePerformanceInfo.getDeviceType())) {
            // 路由器端口设备性能数据列表
            devicePerformanceInfolist = getPage("getRtIfDevicePerformanceCount",
                    "getRtIfDevicePerformance", devicePerformanceInfo,
                    PageTransModel.ASYNC,
                    PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
        }

		return "success";
	}

	public List<String> getChartData() {
		return chartData;
	}

	public void setChartData(List<String> chartData) {
		this.chartData = chartData;
	}

    /**
     * @return the devicePerformanceInfolist
     */
    public List<DevicePerformanceInfo> getDevicePerformanceInfolist() {
        return devicePerformanceInfolist;
    }

    /**
     * @param devicePerformanceInfolist the devicePerformanceInfolist to set
     */
    public void setDevicePerformanceInfolist(List<DevicePerformanceInfo> devicePerformanceInfolist) {
        this.devicePerformanceInfolist = devicePerformanceInfolist;
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
