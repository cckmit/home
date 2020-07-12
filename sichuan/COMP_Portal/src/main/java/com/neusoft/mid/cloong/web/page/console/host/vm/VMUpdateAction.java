/*******************************************************************************
 * @(#)VMNameUpdateAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

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
import com.neusoft.mid.cloong.host.vm.core.VMModify;
import com.neusoft.mid.cloong.host.vm.core.VMModifyFail;
import com.neusoft.mid.cloong.host.vm.core.VMModifyReq;
import com.neusoft.mid.cloong.host.vm.core.VMModifyResp;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 更改虚拟机名称，返回JSON对象
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-8 下午01:14:31
 */
public class VMUpdateAction extends BaseAction {

    private static final long serialVersionUID = 5905281979052112854L;

    private static LogService logger = LogService.getLogger(VMUpdateAction.class);

    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 虚拟机编码
     */
    private String vmId;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     * 原有的虚拟机名称
     */
    private String vmName;

    /**
     * 原有的虚拟机cpu数
     */
    private String cpuNum;

    /**
     * 原有的虚拟机内存
     */
    private String ramSize;

    /**
     * 原有的虚拟机磁盘大小
     */
    private String discSize;

    /**
     * 新的虚拟机名称
     */
    private String vmNameNew;

    /**
     * 新的虚拟机CPU数
     */
    private String cpuNumNew;

    /**
     * 新的虚拟机内存
     */
    private String ramSizeNew;

    /**
     * 新的虚拟机磁盘大小
     */
    private String discSizeNew;

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
     * 虚拟机创建接口
     */
    private VMModify vmModify;

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 虚拟机操作状态判断服务
     */
    private VMStatusService vmStatusService;
    
    //虚拟机批量修改vmList
    private String vmIdList;
    
    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    public String execute() {
        // session中获取用户ID
        String userId = getCurrentUserId();
        VMInstanceInfo vmInstanceInfo = new VMInstanceInfo();
        try {
            vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmStatus", vmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("vmInstance.query.fail"), e);
            result = ConstantEnum.ERROR.toString();
            mes = getText("vmInstance.query.fail");
            return ConstantEnum.FAILURE.toString();
        }

        /*
         * 只有停止的虚拟机才能进行修改 调整到前台页面校验
         * if (!(VMStatus.STOP.equals(vmStatusService.getVMStatus(vmId)))) {
         * logger.info(getText("vmInstance.query.fail.notStop")); result =
         * ConstantEnum.ERROR.toString(); mes = getText("vmInstance.query.fail.notStop"); return
         * ConstantEnum.FAILURE.toString(); }
         */

        // 只有已生效的订单才能进行修改申请
        if (validateVMOrderStatus(vmInstanceInfo.getCaseId())) {
            return ConstantEnum.FAILURE.toString();
        }

        // 如果没修改直接返回，不调用修改接口
        if (cpuNumNew.equals(cpuNum) && ramSizeNew.equals(ramSize) && discSizeNew.equals(discSize)
                && ("[]").equals(netListChanged)) {
            logger.info(getText("vm.equal.old"));
            result = ConstantEnum.SUCCESS.toString();
            mes = getText("vm.equal.old");
            return ConstantEnum.SUCCESS.toString();
        } else {
            vmInstanceInfo.setVmId(vmId);
            vmInstanceInfo.setUpdateUser(userId);
            vmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            /*
             * if (!vmNameNew.equals(vmName)) { vmInstanceInfo.setVmName(vmNameNew); }
             */

            // 获取资源池分区磁盘、内存和CPU总数
            VMInstanceInfo resPoolPartTotal = new VMInstanceInfo();
            try {
                resPoolPartTotal = (VMInstanceInfo) ibatisDAO.getSingleRecord(
                        "queryTotalConfigByRespool", vmInstanceInfo.getResPoolId());
            } catch (SQLException e1) {
                logger.info(getText("vm.queryRespool.fail"));
                result = ConstantEnum.ERROR.toString();
                mes = getText("vm.queryRespool.fail");
                return ConstantEnum.FAILURE.toString();
            }
            // 分别判断磁盘、内存和CPU核数与资源池分区总数，不能超过总数
            if (!cpuNumNew.equals(cpuNum)) {
                if (Integer.valueOf(cpuNumNew) <= Integer
                        .valueOf(resPoolPartTotal.getCpuNumTotal())) {
                    vmInstanceInfo.setCpuNum(cpuNumNew);
                } else {
                    logger.info(getText("vm.cpuNum.toLarge"));
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("vm.cpuNum.toLarge");
                    return ConstantEnum.FAILURE.toString();
                }
            }
            if (!ramSizeNew.equals(ramSize)) {
                if (Integer.valueOf(ramSizeNew) <= Integer.valueOf(resPoolPartTotal
                        .getRamSizeTotal())) {
                    vmInstanceInfo.setRamSize(ramSizeNew);
                } else {
                    logger.info(getText("vm.ramSize.toLarge"));
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("vm.ramSize.toLarge");
                    return ConstantEnum.FAILURE.toString();
                }
            }
            if (!discSizeNew.equals(discSize)) {
                // 要修改的磁盘大小不能小于原有大小
                if (Float.valueOf(discSizeNew) < Float.valueOf(discSize)) { // 修改后比修改小，提示错误
                    logger.info(getText("vm.discSize.small"));
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("vm.discSize.small");
                    return ConstantEnum.FAILURE.toString();
                } else if (Float.valueOf(discSizeNew) <= Float.valueOf(resPoolPartTotal.getDiscSizeTotal())) {
                    vmInstanceInfo.setDiscSize(discSizeNew);
                } else {
                    logger.info(getText("vm.discSize.toLarge"));
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("vm.discSize.toLarge");
                    return ConstantEnum.FAILURE.toString();
                }
            }

            if (!("[]").equals(netListChanged)) {
                try {
                    setVmNetList(netListChanged, vmInstanceInfo);
                } catch (SQLException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            getText("vmInstance.query.fail"), e);
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("vmInstance.query.fail");
                    return ConstantEnum.FAILURE.toString();
                } catch (MaskErrorException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            getText("vmInstance.query.fail"), e);
                    result = ConstantEnum.ERROR.toString();
                    mes = getText("vmInstance.query.fail");
                    return ConstantEnum.FAILURE.toString();
                }
            }
        }

        VMModifyReq modifyVMReq = new VMModifyReq();
        VMModifyResp resp = new VMModifyResp();
        resp.setResultCode(SUCCEESS_CODE);
        if (AUTO.equals(audit)) {
            // 发送申请订单至接口
            assembleReq(modifyVMReq, vmInstanceInfo);
            resp = vmModify.modifyVM(modifyVMReq);
        }
        boolean doWithRespRes = doWithResp(userId, modifyVMReq, vmInstanceInfo, resp);

        if (!doWithRespRes) {
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }

        if (AUTO.equals(audit)) {
            logger.debug("修改虚拟机申请发送成功！");
            mes = "修改虚拟机申请发送成功！";
        } else {
            logger.debug("修改虚拟机申请提交成功，请等待审核！");
            mes = "修改虚拟机申请提交成功，请等待审核！";
        }

        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    //批量修改虚拟机
    public String vmBatchUpdate(){
    	result=ConstantEnum.SUCCESS.toString();
    	// session中获取用户ID
        String userId = getCurrentUserId();
        String[] vmIdInList =vmIdList.split(",");
        // 同一个订单父ID
        String vmModifyBatchId = gen.generatorOrderId(ResourceTypeEnum.VMB
                .getParentCode());
        for (int i = 0; i < vmIdInList.length-1; i++) {
        	if(null!=vmIdInList[i]&&!"".equals(vmIdInList[i])){
        		
        		VMInstanceInfo vmInstanceInfo = new VMInstanceInfo();
        		try {
                    vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmStatus", vmIdInList[i]);
                } catch (Exception e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            getText("vmInstance.query.fail"), e);
                    mes="查询虚拟机信息失败！";
                    result = ConstantEnum.FAILURE.toString();
                    return result;
                }
        		 // 只有已生效的订单才能进行修改申请
                if (validateVMOrderStatus(vmInstanceInfo.getCaseId())) {
                	mes="虚拟机'"+vmInstanceInfo.getVmName()+"'存在未生效的订单！";
                    result = ConstantEnum.FAILURE.toString();
                    return result;
                }
        		List<BatchVO> batchVOs = new ArrayList<BatchVO>();
                    // 返回成功，虚拟机订单和实例入库
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrderId(vmInstanceInfo.getOrderId());
                    orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                    orderInfo.setUpdateUser(userId);
                    orderInfo.setVmModifyBatchId(vmModifyBatchId);
                    vmInstanceInfo.setResPoolId(vmInstanceInfo.getResPoolId());
                    vmInstanceInfo.setResPoolPartId(vmInstanceInfo.getResPoolPartId());
                    if(!"".equals(cpuNumNew)&&null!=cpuNumNew){
                    	vmInstanceInfo.setCpuNum(cpuNumNew);
                    }
                    if(!"".equals(ramSizeNew)&&null!=ramSizeNew){
                    	//页面传过来的内存为G，存到数据库中单位为M
                    	vmInstanceInfo.setRamSize(String.valueOf(Integer.parseInt(ramSizeNew)*1024));
                    }
                    String timeStamp = DateParse.generateDateFormatyyyyMMddHHmmss();
                    vmInstanceInfo.setTimeStamp(timeStamp);
                    try {
                            batchVOs.add(new BatchVO("ADD", "insertVMModifyInfo", vmInstanceInfo));// 修改的虚拟机信息录入到一张临时表
//                          batchVOs.add(new BatchVO("MOD", "updateOrderInfo", orderInfo)); 
                            orderInfo.setStatus("7"); // 更新订单状态为7 修改待审
                            batchVOs.add(new BatchVO("MOD", "updateOrderStatus", orderInfo));// 更新订单修改时间和人
                        ibatisDAO.updateBatchData(batchVOs);
                    } catch (SQLException e) {
                        logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                                getText("vmInfo.modify.fail"), e);
                        mes="修改订单状态失败！";
                        result = ConstantEnum.FAILURE.toString();
                        return result;
                    }
        	}
        }
		return result;
    }
    
    private void setVmNetList(String netListChanged, VMInstanceInfo vmInstanceInfo)
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
                // 标识新增或修改，用来处理数据库操作
                netInfo.setModifyFlag("0");
            } else {
                // 如果是第一个新增的数据，那么从数据库中查询出最后一个网卡标识，再次基础上+1
                String lsatEth = "";
                if (num == 0) {
                    lsatEth = (String) ibatisDAO.getSingleRecord("queryLastEth", vmId);
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
                // 标识新增或修改，用来处理数据库操作
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
        vmInstanceInfo.setNetList(nodes);
    }

    private boolean doWithResp(String userId, VMModifyReq modifyVMReq,
            VMInstanceInfo vmInstanceInfo, VMModifyResp resp) {
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();

        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，虚拟机订单和实例入库
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(vmInstanceInfo.getOrderId());
            orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            orderInfo.setUpdateUser(userId);
            vmInstanceInfo.setResPoolId(resPoolId);
            vmInstanceInfo.setResPoolPartId(resPoolPartId);
            String timeStamp = DateParse.generateDateFormatyyyyMMddHHmmss();
            vmInstanceInfo.setTimeStamp(timeStamp);
            String vmId = vmInstanceInfo.getVmId();
            String caseId = vmInstanceInfo.getCaseId();
            try {
                if (!cpuNumNew.equals(cpuNum) || !ramSizeNew.equals(ramSize)
                        || !discSizeNew.equals(discSize)) {
                    batchVOs.add(new BatchVO("ADD", "insertVMModifyInfo", vmInstanceInfo));// 修改的虚拟机信息录入到一张临时表
                }
                // 如果虚拟机网卡信息修改，需要同时将虚拟机网卡信息录入到一张临时表
                if (null != vmInstanceInfo.getNetList() && vmInstanceInfo.getNetList().size() > 0) {
                    for (NetInfo netInfo : vmInstanceInfo.getNetList()) {
                        netInfo.setVmId(vmId);
                        netInfo.setTimeStamp(timeStamp);
                        netInfo.setCreateTime(timeStamp);
                        netInfo.setCreateUser(userId);
                        netInfo.setUpdateTime(timeStamp);
                        netInfo.setUpdateUser(userId);

                        // 查询vlan和ip段是否有有效的绑定关系，如果没有需要更新（未绑定的绑定在一起,状态为待生效；无效的变为待生效）。
                        int relCount = ibatisDAO.getCount("countVlanIpsegmentRel", netInfo);
                        if (relCount == 0) {
                            batchVOs.add(new BatchVO("ADD", "insertVlanIpsegmentRel", netInfo));
                        }

                        batchVOs.add(new BatchVO("ADD", "insertVMModifyNetInfo", netInfo)); // 修改的虚拟机网卡信息录入到一张临时表
                    }
                }
                if (AUTO.equals(audit)) { // 自动审批
                    batchVOs.add(new BatchVO("MOD", "updateVMInfo", vmInstanceInfo)); // 直接更新虚拟机表信息
                    // 如果虚拟机网卡信息修改，需要同时更新虚拟机网卡表信息
                    if (null != vmInstanceInfo.getNetList()
                            && vmInstanceInfo.getNetList().size() > 0) {
                        for (NetInfo netInfo : vmInstanceInfo.getNetList()) {
                            netInfo.setCreateTime(timeStamp);
                            netInfo.setCreateUser(userId);
                            netInfo.setUpdateTime(timeStamp);
                            netInfo.setUpdateUser(userId);
                            // 如果新增网卡，那么插入新数据；如果修改，那么更新数据
                            if (("0").equals(netInfo.getModifyFlag())) {
                                netInfo.setVmId(vmId);
                                netInfo.setCaseId(caseId);
                                batchVOs.add(new BatchVO("ADD", "insertVMNetInfo", netInfo)); // 直接插入虚拟机网卡表信息
                            } else {
                                netInfo.setVmId(vmId);
                                batchVOs.add(new BatchVO("MOD", "updateVMNetInfo", netInfo)); // 直接更新虚拟机网卡表信息
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
                        getText("vmInfo.modify.fail"), e);
                result = ConstantEnum.ERROR.toString();
                mes = getText("vmInfo.modify.fail");
                return false;
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "修改虚拟机发送申请失败！");
            mes = getText("vm.modifyinfo.sendModifyVM.fail");
            // 入失败库，暂不入库
            /*
             * List<VMModifyFail> vmModifyFails = assembleVMModifyFail( modifyVMReq, resp);
             * List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>(); for (VMModifyFail
             * vmModifyFail : vmModifyFails) { insertFailBatchVO.add(new BatchVO("MOD",
             * "insertVMModifyFail", vmModifyFail)); } try {
             * ibatisDAO.updateBatchData(insertFailBatchVO); } catch (SQLException e) {
             * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟机失败后入失败库发生数据库异常",
             * e); } catch (Exception e2) {
             * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟机失败后入失败库发生数据库异常",
             * e2); }
             */
            mes = "修改虚拟机发送申请失败！";
            return false;
        }
        return true;
    }

    /**
     * assembleVMModifyFail 拼装虚拟机创建失败信息
     * @param tempVMReq 虚拟机请求
     * @param vmResp 应答
     * @return 失败消息List
     */
    private List<VMModifyFail> assembleVMModifyFail(VMModifyReq tempVMReq, VMModifyResp vmResp) {
        List<VMModifyFail> vmModifyFails = new ArrayList<VMModifyFail>();
        VMModifyFail vmModifyFail = new VMModifyFail();
        vmModifyFail.setFailCause(vmResp.getResultMessage());
        vmModifyFail.setFailCode(vmResp.getResultCode());
        vmModifyFail.setResPoolId(tempVMReq.getResourcePoolId());
        vmModifyFail.setResPoolPartId(tempVMReq.getResourcePoolPartId());
        vmModifyFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        vmModifyFail.setVmId(tempVMReq.getVmId());
        vmModifyFail.setCpuNum(tempVMReq.getCpuNum());
        vmModifyFail.setRamSize(tempVMReq.getRamSize());
        vmModifyFail.setDiscSize(tempVMReq.getDiscSize());
        vmModifyFails.add(vmModifyFail);
        return vmModifyFails;
    }

    /**
     * assembleReq 生成请求
     * @param modifyVMReq 修改虚拟机请求
     */
    private void assembleReq(VMModifyReq modifyVMReq, VMInstanceInfo vmInstanceInfo) {
        modifyVMReq.setVmId(vmInstanceInfo.getVmId());
        modifyVMReq.setVmName(vmInstanceInfo.getVmName());
        modifyVMReq.setCpuNum(vmInstanceInfo.getCpuNum());
        modifyVMReq.setRamSize(vmInstanceInfo.getRamSize());
        modifyVMReq.setDiscSize(vmInstanceInfo.getDiscSize());
        modifyVMReq.setNetList(vmInstanceInfo.getNetList());
        modifyVMReq.setResourcePoolId(resPoolId);
        modifyVMReq.setResourcePoolPartId(resPoolPartId);
        modifyVMReq.setSerialNum(gen.generatorVMSerialNum("VMMod"));

    }

    private boolean validateVMOrderStatus(String caseId) {
        OrderInfo orderInfo = new OrderInfo();
        try {
            orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("getOrderStatus", caseId); // 查询当前订单状态
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("vmInfo.modify.fail"), e);
            result = ConstantEnum.ERROR.toString();
            mes = getText("vmInfo.modify.fail");
            return true;
        }
        if (!"3".equals(orderInfo.getStatus())) { // 只有已生效的虚拟机订单才能提供修改
            logger.info(getText("vmOrderStatus.query.fail"));
            result = ConstantEnum.ERROR.toString();
            mes = getText("vmOrderStatus.query.fail");
            return true;
        }
        return false;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
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

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public String getDiscSize() {
        return discSize;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
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

    public VMModify getVmModify() {
        return vmModify;
    }

    public void setVmModify(VMModify vmModify) {
        this.vmModify = vmModify;
    }

    public SequenceGenerator getGen() {
        return gen;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

    public String getVmNameNew() {
        return vmNameNew;
    }

    public void setVmNameNew(String vmNameNew) {
        this.vmNameNew = vmNameNew;
    }

    public String getCpuNumNew() {
        return cpuNumNew;
    }

    public void setCpuNumNew(String cpuNumNew) {
        this.cpuNumNew = cpuNumNew;
    }

    public String getRamSizeNew() {
        return ramSizeNew;
    }

    public void setRamSizeNew(String ramSizeNew) {
        this.ramSizeNew = ramSizeNew;
    }

    public String getDiscSizeNew() {
        return discSizeNew;
    }

    public void setDiscSizeNew(String discSizeNew) {
        this.discSizeNew = discSizeNew;
    }

    public String getNetListChanged() {
        return netListChanged;
    }

    public void setNetListChanged(String netListChanged) {
        this.netListChanged = netListChanged;
    }

    public VMStatusService getVmStatusService() {
        return vmStatusService;
    }

    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

	public String getVmIdList() {
		return vmIdList;
	}

	public void setVmIdList(String vmIdList) {
		this.vmIdList = vmIdList;
	}

}
