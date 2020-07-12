/*******************************************************************************
 * @(#)DiskDeleteAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vFirewall;

import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSDelete;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceResp;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceReq;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceResp;
import com.neusoft.mid.cloong.vfw.core.VFWDel;
import com.neusoft.mid.cloong.vfw.core.VFWDelFail;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.vFirewall.info.VfirewallInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除虚拟防火墙
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-21 下午05:53:57
 */
public class VfirewallDeleteAction extends BaseAction {

    private static final long serialVersionUID = 4407178327415405154L;

    private static LogService logger = LogService.getLogger(VfirewallDeleteAction.class);

    /**
     * ID
     */
    private String virfwid;

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
     * 虚拟防火墙删除接口
     */
    private VFWDel delete;

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;
    
    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    
    /**
     * 删除虚拟防火墙
     * @return
     */
    public String delVfw() {
        if (virfwid == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟防火墙id为空");
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        virfwid = virfwid.trim();
        VfirewallInfo vfwInfo = null;
        try {
            vfwInfo = (VfirewallInfo) ibatisDAO.getSingleRecord("getVfwDetailByFwId", virfwid);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟防火墙ID为[" + virfwid
                    + "]的虚拟防火墙所有者失败", e);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (vfwInfo == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟防火墙ID为[" + virfwid
                    + "]的虚拟机所有者失败", null);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        /* 鉴权改为业务ID */
        if (!getCurrentUser().getAppIdList().contains(vfwInfo.getAppId())) {
            logger.info(getText("disk.operation.auth"));
            resultMessage = getText("disk.operation.auth");
            result = ConstantEnum.NOPERMISSION.toString();
            return ConstantEnum.FAILURE.toString();
        }
    
        CancelVirFWServiceReq req = new CancelVirFWServiceReq();
        req.setVirfwid(virfwid);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(seqCen.generatorSerialNum());
        CancelVirFWServiceResp resp = delete.delVFW(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟防火墙删除失败，接口错误", null);
            resultMessage = MessageFormat.format(getText("vfw.delete.fail"), virfwid);
            result = ConstantEnum.INTERFACEERROR.toString();
            
            VFWDelFail vfwDelFail=new VFWDelFail();
            vfwDelFail.setFailCause(resp.getResultDesc());
            vfwDelFail.setFailCode(resp.getResultCode());
            vfwDelFail.setCreateTime(TIMESTAMP.print(new DateTime()));
            vfwDelFail.setFwId(virfwid);
            vfwDelFail.setResPoolId(resourcePoolId);
            vfwDelFail.setResPoolPartId(resourcePoolPartId);
            vfwDelFail.setSerialNum(req.getSerialNum());
            vfwDelFail.setAppId(appId);
            try {
                ibatisDAO.insertData("insertVFWDelFail", vfwDelFail);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error( "插入删除虚拟防火墙失败表异常",e);
            }
            
            return ConstantEnum.FAILURE.toString();
        }
        try {
         ibatisDAO.updateData("deleteVfw", virfwid);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("diskname.update.fail"), virfwid), e);
            this.addActionError(MessageFormat.format(getText("vfw.delete.fail"), virfwid));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
  
        StringBuilder sb = new StringBuilder();
        sb.append("删除编码为[").append(virfwid).append("]的虚拟防火墙成功！");
        logger.info(sb.toString());
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
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

    public void setDelete(VFWDel delete) {
        this.delete = delete;
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



    public String getVirfwid() {
        return virfwid;
    }



    public void setVirfwid(String virfwid) {
        this.virfwid = virfwid;
    }



    public String getAppId() {
        return appId;
    }



    public void setAppId(String appId) {
        this.appId = appId;
    }
}
