/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.fileStore;

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
import com.neusoft.mid.cloong.web.page.console.fileStore.info.FileStoreInfo;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询分布式文件存储列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class FileStoreListAction extends PageAction {

    private static final long serialVersionUID = -5471800875775849067L;

    private static LogService logger = LogService.getLogger(FileStoreListAction.class);

   private List<FileStoreInfo> fsList;
    
    private FileStoreInfo fs =new FileStoreInfo();

    private String pageBar;
    
    @SuppressWarnings("unchecked")
    public String execute() {
        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = getCurrentUser();
        String userId = user.getUserId();
        fs.setAppIdList(user.getAppIdList());
        fsList=getPage("getFsCount", "getFsList",fs);
        for (FileStoreInfo info : fsList) {
            info.setCreateTime(DateParse.parse(info.getCreateTime()));
        }
        pageBar=super.getPageBar();
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

        }
    }


    public List<FileStoreInfo> getFsList() {
        return fsList;
    }



    public void setFsList(List<FileStoreInfo> fsList) {
        this.fsList = fsList;
    }



    public FileStoreInfo getFs() {
        return fs;
    }



    public void setFs(FileStoreInfo fs) {
        this.fs = fs;
    }



    public String getPageBar() {
        return pageBar;
    }



    public void setPageBar(String pageBar) {
        this.pageBar = pageBar;
    }


}
