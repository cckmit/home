/*******************************************************************************
 * @(#)DiskDeleteAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.fileStore;

import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSDelete;
import com.neusoft.mid.cloong.fileStore.core.DeleteFileStorageReq;
import com.neusoft.mid.cloong.fileStore.core.DeleteFileStorageResp;
import com.neusoft.mid.cloong.fileStore.core.FSDel;
import com.neusoft.mid.cloong.fileStore.core.FSDelFail;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteResponse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.fileStore.info.FileStoreInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除文件存储
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-21 下午05:53:57
 */
public class FileStoreDeleteAction extends BaseAction {

    private static final long serialVersionUID = 4407178327415405154L;

    private static LogService logger = LogService.getLogger(FileStoreDeleteAction.class);

    /**
     * 文件存储ID
     */
    private String fsId;

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
    private static final String SUCCEESS_CODE = "success";

    /**
     * 返回消息
     */
    private String resultMessage = "";

    /**
     * 分布式文件存储删除接口
     */
    private FSDel delete;
    
    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    public String execute() {
        if (fsId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "分布式文件存储id为空");
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        fsId = fsId.trim();
        FileStoreInfo fsInfo = null;
        try {
            fsInfo = (FileStoreInfo) ibatisDAO.getSingleRecord("getFsDetailByFsId", fsId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "分布式文件存储ID为[" + fsId
                    + "]的分布式文件存储所有者失败", e);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (fsInfo == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "分布式文件存储ID为[" + fsId
                    + "]的分布式文件存储所有者失败", null);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        /* 鉴权改为业务ID */
        if (!getCurrentUser().getAppIdList().contains(fsInfo.getAppId())) {
            logger.info(getText("fs.operation.auth"));
            resultMessage = getText("fs.operation.auth");
            result = ConstantEnum.NOPERMISSION.toString();
            return ConstantEnum.FAILURE.toString();
        }
    
        DeleteFileStorageReq req = new DeleteFileStorageReq();
        req.setFileStorageID(fsId);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(seqCen.generatorSerialNum());
        DeleteFileStorageResp resp = delete.delFS(req);
        if (resp.getFaultString()!=null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "分布式文件存储删除失败，接口错误", null);
            resultMessage = MessageFormat.format(getText("fs.delete.fail"), fsId);
            result = ConstantEnum.INTERFACEERROR.toString();
            FSDelFail fsDelFail=new FSDelFail();
            fsDelFail.setCreateTime(TIMESTAMP.print(new DateTime()));
            fsDelFail.setFailCause(resp.getFaultString());
            fsDelFail.setFailCode(resp.getResultCode());
            fsDelFail.setFsId(fsId);
            fsDelFail.setAppId(appId);
            fsDelFail.setResPoolId(resourcePoolId);
            fsDelFail.setResPoolPartId(resourcePoolPartId);
            fsDelFail.setSerialNum(req.getSerialNum());
            
            try {
                ibatisDAO.insertData("insertFSDelFail", fsDelFail);
            } catch (SQLException e) {
               logger.error("分布式文件存储删除失败入库异常",e);
            }
            
            return ConstantEnum.FAILURE.toString();
        }
        try {
           ibatisDAO.updateData("deleteFs", fsId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("fs.update.fail"), fsId), e);
            this.addActionError(MessageFormat.format(getText("fs.delete.fail"), fsId));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
   
        StringBuilder sb = new StringBuilder();
        sb.append("删除编码为[").append(fsId).append("]的分布式文件存储成功！");
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

    public void setDelete(FSDel delete) {
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


    public String getFsId() {
        return fsId;
    }


    public void setFsId(String fsId) {
        this.fsId = fsId;
    }


    public String getAppId() {
        return appId;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }
}
