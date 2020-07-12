package com.neusoft.mid.cloong.host.pm.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.host.pm.ICreatePMSender;
import com.neusoft.mid.cloong.host.pm.PMCreateFail;
import com.neusoft.mid.cloong.host.pm.PMCreateReq;
import com.neusoft.mid.cloong.host.pm.PMCustomCreateReq;
import com.neusoft.mid.cloong.host.pm.PMInfo;
import com.neusoft.mid.cloong.host.pm.PMStateQuery;
import com.neusoft.mid.cloong.host.pm.PMStateQueryReq;
import com.neusoft.mid.cloong.order.info.OrderProcessor;
import com.neusoft.mid.cloong.order.info.OrderInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMStatus;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 发送创建物理机请求后查询状态、创建IP、创建带宽的请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class CreatePMSenderImpl implements ICreatePMSender {

    /**
     * assemblePMStateReq 创建物理机组装查询request
     * @param pmInfo 查询结果pmInfo
     * @param pmReq 创建物理机req
     */
    public void assemblePMStateReq(String pmId, PMCreateReq pmReq) {
        pmStateReq = new PMStateQueryReq();
        pmStateReq.setPmId(pmId);
        pmStateReq.setPassword(pmReq.getPassword());
        pmStateReq.setResourcePoolId(pmReq.getResourcePoolId());
        pmStateReq.setResourcePoolPartId(pmReq.getResourcePoolPartId());
        pmStateReq.setResourceUrl(pmReq.getResourceUrl());
        pmStateReq.setTimestamp(pmReq.getTimestamp());
        pmStateReq.setTransactionID(pmReq.getTransactionID());
        if (logger.isDebugEnable()) {
            logger.debug(pmStateReq.toString());
        }

    }

    /**
     * assemblePMCustomStateReq 高定制创建物理机组装查询request
     * @param pmId 物理机id
     * @param pmReq 创建物理机req
     */
    public void assemblePMCustomStateReq(String pmId, PMCustomCreateReq pmReq) {
    	pmStateReq = new PMStateQueryReq();
    	pmStateReq.setPmId(pmId);
    	pmStateReq.setPassword(pmReq.getPassword());
    	pmStateReq.setResourcePoolId(pmReq.getResourcePoolId());
    	pmStateReq.setResourcePoolPartId(pmReq.getResourcePoolPartId());
    	pmStateReq.setResourceUrl(pmReq.getResourceUrl());
    	pmStateReq.setTimestamp(pmReq.getTimestamp());
    	pmStateReq.setTransactionID(pmReq.getTransactionID());
        if (logger.isDebugEnable()) {
            logger.debug(pmStateReq.toString());
        }

    }

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(CreatePMSenderImpl.class);

    /**
     * 和资源池交互的物理机状态查询接口
     */
    private PMStateQuery pmStateQuery;

    private IbatisDAO ibatisDAO;

    private PMStateQueryReq pmStateReq;

    private int interval = 5;

    private long timeout = 3600000;

    @Override
    public void send() {
        if (queryPM()) {
            if (createIP()) {
                createNetwork();
                bandIp();
            }
        }

    }

    /**
     * 查询物理机状态
     */
    public boolean queryPM() {
        if (logger.isDebugEnable()) {
            logger.debug(pmStateReq.toString());
        }
        // 循环查询物理机状态次数
        int number = 0;
        long startMs = System.currentTimeMillis();
        RPPPMStatusQueryResp pmResp = pmStateQuery.queryPMState(pmStateReq);
        if (pmResp.getResultCode().equals("00000000")) {
            // 如果物理机不是最终状态，再查
            while (!PMStatus.RUNNING.equals(pmResp.getPmStatus()) && !PMStatus.STOP.equals(pmResp.getPmStatus())
                    && !PMStatus.PAUSE.equals(pmResp.getPmStatus())) {

                if (PMStatus.OPERATE_FAIL.equals(pmResp.getPmStatus())) {
                    // 资源池返回创建操作失败
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + pmStateReq.getPmId() + "的物理机资源池返回创建操作失败！");
                    handleFail(pmStateReq, pmResp);
                    return false;
                }

                // 判断查询物理机状态是否超时，超时时间为1小时
                long endMs = System.currentTimeMillis();
                if ((endMs - startMs) > timeout) {
                    // 超时异常
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + pmStateReq.getPmId()
                            + "的物理机状态更新失败,失败原因：物理机状态查询超时！");
                    handleError(pmStateReq, pmResp);
                    return false;
                }
                // 循环时间间隔为60秒。
                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException e1) {
                    logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e1);
                    handleError(pmStateReq, pmResp);
                    return false;
                } catch (Exception e2) {
                    logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e2);
                    handleError(pmStateReq, pmResp);
                    return false;
                }
                // 查询物理机状态
                pmResp = pmStateQuery.queryPMState(pmStateReq);
                if (!pmResp.getResultCode().equals("00000000")) {
                    logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败");
                    handleError(pmStateReq, pmResp);
                    return false;
                }
            }
            // TODO:创建线程时间先后不能确定，所以会造成问题，暂时sleep5秒来解决，避免演示出问题，以后在创建action先Insert再Update,insert时，状态就插入进去。
            // 时间间隔为5秒。
            try {
                logger.debug("现在时间是：" + new DateTime().toString());
                TimeUnit.SECONDS.sleep(5);
                logger.debug("停5秒后时间是：" + new DateTime().toString());
            } catch (InterruptedException e1) {
                logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e1);
                handleError(pmStateReq, pmResp);
                return false;
            } catch (Exception e2) {
                logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e2);
                handleError(pmStateReq, pmResp);
                return false;
            }
            // 如果成功且为最终状态，更新PM实例状态
            PMInfo tempPMInfo = new PMInfo();
            tempPMInfo.setPmId(pmStateReq.getPmId());
            tempPMInfo.setPmState(pmResp.getPmStatus().getCode());
            // 物理机运行中后，返回私网IP
            tempPMInfo.setOperationIP(pmResp.getOperationIp());
            tempPMInfo.setUserName(pmResp.getUserName());
            tempPMInfo.setPassword(pmResp.getPassWord());

            try {
                ibatisDAO.updateData("updatePMStatus", tempPMInfo);
                if (logger.isDebugEnable()) {
                    logger.debug("更新物理机实例状态成功，pmId为" + pmStateReq.getPmId() + "，状态为" + tempPMInfo.getPmState());
                }
            } catch (SQLException e) {
                logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e);
                handleError(pmStateReq, pmResp);
                return false;
            } catch (Exception e2) {
                logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e2);
                handleError(pmStateReq, pmResp);
                return false;
            }
            // 如果成功且为最终状态，更新订单生效
            try {
                // 查询出对应订单信息
                OrderInfo orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryPMOrderInfo", pmStateReq);

                // 设置订单到期时间
                BatchVO updateOrderVO = OrderProcessor.genOrderToEffectiveVO(pmStateReq.getTimestamp(), orderInfo);
                List<BatchVO> voList = new ArrayList<BatchVO>();
                voList.add(updateOrderVO);
                ibatisDAO.updateBatchData(voList);

                if (logger.isDebugEnable()) {
                    logger.debug("更新订单成功, pmId为" + pmStateReq.getPmId());
                }
            } catch (SQLException e) {
                logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e);
                handleError(pmStateReq, pmResp);
                return false;
            } catch (Exception e2) {
                logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败", e2);
                handleError(pmStateReq, pmResp);
                return false;
            }
        } else {
            logger.error("编号为" + pmStateReq.getPmId() + "的物理机状态更新失败");
            handleError(pmStateReq, pmResp);
            return false;
        }
        return true;
    }

    /**
     * 创建IP
     */
    public boolean createIP() {
        // 判断是否有IP资源，发送创建IP申请， 向资源池系统发送创建IP请求，超时时间为10S。
        // 响应成功后，打印info日志“向资源池申请IP为XXX成功！”。将IP插入数据库。
        return true;
    }

    /**
     * 创建带宽
     */
    public boolean createNetwork() {
        // 判断是否有带宽资源，发送创建带宽申请，向资源池系统发送创建带宽请求，超时时间为10S。
        // 响应成功后，打印info日志“向资源池申请带宽为XXX成功！”。将带宽插入数据库。
        return true;
    }

    /**
     * 绑定IP
     */
    public boolean bandIp() {
        // 发送绑定IP至主机申请，向资源池系统发送绑定IP至主机请求，超时时间为10S。
        // 响应成功后，打印info日志“绑定IPXXX至物理机ID为XXX成功！”。将绑定IP与主机关系插入数据库。
        return true;
    }

    private void handleError(PMStateQueryReq tempPMStateReq, RPPPMStatusQueryResp pmResp) {
        // 更新PM失败状态
        updatePMStatus(assmbleErrorPMStatusInfo(tempPMStateReq));

        // 入失败表
        insertPMQueryErrorInfo(assemblePMCreateFail(tempPMStateReq, pmResp));
    }

    /**
     * handleFail 操作失败入库.
     * @param pmStateQueryReq
     * @param pmResp
     */
    private void handleFail(PMStateQueryReq pmStateQueryReq, RPPPMStatusQueryResp pmResp) {
        // 更新PM 资源池返回操作失败
        updatePMStatus(assmbleFailPMStatusInfo(pmStateQueryReq));

        // 入失败表
        insertPMQueryErrorInfo(assemblePMCreateFail(pmStateQueryReq, pmResp));
    }

    /**
     * assmbleFailPMStatusInfo 构造返回信息
     * @param PMStateQueryReq pmStateQueryReq 物理机状态查询接口请求
     * @return PMInfo pmInfo
     */
    private PMInfo assmbleFailPMStatusInfo(PMStateQueryReq pmStateQueryReq) {
        PMInfo pmInfo = new PMInfo();
        pmInfo.setPmId(pmStateQueryReq.getPmId());
        pmInfo.setPmState(PMStatus.OPERATE_FAIL.getCode());
        return pmInfo;
    }

    /**
     * assemblePMCreateFail 构造创建失败返回信息.
     * @param tempPMStateReq
     * @param pmResp
     * @return PMCreateFail pmCreateFail
     */
    private PMCreateFail assemblePMCreateFail(PMStateQueryReq tempPMStateReq, RPPPMStatusQueryResp pmResp) {
        PMCreateFail pmCreateFail = new PMCreateFail();
        pmCreateFail.setFailCause(pmResp.getResultMessage());
        pmCreateFail.setFailCode(pmResp.getResultCode());
        pmCreateFail.setResPoolId(tempPMStateReq.getResourcePoolId());
        pmCreateFail.setResPoolPartId(tempPMStateReq.getResourcePoolPartId());
        if (tempPMStateReq.getTimestamp().contains("-")) { // 首次创建查询状态时时间带格式
        	pmCreateFail.setCreateTime(DateParse.parseTime(tempPMStateReq.getTimestamp()));
        } else { // 重启时从数据库查，时间不带格式，不转换
        	pmCreateFail.setCreateTime(tempPMStateReq.getTimestamp());
        }
        pmCreateFail.setStandardId("");
        pmCreateFail.setSubNetwork("");
        pmCreateFail.setOsId("");
        pmCreateFail.setNum("1");
        return pmCreateFail;
    }

    @Override
    public void run() {
        synchronized (this) {
            send();
        }
    }

    private PMInfo assmbleErrorPMStatusInfo(PMStateQueryReq req) {
        PMInfo pmInfo = new PMInfo();
        pmInfo.setPmId(req.getPmId());
        pmInfo.setPmState(PMStatus.STATUSERROR.getCode());
        return pmInfo;
    }

    private void updatePMStatus(PMInfo pmInfo) {
        try {
            ibatisDAO.updateData("updatePMStatus", pmInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + pmInfo.getPmId()
                    + "】的物理机状态更新失败，物理机状态为【" + pmInfo.getPmState() + "】", e);
        } catch (Exception e2) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + pmInfo.getPmId()
                    + "】的物理机状态更新失败，物理机状态为【" + pmInfo.getPmState() + "】", e2);
        }
    }

    private void insertPMQueryErrorInfo(PMCreateFail pmCreateFail) {
        try {
            ibatisDAO.insertData("insertPMFail", pmCreateFail);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入的创建物理机错误信息错误，插入信息为：" + pmCreateFail.toString(), e);
        } catch (Exception e2) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入的创建物理机错误信息错误，插入信息为：" + pmCreateFail.toString(), e2);
        }
    }

    
    public PMStateQuery getPmStateQuery() {
		return pmStateQuery;
	}

	public void setPmStateQuery(PMStateQuery pmStateQuery) {
		this.pmStateQuery = pmStateQuery;
	}

	public PMStateQueryReq getPmStateReq() {
		return pmStateReq;
	}

	public void setPmStateReq(PMStateQueryReq pmStateReq) {
		this.pmStateReq = pmStateReq;
	}

	public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
