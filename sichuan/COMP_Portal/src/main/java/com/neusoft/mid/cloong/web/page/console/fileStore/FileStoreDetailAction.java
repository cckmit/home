/*******************************************************************************
 * @(#)DiskDetailAction.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.fileStore;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.fileStore.info.FileStoreInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 显示文件存储详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:16:45
 */
public class FileStoreDetailAction extends BaseAction {

    private static final long serialVersionUID = 2501512361728019512L;

    private static LogService logger = LogService.getLogger(FileStoreDetailAction.class);

   private FileStoreInfo fs=new FileStoreInfo();
    /**
     * 实例编号
     */
    private String caseId;

    public String execute() {
        fs.setCaseId(caseId);
        
        try {
            fs=(FileStoreInfo) ibatisDAO.getSingleRecord("getFsDetail", fs);
            fs.setCreateTime(DateParse.parse(fs.getCreateTime()));
            fs.setUpdateTime(DateParse.parse(fs.getUpdateTime()));
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

    public FileStoreInfo getFs() {
        return fs;
    }

    public void setFs(FileStoreInfo fs) {
        this.fs = fs;
    }
}
