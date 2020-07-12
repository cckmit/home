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
 * 提交申请虚拟机订单
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-18 下午1:43:21
 */
public class VMApplyInfoAction extends ResourceManagementBaseAction {

    /**
     * 虚拟化版本号
     */
    private static final long serialVersionUID = -3246558237673423698L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(VMApplyInfoAction.class);

    /**
     * 创建模式 0：模板创建 1：自定义
     */
    private String configuration = "1";

    /**
     * 条目ID
     */
    private String itemId;

    /**
     * 规格ID
     */
    private String standardId;

    /**
     * OSID
     */
    private String osId;

    /**
     * 数量
     */
    private String num;

    /**
     * 时长
     */
    private String lengthTime;
    
    /**
     * 计费方式.
     */
    private String chargeType;

    /**
     * 备注
     */
    private String vmRemark;

    /**
     * 资源池ID
     */
    private String respoolId;

    /**
     * 资源池分区ID
     */
    private String respoolPartId;

    /**
     * 资源池名称
     */
    private String respoolName;

    /**
     * 资源池分区名称
     */
    private String respoolPartName;

    /**
     * CPU数量
     */
    private String cpuNum;

    /**
     * 内存容量
     */
    private String ramSize;

    /**
     * 磁盘容量
     */
    private String discSize;

    /**
     * 申请 类型
     */
    private String type = "0";

    /**
     * 虚拟机名称
     */
    private String vmName;
    
    /**
     * 物理机id
     */
    private String pmId;

    /**
     * 第1个vlan
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
    
    // IP类型
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
     * 虚拟机状态服务类
     */
    private VMStatusService vmStatusService;

    /**
     * 序列号生成器
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * 虚拟机创建接口
     */
    private VMCreate vmCreate;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit;

    /**
     * 默认自动审批
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
    
    // bossOrderId,创虚拟机时需要指定创建的这些虚拟机是哪个boss订单的
    private String vmBossOrderId;
    
    // 创建虚拟机每个IP段中允许多少个虚拟机数量(幂指数，如8,16)
    private int powerNum;
    
    /**
     * execute Action执行方法
     * @return 结果码
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
        	int ipNum = 0;  // 当一个ip段中的ip不足要求创建的vm的数量时记录，记录多个ip的数量
			for (VlanInfo vlanTmp : vlanList) { // 理论上一个用户登录后一个业务先只对应一个vlan，如果多个的话指定是后期扩容了
				logger.info("当前使用的vlan 是：" + vlanTmp.getVlanName());
				if (ipNum < Integer.parseInt(num)) { // ipNum和num的数量相同时，控制不再多循环vlanInfo的信息了
					
					List<IpSegmentInfo> ipSegmentList = searchIpSegmentListByVlan(vlanTmp);
					// ============ search privateIpListByIpSegment begin ===================
		            try {
		                Map<String, String> useIp = new HashMap<String, String>();
		                // 查询正在使用的ip地址，以从查询出的ip段中减去这些使用的ip
		                searchIPUsed(vlanTmp, useIp);	
	                     // ===== 循环Ip段列表 =====
	                    for (IpSegmentInfo ipSegmentInfo : ipSegmentList) {
	                    	logger.info("当前使用的ip段是：" + ipSegmentInfo.getIpSegment());
	                    	List<IPInfo>  ipInfos = new ArrayList<IPInfo>();
	                    	
	                    	// searchIpInfosBySegmentWithoutUserIp
	                    	IpSegment ipSegment = searchIPsBySegment(useIp, ipSegmentInfo, ipInfos);
	    	                
	                    	// SET vlan, ipsegment, ip, gateway's value
//	    	                if (ipNum < Integer.parseInt(num)) {
	    	                	if (ipInfos.size() >= Integer.parseInt(num)) { // ip段中的ip个数大于创建vm的个数
	        	                	for (int i = 0; i < Integer.parseInt(num); i++) {
	        	                		if (ipNum < Integer.parseInt(num)) {
	        	                			// 分配IP
		        	                		ethList1ip[i] = ipInfos.get(i).getIp();
		        	                		if ("0".equals(ipType)) { // IPV4
		        	                			// 分配这个ip的默认网关(默认网关是创建ip段时从资源池请求来的ipSubnet的前半部分)
			        	                		ethList1gateway[i] = ipSegment.getIpSubnet().split("/")[0];
		        	                		} else if ("1".equals(ipType)) { // IPV6
		        	                			// 分配这个ip的默认网关(默认网关是创建ip段时从资源池请求来的ipSubnet的前半部分+1)
			        	                		ethList1gateway[i] = ipSegment.getIpSubnet().split("/")[0] + "1"; // IPV6网关地址是网段的1地址
		        	                		}
		        	                		// 分配ip段
		                            		ethList1ipsegmentId[i] = ipSegmentInfo.getIpSegmentId();
		                            		// 分配vlan
		                            		ethList1vlanId[i] = vlanTmp.getVlanId();
		                            		ipNum ++;
	        	                		}
	        	                	}
	        	                	break;
	        	                } else if (ipInfos.size() < Integer.parseInt(num) && ipInfos.size() > 0) { // ip段中的ip个数小于创建vm的个数(但大于0)
	        	                	for (int i = 0; i < ipInfos.size(); i++) {
	        	                		if (ipNum < Integer.parseInt(num)) {
	        	                			// 分配IP
		        	                		ethList1ip[ipNum] = ipInfos.get(i).getIp();
		        	                		if ("0".equals(ipType)) { // IPV4
		        	                			// 分配这个ip的默认网关(默认网关是创建ip段时从资源池请求来的ipSubnet的前半部分)
			        	                		ethList1gateway[ipNum] = ipSegment.getIpSubnet().split("/")[0];
		        	                		} else if ("1".equals(ipType)) { // IPV6
		        	                			// 分配这个ip的默认网关(默认网关是创建ip段时从资源池请求来的ipSubnet的前半部分+1)
			        	                		ethList1gateway[ipNum] = ipSegment.getIpSubnet().split("/")[0] + "1"; // IPV6网关地址是网段的1地址
		        	                		}
		        	                		// 分配ip段
		                            		ethList1ipsegmentId[ipNum] = ipSegmentInfo.getIpSegmentId();
		                            		// 分配vlan
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
			
			// 判断ipsegement、vlan的数量不够创建vm个数时的异常处理
            if (ipNum < Integer.parseInt(num)) {
            	logger.info("当前ip数量是：" + ipNum);
            	logger.error("申请创建虚拟机的个数大于IP的数量！");
            	errMsg = "申请创建虚拟机的个数大于IP的数量，请申请IP段或者vlan！";
                return ConstantEnum.FAILURE.toString();
            }
            // ============ search IpSegmentListByVlan end =================
            
            
            
            logger.debug("开始创建虚拟机");
            printVmInformation();

            caseId1 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId2 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId3 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId4 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());
            caseId5 = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VM.toString());

            // session中获取用户ID
            String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                    .getAttribute(SessionKeys.userInfo.toString())).getUserId();

            RPPVMCreateReq vmReq = new RPPVMCreateReq();
            // 请求号
            vmReq.setSerialNum(sequenceGenerator.generatorVMSerialNum("VMAdd"));

            if (configuration.equals("0")) {
                vmReq.setStandardId(standardId);
                vmReq.setCreateModel(VMCreateModel.TemplateModel);
            } else {
                standardId = "";
                itemId = "";
                vmReq.setCreateModel(VMCreateModel.CustomModel);
            }
            // 根据虚拟机数量拆单
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
             * 接口处理虚拟机名称有误，暂不发送给资源池，只在运营保存 vmReq.setVmName(vmName);
             */
            vmReq.setOsId(osId);
            vmReq.setPmId(pmId);
            Float diSize = Float.parseFloat(discSize) * 1024;
            vmReq.setOsSizeGB(Math.round(diSize));
            vmReq.setResourcePoolId(respoolId);
            vmReq.setResourcePoolPartId(respoolPartId);
            vmReq.setSecurity("1");

            // 网卡信息
            List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();
            // 保存数据库的net List
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

            // 保存网卡信息
            if (vmNetList != null && vmNetList.size() > 0) {
                for (vmNet vmNet : vmNetList) {
                    BatchVO vo = new BatchVO("ADD", "insertVmNet", vmNet);
                    updateBatchVO.add(vo);
                }
            }

            // 拼凑订单信息
            List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
            // 同一个订单父ID
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
                // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
                assembleOrder(userId, orderInfo, orderId, caseId, respoolId, appId);
                orderInfo.setParentId(parentId);// 同一个订单父ID
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
                // 发送申请订单至接口
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
                // 把虚拟机状态添加到虚拟机状态服务中
                for (VMInstanceInfo vmInfo : vmInrstances) {
                    vmStatusService.addVMStatus(vmInfo.getVmId(), vmInfo.getStatus());
                }
            }
            if (AUTO.equals(audit)) {
                logger.debug("创建虚拟机申请发送成功！");
                msg = "创建虚拟机申请发送成功！";
            } else {
                logger.debug("创建虚拟机申请提交成功，请等待审核！");
                msg = "创建虚拟机申请提交成功，请等待审核！";
            }
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            if (AUTO.equals(audit)) {
                errMsg = "创建虚拟机发送申请失败！";
                logger.error("创建虚拟机发送申请异常", e);
            } else {
                errMsg = "创建虚拟机申请提交失败！";
                logger.error("创建虚拟机申请提交异常", e);
            }

            return ConstantEnum.FAILURE.toString();
        }
    }

    // =======================================================
    /*
     * vlan,segment,gateway,ip总共有5套对应的最大5个虚拟机，每套都可以有多个(因为每个虚拟机都可以有多个网卡)
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
		// 查询正在使用的虚拟机，vlan使用的ip和网关（删除的虚拟机ip可以继续使用）
		List<NetInfo> netInfos = ibatisDAO.getData("getAllNet", vlanTmp.getVlanId());
		for (NetInfo netInfo : netInfos) {
		    useIp.put(netInfo.getIp(), netInfo.getIp());
		    useIp.put(netInfo.getGateway(), netInfo.getGateway());
		}
		// 查询正在修改的虚拟机，vlan使用的ip和网关
		List<NetInfo> netInfosForVmMod = ibatisDAO.getData("getAllNetForVmMod", vlanTmp.getVlanId());
		for (NetInfo netInfo : netInfosForVmMod) {
			useIp.put(netInfo.getIp(), netInfo.getIp());
			useIp.put(netInfo.getGateway(), netInfo.getGateway());
		}
		// 查询负载均衡占用的，vlan使用的ip
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
		// 按照ip段ID 查询ip段的起止ip
		IpSegment ipSegment = (IpSegment) ibatisDAO.getSingleRecord("getIPsegmentByID", ipSegmentInfo.getIpSegmentId());
		String startIp = ipSegment.getStartIp();
		String endIp = ipSegment.getEndIp();
		String preGateWayIp = "";
		String gateWayIp = "";
		if ("0".equals(ipType)) { // IPV4
			gateWayIp = ipSegment.getIpSubnet().split("/")[0];
		} else if ("1".equals(ipType)) { // IPV6
			preGateWayIp = ipSegment.getIpSubnet().split("/")[0]; // 网关前缀
			gateWayIp = preGateWayIp  + "1";
		}
		List<String> ips = new ArrayList<String>();
		if ("0".equals(ipType)) { // IPV4
			ips = IpSegmentUtil.getIpList(startIp, endIp);
		} else if ("1".equals(ipType)) { // IPV6
			ips = Ipv6Util.getIpv6List(powerNum, preGateWayIp, startIp, endIp);
		}

		// 通过起止ip查询出该ip段内包含的所有ip
		
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
			// 如果没有绑定过VLAN，那么根据当前虚拟机所属业务，查询出该业务下所有可用IP段(未绑定VLAN)
			if (null == vlan1.getIpSegmentId() || ("").equals(vlan1.getIpSegmentId())) {
				ipSegment.setAppId(appId);
				ipSegment.setIpType(ipType);
				ipSegmentList = ibatisDAO.getData("getUnusedIpSegmentList", ipSegment);
				// 页面显示IP段格式为 起始IP ~ 结束IP
				for (IpSegmentInfo temp : ipSegmentList) {
					temp.setIpSegment(temp.getStartIp() + " ~ " + temp.getEndIp());
				}
			} else {// 如果当前VLAN有绑定的IP段，那么直接查询出该IP段
				// 资源池和分区
				ipSegment.setResPoolId(vlan1.getResPoolId());
				ipSegment.setResPoolPartId(vlan1.getResPoolPartId());
				// 页面显示IP段格式为 起始IP ~ 结束IP
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
		    // 当前虚拟机所属业务对应的vlan列表
			logger.info("*************开始查询vlan");
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
            // 返回成功，虚拟机订单和实例入库

            // 拼凑实例信息
            try {

                // 根据虚拟机数量拆单
                for (int i = 1; i <= intNum; i++) {
                    // if (AUTO.equals(audit)) {
                    // orderInfos.get(i - 1).setEffectiveTime(TIMESTAMP.print(new DateTime()));
                    // }
                    // 更新数据库
                    updateBatchVO.add(new BatchVO(addTip, "createVMOrder", orderInfos.get(i - 1)));

                    // 拼凑VM实例信息
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

                    // 更新数据库
                    updateBatchVO.add(new BatchVO(addTip, "createVMInstanceInfo", vmInrstances
                            .get(i - 1)));

                    if (logger.isDebugEnable()) {
                        logger.debug("拼凑订单信息，第" + i + "条");
                    }
                }
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("申请虚拟机订单记入数据库成功！");
                }
            } catch (SQLException e2) {
                // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交申请虚拟机订单失败！”。
                logger.error(VMStatusCode.CREATEVMORDER_EXCEPTION_CODE,
                        getText("vm.applyinfo.fail"), e2);
                this.addActionError(getText("vm.applyinfo.fail"));
                errMsg = getText("vm.applyinfo.fail");
                return false;
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机发送申请失败！");
            this.addActionError(getText("vm.applyinfo.sendcreateVM.fail"));
            errMsg = getText("vm.applyinfo.sendcreateVM.fail");
            // 入失败库
            List<VMCreateFail> vmCreateFails = assembleVMCreateFail(vmReq, resp);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (VMCreateFail vmCreateFail : vmCreateFails) {
                insertFailBatchVO.add(new BatchVO(addTip, "insertVMFail", vmCreateFail));
            }
            try {
                ibatisDAO.updateBatchData(insertFailBatchVO);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机失败后入失败库发生数据库异常",
                        e);
            } catch (Exception e2) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机失败后入失败库发生数据库异常",
                        e2);
            }
            return false;
        }
        return true;
    }

    /**
     * convertVmNet 转换网卡信息
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
                } else if ("1".equals(ipType)) { // IPV6无掩码概念，但需要将网段66aa:55dd:d:2ef::/96中的96存到掩码字段中
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
     * assembleOrder 拼装订单信息
     * @param userId 用户ID
     * @param orderInfo 订单信息
     * @param orderId 订单ID
     * @param caseId 实例ID
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
     * assembleVMCreateFail 拼装虚拟机创建失败信息
     * @param vmReq 虚拟机请求
     * @param resp 应答
     * @return 失败消息List
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
     * assembleVMInstance 拼装虚拟机实例
     * @param userId 用户ID
     * @param orderInfo 订单信息
     * @param vmInfo 虚拟机信息
     * @param vmInstanceInfo 实例信息
     * @param tempOsInfo OS信息
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
     * assembleVMInstanceAudit 拼装虚拟机实例(人工审批用)
     * @param userId 用户ID
     * @param orderInfo 订单信息
     * @param vmInstanceInfo 实例信息
     * @param tempOsInfo 虚拟机信息
     * @param subNetwork 子网地址
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
            logger.debug("是否人工审批 0：自动 1：人工" + audit);
            logger.debug("创建模式 0：模板创建 1：自定义:" + configuration);
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
     * 获取itemId字段数据
     * @return Returns the itemId.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置itemId字段数据
     * @param itemId The itemId to set.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取osId字段数据
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 设置osId字段数据
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 获取num字段数据
     * @return Returns the num.
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置num字段数据
     * @param num The num to set.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取lengthTime字段数据
     * @return Returns the lengthTime.
     */
    public String getLengthTime() {
        return lengthTime;
    }

    /**
     * 设置lengthTime字段数据
     * @param lengthTime The lengthTime to set.
     */
    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    /**
     * 获取vmRemark字段数据
     * @return Returns the vmRemark.
     */
    public String getVmRemark() {
        return vmRemark;
    }

    /**
     * 设置vmRemark字段数据
     * @param vmRemark The vmRemark to set.
     */
    public void setVmRemark(String vmRemark) {
        this.vmRemark = vmRemark;
    }

    /**
     * 获取respoolId字段数据
     * @return Returns the respoolId.
     */
    public String getRespoolId() {
        return respoolId;
    }

    /**
     * 设置respoolId字段数据
     * @param respoolId The respoolId to set.
     */
    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    /**
     * 获取respoolPartId字段数据
     * @return Returns the respoolPartId.
     */
    public String getRespoolPartId() {
        return respoolPartId;
    }

    /**
     * 设置respoolPartId字段数据
     * @param respoolPartId The respoolPartId to set.
     */
    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
    }

    /**
     * 获取respoolName字段数据
     * @return Returns the respoolName.
     */
    public String getRespoolName() {
        return respoolName;
    }

    /**
     * 设置respoolName字段数据
     * @param respoolName The respoolName to set.
     */
    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    /**
     * 获取respoolPartName字段数据
     * @return Returns the respoolPartName.
     */
    public String getRespoolPartName() {
        return respoolPartName;
    }

    /**
     * 设置respoolPartName字段数据
     * @param respoolPartName The respoolPartName to set.
     */
    public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    /**
     * 获取cpuNum字段数据
     * @return Returns the cpuNum.
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 获取ramSize字段数据
     * @return Returns the ramSize.
     */
    public String getRamSize() {
        return ramSize;
    }

    /**
     * 设置ramSize字段数据
     * @param ramSize The ramSize to set.
     */
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    /**
     * 获取discSize字段数据
     * @return Returns the discSize.
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * 设置discSize字段数据
     * @param discSize The discSize to set.
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    /**
     * 获取type字段数据
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type字段数据
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取ethList1vlanId字段数据
     * @return Returns the ethList1vlanId.
     */
    public String[] getEthList1vlanId() {
        return ethList1vlanId;
    }

    /**
     * 设置ethList1vlanId字段数据
     * @param ethList1vlanId The ethList1vlanId to set.
     */
    public void setEthList1vlanId(String[] ethList1vlanId) {
        this.ethList1vlanId = ethList1vlanId;
    }

    /**
     * 获取ethList2vlanId字段数据
     * @return Returns the ethList2vlanId.
     */
    public String[] getEthList2vlanId() {
        return ethList2vlanId;
    }

    /**
     * 设置ethList2vlanId字段数据
     * @param ethList2vlanId The ethList2vlanId to set.
     */
    public void setEthList2vlanId(String[] ethList2vlanId) {
        this.ethList2vlanId = ethList2vlanId;
    }

    /**
     * 获取ethList3vlanId字段数据
     * @return Returns the ethList3vlanId.
     */
    public String[] getEthList3vlanId() {
        return ethList3vlanId;
    }

    /**
     * 设置ethList3vlanId字段数据
     * @param ethList3vlanId The ethList3vlanId to set.
     */
    public void setEthList3vlanId(String[] ethList3vlanId) {
        this.ethList3vlanId = ethList3vlanId;
    }

    /**
     * 获取ethList4vlanId字段数据
     * @return Returns the ethList4vlanId.
     */
    public String[] getEthList4vlanId() {
        return ethList4vlanId;
    }

    /**
     * 设置ethList4vlanId字段数据
     * @param ethList4vlanId The ethList4vlanId to set.
     */
    public void setEthList4vlanId(String[] ethList4vlanId) {
        this.ethList4vlanId = ethList4vlanId;
    }

    /**
     * 获取ethList5vlanId字段数据
     * @return Returns the ethList5vlanId.
     */
    public String[] getEthList5vlanId() {
        return ethList5vlanId;
    }

    /**
     * 设置ethList5vlanId字段数据
     * @param ethList5vlanId The ethList5vlanId to set.
     */
    public void setEthList5vlanId(String[] ethList5vlanId) {
        this.ethList5vlanId = ethList5vlanId;
    }

    /**
     * 获取ethList1ipsegmentId字段数据
     * @return Returns the ethList1ipsegmentId.
     */
    public String[] getEthList1ipsegmentId() {
        return ethList1ipsegmentId;
    }

    /**
     * 设置ethList1ipsegmentId字段数据
     * @param ethList1ipsegmentId The ethList1ipsegmentId to set.
     */
    public void setEthList1ipsegmentId(String[] ethList1ipsegmentId) {
        this.ethList1ipsegmentId = ethList1ipsegmentId;
    }

    /**
     * 获取ethList2ipsegmentId字段数据
     * @return Returns the ethList2ipsegmentId.
     */
    public String[] getEthList2ipsegmentId() {
        return ethList2ipsegmentId;
    }

    /**
     * 设置ethList2ipsegmentId字段数据
     * @param ethList2ipsegmentId The ethList2ipsegmentId to set.
     */
    public void setEthList2ipsegmentId(String[] ethList2ipsegmentId) {
        this.ethList2ipsegmentId = ethList2ipsegmentId;
    }

    /**
     * 获取ethList3ipsegmentId字段数据
     * @return Returns the ethList3ipsegmentId.
     */
    public String[] getEthList3ipsegmentId() {
        return ethList3ipsegmentId;
    }

    /**
     * 设置ethList3ipsegmentId字段数据
     * @param ethList3ipsegmentId The ethList3ipsegmentId to set.
     */
    public void setEthList3ipsegmentId(String[] ethList3ipsegmentId) {
        this.ethList3ipsegmentId = ethList3ipsegmentId;
    }

    /**
     * 获取ethList4ipsegmentId字段数据
     * @return Returns the ethList4ipsegmentId.
     */
    public String[] getEthList4ipsegmentId() {
        return ethList4ipsegmentId;
    }

    /**
     * 设置ethList4ipsegmentId字段数据
     * @param ethList4ipsegmentId The ethList4ipsegmentId to set.
     */
    public void setEthList4ipsegmentId(String[] ethList4ipsegmentId) {
        this.ethList4ipsegmentId = ethList4ipsegmentId;
    }

    /**
     * 获取ethList5ipsegmentId字段数据
     * @return Returns the ethList5ipsegmentId.
     */
    public String[] getEthList5ipsegmentId() {
        return ethList5ipsegmentId;
    }

    /**
     * 设置ethList5ipsegmentId字段数据
     * @param ethList5ipsegmentId The ethList5ipsegmentId to set.
     */
    public void setEthList5ipsegmentId(String[] ethList5ipsegmentId) {
        this.ethList5ipsegmentId = ethList5ipsegmentId;
    }

    /**
     * 获取ethList1ip字段数据
     * @return Returns the ethList1ip.
     */
    public String[] getEthList1ip() {
        return ethList1ip;
    }

    /**
     * 设置ethList1ip字段数据
     * @param ethList1ip The ethList1ip to set.
     */
    public void setEthList1ip(String[] ethList1ip) {
        this.ethList1ip = ethList1ip;
    }

    /**
     * 获取ethList2ip字段数据
     * @return Returns the ethList2ip.
     */
    public String[] getEthList2ip() {
        return ethList2ip;
    }

    /**
     * 设置ethList2ip字段数据
     * @param ethList2ip The ethList2ip to set.
     */
    public void setEthList2ip(String[] ethList2ip) {
        this.ethList2ip = ethList2ip;
    }

    /**
     * 获取ethList3ip字段数据
     * @return Returns the ethList3ip.
     */
    public String[] getEthList3ip() {
        return ethList3ip;
    }

    /**
     * 设置ethList3ip字段数据
     * @param ethList3ip The ethList3ip to set.
     */
    public void setEthList3ip(String[] ethList3ip) {
        this.ethList3ip = ethList3ip;
    }

    /**
     * 获取ethList4ip字段数据
     * @return Returns the ethList4ip.
     */
    public String[] getEthList4ip() {
        return ethList4ip;
    }

    /**
     * 设置ethList4ip字段数据
     * @param ethList4ip The ethList4ip to set.
     */
    public void setEthList4ip(String[] ethList4ip) {
        this.ethList4ip = ethList4ip;
    }

    /**
     * 获取ethList5ip字段数据
     * @return Returns the ethList5ip.
     */
    public String[] getEthList5ip() {
        return ethList5ip;
    }

    /**
     * 设置ethList5ip字段数据
     * @param ethList5ip The ethList5ip to set.
     */
    public void setEthList5ip(String[] ethList5ip) {
        this.ethList5ip = ethList5ip;
    }

    /**
     * 获取ethList1gateway字段数据
     * @return Returns the ethList1gateway.
     */
    public String[] getEthList1gateway() {
        return ethList1gateway;
    }

    /**
     * 设置ethList1gateway字段数据
     * @param ethList1gateway The ethList1gateway to set.
     */
    public void setEthList1gateway(String[] ethList1gateway) {
        this.ethList1gateway = ethList1gateway;
    }

    /**
     * 获取ethList2gateway字段数据
     * @return Returns the ethList2gateway.
     */
    public String[] getEthList2gateway() {
        return ethList2gateway;
    }

    /**
     * 设置ethList2gateway字段数据
     * @param ethList2gateway The ethList2gateway to set.
     */
    public void setEthList2gateway(String[] ethList2gateway) {
        this.ethList2gateway = ethList2gateway;
    }

    /**
     * 获取ethList3gateway字段数据
     * @return Returns the ethList3gateway.
     */
    public String[] getEthList3gateway() {
        return ethList3gateway;
    }

    /**
     * 设置ethList3gateway字段数据
     * @param ethList3gateway The ethList3gateway to set.
     */
    public void setEthList3gateway(String[] ethList3gateway) {
        this.ethList3gateway = ethList3gateway;
    }

    /**
     * 获取ethList4gateway字段数据
     * @return Returns the ethList4gateway.
     */
    public String[] getEthList4gateway() {
        return ethList4gateway;
    }

    /**
     * 设置ethList4gateway字段数据
     * @param ethList4gateway The ethList4gateway to set.
     */
    public void setEthList4gateway(String[] ethList4gateway) {
        this.ethList4gateway = ethList4gateway;
    }

    /**
     * 获取ethList5gateway字段数据
     * @return Returns the ethList5gateway.
     */
    public String[] getEthList5gateway() {
        return ethList5gateway;
    }

    /**
     * 设置ethList5gateway字段数据
     * @param ethList5gateway The ethList5gateway to set.
     */
    public void setEthList5gateway(String[] ethList5gateway) {
        this.ethList5gateway = ethList5gateway;
    }

    /**
     * 获取vmStatusService字段数据
     * @return Returns the vmStatusService.
     */
    public VMStatusService getVmStatusService() {
        return vmStatusService;
    }

    /**
     * 设置vmStatusService字段数据
     * @param vmStatusService The vmStatusService to set.
     */
    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

    /**
     * 获取sequenceGenerator字段数据
     * @return Returns the sequenceGenerator.
     */
    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    /**
     * 设置sequenceGenerator字段数据
     * @param sequenceGenerator The sequenceGenerator to set.
     */
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * 获取vmCreate字段数据
     * @return Returns the vmCreate.
     */
    public VMCreate getVmCreate() {
        return vmCreate;
    }

    /**
     * 设置vmCreate字段数据
     * @param vmCreate The vmCreate to set.
     */
    public void setVmCreate(VMCreate vmCreate) {
        this.vmCreate = vmCreate;
    }

    /**
     * 获取audit字段数据
     * @return Returns the audit.
     */
    public String getAudit() {
        return audit;
    }

    /**
     * 设置audit字段数据
     * @param audit The audit to set.
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    /**
     * 获取acceptTimeTip字段数据
     * @return Returns the acceptTimeTip.
     */
    public String getAcceptTimeTip() {
        return acceptTimeTip;
    }

    /**
     * 设置acceptTimeTip字段数据
     * @param acceptTimeTip The acceptTimeTip to set.
     */
    public void setAcceptTimeTip(String acceptTimeTip) {
        this.acceptTimeTip = acceptTimeTip;
    }

    /**
     * 获取addTip字段数据
     * @return Returns the addTip.
     */
    public String getAddTip() {
        return addTip;
    }

    /**
     * 设置addTip字段数据
     * @param addTip The addTip to set.
     */
    public void setAddTip(String addTip) {
        this.addTip = addTip;
    }

    /**
     * 获取serialversionuid字段数据
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 获取succeessCode字段数据
     * @return Returns the succeessCode.
     */
    public static String getSucceessCode() {
        return SUCCEESS_CODE;
    }

    /**
     * 获取timestamp字段数据
     * @return Returns the timestamp.
     */
    public static DateTimeFormatter getTimestamp() {
        return TIMESTAMP;
    }

    /**
     * 获取auto字段数据
     * @return Returns the auto.
     */
    public static String getAuto() {
        return AUTO;
    }

    /**
     * 获取configuration字段数据
     * @return Returns the configuration.
     */
    public String getConfiguration() {
        return configuration;
    }

    /**
     * 设置configuration字段数据
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
