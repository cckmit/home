/*******************************************************************************
 * @(#)LogContext.java 2015年4月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通用日志打印类，会将日志输出到对应的
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年4月7日 上午10:56:50
 */
public class LogProcessor {

    // LogProcessor类自身的logger
    private static LogService logger = LogService.getLogger(LogProcessor.class);

    // 日志信息各个字段之间的分隔符
    private static final String LogSeparator = ";";

    // 所有logger的缓存Map.
    // KEY——log的文件名; VALUE——log的值
    private static Map<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();

    /**
     * 将作为参数的日志对象指定的属性(LogAttr注解标示的),打印到该类注解(LogAttr注解name属性)指定的日志文件中.
     * 
     * <pre>
     * 传入的logContext参数需要标注LogAttr、LogColumn注解，如下类。
     * 其中logAppenderName参数可选,logFileName必选。
     * 
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
     * @author fengj<br>
     *         2015年4月7日 下午4:22:03
     * @param logContext 要被打印的日志context
     * @throws LogProcessException 日志处理异常
     */
    public static void process(Object logContext) throws LogProcessException {

        // 1.加载logContext类注解,获取日志文件名
        Logger theLogger = loadLogger(logContext);

        // 2.获取要打印的日志字符串
        String logString = generateLogString(logContext);

        // 3.将日志内容打印到log文件
        theLogger.info(logString);

    }

    /**
     * 加载类注解,获取日志类对应的logger
     * @author fengj<br>
     *         2015年4月7日 下午4:28:00
     * @param logContext 日志对象
     * @return 日志文件名称
     * @throws LogProcessException 从注解中未获取到文件名称时,会抛出此异常
     */
    private static Logger loadLogger(Object logContext) throws LogProcessException {
        LogAttr objectLogInfo = logContext.getClass().getAnnotation(LogAttr.class);
        if (objectLogInfo == null) {
            String errorMsg = "试图对一个没有标注LogAttr注解的对象进行日志打印,类名[" + logContext.getClass().getName() + "],请联系开发人员调整!";
            logger.error(null, errorMsg);
            throw new LogProcessException(errorMsg);
        }
        String logFileNmae = objectLogInfo.logFileName();
        if (StringUtils.isBlank(logFileNmae)) {
            String errorMsg = "从日志对象注解获取到日志的文件名为空,无法生成日志,类名[" + logContext.getClass().getName() + "],请联系开发人员调整!";
            logger.error(null, errorMsg);
            throw new LogProcessException(errorMsg);
        }
        String logAppenderName = objectLogInfo.logAppenderName();
        if (StringUtils.isBlank(logAppenderName)) {
            String errorMsg = "从日志对象注解获取到日志appender名为空,无法生成日志,类名[" + logContext.getClass().getName() + "],请联系开发人员调整!";
            logger.error(null, errorMsg);
            throw new LogProcessException(errorMsg);
        }
        String templateLoggerName = objectLogInfo.templateLoggerName();
        if (StringUtils.isBlank(templateLoggerName)) {
            String errorMsg = "从日志对象注解获取到模板Logger名为空,无法生成日志,类名[" + logContext.getClass().getName() + "],请联系开发人员调整!";
            logger.error(null, errorMsg);
            throw new LogProcessException(errorMsg);
        }

        Logger theLogger = loggerMap.get(logFileNmae);
        if (theLogger == null) {
            theLogger = Logger.getLogger(logFileNmae);
            theLogger.setAdditivity(false);
            theLogger.setLevel(Level.DEBUG);
            Logger temploateLogger = Logger.getLogger(templateLoggerName);
            if (temploateLogger == null) {
                String errorMsg = "打印定制日志时,未能加载到日志类注解指定的模板logger[" + temploateLogger + "],类名["
                        + logContext.getClass().getName() + "],请联系开发人员调整!";
                logger.error(null, errorMsg);
                throw new LogProcessException(errorMsg);
            }
            FileAppender templateAppender = (FileAppender) temploateLogger.getAppender(logAppenderName);
            if (templateAppender == null) {
                String errorMsg = "打印定制日志时,未能加载到日志类注解指定的模板appender[" + templateAppender + "],类名["
                        + logContext.getClass().getName() + "],请联系开发人员调整!";
                logger.error(null, errorMsg);
                throw new LogProcessException(errorMsg);
            }

            String templateFile = templateAppender.getFile();
            String newLogDirPath = StringUtils.substringBeforeLast(templateFile, "/");
            String newLogFile = newLogDirPath + "/" + logFileNmae;
            if (newLogDirPath.length() == templateFile.length()) {
                // 即只设置了文件名.
                newLogFile = logFileNmae;
            }

            FileAppender newAppender;
            try {
                newAppender = (FileAppender) BeanUtils.cloneBean(templateAppender);
            } catch (Exception e) {
                String errorMsg = "打印定制日志时,创建日志类的Appender异常,类名[" + logContext.getClass().getName() + "],请联系开发人员调整!";
                logger.error(null, errorMsg, e);
                throw new LogProcessException(errorMsg, e);
            }
            newAppender.setFile(newLogFile);
            newAppender.setName(newLogFile);
            newAppender.activateOptions();
            theLogger.addAppender(newAppender);

            loggerMap.put(logFileNmae, theLogger);
        }

        return theLogger;
    }

    /**
     * 根据接收到的logContext,生成对应的日志字符串
     * @author fengj<br>
     *         2015年4月7日 下午4:15:21
     * @param logContext 待处理的日志context
     * @return 日志字符创内容
     * @throws LogProcessException 日志处理异常
     */
    private static String generateLogString(Object logContext) throws LogProcessException {

        StringBuilder logStringBuilder = new StringBuilder();
        try {

            List<String> logAttrValueList = new ArrayList<String>();
            Field[] fields = logContext.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean hasAnnotation = field.isAnnotationPresent(LogColumn.class);
                if (hasAnnotation) {
                    LogColumn annotation = field.getAnnotation(LogColumn.class);
                    int logAttrIndex = annotation.value();
                    if (logAttrIndex < 0) {
                        String errorMsg = "日志context类[" + logContext.getClass().getName() + "]属性[" + field.getName()
                                + "]的LogAttr注解value为[" + logAttrIndex + "]不合法,请联系开发人员调整!";
                        throw new LogProcessException(errorMsg);
                    }
                    // 如果index超出list的容量,则扩展list
                    for (int i = logAttrValueList.size() - 1; i < logAttrIndex; i++) {
                        logAttrValueList.add("");
                    }
                    logAttrValueList.set(logAttrIndex, field.get(logContext).toString());
                }
            }

            for (String logAttrValue : logAttrValueList) {
                logStringBuilder.append(logAttrValue).append(LogSeparator);
            }
            if (logStringBuilder.length() > 0) {
                logStringBuilder.deleteCharAt(logStringBuilder.length() - 1);
            }

        } catch (LogProcessException e) {
            logger.error(null, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(null, "处理日志context的数据时发生异常!", e);
            throw new LogProcessException(e);
        }

        return logStringBuilder.toString();
    }

}
