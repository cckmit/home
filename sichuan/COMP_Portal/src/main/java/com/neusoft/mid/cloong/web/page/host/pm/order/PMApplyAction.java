package com.neusoft.mid.cloong.web.page.host.pm.order;

import com.neusoft.mid.cloong.web.ConstantEnum;

/**
 * 申请物理机跳转前的Action
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午05:11:18
 */
public class PMApplyAction {
    private String itemId;

    private String osId;

    public String execute() {
        return ConstantEnum.SUCCESS.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

}
