/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询登录用户下云硬盘列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class EBSQueryListAction extends PageAction {

    private static final long serialVersionUID = -5471800875775849067L;

    private static LogService logger = LogService.getLogger(EBSQueryListAction.class);

    /**
     * 虚拟机实例信息
     */
    private List<DiskInfo> diskInfos;

    private String hostId;

    private String diskSize;

    private String diskName;

    private String diskId;

    private String status;
    
    private String resourceType;

    private String flag;
    	
    /**
     * 云硬盘所属的业务ID
     */
    private String businessId;
    
	private String businessName;

    @SuppressWarnings("unchecked")
    public String execute() {
        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = getCurrentUser();
        DiskInfo diskInfo = new DiskInfo();
        String userId = user.getUserId();
        diskInfo.setHostId(hostId);
        diskInfo.setDiskSize(diskSize);
        diskInfo.setDiskName(diskName);
        diskInfo.setDiskId(diskId);
        // 如果是在虚拟机详情页挂载云硬盘，那么需要传递虚拟机的业务，查找相同业务下的虚拟磁盘。否则按照当前用户所属业务过滤虚拟磁盘
        if (null != businessId && !("").equals(businessId)) {
            diskInfo.setBusinessId(businessId);
        } else {
            diskInfo.setBusinessList(user.getAppIdList());
        }
        
        //根据物理机/虚拟机类型查询各自的云硬盘
        if(null != resourceType && !("").equals(resourceType)){
            diskInfo.setResourceType(resourceType);
        }

        /* 查询挂载和未挂载状态时需要的 */
        if (null != status && !status.equals("")) {
            String[] tmp = status.split(",");
            List<String> tmpList = new ArrayList<String>();
            for (int i = 0; i < tmp.length; i++)
                tmpList.add(tmp[i]);

            diskInfo.setStatusList(tmpList);
        }

        try {
        	if(null!=flag&&"1".equals(flag)){
        		diskInfos = ibatisDAO.getData("queryEBSUserList", diskInfo);
        	}else{
        		diskInfos = (List<DiskInfo>) getPage("queryEBSUserCount", "queryEBSUserList", diskInfo,
                        PageTransModel.ASYNC);	
        	}
            fomatResultData(diskInfos);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("ebs.queryList.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("ebs.queryList.fail"), userId));
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

    @SuppressWarnings("unchecked")
    public String query4Overview() {

        DiskInfo tmp = new DiskInfo();

        tmp.setBusinessList(getCurrentUser().getAppIdList());

        try {
            diskInfos = ibatisDAO.getData("queryEBSUserList", tmp);
            fomatResultData(diskInfos);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("ebs.queryList.fail"), getCurrentUserId()), e);
            this.addActionError(MessageFormat.format(getText("ebs.queryList.fail"),
                    getCurrentUserId()));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 格式化生效时间、到期时间
     * @param ls要格式化的列表
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
            diskInfo.setDiskSize(String.format("%.1f", Float.parseFloat(diskInfo.getDiskSize())));
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

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
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


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
