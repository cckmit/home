package com.neusoft.mid.cloong.web.page.host.pm.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.IPInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.IpSegment;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过IP段查询可用的IP
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-12 下午4:03:58
 */
public class PMOnloadPrivateIpAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(PMOnloadPrivateIpAction.class);

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

            // 查询正在使用的物理机，vlan使用的ip和网关（删除的物理机ip可以继续使用）
            List<NetInfo> netInfos = ibatisDAO.getData("getAllPmNet", logicVlanId);
            for (NetInfo netInfo : netInfos) {
                useIp.put(netInfo.getIp(), netInfo.getIp());
                useIp.put(netInfo.getGateway(), netInfo.getGateway());
            }

            // 查询正在修改的物理机，vlan使用的ip和网关
            List<NetInfo> netInfosForVmMod = ibatisDAO.getData("getAllNetForPmMod", logicVlanId);
            for (NetInfo netInfo : netInfosForVmMod) {
                useIp.put(netInfo.getIp(), netInfo.getIp());
                useIp.put(netInfo.getGateway(), netInfo.getGateway());
            }
            
            // 按照ip段ID 查询ip段的起止ip
            IpSegment ipSegment = (IpSegment) ibatisDAO.getSingleRecord("getIPsegmentByID",
                    ipSegmentId);
            String startIp = ipSegment.getStartIp();
            String endIp = ipSegment.getEndIp();

            // 通过起止ip查询出该ip段内包含的所有ip
            ipInfos = new ArrayList<IPInfo>();
            List<String> ips = IpSegmentUtil.getIpList(startIp, endIp);
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

        } catch (Exception e) {
            logger.error(PMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
                    getText("pmonload.ip.fail"), e);
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
