package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DeviceRealTimeInfo;

/**
 * 设备性能统计-实时性能统计
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version Revision 1.0 2015年11月6日 下午6:55:29
 */
public class DevicePerformanceRTDetailAction extends DeviceBaseAction {

    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(DevicePerformanceRTDetailAction.class);

    /** 业务下拉列表 - 没用 */
    private List<DevicePerformanceInfo> appList = new ArrayList<DevicePerformanceInfo>();

    /** ajax请求传来的设备类型 */
    private String deviceType;

    /** ajax请求传来的pmId */
    private String deviceId;

    /** 设备实时性能统计信息 */
    protected DeviceRealTimeInfo realTime = new DeviceRealTimeInfo();

    /* 取得图表数据 */
    List<DeviceRealTimeInfo> realTimeList = new ArrayList<DeviceRealTimeInfo>();

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info("设备实时性能统计-CPU、内存使用率统计，开始");

        /* 设置页面标题 */
        setPageTitle();

        /* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
        formatDateForSql();

        try {
            devicePerformanceInfo.setDeviceType(deviceType);
            devicePerformanceInfo.setDeviceId(deviceId);
            realTime.setDeviceId(deviceId);
            if ("0".equals(devicePerformanceInfo.getDeviceType())) { // 统计物理机CPU\内存实时性能
                realTimeList = ibatisDAO.getData("getPmDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getCpuMemPm", realTime);
            } else if ("1".equals(devicePerformanceInfo.getDeviceType())) { // 统计虚拟机CPU\内存实时性能
                realTimeList = ibatisDAO.getData("getVmDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getCpuMemVm", realTime);
            } else if ("2".equals(devicePerformanceInfo.getDeviceType())) { // 统计防火墙CPU\内存实时性能
                realTimeList = ibatisDAO.getData("getFwDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getCpuMemFw", realTime);
            } else if ("3".equals(devicePerformanceInfo.getDeviceType())) { // 统计阵列CPU\内存实时性能
                realTimeList = ibatisDAO.getData("getRaidDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getReadWriteBytesRaid",
                        realTime);
            } else if ("4".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机CPU\内存实时性能
                realTimeList = ibatisDAO.getData("getSwitchDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getCpuMemSwitch",
                        realTime);
            } else if ("5".equals(devicePerformanceInfo.getDeviceType())) { // 统计路由器CPU\内存实时性能
                realTimeList = ibatisDAO.getData("getRouterDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getCpuMemRouter",
                        realTime);
            } else if ("41".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机端口包总数\丢包数实时性能
                realTimeList = ibatisDAO.getData("getSwIfDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getPerformanceSwIf",
                        realTime);
            } else if ("51".equals(devicePerformanceInfo.getDeviceType())) { // 统计路由器端口包总数\丢包数实时性能
                realTimeList = ibatisDAO.getData("getRtIfDevicePerformanceRTDetail", realTime);
                realTime = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord("getPerformanceRtIf",
                        realTime);
            }

            DeviceRealTimeInfo realTimeTemp = (DeviceRealTimeInfo) ibatisDAO.getSingleRecord(
                    "getDeviceMinMaxTime", realTime);
            if (realTime != null) {
                realTime.setMinyear(realTimeTemp.getMinyear());
                realTime.setMinmonth(realTimeTemp.getMinmonth());
                realTime.setMinday(realTimeTemp.getMinday());
                realTime.setMinhour(realTimeTemp.getMinhour());
                realTime.setMaxyear(realTimeTemp.getMaxyear());
                realTime.setMaxmonth(realTimeTemp.getMaxmonth());
                realTime.setMaxday(realTimeTemp.getMaxday());
                realTime.setMaxhour(realTimeTemp.getMaxhour());
            } else {
                realTime = realTimeTemp;
            }
            if (realTimeList.size() == 0) {
                realTimeList = ibatisDAO.getData("getDeviceAllPoint", realTime);
            }
        } catch (SQLException e) {
            logger.error("设备性能统计-CPU、内存使用率统计，操作数据库异常", e);
            e.printStackTrace();
        }

        logger.info("设备实时性能统计-CPU、内存使用率统计，结束");

        return "success";
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceRealTimeInfo getRealTime() {
        return realTime;
    }

    public void setRealTime(DeviceRealTimeInfo realTime) {
        this.realTime = realTime;
    }

    public List<DeviceRealTimeInfo> getRealTimeList() {
        return realTimeList;
    }

    public void setRealTimeList(List<DeviceRealTimeInfo> realTimeList) {
        this.realTimeList = realTimeList;
    }

    /**
     * @return the appList
     */
    public List<DevicePerformanceInfo> getAppList() {
        return appList;
    }

    /**
     * @param appList the appList to set
     */
    public void setAppList(List<DevicePerformanceInfo> appList) {
        this.appList = appList;
    }

}
