package com.neusoft.mid.cloong.web.page.console.ipSegment;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpInfo;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanIpSegmentRel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IP段详情展示
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月11日 下午7:41:11
 */
public class IpSegmentDetailAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 404218835960468199L;

    private final LogService logger = LogService.getLogger(IpSegmentDetailAction.class);

    private String resPoolId;

    private String resPoolName;

    //private String resPoolPartId;

    //private String resPoolPartName;

    private String ipSegmentId;

    private IpSegmentInfo ipSegment;

    private List<IpInfo> usedIpList;

    private Integer ipUsedCount;

    private String ipUnusedCount;

    private String queryBusinessId;

    private String businessName;
    
    private String releaseMsg;

    @SuppressWarnings({ "deprecation", "unchecked" })
    public String execute() {

        logger.info("IP段详情页展示开始");

        IpSegmentInfo para = new IpSegmentInfo();

        para.setIpSegmentId(ipSegmentId);
        para.setResPoolId(resPoolId);

        try {
            ipSegment = (IpSegmentInfo) ibatisDAO.getSingleRecord("getIpSegmentById", para);
            
            ipSegment.setCreateTime(DateParse.parse(ipSegment.getCreateTime()));

            ipUsedCount = (Integer) ibatisDAO.getSingleRecord("getUsedIpCountById", para);

            double ipTotal = Double.parseDouble(ipSegment.getIpTotal()) - ipUsedCount;
            DecimalFormat df = new DecimalFormat("########");
            df.format(ipTotal);
            ipUnusedCount = df.format(ipTotal);

            usedIpList = getPage("getUsedIpCountById", "getUsedIpById", para, PageTransModel.ASYNC,
                    PageToDisplayPerModel.ALL_DISPLAY);

            resPoolName = (String) ibatisDAO.getSingleRecord("getResPoolName", resPoolId);
            //resPoolPartName = (String) ibatisDAO.getSingleRecord("getResPoolPartName",
            //        resPoolPartId);

            if ("2".equals(ipSegment.getStatus()))
                releaseMsg = "IP段正在创建中,请勿释放";
            else {
                VlanIpSegmentRel rel = new VlanIpSegmentRel();
                rel.setIpSegmentId(ipSegment.getIpSegmentId());
                rel.setStatus("2");
                rel = (VlanIpSegmentRel) ibatisDAO.getSingleRecord("getVlanIpBindAss", rel);

                if (null != rel)
                    releaseMsg = "该IP段有待审批的虚拟机，不能释放";

                else if (!ipUsedCount.equals(0)) releaseMsg = "该IP段有在用的ip,不能释放";
            }

        } catch (SQLException e1) {
            logger.error("IP段详情查询异常", e1);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            logger.error("IP段详情查询异常", e2);
            return ConstantEnum.ERROR.toString();
        }

        logger.info("IP段详情页展示完成");

        return ConstantEnum.SUCCESS.toString();
    }

    public static void main(String[] args) {
    	System.out.println(Double.valueOf("4294967296") - 1);
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
     * 获取ipSegment字段数据
     * @return Returns the ipSegment.
     */
    public IpSegmentInfo getIpSegment() {
        return ipSegment;
    }

    /**
     * 设置ipSegment字段数据
     * @param ipSegment The ipSegment to set.
     */
    public void setIpSegment(IpSegmentInfo ipSegment) {
        this.ipSegment = ipSegment;
    }

    /**
     * 获取usedIpList字段数据
     * @return Returns the usedIpList.
     */
    public List<IpInfo> getUsedIpList() {
        return usedIpList;
    }

    /**
     * 设置usedIpList字段数据
     * @param usedIpList The usedIpList to set.
     */
    public void setUsedIpList(List<IpInfo> usedIpList) {
        this.usedIpList = usedIpList;
    }

    /**
     * 获取resPoolName字段数据
     * @return Returns the resPoolName.
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * 设置resPoolName字段数据
     * @param resPoolName The resPoolName to set.
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

//    /**
//     * 获取resPoolPartName字段数据
//     * @return Returns the resPoolPartName.
//     */
//    public String getResPoolPartName() {
//        return resPoolPartName;
//    }
//
//    /**
//     * 设置resPoolPartName字段数据
//     * @param resPoolPartName The resPoolPartName to set.
//     */
//    public void setResPoolPartName(String resPoolPartName) {
//        this.resPoolPartName = resPoolPartName;
//    }

    /**
     * 获取ipUsedCount字段数据
     * @return Returns the ipUsedCount.
     */
    public Integer getIpUsedCount() {
        return ipUsedCount;
    }

    /**
     * 设置ipUsedCount字段数据
     * @param ipUsedCount The ipUsedCount to set.
     */
    public void setIpUsedCount(Integer ipUsedCount) {
        this.ipUsedCount = ipUsedCount;
    }


    /**
	 * @return the ipUnusedCount
	 */
	public String getIpUnusedCount() {
		return ipUnusedCount;
	}

	/**
	 * @param ipUnusedCount the ipUnusedCount to set
	 */
	public void setIpUnusedCount(String ipUnusedCount) {
		this.ipUnusedCount = ipUnusedCount;
	}

	/**
     * 获取queryBusinessId字段数据
     * @return Returns the queryBusinessId.
     */
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    /**
     * 设置queryBusinessId字段数据
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    /**
     * 获取releaseMsg字段数据
     * @return Returns the releaseMsg.
     */
    public String getReleaseMsg() {
        return releaseMsg;
    }

    /**
     * 设置releaseMsg字段数据
     * @param releaseMsg The releaseMsg to set.
     */
    public void setReleaseMsg(String releaseMsg) {
        this.releaseMsg = releaseMsg;
    }

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
