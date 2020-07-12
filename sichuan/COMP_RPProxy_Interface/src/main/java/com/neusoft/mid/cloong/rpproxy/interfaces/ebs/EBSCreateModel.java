package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

/**
 * 传参标志符
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月27日 下午1:30:28
 */
public enum EBSCreateModel {

    /**
     * 使用模板
     */
    template(0),
    /**
     * 使用参数
     */
    paramArray(1);

    private int value;

    EBSCreateModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
