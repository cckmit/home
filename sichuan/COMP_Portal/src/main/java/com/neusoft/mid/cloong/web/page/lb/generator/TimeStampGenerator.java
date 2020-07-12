/*******************************************************************************
 * @(#)SequenceGenerator.java 2013-2-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.lb.generator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

/**
 * 时间戳生成器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午03:31:50
 */
public class TimeStampGenerator {

    /**
     * 时间格式yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat
            .forPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式YYYYMMDDhhmmss
     */
    private static final DateTimeFormatter DATE_FORMAT_YYYYMMddHHmmss = DateTimeFormat
            .forPattern("YYYYMMddHHmmss");

    public String generateTimeStamp() {
        return DATE_FORMAT.print(new DateTime());
    }

    public String generateTimeStampYYYYMMddHHmmss() {
        return DATE_FORMAT_YYYYMMddHHmmss.print(new DateTime());
    }

}
