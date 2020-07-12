/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询云硬盘列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class QueryEBSListAction extends ResourceManagementBaseAction {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -5471800875775849067L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(QueryEBSListAction.class);

    /**
     * 虚拟机实例信息
     */
    private List<DiskInfo> diskInfos;

    /**
     * 主机ID
     */
    private String hostId;

    /**
     * 容量大小
     */
    private String diskSize;

    /**
     * 存储名称
     */
    private String diskName;

    /**
     * 存储ID
     */
    private String diskId;

    /**
     * 存储绑定的业务ID
     */
    private String queryBusinessId;
    
	private String businessName;

    private String status;

    /**
     * execute 执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @SuppressWarnings("unchecked")
    public String execute() {
        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();

        DiskInfo diskInfo = new DiskInfo();
        diskInfo.setHostId(hostId);
        diskInfo.setDiskSize(diskSize);
        diskInfo.setDiskName(diskName);
        diskInfo.setDiskId(diskId);
        List<String> tmp = new ArrayList<String>();
        tmp.add(queryBusinessId);
        diskInfo.setBusinessList(tmp);

        /* 查询挂载和未挂载状态时需要的 */
        if (null != status && !status.equals("")) {
            String[] tmpArr = status.split(",");
            List<String> tmpList = new ArrayList<String>();
            for (int i = 0; i < tmpArr.length; i++)
                tmpList.add(tmpArr[i]);

            diskInfo.setStatusList(tmpList);
        }

        try {
            diskInfos = (List<DiskInfo>) getPage("queryEBSUserListCountByBusinessId",
                    "queryEBSUserListByBusinessId", diskInfo, PageTransModel.ASYNC);
            fomatResultData(diskInfos);
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("vm.queryList.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vm.queryList.fail"), userId));
            return ConstantEnum.ERROR.toString();
        }

        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("租户：").append(userId);
            sb.append("虚拟机列表长度为:");
            sb.append("lenght:").append(diskInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 格式化生效时间、到期时间
     * @param ls 要格式化的列表
     */
    private void fomatResultData(List<DiskInfo> ls) {
        for (DiskInfo diskInfo : ls) {
            String sbt = diskInfo.getCreateTime() == null ? "" : diskInfo.getCreateTime().trim();
            String ebt = diskInfo.getExpireTime() == null ? "" : diskInfo.getExpireTime().trim();
            if (!"".equals(ebt))
                diskInfo.setExpireTime(DateParse.parse(diskInfo.getExpireTime()));
            else if (diskInfo.getDiskLength().equals("0") && !"".equals(sbt))
                diskInfo.setExpireTime("无限期");
            if (!"".equals(sbt)) {
                String effectiveTime = DateParse.parse(diskInfo.getCreateTime());
                diskInfo.setCreateTime(effectiveTime);
            }

        }
    }

    public List<DiskInfo> getDiskInfos() {
        return diskInfos;
    }

    /**
     * @param hostId the hostId to set
     */
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    /**
     * @param diskSize the diskSize to set
     */
    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    /**
     * @param diskName the diskName to set
     */
    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    /**
     * @param diskId the diskId to set
     */
    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    /**
     * 获取queryBusinessId字段数据
     * @return Returns the queryBusinessId.
     */
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    /**
     * 设置queryBusinessId字段数据
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    /**
     * 获取status字段数据
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status字段数据
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
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
