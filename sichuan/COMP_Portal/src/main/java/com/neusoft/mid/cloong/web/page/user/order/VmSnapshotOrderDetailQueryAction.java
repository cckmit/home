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
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机备份订单详细信息
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-2-7 下午04:18:43
 */
public class VmSnapshotOrderDetailQueryAction extends BaseAction {

    /**
     * serialVersionUID : 序列号
     */
    private static final long serialVersionUID = -1637521925126834826L;


	/**
	 * 日志
	 */
	private static LogService logger = LogService.getLogger(VmSnapshotOrderDetailQueryAction.class);

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单信息
     */
    private OrderInfo orderInfo;

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
     * execute 用于虚拟机备份详情
     * @return 是否成功
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
            orderAuditInfo = (OrderAuditInfo) ibatisDAO.getSingleRecord("queryOrderAuditInfo",orderId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单编号为" + orderId + "的详细信息时失败，数据库异常！", e);
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

    /**
     * 获取orderId字段数据
     * @return Returns the orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId字段数据
     * @param orderId The orderId to set.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取orderInfo字段数据
     * @return Returns the orderInfo.
     */
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    /**
     * 设置orderInfo字段数据
     * @param orderInfo The orderInfo to set.
     */
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    /**
     * 获取orderAuditInfo字段数据
     * @return Returns the orderAuditInfo.
     */
    public OrderAuditInfo getOrderAuditInfo() {
        return orderAuditInfo;
    }

    /**
     * 设置orderAuditInfo字段数据
     * @param orderAuditInfo The orderAuditInfo to set.
     */
    public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
        this.orderAuditInfo = orderAuditInfo;
    }

    /**
     * 获取resultPath字段数据
     * @return Returns the resultPath.
     */
    public String getResultPath() {
        return resultPath;
    }

    /**
     * 设置resultPath字段数据
     * @param resultPath The resultPath to set.
     */
    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

}
