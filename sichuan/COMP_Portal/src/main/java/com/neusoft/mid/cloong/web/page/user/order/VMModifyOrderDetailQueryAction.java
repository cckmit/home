/*******************************************************************************
 * @(#)OrderDetailQueryAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMStandardInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询待修改审批的虚拟机订单详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-7 上午08:53:39
 */
public class VMModifyOrderDetailQueryAction extends BaseAction {

    private static final long serialVersionUID = -3291560302940406846L;

    private static LogService logger = LogService.getLogger(VMModifyOrderDetailQueryAction.class);

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单信息
     */
    private OrderInfo orderInfo;

    /**
     * 镜像信息
     */
    private OsInfo osInfo;

    /**
     * 虚拟机实例信息
     */
    private VMInstanceInfo vmInstanceInfo;
    
    /**
     * 虚拟机规格信息
     */
    private VMStandardInfo stardardInfo;
    
    /**
     * 订单审批记录表实体
     */
    private OrderAuditInfo orderAuditInfo;
    
    /**
     * 返回路径，用于在界面判断是否系统错误
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        orderInfo.setCreateUser(userId);
        try {
            orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryModifyOrderDetailInfo", orderInfo);
            osInfo = (OsInfo) ibatisDAO.getSingleRecord("queryOsInof", orderInfo.getOsId());
            vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("selectVMModifyInfo", orderInfo.getOrderId());
            vmInstanceInfo.setDiscSize(Long.toString(Long.parseLong(vmInstanceInfo.getDiscSizeAdd())+Long.parseLong(vmInstanceInfo.getDiscSize()))); // 显示的详情是修改后的（原来的加上新增的）
            stardardInfo = (VMStandardInfo) ibatisDAO.getSingleRecord("queryVmModifyStardInfo", vmInstanceInfo.getStandardId());
            orderAuditInfo = (OrderAuditInfo) ibatisDAO.getSingleRecord("queryOrderAuditInfo",orderId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单编号为" + orderId
                    + "的详细信息时失败，数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单编号为" + orderId
                    + "的详细信息时失败，数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        if (orderInfo.getEffectiveTime() != null) {
            orderInfo.setEffectiveTime(DateParse.parse(orderInfo.getEffectiveTime()));
        }
        if (orderInfo.getExpireTime() != null) {
            orderInfo.setExpireTime(DateParse.parse(orderInfo.getExpireTime()));
        }
        if (orderAuditInfo != null && orderAuditInfo.getAuditTime() != null) {
        	orderAuditInfo.setAuditTime(DateParse.parse(orderAuditInfo.getAuditTime()));
        }
        orderInfo.setCreateTime(DateParse.parse(orderInfo.getCreateTime()));
        return ConstantEnum.SUCCESS.toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public OsInfo getOsInfo() {
        return osInfo;
    }

    public VMInstanceInfo getVmInstanceInfo() {
        return vmInstanceInfo;
    }

    public void setVmInstanceInfo(VMInstanceInfo vmInstanceInfo) {
        this.vmInstanceInfo = vmInstanceInfo;
    }

    public String getResultPath() {
        return resultPath;
    }

	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

    public VMStandardInfo getStardardInfo() {
        return stardardInfo;
    }

    public void setStardardInfo(VMStandardInfo stardardInfo) {
        this.stardardInfo = stardardInfo;
    }

}
