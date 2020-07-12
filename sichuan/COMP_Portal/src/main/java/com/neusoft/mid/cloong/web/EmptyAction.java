package com.neusoft.mid.cloong.web;

/** 空action
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue </a>
 * @version $Revision 1.0 $ 2009-6-24 下午07:29:48
 */
public class EmptyAction {
    private static final long serialVersionUID = 1L;

    public String execute() {
        return "SUCCESS";
    }

    // 进入系统首页
    public String firstPage() {
        return "SUCCESS";
    }

    // 鉴权失败后跳转
    public String noauthority() {
        return "SUCCESS";
    }

}
