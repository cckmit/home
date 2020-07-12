package com.neusoft.mid.cloong.rpproxy.interfaces.vlan;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 创建虚拟硬盘接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class RPPVlanCancelResp extends RPPBaseResp implements Serializable {

    private static final long serialVersionUID = 1635945078071367626L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

}
