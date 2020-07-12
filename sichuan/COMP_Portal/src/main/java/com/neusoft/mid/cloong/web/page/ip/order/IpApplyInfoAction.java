/*******************************************************************************
 * @(#)EBSApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.ip.order;

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
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.ip.order.info.PublicIpInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交申请ip订单
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:18:41
 */
public class IpApplyInfoAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -3246558237673423698L;

    private static LogService logger = LogService.getLogger(IpApplyInfoAction.class);

    private String respoolId;

    private String respoolPartId;

    private String respoolName;

    private String respoolPartName;

    private String resourceType;

    private String description;

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

    private String num;

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

        RPPEBSCreateReq ebsReq = new RPPEBSCreateReq();

  
      
        // 拼凑订单信息
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.IP.getParentCode());
        for (int i = 0; i < Integer.valueOf(num); i++) {

            String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.IP.toString());
            String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.IP.toString());
            // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
            OrderInfo orderInfo = new OrderInfo();
            assembleOrder(userId, orderInfo, orderId, caseId);
            orderInfo.setParentId(parentId);// 同一个订单父ID
            orderInfos.add(orderInfo);
            if (logger.isDebugEnable()) {
                logger.debug("orderId = " + orderId);
                logger.debug("caseId = " + caseId);
                logger.debug("parentId = " + parentId);
            }

        }

        RPPEBSCreateResp resp = new RPPEBSCreateResp();
        resp.setResultCode(SUCCEESS_CODE);

        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，云硬盘订单和实例入库
            for (int i = 0; i < orderInfos.size(); i++) {
                List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
            // 拼凑实例信息
            try {
                // 拼凑订单信息
            
                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createPublicIpOrder", orderInfos.get(i)));

                // 拼凑IP实例信息
                PublicIpInstanceInfo publicIpInstanceInfo = new PublicIpInstanceInfo();
                publicIpInstanceInfo.setDescription(description);
                List<PublicIpInfo> ips = ibatisDAO.getData("getIpList", null);
                if (ips.size() > 0) {
                    publicIpInstanceInfo.setPublicIp(ips.get(0).getPublicIp());
                } else {
                    errMsg = "没有可分配IP地址！";
                    return ConstantEnum.FAILURE.toString();
                }
                if (AUTO.equals(audit)) {
                    assemblePublicIpInstance(userId, orderInfos.get(i), publicIpInstanceInfo);
                } else {
                    assemblePublicIpInstanceAudit(userId, orderInfos.get(i), publicIpInstanceInfo);
                }

                // 更新数据库
                updateBatchVO.add(
                        new BatchVO("ADD", "createPublicIpInstanceInfo", publicIpInstanceInfo));
                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "updatePublicIpStatus", publicIpInstanceInfo));

                if (logger.isDebugEnable()) {
                    logger.debug("拼凑订单信息完成！");
                }
            } catch (Exception e2) {
                this.addActionError(getText("publicIp.applyinfo.fail"));
                errMsg = getText("publicIp.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
            try {
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("申请公有ip订单记入数据库成功！");
                }
            } catch (SQLException e) {
                this.addActionError(getText("publicIp.applyinfo.fail"));
                errMsg = getText("publicIp.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            } catch (Exception e2) {
                this.addActionError(getText("publicIp.applyinfo.fail"));
                errMsg = getText("publicIp.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
            
            
        }
        } else {
            // 返回失败，入失败库

            return ConstantEnum.FAILURE.toString();
        }

        if (logger.isDebugEnable()) {
            logger.debug("创建公有ip发送申请成功！");
        }

        // TODO：出话单
        msg = "创建公有ip发送申请成功！";
        return ConstantEnum.SUCCESS.toString();
    }

    private void assemblePublicIpInstance(String userId, OrderInfo orderInfo,
            PublicIpInstanceInfo publicIpInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        publicIpInstanceInfo.setCaseId(orderInfo.getCaseId());
        publicIpInstanceInfo.setStatus(PublicIpStatus.CREATED);
        publicIpInstanceInfo.setResPoolId(respoolId);
        publicIpInstanceInfo.setResPoolPartId(respoolPartId);
        publicIpInstanceInfo.setOrderId(orderInfo.getOrderId());
        publicIpInstanceInfo.setCreateTime(time);
        publicIpInstanceInfo.setCreateUser(userId);
        publicIpInstanceInfo.setUpdateTime(time);
        publicIpInstanceInfo.setUpdateUser(userId);
        publicIpInstanceInfo.setAppId(appId);
        publicIpInstanceInfo.setResourceType(resourceType);
    }

    /**
     * 为人工审批时做准备，为保存资源池id资源池分区id
     * @param userId 用户id
     * @param orderInfo 订单信息
     * @param public ip实例
     */
    private void assemblePublicIpInstanceAudit(String userId, OrderInfo orderInfo,
            PublicIpInstanceInfo publicIpInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        publicIpInstanceInfo.setCaseId(orderInfo.getCaseId());
        publicIpInstanceInfo.setStatus(PublicIpStatus.PREPARE);
        publicIpInstanceInfo.setResPoolId(respoolId);
        publicIpInstanceInfo.setResPoolPartId(respoolPartId);
        publicIpInstanceInfo.setOrderId(orderInfo.getOrderId());
        publicIpInstanceInfo.setCreateTime(time);
        publicIpInstanceInfo.setCreateUser(userId);
        publicIpInstanceInfo.setUpdateTime(time);
        publicIpInstanceInfo.setUpdateUser(userId);
        publicIpInstanceInfo.setAppId(appId);
        publicIpInstanceInfo.setResourceType(resourceType);
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
        orderInfo.setCaseType("7");
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

    public EBSCreate getEbsCreate() {
        return ebsCreate;
    }

    public void setEbsCreate(EBSCreate ebsCreate) {
        this.ebsCreate = ebsCreate;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
