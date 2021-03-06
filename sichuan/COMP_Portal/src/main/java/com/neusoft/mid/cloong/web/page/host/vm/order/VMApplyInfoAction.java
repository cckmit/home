/*******************************************************************************
 * @(#)VMApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.common.util.ip.Ipv6Util;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMCreate;
import com.neusoft.mid.cloong.host.vm.core.VMCreateFail;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp.VMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMEthPurpose;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.IPInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VlanIPBind;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.vmNet;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.IpSegment;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * ???????????????????????????
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-18 ??????1:43:21
 */
public class VMApplyInfoAction extends ResourceManagementBaseAction {

    /**
     * ??????????????????
     */
    private static final long serialVersionUID = -3246558237673423698L;

    /**
     * ???????????????
     */
    private static LogService logger = LogService.getLogger(VMApplyInfoAction.class);

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
     * ??????
     */
    private String num;

    /**
     * ??????
     */
    private String lengthTime;
    
    /**
     * ????????????.
     */
    private String chargeType;

    /**
     * ??????
     */
    private String vmRemark;

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
    private String cpuNum;

    /**
     * ????????????
     */
    private String ramSize;

    /**
     * ????????????
     */
    private String discSize;

    /**
     * ?????? ??????
     */
    private String type = "0";

    /**
     * ???????????????
     */
    private String vmName;
    
    /**
     * ?????????id
     */
    private String pmId;

    /**
     * ???1???vlan
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
    
    // IP??????
    private String ipType;

    /**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	private String[] ethList2gateway;

    private String[] ethList3gateway;

    private String[] ethList4gateway;

    private String[] ethList5gateway;

    /**
     * ????????????????????????
     */
    private VMStatusService vmStatusService;

    /**
     * ??????????????????
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * ?????????????????????
     */
    private VMCreate vmCreate;

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
     * tip ACCEPT_TIME
     */
    private String acceptTimeTip = "ACCEPT_TIME";

    /**
     * tip ADD
     */
    private String addTip = "ADD";

    private String caseId1 = "";

    private String caseId2 = "";

    private String caseId3 = "";

    private String caseId4 = "";

    private String caseId5 = "";

    private String appId1;
    
    // bossOrderId,????????????????????????????????????????????????????????????boss?????????
    private String vmBossOrderId;
    
    // ?????????????????????IP????????????????????????????????????(???????????????8,16)
    private int powerNum;
    
    /**
     * execute Action????????????
     * @return ?????????
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        try {
        	ethList1vlanId = new String[Integer.parseInt(num)];
        	ethList1ipsegmentId = new String[Integer.parseInt(num)];
        	ethList1ip = new String[Integer.parseInt(num)];
        	ethList1gateway = new String[Integer.parseInt(num)];
        	// ========== search vlanList begin ==============
        	logger.info("***********appId:" + appId);
        	if (null == appId) {
        		appId = appId1;
        	}
        	List<VlanInfo> vlanList = searchVlanList();
            // =========== search vlanList end ==============
            
            // ============ search IpSegmentListByVlan being ===============
        	int ipNum = 0;  // ?????????ip?????????ip?????????????????????vm?????????????????????????????????ip?????????
			for (VlanInfo vlanTmp : vlanList) { // ????????????????????????????????????????????????????????????vlan?????????????????????????????????????????????
				logger.info("???????????????vlan ??????" + vlanTmp.getVlanName());
				if (ipNum < Integer.parseInt(num)) { // ipNum???num??????????????????????????????????????????vlanInfo????????????
					
					List<IpSegmentInfo> ipSegmentList = searchIpSegmentListByVlan(vlanTmp);
					// ============ search privateIpListByIpSegment begin ===================
		            try {
		                Map<String, String> useIp = new HashMap<String, String>();
		                // ?????????????????????ip???????????????????????????ip???????????????????????????ip
		                searchIPUsed(vlanTmp, useIp);	
	                     // ===== ??????Ip????????? =====
	                    for (IpSegmentInfo ipSegmentInfo : ipSegmentList) {
	                    	logger.info("???????????????ip?????????" + ipSegmentInfo.getIpSegment());
	                    	List<IPInfo>  ipInfos = new ArrayList<IPInfo>();
	                    	
	                    	// searchIpInfosBySegmentWithoutUserIp
	                    	IpSegment ipSegment = searchIPsBySegment(useIp, ipSegmentInfo, ipInfos);
	    	                
	                    	// SET vlan, ipsegment, ip, gateway's value
//	    	                if (ipNum < Integer.parseInt(num)) {
	    	                	if (ipInfos.size() >= Integer.parseInt(num)) { // ip?????????ip??????????????????vm?????????
	        	                	for (int i = 0; i < Integer.parseInt(num); i++) {
	        	                		if (ipNum < Integer.parseInt(num)) {
	        	                			// ??????IP
		        	                		ethList1ip[i] = ipInfos.get(i).getIp();
		        	                		if ("0".equals(ipType)) { // IPV4
		        	                			// ????????????ip???????????????(?????????????????????ip??????????????????????????????ipSubnet???????????????)
			        	                		ethList1gateway[i] = ipSegment.getIpSubnet().split("/")[0];
		        	                		} else if ("1".equals(ipType)) { // IPV6
		        	                			// ????????????ip???????????????(?????????????????????ip??????????????????????????????ipSubnet???????????????+1)
			        	                		ethList1gateway[i] = ipSegment.getIpSubnet().split("/")[0] + "1"; // IPV6????????????????????????1??????
		        	                		}
		        	                		// ??????ip???
		                            		ethList1ipsegmentId[i] = ipSegmentInfo.getIpSegmentId();
		                            		// ??????vlan
		                            		ethList1vlanId[i] = vlanTmp.getVlanId();
		                            		ipNum ++;
	        	                		}
	        	                	}
	        	                	break;
	        	                } else if (ipInfos.size() < Integer.parseInt(num) && ipInfos.size() > 0) { // ip?????????ip??????????????????vm?????????(?????????0)
	        	                	for (int i = 0; i < ipInfos.size(); i++) {
	        	                		if (ipNum < Integer.parseInt(num)) {
	        	                			// ??????IP
		        	                		ethList1ip[ipNum] = ipInfos.get(i).getIp();
		        	                		if ("0".equals(ipType)) { // IPV4
		        	                			// ????????????ip???????????????(?????????????????????ip??????????????????????????????ipSubnet???????????????)
			        	                		ethList1gateway[ipNum] = ipSegment.getIpSubnet().split("/")[0];
		        	                		} else if ("1".equals(ipType)) { // IPV6
		        	                			// ????????????ip???????????????(?????????????????????ip??????????????????????????????ipSubnet???????????????+1)
			        	                		ethList1gateway[ipNum] = ipSegment.getIpSubnet().split("/")[0] + "1"; // IPV6????????????????????????1??????
		        	                		}
		        	                		// ??????ip???
		                            		ethList1ipsegmentId[ipNum] = ipSegmentInfo.getIpSegmentId();
		                            		// ??????vlan
		                            		ethList1vlanId[ipNum] = vlanTmp.getVlanId();
		                            		ipNum++;
	        	                		}
	        	                	}
	        	                	continue;
	        	                }
//	    	                }
	                    }
	                    
	                    if ("1".equals(num)) {
		                	// TODO do nothing
		                } else if ("2".equals(num)) {
		                	fixList2();
		                } else if ("3".equals(num)) {
		                	fixList2();
		                	fixList3();
		                } else if ("4".equals(num)) {
		                	fixList2();
		                	fixList3();
		                	fixList4();
		                } else if ("5".equals(num)) {
		                	fixList2();
		                	fixList3();
		                	fixList4();
		                	fixList5();
		                }
	                    fixList(num);
	                    
		            } catch (Exception e) {
		                logger.error(VMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
		                        getText("vmonload.ip.fail"), e);
		            }
		            // ============ search privateIpListByIpSegment end =====================
		            
				}
				
	            
			}
			
			// ??????ipsegement???vlan?????????????????????vm????????????????????????
            if (ipNum < Integer.parseInt(num)) {
            	logger.info("??????ip????????????" + ipNum);
            	logger.error("????????????????????????????????????IP????????????");
            	errMsg = "????????????????????????????????????IP?????????????????????IP?????????vlan???";
                return ConstantEnum.FAILURE.toString();
            }
            // ============ search IpSegmentListByVlan end =================
            
            
            
            logger.debug("?????????????????????");
            printVmInformation();

            caseId1 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId2 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId3 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId4 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId5 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());

            // session???????????????ID
            String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                    .getAttribute(SessionKeys.userInfo.toString())).getUserId();

            RPPVMCreateReq vmReq = new RPPVMCreateReq();
            // ?????????
            vmReq.setSerialNum(sequenceGenerator.generatorVMSerialNum("VMAdd"));

            if (configuration.equals("0")) {
                vmReq.setStandardId(standardId);
                vmReq.setCreateModel(VMCreateModel.TemplateModel);
            } else {
                standardId = "";
                itemId = "";
                vmReq.setCreateModel(VMCreateModel.CustomModel);
            }
            // ???????????????????????????
            int intNum = Integer.parseInt(num);
            vmReq.setAppId(appId);

            BusinessInfo app = (BusinessInfo) ibatisDAO.getSingleRecord("queryBusinessByAppId",
                    appId);
            vmReq.setAppName(app.getName());

            vmReq.setCount(intNum);
            vmReq.setCpuNum(Integer.valueOf(cpuNum));
            vmReq.setMemorySizsMB(Integer.valueOf(ramSize) * 1024);
            vmReq.setOsDiskType("0");
            /*
             * ????????????????????????????????????????????????????????????????????????????????? vmReq.setVmName(vmName);
             */
            vmReq.setOsId(osId);
            vmReq.setPmId(pmId);
            Float diSize = Float.parseFloat(discSize) * 1024;
            vmReq.setOsSizeGB(Math.round(diSize));
            vmReq.setResourcePoolId(respoolId);
            vmReq.setResourcePoolPartId(respoolPartId);
            vmReq.setSecurity("1");

            // ????????????
            List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();
            // ??????????????????net List
            List<vmNet> vmNetList = new ArrayList<vmNet>();
            vmReq.setEthList(ethList);

            List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();

            if (ethList1vlanId != null && ethList1vlanId.length > 0) {
                convertVmNet(ethList1vlanId, ethList1ipsegmentId, ethList1ip, ethList1gateway,
                        ethList, vmNetList, userId, caseId1, updateBatchVO);
            }
            if (ethList2vlanId != null && ethList2vlanId.length > 0) {
                convertVmNet(ethList2vlanId, ethList2ipsegmentId, ethList2ip, ethList2gateway,
                        ethList, vmNetList, userId, caseId2, updateBatchVO);
            }
            if (ethList3vlanId != null && ethList3vlanId.length > 0) {
                convertVmNet(ethList3vlanId, ethList3ipsegmentId, ethList3ip, ethList3gateway,
                        ethList, vmNetList, userId, caseId3, updateBatchVO);
            }
            if (ethList4vlanId != null && ethList4vlanId.length > 0) {
                convertVmNet(ethList4vlanId, ethList4ipsegmentId, ethList4ip, ethList4gateway,
                        ethList, vmNetList, userId, caseId4, updateBatchVO);
            }
            if (ethList5vlanId != null && ethList5vlanId.length > 0) {
                convertVmNet(ethList5vlanId, ethList5ipsegmentId, ethList5ip, ethList5gateway,
                        ethList, vmNetList, userId, caseId5, updateBatchVO);
            }

            // ??????????????????
            if (vmNetList != null && vmNetList.size() > 0) {
                for (vmNet vmNet : vmNetList) {
                    BatchVO vo = new BatchVO("ADD", "insertVmNet", vmNet);
                    updateBatchVO.add(vo);
                }
            }

            // ??????????????????
            List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
            // ??????????????????ID
            String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.VM
                    .getParentCode());
            for (int i = 1; i <= intNum; i++) {
                OrderInfo orderInfo = new OrderInfo();
                String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.VM.toString());
                // String caseId = sequenceGenerator
                // .generatorCaseId(ResourceTypeEnum.VM.toString());
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

            RPPVMCreateResp resp = new RPPVMCreateResp();
            resp.setResultCode(SUCCEESS_CODE);

            if (AUTO.equals(audit)) {
                // ???????????????????????????
                resp = vmCreate.createCustomVM(vmReq);

            }

            List<VMInstanceInfo> vmInrstances = new ArrayList<VMInstanceInfo>();
            boolean doWithRespRes = true;
            doWithRespRes = doWithResp(userId, vmReq, updateBatchVO, orderInfos, intNum, resp,
                    vmInrstances, appId, vmNetList);

            if (!doWithRespRes) {
                return ConstantEnum.FAILURE.toString();
            }

            if (AUTO.equals(audit)) {
                // ???????????????????????????????????????????????????
                for (VMInstanceInfo vmInfo : vmInrstances) {
                    vmStatusService.addVMStatus(vmInfo.getVmId(), vmInfo.getStatus());
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

    // =======================================================
    /*
     * vlan,segment,gateway,ip?????????5??????????????????5???????????????????????????????????????(?????????????????????????????????????????????)
     */
	private void fixList5() {
		ethList5ip = new String[1];
		ethList5gateway = new String[1];
		ethList5ipsegmentId = new String[1];
		ethList5vlanId = new String[1];
		
		ethList5ip[0] = ethList1ip[4];
		ethList5gateway[0] = ethList1gateway[4];	
		ethList5ipsegmentId[0] = ethList1ipsegmentId[4];
		ethList5vlanId[0] = ethList1vlanId[4];
//		ethList1ip = ArrayUtils.remove(ethList1ip, 0);
//		ethList1gateway = ArrayUtils.remove(ethList1gateway, 0);
//		ethList1ipsegmentId = ArrayUtils.remove(ethList1ipsegmentId, 0);
//		ethList1vlanId = ArrayUtils.remove(ethList1vlanId, 0);
	}

	private void fixList4() {
		ethList4ip = new String[1];
		ethList4gateway = new String[1];
		ethList4ipsegmentId = new String[1];
		ethList4vlanId = new String[1];
		
		ethList4ip[0] = ethList1ip[3];
		ethList4gateway[0] = ethList1gateway[3];	
		ethList4ipsegmentId[0] = ethList1ipsegmentId[3];
		ethList4vlanId[0] = ethList1vlanId[3];
//		ethList1ip = ArrayUtils.remove(ethList1ip, 0);
//		ethList1gateway = ArrayUtils.remove(ethList1gateway, 0);
//		ethList1ipsegmentId = ArrayUtils.remove(ethList1ipsegmentId, 0);
//		ethList1vlanId = ArrayUtils.remove(ethList1vlanId, 0);
	}

	private void fixList3() {
		ethList3ip = new String[1];
		ethList3gateway = new String[1];
		ethList3ipsegmentId = new String[1];
		ethList3vlanId = new String[1];
		
		ethList3ip[0] = ethList1ip[2];
		ethList3gateway[0] = ethList1gateway[2];	
		ethList3ipsegmentId[0] = ethList1ipsegmentId[2];
		ethList3vlanId[0] = ethList1vlanId[2];
//		ethList1ip = ArrayUtils.remove(ethList1ip, 0);
//		ethList1gateway = ArrayUtils.remove(ethList1gateway, 0);
//		ethList1ipsegmentId = ArrayUtils.remove(ethList1ipsegmentId, 0);
//		ethList1vlanId = ArrayUtils.remove(ethList1vlanId, 0);
	}

	private void fixList2() {
		ethList2ip = new String[1];
		ethList2gateway = new String[1];
		ethList2ipsegmentId = new String[1];
		ethList2vlanId = new String[1];
		
		ethList2ip[0] = ethList1ip[1];
		ethList2gateway[0] = ethList1gateway[1];	
		ethList2ipsegmentId[0] = ethList1ipsegmentId[1];
		ethList2vlanId[0] = ethList1vlanId[1];
//		ethList1ip = ArrayUtils.remove(ethList1ip, 1);
//		ethList1gateway =ArrayUtils.remove(ethList1gateway, 1);
//		ethList1ipsegmentId = ArrayUtils.remove(ethList1ipsegmentId, 1);
//		ethList1vlanId = ArrayUtils.remove(ethList1vlanId, 1);
	}
	
	private void fixList(String num) {
		if ("2".equals(num)) {
			ethList1ip = ArrayUtils.remove(ethList1ip, 1);
			ethList1gateway =ArrayUtils.remove(ethList1gateway, 1);
			ethList1ipsegmentId = ArrayUtils.remove(ethList1ipsegmentId, 1);
			ethList1vlanId = ArrayUtils.remove(ethList1vlanId, 1);
		} else if ("3".equals(num)) {
			ethList1ip = ArrayUtils.removeAll(ethList1ip, 1, 2);
			ethList1gateway =ArrayUtils.removeAll(ethList1gateway, 1, 2);
			ethList1ipsegmentId = ArrayUtils.removeAll(ethList1ipsegmentId, 1, 2);
			ethList1vlanId = ArrayUtils.removeAll(ethList1vlanId, 1, 2);
		} else if ("4".equals(num)) {
			ethList1ip = ArrayUtils.removeAll(ethList1ip, 1, 2, 3);
			ethList1gateway =ArrayUtils.removeAll(ethList1gateway, 1, 2, 3);
			ethList1ipsegmentId = ArrayUtils.removeAll(ethList1ipsegmentId, 1, 2, 3);
			ethList1vlanId = ArrayUtils.removeAll(ethList1vlanId, 1, 2, 3);
		} else if ("5".equals(num)) {
			ethList1ip = ArrayUtils.removeAll(ethList1ip, 1, 2, 3, 4);
			ethList1gateway =ArrayUtils.removeAll(ethList1gateway, 1, 2, 3, 4);
			ethList1ipsegmentId = ArrayUtils.removeAll(ethList1ipsegmentId, 1, 2, 3, 4);
			ethList1vlanId = ArrayUtils.removeAll(ethList1vlanId, 1, 2, 3, 4);
		}
		
	}
	// ========================================================
	
    /*
     * searchIPUsed
     */
	private void searchIPUsed(VlanInfo vlanTmp, Map<String, String> useIp) throws SQLException {
		// ?????????????????????????????????vlan?????????ip??????????????????????????????ip?????????????????????
		List<NetInfo> netInfos = ibatisDAO.getData("getAllNet", vlanTmp.getVlanId());
		for (NetInfo netInfo : netInfos) {
		    useIp.put(netInfo.getIp(), netInfo.getIp());
		    useIp.put(netInfo.getGateway(), netInfo.getGateway());
		}
		// ?????????????????????????????????vlan?????????ip?????????
		List<NetInfo> netInfosForVmMod = ibatisDAO.getData("getAllNetForVmMod", vlanTmp.getVlanId());
		for (NetInfo netInfo : netInfosForVmMod) {
			useIp.put(netInfo.getIp(), netInfo.getIp());
			useIp.put(netInfo.getGateway(), netInfo.getGateway());
		}
		// ??????????????????????????????vlan?????????ip
		List<NetInfo> LBnetInfos = ibatisDAO.getData("getALLnetforLB", vlanTmp.getVlanId());
		 for (NetInfo netInfo : LBnetInfos) {
		     useIp.put(netInfo.getIp(), netInfo.getIp());
		}
	}

    /*
     * searchIPsBySegment
     */
	private IpSegment searchIPsBySegment(Map<String, String> useIp, IpSegmentInfo ipSegmentInfo, List<IPInfo> ipInfos)
			throws SQLException {
		// ??????ip???ID ??????ip????????????ip
		IpSegment ipSegment = (IpSegment) ibatisDAO.getSingleRecord("getIPsegmentByID", ipSegmentInfo.getIpSegmentId());
		String startIp = ipSegment.getStartIp();
		String endIp = ipSegment.getEndIp();
		String preGateWayIp = "";
		String gateWayIp = "";
		if ("0".equals(ipType)) { // IPV4
			gateWayIp = ipSegment.getIpSubnet().split("/")[0];
		} else if ("1".equals(ipType)) { // IPV6
			preGateWayIp = ipSegment.getIpSubnet().split("/")[0]; // ????????????
			gateWayIp = preGateWayIp  + "1";
		}
		List<String> ips = new ArrayList<String>();
		if ("0".equals(ipType)) { // IPV4
			ips = IpSegmentUtil.getIpList(startIp, endIp);
		} else if ("1".equals(ipType)) { // IPV6
			ips = Ipv6Util.getIpv6List(powerNum, preGateWayIp, startIp, endIp);
		}

		// ????????????ip????????????ip?????????????????????ip
		
		if (ips != null && ips.size() > 0) {
		    for (String ip : ips) {
		        if (useIp.get(ip) != null) {
		            continue;
		        }
		        IPInfo ipInfo = new IPInfo();
		        ipInfo.setIp(ip);
		        ipInfos.add(ipInfo);
		    }
		}
		for (int i = 0; i <ipInfos.size(); i++) {
			if (gateWayIp.equals(ipInfos.get(i).getIp())) {
				ipInfos.remove(i);
			}
		}
		return ipSegment;
	}
	
    /*
     * search IpSegmentListByVlan
     */
	private List<IpSegmentInfo> searchIpSegmentListByVlan(VlanInfo vlanTmp) {
		IpSegmentInfo ipSegment = new IpSegmentInfo();
		List<IpSegmentInfo> ipSegmentList = new ArrayList<IpSegmentInfo>();
		try {
			VlanInfo vlan1 = new VlanInfo();
			vlan1.setVlanId(vlanTmp.getVlanId());
			List<Integer> tmp1 = new ArrayList<Integer>();
			tmp1.add(0);
			vlan1.setCanceled(tmp1);
			vlan1 = (VlanInfo) ibatisDAO.getSingleRecord("getVlanList", vlan1);
			// ?????????????????????VLAN??????????????????????????????????????????????????????????????????????????????IP???(?????????VLAN)
			if (null == vlan1.getIpSegmentId() || ("").equals(vlan1.getIpSegmentId())) {
				ipSegment.setAppId(appId);
				ipSegment.setIpType(ipType);
				ipSegmentList = ibatisDAO.getData("getUnusedIpSegmentList", ipSegment);
				// ????????????IP???????????? ??????IP ~ ??????IP
				for (IpSegmentInfo temp : ipSegmentList) {
					temp.setIpSegment(temp.getStartIp() + " ~ " + temp.getEndIp());
				}
			} else {// ????????????VLAN????????????IP??????????????????????????????IP???
				// ??????????????????
				ipSegment.setResPoolId(vlan1.getResPoolId());
				ipSegment.setResPoolPartId(vlan1.getResPoolPartId());
				// ????????????IP???????????? ??????IP ~ ??????IP
				ipSegment.setIpSegment(vlan1.getStartIp() + " ~ " + vlan1.getEndIp());
				ipSegment.setIpSegmentId(vlan1.getIpSegmentId());
				ipSegmentList.add(ipSegment);
			}

		} catch (Exception e2) {
			logger.error(VMStatusCode.ONLOADQUERYRIPSEGMENTS_EXCEPTION_CODE, getText("vmonload.ipsegment.fail"),
					e2);
		}
		return ipSegmentList;
	}

    /*
     * search vlanList
     */
	private List<VlanInfo> searchVlanList() {
		List<VlanInfo> vlanList = null;
		VlanInfo vlan = new VlanInfo();
		List<String> list = new ArrayList<String>();
		list.add(appId); 
		vlan.setBusinessList(list);
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(0);
		vlan.setCanceled(tmp);
		vlan.setIpType(ipType);
		vlan.setResPoolId(respoolId);
		try {
		    // ????????????????????????????????????vlan??????
			logger.info("*************????????????vlan");
		    vlanList = ibatisDAO.getData("getVlansForVm", vlan);
		} catch (Exception e) {
		    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
		            MessageFormat.format(getText("vlan.queryList.fail"), getCurrentUserId()), e);
		}
		return vlanList;
	}
    

    private boolean doWithResp(String userId, RPPVMCreateReq vmReq, List<BatchVO> updateBatchVO,
            List<OrderInfo> orderInfos, int intNum, RPPVMCreateResp resp,
            List<VMInstanceInfo> vmInrstances, String appId, List<vmNet> vmNetList) {
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // ?????????????????????????????????????????????

            // ??????????????????
            try {

                // ???????????????????????????
                for (int i = 1; i <= intNum; i++) {
                    // if (AUTO.equals(audit)) {
                    // orderInfos.get(i - 1).setEffectiveTime(TIMESTAMP.print(new DateTime()));
                    // }
                    // ???????????????
                    updateBatchVO.add(new BatchVO(addTip, "createVMOrder", orderInfos.get(i - 1)));

                    // ??????VM????????????
                    VMInstanceInfo vmInstanceInfo = new VMInstanceInfo();
                    OsInfo tempOsInfo = (OsInfo) ibatisDAO.getSingleRecord("queryOsName", osId);
                    if (AUTO.equals(audit)) {
                        assembleVMInstance(userId, orderInfos.get(i - 1),
                                resp.getVmInfoList().get(i - 1), vmInstanceInfo, tempOsInfo, appId,
                                updateBatchVO, vmNetList);
                    } else {
                        assembleVMInstanceAudit(userId, orderInfos.get(i - 1), vmInstanceInfo,
                                tempOsInfo, appId);
                    }

                    vmInrstances.add(vmInstanceInfo);

                    // ???????????????
                    updateBatchVO.add(new BatchVO(addTip, "createVMInstanceInfo", vmInrstances
                            .get(i - 1)));

                    if (logger.isDebugEnable()) {
                        logger.debug("????????????????????????" + i + "???");
                    }
                }
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("?????????????????????????????????????????????");
                }
            } catch (SQLException e2) {
                // ????????????????????????????????????error????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                logger.error(VMStatusCode.CREATEVMORDER_EXCEPTION_CODE,
                        getText("vm.applyinfo.fail"), e2);
                this.addActionError(getText("vm.applyinfo.fail"));
                errMsg = getText("vm.applyinfo.fail");
                return false;
            }
        } else {
            // ???????????????????????????
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "????????????????????????????????????");
            this.addActionError(getText("vm.applyinfo.sendcreateVM.fail"));
            errMsg = getText("vm.applyinfo.sendcreateVM.fail");
            // ????????????
            List<VMCreateFail> vmCreateFails = assembleVMCreateFail(vmReq, resp);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (VMCreateFail vmCreateFail : vmCreateFails) {
                insertFailBatchVO.add(new BatchVO(addTip, "insertVMFail", vmCreateFail));
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
     * convertVmNet ??????????????????
     * @param ethListvlanId
     * @param ethListipsegmentId
     * @param ethListip
     * @param ethListgateway
     * @param ethList
     * @param vmNetList
     * @param userId
     * @param caseId
     * @throws Exception
     */
    private void convertVmNet(String[] ethListvlanId, String[] ethListipsegmentId,
            String[] ethListip, String[] ethListgateway, List<List<EthInfo>> ethList,
            List<vmNet> vmNetList, String userId, String caseId, List<BatchVO> updateBatchVO)
            throws Exception {

        if (ethListvlanId != null && ethListvlanId.length > 0) {
            List<EthInfo> ethInfos = new ArrayList<EthInfo>();
            for (String vlanId : ethListvlanId) {
                EthInfo ethInfo = new EthInfo();
                ethInfo.setVlanId(vlanId);
                ethInfos.add(ethInfo);
            }
            for (int i = 0; i < ethListipsegmentId.length; i++) {
            	logger.info("ethListipsegmentId"+i+":="+ethListipsegmentId[i]);
                IpSegmentInfo ipSegmentInfo = (IpSegmentInfo) ibatisDAO.getSingleRecord(
                        "getIPsegmentByIpSegmentId", ethListipsegmentId[i]);
                if ("0".equals(ipType)) { // IPV4
                	String mask = IpSegmentUtil.getMask(ipSegmentInfo.getIpSubnet());
                    ethInfos.get(i).setSubNetMask(mask);
                } else if ("1".equals(ipType)) { // IPV6????????????????????????????????????66aa:55dd:d:2ef::/96??????96?????????????????????
                	ethInfos.get(i).setSubNetMask(ipSegmentInfo.getIpSubnet().split("/")[1]);
                }

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
                ethInfos.get(i).setPurpose(VMEthPurpose.Business);
            }
            ethList.add(ethInfos);

            for (EthInfo ethInfo : ethInfos) {
                vmNet vmNet = new vmNet();
                vmNet.setCaseId(caseId);
                vmNet.setEth(ethInfo.getName());
                vmNet.setGateway(ethInfo.getDefaultGateWay());
                vmNet.setIp(ethInfo.getIp());
                vmNet.setPurPose("2");
                vmNet.setSubnetmask(ethInfo.getSubNetMask());
                vmNet.setVlanId(ethInfo.getVlanId());
                vmNet.setIpType(ipType);
                vmNetList.add(vmNet);
            }
        }
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
        if (AUTO.equals(audit)) {
            orderInfo.setStatus("1");
        } else {
            orderInfo.setStatus("0");
        }
        orderInfo.setLengthTime(lengthTime);
        orderInfo.setLengthUnit(chargeType);
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);
        orderInfo.setCaseId(caseId);
        orderInfo.setAppId(appId);
        orderInfo.setResPoolId(respoolId);
        orderInfo.setCaseType("0");
    }

    /**
     * assembleVMCreateFail ?????????????????????????????????
     * @param vmReq ???????????????
     * @param resp ??????
     * @return ????????????List
     */
    private List<VMCreateFail> assembleVMCreateFail(RPPVMCreateReq vmReq, RPPVMCreateResp resp) {
        List<VMCreateFail> vmCreateFails = new ArrayList<VMCreateFail>();
        VMCreateFail vmCreateFail = new VMCreateFail();
        vmCreateFail.setFailCause(resp.getResultMessage());
        vmCreateFail.setFailCode(resp.getResultCode());
        vmCreateFail.setResPoolId(vmReq.getResourcePoolId());
        vmCreateFail.setResPoolPartId(vmReq.getResourcePoolPartId());
        vmCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        vmCreateFail.setStandardId(vmReq.getStandardId());
        vmCreateFail.setOsId(vmReq.getOsId());
        vmCreateFail.setNum(String.valueOf(vmReq.getCount()));
        vmCreateFails.add(vmCreateFail);

        return vmCreateFails;
    }

    /**
     * assembleVMInstance ?????????????????????
     * @param userId ??????ID
     * @param orderInfo ????????????
     * @param vmInfo ???????????????
     * @param vmInstanceInfo ????????????
     * @param tempOsInfo OS??????
     * @throws SQLException
     */
    private void assembleVMInstance(String userId, OrderInfo orderInfo, VMInfo vmInfo,
            VMInstanceInfo vmInstanceInfo, OsInfo tempOsInfo, String appId,
            List<BatchVO> updateBatchVO, List<vmNet> vmNetList) throws SQLException {
        vmInstanceInfo.setCaseId(orderInfo.getCaseId());
        vmInstanceInfo.setOrderId(orderInfo.getOrderId());
        vmInstanceInfo.setStatus(VMStatus.CREATING);

        vmInstanceInfo.setVmId(vmInfo.getVmId());
        vmInstanceInfo.setPrivateIp(vmInfo.getOperationIP());
        vmInstanceInfo.setVmPassword(vmInfo.getPassWord());
        vmInstanceInfo.setUserName(vmInfo.getUserName());
        vmInstanceInfo.setOperationURL(vmInfo.getUrl());

        vmInstanceInfo.setParamFlag(configuration);
        if ("0".equals(configuration)) {
            vmInstanceInfo.setItemId(itemId);
            vmInstanceInfo.setStandardId(standardId);
        }

        vmInstanceInfo.setVmName(vmName);
        vmInstanceInfo.setResPoolId(respoolId);
        vmInstanceInfo.setResPoolPartId(respoolPartId);
        vmInstanceInfo.setCpuNum(cpuNum);
        vmInstanceInfo.setRamSize(String.valueOf((Integer.valueOf(ramSize) * 1024)));
        vmInstanceInfo.setDiscSize(String.valueOf(Math.round(Float.parseFloat(discSize) * 1024)));
        vmInstanceInfo.setIsoId(osId);
        vmInstanceInfo.setIsoName(tempOsInfo.getOsName());
        vmInstanceInfo.setIsoDescription(tempOsInfo.getOsDescription());
        vmInstanceInfo.setDescription(vmRemark);
        vmInstanceInfo.setCreateTime(TIMESTAMP.print(new DateTime()));
        vmInstanceInfo.setCreateUser(userId);
        vmInstanceInfo.setUpdateTime(TIMESTAMP.print(new DateTime()));
        vmInstanceInfo.setUpdateUser(userId);
        vmInstanceInfo.setResPoolName(respoolName);
        vmInstanceInfo.setResPoolPartName(respoolPartName);
        vmInstanceInfo.setAppId(appId);
        vmInstanceInfo.setPmId(pmId);
        vmInstanceInfo.setVmBossOrderId(vmBossOrderId);

        if (vmNetList != null && vmNetList.size() > 0) {
            vmNet vmNet = new vmNet();
            vmNet.setCaseId(orderInfo.getCaseId());
            vmNet.setVmId(vmInfo.getVmId());

            updateBatchVO.add(new BatchVO("MOD", "updateVmNet", vmNet));
        }
    }

    /**
     * assembleVMInstanceAudit ?????????????????????(???????????????)
     * @param userId ??????ID
     * @param orderInfo ????????????
     * @param vmInstanceInfo ????????????
     * @param tempOsInfo ???????????????
     * @param subNetwork ????????????
     */
    private void assembleVMInstanceAudit(String userId, OrderInfo orderInfo,
            VMInstanceInfo vmInstanceInfo, OsInfo tempOsInfo, String appId) {

        vmInstanceInfo.setCaseId(orderInfo.getCaseId());
        vmInstanceInfo.setOrderId(orderInfo.getOrderId());
        vmInstanceInfo.setStatus(VMStatus.PREPARE);

        vmInstanceInfo.setParamFlag(configuration);
        if ("0".equals(configuration)) {
            vmInstanceInfo.setItemId(itemId);
            vmInstanceInfo.setStandardId(standardId);
        }

        vmInstanceInfo.setVmName(vmName);
        vmInstanceInfo.setResPoolId(respoolId);
        vmInstanceInfo.setResPoolPartId(respoolPartId);
        vmInstanceInfo.setCpuNum(cpuNum);
        vmInstanceInfo.setRamSize(String.valueOf((Integer.valueOf(ramSize) * 1024)));
        vmInstanceInfo.setDiscSize(String.valueOf(Math.round(Float.parseFloat(discSize) * 1024)));
        vmInstanceInfo.setIsoId(osId);
        vmInstanceInfo.setIsoName(tempOsInfo.getOsName());
        vmInstanceInfo.setIsoDescription(tempOsInfo.getOsDescription());
        vmInstanceInfo.setDescription(vmRemark);
        vmInstanceInfo.setCreateTime(TIMESTAMP.print(new DateTime()));
        vmInstanceInfo.setCreateUser(userId);
        vmInstanceInfo.setUpdateTime(TIMESTAMP.print(new DateTime()));
        vmInstanceInfo.setUpdateUser(userId);
        vmInstanceInfo.setResPoolName(respoolName);
        vmInstanceInfo.setResPoolPartName(respoolPartName);
        vmInstanceInfo.setAppId(appId);
        vmInstanceInfo.setPmId(pmId);
        vmInstanceInfo.setVmBossOrderId(vmBossOrderId);
    }

    private void printVmInformation() {
        if (logger.isDebugEnable()) {
            logger.debug("?????????????????? 0????????? 1?????????" + audit);
            logger.debug("???????????? 0??????????????? 1????????????:" + configuration);
            if (configuration.equals("0")) {
                logger.debug("itemId = " + itemId);
                logger.debug("standardId = " + standardId);
            } else {
                logger.debug("itemId = ");
                logger.debug("standardId = ");
            }
            logger.debug("osId = " + osId);
            logger.debug("num = " + num);
            logger.debug("lengthTime = " + lengthTime);
            logger.debug("vmRemark = " + vmRemark);
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
            logger.debug("respoolName = " + respoolName);
            logger.debug("respoolPartName = " + respoolPartName);
            logger.debug("appId = " + appId);
            logger.debug("vmName = " + vmName);
            if (ethList1vlanId != null) {
                logger.debug("ethList1vlanId = " + Arrays.asList(ethList1vlanId));
            }
            if (ethList2vlanId != null) {
                logger.debug("ethList2vlanId = " + Arrays.asList(ethList2vlanId));
            }
            if (ethList3vlanId != null) {
                logger.debug("ethList3vlanId = " + Arrays.asList(ethList3vlanId));
            }
            if (ethList4vlanId != null) {
                logger.debug("ethList4vlanId = " + Arrays.asList(ethList4vlanId));
            }
            if (ethList5vlanId != null) {
                logger.debug("ethList5vlanId = " + Arrays.asList(ethList5vlanId));
            }
            if (ethList1ipsegmentId != null) {
                logger.debug("ethList1ipsegmentId = " + Arrays.asList(ethList1ipsegmentId));
            }
            if (ethList2ipsegmentId != null) {
                logger.debug("ethList2ipsegmentId = " + Arrays.asList(ethList2ipsegmentId));
            }
            if (ethList3ipsegmentId != null) {
                logger.debug("ethList3ipsegmentId = " + Arrays.asList(ethList3ipsegmentId));
            }
            if (ethList4ipsegmentId != null) {
                logger.debug("ethList4ipsegmentId = " + Arrays.asList(ethList4ipsegmentId));
            }
            if (ethList5ipsegmentId != null) {
                logger.debug("ethList5ipsegmentId = " + Arrays.asList(ethList5ipsegmentId));
            }
            if (ethList1ip != null) {
                logger.debug("ethList1ip = " + Arrays.asList(ethList1ip));
            }
            if (ethList2ip != null) {
                logger.debug("ethList2ip = " + Arrays.asList(ethList2ip));
            }
            if (ethList3ip != null) {
                logger.debug("ethList3ip = " + Arrays.asList(ethList3ip));
            }
            if (ethList4ip != null) {
                logger.debug("ethList4ip = " + Arrays.asList(ethList4ip));
            }
            if (ethList5ip != null) {
                logger.debug("ethList5ip = " + Arrays.asList(ethList5ip));
            }
            if (ethList1gateway != null) {
                logger.debug("ethList1gateway = " + Arrays.asList(ethList1gateway));
            }
            if (ethList2gateway != null) {
                logger.debug("ethList2gateway = " + Arrays.asList(ethList2gateway));
            }
            if (ethList3gateway != null) {
                logger.debug("ethList3gateway = " + Arrays.asList(ethList3gateway));
            }
            if (ethList4gateway != null) {
                logger.debug("ethList4gateway = " + Arrays.asList(ethList4gateway));
            }
            if (ethList5gateway != null) {
                logger.debug("ethList5gateway = " + Arrays.asList(ethList5gateway));
            }
        }
    }

    /**
     * ??????itemId????????????
     * @return Returns the itemId.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * ??????itemId????????????
     * @param itemId The itemId to set.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * ??????standardId????????????
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * ??????standardId????????????
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * ??????osId????????????
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * ??????osId????????????
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * ??????num????????????
     * @return Returns the num.
     */
    public String getNum() {
        return num;
    }

    /**
     * ??????num????????????
     * @param num The num to set.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * ??????lengthTime????????????
     * @return Returns the lengthTime.
     */
    public String getLengthTime() {
        return lengthTime;
    }

    /**
     * ??????lengthTime????????????
     * @param lengthTime The lengthTime to set.
     */
    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    /**
     * ??????vmRemark????????????
     * @return Returns the vmRemark.
     */
    public String getVmRemark() {
        return vmRemark;
    }

    /**
     * ??????vmRemark????????????
     * @param vmRemark The vmRemark to set.
     */
    public void setVmRemark(String vmRemark) {
        this.vmRemark = vmRemark;
    }

    /**
     * ??????respoolId????????????
     * @return Returns the respoolId.
     */
    public String getRespoolId() {
        return respoolId;
    }

    /**
     * ??????respoolId????????????
     * @param respoolId The respoolId to set.
     */
    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    /**
     * ??????respoolPartId????????????
     * @return Returns the respoolPartId.
     */
    public String getRespoolPartId() {
        return respoolPartId;
    }

    /**
     * ??????respoolPartId????????????
     * @param respoolPartId The respoolPartId to set.
     */
    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
    }

    /**
     * ??????respoolName????????????
     * @return Returns the respoolName.
     */
    public String getRespoolName() {
        return respoolName;
    }

    /**
     * ??????respoolName????????????
     * @param respoolName The respoolName to set.
     */
    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    /**
     * ??????respoolPartName????????????
     * @return Returns the respoolPartName.
     */
    public String getRespoolPartName() {
        return respoolPartName;
    }

    /**
     * ??????respoolPartName????????????
     * @param respoolPartName The respoolPartName to set.
     */
    public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    /**
     * ??????cpuNum????????????
     * @return Returns the cpuNum.
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * ??????cpuNum????????????
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * ??????ramSize????????????
     * @return Returns the ramSize.
     */
    public String getRamSize() {
        return ramSize;
    }

    /**
     * ??????ramSize????????????
     * @param ramSize The ramSize to set.
     */
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    /**
     * ??????discSize????????????
     * @return Returns the discSize.
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * ??????discSize????????????
     * @param discSize The discSize to set.
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
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

    /**
     * ??????appId????????????
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * ??????appId????????????
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * ??????ethList1vlanId????????????
     * @return Returns the ethList1vlanId.
     */
    public String[] getEthList1vlanId() {
        return ethList1vlanId;
    }

    /**
     * ??????ethList1vlanId????????????
     * @param ethList1vlanId The ethList1vlanId to set.
     */
    public void setEthList1vlanId(String[] ethList1vlanId) {
        this.ethList1vlanId = ethList1vlanId;
    }

    /**
     * ??????ethList2vlanId????????????
     * @return Returns the ethList2vlanId.
     */
    public String[] getEthList2vlanId() {
        return ethList2vlanId;
    }

    /**
     * ??????ethList2vlanId????????????
     * @param ethList2vlanId The ethList2vlanId to set.
     */
    public void setEthList2vlanId(String[] ethList2vlanId) {
        this.ethList2vlanId = ethList2vlanId;
    }

    /**
     * ??????ethList3vlanId????????????
     * @return Returns the ethList3vlanId.
     */
    public String[] getEthList3vlanId() {
        return ethList3vlanId;
    }

    /**
     * ??????ethList3vlanId????????????
     * @param ethList3vlanId The ethList3vlanId to set.
     */
    public void setEthList3vlanId(String[] ethList3vlanId) {
        this.ethList3vlanId = ethList3vlanId;
    }

    /**
     * ??????ethList4vlanId????????????
     * @return Returns the ethList4vlanId.
     */
    public String[] getEthList4vlanId() {
        return ethList4vlanId;
    }

    /**
     * ??????ethList4vlanId????????????
     * @param ethList4vlanId The ethList4vlanId to set.
     */
    public void setEthList4vlanId(String[] ethList4vlanId) {
        this.ethList4vlanId = ethList4vlanId;
    }

    /**
     * ??????ethList5vlanId????????????
     * @return Returns the ethList5vlanId.
     */
    public String[] getEthList5vlanId() {
        return ethList5vlanId;
    }

    /**
     * ??????ethList5vlanId????????????
     * @param ethList5vlanId The ethList5vlanId to set.
     */
    public void setEthList5vlanId(String[] ethList5vlanId) {
        this.ethList5vlanId = ethList5vlanId;
    }

    /**
     * ??????ethList1ipsegmentId????????????
     * @return Returns the ethList1ipsegmentId.
     */
    public String[] getEthList1ipsegmentId() {
        return ethList1ipsegmentId;
    }

    /**
     * ??????ethList1ipsegmentId????????????
     * @param ethList1ipsegmentId The ethList1ipsegmentId to set.
     */
    public void setEthList1ipsegmentId(String[] ethList1ipsegmentId) {
        this.ethList1ipsegmentId = ethList1ipsegmentId;
    }

    /**
     * ??????ethList2ipsegmentId????????????
     * @return Returns the ethList2ipsegmentId.
     */
    public String[] getEthList2ipsegmentId() {
        return ethList2ipsegmentId;
    }

    /**
     * ??????ethList2ipsegmentId????????????
     * @param ethList2ipsegmentId The ethList2ipsegmentId to set.
     */
    public void setEthList2ipsegmentId(String[] ethList2ipsegmentId) {
        this.ethList2ipsegmentId = ethList2ipsegmentId;
    }

    /**
     * ??????ethList3ipsegmentId????????????
     * @return Returns the ethList3ipsegmentId.
     */
    public String[] getEthList3ipsegmentId() {
        return ethList3ipsegmentId;
    }

    /**
     * ??????ethList3ipsegmentId????????????
     * @param ethList3ipsegmentId The ethList3ipsegmentId to set.
     */
    public void setEthList3ipsegmentId(String[] ethList3ipsegmentId) {
        this.ethList3ipsegmentId = ethList3ipsegmentId;
    }

    /**
     * ??????ethList4ipsegmentId????????????
     * @return Returns the ethList4ipsegmentId.
     */
    public String[] getEthList4ipsegmentId() {
        return ethList4ipsegmentId;
    }

    /**
     * ??????ethList4ipsegmentId????????????
     * @param ethList4ipsegmentId The ethList4ipsegmentId to set.
     */
    public void setEthList4ipsegmentId(String[] ethList4ipsegmentId) {
        this.ethList4ipsegmentId = ethList4ipsegmentId;
    }

    /**
     * ??????ethList5ipsegmentId????????????
     * @return Returns the ethList5ipsegmentId.
     */
    public String[] getEthList5ipsegmentId() {
        return ethList5ipsegmentId;
    }

    /**
     * ??????ethList5ipsegmentId????????????
     * @param ethList5ipsegmentId The ethList5ipsegmentId to set.
     */
    public void setEthList5ipsegmentId(String[] ethList5ipsegmentId) {
        this.ethList5ipsegmentId = ethList5ipsegmentId;
    }

    /**
     * ??????ethList1ip????????????
     * @return Returns the ethList1ip.
     */
    public String[] getEthList1ip() {
        return ethList1ip;
    }

    /**
     * ??????ethList1ip????????????
     * @param ethList1ip The ethList1ip to set.
     */
    public void setEthList1ip(String[] ethList1ip) {
        this.ethList1ip = ethList1ip;
    }

    /**
     * ??????ethList2ip????????????
     * @return Returns the ethList2ip.
     */
    public String[] getEthList2ip() {
        return ethList2ip;
    }

    /**
     * ??????ethList2ip????????????
     * @param ethList2ip The ethList2ip to set.
     */
    public void setEthList2ip(String[] ethList2ip) {
        this.ethList2ip = ethList2ip;
    }

    /**
     * ??????ethList3ip????????????
     * @return Returns the ethList3ip.
     */
    public String[] getEthList3ip() {
        return ethList3ip;
    }

    /**
     * ??????ethList3ip????????????
     * @param ethList3ip The ethList3ip to set.
     */
    public void setEthList3ip(String[] ethList3ip) {
        this.ethList3ip = ethList3ip;
    }

    /**
     * ??????ethList4ip????????????
     * @return Returns the ethList4ip.
     */
    public String[] getEthList4ip() {
        return ethList4ip;
    }

    /**
     * ??????ethList4ip????????????
     * @param ethList4ip The ethList4ip to set.
     */
    public void setEthList4ip(String[] ethList4ip) {
        this.ethList4ip = ethList4ip;
    }

    /**
     * ??????ethList5ip????????????
     * @return Returns the ethList5ip.
     */
    public String[] getEthList5ip() {
        return ethList5ip;
    }

    /**
     * ??????ethList5ip????????????
     * @param ethList5ip The ethList5ip to set.
     */
    public void setEthList5ip(String[] ethList5ip) {
        this.ethList5ip = ethList5ip;
    }

    /**
     * ??????ethList1gateway????????????
     * @return Returns the ethList1gateway.
     */
    public String[] getEthList1gateway() {
        return ethList1gateway;
    }

    /**
     * ??????ethList1gateway????????????
     * @param ethList1gateway The ethList1gateway to set.
     */
    public void setEthList1gateway(String[] ethList1gateway) {
        this.ethList1gateway = ethList1gateway;
    }

    /**
     * ??????ethList2gateway????????????
     * @return Returns the ethList2gateway.
     */
    public String[] getEthList2gateway() {
        return ethList2gateway;
    }

    /**
     * ??????ethList2gateway????????????
     * @param ethList2gateway The ethList2gateway to set.
     */
    public void setEthList2gateway(String[] ethList2gateway) {
        this.ethList2gateway = ethList2gateway;
    }

    /**
     * ??????ethList3gateway????????????
     * @return Returns the ethList3gateway.
     */
    public String[] getEthList3gateway() {
        return ethList3gateway;
    }

    /**
     * ??????ethList3gateway????????????
     * @param ethList3gateway The ethList3gateway to set.
     */
    public void setEthList3gateway(String[] ethList3gateway) {
        this.ethList3gateway = ethList3gateway;
    }

    /**
     * ??????ethList4gateway????????????
     * @return Returns the ethList4gateway.
     */
    public String[] getEthList4gateway() {
        return ethList4gateway;
    }

    /**
     * ??????ethList4gateway????????????
     * @param ethList4gateway The ethList4gateway to set.
     */
    public void setEthList4gateway(String[] ethList4gateway) {
        this.ethList4gateway = ethList4gateway;
    }

    /**
     * ??????ethList5gateway????????????
     * @return Returns the ethList5gateway.
     */
    public String[] getEthList5gateway() {
        return ethList5gateway;
    }

    /**
     * ??????ethList5gateway????????????
     * @param ethList5gateway The ethList5gateway to set.
     */
    public void setEthList5gateway(String[] ethList5gateway) {
        this.ethList5gateway = ethList5gateway;
    }

    /**
     * ??????vmStatusService????????????
     * @return Returns the vmStatusService.
     */
    public VMStatusService getVmStatusService() {
        return vmStatusService;
    }

    /**
     * ??????vmStatusService????????????
     * @param vmStatusService The vmStatusService to set.
     */
    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

    /**
     * ??????sequenceGenerator????????????
     * @return Returns the sequenceGenerator.
     */
    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    /**
     * ??????sequenceGenerator????????????
     * @param sequenceGenerator The sequenceGenerator to set.
     */
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * ??????vmCreate????????????
     * @return Returns the vmCreate.
     */
    public VMCreate getVmCreate() {
        return vmCreate;
    }

    /**
     * ??????vmCreate????????????
     * @param vmCreate The vmCreate to set.
     */
    public void setVmCreate(VMCreate vmCreate) {
        this.vmCreate = vmCreate;
    }

    /**
     * ??????audit????????????
     * @return Returns the audit.
     */
    public String getAudit() {
        return audit;
    }

    /**
     * ??????audit????????????
     * @param audit The audit to set.
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    /**
     * ??????acceptTimeTip????????????
     * @return Returns the acceptTimeTip.
     */
    public String getAcceptTimeTip() {
        return acceptTimeTip;
    }

    /**
     * ??????acceptTimeTip????????????
     * @param acceptTimeTip The acceptTimeTip to set.
     */
    public void setAcceptTimeTip(String acceptTimeTip) {
        this.acceptTimeTip = acceptTimeTip;
    }

    /**
     * ??????addTip????????????
     * @return Returns the addTip.
     */
    public String getAddTip() {
        return addTip;
    }

    /**
     * ??????addTip????????????
     * @param addTip The addTip to set.
     */
    public void setAddTip(String addTip) {
        this.addTip = addTip;
    }

    /**
     * ??????serialversionuid????????????
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * ??????succeessCode????????????
     * @return Returns the succeessCode.
     */
    public static String getSucceessCode() {
        return SUCCEESS_CODE;
    }

    /**
     * ??????timestamp????????????
     * @return Returns the timestamp.
     */
    public static DateTimeFormatter getTimestamp() {
        return TIMESTAMP;
    }

    /**
     * ??????auto????????????
     * @return Returns the auto.
     */
    public static String getAuto() {
        return AUTO;
    }

    /**
     * ??????configuration????????????
     * @return Returns the configuration.
     */
    public String getConfiguration() {
        return configuration;
    }

    /**
     * ??????configuration????????????
     * @param configuration The configuration to set.
     */
    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	public String getAppId1() {
		return appId1;
	}

	public void setAppId1(String appId1) {
		this.appId1 = appId1;
	}

	public String getVmBossOrderId() {
		return vmBossOrderId;
	}

	public void setVmBossOrderId(String vmBossOrderId) {
		this.vmBossOrderId = vmBossOrderId;
	}

	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	/**
	 * @return the powerNum
	 */
	public int getPowerNum() {
		return powerNum;
	}

	/**
	 * @param powerNum the powerNum to set
	 */
	public void setPowerNum(int powerNum) {
		this.powerNum = powerNum;
	}
	
}
