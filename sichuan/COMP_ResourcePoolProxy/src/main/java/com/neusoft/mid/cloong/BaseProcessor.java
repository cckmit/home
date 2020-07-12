/*******************************************************************************
 * @(#)BaseProcessor.java 2013-3-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import java.util.regex.Pattern;

import com.neusoft.mid.cloong.common.AcquireStandardResPoolId;
import com.neusoft.mid.cloong.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.grains.commons.route.api.impl.RouteService;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 请求处理类的基类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-28 上午10:13:49
 */
public abstract class BaseProcessor implements RequestProcessor {

    private static LogService logger = LogService.getLogger(BaseProcessor.class);

    protected static final String SUCCESS = "ok";

    protected static final String FAILURE = "failure";

    /**
     * 请求中虚拟机操作流水号最大长度
     */
    protected static final int SERIAL_NUM_LENGTH = 30;

    /**
     * 消息序列号生成器
     */
    private TransactionIdGenerator transactonGen;

    /**
     * 时间戳生成器
     */
    private TimeStampGenerator timeStampgen;

    /**
     * 资源池信息缓存服务
     */
    private RouteService resourcePoolCacheService;

    /** 查询规格对应资源池ID方法 **/
    protected AcquireStandardResPoolId acquireStandardResPoolId;

    public TransactionIdGenerator getTransactonGen() {
        return transactonGen;
    }

    public void setTransactonGen(TransactionIdGenerator transactonGen) {
        this.transactonGen = transactonGen;
    }

    public TimeStampGenerator getTimeStampgen() {
        return timeStampgen;
    }

    public void setTimeStampgen(TimeStampGenerator timeStampgen) {
        this.timeStampgen = timeStampgen;
    }

    public RouteService getResourcePoolCacheService() {
        return resourcePoolCacheService;
    }

    public void setResourcePoolCacheService(RouteService resourcePoolCacheService) {
        this.resourcePoolCacheService = resourcePoolCacheService;
    }

    /**
     * 请求长度正则表达式，长度在1到30
     */
    protected static final Pattern P_STRING = Pattern.compile(".{1,31}");

    /**
     * 生成异常状态错误报文
     * @param code 错误码
     * @param rcxtResp 应答传输报文
     */
    protected void assembleErrorResp(InterfaceResultCode code, RuntimeContext rcxtResp, RPPBaseResp resp) {
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        rcxtResp.setAttribute(RPPBaseResp.RESP_BODY, resp);
    }

    /**
     * 检查执行参数的长度是否合法(<=31位长度为合法)
     * @author fengj<br>
     *         2015年2月13日 上午9:12:40
     * @param req 请求
     * @param paras 请求的参数
     * @return 是否合法
     */
    protected boolean validateInputPara(RuntimeContext req, String... paras) {
        for (String para : paras) {
            if (req.getAttribute(para) == null || (!P_STRING.matcher((String) req.getAttribute(para)).matches())) {
                logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查参数 " + para, null);
                return false;
            }
        }
        return true;
    }

    public void setAcquireStandardResPoolId(AcquireStandardResPoolId acquireStandardResPoolId) {
        this.acquireStandardResPoolId = acquireStandardResPoolId;
    }

    public abstract String process(RuntimeContext req, RuntimeContext resp);

    @Override
    public String doProcess(RuntimeContext reqCxt, RuntimeContext respCxt) {
        if (reqCxt.getAttribute(RPPBaseReq.REQ_BODY) != null
                && reqCxt.getAttribute(RPPBaseReq.REQ_BODY) instanceof RPPBaseReq) {
            RPPBaseReq req = (RPPBaseReq) reqCxt.getAttribute(RPPBaseReq.REQ_BODY);
            req.setSerialNum((String) reqCxt.getAttribute("SERIAL_NUM"));
        }
        return this.process(reqCxt, respCxt);
    }

    /**
     * 为请求Bean设置公共参数
     * @author fengj<br>
     *         2015年2月28日 下午3:29:10
     * @param req
     * @param ipQueryReq
     */
    protected void setCommAttribute(String resourcePoolId, String resourcePoolPartId, ReqBodyInfo reqBody)
            throws ServiceStopException, UnmatchException {
        reqBody.setResourcePoolId(resourcePoolId);
        reqBody.setResourcePoolPartId(resourcePoolPartId);
        reqBody.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        reqBody.setTransactionID(this.getTransactonGen().generateTransactionId(reqBody.getResourcePoolId(),
                reqBody.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                reqBody.getResourcePoolId());
        reqBody.setPassword(resourceInfo.getUserPassword());
        reqBody.setResourceUrl(resourceInfo.getResourcePoolUrl());
    }
}
