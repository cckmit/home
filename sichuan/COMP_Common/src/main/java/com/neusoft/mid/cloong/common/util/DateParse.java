/*******************************************************************************
 * @(#)DateParse.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期时间转换工具类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-14 上午10:18:20
 */
public final class DateParse {

    private DateParse() {

    }

    /**
     * 目的时间格式yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEST_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 源时间格式yyyyMMddHHmmss
     */
    private static final DateTimeFormatter SOURCE_FORMAT = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    
    
    /**
     * 目的时间格式yyyy-MM-dd
     */
    private static final DateTimeFormatter DEST_FORMAT1 = DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * 源时间格式yyyyMMdd
     */
    private static final DateTimeFormatter SOURCE_FORMAT1 = DateTimeFormat.forPattern("yyyyMMdd");

    /**
     * 把yyyyMMddHHmmss时间转换为yyyy-MM-dd HH:mm:ss
     * @param sourceDate 输入时间，格式为yyyyMMddHHmmss
     * @return 输出时间，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String parse(String sourceDate) {
        if (sourceDate.isEmpty()) {
            return "";
        } else {
            return DEST_FORMAT.print(SOURCE_FORMAT.parseDateTime(sourceDate));
        }
    }
    
    /**
     * 把yyyyMMdd时间转换为yyyy-MM-dd
     * @param sourceDate 输入时间，格式为yyyyMMdd
     * @return 输出时间，格式为yyyy-MM-dd
     */
    public static String parse1(String sourceDate) {
        if (sourceDate.isEmpty()) {
            return "";
        } else {
            return DEST_FORMAT1.print(SOURCE_FORMAT1.parseDateTime(sourceDate));
        }
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss时间转换为yyyyMMddHHmmss
     * @param sourceDate 输入时间，格式为yyyy-MM-dd HH:mm:ss
     * @return 输出时间，格式为yyyyMMddHHmmss
     */
    public static String parseTime(String sourceDate) {
        // 此处没有做严格格式判断
        if (!sourceDate.contains("-")) {
            return sourceDate;
        }

        if (sourceDate.isEmpty()) {
            return "";
        } else {
            return SOURCE_FORMAT.print(DEST_FORMAT.parseDateTime(sourceDate));
        }
    }

    /**
     * 把yyyyMMddHHmmss时间加上若干小时，然后转换为yyyy-MM-dd HH:mm:ss格式
     * @param sourceDate 输入时间，格式为yyyyMMddHHmmss
     * @param increasementHour 需要增加的小时数
     * @return 若干天后的输出时间，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String parseAndAddHour(String sourceDate, int increasementHour) {
        return DEST_FORMAT.print(SOURCE_FORMAT.parseDateTime(sourceDate).withFieldAdded(DurationFieldType.hours(),
                increasementHour));
    }

    public static String generateDateFormatyyyyMMddHHmmss() {
        return SOURCE_FORMAT.print(new DateTime());

    }

    /**
     * 将给定时间转换为格式为yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String generateDateFormatLong(Date date) {
        return DEST_FORMAT.print(new DateTime(date));
    }

    /**
     * 将给定的字符串yyyy-MM-dd HH:mm:ss转换为date
     * @param date
     * @return
     */
    public static Date generateDateFromLongString(String dateString) {
        return DEST_FORMAT.parseDateTime(dateString).toDate();
    }

    /**
     * 将yyyMMddHHmmss格式的时间加上若干小时
     * @param sourceDate sourceDate 输入时间，格式为yyyyMMddHHmmss
     * @param hour increasementHour 需要增加的小时数
     * @return
     */
    public static String addHour(String sourceDate, int hour) {
        return SOURCE_FORMAT.print(SOURCE_FORMAT.parseDateTime(sourceDate).withFieldAdded(DurationFieldType.hours(),
                hour));
    }

    /**
     * 将yyyMMddHHmmss格式的时间加上若干年
     * @param sourceDate sourceDate 输入时间，格式为yyyyMMddHHmmss
     * @param hour increasementHour 需要增加的年
     * @return
     */
    public static String addYear(String sourceDate, int year) {
        return SOURCE_FORMAT.print(SOURCE_FORMAT.parseDateTime(sourceDate).withFieldAdded(DurationFieldType.years(),
                year));
    }

    /**
     * 将yyyMMddHHmmss格式的时间加上若干月
     * @param sourceDate sourceDate 输入时间，格式为yyyyMMddHHmmss
     * @param hour increasementHour 需要增加的月数
     * @return
     */
    public static String addMonth(String sourceDate, int month) {
        return SOURCE_FORMAT.print(SOURCE_FORMAT.parseDateTime(sourceDate).withFieldAdded(DurationFieldType.months(),
                month));
    }

    /**
     * 将yyyMMddHHmmss格式的时间加上若干天
     * @param sourceDate sourceDate 输入时间，格式为yyyyMMddHHmmss
     * @param hour increasementHour 需要增加的天数
     * @return
     */
    public static String addDay(String sourceDate, int day) {
        return SOURCE_FORMAT.print(SOURCE_FORMAT.parseDateTime(sourceDate)
                .withFieldAdded(DurationFieldType.days(), day));
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式转换成yyyMMddHHmmss
     * @param sourceDate sourceDate 输入时间，格式为yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String fomartDay(String sourceDate) {
        return SOURCE_FORMAT.print(DEST_FORMAT.parseDateTime(sourceDate));
    }

    /**
     * countExpireTime 根据计费类型、生效时间、计费时长.计算到期日期
     * @param lengthUnit 计费类型
     * @param effectiveTime 生效时间
     * @param lengthTime 时长
     * @return 返回到期时间格式为yyyyMMddHHmmss
     */
    public static String countExpireTime(String lengthUnit, String effectiveTime, String lengthTime) {
        String expireTime = "";
        String mondth = "m";
        String year = "y";
        String date = "d";
        String hour = "h";
        if (year.equals(lengthUnit.toLowerCase())) {
            int time = Integer.parseInt(lengthTime);
            expireTime = DateParse.addYear(effectiveTime, time);
        } else if (mondth.equals(lengthUnit.toLowerCase())) {
            int time = Integer.parseInt(lengthTime);
            expireTime = DateParse.addMonth(effectiveTime, time);
        } else if (date.equals(lengthUnit.toLowerCase())) {
            int time = Integer.parseInt(lengthTime);
            expireTime = DateParse.addDay(effectiveTime, time);
        } else if (hour.equals(lengthUnit.toLowerCase())) {
            int time = Integer.parseInt(lengthTime);
            expireTime = DateParse.addHour(effectiveTime, time);
        }

        return expireTime;
    }
}
