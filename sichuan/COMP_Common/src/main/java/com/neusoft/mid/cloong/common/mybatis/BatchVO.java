package com.neusoft.mid.cloong.common.mybatis;

/*
 * @(#)BatchVO.java
 *
 * Short Message Internet Access Solution Management System
 * Copyright 2004 Neusoft MID. All Rights Reserved.
 */

/**
 * <p>
 * 批处理VO列表：用于事务控制多次的修改。
 * </p>
 * @author <a href="mailto:sunxiwei@neusoft.com">XiWei.SUN</a>
 * @version Revision: 1.1 Date: 2005-7-15 13:19:12
 */

public class BatchVO {

    /**
     * 操作类型:ADD
     */
    public static final String OPERATION_ADD = "ADD";

    /**
     * 操作类型:DEL
     */
    public static final String OPERATION_DEL = "DEL";

    /**
     * 操作类型:MOD
     */
    public static final String OPERATION_MOD = "MOD";

    // 操作类型:ADD,DEL,MOD
    private String operate;

    // 实际操作的DAO的传入VO
    private Object object;

    // 调用ibatis的sql语句ID
    private String string;

    /**
     * @return Returns the operate.
     */
    public String getOperate() {
        return operate;
    }

    /**
     * @param operate The operate to set.
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * @param operate
     * @param object
     * @param string
     */
    public BatchVO(String operate, String string, Object object) {
        super();
        this.operate = operate;
        this.object = object;
        this.string = string;
    }

    /**
     * @return Returns the object.
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object The object to set.
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return Returns the string.
     */
    public String getString() {
        return string;
    }

    /**
     * @param string The string to set.
     */
    public void setString(String string) {
        this.string = string;
    }
}
