/*******************************************************************************
 * @(#)SequenceGenerator.java 2013-1-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.util;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 序列号生成器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-24 下午04:27:11
 */
public class SequenceGenerator implements Serializable{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

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
     * 条目ID锁
     */
    private byte[] itemLock = new byte[0];

    /**
     * 资源规格公用前缀
     */
    private static final String STANDARD_PREFIX = "SD";

    /**
     * 虚拟机规格随机数
     */
    private int vmStandardSequence = 0;

    /**
     * 上次生成规格ID的时间戳
     */
    private String standardLastTimeStamp = "";

    /**
     * 虚拟机规格锁
     */
    private byte[] vmStandardLock = new byte[10];

    /**
     * 服务目录ID锁
     */
    private byte[] catalogLock = new byte[0];

    /**
     * 条目ID
     */
    private String itemId = "SI";

    /**
     * 服务目录ID
     */
    private String catalogId = "CL";

    /**
     * 虚拟机条目ID随机数
     */
    private int itemIdSequence = 0;

    /**
     * 服务目录ID随机数
     */
    private int catalogIdSequence = 0;

    /**
     * 上次生成虚拟机实例ID的时间戳
     */
    private String itemLastTimeStamp = "";

    /**
     * 上次生成服务目录ID的时间戳
     */
    private String catalogLastTimeStamp = "";
    
    /**
     * 订单锁
     */
    private byte[] orderLock = new byte[0];

    /**
     * 实例ID锁
     */
    private byte[] caseLock = new byte[0];

    /**
     * 流水号锁
     */
    private byte[] serialNumLock = new byte[0];

    /**
     * 实例ID
     */
    private String instanceId = "TT";

    /**
     * 订单ID四位随机数
     */
    private int orderSequence = 0;

    /**
     * 虚拟机实例ID随机数
     */
    private int caseIdSequence = 0;

    /**
     * 虚拟机操作流水号随机数
     */
    private int serialNumIdSequence = 0;

    /**
     * 上次生成订单序列号的时间戳
     */
    private String orderLastTimeStamp = "";

    /**
     * 上次生成虚拟机实例ID的时间戳
     */
    private String caseLastTimeStamp = "";

    /**
     * 上次生成虚拟机操作流水号的时间戳
     */
    private String serialNumLastTimeStamp = "";

    /**
     * 生成订单序列号 格式为：ORDER-[资源类型]-[实例ID]-yyyyMMddHHmmss+四位随机数
     * @param type 订单类型
     * @return
     */
    public String generatorOrderId(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("ORDER-").append(type).append("-").append(instanceId).append("-")
                .append(currentTimeStamp);
        int tempSequence = 0;
        synchronized (orderLock) {
            if (!orderLastTimeStamp.equals(currentTimeStamp) || ++orderSequence > MAX_VALUE) {
                orderSequence = 0;
                orderLastTimeStamp = currentTimeStamp;
            }
            tempSequence = orderSequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    /**
     * 生成虚拟机条目Id 格式为：SI-[资源类型]-yyyyMMddHHmmss+四位随机数
     * @param type 资源类型
     * @return
     */
    public String generatorItemId(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append(itemId).append("-").append(type).append("-").append(currentTimeStamp);
        int tempSequence = 0;
        synchronized (itemLock) {
            if (!itemLastTimeStamp.equals(currentTimeStamp) || ++itemIdSequence > MAX_VALUE) {
                itemIdSequence = 0;
                itemLastTimeStamp = currentTimeStamp;
            }
            tempSequence = itemIdSequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    /**
     * 生成服务目录Id 格式为：CL-[资源类型]-yyyyMMddHHmmss+四位随机数
     * @param ResourceTypeEnum type 资源类型
     * @return
     */
    public String generatorCatalogId(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        if (type.equals("0")) {
            type = "VM";
        } else if (type.equals("1")) {
            type = "PH";
        } else if (type.equals("4")) {
            type = "BK";
        } else if (type.equals("5")) {
            type = "BS";
        } else if (type.equals("6")) {
            type = "OS";
        } else if (type.equals("7")) {
            type = "IP";
        } else if (type.equals("8")) {
            type = "BW";
        } else if (type.equals("9")) {
            type = "SG";
        } else if (type.equals("10")) {
            type = "CM";
        }
        sb.append(catalogId).append("-").append(type).append("-").append(currentTimeStamp);
        int tempSequence = 0;
        synchronized (catalogLock) {
            if (!catalogLastTimeStamp.equals(currentTimeStamp) || ++catalogIdSequence > MAX_VALUE) {
                catalogIdSequence = 0;
                catalogLastTimeStamp = currentTimeStamp;
            }
            tempSequence = catalogIdSequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    public String generateStandardId(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append(STANDARD_PREFIX).append("-").append(type).append("-").append(currentTimeStamp)
                .append("-");
        int tempSequence = 0;
        synchronized (vmStandardLock) {
            if (!standardLastTimeStamp.equals(currentTimeStamp) || ++vmStandardSequence > MAX_VALUE) {
                vmStandardSequence = 0;
                standardLastTimeStamp = currentTimeStamp;
            }
            tempSequence = vmStandardSequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    /**
     * 生成虚拟机实例Id 格式为：CASE-[资源类型]- [实例ID]-yyyyMMddHHmmss+四位随机数
     * @param type 资源类型
     * @return
     */
    public String generatorCaseId(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("CASE-").append(type).append("-").append(instanceId).append("-")
                .append(currentTimeStamp);
        int tempSequence = 0;
        synchronized (caseLock) {
            if (!caseLastTimeStamp.equals(currentTimeStamp) || ++caseIdSequence > MAX_VALUE) {
                caseIdSequence = 0;
                caseLastTimeStamp = currentTimeStamp;
            }
            tempSequence = caseIdSequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    /**
     * 生成虚拟机操作流水号
     * @param type
     * @return
     */
    public String generatorVMSerialNum(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("VM-").append(type).append("-").append(instanceId).append("-")
                .append(currentTimeStamp);
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
     * 生成物理机操作流水号
     * @param type
     * @return
     */
    public String generatorPMSerialNum(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("PH-").append(type).append("-").append(instanceId).append("-")
                .append(currentTimeStamp);
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
     * 生成虚拟机备份操作流水号
     * @param type
     * @return
     */
    public String generatorVmBakSerialNum(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("BK-").append(type).append("-").append(instanceId).append("-")
                .append(currentTimeStamp);
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
     * 生成虚拟机快照操作流水号
     * @param type 类型
     * @return String
     */
    public String generatorVmSnapshotSerialNum(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("VMSN-").append(type).append("-").append(instanceId).append("-")
                .append(currentTimeStamp);
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

    public void setInstanceId(String itemId) {
        this.itemId = itemId;
    }

}
