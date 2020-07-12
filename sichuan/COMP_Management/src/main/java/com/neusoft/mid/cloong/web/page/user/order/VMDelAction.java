/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-14
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
import com.neusoft.mid.cloong.vm.core.VMDel;
import com.neusoft.mid.cloong.vm.core.VMStatus;
import com.neusoft.mid.cloong.vm.core.VmDeleteReq;
import com.neusoft.mid.cloong.vm.core.VmDeleteResp;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机删除操作 返回 json
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-14 上午10:08:55
 */
public class VMDelAction extends BaseAction {

    private static final long serialVersionUID = 1788639715970933829L;

    private static LogService logger = LogService.getLogger(VMDelAction.class);

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 跳转订单列表界面（删除成功重新执行查询操作）
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
     * 虚拟机已经被删除
     */

//    private static final String DETELTE = "2";

    /**
     * 虚拟机是否被删除的标准
     */
//    private static final String IS_DELETE = "0";

    /**
     * 流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 虚拟机删除
     */
    private VMDel vmDel;

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
    
    private static final String USERVMIDTEXT = "vm.del.user.vmid.text";

    private static final String FAILMESSAGE = "vm.del.fail.message";
    
    private String name;
    
    /**
     * tip  msg
     */
    private String msgTip = "订单对应虚拟机删除失败！数据库连接异常！";

    @Override
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        resultPath = GOTOLISTPAGE;
        // 判断订单对应的虚拟机已经被删除
        VMInstanceInfo vmInstanceInfo;
        try {
            vmInstanceInfo = (VMInstanceInfo)ibatisDAO.getSingleRecord("queryVmInstanceInfo", orderId);
            if(VMStatus.DELETED.equals(vmInstanceInfo.getStatus())) { // 订单对应的虚拟机已删除,要从数据库取，因为删除是在门户中删的，内存取不到 
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setStatus("6");
                orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                orderInfo.setOrderId(orderId);
                orderInfo.setUpdateUser(userId);
                ibatisDAO.updateData("updateOrderStatusDel", orderInfo);
                resultPath = RETURNNOWPAGE;
                mes = getText("vm.del.status.del.text");
                return ConstantEnum.FAILURE.toString();
            }
            name = vmInstanceInfo.getVmName();
            // 判断是否存在其他关联信息
            if (isConnect(userId, vmInstanceInfo.getVmId())) {
                return ConstantEnum.FAILURE.toString();
            }
            // 调用删除虚拟接口
            VmDeleteReq req = new VmDeleteReq();
            req.setVmId(vmInstanceInfo.getVmId());
            req.setResourcePoolId(vmInstanceInfo.getResPoolId());
            req.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
            req.setSerialNum(gen.generatorVMSerialNum("VMDel"));
            VmDeleteResp resp = vmDel.delVm(req);
            if(SUCCEESS_CODE.equals(resp.getResultCode())){
	            // 更新数据库 删除虚拟机, 且更新订单表状态为已失效
	            if (delVmDB(userId, vmInstanceInfo.getVmId())) {
	                return ConstantEnum.FAILURE.toString();
	            }
            }else{
            	logger.error(CommonStatusCode.RUNTIME_EXCEPTION, MessageFormat.format(
                        getText("vm.del.fail.message"), vmInstanceInfo.getVmId())
                        + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
                // 操作失败插入数据库
                // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
            	resultPath = RETURNNOWPAGE;
            	mes = getText("vm.del.fail.message");
                return ConstantEnum.FAILURE.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 更新数据库，将虚拟机状态变为删除。
     * @param userId 当前用户id
     * @return 成功 true 失败 false
     */
    private boolean delVmDB(String userId, String vmId) {
        VMInstanceInfo vmInfo = new VMInstanceInfo();
        vmInfo.setVmId(vmId);
        vmInfo.setStatus(VMStatus.DELETED);
        vmInfo.setUpdateUser(userId);
        vmInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        vmInfo.setOrderId(orderId);
        
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus("6");
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setOrderId(orderId);
        orderInfo.setUpdateUser(userId);
        
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        BatchVO batchInfoVm = new BatchVO("MOD", "updateVMStatusAndOwn", vmInfo);
        BatchVO batchInfoOrder = new BatchVO("MOD", "updateOrderStatusDel", orderInfo);
        batchVOs.add(batchInfoVm);
        batchVOs.add(batchInfoOrder);
        
        try {
        	ibatisDAO.updateBatchData(batchVOs);
            /*int upNum = ibatisDAO.updateData("updateVMStatusAndOwn", vmInfo);
            int orderNum = ibatisDAO.updateData("updateOrderStatusDel", orderInfo);
            if (upNum == 1 && orderNum == 1) {*/
                mes = getText("vm.del.sucess");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, vmId));
                    sb.append(getText("vm.del.debug.sucess"));
                    logger.debug(sb.toString());
                }
            /*} else {暂时没发现会出现这种情况
                resultPath = RETURNNOWPAGE;
                mes = getText("vm.del.status.del.text");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, vmId));
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
     * 查询虚拟机是否存在关联数据
     * @param userId
     * @return 不存在关联 false 存在关联 true
     */
    private boolean isConnect(String userId, String vmId) {
    	// 查询其关联是否已被删除
        StringBuffer res = new StringBuffer();
        int vhCount = 0;
        int vmBakCount = 0;
        try {
        	// 是否挂载虚拟硬盘
            vhCount = ibatisDAO.getCount("getCountEbsByVMid", vmId);
            // 是否有备份任务 
            //vmBakCount = ibatisDAO.getCount("getCountVmBakByVMid", vmId);
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
                res.append(getText("vm.del.vh.beings"));
            } else {
                res.append(getText("vm.del.vh.being"));
            }
        }
        if (vmBakCount > 0) {
            if (null == res || "".equals(res.toString())) {
                res.append(getText("vm.del.vmBak.beings"));
            } else {
                res.append(getText("vm.del.vmBak.being"));
            }
        }
        if (!"".equals(res.toString())) {
            res.append(getText("vm.del.connect.over.text"));
            mes = res.toString();
            resultPath = RETURNNOWPAGE;
            if (logger.isDebugEnable()) {
                StringBuilder sb = new StringBuilder();
                sb.append(getText(USERVMIDTEXT, userId, vmId));
                sb.append("\n").append(res);
                logger.debug(sb.toString());
            }
            return true;
        } else {
            return false;
        }
    }

    public VMDel getVmDel() {
        return vmDel;
    }

    public void setVmDel(VMDel vmDel) {
        this.vmDel = vmDel;
    }

    public String getMes() {
        return mes;
    }

    public String getResultPath() {
        return resultPath;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return the gen
	 */
	public SequenceGenerator getGen() {
		return gen;
	}

	/**
	 * @param gen the gen to set
	 */
	public void setGen(SequenceGenerator gen) {
		this.gen = gen;
	}
}
