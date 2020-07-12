/*******************************************************************************
 * @(#)EBSApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.vFirewall.order;

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
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.publicIp.core.PublicIpStatus;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.CreateVirFwBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationResponse;
import com.neusoft.mid.cloong.vfw.core.CreateVirFW;
import com.neusoft.mid.cloong.vfw.core.VFWCreateFail;
import com.neusoft.mid.cloong.vfw.core.VfwStatus;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.ip.order.info.PublicIpInstanceInfo;
import com.neusoft.mid.cloong.web.page.vFirewall.order.info.VfwInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交申请vfirewall订单
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:18:41
 */
public class VfwApplyInfoAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -3246558237673423698L;

    private static LogService logger = LogService.getLogger(VfwApplyInfoAction.class);

    private String respoolId;

    private String respoolPartId;

    private String respoolName;

    private String respoolPartName;

    private String resourceType;

    private String description;

    private String fwName;

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
     * 虚拟防火墙创建接口
     */
    private CreateVirFW virfwClient;

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

    /**
     * 数量
     */
    private String num = "1";

    @Override
    public String execute() {
        if (logger.isDebugEnable()) {
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
            logger.debug("respoolName = " + respoolName);
            logger.debug("respoolPartName = " + respoolPartName);
            logger.debug("appId = " + appId);
            logger.debug("resourceType = " + resourceType);
        }
        // TODO: 记入操作日志

        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        // 拼凑订单信息
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.FW.getParentCode());

        for (int i = 0; i < Integer.valueOf(num); i++) {
            OrderInfo orderInfo = new OrderInfo();
            String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.FW.toString());
            String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.FW.toString());
            // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
            assembleOrder(userId, orderInfo, orderId, caseId);
            orderInfo.setParentId(parentId);// 同一个订单父ID
            orderInfos.add(orderInfo);
            if (logger.isDebugEnable()) {
                logger.debug("orderId = " + orderId);
                logger.debug("caseId = " + caseId);
                logger.debug("parentId = " + parentId);
            }
        }
        for (int i = 0; i < orderInfos.size(); i++) {
            // 拼凑订单信息
            List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
            RPPApplyVirfwOperationRequst vfwReq = new RPPApplyVirfwOperationRequst();
            RPPApplyVirfwOperationResponse resp = new RPPApplyVirfwOperationResponse();
            resp.setResultCode(SUCCEESS_CODE);
            if (AUTO.equals(audit)) {
                // 自动审批
                // 发送申请订单至接口
                assembleReq(vfwReq);
                resp = virfwClient.createVirFw(vfwReq);
            }
            if (resp.getResultCode().equals(SUCCEESS_CODE)) {

                // 拼凑实例信息
                try {

                    // 更新数据库
                    updateBatchVO.add(new BatchVO("ADD", "createVfwOrder", orderInfos.get(i)));

                    // 拼凑IP实例信息
                    VfwInstanceInfo vfwInstanceInfo = new VfwInstanceInfo();
                    vfwInstanceInfo.setDescription(description);
                    vfwInstanceInfo.setFwName(fwName);
                    if (AUTO.equals(audit)) {
                        assembleVfwInstance(userId, orderInfos.get(i), resp, vfwInstanceInfo);
                    } else {
                        assembleVfwInstanceAudit(userId, orderInfos.get(i), vfwInstanceInfo);
                    }

                    // 更新数据库
                    updateBatchVO.add(new BatchVO("ADD", "createVfwInstanceInfo", vfwInstanceInfo));

                    if (logger.isDebugEnable()) {
                        logger.debug("拼凑订单信息完成！");
                    }
                } catch (Exception e2) {
                    this.addActionError(getText("vfw.applyinfo.fail"));
                    errMsg = getText("vfw.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                }
                try {
                    ibatisDAO.updateBatchData(updateBatchVO);
                    if (logger.isDebugEnable()) {
                        logger.debug("申请虚拟防火墙订单记入数据库成功！");
                    }
                } catch (SQLException e) {
                    this.addActionError(getText("vfw.applyinfo.fail"));
                    errMsg = getText("vfw.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                } catch (Exception e2) {
                    this.addActionError(getText("vfw.applyinfo.fail"));
                    errMsg = getText("vfw.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                }
            } else {
                // 返回失败，入失败库
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建公有ip发送申请失败！");
                this.addActionError(getText("vfw.applyinfo.sendcreateVFW.fail"));
                errMsg = getText("vfw.applyinfo.sendcreateVFW.fail");
                // 入失败库
                VFWCreateFail vfwCreateFail = assembleVFWCreateFail(vfwReq, resp, userId);
                List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
                insertFailBatchVO.add(new BatchVO("ADD", "insertVFWCreateFail", vfwCreateFail));
                try {
                    ibatisDAO.updateBatchData(insertFailBatchVO);
                } catch (SQLException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            "申请公有ip失败后入失败库发生数据库异常", e);
                    return ConstantEnum.FAILURE.toString();
                } catch (Exception e2) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            "申请公有ip失败后入失败库发生数据库异常", e2);
                    return ConstantEnum.FAILURE.toString();
                }
                return ConstantEnum.FAILURE.toString();
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("创建虚拟防火墙发送申请成功！");
        }

        // TODO：出话单
        msg = "创建虚拟防火墙发送申请成功！";
        return ConstantEnum.SUCCESS.toString();
    }

    private void assembleReq(RPPApplyVirfwOperationRequst vfwReq) {
        /* 模板创建 */
        vfwReq.setAppID(appId);
        vfwReq.setAppName(appName);
        vfwReq.setFwName(fwName);
        vfwReq.setResourcePoolId(respoolId);
        vfwReq.setResourcePoolPartId(respoolPartId);
    }

    private VFWCreateFail assembleVFWCreateFail(RPPApplyVirfwOperationRequst vfwReq,
            RPPApplyVirfwOperationResponse vfwResp, String userId) {
        VFWCreateFail vfwCreateFail = new VFWCreateFail();
        vfwCreateFail.setFailCause(vfwResp.getResultMessage());
        vfwCreateFail.setFailCode(vfwResp.getResultCode());
        vfwCreateFail.setResPoolId(vfwReq.getResourcePoolId());
        vfwCreateFail.setResPoolPartId(vfwReq.getResourcePoolPartId());
        vfwCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        vfwCreateFail.setAppId(vfwReq.getAppID());
        vfwCreateFail.setNum(num);
        vfwCreateFail.setFwName(vfwReq.getFwName());
        vfwCreateFail.setCreateUser(userId);
        return vfwCreateFail;
    }

    private void assembleVfwInstance(String userId, OrderInfo orderInfo,
            RPPApplyVirfwOperationResponse resp, VfwInstanceInfo vfwInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        vfwInstanceInfo.setCaseId(orderInfo.getCaseId());
        vfwInstanceInfo.setStatus(VfwStatus.PREPARE);
        vfwInstanceInfo.setResPoolId(respoolId);
        vfwInstanceInfo.setResPoolPartId(respoolPartId);
        vfwInstanceInfo.setOrderId(orderInfo.getOrderId());
        vfwInstanceInfo.setCreateTime(time);
        vfwInstanceInfo.setCreateUser(userId);
        vfwInstanceInfo.setUpdateTime(time);
        vfwInstanceInfo.setUpdateUser(userId);
        vfwInstanceInfo.setHostId("");
        vfwInstanceInfo.setDescription("");
        vfwInstanceInfo.setExpireTime("");
        vfwInstanceInfo.setAppId(appId);
        vfwInstanceInfo.setResourceType(resourceType);
    }

    /**
     * 为人工审批时做准备，为保存资源池id资源池分区id
     * @param userId 用户id
     * @param orderInfo 订单信息
     * @param public ip实例
     */
    private void assembleVfwInstanceAudit(String userId, OrderInfo orderInfo,
            VfwInstanceInfo vfwInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        vfwInstanceInfo.setCaseId(orderInfo.getCaseId());
        vfwInstanceInfo.setStatus(VfwStatus.PREPARE);
        vfwInstanceInfo.setResPoolId(respoolId);
        vfwInstanceInfo.setResPoolPartId(respoolPartId);
        vfwInstanceInfo.setOrderId(orderInfo.getOrderId());
        vfwInstanceInfo.setCreateTime(time);
        vfwInstanceInfo.setCreateUser(userId);
        vfwInstanceInfo.setUpdateTime(time);
        vfwInstanceInfo.setUpdateUser(userId);
        vfwInstanceInfo.setAppId(appId);
        vfwInstanceInfo.setResourceType(resourceType);
    }

    private void assembleOrder(String userId, OrderInfo orderInfo, String orderId, String caseId) {
        orderInfo.setOrderId(orderId);
        if (AUTO.equals(audit)) {
            orderInfo.setStatus("1");
        } else {
            orderInfo.setStatus("0");
        }
        // String[] templengthTime = lengthTime.split("_");
        orderInfo.setCaseId(caseId);
        orderInfo.setCaseType("16");
        orderInfo.setAppId(appId);
        orderInfo.setResPoolId(respoolId);
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);

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

    public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFwName() {
        return fwName;
    }

    public void setFwName(String fwName) {
        this.fwName = fwName;
    }

    public CreateVirFW getVirfwClient() {
        return virfwClient;
    }

    public void setVirfwClient(CreateVirFW virfwClient) {
        this.virfwClient = virfwClient;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
