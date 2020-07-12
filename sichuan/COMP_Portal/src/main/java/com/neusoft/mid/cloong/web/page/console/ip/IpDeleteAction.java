/*******************************************************************************
 * @(#)DiskDeleteAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.ip;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSDelete;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除公有ip
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-21 下午05:53:57
 */
public class IpDeleteAction extends BaseAction {

    private static final long serialVersionUID = 4407178327415405154L;

    private static LogService logger = LogService.getLogger(IpDeleteAction.class);

    private PublicIpInfo ipInfo = new PublicIpInfo();

    /**
     * 页面返回路径
     */
    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 返回消息
     */
    private String resultMessage = "";

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    /**
     * 删除公网ip
     * @return
     */
    public String delPublicIp() {

        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        updateBatchVO.add(new BatchVO("MOD", "deleteCasePublicIp", ipInfo));
        updateBatchVO.add(new BatchVO("MOD", "deletePublicIp", ipInfo));
        try {
            ibatisDAO.updateBatchData(updateBatchVO);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public PublicIpInfo getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(PublicIpInfo ipInfo) {
        this.ipInfo = ipInfo;
    }
}
