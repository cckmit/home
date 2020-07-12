/*******************************************************************************
 * @(#)LogAttr.java 2015年4月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录注解，被注解的类属性，会被写入日志. 注解的有两个属性要设置.<br>
 * 
 * <pre>
 *      logFileName     要打印的日志文件名称。必选
 *      logAppenderName 要采用的日志appender名称。可选
 * </pre>
 * 
 * <br>
 * 例如：
 * 
 * <pre>
 * 下面例子中，vmID、createTime属性会按照顺序写入日志，二vmIP属性则不会
 * 
 * &#064;LogAttr(logAppenderName = "templateLog", logFileName = "LA.log")
 * class VMCreateErrLogContext {
 * 
 *     &#064;@LogColumn(0)
 *     public String vmID;
 * 
 *     &#064;@LogColumn(1)
 *     public String createTime;
 * 
 *     public String vmIP;
 * 
 * }
 * 
 * </pre>
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年4月7日 上午10:46:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface LogAttr {

    /**
     * 标示日志的名称
     */
    String logFileName() default "";

    /**
     * 标示日志的名称
     */
    String logAppenderName() default "templateLog";

    /**
     * 标示日志的名称
     */
    String templateLoggerName() default "template";

}
