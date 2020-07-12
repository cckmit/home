package com.neusoft.mid.cloong.lb.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateResp;





/**
 * 虚拟机操作接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午10:13:56
 */
public interface LBCreate {

    /**
     * 
     * createVM 普通创建虚拟机，可指定vlan也可以不指定
     * @param req 创建请求
     * @return resp 响应消息
     */
	RPPLBCreateResp createLB(RPPLBCreateReq req);
    
}
