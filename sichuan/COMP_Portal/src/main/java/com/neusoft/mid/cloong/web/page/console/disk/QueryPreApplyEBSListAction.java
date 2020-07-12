/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.disk.info.PreApplyDiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询云硬盘列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class QueryPreApplyEBSListAction extends PageAction {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = -5471800875775849067L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(QueryPreApplyEBSListAction.class);

    /**
     * 虚拟机实例信息
     */
    private List<PreApplyDiskInfo> diskInfos;

    /**
     * 存储名称
     */
    private String diskName;
    
    private Map<String, Object> ebsResultInfoMap;

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

        PreApplyDiskInfo preApplyDiskInfo = new PreApplyDiskInfo();
        preApplyDiskInfo.setEbsName(diskName);

        try {
            diskInfos = (List<PreApplyDiskInfo>) getPage("queryPreApplyEBSUserListCount",
                    "queryPreApplyEBSUserList", preApplyDiskInfo, PageTransModel.ASYNC);
            fomatResultData(diskInfos);
            ebsResultInfoMap = new HashMap<String, Object>();
            ebsResultInfoMap.put("list", diskInfos);
            ebsResultInfoMap.put("page", getPageBar());
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("vm.queryList.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vm.queryList.fail"), userId));
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 格式化生效时间、到期时间
     * @param ls 要格式化的列表
     */
    private void fomatResultData(List<PreApplyDiskInfo> ls) {
        for (PreApplyDiskInfo preApplyDiskInfo : ls) {
            String sbt = preApplyDiskInfo.getCreateTime() == null ? "" : preApplyDiskInfo.getCreateTime().trim();
            String ebt = preApplyDiskInfo.getUpdateTime() == null ? "" : preApplyDiskInfo.getUpdateTime().trim();
            if (!"".equals(ebt))
            	preApplyDiskInfo.setUpdateTime(DateParse.parse(preApplyDiskInfo.getUpdateTime()));
            if (!"".equals(sbt)) {
                String effectiveTime = DateParse.parse(preApplyDiskInfo.getCreateTime());
                preApplyDiskInfo.setCreateTime(effectiveTime);
            }

        }
    }

	public List<PreApplyDiskInfo> getDiskInfos() {
		return diskInfos;
	}

	public void setDiskInfos(List<PreApplyDiskInfo> diskInfos) {
		this.diskInfos = diskInfos;
	}

	public String getDiskName() {
		return diskName;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	public Map<String, Object> getEbsResultInfoMap() {
		return ebsResultInfoMap;
	}

	public void setEbsResultInfoMap(Map<String, Object> ebsResultInfoMap) {
		this.ebsResultInfoMap = ebsResultInfoMap;
	}

}
