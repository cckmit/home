/*******************************************************************************
 * @(#)PMDelAction.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.sql.SQLException;
import java.text.MessageFormat;

import javax.xml.ws.Holder;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMDel;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.host.pm.core.PmDeleteReq;
import com.neusoft.mid.cloong.host.pm.core.PmDeleteResp;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMDelFailErrorInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMOperatorErrorInfo;
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
     * 当前页面
     */
    private static final String RETURNNOWPAGE = "0";

    /**
     * 跳转出错界面
     */
    private static final String GOTOERROR = "-1";

    /**
     * 物理机已经被删除
     */

    private static final String DETELTE = "2";

    /**
     * 物理机是否被删除的标准
     */
    private static final String IS_DELETE = "0";

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 物理机删除
     */
    private PMDel pmDel;

    /**
     * 获取物理机编码
     */
    private String pmId;

    private String resourcePoolId;

    private String resourcePoolPartId;

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
     * tip msg
     */
    private String msgTip = "物理机删除失败！数据库连接异常！";

    @Override
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        resultPath = GOTOLISTPAGE;

        // 判断是否存在其他关联信息（是否挂载云硬盘）
        if (isConnect(userId)) {
            return ConstantEnum.FAILURE.toString();
        }
        // 判断物理机状态是否正确，运行被删除
        PMOperatorErrorInfo errorInfo = new PMOperatorErrorInfo();
        Holder<PMOperatorErrorInfo> holder = new Holder<PMOperatorErrorInfo>(errorInfo);
        if (!pmStatusService.judgePMDelete(pmId, holder)) {
            if (holder.value.getErrorCode().equals(IS_DELETE)) {
                resultPath = DETELTE;
                mes = getText("pm.del.status.del.text");
            } else {
                resultPath = RETURNNOWPAGE;
                mes = holder.value.getErrorMessage();
            }
            return ConstantEnum.FAILURE.toString();
        }
        // 更新数据库 删除物理机
        if (delPmDB(userId)) {
            return ConstantEnum.FAILURE.toString();
        }
        // 调用删除虚拟接口
        delPm(pmId, resourcePoolId, resourcePoolPartId);
        pmStatusService.removePMStatus(pmId);
        return ConstantEnum.SUCCESS.toString();
    }

    private boolean delPm(String pmId, String resourcePoolId, String resourcePoolPartId) {
        PmDeleteReq req = new PmDeleteReq();
        req.setPmId(pmId);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(gen.generatorPMSerialNum("PMDel"));
        PmDeleteResp resp = pmDel.delPm(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(
                    CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("pm.delete.fail"), pmId) + "失败原因为："
                            + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
            // 操作失败插入数据库，失败信息暂不入库
            // insertPMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
            return false;
        }
        return true;
    }

    /**
     * 插入物理机删除失败
     * @param errorInfo
     */
    private void insertPMQueryErrorInfo(PMDelFailErrorInfo errorInfo) {
        try {
            ibatisDAO.insertData("insertPmDelFailErrorInfo", errorInfo);
        } catch (SQLException e) {
            logger.error(
                    CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入编号为【" + errorInfo.getPmId() + "】的物理机删除错误信息错误，插入信息为："
                            + errorInfo.toString(), e);
        } catch (Exception e) {
            logger.error(
                    CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入编号为【" + errorInfo.getPmId() + "】的物理机删除错误信息错误，插入信息为："
                            + errorInfo.toString(), e);
        }
    }

    /**
     * 获取错误bean
     * @param req
     * @param resp
     * @return
     */
    private PMDelFailErrorInfo assmbleStateErrorInfo(PmDeleteReq req, PmDeleteResp resp) {
        PMDelFailErrorInfo errorInfo = new PMDelFailErrorInfo();
        errorInfo.setPmId(req.getPmId());
        errorInfo.setFailCause(resp.getResultMessage());
        errorInfo.setFailCode(resp.getResultCode());
        errorInfo.setResourcePoolId(req.getResourcePoolId());
        errorInfo.setResourcePoolPartId(req.getResourcePoolPartId());
        errorInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        errorInfo.setSerialNum(req.getSerialNum());
        return errorInfo;
    }

    /**
     * 更新数据库，将物理机状态变为删除。
     * @param userId 当前用户id
     * @return 成功 true 失败 false
     */
    private boolean delPmDB(String userId) {
        PMInstanceInfo pmInfo = new PMInstanceInfo();
        pmInfo.setPmId(pmId);
        pmInfo.setStatus(PMStatus.DELETED);
        pmInfo.setUpdateUser(userId);
        pmInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        try {
            int upNum = ibatisDAO.updateData("updatePMStatusAndOwn", pmInfo);
            if (upNum == 1) {
                mes = getText("pm.del.sucess");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, pmId));
                    sb.append(getText("pm.del.debug.sucess"));
                    logger.debug(sb.toString());
                }
            } else {
                resultPath = RETURNNOWPAGE;
                mes = getText("pm.del.status.del.text");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, pmId));
                    sb.append(getText(FAILMESSAGE));
                    logger.debug(sb.toString());
                }
                return true;
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, msgTip, e);
            this.addActionError(msgTip);
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
    private boolean isConnect(String userId) {
        StringBuffer res = new StringBuffer();
        int ebsCount = 0;
        try {
            // 是否挂载云硬盘
            ebsCount = ibatisDAO.getCount("getCountEbsByPMid", pmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, msgTip, e);
            this.addActionError(msgTip);
            resultPath = RETURNNOWPAGE;
            mes = getText(FAILMESSAGE);
            return true;
        }
        if (ebsCount > 0) {
            if (null == res || "".equals(res.toString())) {
                res.append(getText("pm.del.ebs.beings"));
            } else {
                res.append(getText("pm.del.ebs.being"));
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

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public PMDel getPmDel() {
        return pmDel;
    }

    public void setPmDel(PMDel pmDel) {
        this.pmDel = pmDel;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
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

}
