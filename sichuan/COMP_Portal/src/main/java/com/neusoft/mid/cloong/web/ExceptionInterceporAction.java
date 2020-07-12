package com.neusoft.mid.cloong.web;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 异常拦截器拦截异常后，会跳到该类中
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-9 上午11:19:50
 */
public class ExceptionInterceporAction {
    private static final long serialVersionUID = 1L;

    /**
     * 异常信息
     */
    private Throwable exception;

    /**
     * 日志相关
     */
    private static LogService logger = LogService.getLogger(ExceptionInterceporAction.class);

    public String execute() {
        logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "拦截器拦截到一个异常", exception);
        return ConstantEnum.SUCCESS.toString();
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

}
