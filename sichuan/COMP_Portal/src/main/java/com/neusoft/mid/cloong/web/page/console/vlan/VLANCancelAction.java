package com.neusoft.mid.cloong.web.page.console.vlan;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelResp;
import com.neusoft.mid.cloong.vlan.core.VLANCancel;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanIpSegmentRel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * vlan取消action
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月5日 上午10:19:27
 */
public class VLANCancelAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 6787632155317285193L;

    private static LogService logger = LogService.getLogger(VLANCancelAction.class);

    /**
     * vlan号
     */
    private String vlanId;

    /**
     * 取消vlan接口
     */
    private VLANCancel vlanCancel;

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
    private String resourcePoolPartId;

    private String resultMessage;

    private String appId;

    public String execute() {

        logger.info("取消vlan开始");

        /* 开启事务 */
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();

        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));

        if (null == vlanId || "".equals(vlanId)) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "vlan号为空");
            msg = "vlan号为空";
            return ConstantEnum.FAILURE.toString();
        }

        vlanId = vlanId.trim();

        RPPVlanCancelReq req = new RPPVlanCancelReq();

        RPPVlanCancelResp resp = null;
        try {
            /* 校验是否可以取消 */
            if (validateVlan(vlanId)) {
                req.setVlanId(vlanId);
                req.setResourcePoolId(resourcePoolId);
                req.setResourcePoolPartId(resourcePoolPartId);
                req.setSerialNum(seqCen.generatorSerialNum());
                /* 校验成功,调用接口 */
                resp = vlanCancel.cancel(req);
            } else {
                /* 校验失败,直接返回不能操作 */
                errMsg = "vlan中有在用IP,无法删除";
                return ConstantEnum.STATUSINVALID.toString();
            }
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "vlan号为" + vlanId
                    + "的vlan失败", e);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e1) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "vlan取消失败，接口错误", e1);
            resultMessage = MessageFormat.format(getText("vlan.cancel.fail"), vlanId);
            errMsg = "vlan取消失败，接口错误";
            return ConstantEnum.FAILURE.toString();
        }

        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            /* 调用接口失败,打印错误信息,返回失败 */
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "vlan取消失败，接口错误");
            resultMessage = MessageFormat.format(getText("vlan.cancel.fail"), vlanId);
            errMsg = "vlan取消失败,调用资源池接口返回异常";
            return ConstantEnum.FAILURE.toString();
        } else {
            /* 调用接口成功,vlan取消状态入库 */

            VlanInfo vlaninfo = new VlanInfo();
            vlaninfo.setVlanId(vlanId);
            vlaninfo.setCancelUser(user.getUserId());
            vlaninfo.setCancelTime(DateParse.generateDateFormatyyyyMMddHHmmss());

            try {

                updateBatchVO.add(new BatchVO(BatchVO.OPERATION_MOD, "cancelVlan", vlaninfo));

                /* 判断IP段和VLAN绑定关系表是否需要置位 */
                VlanIpSegmentRel rel = new VlanIpSegmentRel();

                rel.setVlanId(vlanId);
                rel.setStatus("0");

                VlanIpSegmentRel res = (VlanIpSegmentRel) ibatisDAO.getSingleRecord("getVlanIpBindAss", rel);

                if (null != res) {
                    /* 将IP段和VLAN绑定关系表置位 */
                    VlanIpSegmentRel tmp = new VlanIpSegmentRel();
                    tmp.setVlanId(vlanId);
                    updateBatchVO.add(new BatchVO(BatchVO.OPERATION_MOD,
                            "invalidateIpSegmentAndVlanAss", tmp));
                }

                ibatisDAO.updateBatchData(updateBatchVO);

            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "vlan号为" + vlanId
                        + "的vlan更新数据库状态失败", e);
                return ConstantEnum.ERROR.toString();
            }
        }

        logger.info("取消vlan完成");
        msg = "取消vlan完成";

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 校验vlan是否可以被取消
     * @param vlanId vlan号
     * @return 校验通过 - true ; 不通过 - false
     * @throws SQLException 数据库访问异常
     */
    private boolean validateVlan(String vlanId) throws SQLException {

        boolean validate = true;

        /*
         * 由于资源池不会解绑vlan和IP段的关系，运营亦须维护vlan和IP段的关系表 因此，校验2项： 1.vlan和IP段关系表中是否有此vlan，如果没有，则可以取消
         * 2.1中如果有，那么校验该vlan绑定的IP段中是否有IP在用，如果没有，则可以取消 3.2中如果有，则不可以取消 取消后，需要将绑定表置位
         */
        Integer count = (Integer) ibatisDAO.getSingleRecord("getVmBindingVlan", vlanId);

        if (!count.equals(0)) validate = false;

        return validate;
    }

    /**
     * 获取vlanCancel字段数据
     * @return Returns the vlanCancel.
     */
    public VLANCancel getVlanCancel() {
        return vlanCancel;
    }

    /**
     * 设置vlanCancel字段数据
     * @param vlanCancel The vlanCancel to set.
     */
    public void setVlanCancel(VLANCancel vlanCancel) {
        this.vlanCancel = vlanCancel;
    }

    /**
     * 获取vlanId字段数据
     * @return Returns the vlanId.
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
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

    /**
     * 获取resourcePoolPartId字段数据
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 设置resourcePoolPartId字段数据
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 获取resultMessage字段数据
     * @return Returns the resultMessage.
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * 设置resultMessage字段数据
     * @param resultMessage The resultMessage to set.
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

}
