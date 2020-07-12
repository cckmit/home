package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 修改块存储相应类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月26日 下午3:10:59
 */
public class RPPEBSModifyResp extends RPPBaseResp {

    private static final long serialVersionUID = -4104733579872574148L;

    /**
     * 故障描述
     */
    private String faultString;

    /**
     * 获取faultString字段数据
     * @return Returns the faultString.
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * 设置faultString字段数据
     * @param faultString The faultString to set.
     */
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

}
