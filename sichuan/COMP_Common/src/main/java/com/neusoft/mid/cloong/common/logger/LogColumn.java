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

import org.springframework.beans.factory.annotation.Required;

/**
 * 操作日志记录注解,被注解的类属性，会被写入日志. 注解的value属性用来标示写入日志文件的顺序.<br>
 * <b>注意:顺序值从0开始,不是1</b> <br>
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
@Target({ ElementType.FIELD })
public @interface LogColumn {

    /**
     * 默认属性,标示日志各字段的顺序
     */
    @Required
    int value() default -1;

}
