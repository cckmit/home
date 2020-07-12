/*******************************************************************************
 * @(#)BaseResCountService.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.tasklet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;

/**
 * 处理任务
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 下午4:47:48
 * @param <T> 任务执行的资源类型
 */
public abstract class BaseResStatusTasklet<T> {

    /**
     * execute 执行任务
     */
    public abstract ResStatusTaskRes execute(T pmInfo,String resPoolId) throws TaskletException;

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

    /**
     * 将上报时间格式转换为入库时间格式
     * @author fengj<br>
     *         2015年1月14日 上午10:09:09
     * @param pmStopTime 性能采集停止时间
     * @throws ParseException 解析异常
     */
    protected String transformatCollectTime(String pmStopTime) throws ParseException {
        SimpleDateFormat srcDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date srcDate = srcDateFormat.parse(pmStopTime);
        SimpleDateFormat destDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String collectTimeString = destDateFormat.format(srcDate);
        return collectTimeString;
    }

}
