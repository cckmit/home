/*******************************************************************************
 * @(#)OrderProcessor.java 2015年3月6日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.order.info;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;

/**
 * 订单操作的处理类.由于程序中没有统一的订单处理类,因此慢慢将订单操作抽取到此类来
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月6日 下午4:35:01
 */
public class OrderProcessor {

    /**
     * 将订单更新为生效状态，同时更新订单生效、过期时间
     * @author fengj<br>
     *         2015年3月6日 下午4:13:10
     * @param currentTimestamp 处理订单的时间戳 格式 yyyy-MM-dd HH:mm:ss
     * @param orderInfo 要更新的订单bean
     */
    public static BatchVO genOrderToEffectiveVO(String currentTimestamp, OrderInfo orderInfo) {

        Map<String, String> updateOrderParam = new HashMap<String, String>();

        String effectiveTime = DateParse.parseTime(currentTimestamp);
        updateOrderParam.put("effectiveTime", effectiveTime);
        if (!StringUtils.isBlank(orderInfo.getLengthTime()) && !"0".equals(orderInfo.getLengthTime().trim())) {
            // 将请求的yyyy-MM-dd HH:mm:ss时间转换为yyyyMMddHHmmss
            String expireTime = DateParse.countExpireTime(orderInfo.getLengthUnit(), effectiveTime,
                    orderInfo.getLengthTime());
            updateOrderParam.put("expireTime", expireTime);
        }

        updateOrderParam.put("orderId", orderInfo.getOrderId());
        updateOrderParam.put("orderStatus", OrderInfo.ORDER_STATUS_EFFECT);
        BatchVO vo = new BatchVO(BatchVO.OPERATION_MOD, "updateOrder", updateOrderParam);
        return vo;
    }

}
