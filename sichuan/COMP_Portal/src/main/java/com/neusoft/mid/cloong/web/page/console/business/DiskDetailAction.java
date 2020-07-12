/*******************************************************************************
 * @(#)DiskDetailAction.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 显示云硬盘详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:16:45
 */
public class DiskDetailAction extends ResourceManagementBaseAction {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 2501512361728019512L;

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(DiskDetailAction.class);

    /**
     * 云硬盘详细信息
     */
    private DiskInfo diskInfo;

    /**
     * 云硬盘编号
     */
    private String diskId;
    
    /**
     * 实例编号
     */
    private String caseId;
    
    private String queryBusinessId;
    
	private String businessName;

    /**
     * execute Action执行方法
     * @return 返回状态码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        
        DiskInfo tmp = new DiskInfo();
        
        tmp.setDiskId(diskId);
        tmp.setCaseId(caseId);
        
        try {
            diskInfo = (DiskInfo) ibatisDAO.getSingleRecord("queryDiskDetail", tmp);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("disk.query.fail"), diskId), e);
            this.setErrMsg(MessageFormat.format(getText("disk.query.fail"), diskId));
            return ConstantEnum.FAILURE.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("disk.query.fail"), diskId), e);
            this.setErrMsg(MessageFormat.format(getText("disk.query.fail"), diskId));
            return ConstantEnum.FAILURE.toString();
        }
        String emptyStr = "";
        if (diskInfo.getExpireTime() != null && !diskInfo.getExpireTime().equals(emptyStr))
            diskInfo.setExpireTime(DateParse.parse(diskInfo.getExpireTime()));
        else if (diskInfo.getDiskLength().equals("0") && diskInfo.getCreateTime() != null
                && !diskInfo.getCreateTime().equals(emptyStr)) {
            diskInfo.setDiskLength("无限期");
            diskInfo.setExpireTime("无限期");
        }
        if (diskInfo.getCreateTime() != null && !diskInfo.getCreateTime().equals(emptyStr)) {
            diskInfo.setCreateTime(DateParse.parse(diskInfo.getCreateTime()));
        }
        // session中获取用户ID
        List<String> appList = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getAppIdList();

        String res = ConstantEnum.SUCCESS.toString();

        String notExistStatus = "10";
        /* 改为业务过滤 */
        if (!appList.contains(diskInfo.getBusinessId())) {
            logger.info(getText("disk.operation.auth"));
            this.setErrMsg(getText("disk.operation.auth"));
            res = ConstantEnum.FAILURE.toString();
        } else if (diskInfo.getDiskStatus().equals(notExistStatus)) {
            logger.info(MessageFormat.format(getText("disk.query.notexist"), diskId));
            this.setErrMsg(MessageFormat.format(getText("disk.query.notexist"), diskId));
            res = ConstantEnum.FAILURE.toString();
        } else if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("查询编号为[").append(diskId).append("]的云硬盘详细信息成功!");
            logger.debug(sb.toString());
        }
        return res;
    }

    public DiskInfo getDiskInfo() {
        return diskInfo;
    }

    public void setDiskInfo(DiskInfo diskInfo) {
        this.diskInfo = diskInfo;
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
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

	/**
	 * @return the queryBusinessId
	 */
	public String getQueryBusinessId() {
		return queryBusinessId;
	}

	/**
	 * @param queryBusinessId the queryBusinessId to set
	 */
	public void setQueryBusinessId(String queryBusinessId) {
		this.queryBusinessId = queryBusinessId;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
