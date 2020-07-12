/*******************************************************************************
 * @(#)DiskDeleteAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.ebs.core.EBSDelete;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除虚拟硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-21 下午05:53:57
 */
public class DiskDeleteAction extends BaseAction {

    private static final long serialVersionUID = 4407178327415405154L;

    private static LogService logger = LogService.getLogger(DiskDeleteAction.class);

    /**
     * 获取订单号
     */
    private String orderId;

    /**
     * 跳转订单列表界面（删除成功重新执行查询操作）
     */
    private static final String GOTOLISTPAGE = "1";

    /**
     * 当前页面（已删除或者失败的操作）
     */
    private static final String RETURNNOWPAGE = "0";

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 返回消息
     */
//    private String resultMessage = "";

    /**
     * 虚拟硬盘删除接口
     */
    private EBSDelete delete;
    
    /**
     * 返回标识
     */
    private String resultPath;
    
    /**
     * 返回提示消息
     */
    private String mes;
    
    private String name;
    
    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    public String execute() {
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        resultPath = GOTOLISTPAGE;
        // 判断订单对应的虚拟硬盘已经被删除
        DiskInfo diskInfo = null;
        try {
            diskInfo = (DiskInfo) ibatisDAO.getSingleRecord("qureyDiskInfo", orderId);
            if (diskInfo == null) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单ID为[" + orderId
                        + "]的硬盘订单信息查询失败", null);
                resultPath = RETURNNOWPAGE;
                return ConstantEnum.FAILURE.toString();
            }
            if("10".equals(diskInfo.getDiskStatus())) { // 订单对应的虚拟硬盘已删除,要从数据库取，因为删除是在门户中删的，内存取不到 
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setStatus("6");
                orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                orderInfo.setOrderId(orderId);
                orderInfo.setUpdateUser(userId);
                ibatisDAO.updateData("updateOrderStatusDel", orderInfo);
                resultPath = RETURNNOWPAGE;
                mes = getText("vmdisk.del.status.del.text");
                return ConstantEnum.FAILURE.toString();
            }
            name = diskInfo.getDiskName();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "硬盘ID为[" + diskInfo.getDiskId()
                    + "]的虚拟机所有者失败", e);
            resultPath = RETURNNOWPAGE;
            return ConstantEnum.FAILURE.toString();
        }
        if (diskInfo.getDiskId() == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "硬盘编码为空");
            resultPath = RETURNNOWPAGE;
            return ConstantEnum.FAILURE.toString();
        }
        
        if ("4".equals(diskInfo.getDiskStatus())) { // 已挂载
            logger.info(MessageFormat.format(getText("disk.delete.detach.first"), diskInfo.getDiskId(), diskInfo
                    .getMountVmName()));
            mes = MessageFormat.format(getText("disk.delete.detach.first"), diskInfo.getDiskId(),
                    diskInfo.getMountVmName());
            resultPath = RETURNNOWPAGE;
            return ConstantEnum.FAILURE.toString();
        }
        RPPEBSDeleteReq req = new RPPEBSDeleteReq();
        req.setEbsId(diskInfo.getDiskId());
        req.setResourcePoolId(diskInfo.getResourcePoolId());
        req.setResourcePoolPartId(diskInfo.getResourcePoolPartId());
        req.setSerialNum(seqCen.generatorSerialNum());
        RPPEBSDeleteResp resp = new RPPEBSDeleteResp();
        resp = delete.delete(req);
        //resp.setResultCode(SUCCEESS_CODE);
        if (!SUCCEESS_CODE.equals(resp.getResultCode())) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟机硬盘删除失败，接口错误", null);
            mes = MessageFormat.format(getText("disk.delete.fail"), diskInfo);
            resultPath = RETURNNOWPAGE;
            return ConstantEnum.FAILURE.toString();
        }
        diskInfo.setDiskStatus("10");
        diskInfo.setUpdateUser(userId);
        diskInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        diskInfo.setOrderId(orderId);
        
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus("6");
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setOrderId(orderId);
        orderInfo.setUpdateUser(userId);   
        //int updateResult = 0;
        //int orderNum = 0;
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
        BatchVO batchInfoDisk = new BatchVO("MOD", "deleteDisk", diskInfo);
        BatchVO batchInfoOrder = new BatchVO("MOD", "updateOrderStatusDel", orderInfo);
        batchVOs.add(batchInfoDisk);
        batchVOs.add(batchInfoOrder);
        try {    
            //updateResult = ibatisDAO.updateData("deleteDisk", diskInfo);
            //orderNum = ibatisDAO.updateData("updateOrderStatusDel", orderInfo);
            ibatisDAO.updateBatchData(batchVOs);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("diskname.update.fail"), diskInfo), e);
            this.addActionError(MessageFormat.format(getText("disk.delete.fail"), diskInfo));
            resultPath = RETURNNOWPAGE;
            return ConstantEnum.FAILURE.toString();
        }
        /*if (updateResult != 1 && orderNum != 1) {暂时没发现会出现这种情况
            logger.info(MessageFormat.format(getText("disk.query.notexist"), diskInfo));
            mes = MessageFormat.format(getText("disk.query.notexist"), diskInfo);
            resultPath = RETURNNOWPAGE;
            return ConstantEnum.FAILURE.toString();
        }*/
        StringBuilder sb = new StringBuilder();
        sb.append("删除编码为[").append(diskInfo).append("]的硬盘成功！");
        logger.info(sb.toString());
        resultPath = RETURNNOWPAGE;
        resultPath = GOTOLISTPAGE;
        return ConstantEnum.SUCCESS.toString();
    }

//    public String getResultMessage() {
//        return resultMessage;
//    }
//
//    public void setResultMessage(String resultMessage) {
//        this.resultMessage = resultMessage;
//    }

    public void setDelete(EBSDelete delete) {
        this.delete = delete;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return the seqCen
	 */
	public CommonSequenceGenerator getSeqCen() {
		return seqCen;
	}

	/**
	 * @param seqCen the seqCen to set
	 */
	public void setSeqCen(CommonSequenceGenerator seqCen) {
		this.seqCen = seqCen;
	}

	/**
	 * @return the delete
	 */
	public EBSDelete getDelete() {
		return delete;
	}
    
}
