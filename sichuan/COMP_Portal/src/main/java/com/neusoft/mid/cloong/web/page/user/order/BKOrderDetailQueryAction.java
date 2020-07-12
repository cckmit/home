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
import com.neusoft.mid.cloong.web.page.vmbak.order.info.VMBAKStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机备份订单详细信息
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-2-7 下午04:18:43
 */
public class BKOrderDetailQueryAction extends BaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

	/**
	 * 日志
	 */
	private static LogService logger = LogService.getLogger(BKOrderDetailQueryAction.class);

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单信息
     */
    private OrderInfo orderInfo;

    /**
     * 虚拟机备份规格信息
     */
    private VMBAKStandardInfo stardardInfo;
    
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
            stardardInfo = (VMBAKStandardInfo) ibatisDAO.getSingleRecord("queryBKStardInfo",
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

    /**
     * 
     * getOrderId TODO 方法
     * @return TODO
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * setOrderId TODO 方法
     * @param orderId TODO
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * getOrderInfo TODO 方法
     * @return TODO
     */
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    /**
     * 
     * getStardardInfo TODO 方法
     * @return TODO
     */
    public VMBAKStandardInfo getStardardInfo() {
        return stardardInfo;
    }

    /**
     * 
     * getResultPath TODO 方法
     * @return TODO
     */
    public String getResultPath() {
        return resultPath;
    }

    /**
     * 
     * getOrderAuditInfo TODO 方法
     * @return TODO
     */
	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}
    
}
