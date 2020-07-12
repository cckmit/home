package com.neusoft.mid.cloong.web.page.user.order.lb;

public enum Cancelable {

    bindTakingEffect("该Vlan有待审批的虚拟机，不能释放"), toBeCreated("待创建资源不能释放test"), inUse("该Vlan有在用的ip,不能释放");

    private String msg;

    private Cancelable(String str) {
        msg = str;
    }

    /**
     * 获取msg字段数据
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置msg字段数据
     * @param msg The msg to set.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
