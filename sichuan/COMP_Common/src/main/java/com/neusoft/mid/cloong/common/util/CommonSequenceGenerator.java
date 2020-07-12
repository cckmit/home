/*******************************************************************************
 * @(#)CommonSequenceGenerator.java 2014年2月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 通用序列号生成类，在过去的生成类基础上修改而来
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月15日 下午4:10:58
 */
public class CommonSequenceGenerator {

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 随机数最大值
     */
    private static final int MAX_VALUE = 9999;

    /**
     * 随机数最大长度
     */
    private static final int MAX_LENGTH = 4;

    /**
     * 流水号锁
     */
    private byte[] serialNumLock = new byte[0];

    /**
     * 实例ID
     */
    private String prefix = "";

    /**
     * 流水号随机数
     */
    private int serialNumIdSequence = 0;

    /**
     * 上次生成流水号的时间戳
     */
    private String serialNumLastTimeStamp = "";

    /**
     * 
     * generatorSerialNum 生成流水号
     * @return 生成的流水号
     */
    public String generatorSerialNum() {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append(prefix).append(currentTimeStamp);
        int tempSequence = 0;
        synchronized (serialNumLock) {
            if (!serialNumLastTimeStamp.equals(currentTimeStamp)
                    || ++serialNumIdSequence > MAX_VALUE) {
                serialNumIdSequence = 0;
                serialNumLastTimeStamp = currentTimeStamp;
            }
            tempSequence = serialNumIdSequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    /**
     * 获取prefix字段数据
     * @return Returns the prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设置prefix字段数据
     * @param prefix The prefix to set.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
