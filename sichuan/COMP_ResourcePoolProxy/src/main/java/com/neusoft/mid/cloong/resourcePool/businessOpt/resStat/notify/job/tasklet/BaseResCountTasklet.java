/*******************************************************************************
 * @(#)BaseResCountService.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;

/**
 * TODO 这里请补充该类型的简述说明
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 下午4:47:48
 * @param <T> 任务执行的资源类型
 * @param <O> 任务执行的反馈结果
 */
public abstract class BaseResCountTasklet<T, O> {

    /**
     * execute 执行任务
     * @param parmeter 要执行数据的内容
     * @param startTime 周期开始时间
     * @param endTime 周期结束时间
     * @param periodTime 周期时间
     * @param resPoolId 资源池ID
     * @param resPoolPartId 资源池分区ID
     * @return 返回
     * @throws TaskletException  任务失败异常
     */
    public abstract O execute(T parmeter, final String startTime, final String endTime,
            final int periodTime, final String resPoolId, final String resPoolPartId) throws TaskletException;

    /**
     * 持久化类
     */
    protected IbatisDAO ibatisDAO;

    /**
     * 设置ibatisDAO字段数据
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

}
