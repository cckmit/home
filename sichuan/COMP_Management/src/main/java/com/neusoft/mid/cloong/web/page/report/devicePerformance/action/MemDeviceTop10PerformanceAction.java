package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;

/**
 * 设备性能统计-内存Top10统计
 * 
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version Revision 1.0 2015年11月6日 下午6:55:29
 */
public class MemDeviceTop10PerformanceAction extends DeviceBaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MemDeviceTop10PerformanceAction.class);

	/** 内存Top10统计图表的横纵坐标数值 */
	private List<String> chartData = new ArrayList<String>();

	/** ajax传递的用来异步分页的数据 */
	private List<DevicePerformanceInfo> devicePerformanceInfolist = new ArrayList<DevicePerformanceInfo>();
	
	/** 业务下拉列表  - 没用*/
	private List<HistoryPerformanceInfo> appList = new ArrayList<HistoryPerformanceInfo>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("设备性能统计-内存使用率统计，开始");

		/* 设置页面标题 */
		setPageTitle();

		/* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
		formatDateForSql();

		/* 取得图表数据 */
		List<DevicePerformanceInfo> chartDatalist = null; // 图表的横纵坐标数值List
		try {
		    if ("0".equals(devicePerformanceInfo.getDeviceType())) { // 统计物理机内存top10
				chartDatalist = ibatisDAO.getData("getPmMemDeviceTop10Performance", devicePerformanceInfo);
			} else if ("1".equals(devicePerformanceInfo.getDeviceType())) { // 统计虚拟机内存top10
			    chartDatalist = ibatisDAO.getData("getVmMemDeviceTop10Performance", devicePerformanceInfo);
			} else if ("2".equals(devicePerformanceInfo.getDeviceType())) { // 统计防火墙内存top10
                chartDatalist = ibatisDAO.getData("getFWMemDeviceTop10Performance", devicePerformanceInfo);
            } else if ("3".equals(devicePerformanceInfo.getDeviceType())) { // 统计阵列内存top10
                chartDatalist = ibatisDAO.getData("getRaidMemDeviceTop10Performance", devicePerformanceInfo);
            } else if ("4".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机内存top10
				chartDatalist = ibatisDAO.getData("getSwitchMemDeviceTop10Performance", devicePerformanceInfo);
			} else if ("5".equals(devicePerformanceInfo.getDeviceType())) { // 统计路由器内存top10
				chartDatalist = ibatisDAO.getData("getRouterMemDeviceTop10Performance", devicePerformanceInfo);
			} else if ("41".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机端口丢包数top10
                chartDatalist = ibatisDAO.getData("getSwIfDiscardsDeviceTop10Performance", devicePerformanceInfo);
            } else if ("51".equals(devicePerformanceInfo.getDeviceType())) { // 统计路由器端口丢包数top10
                chartDatalist = ibatisDAO.getData("getRtIfDiscardsDeviceTop10Performance", devicePerformanceInfo);
            }
			
		} catch (SQLException e) {
			logger.error("设备性能统计-内存使用率统计，操作数据库异常", e);
			e.printStackTrace();
		}

		/* 设置内存Top10使用率统计json */
		if (null != chartDatalist) {
			chartData = this.JsonPackaging(chartDatalist, devicePerformanceInfo.getDeviceType());
		}

		/* 设置查询日期，用于页面展示（格式：yyyy-MM-dd） */
		formatDateForDisplay();
		
		logger.info("设备性能统计-内存使用率统计，结束");

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
			    obj.put("y", i.getHstDiskWriteBytes());
			} else if("41".equals(deviceType) || "51".equals(deviceType)){// 交换机、路由器端口
                obj.put("y", i.getDiscards());
            } else {
			    obj.put("y", i.getMemUsedPer());
			}
			obj.put("设备名字", i.getDeviceName());
			top10CpuData.add(obj);

		}

		JSONObject top10CpuObj = new JSONObject();
		if ("3".equals(deviceType)) { // 阵列
            top10CpuObj.put("name", "阵列写吞吐量平均值");
        } else if("41".equals(deviceType) || "51".equals(deviceType)){ // 交换机、路由器端口
            top10CpuObj.put("name", "丢包数平均值");
        } else {
            top10CpuObj.put("name", devicePerformanceInfo.getDeviceTypeName() + "内存平均使用率");
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
