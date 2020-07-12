package com.neusoft.mid.cloong.web.page.lb.generator;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 系统错误代码
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午12:54:19
 */
public enum CommonStatusCode implements StatusCode {
    /**
     * 输入参数错误
     */
    INPUT_PARA_ERROR("10001"),
    /**
     * 鉴权失败
     */
    AUTH_ERROR("10002"),
    /**
     * 数据库操作异常
     */
    DATABASE_OPERATION_ECXEPTION("10003"),
    /**
     * 内部错误
     */
    INTERNAL_ERROR("10004"),
    /**
     * 线程中断异常
     */
    THREAD_INTERRUPT("10005"),
    /**
     * 向资源池发送消息异常
     */
    SENDMSG_ERROR("10006"),
    /**
     * 创建JAXB实例异常
     */
    JAXBCONTEXT_EXP("10007"),
    /**
     * JAXB实例解析异常
     */
    JAXBCONTEXT_PARSE_EXCEPTION("10008"),
    /**
     * 其他异常
     */
    OTHER_EXCEPTION("99999");
    private final String code;

    CommonStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
