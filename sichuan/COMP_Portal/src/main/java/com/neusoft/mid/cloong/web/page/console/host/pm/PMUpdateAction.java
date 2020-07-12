/*******************************************************************************
 * @(#)PMUpdateAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.common.util.ip.MaskErrorException;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMModify;
import com.neusoft.mid.cloong.host.pm.core.PMModifyFail;
import com.neusoft.mid.cloong.host.pm.core.PMModifyReq;
import com.neusoft.mid.cloong.host.pm.core.PMModifyResp;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 更改物理机网络，返回JSON对象
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-8 下午01:14:31
 */
public class PMUpdateAction extends BaseAction {

    private static final long serialVersionUID = 5905281979052112854L;

    private static LogService logger = LogService.getLogger(PMUpdateAction.class);

    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 物理机编码
     */
    private String pmId;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     * 新增和修改的网卡列表
     */
    private String netListChanged;

    /**
     * 返回提示消息
     */
    private String mes;

    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";

    /**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 物理机创建接口
     */
    private PMModify pmModify;

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 物理机操作状态判断服务
     */
    private PMStatusService pmStatusService;

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    public String execute() {
        // session中获取用户ID
        String userId = getCurrentUserId();
        PMInstanceInfo pmInstanceInfo = new PMInstanceInfo();
        try {
            pmInstanceInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord("getPmStatus", pmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("pmInstance.query.fail"), e);
            result = ConstantEnum.ERROR.toString();
            mes = getText("pmInstance.query.fail");
            return ConstantEnum.FAILURE.toString();
        }

        // 只有已生效的订单才能进行修改申请
        if (validatePMOrderStatus(pmInstanceInfo.getCaseId())) {
            return ConstantEnum.FAILURE.toString();
        }

        // 如果没修改直接返回，不调用修改接口
        if (("[]").equals(netListChanged)) {
            logger.info(getText("pm.equal.old"));
            result = ConstantEnum.SUCCESS.toString();
            mes = getText("pm.equal.old");
            return ConstantEnum.SUCCESS.toString();
        } else {// 接口可以修改名称和网络信息，目前不支持修改名称
            pmInstanceInfo.setPmId(pmId);
            pmInstanceInfo.setUpdateUser(userId);
            pmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            // 修改网卡
            if (!("[]").equals(netListChanged)) {
                try {
                    setPmNetList(netListChanged, pmInstanceInfo);
                } catch (SQLException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            getText("pmInstance.query.fail"), e);
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("pmInstance.query.fail");
                    return ConstantEnum.FAILURE.toString();
                } catch (MaskErrorException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            getText("pmInstance.query.fail"), e);
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("pmInstance.query.fail");
                    return ConstantEnum.FAILURE.toString();
                }
            }
        }

        PMModifyReq modifyPMReq = new PMModifyReq();
        PMModifyResp resp = new PMModifyResp();
        resp.setResultCode(SUCCEESS_CODE);
        if (AUTO.equals(audit)) {
            // 发送申请订单至接口
            assembleReq(modifyPMReq, pmInstanceInfo);
            resp = pmModify.modifyPM(modifyPMReq);
        }
        boolean doWithRespRes = doWithResp(userId, modifyPMReq, pmInstanceInfo, resp);

        if (!doWithRespRes) {
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }

        if (AUTO.equals(audit)) {
            logger.debug("修改物理机申请发送成功！");
            mes = "修改物理机申请发送成功！";
        } else {
            logger.debug("修改物理机申请提交成功，请等待审核！");
            mes = "修改物理机申请提交成功，请等待审核！";
        }

        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    private void setPmNetList(String netListChanged, PMInstanceInfo pmInstanceInfo)
            throws SQLException, MaskErrorException {
        List<NetInfo> nodes = new ArrayList<NetInfo>();
        JSONArray jsons = JSONArray.fromObject(netListChanged);
        int num = 0;
        for (Object o : jsons) {
            JSONObject jsonNode = JSONObject.fromObject(o);
            NetInfo netInfo = new NetInfo();
            String eth = jsonNode.getString("eth");
            // 如果网卡标识不为空，说明该网卡已修改，否则为新增
            if (eth != null && !("").equals(eth)) {
                netInfo.setEth(eth);
                // 标识新增，用来处理数据库操作， 0：新增，1：修改
                netInfo.setModifyFlag("0");
            } else {
                // 如果是第一个新增的数据，那么从数据库中查询出最后一个网卡标识，再次基础上+1
                String lsatEth = "";
                if (num == 0) {
                    lsatEth = (String) ibatisDAO.getSingleRecord("queryPMLastEth", pmId);
                }

                // 计算新增网卡标识数字部分，
                int subEth;
                if (null != lsatEth && !("").equals(lsatEth)) {
                    subEth = Integer.valueOf(lsatEth.substring(3)) + num + 1;
                } else {
                    subEth = num;
                }
                eth = "eth" + String.valueOf(subEth);
                num++;

                netInfo.setEth(eth);
                // 标识修改，用来处理数据库操作， 0：新增，1：修改
                netInfo.setModifyFlag("1");
            }
            netInfo.setVlanId(jsonNode.getString("vlanId"));
            netInfo.setIpSegmentId(jsonNode.getString("ipSegmentId"));
            netInfo.setIp(jsonNode.getString("ip"));
            // 通过网段获取子网掩码
            String ipSubnet = (String) ibatisDAO.getSingleRecord("querySubnetmaskByIpSubnet",
                    netInfo.getIpSegmentId());
            netInfo.setSubNetMask(IpSegmentUtil.getMask(ipSubnet));

            netInfo.setGateway(jsonNode.getString("gateway"));
            nodes.add(netInfo);
        }
        pmInstanceInfo.setNetList(nodes);
    }

    private boolean doWithResp(String userId, PMModifyReq modifyPMReq,
            PMInstanceInfo pmInstanceInfo, PMModifyResp resp) {
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();

        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，物理机订单和实例入库
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(pmInstanceInfo.getOrderId());
            orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            orderInfo.setUpdateUser(userId);
            pmInstanceInfo.setResPoolId(resPoolId);
            pmInstanceInfo.setResPoolPartId(resPoolPartId);
            String timeStamp = DateParse.generateDateFormatyyyyMMddHHmmss();
            String pmId = pmInstanceInfo.getPmId();
            String caseId = pmInstanceInfo.getCaseId();
            try {
                // 如果物理机网卡信息修改，需要将物理机网卡信息录入到一张临时表
                if (null != pmInstanceInfo.getNetList() && pmInstanceInfo.getNetList().size() > 0) {
                    for (NetInfo netInfo : pmInstanceInfo.getNetList()) {
                        netInfo.setPmId(pmId);
                        netInfo.setTimeStamp(timeStamp);
                        netInfo.setCreateTime(timeStamp);
                        netInfo.setCreateUser(userId);
                        netInfo.setUpdateTime(timeStamp);
                        netInfo.setUpdateUser(userId);

                        // 查询vlan和ip段是否有有效的绑定关系，如果没有需要更新（未绑定的绑定在一起,状态为待生效；无效的变为待生效）。
                        int relCount = ibatisDAO.getCount("countPMVlanIpsegmentRel", netInfo);
                        if (relCount == 0) {
                            batchVOs.add(new BatchVO("ADD", "insertPMVlanIpsegmentRel", netInfo));
                        }

                        batchVOs.add(new BatchVO("ADD", "insertPMModifyNetInfo", netInfo)); // 修改的物理机网卡信息录入到一张临时表
                    }
                }
                if (AUTO.equals(audit)) { // 自动审批
                    // 如果物理机网卡信息修改，需要同时更新物理机网卡表信息
                    if (null != pmInstanceInfo.getNetList()
                            && pmInstanceInfo.getNetList().size() > 0) {
                        for (NetInfo netInfo : pmInstanceInfo.getNetList()) {
                            netInfo.setCreateTime(timeStamp);
                            netInfo.setCreateUser(userId);
                            netInfo.setUpdateTime(timeStamp);
                            netInfo.setUpdateUser(userId);
                            // 如果新增网卡，那么插入新数据；如果修改，那么更新数据
                            if (("0").equals(netInfo.getModifyFlag())) {
                                netInfo.setPmId(pmId);
                                netInfo.setCaseId(caseId);
                                batchVOs.add(new BatchVO("ADD", "insertPMNetInfo", netInfo)); // 直接插入物理机网卡表信息
                            } else {
                                netInfo.setPmId(pmId);
                                batchVOs.add(new BatchVO("MOD", "updatePMNetInfo", netInfo)); // 直接更新物理机网卡表信息
                            }
                        }
                    }
                    batchVOs.add(new BatchVO("MOD", "updateOrderInfo", orderInfo)); // 更新订单修改时间和人
                } else { // 手动审批
                    orderInfo.setStatus("7"); // 更新订单状态为7 修改待审
                    batchVOs.add(new BatchVO("MOD", "updateOrderStatus", orderInfo));
                }
                ibatisDAO.updateBatchData(batchVOs);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        getText("pmInfo.modify.fail"), e);
                result = ConstantEnum.ERROR.toString();
                mes = getText("pmInfo.modify.fail");
                return false;
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "修改物理机发送申请失败！");
            mes = getText("pm.modifyinfo.sendModifyPM.fail");
            // TODO 入失败库，暂不入库
            mes = "修改物理机发送申请失败！";
            return false;
        }
        return true;
    }

    /**
     * assemblePMModifyFail 拼装物理机创建失败信息
     * @param tempPMReq 物理机请求
     * @param PMResp 应答
     * @return 失败消息List
     */
    private List<PMModifyFail> assembleVMModifyFail(PMModifyReq tempVMReq, PMModifyResp vmResp) {
        List<PMModifyFail> vmModifyFails = new ArrayList<PMModifyFail>();
        PMModifyFail vmModifyFail = new PMModifyFail();
        vmModifyFail.setFailCause(vmResp.getResultMessage());
        vmModifyFail.setFailCode(vmResp.getResultCode());
        vmModifyFail.setResPoolId(tempVMReq.getResourcePoolId());
        vmModifyFail.setResPoolPartId(tempVMReq.getResourcePoolPartId());
        vmModifyFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        vmModifyFail.setPmId(tempVMReq.getPmId());
        vmModifyFails.add(vmModifyFail);
        return vmModifyFails;
    }

    /**
     * assembleReq 生成请求
     * @param modifyPMReq 修改物理机请求
     */
    private void assembleReq(PMModifyReq modifyPMReq, PMInstanceInfo pmInstanceInfo) {
        modifyPMReq.setPmId(pmInstanceInfo.getPmId());
        modifyPMReq.setNetList(pmInstanceInfo.getNetList());
        modifyPMReq.setResourcePoolId(resPoolId);
        modifyPMReq.setResourcePoolPartId(resPoolPartId);
        modifyPMReq.setSerialNum(gen.generatorPMSerialNum("PHMod"));

    }

    private boolean validatePMOrderStatus(String caseId) {
        OrderInfo orderInfo = new OrderInfo();
        try {
            orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("getOrderStatus", caseId); // 查询当前订单状态
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("pmInfo.modify.fail"), e);
            result = ConstantEnum.ERROR.toString();
            mes = getText("pmInfo.modify.fail");
            return true;
        }
        if (!"3".equals(orderInfo.getStatus())) { // 只有已生效的物理机订单才能提供修改
            logger.info(getText("pmOrderStatus.query.fail"));
            result = ConstantEnum.ERROR.toString();
            mes = getText("pmOrderStatus.query.fail");
            return true;
        }
        return false;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public SequenceGenerator getGen() {
        return gen;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

    public String getNetListChanged() {
        return netListChanged;
    }

    public void setNetListChanged(String netListChanged) {
        this.netListChanged = netListChanged;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public PMModify getPmModify() {
        return pmModify;
    }

    public void setPmModify(PMModify pmModify) {
        this.pmModify = pmModify;
    }

    public PMStatusService getPmStatusService() {
        return pmStatusService;
    }

    public void setPmStatusService(PMStatusService pmStatusService) {
        this.pmStatusService = pmStatusService;
    }

}
