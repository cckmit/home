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
 * ????????????vfirewall??????
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 ??????04:18:41
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
     * ??????ID
     */
    private String appId;

    /**
     * ????????????
     */
    private String appName;

    private SequenceGenerator sequenceGenerator;

    /**
     * ???????????????????????????
     */
    private CreateVirFW virfwClient;

    /**
     * ????????????????????????
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * ????????? yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * ???????????????????????? 0????????? 1?????????
     */
    private String audit;

    /**
     * ??????????????????
     */
    private static final String AUTO = "0";

    /**
     * ??????????????????
     */
    private CommonSequenceGenerator seqCen;

    /**
     * ??????
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
        // TODO: ??????????????????

        // session???????????????ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        // ??????????????????
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
        // ??????????????????ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.FW.getParentCode());

        for (int i = 0; i < Integer.valueOf(num); i++) {
            OrderInfo orderInfo = new OrderInfo();
            String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.FW.toString());
            String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.FW.toString());
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
            assembleOrder(userId, orderInfo, orderId, caseId);
            orderInfo.setParentId(parentId);// ??????????????????ID
            orderInfos.add(orderInfo);
            if (logger.isDebugEnable()) {
                logger.debug("orderId = " + orderId);
                logger.debug("caseId = " + caseId);
                logger.debug("parentId = " + parentId);
            }
        }
        for (int i = 0; i < orderInfos.size(); i++) {
            // ??????????????????
            List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
            RPPApplyVirfwOperationRequst vfwReq = new RPPApplyVirfwOperationRequst();
            RPPApplyVirfwOperationResponse resp = new RPPApplyVirfwOperationResponse();
            resp.setResultCode(SUCCEESS_CODE);
            if (AUTO.equals(audit)) {
                // ????????????
                // ???????????????????????????
                assembleReq(vfwReq);
                resp = virfwClient.createVirFw(vfwReq);
            }
            if (resp.getResultCode().equals(SUCCEESS_CODE)) {

                // ??????????????????
                try {

                    // ???????????????
                    updateBatchVO.add(new BatchVO("ADD", "createVfwOrder", orderInfos.get(i)));

                    // ??????IP????????????
                    VfwInstanceInfo vfwInstanceInfo = new VfwInstanceInfo();
                    vfwInstanceInfo.setDescription(description);
                    vfwInstanceInfo.setFwName(fwName);
                    if (AUTO.equals(audit)) {
                        assembleVfwInstance(userId, orderInfos.get(i), resp, vfwInstanceInfo);
                    } else {
                        assembleVfwInstanceAudit(userId, orderInfos.get(i), vfwInstanceInfo);
                    }

                    // ???????????????
                    updateBatchVO.add(new BatchVO("ADD", "createVfwInstanceInfo", vfwInstanceInfo));

                    if (logger.isDebugEnable()) {
                        logger.debug("???????????????????????????");
                    }
                } catch (Exception e2) {
                    this.addActionError(getText("vfw.applyinfo.fail"));
                    errMsg = getText("vfw.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                }
                try {
                    ibatisDAO.updateBatchData(updateBatchVO);
                    if (logger.isDebugEnable()) {
                        logger.debug("???????????????????????????????????????????????????");
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
                // ???????????????????????????
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "????????????ip?????????????????????");
                this.addActionError(getText("vfw.applyinfo.sendcreateVFW.fail"));
                errMsg = getText("vfw.applyinfo.sendcreateVFW.fail");
                // ????????????
                VFWCreateFail vfwCreateFail = assembleVFWCreateFail(vfwReq, resp, userId);
                List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
                insertFailBatchVO.add(new BatchVO("ADD", "insertVFWCreateFail", vfwCreateFail));
                try {
                    ibatisDAO.updateBatchData(insertFailBatchVO);
                } catch (SQLException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            "????????????ip??????????????????????????????????????????", e);
                    return ConstantEnum.FAILURE.toString();
                } catch (Exception e2) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            "????????????ip??????????????????????????????????????????", e2);
                    return ConstantEnum.FAILURE.toString();
                }
                return ConstantEnum.FAILURE.toString();
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("??????????????????????????????????????????");
        }

        // TODO????????????
        msg = "??????????????????????????????????????????";
        return ConstantEnum.SUCCESS.toString();
    }

    private void assembleReq(RPPApplyVirfwOperationRequst vfwReq) {
        /* ???????????? */
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
     * ????????????????????????????????????????????????id???????????????id
     * @param userId ??????id
     * @param orderInfo ????????????
     * @param public ip??????
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
     * getAudit ??????????????????
     * @return ??????????????????
     */
    public String getAudit() {
        return audit;
    }

    /**
     * setAudit ??????????????????
     * @param audit ??????????????????
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
     * ??????seqCen????????????
     * @return Returns the seqCen.
     */
    public CommonSequenceGenerator getSeqCen() {
        return seqCen;
    }

    /**
     * ??????seqCen????????????
     * @param seqCen The seqCen to set.
     */
    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }

    /**
     * ??????appName????????????
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * ??????appName????????????
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
