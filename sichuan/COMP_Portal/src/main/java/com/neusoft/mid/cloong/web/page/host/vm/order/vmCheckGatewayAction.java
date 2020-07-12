package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.IPInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.IpSegment;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.IPv4Util;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 检查网关信息是否合法
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-17 上午10:51:53
 */
public class vmCheckGatewayAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2939491577089339448L;

    private static LogService logger = LogService.getLogger(vmCheckGatewayAction.class);

    /**
     * gatewayId
     */
    private String gatewayId;

    /**
     * ipsegmentId
     */
    private String ipsegmentId;

    /**
     * ip
     */
    private String ip;

    /**
     * 返回结果路径,error,failure
     */
    private String resultRoute;

    private String errMsg;

    private static final Pattern IPV4_PATTERN = Pattern
            .compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN = Pattern
            .compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern
            .compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    public String execute() {
        try {

            if (StringUtils.isEmpty(ipsegmentId)) {
                errMsg = "IP段为空，请选择IP段";
                resultRoute = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }

            if (StringUtils.isEmpty(ip)) {
                errMsg = "IP为空，请选择IP";
                resultRoute = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }

            if (StringUtils.isEmpty(gatewayId)) {
                errMsg = "网关IP为空，请填写网关IP";
                resultRoute = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }

            if (!isIPv4Address(gatewayId)) {
                errMsg = "网关IP地址:" + gatewayId + " 非法";
                resultRoute = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }

            if (gatewayId.equals(ip)) {
                errMsg = "网关IP地址与IP地址相同，请重新输入";
                resultRoute = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }

            IpSegment ipSegment = (IpSegment) ibatisDAO.getSingleRecord("getIPsegmentByID",
                    ipsegmentId);
            String startIp = ipSegment.getStartIp();
            String endIp = ipSegment.getEndIp();
            List<String> ips = IpSegmentUtil.getIpList(startIp, endIp);
            if (ips != null && ips.size() > 0) {
                for (String ip : ips) {
                    if (ip.equals(gatewayId)) {
                        return ConstantEnum.SUCCESS.toString();
                    }
                }
            }

        } catch (Exception e) {
            logger.error(VMStatusCode.ONLOADQUERYRIPSEGMENTS_EXCEPTION_CODE,
                    getText("vmonload.ipsegment.fail"), e);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }

        errMsg = "网关IP地址非法，与选择的IP段不是同一个网段";
        resultRoute = ConstantEnum.ERROR.toString();
        return ConstantEnum.ERROR.toString();

    }

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    /**
     * 获取gatewayId字段数据
     * @return Returns the gatewayId.
     */
    public String getGatewayId() {
        return gatewayId;
    }

    /**
     * 设置gatewayId字段数据
     * @param gatewayId The gatewayId to set.
     */
    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    /**
     * 获取ipsegmentId字段数据
     * @return Returns the ipsegmentId.
     */
    public String getIpsegmentId() {
        return ipsegmentId;
    }

    /**
     * 设置ipsegmentId字段数据
     * @param ipsegmentId The ipsegmentId to set.
     */
    public void setIpsegmentId(String ipsegmentId) {
        this.ipsegmentId = ipsegmentId;
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

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取errMsg字段数据
     * @return Returns the errMsg.
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 设置errMsg字段数据
     * @param errMsg The errMsg to set.
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
