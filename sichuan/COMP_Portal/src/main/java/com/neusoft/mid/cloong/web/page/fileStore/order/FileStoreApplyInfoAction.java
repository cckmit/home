/*******************************************************************************
 * @(#)EBSApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.fileStore.order;

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
import com.neusoft.mid.cloong.fileStore.core.FSCreateFail;
import com.neusoft.mid.cloong.fileStore.core.FileStorageExecuter;
import com.neusoft.mid.cloong.fileStore.core.FileStoreStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateResponse;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.fileStore.order.info.FileStoreInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交申请ip订单
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:18:41
 */
public class FileStoreApplyInfoAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -3246558237673423698L;

    private static LogService logger = LogService.getLogger(FileStoreApplyInfoAction.class);

    FileStoreInstanceInfo fsInstanceInfo = new FileStoreInstanceInfo();

    private SequenceGenerator sequenceGenerator;

    /**
     * 云硬盘创建接口
     */
    private FileStorageExecuter fileStorage;

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
            logger.debug("respoolId = " + fsInstanceInfo.getResPoolId());
            logger.debug("respoolPartId = " + fsInstanceInfo.getResPoolPartId());
            logger.debug("respoolName = " + fsInstanceInfo.getResPoolName());
            logger.debug("respoolPartName = " + fsInstanceInfo.getResPoolPartName());
            logger.debug("appId = " + appId);
            logger.debug("resourceType = " + fsInstanceInfo.getResourceType());
        }
        // TODO: 记入操作日志

        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();

        // 拼凑订单信息
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.FS.getParentCode());

        for (int i = 0; i < Integer.valueOf(num); i++) {
            OrderInfo orderInfo = new OrderInfo();
            String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.FS.toString());
            String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.FS.toString());
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
            RPPFileStorageCreateReq fsReq = new RPPFileStorageCreateReq();
            RPPFileStorageCreateResponse resp = new RPPFileStorageCreateResponse();
            List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
            resp.setResultCode(SUCCEESS_CODE);
            if (AUTO.equals(audit)) {
                // 自动审批
                // 发送申请订单至接口
                assembleReq(fsReq);
                resp = fileStorage.createFileStorage(fsReq);
            }
            if (resp.getResultCode().equals(SUCCEESS_CODE)) {
                // 返回成功，云硬盘订单和实例入库

                // 拼凑实例信息
                try {
                    // 更新数据库
                    updateBatchVO.add(new BatchVO("ADD", "createFsOrder", orderInfos.get(i)));

                    if (AUTO.equals(audit)) {
                        assembleFsInstance(userId, orderInfos.get(i), resp, fsInstanceInfo);
                    } else {
                        assembleFsInstanceAudit(userId, orderInfos.get(i), fsInstanceInfo);
                    }

                    // 更新数据库
                    updateBatchVO.add(new BatchVO("ADD", "createFsInstanceInfo", fsInstanceInfo));
                    if (logger.isDebugEnable()) {
                        logger.debug("拼凑订单信息完成！");
                    }
                } catch (Exception e2) {
                    this.addActionError(getText("ebs.applyinfo.fail"));
                    errMsg = getText("ebs.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                }
                try {
                    ibatisDAO.updateBatchData(updateBatchVO);
                    if (logger.isDebugEnable()) {
                        logger.debug("申请文件存储订单记入数据库成功！");
                    }
                } catch (SQLException e) {
                    this.addActionError(getText("ebs.applyinfo.fail"));
                    errMsg = getText("ebs.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                } catch (Exception e2) {
                    this.addActionError(getText("ebs.applyinfo.fail"));
                    errMsg = getText("ebs.applyinfo.fail");
                    return ConstantEnum.FAILURE.toString();
                }
            } else {
                // 返回失败，入失败库
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建公有ip发送申请失败！");
                this.addActionError(getText("ebs.applyinfo.sendcreateEBS.fail"));
                errMsg = getText("ebs.applyinfo.sendcreateEBS.fail");
                // 入失败库
                FSCreateFail fsCreateFail = assembleFSCreateFail(fsReq, resp, userId);
                List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();

                insertFailBatchVO.add(new BatchVO("ADD", "insertFSCreateFail", fsCreateFail));

                try {
                    ibatisDAO.updateBatchData(insertFailBatchVO);
                } catch (SQLException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            "申请文件存储失败后入失败库发生数据库异常", e);
                    return ConstantEnum.FAILURE.toString();
                } catch (Exception e2) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            "申请文件存储失败后入失败库发生数据库异常", e2);
                    return ConstantEnum.FAILURE.toString();
                }
                return ConstantEnum.FAILURE.toString();
            }
        }
        if (logger.isDebugEnable()) {
            logger.debug("创建文件存储发送申请成功！");
        }

        // TODO：出话单
        msg = "创建文件存储发送申请成功！";
        return ConstantEnum.SUCCESS.toString();
    }

    private void assembleReq(RPPFileStorageCreateReq fsReq) {
        /* 模板创建 */
        fsReq.setResourcePoolId(fsInstanceInfo.getResPoolId());
        fsReq.setResourcePoolPartId(fsInstanceInfo.getResPoolPartId());
        fsReq.setFileStorageName(fsInstanceInfo.getFsName());
        fsReq.setAppID(appId);
        fsReq.setAppName(fsInstanceInfo.getAppName());
        fsReq.setSerialNum(seqCen.generatorSerialNum());
        fsReq.setSharetype(Integer.parseInt(fsInstanceInfo.getFsShareType()));
        fsReq.setQuotaSize(Integer.parseInt(fsInstanceInfo.getFsQuotaSize()));
        fsReq.setPassword(fsInstanceInfo.getFsAdminPassword());
    }

    private FSCreateFail assembleFSCreateFail(RPPFileStorageCreateReq tempFSReq,
            RPPFileStorageCreateResponse fsResp, String userId) {
        FSCreateFail fsCreateFail = new FSCreateFail();
        fsCreateFail.setFailCause(fsResp.getResultMessage());
        fsCreateFail.setFailCode(fsResp.getResultCode());
        fsCreateFail.setResPoolId(tempFSReq.getResourcePoolId());
        fsCreateFail.setResPoolPartId(tempFSReq.getResourcePoolPartId());
        fsCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        fsCreateFail.setFsName(tempFSReq.getFileStorageName());
        fsCreateFail.setCreateUser(userId);
        fsCreateFail.setNum(num);
        fsCreateFail.setAppId(appId);
        return fsCreateFail;
    }

    private void assembleFsInstance(String userId, OrderInfo orderInfo,
            RPPFileStorageCreateResponse resp, FileStoreInstanceInfo fsInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        fsInstanceInfo.setCaseId(orderInfo.getCaseId());
        fsInstanceInfo.setStatus(FileStoreStatus.PREPARE);
        fsInstanceInfo.setOrderId(orderInfo.getOrderId());
        fsInstanceInfo.setCreateTime(time);
        fsInstanceInfo.setCreateUser(userId);
        fsInstanceInfo.setUpdateTime(time);
        fsInstanceInfo.setUpdateUser(userId);
        fsInstanceInfo.setDescription("");
        fsInstanceInfo.setExpireTime("");
        fsInstanceInfo.setFsAdminUser("root");
        fsInstanceInfo.setFsAdminPassword("!QAZ2wsx");
    }

    /**
     * 为人工审批时做准备，为保存资源池id资源池分区id
     * @param userId 用户id
     * @param orderInfo 订单信息
     * @param public ip实例
     */
    private void assembleFsInstanceAudit(String userId, OrderInfo orderInfo,
            FileStoreInstanceInfo fsInstanceInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        fsInstanceInfo.setCaseId(orderInfo.getCaseId());
        fsInstanceInfo.setStatus(FileStoreStatus.PREPARE);
        fsInstanceInfo.setOrderId(orderInfo.getOrderId());
        fsInstanceInfo.setCreateTime(time);
        fsInstanceInfo.setCreateUser(userId);
        fsInstanceInfo.setUpdateTime(time);
        fsInstanceInfo.setUpdateUser(userId);
        fsInstanceInfo.setFsAdminUser("root");
        fsInstanceInfo.setFsAdminPassword("!QAZ2wsx");
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
        orderInfo.setCaseType("15");
        orderInfo.setAppId(fsInstanceInfo.getAppId());
        orderInfo.setResPoolId(fsInstanceInfo.getResPoolId());
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);

    }

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
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

    public FileStoreInstanceInfo getFsInstanceInfo() {
        return fsInstanceInfo;
    }

    public void setFsInstanceInfo(FileStoreInstanceInfo fsInstanceInfo) {
        this.fsInstanceInfo = fsInstanceInfo;
    }

    public FileStorageExecuter getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(FileStorageExecuter fileStorage) {
        this.fileStorage = fileStorage;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
