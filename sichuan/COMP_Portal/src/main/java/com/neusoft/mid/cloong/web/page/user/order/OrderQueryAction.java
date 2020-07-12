/*******************************************************************************
 * @(#)OrderQueryAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户订单查询
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-6 下午02:14:33
 */
public class OrderQueryAction extends PageAction {

    private static final long serialVersionUID = -1178120268865015053L;

    private static LogService logger = LogService.getLogger(OrderQueryAction.class);

    /**
     * 从页面获取的查询条件
     */
    private String status;

    /**
     * 订单对应的资源实例类型
     */
    private String caseType;
    
    /**
     * 资源池ID
     */
    private String resPoolId;
    
    /**
     * 业务名称
     */
    private String appName;

    /**
     * 订单信息列表
     */
    private List<OrderInfo> orderInfoLs;

    /**
     * 异常刷新 订单信息
     */
    private Map<String, Object> orderInfos;
    
    /**
	 * 资源池
	 */
	private List<OrderQueryItem> pools = new ArrayList<OrderQueryItem>();

    /**
     * 返回路径，用于在界面判断是否系统错误
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();

    /**
     * 调用同步翻页状态码 0为异步 非0为同步(1)
     */
    private final String SYNCCODE = "0";

    /**
     * 同步标志位 0为异步 非0为同步(1) sprin配置 默认异步可以不配置
     */
    private String syncFlage = "0";

    /**
     * 设置syncFlage字段数据
     * @param syncFlage The syncFlage to set.
     */
    public void setSyncFlage(String syncFlage) {
        this.syncFlage = syncFlage;
    }

    @SuppressWarnings("unchecked")
    public String execute() {
    	try {
			pools = ibatisDAO.getData("getResPoolList", null);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单时失败，数据库异常！", e1);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
		}
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        OrderQueryItem orderQuery = new OrderQueryItem();
        // 页面上选择状态复选框进入
        if (null != status && !("").equals(status)) {
            orderQuery.setStatus(Arrays.asList(status.split(",")));
        }
        if (caseType != null && !caseType.equals("")) {
            orderQuery.setCaseType(caseType);
        }
        if (resPoolId != null && !"".equals(resPoolId)) {
            orderQuery.setResPoolId(resPoolId.trim());
        }
        if (appName != null && !"".equals(appName)) {
            orderQuery.setAppName(appName.trim());
        }
        orderQuery.setUserId(userId);
        try {
            if (SYNCCODE.equals(syncFlage)) {
                if (null == status) { // 第一次进入页面时status为空，sql不判断status
                    orderInfoLs = getPage("queryOrderCountByUserNoStatus", "queryOrderByUserNoStatus", orderQuery,
                            PageTransModel.ASYNC);
                } else {
                    orderInfoLs = getPage("queryOrderInfoCountByUser", "queryOrderInfoByUser", orderQuery,
                            PageTransModel.ASYNC);
                }
            } else {
                if (null == status) { // 第一次进入页面时status为空，sql不判断status
                    orderInfoLs = getPage("queryOrderCountByUserNoStatus", "queryOrderByUserNoStatus", orderQuery);
                } else {
                    orderInfoLs = getPage("queryOrderInfoCountByUser", "queryOrderInfoByUser", orderQuery);
                }
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "ID为[" + userId + "]的用户查询订单时失败，数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        if (orderInfoLs.size() == 0) {
            logger.info("ID为[" + userId + "]用户没有查询到符合条件的任何订单");
            this.addActionMessage(getText("user.order.query.null"));
            orderInfos = new HashMap<String, Object>();
            orderInfos.put("list", orderInfoLs);
            orderInfos.put("page", getPageBar());
            return ConstantEnum.SUCCESS.toString();
        }
        for (OrderInfo ordernfo : orderInfoLs) {
            ordernfo.setUpdateTime(DateParse.parse(ordernfo.getUpdateTime()));
        }
        logger.info("ID为[" + userId + "]的用户查询订单成功，共有" + orderInfoLs.size() + "条订单");
        orderInfos = new HashMap<String, Object>();
        orderInfos.put("list", orderInfoLs);
        orderInfos.put("page", getPageBar());
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取orderInfoLs字段数据
     * @return Returns the orderInfoLs.
     */
    public List<OrderInfo> getOrderInfoLs() {
        return orderInfoLs;
    }

    /**
     * 设置orderInfoLs字段数据
     * @param orderInfoLs The orderInfoLs to set.
     */
    public void setOrderInfoLs(List<OrderInfo> orderInfoLs) {
        this.orderInfoLs = orderInfoLs;
    }

    /**
     * 获取orderInfos字段数据
     * @return Returns the orderInfos.
     */
    public Map getOrderInfos() {
        return orderInfos;
    }

    /**
     * 设置orderInfos字段数据
     * @param orderInfos The orderInfos to set.
     */
    public void setOrderInfos(Map orderInfos) {
        this.orderInfos = orderInfos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    /**
     * 获取caseType字段数据
     * @return Returns the caseType.
     */
    public String getCaseType() {
        return caseType;
    }

    /**
     * 设置caseType字段数据
     * @param caseType The caseType to set.
     */
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

	/**
	 * @return the resPoolId
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * @param resPoolId the resPoolId to set
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the pools
	 */
	public List<OrderQueryItem> getPools() {
		return pools;
	}

	/**
	 * @param pools the pools to set
	 */
	public void setPools(List<OrderQueryItem> pools) {
		this.pools = pools;
	}

}
