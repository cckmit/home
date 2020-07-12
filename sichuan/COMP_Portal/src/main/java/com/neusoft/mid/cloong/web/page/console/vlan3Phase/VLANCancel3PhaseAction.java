package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

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
import com.neusoft.mid.cloong.vlan3Phase.core.VLANCancel;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanIpSegmentRel;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * vlan取消action
 */
public class VLANCancel3PhaseAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 6787632155317285193L;

    private static LogService logger = LogService.getLogger(VLANCancel3PhaseAction.class);

    /**
     * vlan号
     */
    private String vlanId;
    
    /**
     * vlan订单ID
     */
    private String caseId;

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

    private String msg;
    
    private String errMsg;
    
	private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {

        logger.info("删除vlan订单开始");
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        
        caseId = caseId.trim();
        if (null == caseId || "".equals(caseId)) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "订单ID为空");
            msg = "订单ID为空";
            return ConstantEnum.FAILURE.toString();
        }
        
//      查询VLAN_SDN信息是否符合删除条件
        VlanInfo vlan = new VlanInfo();
        vlan.setCaseId(caseId);
        OrderInfo orderInfo = new OrderInfo();
        try{
        	VlanInfo vlanInfo =  (VlanInfo) ibatisDAO.getSingleRecord("getVlanRangeInfo", vlan);
        	orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("getVlanRangeOrderInfo", vlanInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "vlan订单ID为" + caseId
                    + "的vlan查询数据库失败", e);
			resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (!("3".equals(orderInfo.getStatus())||"1".equals(orderInfo.getStatus()))) { // 只有已生效的vlan范围订单才能提供释放
            logger.info(getText("该vlan未生效，无法删除"));
            msg = getText("该vlan未生效，无法删除");
			resultPath = ConstantEnum.FAILURE.toString();
            return ConstantEnum.FAILURE.toString();
        }else{
        	orderInfo.setStatus("8");
        }
        
//        重置订单表状态，提交删除订单
        try{
        	ibatisDAO.updateData("cancelVlanRange3Phase", orderInfo);
        } catch (SQLException e) {
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "vlan刪除失败", e);
            errMsg = "vlan刪除失败！";
			resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
		}               
        logger.info("删除vlan订单完成");
        msg = "删除VLAN已提交，请等待审批";
		resultPath = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
    
    

}
