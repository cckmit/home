/*******************************************************************************
 * @(#)PMMeterageAction.java 2014年2月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.meterage.resource;


import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;

/**
 * 资源计量管理
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月13日 下午7:37:30
 */
public class ResourceMeterageManagementAction extends BaseAction {

    /**
     * serialVersionUID : 序列号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     * 跳转计量界面
     * @return 计量界面
     */
    public String execute(){
         
        return ConstantEnum.SUCCESS.toString();
    }
    

}
