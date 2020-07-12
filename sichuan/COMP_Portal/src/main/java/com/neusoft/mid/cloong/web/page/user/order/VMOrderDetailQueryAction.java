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
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.StandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机订单详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-7 上午08:53:39
 */
public class VMOrderDetailQueryAction extends BaseAction {
    
    /**
     * 序列号
     */
    private static final long serialVersionUID = -6312042526635641074L;
    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(VMOrderDetailQueryAction.class);

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
     * 虚拟机规格信息
     */
    private StandardInfo stardardInfo;
    
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
            orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryOrderDetailInfo", orderInfo);
            osInfo = (OsInfo) ibatisDAO.getSingleRecord("queryOsInof", orderInfo.getOsId());
            stardardInfo = (StandardInfo) ibatisDAO.getSingleRecord("queryVmStardInfo", orderInfo.getStandardId());
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

    public StandardInfo getStardardInfo() {
        return stardardInfo;
    }

    public String getResultPath() {
        return resultPath;
    }

	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

    
}
