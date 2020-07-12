/*******************************************************************************
 * @(#)EBSStandardMountAction.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSAttach;
import com.neusoft.mid.cloong.ebs.core.EBSDetach;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSOperatorResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.1 $ 2013-3-25 上午9:36:11
 */
public class DiskMountAction extends BaseAction {

    private static final long serialVersionUID = 4304902539511967254L;

    private static LogService logger = LogService.getLogger(DiskMountAction.class);

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    private String diskId;

    private String hostId;

    private String result;

    private String status;

    /**
     * 云硬盘挂载接口
     */
    private EBSAttach attach;

    /**
     * 云硬盘卸载接口
     */
    private EBSDetach detach;

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    public String execute() {
        DiskInfo disk = new DiskInfo();
        disk.setDiskId(diskId);
        disk.setDiskStatus(status);
        boolean flage = isMountAndUnMount();
        if (flage) {
            try {
                disk.setHostId(hostId);
                ibatisDAO.updateData("updateMountDisk", disk);
            } catch (Exception e) {
                logger.error(CommonStatusCode.INTERCEPTION_EXCEPTION_CODE, "挂载云硬盘失败", e);
                result = "挂载云硬盘失败";
                return ConstantEnum.ERROR.toString();
            }
        } else {
            return ConstantEnum.ERROR.toString();
        }
        result = "success";
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * isMountAndUnMount 调用挂载或卸载接口.判断是否成功.
     * @return 如果挂载或卸载成功返回true.
     */
    private boolean isMountAndUnMount() {
        RPPEBSOperatorReq req = new RPPEBSOperatorReq();
        req.setEbsId(diskId);
        List<String> Resources = new ArrayList<String>();
        Resources.add(hostId);
        req.setVmId(Resources);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(seqCen.generatorSerialNum());
        RPPEBSOperatorResp resp = null;
        if ("4".equals(status)) {
            resp = attach.attach(req);
            logger.info("挂载返回码：" + resp.getResultCode());
            result = "挂载云硬盘";
        } else if ("5".equals(status)) {
            hostId = "";
            resp = detach.detach(req);
            logger.info("卸载返回码：" + resp.getResultCode());
            result = "卸载云硬盘";
        }
        if (resp == null) {
            logger.info("操作状态为：" + status + "！操作异常！");
            result = "云硬盘操作异常！";
            return false;
        }
        if (SUCCEESS_CODE.equals(resp.getResultCode())) {
            result += "成功！";
            return true;
        } else {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "云硬盘操作失败，接口错误", null);
            result += "失败！";
            return false;
        }
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param diskId the diskId to set
     */
    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    /**
     * @param hostId the hostId to set
     */
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param resourcePoolId the resourcePoolId to set
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * @param resourcePoolPartId the resourcePoolPartId to set
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * @param attach the attach to set
     */
    public void setAttach(EBSAttach attach) {
        this.attach = attach;
    }

    /**
     * @param detach the detach to set
     */
    public void setDetach(EBSDetach detach) {
        this.detach = detach;
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

}
