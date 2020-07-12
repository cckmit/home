/*******************************************************************************
 * @(#)VmListAction.java 2018年5月3日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.ip;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月3日 下午6:49:15
 */
public class VmListAction extends PageAction{
    
    private static final long serialVersionUID = 1L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(VmListAction.class);
    
    private List<VMInstanceInfo> vmList =new ArrayList<VMInstanceInfo>();
    
    private VMInstanceInfo vm=new VMInstanceInfo();
    
    private String pageBar;
    
    /**
     * ajax 请求vm list
     * @return
     */
    public String queryVmListJson(){
        
        vmList= getPage("queryVmsCount", "queryVms", vm,PageTransModel.ASYNC);
        this.pageBar=super.getPageBar();
        return ConstantEnum.SUCCESS.toString();
    }

    public List<VMInstanceInfo> getVmList() {
        return vmList;
    }

    public void setVmList(List<VMInstanceInfo> vmList) {
        this.vmList = vmList;
    }

    public VMInstanceInfo getVm() {
        return vm;
    }

    public void setVm(VMInstanceInfo vm) {
        this.vm = vm;
    }

    public String getPageBar() {
        return pageBar;
    }

    public void setPageBar(String pageBar) {
        this.pageBar = pageBar;
    }
}
