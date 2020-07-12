package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 订单审批
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-29 下午01:35:45
 */

public class OrderAidutAction extends BaseAction {

	private static final long serialVersionUID = 4196853216651241774L;

	private static LogService logger = LogService.getLogger(OrderAidutAction.class);

    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 是否通过审批 1：通过 2：不通过
     */
    private String pass;
    
    /**
     * 审批意见
     */
    private String auditInfo;
    
    /**
     * 订单实体
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

    public String execute() {
        orderInfo = new OrderInfo();
    	orderInfo.setOrderId(orderId);
    	orderInfo.setStatus(pass);
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        orderAuditInfo = new OrderAuditInfo();
        orderAuditInfo.setAuditUser(userId);
        orderAuditInfo.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderAuditInfo.setOrderId(orderId);
        orderAuditInfo.setStatus(pass);
        orderAuditInfo.setAuditInfo(auditInfo);
        try {
        	List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        	BatchVO batchVOorderInfo = new BatchVO("MOD","updateOrderInfo",orderInfo);
        	BatchVO batchVOorderAuditInfo = new BatchVO("ADD","updateOrderAuditInfo",orderAuditInfo);
        	batchVOs.add(batchVOorderInfo);
        	batchVOs.add(batchVOorderAuditInfo);
        	ibatisDAO.updateBatchData(batchVOs);
           
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
                    + "审批时，操作数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
                    + "审批时，操作数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    

    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

	public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
		this.orderAuditInfo = orderAuditInfo;
	}
}
