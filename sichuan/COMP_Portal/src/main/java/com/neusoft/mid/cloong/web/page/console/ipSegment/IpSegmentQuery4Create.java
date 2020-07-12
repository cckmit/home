package com.neusoft.mid.cloong.web.page.console.ipSegment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.common.util.ip.Ipv6Util;
import com.neusoft.mid.cloong.common.util.ip.MaskErrorException;
import com.neusoft.mid.cloong.ipSegment.core.QueryIpSegment;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPApplyState;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp.IPSegmentInfo;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.common.Page;
import com.neusoft.mid.cloong.web.page.common.PageHelper;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * ajax调用资源池接口,以查询可申请的IP段
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月9日 下午5:06:40
 */
public class IpSegmentQuery4Create extends PageAction {

    private static final long serialVersionUID = -882196757064670109L;

    private final LogService logger = LogService.getLogger(IpSegmentQuery4Create.class);

    /**
     * 传值list
     */
    private List<IpSegmentInfo> ipSegmentList;

    /**
     * 查询IP段接口
     */
    private QueryIpSegment queryIpSegment;

    /**
     * 接口调用返回成功码
     */
    private final String SUCCESS_CODE = "00000000";

    /**
     * 资源池ID
     */
    private String resPoolId;
    
    /**
     * IP的地址类型：IPV4, IPV6
     */
    private String ipType;

    /**
     * 资源池分区ID  
     * 创建ip跨分区
     */
   // private String resPoolPartId;

    /**
     * 调用失败时的错误信息
     */
    private String faultString;

    @SuppressWarnings({ "deprecation", "unchecked" })
    public String execute() {

        RPPIPQueryReq req = new RPPIPQueryReq();

        req.setResourcePoolId(resPoolId);

        //req.setResourcePoolPartId(resPoolPartId);

        RPPIPQueryResp resp = queryIpSegment.queryIpSegment(req);

        ipSegmentList = new ArrayList<IpSegmentInfo>();

        List<IpSegmentInfo> reportingList = null;

        try {
            reportingList = ibatisDAO.getData("getReportingIpSegment", null);
        } catch (Exception e) {
            logger.error("查询待审IP段数据库异常", e);
            return ConstantEnum.ERROR.toString();
        }

        if (null != resp && SUCCESS_CODE.equals(resp.getResultCode())) {

            for (IPSegmentInfo rppInfo : resp.getIpSegmentInfoList()) {

                boolean flag = true;

                /* 过滤待审的IP段 */
                for (IpSegmentInfo ipSegmentInfo : reportingList)
                    if (ipSegmentInfo.getIpSubnet().equals(rppInfo.getIpSubNet())) {
                        flag = false;
                        break;
                    }

                if (flag && IPApplyState.NO_APPLY.equals(rppInfo.getApplyState())) {

                    IpSegmentInfo ipSegment = new IpSegmentInfo();

                    ipSegment.setIpSegmentId(rppInfo.getIpSegmentURI());

                    // 获得IPV4&IPV6的网段信息：IPV4:网关ip/24; IPV6:网段/96
                    String subnet = rppInfo.getIpSubNet();

                    String firstIp = "";

                    String lastIp = "";
                    
                    if ("0".equals(ipType) && rppInfo.getIpProType().getValue().equals("IPV4")) {
                    	try {
                            List<String> extreme = IpSegmentUtil.getExtremeIp(subnet);
                            firstIp = extreme.get(0);
                            lastIp = extreme.get(1);
                        } catch (MaskErrorException e) {
                            logger.error("查询IP段接口中,传回IP段的掩码位数异常", e);
                            return ConstantEnum.ERROR.toString();
                        }

                        ipSegment.setStartIp(firstIp);
                        ipSegment.setEndIp(lastIp);
                        ipSegment.setIpSubnet(subnet);
                        ipSegment.setIpTotal(IpSegmentUtil.getIpSegmentSize(firstIp, lastIp).toString());

                        ipSegmentList.add(ipSegment);
                    }
                    
                    if ("1".equals(ipType) && rppInfo.getIpProType().getValue().equals("IPV6")) {
                    	try {
                    		List<String> extremeIpv6 = Ipv6Util.getIpv6ExtremeIp(subnet);
                    		firstIp = extremeIpv6.get(0);
                            lastIp = extremeIpv6.get(1);
                    	} catch (MaskErrorException e) {
                            logger.error("查询IP段接口中,传回IP段的掩码位数异常", e);
                            return ConstantEnum.ERROR.toString();
                        }
                    	
                    	ipSegment.setStartIp(firstIp);
                        ipSegment.setEndIp(lastIp);
                        ipSegment.setIpSubnet(subnet);
                        ipSegment.setIpTotal(Ipv6Util.getIpv6Size(subnet.split("/")[1]));

                        ipSegmentList.add(ipSegment);
                    }
                    

                }
            }

        } else {
            if (null != resp)
                faultString = resp.getResultMessage();
            else
                faultString = "IP段查询接口调用响应失败";
            logger.info(faultString);
            return ConstantEnum.FAILURE.toString();
        }

        /* 分页 */
        Page pageObj = new Page(getPage(), ipSegmentList.size(), getPageSize(), getUrl(),
                getParam(), getAsyncJSPageMethod());
        this.setPageBar(PageHelper.getPageBar(pageObj, PageTransModel.ASYNC,
                PageToDisplayPerModel.PAGESIZE_10_DISPLAY));
        if (ipSegmentList.size() > getPageSize()) {
            int lastIndex = pageObj.getStartOfPage() + getPageSize();
            if (lastIndex > ipSegmentList.size()) lastIndex = ipSegmentList.size();
            ipSegmentList = ipSegmentList.subList(pageObj.getStartOfPage(), lastIndex);
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取ipSegmentList字段数据
     * @return Returns the ipSegmentList.
     */
    public List<IpSegmentInfo> getIpSegmentList() {
        return ipSegmentList;
    }

    /**
     * 设置ipSegmentList字段数据
     * @param ipSegmentList The ipSegmentList to set.
     */
    public void setIpSegmentList(List<IpSegmentInfo> ipSegmentList) {
        this.ipSegmentList = ipSegmentList;
    }

    /**
     * 获取queryIpSegment字段数据
     * @return Returns the queryIpSegment.
     */
    public QueryIpSegment getQueryIpSegment() {
        return queryIpSegment;
    }

    /**
     * 设置queryIpSegment字段数据
     * @param queryIpSegment The queryIpSegment to set.
     */
    public void setQueryIpSegment(QueryIpSegment queryIpSegment) {
        this.queryIpSegment = queryIpSegment;
    }

    /**
     * 获取faultString字段数据
     * @return Returns the faultString.
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * 设置faultString字段数据
     * @param faultString The faultString to set.
     */
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    /**
     * 获取resPoolId字段数据
     * @return Returns the resPoolId.
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 设置resPoolId字段数据
     * @param resPoolId The resPoolId to set.
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
    

//    /**
//     * 获取resPoolPartId字段数据
//     * @return Returns the resPoolPartId.
//     */
//    public String getResPoolPartId() {
//        return resPoolPartId;
//    }
//
//    /**
//     * 设置resPoolPartId字段数据
//     * @param resPoolPartId The resPoolPartId to set.
//     */
//    public void setResPoolPartId(String resPoolPartId) {
//        this.resPoolPartId = resPoolPartId;
//    }
}
