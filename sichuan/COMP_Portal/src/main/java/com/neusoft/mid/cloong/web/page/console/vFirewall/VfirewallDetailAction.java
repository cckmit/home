/*******************************************************************************
 * @(#)DiskDetailAction.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vFirewall;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.vfw.core.AddVirFWStrategyReq;
import com.neusoft.mid.cloong.vfw.core.AddVirFWStrategyResp;
import com.neusoft.mid.cloong.vfw.core.DeleteVirFWStrategyReq;
import com.neusoft.mid.cloong.vfw.core.DeleteVirFWStrategyResp;
import com.neusoft.mid.cloong.vfw.core.VFWDelFail;
import com.neusoft.mid.cloong.vfw.core.VFWRulesCreateFail;
import com.neusoft.mid.cloong.vfw.core.VFWRulesDelFail;
import com.neusoft.mid.cloong.vfw.core.VFWStrategyAdd;
import com.neusoft.mid.cloong.vfw.core.VFWStrategyDel;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.ip.IpDetailAction;
import com.neusoft.mid.cloong.web.page.console.ip.info.PortConfigInfo;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.cloong.web.page.console.vFirewall.info.VfirewallInfo;
import com.neusoft.mid.cloong.web.page.console.vFirewall.info.VfirewallRuleInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 显示虚拟防火墙详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:16:45
 */
public class VfirewallDetailAction extends BaseAction {

    private static final long serialVersionUID = 2501512361728019512L;

    private static LogService logger = LogService.getLogger(IpDetailAction.class);

    private VfirewallInfo vfw = new VfirewallInfo();

    private VfirewallRuleInfo vfwRule = new VfirewallRuleInfo();

    private List<VfirewallRuleInfo> vfwRuleList = new ArrayList<VfirewallRuleInfo>();

    private VFWStrategyDel delete;

    private VFWStrategyAdd create;

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;
    
    /**
     * 业务id
     */
    private String appId;

    /**
     * 页面返回路径
     */
    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "0";

    /**
     * 返回消息
     */
    private String resultMessage = "";

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    /**
     * 实例编号
     */
    private String caseId;

    public String execute() {
        vfw.setCaseId(caseId);
        try {
            // 查询虚拟防火墙详情
            vfw = (VfirewallInfo) ibatisDAO.getSingleRecord("getVfwDetail", vfw);
            vfw.setCreateTime(DateParse.parse(vfw.getCreateTime()));
            vfw.setUpdateTime(DateParse.parse(vfw.getUpdateTime()));
            // 有防火墙id再查防火墙规则
            if (vfw.getFwId() != null) {
                vfwRuleList = ibatisDAO.getData("getVfwRuleByFwId", vfw);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 添加规则
     * @return
     */
    public String addFwRule() {

        logger.info(vfwRule.toString());
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        vfwRule.setCreateUser(userId);
        vfwRule.setCreateTime(time);

        AddVirFWStrategyReq req = new AddVirFWStrategyReq();
        req.setFwId(vfwRule.getFwId());
        req.setProtocol(vfwRule.getProtocol());
        req.setSourceIp(vfwRule.getSourceIp());
        req.setSourcePort(vfwRule.getSourcePort());
        req.setDestinationIp(vfwRule.getDestinationIp());
        req.setDestinationPort(vfwRule.getDestinationPort());
        req.setActType(vfwRule.getActType());
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        AddVirFWStrategyResp resp = create.addVFWStrategy(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟防火墙创建失败，接口错误", null);
            resultMessage = MessageFormat.format(getText("vfw.delete.fail"), vfwRule.getFwRuleId());
            result = ConstantEnum.INTERFACEERROR.toString();
            VFWRulesCreateFail vfwRulesCreateFail = new VFWRulesCreateFail();
            vfwRulesCreateFail.setFailCause(resp.getResultDesc());
            vfwRulesCreateFail.setFailCode(resp.getResultCode());
            vfwRulesCreateFail.setFwId(req.getFwId());
            vfwRulesCreateFail.setResPoolId(resourcePoolId);
            vfwRulesCreateFail.setResPoolPartId(resourcePoolPartId);
            vfwRulesCreateFail.setSerialNum(req.getSerialNum());
            vfwRulesCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
            vfwRulesCreateFail.setAppId(appId);
            try {
                ibatisDAO.insertData("insertVfwRulesCreateFail", vfwRulesCreateFail);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error("插入虚拟防火墙策略创建失败表异常",e);
            }

            return ConstantEnum.FAILURE.toString();
        }
        vfwRule.setFwRuleId(resp.getResultDesc());
        try {
            ibatisDAO.insertData("addVfwRule", vfwRule);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 删除规则
     * @return
     */
    public String delFwRule() {

        try {
            DeleteVirFWStrategyReq req = new DeleteVirFWStrategyReq();
            req.setFwStrategyID(vfwRule.getFwRuleId());
            req.setSerialNum(seqCen.generatorSerialNum());
            req.setResourcePoolId(resourcePoolId);
            req.setResourcePoolPartId(resourcePoolPartId);
            DeleteVirFWStrategyResp resp = delete.delVFWStrategy(req);
            if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
                logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟防火墙策略删除失败，接口错误", null);
                resultMessage = MessageFormat.format(getText("vfw.delete.fail"),
                        vfwRule.getFwRuleId());
                result = ConstantEnum.INTERFACEERROR.toString();
                VFWRulesDelFail vfwRulesDelFail = new VFWRulesDelFail();
                vfwRulesDelFail.setFailCause(resp.getResultDesc());
                vfwRulesDelFail.setFailCode(resp.getResultCode());
                vfwRulesDelFail.setFwId(vfwRule.getFwId());
                vfwRulesDelFail.setResPoolId(resourcePoolId);
                vfwRulesDelFail.setResPoolPartId(resourcePoolPartId);
                vfwRulesDelFail.setSerialNum(req.getSerialNum());
                vfwRulesDelFail.setCreateTime(TIMESTAMP.print(new DateTime()));
                vfwRulesDelFail.setAppId(appId);
                ibatisDAO.insertData("insertVfwRulesDelFail", vfwRulesDelFail);

                return ConstantEnum.FAILURE.toString();
            }
            ibatisDAO.deleteData("delVfwRule", vfwRule);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public VfirewallInfo getVfw() {
        return vfw;
    }

    public void setVfw(VfirewallInfo vfw) {
        this.vfw = vfw;
    }

    public VfirewallRuleInfo getVfwRule() {
        return vfwRule;
    }

    public void setVfwRule(VfirewallRuleInfo vfwRule) {
        this.vfwRule = vfwRule;
    }

    public List<VfirewallRuleInfo> getVfwRuleList() {
        return vfwRuleList;
    }

    public void setVfwRuleList(List<VfirewallRuleInfo> vfwRuleList) {
        this.vfwRuleList = vfwRuleList;
    }

    public void setDelete(VFWStrategyDel delete) {
        this.delete = delete;
    }

    public CommonSequenceGenerator getSeqCen() {
        return seqCen;
    }

    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }

    public void setCreate(VFWStrategyAdd create) {
        this.create = create;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
