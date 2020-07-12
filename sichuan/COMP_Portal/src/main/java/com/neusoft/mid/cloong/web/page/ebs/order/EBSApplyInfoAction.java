/*******************************************************************************
 * @(#)EBSApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.ebs.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSCreate;
import com.neusoft.mid.cloong.ebs.core.EBSCreateFail;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.cloong.ebs.core.EBSStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.ebs.order.info.EBSInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交申请云硬盘订单
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:18:41
 */
public class EBSApplyInfoAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -3246558237673423698L;

    private static LogService logger = LogService.getLogger(EBSApplyInfoAction.class);

    private String itemId;

    private String standardId;

    private String lengthTime;

    private String respoolId;

    private String respoolPartId;

    private String respoolName;

    private String respoolPartName;

    private String ebsName;

    private String discSize;
    
    private String resourceType;
    /**
     * 收费类型.
     */
    private String chargeType;
    
    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    private SequenceGenerator sequenceGenerator;

    /**
     * 云硬盘创建接口
     */
    private EBSCreate ebsCreate;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit;

    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    @Override
    public String execute() {
        if (logger.isDebugEnable()) {
            logger.debug("itemId = " + itemId);
            logger.debug("standardId = " + standardId);
            logger.debug("lengthTime = " + lengthTime);
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
            logger.debug("respoolName = " + respoolName);
            logger.debug("respoolPartName = " + respoolPartName);
            logger.debug("ebsName = " + ebsName);
            logger.debug("appId = " + appId);
            logger.debug("resourceType = " + resourceType);
        }
        // TODO: 记入操作日志

        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();

        RPPEBSCreateReq ebsReq = new RPPEBSCreateReq();

        // 拼凑订单信息
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        OrderInfo orderInfo = new OrderInfo();

        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.BS.getParentCode());
        String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.BS.toString());
        String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.BS.toString());
        // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
        assembleOrder(userId, orderInfo, orderId, caseId);
        orderInfo.setParentId(parentId);// 同一个订单父ID
        if (logger.isDebugEnable()) {
            logger.debug("orderId = " + orderId);
            logger.debug("caseId = " + caseId);
            logger.debug("parentId = " + parentId);
        }

        RPPEBSCreateResp resp = new RPPEBSCreateResp();
        resp.setResultCode(SUCCEESS_CODE);
        if (AUTO.equals(audit)) {
            // 自动审批
            // 发送申请订单至接口
            assembleReq(ebsReq);
            resp = ebsCreate.createEBS(ebsReq);
        }
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，云硬盘订单和实例入库

            // 拼凑实例信息
            try {
                /* 当云硬盘申请成功时,订单置生效时间 */
                // orderInfo.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                /*
                 * if (orderInfo.getLengthUnit().equals("h")) {
                 * orderInfo.setExpireTime(DateParse.addHour(orderInfo.getEffectiveTime(),
                 * Integer.valueOf(orderInfo.getLengthTime()))); } if
                 * (orderInfo.getLengthUnit().equals("m")) {
                 * orderInfo.setExpireTime(DateParse.addMonth(orderInfo.getEffectiveTime(),
                 * Integer.valueOf(orderInfo.getLengthTime()))); } if
                 * (orderInfo.getLengthUnit().equals("d")) {
                 * orderInfo.setExpireTime(DateParse.addDay(orderInfo.getEffectiveTime(),
                 * Integer.valueOf(orderInfo.getLengthTime()))); } if
                 * (orderInfo.getLengthUnit().equals("y")) {
                 * orderInfo.setExpireTime(DateParse.addYear(orderInfo.getEffectiveTime(),
                 * Integer.valueOf(orderInfo.getLengthTime()))); }
                 */
                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createEBSOrder", orderInfo));

                // 拼凑EBS实例信息
                EBSInstanceInfo ebsInstanceInfo = new EBSInstanceInfo();
                if (AUTO.equals(audit)) {
                    assembleEBSInstance(userId, orderInfo, resp, ebsInstanceInfo);
                } else {
                    assembleEBSInstanceAudit(userId, orderInfo, ebsInstanceInfo);
                }

                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createEBSInstanceInfo", ebsInstanceInfo));
                if (logger.isDebugEnable()) {
                    logger.debug("拼凑订单信息完成！");
                }
            } catch (Exception e2) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(EBSStatusCode.CREATEEBSORDER_EXCEPTION_CODE,
                        getText("ebs.applyinfo.fail"), e2);
                this.addActionError(getText("ebs.applyinfo.fail"));
                errMsg = getText("ebs.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
            try {
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("申请云硬盘订单记入数据库成功！");
                }
            } catch (SQLException e) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(EBSStatusCode.CREATEEBSORDER_EXCEPTION_CODE,
                        getText("ebs.applyinfo.fail"), e);
                this.addActionError(getText("ebs.applyinfo.fail"));
                errMsg = getText("ebs.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            } catch (Exception e2) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(EBSStatusCode.CREATEEBSORDER_EXCEPTION_CODE,
                        getText("ebs.applyinfo.fail"), e2);
                this.addActionError(getText("ebs.applyinfo.fail"));
                errMsg = getText("ebs.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建云硬盘发送申请失败！");
            this.addActionError(getText("ebs.applyinfo.sendcreateEBS.fail"));
            errMsg = getText("ebs.applyinfo.sendcreateEBS.fail");
            // 入失败库
            List<EBSCreateFail> ebsCreateFails = assembleEBSCreateFail(ebsReq, resp, userId);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (EBSCreateFail ebsCreateFail : ebsCreateFails) {
                insertFailBatchVO.add(new BatchVO("ADD", "insertEBSFail", ebsCreateFail));
            }
            try {
                ibatisDAO.updateBatchData(insertFailBatchVO);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请云硬盘失败后入失败库发生数据库异常",
                        e);
                return ConstantEnum.FAILURE.toString();
            } catch (Exception e2) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请云硬盘失败后入失败库发生数据库异常",
                        e2);
                return ConstantEnum.FAILURE.toString();
            }
            return ConstantEnum.FAILURE.toString();
        }

        if (logger.isDebugEnable()) {
            logger.debug("创建云硬盘发送申请成功！");
        }

        // TODO：出话单
        msg = "创建云硬盘发送申请成功！";
        return ConstantEnum.SUCCESS.toString();
    }

    private void assembleReq(RPPEBSCreateReq ebsReq) {
        /* 模板创建 */
        ebsReq.setCreateModel(EBSCreateModel.template);
        ebsReq.setResourcePoolId(respoolId);
        ebsReq.setResourcePoolPartId(respoolPartId);
        ebsReq.setStandardId(standardId);
        ebsReq.setEbsName(ebsName);
        ebsReq.setAppId(appId);
        ebsReq.setAppName(appName);
        ebsReq.setSerialNum(seqCen.generatorSerialNum());
    }

    private List<EBSCreateFail> assembleEBSCreateFail(RPPEBSCreateReq tempEBSReq,
            RPPEBSCreateResp ebsResp, String userId) {
        List<EBSCreateFail> ebsCreateFails = new ArrayList<EBSCreateFail>();
        EBSCreateFail ebsCreateFail = new EBSCreateFail();
        ebsCreateFail.setFailCause(ebsResp.getResultMessage());
        ebsCreateFail.setFailCode(ebsResp.getResultCode());
        ebsCreateFail.setResPoolId(tempEBSReq.getResourcePoolId());
        ebsCreateFail.setResPoolPartId(tempEBSReq.getResourcePoolPartId());
        ebsCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        ebsCreateFail.setStandardId(tempEBSReq.getStandardId());
        ebsCreateFail.setEbsName(tempEBSReq.getEbsName());
        ebsCreateFail.setCreateUser(userId);
        ebsCreateFails.add(ebsCreateFail);
        return ebsCreateFails;
    }

    private void assembleEBSInstance(String userId, OrderInfo orderInfo, RPPEBSCreateResp resp,
            EBSInstanceInfo ebsInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        ebsInstanceInfo.setCaseId(orderInfo.getCaseId());
        ebsInstanceInfo.setEbsId(resp.getBSIDs().get(0)); // 按模板创建只能创建一个块
        ebsInstanceInfo.setAcceptTime(time);
        ebsInstanceInfo.setItemId(itemId);
        ebsInstanceInfo.setStandardId(standardId);
        ebsInstanceInfo.setEbsName(ebsName);
        ebsInstanceInfo.setStatus(EBSStatus.CREATING);
        ebsInstanceInfo.setResPoolId(respoolId);
        ebsInstanceInfo.setResPoolPartId(respoolPartId);
        ebsInstanceInfo.setOrderId(orderInfo.getOrderId());
        ebsInstanceInfo.setCreateTime(time);
        ebsInstanceInfo.setCreateUser(userId);
        ebsInstanceInfo.setUpdateTime(time);
        ebsInstanceInfo.setUpdateUser(userId);
        ebsInstanceInfo.setHostId("");
        ebsInstanceInfo.setDescription("");
        ebsInstanceInfo.setExpireTime("");
        ebsInstanceInfo.setAppId(appId);
        ebsInstanceInfo.setDiskSize(discSize);
        ebsInstanceInfo.setResourceType(resourceType);
    }

    /**
     * 为人工审批时做准备，为保存资源池id资源池分区id
     * @param userId 用户id
     * @param orderInfo 订单信息
     * @param ebsInstanceInfo ebs实例
     */
    private void assembleEBSInstanceAudit(String userId, OrderInfo orderInfo,
            EBSInstanceInfo ebsInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        ebsInstanceInfo.setCaseId(orderInfo.getCaseId());
        ebsInstanceInfo.setAcceptTime(time);
        ebsInstanceInfo.setItemId(itemId);
        ebsInstanceInfo.setStandardId(standardId);
        ebsInstanceInfo.setEbsName(ebsName);
        ebsInstanceInfo.setStatus(EBSStatus.PREPARE);
        ebsInstanceInfo.setResPoolId(respoolId);
        ebsInstanceInfo.setResPoolPartId(respoolPartId);
        ebsInstanceInfo.setOrderId(orderInfo.getOrderId());
        ebsInstanceInfo.setCreateTime(time);
        ebsInstanceInfo.setCreateUser(userId);
        ebsInstanceInfo.setUpdateTime(time);
        ebsInstanceInfo.setUpdateUser(userId);
        ebsInstanceInfo.setAppId(appId);
        ebsInstanceInfo.setDiskSize(discSize);
        ebsInstanceInfo.setResourceType(resourceType);
    }

    private void assembleOrder(String userId, OrderInfo orderInfo, String orderId, String caseId) {
        orderInfo.setOrderId(orderId);
        if (AUTO.equals(audit)) {
            orderInfo.setStatus("1");
        } else {
            orderInfo.setStatus("0");
        }
        //String[] templengthTime = lengthTime.split("_");
        orderInfo.setLengthTime(lengthTime);
        orderInfo.setLengthUnit(chargeType);
        orderInfo.setCaseId(caseId);
        orderInfo.setCaseType("5");
        orderInfo.setAppId(appId);
        orderInfo.setResPoolId(respoolId);
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getLengthTime() {
        return lengthTime;
    }

    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public String getRespoolId() {
        return respoolId;
    }

    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    public String getRespoolPartId() {
        return respoolPartId;
    }

    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
    }

    public String getRespoolName() {
        return respoolName;
    }

    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    public String getRespoolPartName() {
        return respoolPartName;
    }

 

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    public EBSCreate getEbsCreate() {
        return ebsCreate;
    }

    public void setEbsCreate(EBSCreate ebsCreate) {
        this.ebsCreate = ebsCreate;
    }

    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    public String getEbsName() {
        return ebsName;
    }

    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    /**
     * getAudit 是否人工审批
     * @return 是否人工审批
     */
    public String getAudit() {
        return audit;
    }

    /**
     * setAudit 是否人工审批
     * @param audit 是否人工审批
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取seqCen字段数据
     * @return Returns the seqCen.
     */
    public CommonSequenceGenerator getSeqCen() {
        return seqCen;
    }

    /**
     * 设置seqCen字段数据
     * @param seqCen The seqCen to set.
     */
    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取discSize字段数据
     * @return Returns the discSize.
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * 设置discSize字段数据
     * @param discSize The discSize to set.
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

}
