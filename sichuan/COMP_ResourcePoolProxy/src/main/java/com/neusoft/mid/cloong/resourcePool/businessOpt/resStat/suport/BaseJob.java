/*******************************************************************************
 * @(#)Iprocessor.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.suport;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.RespReslutCode;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 处理器基类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午5:15:04
 * @param <I> 处理入参数据
 * @param <RESP> 处理出参数据
 */
public abstract class BaseJob<REQ, RESP, REQ_HEADER, RESP_HEADER> {

    /**
     * process 处理业务
     * @param parmeter 内容
     * @return 处理结果
     */
    public abstract RESP process(REQ parmeter, REQ_HEADER authInfo, RESP_HEADER simpleResponseHeader);

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(BaseJob.class);

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
     * idc ID在transactionId中的长度
     */
    public static final int idcLength = 9;

    /**
     * auth 鉴权
     * @param authInfo 鉴权信息
     * @return 结果码（通WEBSERVICE的ResultCode一致）
     */
    protected RespReslutCode checkHeaderAndAuth(String transactionId, String zoneId) {
        RespReslutCode res = RespReslutCode.SUCCESS;

        try {
            // 由于资源池会上报36为的transactionId,因此不做校验
            /*
             * if (transactionId == null || transactionId.length() != 37) {
             * logger.info("资源池上报的transactionId格式非法！"); return
             * RespReslutCode.ERROR_HEADER_TRANSACTIONID_INVALIED; }
             */
            String idcAccessId = parseIDCId(transactionId);

            // 校验资源池和分区的ID
            // 1.校验是否存在
            RespReslutCode resCode = verifyPool(zoneId, idcAccessId);
            if (!RespReslutCode.SUCCESS.equals(resCode)) {
                return resCode;
            }

            // 2.校验请求的IP地址是否合法

        } catch (SQLException e) {
            logger.error("从数据库获取资源池及分区数据时失败！", e);
            res = RespReslutCode.ERROR_OHTER;
        }
        return res;
    }

    /**
     * 从transactionId中解析资源池ID
     * @author fengj<br>
     *         2015年1月14日 下午7:02:53
     * @param transactionId
     * @return 解析号的资源池ID
     */
    protected String parseIDCId(String transactionId) {
        String idcAccessId = transactionId.substring(0, idcLength);
        return idcAccessId;
    }

    /**
     * isExistPool 资源池和资源池分区在运营系统中是否存在
     * @param zoneId 资源池分区Id
     * @param resPoolId 资源池ID
     * @return 状态码
     * @throws SQLException 数据库异常
     */
    private RespReslutCode verifyPool(String zoneId, String resPoolId) throws SQLException {
        RespReslutCode res = RespReslutCode.SUCCESS;

        String ip = (String) ibatisDAO.getSingleRecord("getIPOfResPoolById", resPoolId);

        if (ip == null) {
            logger.info("上报信息的资源池ID在运营平台中不存在！");
            res = RespReslutCode.ERROR_HEADER_IDCACCESSID_NOEXIST;
        }
        /*
         * else if (!ip.equals(ip)) { logger.info("资源池IP鉴权失败！"); res =
         * RespReslutCode.ERROR_HEADER_IDCACCESSID_IP_ILLEGAL; }
         */
        // 为了cat而写为此形式
        if (!RespReslutCode.SUCCESS.equals(res)) {
            return res;
        }

        // 由于分区ID为可选字段,因此当没有上报分区信息时,不做校验
        if (!StringUtils.isBlank(zoneId)) {
            Map<String, String> queryPam = new HashMap<String, String>();
            queryPam.put("resPoolId", resPoolId);
            queryPam.put("resPoolPartId", zoneId);
            int resPoolPartCount = ibatisDAO.getCount("getCountResPoolPartById", queryPam);
            if (resPoolPartCount < 1) {
                logger.info("上报信息的资源池分区ID在运营平台中不存在！");
                res = RespReslutCode.ERROR_HEADER_ZONEID_NOEXIST;
            }
        }

        return res;
    }

    /**
     * xmlGalendarToDateStr 从xmlGalendar转换为时间字符串
     * @param xmlGalendar xmlGalendar对象
     * @return 转化后的字符串，格式为yyyyMMddHHmmss
     */
    protected String xmlGalendarToDateStr(XMLGregorianCalendar xmlGalendar) {
        Date date = xmlGalendar.toGregorianCalendar().getTime();
        String format = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateStr = formatter.format(date);
        return dateStr;
    }

}
