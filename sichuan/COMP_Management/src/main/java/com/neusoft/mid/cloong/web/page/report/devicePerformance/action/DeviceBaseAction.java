package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.monthPerformance.info.MonthPerformanceInfo;

/**
 * 设备性能统计
 * 
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version Revision 1.0 2015年11月6日 下午6:55:29
 */
public class DeviceBaseAction extends PageAction {

	private static final long serialVersionUID = 1L;

	/** 设备性能统计信息 */
	protected DevicePerformanceInfo devicePerformanceInfo = new DevicePerformanceInfo();
	
	/** 资源池名称list -无用*/
	private List<MonthPerformanceInfo> resPoolList = new ArrayList<MonthPerformanceInfo>();

	/** 分区名称list -无用*/
	private List<MonthPerformanceInfo> poolPartList = new ArrayList<MonthPerformanceInfo>();
    
	/**
	 * 设置页面标题
	 */
	protected void setPageTitle () {
		if ("0".equals(devicePerformanceInfo.getDeviceType())) {
		    devicePerformanceInfo.setDeviceTypeName("物理机");
		} else if ("1".equals(devicePerformanceInfo.getDeviceType())) {
		    devicePerformanceInfo.setDeviceTypeName("虚拟机");
		} else if ("2".equals(devicePerformanceInfo.getDeviceType())) {
		    devicePerformanceInfo.setDeviceTypeName("防火墙");
		} else if ("3".equals(devicePerformanceInfo.getDeviceType())) {
		    devicePerformanceInfo.setDeviceTypeName("阵列");
		} else if ("4".equals(devicePerformanceInfo.getDeviceType()) || "41".equals(devicePerformanceInfo.getDeviceType())) {
            devicePerformanceInfo.setDeviceTypeName("交换机");
        } else if ("5".equals(devicePerformanceInfo.getDeviceType()) || "51".equals(devicePerformanceInfo.getDeviceType())) {
            devicePerformanceInfo.setDeviceTypeName("路由器");
        }
	}
	
	/**
	 * 设置查询日期（格式：yyyyMMdd）
	 */
	protected void formatDateForSql () {
		String startDate = devicePerformanceInfo.getStartDate();
		String endDate = devicePerformanceInfo.getEndDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if ((null == startDate || "".equals(startDate))
				&& (null == endDate || "".equals(endDate))) { // 页面初始化默认展示最近30天的数据
			/* 取得前31天 */
			Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -31);
	        devicePerformanceInfo.setStartDate(sdf.format(cal.getTime()));
	        /* 取得前1天 */
	        cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1);
	        devicePerformanceInfo.setEndDate(sdf.format(cal.getTime()));
		} else {
			if (null != startDate && !"".equals(startDate)) {
			    devicePerformanceInfo.setStartDate(startDate.replaceAll("-", ""));
			}
			if (null != endDate && !"".equals(endDate)) {
			    devicePerformanceInfo.setEndDate(endDate.replace("-", ""));
			}
		}
	}
	
	/**
	 * 设置查询日期（格式：yyyy-MM-dd）
	 */
	protected void formatDateForDisplay () {
		String startDate = devicePerformanceInfo.getStartDate();
		String endDate = devicePerformanceInfo.getEndDate();

		if (null != startDate && !"".equals(startDate)) {
		    devicePerformanceInfo.setStartDate(this.formatDate(startDate));
		}
		if (null != endDate && !"".equals(endDate)) {
		    devicePerformanceInfo.setEndDate(this.formatDate(endDate));
		}
	}
	
	/**
	 * 日期格式化（格式：yyyyMMdd）
	 */
	private String formatDate (String date) {
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6);
		date = year + "-" + month + "-" + day;
		return date;
	}

    /**
     * @return the devicePerformanceInfo
     */
    public DevicePerformanceInfo getDevicePerformanceInfo() {
        return devicePerformanceInfo;
    }

    /**
     * @param devicePerformanceInfo the devicePerformanceInfo to set
     */
    public void setDevicePerformanceInfo(DevicePerformanceInfo devicePerformanceInfo) {
        this.devicePerformanceInfo = devicePerformanceInfo;
    }

	/**
	 * @return the resPoolList
	 */
	public List<MonthPerformanceInfo> getResPoolList() {
		return resPoolList;
	}

	/**
	 * @param resPoolList the resPoolList to set
	 */
	public void setResPoolList(List<MonthPerformanceInfo> resPoolList) {
		this.resPoolList = resPoolList;
	}

	/**
	 * @return the poolPartList
	 */
	public List<MonthPerformanceInfo> getPoolPartList() {
		return poolPartList;
	}

	/**
	 * @param poolPartList the poolPartList to set
	 */
	public void setPoolPartList(List<MonthPerformanceInfo> poolPartList) {
		this.poolPartList = poolPartList;
	}

}
