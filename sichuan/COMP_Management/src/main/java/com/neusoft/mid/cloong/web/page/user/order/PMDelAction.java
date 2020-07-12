/*******************************************************************************
 * @(#)PMDelAction.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.pm.core.PMDel;
import com.neusoft.mid.cloong.pm.core.PMStatus;
import com.neusoft.mid.cloong.pm.core.PmDeleteReq;
import com.neusoft.mid.cloong.pm.core.PmDeleteResp;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机删除操作 返回 json
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:11:00
 */
public class PMDelAction extends BaseAction {

    private static final long serialVersionUID = 1788639715970933829L;

    private static LogService logger = LogService.getLogger(PMDelAction.class);

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 物理机操作状态判断服务
     */
    private PMStatusService pmStatusService;

    /**
     * 跳转物理机列表界面
     */
    private static final String GOTOLISTPAGE = "1";

    /**
     * 当前页面（已删除或者失败的操作）
     */
    private static final String RETURNNOWPAGE = "0";

    /**
     * 跳转出错界面
     */
//    private static final String GOTOERROR = "-1";

    /**
     * 物理机已经被删除
     */

//    private static final String DETELTE = "2";

    /**
     * 物理机是否被删除的标准
     */
//    private static final String IS_DELETE = "0";

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 物理机删除
     */
    private PMDel pmDel;

    /**
     * 获取订单号
     */
    private String orderId;

    /**
     * 返回标识
     */
    private String resultPath;

    /**
     * 返回提示消息
     */
    private String mes;

    // 读取配置文件 时多次使用的字符串.声明成变量
    
    private static final String USERVMIDTEXT = "pm.del.user.pmid.text";

    private static final String FAILMESSAGE = "pm.del.fail.message";
    
    /**
     * tip  msg
     */
    private String msgTip = "订单对应物理机删除失败！数据库连接异常！";

    private String name;
    
    @Override
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        resultPath = GOTOLISTPAGE;
        // 判断订单对应的物理机已经被删除
        PMInstanceInfo pmInstanceInfo;
        try {
            pmInstanceInfo = (PMInstanceInfo)ibatisDAO.getSingleRecord("queryPmInstanceInfo", orderId);
            if(PMStatus.DELETED.equals(pmInstanceInfo.getStatus())) { // 订单对应的物理机已删除,要从数据库取，因为删除是在门户中删的，内存取不到 
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setStatus("6");//TODO 已失效
                orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                orderInfo.setOrderId(orderId);
                orderInfo.setUpdateUser(userId);
                ibatisDAO.updateData("updateOrderStatusDel", orderInfo);
                resultPath = RETURNNOWPAGE;
                mes = getText("pm.del.status.del.text");
                return ConstantEnum.FAILURE.toString();
            }
            name = pmInstanceInfo.getPmName();
            // 判断是否存在其他关联信息
            if (isConnect(userId, pmInstanceInfo.getPmId())) {
                return ConstantEnum.FAILURE.toString();
            }
            
            PmDeleteReq req = new PmDeleteReq();
            req.setPmId(pmInstanceInfo.getPmId());
            req.setResourcePoolId(pmInstanceInfo.getResPoolId());
            req.setResourcePoolPartId(pmInstanceInfo.getResPoolPartId());
            req.setSerialNum(gen.generatorPMSerialNum("PMDel"));
            PmDeleteResp resp = pmDel.delPm(req);
//            resp.setResultCode(SUCCEESS_CODE);
            if(SUCCEESS_CODE.equals(resp.getResultCode())){
	            // 更新数据库 删除物理机, 且更新订单表状态为已失效
	            if (delPmDB(userId, pmInstanceInfo.getPmId())) {
	                return ConstantEnum.FAILURE.toString();
	            }
            }else{
            	logger.error(CommonStatusCode.RUNTIME_EXCEPTION, MessageFormat.format(
                        getText("pm.del.fail.message"), pmInstanceInfo.getPmId())
                        + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
                // 操作失败插入数据库
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
            	resultPath = RETURNNOWPAGE;
            	mes = getText("pm.del.fail.message");
                return ConstantEnum.FAILURE.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 更新数据库，将物理机状态变为删除。
     * @param userId 当前用户id
     * @return 成功 true 失败 false
     */
    private boolean delPmDB(String userId, String pmId) {
        PMInstanceInfo pmInfo = new PMInstanceInfo();
        pmInfo.setPmId(pmId);
        pmInfo.setStatus(PMStatus.DELETED);
        pmInfo.setOwnUser(userId);
        pmInfo.setUpdateUser(userId);
        pmInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        pmInfo.setOrderId(orderId);
        
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus("6");//TODO 已失效
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setOrderId(orderId);
        orderInfo.setUpdateUser(userId);
        
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        BatchVO batchInfoPm = new BatchVO("MOD", "updatePMStatusAndOwn", pmInfo);
        BatchVO batchInfoOrder = new BatchVO("MOD", "updateOrderStatusDel", orderInfo);
        batchVOs.add(batchInfoPm);
        batchVOs.add(batchInfoOrder);
        
        try {
        	ibatisDAO.updateBatchData(batchVOs);
            /*int upNum = ibatisDAO.updateData("updatePMStatusAndOwn", pmInfo);
            int orderNum = ibatisDAO.updateData("updateOrderStatusDel", orderInfo);
            if (upNum == 1 && orderNum == 1) {*/
                mes = getText("pm.del.sucess");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, pmId));
                    sb.append(getText("pm.del.debug.sucess"));
                    logger.debug(sb.toString());
                }
            /*} else {
                resultPath = RETURNNOWPAGE;
                mes = getText("pm.del.status.del.text");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, pmId));
                    sb.append(getText(FAILMESSAGE));
                    logger.debug(sb.toString());
                }
                return true;
            }*/
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,  msgTip, e);
            this.addActionError( msgTip);
            resultPath = RETURNNOWPAGE;
            mes = getText(FAILMESSAGE);
            return true;
        }
        return false;
    }

    /**
     * 查询物理机是否存在关联数据
     * @param userId
     * @return 不存在关联 false 存在关联 true
     */
    private boolean isConnect(String userId, String pmId) {
    	// 查询其关联是否已被删除
        StringBuffer res = new StringBuffer();
        int vhCount = 0;
        try {
        	// 是否挂载物理硬盘
            vhCount = ibatisDAO.getCount("getCountEbsByVMid", pmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,  msgTip, e);
            this.addActionError(msgTip);
            resultPath = RETURNNOWPAGE;
            mes = getText(FAILMESSAGE);
            return true;
            // return ConstantEnum.FAILURE.toString();
        }
        if (vhCount > 0) {
            if (null == res || "".equals(res.toString())) {
                res.append(getText("pm.del.vh.beings"));
            } else {
                res.append(getText("pm.del.vh.being"));
            }
        }
        if (!"".equals(res.toString())) {
            res.append(getText("pm.del.connect.over.text"));
            mes = res.toString();
            resultPath = RETURNNOWPAGE;
            if (logger.isDebugEnable()) {
                StringBuilder sb = new StringBuilder();
                sb.append(getText(USERVMIDTEXT, userId, pmId));
                sb.append("\n").append(res);
                logger.debug(sb.toString());
            }
            return true;
        } else {
            return false;
        }
    }

    public PMDel getPmDel() {
        return pmDel;
    }

    public void setPmDel(PMDel pmDel) {
        this.pmDel = pmDel;
    }

    public String getMes() {
        return mes;
    }

    public String getResultPath() {
        return resultPath;
    }

    public PMStatusService getPmStatusService() {
        return pmStatusService;
    }

    public void setPmStatusService(PMStatusService pmStatusService) {
        this.pmStatusService = pmStatusService;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
