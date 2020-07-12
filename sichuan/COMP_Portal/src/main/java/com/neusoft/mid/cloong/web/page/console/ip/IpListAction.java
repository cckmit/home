/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.ip;

import java.text.MessageFormat;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 公有ip列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class IpListAction extends PageAction {

    private static final long serialVersionUID = -5471800875775849067L;

    private static LogService logger = LogService.getLogger(IpListAction.class);

   
    private List<PublicIpInfo> ips;
    
    private PublicIpInfo ip =new PublicIpInfo();

    private String pageBar;
    
    private String errMsg;
    
    @SuppressWarnings("unchecked")
    public String execute() {
        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = getCurrentUser();
        String userId = user.getUserId();
        ip.setAppIdList(user.getAppIdList());
        ips=getPage("getPublicIpCount", "getPublicIpList",ip);
        for (PublicIpInfo info : ips) {
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

    /**
     * 页面ajax 请求ip列表
     */
    public String queryIpList(){
        ips = getPage("getIpCount", "getIpList", ip,PageTransModel.ASYNC);
        pageBar=super.getPageBar();
        return ConstantEnum.SUCCESS.toString();
    }


    public List<PublicIpInfo> getIps() {
        return ips;
    }



    public void setIps(List<PublicIpInfo> ips) {
        this.ips = ips;
    }



    public PublicIpInfo getIp() {
        return ip;
    }



    public void setIp(PublicIpInfo ip) {
        this.ip = ip;
    }



    public String getPageBar() {
        return pageBar;
    }



    public void setPageBar(String pageBar) {
        this.pageBar = pageBar;
    }



    public String getErrMsg() {
        return errMsg;
    }



    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    


}
