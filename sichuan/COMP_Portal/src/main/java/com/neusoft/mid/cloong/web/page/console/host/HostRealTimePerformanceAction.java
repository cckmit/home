package com.neusoft.mid.cloong.web.page.console.host;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.RealTimePerformance;

/**
 * 控制台->资源视图/业务视图->设备详情->运行监控
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月11日 上午9:56:59
 */
public class HostRealTimePerformanceAction extends BaseAction {
    private static final long serialVersionUID = -5024421508278442852L;

    private static Logger logger = Logger.getLogger(HostRealTimePerformanceAction.class);

    private String deviceId;// 设备Id

    private String deviceName;// 设备名称

    private String deviceType;// 设备类型：VM-虚拟机、PM-物理机

    private String staType;// 统计类型：CPU-CPU、MEM-内存

    // 设备过去24小时历史性能指标数据集
    private List<RealTimePerformance> historyPerformance = new ArrayList<RealTimePerformance>();

    // 设备当前最新的指标数据
    private RealTimePerformance currentPerformance = new RealTimePerformance();

    /**
     * 跳转Action，里面传递了chart图标的数据
     */
    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info("实时性能报表统计开始");
        try {
            currentPerformance.setDeviceId(deviceId);
            if ("VM".equals(deviceType)) {
                loadVMPerformanceInfo();
            } else if ("PM".equals(deviceType)) {
                loadPMPerformanceInfo();
            } else if ("MINIPM".equals(deviceType)) {
                loadMiniPMPerformanceInfo();
            } else {
                loadMiniPMPartPerformanceInfo();
            }
            setPerformanceChartAxis();
            if (historyPerformance.size() == 0) {
                historyPerformance = ibatisDAO.getData("getAllPoint", currentPerformance);
            }
        } catch (SQLException e) {
            logger.error("实时性能报表，操作数据库异常", e);
            e.printStackTrace();
        }
        logger.info("实时性能报表统计完成");
        return "success";
    }

    /**
     * 设置性能图标的坐标值
     * @author fengj<br>
     *         2015年3月11日 上午11:16:11
     * @throws SQLException 数据库操作异常
     */
    void setPerformanceChartAxis() throws SQLException {
        RealTimePerformance realTimeTemp = (RealTimePerformance) ibatisDAO.getSingleRecord("getMinMaxTime",
                currentPerformance);
        correctTimeline(realTimeTemp);   //  修正跨月时，时间轴问题     
        if (currentPerformance != null) {
            // 如果加载到了性能数据,则为对象设置坐标信息
            currentPerformance.setMinyear(realTimeTemp.getMinyear());
            currentPerformance.setMinmonth(realTimeTemp.getMinmonth());
            currentPerformance.setMinday(realTimeTemp.getMinday());
            currentPerformance.setMinhour(realTimeTemp.getMinhour());
            currentPerformance.setMaxyear(realTimeTemp.getMaxyear());
            currentPerformance.setMaxmonth(realTimeTemp.getMaxmonth());
            currentPerformance.setMaxday(realTimeTemp.getMaxday());
            currentPerformance.setMaxhour(realTimeTemp.getMaxhour());
        } else {
            // 如果没有加载到性能数据,则currentPerformance赋值为坐标信息的bean
            currentPerformance = realTimeTemp;
        }
    }
                
    /**
     * 跨月份时间轴异常修正
     * @author lanzhb 2019.03.01
     */
    public RealTimePerformance correctTimeline (RealTimePerformance realTimeTemp){
//    跨月份的时间轴问题：2至3月份
      if("02".equals(realTimeTemp.getMinmonth()) && "28".equals(realTimeTemp.getMinday()) && "03".equals(realTimeTemp.getMaxmonth())){
      	realTimeTemp.setMinmonth("03");
      	realTimeTemp.setMinday("01");
      	realTimeTemp.setMinhour("00");       	
      }
//    跨月份的时间轴问题：4至5月份
      if("04".equals(realTimeTemp.getMinmonth()) && "30".equals(realTimeTemp.getMinday()) && "05".equals(realTimeTemp.getMaxmonth())){
    	realTimeTemp.setMinmonth("05");
    	realTimeTemp.setMinday("01");
    	realTimeTemp.setMinhour("00");       	
    }
//    跨月份的时间轴问题：6至7月份
      if("06".equals(realTimeTemp.getMinmonth()) && "30".equals(realTimeTemp.getMinday()) && "07".equals(realTimeTemp.getMaxmonth())){
  	    realTimeTemp.setMinmonth("07");
     	realTimeTemp.setMinday("01");
  	    realTimeTemp.setMinhour("00");       	
  }
//    跨月份的时间轴问题：9至10月份
      if("09".equals(realTimeTemp.getMinmonth()) && "30".equals(realTimeTemp.getMinday()) && "10".equals(realTimeTemp.getMaxmonth())){
  	    realTimeTemp.setMinmonth("10");
     	realTimeTemp.setMinday("01");
  	    realTimeTemp.setMinhour("00");       	
  }
//    跨月份的时间轴问题：11至12月份
      if("11".equals(realTimeTemp.getMinmonth()) && "30".equals(realTimeTemp.getMinday()) && "12".equals(realTimeTemp.getMaxmonth())){
  	    realTimeTemp.setMinmonth("12");
     	realTimeTemp.setMinday("01");
  	    realTimeTemp.setMinhour("00");       	
  }	
    	return realTimeTemp ;
    }       

    /**
     * 加载小型机分区性能数据
     * @author fengj<br>
     *         2015年3月11日 上午10:18:07
     * @throws SQLException 数据库异常
     */
    void loadMiniPMPartPerformanceInfo() throws SQLException {
        deviceName = (String) ibatisDAO.getSingleRecord("getMiniPmParName", currentPerformance);
        if ("CPU".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getCpuUsedPerMiniPmPar", currentPerformance);
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getCpuMiniPmPar", currentPerformance);
        } else if ("MEM".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getMemUsedPerMiniPmPar", currentPerformance);
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getMemMiniPmPar", currentPerformance);
        }
    }

    /**
     * 加载小型机性能数据
     * @author fengj<br>
     *         2015年3月11日 上午10:18:07
     * @throws SQLException 数据库异常
     */
    void loadMiniPMPerformanceInfo() throws SQLException {
        deviceName = (String) ibatisDAO.getSingleRecord("getMiniPmName", currentPerformance);
        if ("CPU".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getCpuUsedPerMiniPm", currentPerformance);
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getCpuMiniPm", currentPerformance);
        } else if ("MEM".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getMemUsedPerMiniPm", currentPerformance);
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getMemMiniPm", currentPerformance);
        }
    }

    /**
     * 加载物理机性能数据
     * @author fengj<br>
     *         2015年3月11日 上午10:18:07
     * @throws SQLException 数据库异常
     */
    void loadPMPerformanceInfo() throws SQLException {
        deviceName = (String) ibatisDAO.getSingleRecord("getPmName", currentPerformance);
        if ("CPU".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getCpuUsedPerPm", currentPerformance);
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getCpuPm", currentPerformance);
        } else if ("MEM".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getMemUsedPerPm", currentPerformance);
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getMemPm", currentPerformance);
        }
    }

    /**
     * 加载虚拟机性能数据
     * @author fengj<br>
     *         2015年3月11日 上午10:18:07
     * @throws SQLException 数据库异常
     */
    void loadVMPerformanceInfo() throws SQLException {
        deviceName = (String) ibatisDAO.getSingleRecord("getVmName", currentPerformance);
        if ("CPU".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getCpuUsedPerVm", currentPerformance);   
            historyPerformance = correctData(historyPerformance);  //  跨月修改数据
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getCpuVm", currentPerformance);                      
        } else if ("MEM".equals(staType)) {
            historyPerformance = ibatisDAO.getData("getMemUsedPerVm", currentPerformance);      
            historyPerformance = correctData(historyPerformance);//  跨月修改数据
            currentPerformance = (RealTimePerformance) ibatisDAO.getSingleRecord("getMemVm", currentPerformance); 
        }
    }
   
    /**
     * 跨月时，重构数据
     * @author lanzhb 2019.3.01
     */
    public List<RealTimePerformance> correctData(List<RealTimePerformance> historyPerformance){
    	List<RealTimePerformance> newhistoryPerformance = new ArrayList<RealTimePerformance>();
    	RealTimePerformance data1 = new RealTimePerformance();
    	RealTimePerformance data2 = new RealTimePerformance();
    	if(! historyPerformance.isEmpty()){
    		data1 = historyPerformance.get(0);
    		data2 = historyPerformance.get(historyPerformance.size()-1);
    		String  dataDay = data1.getDay();
    		int dayNum = Integer.parseInt(dataDay);
    		if(dayNum<31 && !"01".equals(dataDay) && "01".equals(data2.getDay())){
    	  		for(int j = 0, len = historyPerformance.size(); j < len; j++){
    	  			if("28".equals(historyPerformance.get(j).getDay()) || "29".equals(historyPerformance.get(j).getDay()) || "30".equals(historyPerformance.get(j).getDay())){
    	  				continue;
    	  			}else{
    	  				newhistoryPerformance.add(historyPerformance.get(j));
    	  			}
    	  		}
    	  		return newhistoryPerformance;
    		}
    	}
    	return historyPerformance;
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
    public RealTimePerformance getRealTime() {
        return currentPerformance;
    }

    /**
     * @param realTime the realTime to set
     */
    public void setRealTime(RealTimePerformance realTime) {
        this.currentPerformance = realTime;
    }

    /**
     * @return the realTimeList
     */
    public List<RealTimePerformance> getRealTimeList() {
        return historyPerformance;
    }

    /**
     * @param realTimeList the realTimeList to set
     */
    public void setRealTimeList(List<RealTimePerformance> realTimeList) {
        this.historyPerformance = realTimeList;
    }

}
