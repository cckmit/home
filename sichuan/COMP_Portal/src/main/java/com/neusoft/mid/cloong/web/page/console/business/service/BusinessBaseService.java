/*******************************************************************************
 * @(#)BusinessBaseService.java 2014年2月7日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business.service;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;

/**
 * 服务基类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月7日 下午3:03:56
 */
public class BusinessBaseService {

    /**
     * 数据库处理DAO
     */
    protected IbatisDAO ibatisDAO;

    /**
     * 获取ibatisDAO字段数据
     * @return Returns the ibatisDAO.
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
