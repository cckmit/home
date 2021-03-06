/*******************************************************************************
 * @(#)PMApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.host.pm.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMCreate;
import com.neusoft.mid.cloong.host.pm.core.PMCreateFail;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp.PMInfo;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VlanIPBind;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * ???????????????????????????
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 ??????04:18:41
 */
public class PMApplyInfoAction extends ResourceManagementBaseAction {

    /**
     * ??????????????????
     */
    private static final long serialVersionUID = -3246558237673423698L;

    /**
     * ???????????????
     */
    private static LogService logger = LogService.getLogger(PMApplyInfoAction.class);

    /**
     * ???????????? 0??????????????? 1????????????
     */
    private String configuration = "1";

    /**
     * ??????ID
     */
    private String itemId;

    /**
     * ??????ID
     */
    private String standardId;

    /**
     * OSID
     */
    private String osId;

    /**
     * ???????????????
     */
    private String pmName;

    /**
     * ??????
     */
    private String num;

    /**
     * ??????
     */
    private String lengthTime;

    /**
     * pmRemark
     */
    private String pmRemark;

    /**
     * ?????????ID
     */
    private String respoolId;

    /**
     * ???????????????ID
     */
    private String respoolPartId;

    /**
     * ???????????????
     */
    private String respoolName;

    /**
     * ?????????????????????
     */
    private String respoolPartName;

    /**
     * CPU??????
     */
    private String cpuType;

    /**
     * ????????????
     */
    private String ramSize;

    /**
     * ????????????
     */
    private String discSize;

    /**
     * ???????????????
     */
    private String serverType;

    /**
     * ?????????????????????????????????ID???
     */
    private String queryBusinessId;

    /**
     * ???1~5???vlan
     */
    private String[] ethList1vlanId;

    private String[] ethList2vlanId;

    private String[] ethList3vlanId;

    private String[] ethList4vlanId;

    private String[] ethList5vlanId;

    private String[] ethList1ipsegmentId;

    private String[] ethList2ipsegmentId;

    private String[] ethList3ipsegmentId;

    private String[] ethList4ipsegmentId;

    private String[] ethList5ipsegmentId;

    private String[] ethList1ip;

    private String[] ethList2ip;

    private String[] ethList3ip;

    private String[] ethList4ip;

    private String[] ethList5ip;

    private String[] ethList1gateway;

    private String[] ethList2gateway;

    private String[] ethList3gateway;

    private String[] ethList4gateway;

    private String[] ethList5gateway;

    /**
     * ????????????????????????
     */
    private PMStatusService pmStatusService;

    /**
     * ??????????????????
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * ?????????????????????
     */
    private PMCreate pmCreate;

    /**
     * ????????????????????????
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * ????????? yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * ???????????????????????? 0????????? 1?????????
     */
    private String audit;

    /**
     * ??????????????????
     */
    private static final String AUTO = "0";

    /**
     * tip
     */
    private String addTip = "ADD";

    private String caseId1 = "";

    private String caseId2 = "";

    private String caseId3 = "";

    private String caseId4 = "";

    private String caseId5 = "";

    /**
     * pm??????????????????
     */
    private static final String QUERYCODE = "0";

    /**
     * ?????????????????? ???0:pm???????????????????????? 0???pm????????????????????????.spring??????
     */
    private String pmStatusQuery = "0";

    /**
     * ?????? ??????
     */
    private String type = "1";

	
	/**
     * ?????????????????????????????????????????????action
     */
    private String fromPmApplyAction;
    
    /**
     * execute Action????????????
     * @return ?????????
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        try {
            logger.debug("?????????????????????");

            printPmInformation();

			//??????????????????????????????????????????????????????PMQueryListAction????????????
            this.setFromPmApplyAction("1");
			
            caseId1 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.PH.toString());
            caseId2 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.PH.toString());
            caseId3 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.PH.toString());
            caseId4 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.PH.toString());
            caseId5 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.PH.toString());

            String res = ConstantEnum.SUCCESS.toString();

            // session???????????????ID
            String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                    .getAttribute(SessionKeys.userInfo.toString())).getUserId();

            RPPPMCreateReq pmReq = new RPPPMCreateReq();

            // ?????????
            pmReq.setSerialNum(sequenceGenerator.generatorPMSerialNum("PHAdd"));

            if (configuration.equals("0")) {
                pmReq.setStandardId(standardId);
                pmReq.setCreateModel(PMCreateModel.TemplateModel);
            } else {
                standardId = "";
                itemId = "";
                pmReq.setCreateModel(PMCreateModel.CustomModel);
            }

            // ??????????????????
            List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
            List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
            // ???????????????????????????
            int intNum = Integer.parseInt(num);

            pmReq.setAppId(appId);

            BusinessInfo app = (BusinessInfo) ibatisDAO.getSingleRecord("queryBusinessByAppId",
                    appId);
            pmReq.setAppName(app.getName());

            pmReq.setCount(intNum);
            pmReq.setSecurity("1");
            pmReq.setOsId(osId);
            pmReq.setResourcePoolId(respoolId);
            pmReq.setResourcePoolPartId(respoolPartId);
            /*
             * ????????????????????????????????????????????????????????????????????????????????? pmReq.setPmName(pmName);
             */

            // ????????????
            List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();
            // ??????????????????net List
            List<NetInfo> pmNetList = new ArrayList<NetInfo>();
            pmReq.setEthList(ethList);

            if (ethList1vlanId != null && ethList1vlanId.length > 0) {
                convertPmNet(ethList1vlanId, ethList1ipsegmentId, ethList1ip, ethList1gateway,
                        ethList, pmNetList, userId, caseId1, updateBatchVO);
            }

            // ??????????????????
            if (pmNetList != null && pmNetList.size() > 0) {
                for (NetInfo netInfo : pmNetList) {
                    BatchVO vo = new BatchVO("ADD", "insertPmNet", netInfo);
                    updateBatchVO.add(vo);
                }
            }

            // ??????????????????ID
            String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.PH
                    .getParentCode());
            for (int i = 1; i <= intNum; i++) {
                OrderInfo orderInfo = new OrderInfo();
                String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.PH.toString());
                // String caseId =
                // sequenceGenerator.generatorCaseId(ResourceTypeEnum.PH.toString());
                String caseId = "";
                if (i == 1) {
                    caseId = caseId1;
                } else if (i == 2) {
                    caseId = caseId2;
                } else if (i == 3) {
                    caseId = caseId3;
                } else if (i == 4) {
                    caseId = caseId4;
                } else if (i == 5) {
                    caseId = caseId5;
                }
                // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
                assembleOrder(userId, orderInfo, orderId, caseId, respoolId, appId);
                orderInfo.setParentId(parentId);// ??????????????????ID
                orderInfos.add(orderInfo);
                if (logger.isDebugEnable()) {
                    logger.debug("orderId = " + orderId);
                    logger.debug("caseId = " + caseId);
                    logger.debug("parentId = " + parentId);
                }
            }

            RPPPMCreateResp resp = new RPPPMCreateResp();
            resp.setResultCode(SUCCEESS_CODE);
            if (AUTO.equals(audit)) {
                // ???????????????????????????
                resp = pmCreate.createCustomPM(pmReq);
            }

            List<PMInstanceInfo> pmInstances = new ArrayList<PMInstanceInfo>();
            boolean doWithRespRes = doWithResp(userId, pmReq, updateBatchVO, orderInfos, intNum,
                    resp, pmInstances, appId, pmNetList);

            if (!doWithRespRes) {
                return ConstantEnum.FAILURE.toString();
            }

            if (AUTO.equals(audit)) {
                // ???????????????????????????????????????????????????
                for (PMInstanceInfo pmInfo : pmInstances) {
                    pmStatusService.addPMStatus(pmInfo.getPmId(), pmInfo.getStatus());
                }
            }

            if (AUTO.equals(audit)) {
                logger.debug("????????????????????????????????????");
                msg = "????????????????????????????????????";
            } else {
                logger.debug("??????????????????????????????????????????????????????");
                msg = "??????????????????????????????????????????????????????";
            }

            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            if (AUTO.equals(audit)) {
                errMsg = "????????????????????????????????????";
                logger.error("?????????????????????????????????", e);
            } else {
                errMsg = "????????????????????????????????????";
                logger.error("?????????????????????????????????", e);
            }

            return ConstantEnum.FAILURE.toString();
        }
    }

    private void printPmInformation() {
        if (logger.isDebugEnable()) {
            logger.debug("?????????????????? 0????????? 1?????????" + audit);
            logger.debug("???????????? 0??????????????? 1????????????:" + configuration);
            if (configuration.equals("0")) {
                logger.debug("itemId = " + itemId);
                logger.debug("standardId = " + standardId);
            }
            logger.debug("osId = " + osId);
            logger.debug("num = " + num);
            logger.debug("itemId = " + itemId);
            logger.debug("standardId = " + standardId);
            logger.debug("lengthTime = " + lengthTime);
            logger.debug("pmRemark = " + pmRemark);
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
            logger.debug("respoolName = " + respoolName);
            logger.debug("respoolPartName = " + respoolPartName);
            logger.debug("appId = " + appId);
            logger.debug("pmName = " + pmName);
            if (ethList1vlanId != null) {
                logger.debug("ethList1vlanId = " + Arrays.asList(ethList1vlanId));
            }
            if (ethList1ipsegmentId != null) {
                logger.debug("ethList1ipsegmentId = " + Arrays.asList(ethList1ipsegmentId));
            }
            if (ethList1ip != null) {
                logger.debug("ethList1ip = " + Arrays.asList(ethList1ip));
            }
            if (ethList1gateway != null) {
                logger.debug("ethList1gateway = " + Arrays.asList(ethList1gateway));
            }
        }
    }

    /**
     * convertPmNet ??????????????????
     * @param ethListvlanId
     * @param ethListipsegmentId
     * @param ethListip
     * @param ethListgateway
     * @param ethList
     * @param pmNetList
     * @param userId
     * @param caseId
     * @throws Exception
     */
    private void convertPmNet(String[] ethListvlanId, String[] ethListipsegmentId,
            String[] ethListip, String[] ethListgateway, List<List<EthInfo>> ethList,
            List<NetInfo> pmNetList, String userId, String caseId, List<BatchVO> updateBatchVO)
            throws Exception {

        if (ethListvlanId != null && ethListvlanId.length > 0) {
            List<EthInfo> ethInfos = new ArrayList<EthInfo>();
            for (String vlanId : ethListvlanId) {
                EthInfo ethInfo = new EthInfo();
                ethInfo.setVlanId(vlanId);
                ethInfos.add(ethInfo);
            }
            for (int i = 0; i < ethListipsegmentId.length; i++) {
                IpSegmentInfo ipSegmentInfo = (IpSegmentInfo) ibatisDAO.getSingleRecord(
                        "getIPsegmentByIpSegmentId", ethListipsegmentId[i]);
                String mask = IpSegmentUtil.getMask(ipSegmentInfo.getIpSubnet());
                ethInfos.get(i).setSubNetMask(mask);

                VlanIPBind vlanIPBind = new VlanIPBind();
                vlanIPBind.setVlanId(ethInfos.get(i).getVlanId());
                vlanIPBind.setIpSegment(ethListipsegmentId[i]);
                vlanIPBind.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                vlanIPBind.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                vlanIPBind.setCreateUser(userId);
                vlanIPBind.setUpdateUser(userId);
                vlanIPBind.setStatus("0");
                VlanIPBind vlanIPBindTemp = (VlanIPBind) ibatisDAO.getSingleRecord("getVlanIPBind",
                        vlanIPBind);
                if (vlanIPBindTemp == null) {
                    vlanIPBind.setStatus("2");
                    updateBatchVO.add(new BatchVO("ADD", "insertVlanIPBind", vlanIPBind));
                }
            }

            for (int i = 0; i < ethListip.length; i++) {
                ethInfos.get(i).setIp(ethListip[i]);
            }
            for (int i = 0; i < ethListgateway.length; i++) {
                ethInfos.get(i).setDefaultGateWay(ethListgateway[i]);
                ethInfos.get(i).setName("eth" + i);
                ethInfos.get(i).setPurpose(PMEthPurpose.Business);
            }
            ethList.add(ethInfos);

            for (EthInfo ethInfo : ethInfos) {
                NetInfo netInfo = new NetInfo();
                netInfo.setCaseId(caseId);
                netInfo.setEth(ethInfo.getName());
                netInfo.setGateway(ethInfo.getDefaultGateWay());
                netInfo.setIp(ethInfo.getIp());
                netInfo.setPurPose("2");
                netInfo.setSubnetmask(ethInfo.getSubNetMask());
                netInfo.setVlanId(ethInfo.getVlanId());
                pmNetList.add(netInfo);
            }
        }
    }

    /**
     * doWithResp ????????????
     * @param userId ??????ID
     * @param pmReq ??????
     * @param updateBatchVO VO
     * @param orderInfos ????????????
     * @param intNum ??????
     * @param resp ??????
     * @param pmInstances ??????
     * @return ???????????? ??????true ?????? false
     */
    private boolean doWithResp(String userId, RPPPMCreateReq pmReq, List<BatchVO> updateBatchVO,
            List<OrderInfo> orderInfos, int intNum, RPPPMCreateResp resp,
            List<PMInstanceInfo> pmInstances, String appId, List<NetInfo> pmNetList) {
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // ?????????????????????????????????????????????

            // ??????????????????
            boolean funRes = doWithSuccess(userId, updateBatchVO, orderInfos, intNum, resp,
                    pmInstances, appId, pmNetList);
            if (!funRes) {
                return false;
            }
        } else {
            // ???????????????????????????
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "????????????????????????????????????");
            this.addActionError(getText("pm.applyinfo.sendcreatePM.fail"));
            errMsg = getText("pm.applyinfo.sendcreatePM.fail");
            // ????????????
            List<PMCreateFail> pmCreateFails = assemblePMCreateFail(pmReq, resp);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (PMCreateFail pmCreateFail : pmCreateFails) {
                insertFailBatchVO.add(new BatchVO(addTip, "insertPMFail", pmCreateFail));
            }
            try {
                ibatisDAO.updateBatchData(insertFailBatchVO);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "?????????????????????????????????????????????????????????",
                        e);
            } catch (Exception e2) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "?????????????????????????????????????????????????????????",
                        e2);
            }
            return false;
        }
        return true;
    }

    /**
     * doWithSuccess ??????????????????
     * @param userId ??????ID
     * @param updateBatchVO batchVO
     * @param orderInfos ????????????
     * @param intNum ??????
     * @param resp ??????
     * @param pmInstances ???????????????
     * @return ????????????
     */
    private boolean doWithSuccess(String userId, List<BatchVO> updateBatchVO,
            List<OrderInfo> orderInfos, int intNum, RPPPMCreateResp resp,
            List<PMInstanceInfo> pmInstances, String appId, List<NetInfo> pmNetList) {
        String tip = getText("pm.applyinfo.fail");
        try {

            // ???????????????????????????
            for (int i = 1; i <= intNum; i++) {
                /*
                 * if (AUTO.equals(audit)) { OrderInfo orderInfo = orderInfos.get(i - 1);
                 * orderInfo.setEffectiveTime(resp.getPmInfo().get(i - 1).get("ACCEPT_TIME")); //
                 * ???????????????.????????????????????? if (!pmStatusQuery.equals(QUERYCODE)) {
                 * orderInfo.setExpireTime(DateParse.countExpireTime( orderInfo.getLengthUnit(),
                 * orderInfo.getEffectiveTime(), orderInfo.getLengthTime())); } }
                 */

                // ???????????????
                updateBatchVO.add(new BatchVO(addTip, "createPMOrder", orderInfos.get(i - 1)));

                // ??????PM????????????
                PMInstanceInfo pmInstanceInfo = new PMInstanceInfo();
                OsInfo tempOsInfo = (OsInfo) ibatisDAO.getSingleRecord("queryOsName", osId);
                if (AUTO.equals(audit)) {
                    assemblePMInstance(userId, orderInfos.get(i - 1),
                            resp.getPmInfoList().get(i - 1), pmInstanceInfo, tempOsInfo, appId, 
                            updateBatchVO,pmNetList);
                } else {
                    assemblePMInstanceAudit(userId, orderInfos.get(i - 1), pmInstanceInfo,tempOsInfo, 
                    		appId);
                }

                pmInstances.add(pmInstanceInfo);

                // ???????????????
                updateBatchVO.add(new BatchVO(addTip, "createPMInstanceInfo", pmInstances
                        .get(i - 1)));
                if (logger.isDebugEnable()) {
                    logger.debug("????????????????????????" + i + "???");
                }
            }
        } catch (Exception e2) {
            // ????????????????????????????????????error????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            logger.error(PMStatusCode.CREATEPMORDER_EXCEPTION_CODE, tip, e2);
            this.addActionError(tip);
            errMsg = tip;
            return false;
        }
        try {
            ibatisDAO.updateBatchData(updateBatchVO);
            if (logger.isDebugEnable()) {
                logger.debug("?????????????????????????????????????????????");
            }
        } catch (Exception e2) {
            // ????????????????????????????????????error????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            logger.error(PMStatusCode.CREATEPMORDER_EXCEPTION_CODE, tip, e2);
            this.addActionError(tip);
            errMsg = tip;
            return false;
        }
        return true;
    }

    /**
     * assemblePMCreateFail ?????????????????????????????????
     * @param tempPMReq ???????????????
     * @param pmResp ??????
     * @return ????????????List
     */
    private List<PMCreateFail> assemblePMCreateFail(RPPPMCreateReq tempPMReq, RPPPMCreateResp pmResp) {
        List<PMCreateFail> pmCreateFails = new ArrayList<PMCreateFail>();
        PMCreateFail pmCreateFail = new PMCreateFail();
        pmCreateFail.setFailCause(pmResp.getResultMessage());
        pmCreateFail.setFailCode(pmResp.getResultCode());
        pmCreateFail.setResPoolId(tempPMReq.getResourcePoolId());
        pmCreateFail.setResPoolPartId(tempPMReq.getResourcePoolPartId());
        pmCreateFail.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        pmCreateFail.setStandardId(tempPMReq.getStandardId());
        pmCreateFail.setNum(String.valueOf(tempPMReq.getCount()));
        pmCreateFails.add(pmCreateFail);
        return pmCreateFails;
    }

    /**
     * assemblePMInstance ?????????????????????
     * @param userId ??????ID
     * @param orderInfo ????????????
     * @param pmInfoSet ???????????????
     * @param pmInstanceInfo ?????????????????????
     */
    private void assemblePMInstance(String userId, OrderInfo orderInfo, PMInfo pmInfo,
            PMInstanceInfo pmInstanceInfo,OsInfo tempOsInfo,  String appId, List<BatchVO> updateBatchVO,
            List<NetInfo> pmNetList) {
        pmInstanceInfo.setCaseId(orderInfo.getCaseId());
        pmInstanceInfo.setOrderId(orderInfo.getOrderId());
        pmInstanceInfo.setStatus(PMStatus.CREATING);

        pmInstanceInfo.setPmId(pmInfo.getPmId());
        pmInstanceInfo.setOperationIP(pmInfo.getOperationIP());
        pmInstanceInfo.setPmUser(pmInfo.getUserName());
        pmInstanceInfo.setPmPassword(pmInfo.getPassWord());

        pmInstanceInfo.setParamFlag(configuration);
        if ("0".equals(configuration)) {
            pmInstanceInfo.setItemId(itemId);
            pmInstanceInfo.setStandardId(standardId);
        }

        /*
         * if (pmStatusQuery.equals(QUERYCODE)) { pmInstanceInfo.setStatus(PMStatus.CREATING); }
         * else { pmInstanceInfo.setStatus(PMStatus.RUNNING); }
         */
        pmInstanceInfo.setAppId(appId);
        pmInstanceInfo.setResPoolId(respoolId);
        pmInstanceInfo.setResPoolPartId(respoolPartId);

        pmInstanceInfo.setCpuType(cpuType);
        pmInstanceInfo.setRamSize(ramSize);
        pmInstanceInfo.setDiscSize(discSize);
        pmInstanceInfo.setServerType(serverType);
        pmInstanceInfo.setDescription(pmRemark);
        pmInstanceInfo.setCreateTime(TIMESTAMP.print(new DateTime()));
        pmInstanceInfo.setCreateUser(userId);
        pmInstanceInfo.setUpdateTime(TIMESTAMP.print(new DateTime()));
        pmInstanceInfo.setUpdateUser(userId);
        pmInstanceInfo.setResPoolName(respoolName);
        pmInstanceInfo.setResPoolPartName(respoolPartName);

        pmInstanceInfo.setIsoId(osId);
        pmInstanceInfo.setIsoName(tempOsInfo.getOsName());
        pmInstanceInfo.setIsoDescription(tempOsInfo.getOsDescription());
        // pmInstanceInfo.setIsoName(tempOsInfo.getOsName());

        if (pmNetList != null && pmNetList.size() > 0) {
            NetInfo netInfo = new NetInfo();
            netInfo.setCaseId(orderInfo.getCaseId());
            netInfo.setPmId(pmInfo.getPmId());

            updateBatchVO.add(new BatchVO("MOD", "updatePmNet", netInfo));
        }
    }

    /**
     * assemblePMInstanceAudit ?????????????????????
     * @param userId ??????ID
     * @param orderInfo ????????????
     * @param pmInstanceInfo ?????????????????????
     */
    private void assemblePMInstanceAudit(String userId, OrderInfo orderInfo,
            PMInstanceInfo pmInstanceInfo, OsInfo tempOsInfo, String appId) {

        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        pmInstanceInfo.setCaseId(orderInfo.getCaseId());
        pmInstanceInfo.setOrderId(orderInfo.getOrderId());
        pmInstanceInfo.setStatus(PMStatus.PREPARE);

        pmInstanceInfo.setParamFlag(configuration);
        if ("0".equals(configuration)) {
            pmInstanceInfo.setItemId(itemId);
            pmInstanceInfo.setStandardId(standardId);
        }
        pmInstanceInfo.setPmName(pmName);
        pmInstanceInfo.setResPoolId(respoolId);
        pmInstanceInfo.setResPoolPartId(respoolPartId);

        pmInstanceInfo.setCpuType(cpuType);
        pmInstanceInfo.setRamSize(ramSize);
        pmInstanceInfo.setDiscSize(discSize);
        pmInstanceInfo.setServerType(serverType);
        pmInstanceInfo.setIsoId(osId);
        pmInstanceInfo.setIsoName(tempOsInfo.getOsName());
        pmInstanceInfo.setIsoDescription(tempOsInfo.getOsDescription());
        // pmInstanceInfo.setIsoName(tempOsInfo.getOsName());
        pmInstanceInfo.setDescription(pmRemark);
        pmInstanceInfo.setCreateTime(time);
        pmInstanceInfo.setCreateUser(userId);
        pmInstanceInfo.setUpdateTime(time);
        pmInstanceInfo.setUpdateUser(userId);
        pmInstanceInfo.setResPoolName(respoolName);
        pmInstanceInfo.setResPoolPartName(respoolPartName);
        pmInstanceInfo.setAppId(appId);

    }

    /**
     * assembleOrder ??????????????????
     * @param userId ??????ID
     * @param orderInfo ????????????
     * @param orderId ??????ID
     * @param caseId ??????ID
     */
    private void assembleOrder(String userId, OrderInfo orderInfo, String orderId, String caseId,
            String respoolId, String appId) {
        orderInfo.setOrderId(orderId);
        /*
         * if (AUTO.equals(audit)) { if (pmStatusQuery.equals(QUERYCODE)) {
         * orderInfo.setStatus("1"); } else { orderInfo.setStatus("3"); } } else {
         * orderInfo.setStatus("0"); }
         */
        if (AUTO.equals(audit)) {
            orderInfo.setStatus("1");
        } else {
            orderInfo.setStatus("0");
        }
        String[] templengthTime = lengthTime.split("_");
        orderInfo.setLengthTime(templengthTime[0]);
        orderInfo.setLengthUnit(templengthTime[1]);
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);
        orderInfo.setItemId(itemId);
        orderInfo.setCaseId(caseId);
        orderInfo.setAppId(appId);
        orderInfo.setResPoolId(respoolId);
        orderInfo.setCaseType("1");
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPmRemark() {
        return pmRemark;
    }

    public void setPmRemark(String pmRemark) {
        this.pmRemark = pmRemark;
    }

    public String getLengthTime() {
        return lengthTime;
    }

    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public String getRespoolId() {
        return respoolId;
    }

    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    public String getRespoolPartId() {
        return respoolPartId;
    }

    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
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

    public String getRespoolName() {
        return respoolName;
    }

    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    public String getRespoolPartName() {
        return respoolPartName;
    }

    public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    public PMCreate getPmCreate() {
        return pmCreate;
    }

    public void setPmCreate(PMCreate pmCreate) {
        this.pmCreate = pmCreate;
    }

    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public PMStatusService getPmStatusService() {
        return pmStatusService;
    }

    public void setPmStatusService(PMStatusService pmStatusService) {
        this.pmStatusService = pmStatusService;
    }

    /**
     * ??????queryBusinessId????????????
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    /**
     * ??????queryBusinessId????????????
     * @return Returns the queryBusinessId.
     */
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    /**
     * getAudit ??????????????????
     * @return ??????????????????
     */
    public String getAudit() {
        return audit;
    }

    /**
     * setAudit ??????????????????
     * @param audit ??????????????????
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    /**
     * setPmStatusQuery
     * @param pmStatusQuery
     */

    public void setPmStatusQuery(String pmStatusQuery) {
        this.pmStatusQuery = pmStatusQuery;
    }

    /**
     * ??????type????????????
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * ??????type????????????
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    public static LogService getLogger() {
        return logger;
    }

    public static void setLogger(LogService logger) {
        PMApplyInfoAction.logger = logger;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public String[] getEthList1vlanId() {
        return ethList1vlanId;
    }

    public void setEthList1vlanId(String[] ethList1vlanId) {
        this.ethList1vlanId = ethList1vlanId;
    }

    public String[] getEthList2vlanId() {
        return ethList2vlanId;
    }

    public void setEthList2vlanId(String[] ethList2vlanId) {
        this.ethList2vlanId = ethList2vlanId;
    }

    public String[] getEthList3vlanId() {
        return ethList3vlanId;
    }

    public void setEthList3vlanId(String[] ethList3vlanId) {
        this.ethList3vlanId = ethList3vlanId;
    }

    public String[] getEthList4vlanId() {
        return ethList4vlanId;
    }

    public void setEthList4vlanId(String[] ethList4vlanId) {
        this.ethList4vlanId = ethList4vlanId;
    }

    public String[] getEthList5vlanId() {
        return ethList5vlanId;
    }

    public void setEthList5vlanId(String[] ethList5vlanId) {
        this.ethList5vlanId = ethList5vlanId;
    }

    public String[] getEthList1ipsegmentId() {
        return ethList1ipsegmentId;
    }

    public void setEthList1ipsegmentId(String[] ethList1ipsegmentId) {
        this.ethList1ipsegmentId = ethList1ipsegmentId;
    }

    public String[] getEthList2ipsegmentId() {
        return ethList2ipsegmentId;
    }

    public void setEthList2ipsegmentId(String[] ethList2ipsegmentId) {
        this.ethList2ipsegmentId = ethList2ipsegmentId;
    }

    public String[] getEthList3ipsegmentId() {
        return ethList3ipsegmentId;
    }

    public void setEthList3ipsegmentId(String[] ethList3ipsegmentId) {
        this.ethList3ipsegmentId = ethList3ipsegmentId;
    }

    public String[] getEthList4ipsegmentId() {
        return ethList4ipsegmentId;
    }

    public void setEthList4ipsegmentId(String[] ethList4ipsegmentId) {
        this.ethList4ipsegmentId = ethList4ipsegmentId;
    }

    public String[] getEthList5ipsegmentId() {
        return ethList5ipsegmentId;
    }

    public void setEthList5ipsegmentId(String[] ethList5ipsegmentId) {
        this.ethList5ipsegmentId = ethList5ipsegmentId;
    }

    public String[] getEthList1ip() {
        return ethList1ip;
    }

    public void setEthList1ip(String[] ethList1ip) {
        this.ethList1ip = ethList1ip;
    }

    public String[] getEthList2ip() {
        return ethList2ip;
    }

    public void setEthList2ip(String[] ethList2ip) {
        this.ethList2ip = ethList2ip;
    }

    public String[] getEthList3ip() {
        return ethList3ip;
    }

    public void setEthList3ip(String[] ethList3ip) {
        this.ethList3ip = ethList3ip;
    }

    public String[] getEthList4ip() {
        return ethList4ip;
    }

    public void setEthList4ip(String[] ethList4ip) {
        this.ethList4ip = ethList4ip;
    }

    public String[] getEthList5ip() {
        return ethList5ip;
    }

    public void setEthList5ip(String[] ethList5ip) {
        this.ethList5ip = ethList5ip;
    }

    public String[] getEthList1gateway() {
        return ethList1gateway;
    }

    public void setEthList1gateway(String[] ethList1gateway) {
        this.ethList1gateway = ethList1gateway;
    }

    public String[] getEthList2gateway() {
        return ethList2gateway;
    }

    public void setEthList2gateway(String[] ethList2gateway) {
        this.ethList2gateway = ethList2gateway;
    }

    public String[] getEthList3gateway() {
        return ethList3gateway;
    }

    public void setEthList3gateway(String[] ethList3gateway) {
        this.ethList3gateway = ethList3gateway;
    }

    public String[] getEthList4gateway() {
        return ethList4gateway;
    }

    public void setEthList4gateway(String[] ethList4gateway) {
        this.ethList4gateway = ethList4gateway;
    }

    public String[] getEthList5gateway() {
        return ethList5gateway;
    }

    public void setEthList5gateway(String[] ethList5gateway) {
        this.ethList5gateway = ethList5gateway;
    }

    public String getAddTip() {
        return addTip;
    }

    public void setAddTip(String addTip) {
        this.addTip = addTip;
    }

    public static String getSucceessCode() {
        return SUCCEESS_CODE;
    }

    public static String getAuto() {
        return AUTO;
    }

    public static String getQuerycode() {
        return QUERYCODE;
    }

    public String getPmStatusQuery() {
        return pmStatusQuery;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

	public String getFromPmApplyAction() {
		return fromPmApplyAction;
	}

	public void setFromPmApplyAction(String fromPmApplyAction) {
		this.fromPmApplyAction = fromPmApplyAction;
	}

}
