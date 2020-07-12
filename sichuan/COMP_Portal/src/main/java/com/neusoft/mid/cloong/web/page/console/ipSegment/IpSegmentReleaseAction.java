package com.neusoft.mid.cloong.web.page.console.ipSegment;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ipSegment.core.ReleaseIpSegment;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanIpSegmentRel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过调用ajax请求,释放IP段
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月13日 下午2:55:09
 */
public class IpSegmentReleaseAction extends BaseAction {

    private static final long serialVersionUID = -3721696848952625902L;

    private final LogService logger = LogService.getLogger(IpSegmentReleaseAction.class);

    private String ipSegmentId;

    /**
     * 释放IP段接口
     */
    private ReleaseIpSegment ipSegmentRelease;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    //private String resourcePoolPartId;

    @SuppressWarnings("deprecation")
    public String execute() {

        logger.info("释放IP段Action开始");

        /* 开启事务 */
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();

        Integer illegalCount = 0;

        RPPIPReleaseResp resp = null;

        try {
            IpSegmentInfo info = new IpSegmentInfo();
            info.setIpSegmentId(ipSegmentId);

            illegalCount = (Integer) ibatisDAO.getSingleRecord("getUsedIpCountById", info);
        } catch (Exception e) {
            logger.error("释放IP段查询数据库异常", e);
            msg = "释放IP段查询数据库异常";
            return ConstantEnum.ERROR.toString();
        }

        if (illegalCount != 0) {
            /* 不能释放 */
            msg = "该IP段包含正在使用的IP,无法释放";
            return ConstantEnum.FAILURE.toString();
        } else {
            /* 释放 */
            try {
                RPPIPReleaseReq req = new RPPIPReleaseReq();
                req.setIpSegmentURI(ipSegmentId);
                req.setResourcePoolId(resourcePoolId);
                //req.setResourcePoolPartId(resourcePoolPartId);
                req.setSerialNum(seqCen.generatorSerialNum());

                resp = ipSegmentRelease.release(req);

            } catch (Exception e) {
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "vlan取消失败，接口错误", e);
                msg = "释放IP段失败，接口错误";
                return ConstantEnum.FAILURE.toString();
            }

            /* 调用接口失败 */
            if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
                /* 调用接口失败,打印错误信息,返回失败 */
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "IP段释放失败，接口错误");
                msg = "IP段释放失败,调用资源池接口返回异常";
                return ConstantEnum.FAILURE.toString();
            } else {
                try {
                    updateBatchVO.add(new BatchVO(BatchVO.OPERATION_MOD, "releaseIpSegment",
                            ipSegmentId));
                    /* 判断IP段和VLAN绑定关系表是否需要置位 */
                    VlanIpSegmentRel rel = new VlanIpSegmentRel();

                    rel.setIpSegmentId(ipSegmentId);
                    rel.setStatus("0");
                    
                    VlanIpSegmentRel res = (VlanIpSegmentRel) ibatisDAO.getSingleRecord(
                            "getVlanIpBindAss", rel);

                    if (null != res) {
                        VlanIpSegmentRel tmp = new VlanIpSegmentRel();
                        tmp.setIpSegmentId(ipSegmentId);
                        updateBatchVO.add(new BatchVO(BatchVO.OPERATION_MOD,
                                "invalidateIpSegmentAndVlanAss", tmp));
                    }
                    ibatisDAO.updateBatchData(updateBatchVO);

                    msg = "释放IP段成功";
                } catch (Exception e) {
                    logger.error("释放IP段更新数据库异常", e);
                    msg = "释放IP段更新数据库异常";
                    return ConstantEnum.ERROR.toString();
                }
            }

        }

        logger.info("释放IP段Action完成");

        return ConstantEnum.SUCCESS.toString();
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
     * 获取ipSegmentRelease字段数据
     * @return Returns the ipSegmentRelease.
     */
    public ReleaseIpSegment getIpSegmentRelease() {
        return ipSegmentRelease;
    }

    /**
     * 设置ipSegmentRelease字段数据
     * @param ipSegmentRelease The ipSegmentRelease to set.
     */
    public void setIpSegmentRelease(ReleaseIpSegment ipSegmentRelease) {
        this.ipSegmentRelease = ipSegmentRelease;
    }

    /**
     * 获取seqCen字段数据
     * @return Returns the seqCen.
     */
    public CommonSequenceGenerator getSeqCen() {
        return seqCen;
    }

    /**
     * 设置seqCen字段数据
     * @param seqCen The seqCen to set.
     */
    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }

    /**
     * 获取resourcePoolId字段数据
     * @return Returns the resourcePoolId.
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 设置resourcePoolId字段数据
     * @param resourcePoolId The resourcePoolId to set.
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

//    /**
//     * 获取resourcePoolPartId字段数据
//     * @return Returns the resourcePoolPartId.
//     */
//    public String getResourcePoolPartId() {
//        return resourcePoolPartId;
//    }
//
//    /**
//     * 设置resourcePoolPartId字段数据
//     * @param resourcePoolPartId The resourcePoolPartId to set.
//     */
//    public void setResourcePoolPartId(String resourcePoolPartId) {
//        this.resourcePoolPartId = resourcePoolPartId;
//    }

}
