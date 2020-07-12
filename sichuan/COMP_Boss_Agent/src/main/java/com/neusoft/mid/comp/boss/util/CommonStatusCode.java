package com.neusoft.mid.comp.boss.util;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 系统错误代码
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午12:54:19
 */
public enum CommonStatusCode implements StatusCode {
    /**
     * 无效的HTTP协议
     */
    INVALID_HTTP_PROTOCOL("10001"),
    /**
     * 输入输出错误
     */
    IO_OPTION_ERROR("10002"),
    /**
     * 运行时异常
     */
    RUNTIME_EXCEPTION("10003"),
    /**
     * 无效的输入项
     */
    INVALID_INPUT_PARAMENTER("10004"),
    /**
     * 数据库操作异常
     */
    DATABASE_OPERATION_ECXEPTION("10005"),
    /**
     * 拦截到异常
     */
    INTERCEPTION_EXCEPTION_CODE("10006");
    private final String code;

    CommonStatusCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
