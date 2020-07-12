package com.neusoft.mid.cloong.web.page.user.order.lb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过IP段查询可用的IP
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-12 下午4:03:58
 */
public class VMOnloadPrivateIpAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(VMOnloadPrivateIpAction.class);

    /**
     * 逻辑vlanid 查询已经使用的IP
     */
    private String logicVlanId;

    /**
     * ip段ID
     */
    private String ipSegmentId;

    /**
     * IP下拉框选项
     */
    private List<IPInfo> ipInfos;

    /**
     * 返回结果路径,error,failure
     */
    private String resultRoute;
    

    public String execute() {

        if (logger.isDebugEnable()) {
            logger.debug("logicVlanId = " + logicVlanId);
            logger.debug("ipSegmentId = " + ipSegmentId);
        }

        try {
            Map<String, String> useIp = new HashMap<String, String>();

            // 查询正在使用的虚拟机，vlan使用的ip和网关（删除的虚拟机ip可以继续使用）
            List<NetInfo> netInfos = ibatisDAO.getData("getAllNet", logicVlanId);
            for (NetInfo netInfo : netInfos) {
                useIp.put(netInfo.getIp(), netInfo.getIp());
                useIp.put(netInfo.getGateway(), netInfo.getGateway());
            }

            // 查询正在修改的虚拟机，vlan使用的ip和网关
            List<NetInfo> netInfosForVmMod = ibatisDAO.getData("getAllNetForVmMod", logicVlanId);
            for (NetInfo netInfo : netInfosForVmMod) {
                useIp.put(netInfo.getIp(), netInfo.getIp());
                useIp.put(netInfo.getGateway(), netInfo.getGateway());
            }
            
            // 查询正在使用的负载均衡，vlan使用的ip
            List<NetInfo> LBnetInfos = ibatisDAO.getData("getALLnetforLB", logicVlanId);
            for (NetInfo netInfo : LBnetInfos) {
                useIp.put(netInfo.getIp(), netInfo.getIp());               
            }
            
            
            // 按照ip段ID 查询ip段的起止ip
            IpSegment ipSegment = (IpSegment) ibatisDAO.getSingleRecord("getIPsegmentByID",
                    ipSegmentId);
            if(null != ipSegment){            	
            	ipInfos = new ArrayList<IPInfo>();
            	List<String> ips = new ArrayList<String>();
//            	根据IP类型获取IP段IP地址集
            	if("0".equals(ipSegment.getIpType())){
               	 String startIp = ipSegment.getStartIp();
                 String endIp = ipSegment.getEndIp();                 
                 // 通过起止ip查询出该ip段内包含的所有ip
                  ips = IpSegmentUtil.getIpList(startIp, endIp);
            	}else{
            		int num = useIp.size()+100;
            		String ipSubnet = ipSegment.getIpSubnet();
            		 ips = IpSegmentUtil.getIpv6List(ipSubnet,num);
            	}
/*            	 String startIp = ipSegment.getStartIp();
                 String endIp = ipSegment.getEndIp();                 
                 // 通过起止ip查询出该ip段内包含的所有ip
                 ipInfos = new ArrayList<IPInfo>();
                 List<String> ips = IpSegmentUtil.getIpList(startIp, endIp);*/
                 if (ips != null && ips.size() > 0) {
                     for (String ip : ips) {
                         if (useIp.get(ip) != null) {
                             continue;
                         }
                         IPInfo ipInfo = new IPInfo();
                         ipInfo.setIp(ip);
                         ipInfos.add(ipInfo);
                     }
                 }
            }

        } catch (Exception e) {
            logger.error(VMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
                    getText("vmonload.ip.fail"), e);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();

    }

    /**
     * 获取logicVlanId字段数据
     * @return Returns the logicVlanId.
     */
    public String getLogicVlanId() {
        return logicVlanId;
    }

    /**
     * 设置logicVlanId字段数据
     * @param logicVlanId The logicVlanId to set.
     */
    public void setLogicVlanId(String logicVlanId) {
        this.logicVlanId = logicVlanId;
    }

    /**
     * 获取ipSegmentId字段数据
     * @return Returns the ipSegmentId.
     */
    public String getIpSegmentId() {
        return ipSegmentId;
    }

    /**
     * 设置ipSegmentId字段数据
     * @param ipSegmentId The ipSegmentId to set.
     */
    public void setIpSegmentId(String ipSegmentId) {
        this.ipSegmentId = ipSegmentId;
    }

    /**
     * 获取ipInfos字段数据
     * @return Returns the ipInfos.
     */
    public List<IPInfo> getIpInfos() {
        return ipInfos;
    }

    /**
     * 设置ipInfos字段数据
     * @param ipInfos The ipInfos to set.
     */
    public void setIpInfos(List<IPInfo> ipInfos) {
        this.ipInfos = ipInfos;
    }

    /**
     * 获取resultRoute字段数据
     * @return Returns the resultRoute.
     */
    public String getResultRoute() {
        return resultRoute;
    }

    /**
     * 设置resultRoute字段数据
     * @param resultRoute The resultRoute to set.
     */
    public void setResultRoute(String resultRoute) {
        this.resultRoute = resultRoute;
    }


}
