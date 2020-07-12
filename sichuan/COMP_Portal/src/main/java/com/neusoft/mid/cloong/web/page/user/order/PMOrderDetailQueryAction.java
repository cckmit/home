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
import com.neusoft.mid.cloong.web.page.host.pm.order.info.PMStandardInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询物理机订单详细信息
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-2-7 下午02:57:14
 */
public class PMOrderDetailQueryAction extends BaseAction {



	/**
     * serialVersionUID 序列号
     */
    private static final long serialVersionUID = 8047972257379138372L;

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(PMOrderDetailQueryAction.class);

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单信息
     */
    private OrderInfo orderInfo;

    /**
     * 物理机规格信息
     */
    private PMStandardInfo stardardInfo;
    
    /**
     * 订单审批记录表实体
     */
    private OrderAuditInfo orderAuditInfo;

    /**
     * 返回路径，用于在界面判断是否系统错误
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();
    /**
     * 
     * execute 用于查询物理机订单详细信息
     * @return TODO 是否成功
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        orderInfo.setCreateUser(userId);
        try {
            orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryOrderDetailInfo", orderInfo);
            stardardInfo = (PMStandardInfo) ibatisDAO.getSingleRecord("queryPMStardInfo",
                    orderInfo.getStandardId());
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

    public String getResultPath() {
        return resultPath;
    }

	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

	public PMStandardInfo getStardardInfo() {
		return stardardInfo;
	}

}
