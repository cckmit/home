package com.neusoft.mid.cloong.web.page.host.pm.order;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请物理机页面加载资源池和资源池分区页面信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午02:30:35
 */
public class PMOnloadIpsegmentAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(PMOnloadIpsegmentAction.class);

    /**
     * vlanid
     */
    private String vlanId;

    /**
     * appid
     */
    private String appId;

    /**
     * IP段列表
     */
    private List<IpSegmentInfo> ipSegmentList;

    /**
     * 返回结果路径,error,failure
     */
    private String resultRoute;

    public String execute() {
        try {
            IpSegmentInfo ipSegment = new IpSegmentInfo();
            ipSegmentList = new ArrayList<IpSegmentInfo>();
            VlanInfo vlan = new VlanInfo();
            vlan.setVlanId(vlanId);
            List<Integer> tmp = new ArrayList<Integer>();
            tmp.add(0);
            vlan.setCanceled(tmp);
            vlan = (VlanInfo) ibatisDAO.getSingleRecord("getVlanList", vlan);
            // 如果没有绑定过VLAN，那么根据当前物理机所属业务，查询出该业务下所有可用IP段(未绑定VLAN)
            if (null == vlan.getIpSegmentId() || ("").equals(vlan.getIpSegmentId())) {
                ipSegment.setAppId(appId);
                ipSegmentList = ibatisDAO.getData("getUnusedIpSegmentList", ipSegment);
                // 页面显示IP段格式为 起始IP ~ 结束IP
                for (IpSegmentInfo temp : ipSegmentList) {
                    temp.setIpSegment(temp.getStartIp() + " ~ " + temp.getEndIp());
                }
            } else {// 如果当前VLAN有绑定的IP段，那么直接查询出该IP段
                // 资源池和分区
                ipSegment.setResPoolId(vlan.getResPoolId());
                ipSegment.setResPoolPartId(vlan.getResPoolPartId());
                // 页面显示IP段格式为 起始IP ~ 结束IP
                ipSegment.setIpSegment(vlan.getStartIp() + " ~ " + vlan.getEndIp());
                ipSegment.setIpSegmentId(vlan.getIpSegmentId());
                ipSegmentList.add(ipSegment);
            }
        } catch (Exception e2) {
            logger.error(PMStatusCode.ONLOADQUERYRIPSEGMENTS_EXCEPTION_CODE,
                    getText("pmonload.ipsegment.fail"), e2);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();

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

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<IpSegmentInfo> getIpSegmentList() {
        return ipSegmentList;
    }

    public void setIpSegmentList(List<IpSegmentInfo> ipSegmentList) {
        this.ipSegmentList = ipSegmentList;
    }

}
