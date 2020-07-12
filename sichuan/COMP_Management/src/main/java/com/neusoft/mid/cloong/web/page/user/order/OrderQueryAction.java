/*******************************************************************************
 * @(#)OrderQueryAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaCapacityInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户订单查询
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
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
     * 资源规格
     */
    private String caseType;
    
    /**
     * 从页面获取查询条件开始时间
     */
    private String startTime;
    
    /**
     * 从页面获取查询条件结束时间
     */
    private String endTime;
    
    /**
     * 子订单ID
     */
    private String orderId;
    
    /**
     * 父订单ID
     */
    private String parentId;

    /**
     * 订单信息列表
     */
    private List<OrderInfo> orderInfoList;
    
    /**
     * item信息
     */
    private Map<String,Object> orderInfos;

    /**
     * 返回路径，用于在界面判断是否系统错误
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();
    
    /**
     * 调用同步翻页状态码 0为异步  非0为同步(1)
     */
    private final String SYNCCODE = "0";
    
    /**
     * 同步标志位 0为异步  非0为同步(1) sprin配置 默认异步可以不配置
     */
    private String syncFlage = "0";
    
    /**
	 * 资源池
	 */
	private List<StaCapacityInfo> pools = new ArrayList<StaCapacityInfo>();

	/**
     * 资源池ID
     */
    private String resPoolId;
    
    /**
     * 业务名称
     */
    private String appName;
    
    /**
     * 申请人
     */
    private String updateUser;
    
    @SuppressWarnings("unchecked")
    public String execute() {
    	try {
			pools = ibatisDAO.getData("getResPoolList", null);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单时失败，数据库异常！", e1);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
		}
        OrderQueryItem orderQuery = new OrderQueryItem();
        if (status != null && !status.equals("")) {
            orderQuery.setStatus(status.trim());
        } else {
            orderQuery.setStatus("0");
            status = "0";
        }
        if (caseType != null && !caseType.equals("")) {
            orderQuery.setCaseType(caseType.trim());
        }
        if (startTime != null && !"".equals(startTime)) {
            orderQuery.setStartTime(startTime.trim());
        } 
        if (endTime != null && !"".equals(endTime)) {
            orderQuery.setEndTime(endTime.trim());
        } 
        if (orderId != null && !"".equals(orderId)) {
            orderQuery.setOrderId(orderId.trim());
        }
        if (parentId != null && !"".equals(parentId)) {
            orderQuery.setParentId(parentId.trim());
        }
        if (resPoolId != null && !"".equals(resPoolId)) {
            orderQuery.setResPoolId(resPoolId.trim());
        }
        if (appName != null && !"".equals(appName)) {
            orderQuery.setAppName(appName.trim());
        }
        if (updateUser != null && !"".equals(updateUser)) {
            orderQuery.setUpdateUser(updateUser.trim());
        }
        queryOrder(orderQuery);
        // 查询条件是已过期“5”
        if ("5".equals(orderQuery.getStatus())) { // 查询已生效且已过期的订单且将状态置为已过期
            for (OrderInfo ordernfo : orderInfoList) {
                if ("5".equals(ordernfo.getStatus())) { // 已经是已过期状态，就不需要更新状态了，跳出执行下一条
                    continue;
                } else {
                    try {
                        ibatisDAO.updateData("updateOrderStatusInfo", ordernfo);
                    } catch (SQLException e) {
                        logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "更新订单状态为过期时失败，数据库异常！", e);
                        resultPath = ConstantEnum.ERROR.toString();
                        return ConstantEnum.ERROR.toString();
                    } 
                }
            }
            queryOrder(orderQuery); // 更新已过期状态后重查页面
        } 
        if (orderInfoList.size() == 0) {
            logger.info("用户没有申请任何订单");
            return ConstantEnum.SUCCESS.toString();
        }
        for (OrderInfo ordernfo : orderInfoList) {
            ordernfo.setUpdateTime(DateParse.parse(ordernfo.getUpdateTime()));
        } 
        logger.info("查询订单成功，共有" + orderInfoList.size() + "条订单");
        if (null != errMsg && !"".equals(errMsg)) {
        	if (null != errMsg && !"".equals(errMsg)) {
                this.addActionError(errMsg);
            }
            return ConstantEnum.ERROR.toString();
        }else{
        	return ConstantEnum.SUCCESS.toString();
        }
    }

    private String queryOrder(OrderQueryItem orderQuery) {
        try {
            if(SYNCCODE.equals(syncFlage)){
                // 要查询已过期的订单时，sql中有(STATUS != '0' and STATUS != '1' and STATUS != '2')，因为这些状态的过期时间为空，而为空的时间也会小于当前时间
                // 目前只有已生效的订单有过期时间，但不排除其他状态时也会有过期时间，且修改为过期状态后的订单也有过期时间，所以用不等于排除没有过期时间的记录
                orderInfoList = getPage("queryOrderInfoCount", "queryOrderInfoAll", orderQuery, PageTransModel.ASYNC);
            }else{
                orderInfoList = getPage("queryOrderInfoCount", "queryOrderInfoAll", orderQuery);
            }
            int orderInfoListCount = ibatisDAO.getCount("queryOrderInfoCount", orderQuery);
            orderInfos = new HashMap<String, Object>();
            orderInfos.put("listCount", orderInfoListCount);
            orderInfos.put("list", orderInfoList);
            orderInfos.put("page", getPageBar());
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单时失败，数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * setSyncFlage 方法 
     * @param syncFlage 方法
     */
    public void setSyncFlage(String syncFlage) {
        this.syncFlage = syncFlage;
    }


    public List<OrderInfo> getOrderInfoList() {
        return orderInfoList;
    }


    public void setOrderInfoList(List<OrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }


    public Map<String, Object> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(Map<String, Object> orderInfos) {
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
     * 
     * getStartTime 方法 
     * @return 方法
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * setStartTime 方法 
     * @param startTime 方法
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * getEndTime 方法 
     * @return 方法
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * setEndTime 方法 
     * @param endTime 方法
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
     * 获取parentId字段数据
     * @return Returns the parentId.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置parentId字段数据
     * @param parentId The parentId to set.
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	/**
	 * @return the pools
	 */
	public List<StaCapacityInfo> getPools() {
		return pools;
	}

	/**
	 * @param pools the pools to set
	 */
	public void setPools(List<StaCapacityInfo> pools) {
		this.pools = pools;
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
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the caseType
	 */
	public String getCaseType() {
		return caseType;
	}

	/**
	 * @param caseType the caseType to set
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
    
}
