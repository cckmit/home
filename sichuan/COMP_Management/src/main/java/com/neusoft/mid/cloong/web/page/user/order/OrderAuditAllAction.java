package com.neusoft.mid.cloong.web.page.user.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.H3C.vlan.H3cVlanResp;
import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.ebs.core.EBSCreate;
import com.neusoft.mid.cloong.ebs.core.EBSModify;
import com.neusoft.mid.cloong.ebs.core.EBSStatus;
import com.neusoft.mid.cloong.filestorage.FileStorageExecuter;
import com.neusoft.mid.cloong.filestorage.bean.FSCreateFail;
import com.neusoft.mid.cloong.filestorage.bean.FileStorageBean;
import com.neusoft.mid.cloong.httpclient.support.H3CHttpClientService;
import com.neusoft.mid.cloong.identity.bean.ChargesInfo;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.ipSegment.core.ApplyIpSegment;
import com.neusoft.mid.cloong.lb.core.LBCreate;
import com.neusoft.mid.cloong.pm.core.PMCreate;
import com.neusoft.mid.cloong.pm.core.PMModify;
import com.neusoft.mid.cloong.pm.core.PMModifyReq;
import com.neusoft.mid.cloong.pm.core.PMModifyResp;
import com.neusoft.mid.cloong.pm.core.PMStatus;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.BsParam;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPProType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.LBDemand;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMEthPurpose;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp.PMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp.VMInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakMode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMEthPurpose;
import com.neusoft.mid.cloong.virfw.CreateVirFW;
import com.neusoft.mid.cloong.virfw.bean.VFWCreateFail;
import com.neusoft.mid.cloong.vlan.core.VLANCreate;
import com.neusoft.mid.cloong.vm.core.VMCreate;
import com.neusoft.mid.cloong.vm.core.VMModify;
import com.neusoft.mid.cloong.vm.core.VMModifyReq;
import com.neusoft.mid.cloong.vm.core.VMModifyResp;
import com.neusoft.mid.cloong.vm.core.VMStatus;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreate;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.user.order.lb.LoadBalanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 订单审批
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-3-11 上午09:35:45
 */
@SuppressWarnings("unchecked")
public class OrderAuditAllAction extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4196853216651241774L;

	/**
	 * 日志
	 */
	private static LogService logger = LogService.getLogger(OrderAuditAllAction.class);

	/**
	 * 返回路径，用于在界面判断是否系统错误
	 */
	private String resultPath = ConstantEnum.SUCCESS.toString();

	/**
	 * 成功的接口响应码
	 */
	private static final String SUCCEESS_CODE = "00000000";

	/**
	 * 申请资源是否成功 默认成功
	 */
	private String isSuccess = SUCCEESS_CODE;

	/**
	 * 是否审批通过 默认通过
	 */
	private static final String auditPass = "1";

	/**
	 * 失败消息
	 */
	private String message = "订单审批失败  ";

	/**
	 * 流水号生成器
	 */
	private CommonSequenceGenerator seqCen;

	private LBCreate lbCreate;

	/**
	 * 虚拟机创建接口
	 */
	private VMCreate vmCreate;

	/**
	 * 虚拟机修改接口
	 */
	private VMModify vmModify;

	/**
	 * 虚拟机创建接口
	 */
	private PMCreate pmCreate;

	/**
	 * 虚拟机修改接口
	 */
	private PMModify pmModify;

	/**
	 * 虚拟备份任务创建接口
	 */
	private VmBakCreate vmBakCreate;

	/**
	 * 虚拟硬盘创建接口
	 */
	private EBSCreate ebsCreate;

	/**
	 * 虚拟硬盘创建接口
	 */
	private EBSModify ebsModify;
	/**
	 * IP段创建接口
	 */
	private ApplyIpSegment ipCreate;
	/**
	 * VLAN创建接口
	 */
	private VLANCreate vlanCreate;

	/**
	 * 从配置文件导入的默认值： 存储性能级别
	 */
	private Integer tier;

	/**
	 * 从配置文件导入的默认值：RAID级别
	 */
	private Integer raid;

	/**
	 * 从配置文件导入的默认值：选用的存储网络类型
	 */
	private Integer storageNet;

	/**
	 * 从配置文件导入的默认值：资源类型
	 */
	private Integer resourceType;

	/**
	 * 从配置文件导入的默认值：是否使用动态分级
	 */
	private Integer tierOpen;

	/**
	 * 订单实体
	 */
	private OrderInfo orderInfo;

	/**
	 * 订单审批记录表实体
	 */
	private OrderAuditInfo orderAuditInfo;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 父订单ID
	 */
	private String parentId;

	/**
	 * 资源类型
	 */
	private String caseType;

	/**
	 * 当前状态
	 */
	private String status;

	/**
	 * 是否通过审批 1：通过 2：不通过
	 */
	private String pass;

	/**
	 * 审批意见
	 */
	private String auditInfo;

	/**
	 * 登陆用户ID
	 */
	private String userId;

	private List<BatchVO> batchVOs;

	/**
	 * pmId
	 */
	private String pmId;

	private String vlanSelect;

	private String ipsegmentSelect;

	private String privateIpSelect;
	
	private String flag ="yes";

	/* add by huxin */
	private FileStorageExecuter fileStorage;
	
	private CreateVirFW virfwClient;
	
	 /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    
    private H3CHttpClientService h3CHttpClientService;

	// private BillingChargesService billingService;
	/**
	 * execute 执行人工审批
	 * 
	 * @return 人工审批是否成功
	 */
	public String execute() {
		batchVOs = new ArrayList<BatchVO>();
		// session中获取用户ID
		userId = ((UserBean) ServletActionContext.getRequest().getSession()
				.getAttribute(SessionKeys.userInfo.toString())).getUserId();
		// 对通过的订单发送创建资源请求，对不通过的订单删除已经存在的实例信息
		String message = "";
		if (auditPass.equals(pass)) {
			if ("0".equals(caseType)) {
				if ("0".equals(status)) {
					message = sentVmCreate(userId);
				} else if ("7".equals(status)) {
					message = sentVmModify();
				}
			} else if ("1".equals(caseType)) {
				if ("0".equals(status)) {
					message = sentPmCreate();
				} else if ("7".equals(status)) {
					message = sentPmModify();
				}
			} else if ("4".equals(caseType)) {
				message = sentBkCreate();
			} else if ("5".equals(caseType)) {
				if ("0".equals(status)) {
					message = sentEbsCreate(userId);
				} else if ("7".equals(status)) {
					message = sentEbsModify();
				}
			} else if ("12".equals(caseType)) {
				message = sentPipCreate(userId);
			} else if ("13".equals(caseType)) {
				message = sentVlanCreate(userId);
			} else if ("14".equals(caseType)) {
				message = sentLbCreate(userId);
			} else if ("15".equals(caseType)) {
				message = sentFileStorageCreate(userId);
			} else if ("16".equals(caseType)) {
				message = sentVirFwCreate();
			}else if ("7".equals(caseType)) {
                message = sentPublicIpCreate(userId);
            } else if ("17".equals(caseType)) {
            	if ("0".equals(status)) {
            		message = sentVlan4SDNCreate(userId);
            	} else if ("7".equals(status)) {
            		message = sentVlan4SDNUpdate(userId);
            	} else if ("8".equals(status)) {
            		message = sentVlan4SDNDel(userId);
            	}
            }
			
			if (!ConstantEnum.SUCCESS.toString().equals(message)) {
				return ConstantEnum.ERROR.toString();
			}
		} else {
			message = delInstanceInfo();
			if (!ConstantEnum.SUCCESS.toString().equals(message)) {
				return ConstantEnum.ERROR.toString();
			}
		}
		logger.info("message++++++++++++++++" + message);
		logger.info("isSuccess++++++++++++++++" + isSuccess);

		// 如果消息发送不成功，则提示用户，直接返回。
		if (!SUCCEESS_CODE.endsWith(isSuccess)) {
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

		// 拼凑订单信息
		orderInfo = new OrderInfo();
		orderInfo.setOrderId(orderId);
		if ("1".equals(pass) && ("4".equals(caseType) || "12".equals(caseType) || "13".equals(caseType))) {
			// 同步接口直接更新生效状态及生效时间
			orderInfo.setStatus("3");
			orderInfo.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		} else if ("7".equals(status)) {
			// 修改待审和释放待审，将订单置为已生效，让之前的配置能正常使用
			orderInfo.setStatus("3");
		}else if("8".equals(status)){
			if("yes".equals(flag)){
				orderInfo.setStatus("6");
			}else{
				orderInfo.setStatus("3");
			}
		}else {
			orderInfo.setStatus(pass);
		}

		orderAuditInfo = new OrderAuditInfo();
		orderAuditInfo.setAuditUser(userId);
		orderAuditInfo.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		orderAuditInfo.setOrderId(orderId);
		orderAuditInfo.setStatus(pass);
		orderAuditInfo.setAuditInfo(auditInfo);
		BatchVO batchVOorderInfo = new BatchVO("MOD", "updateOrderInfo", orderInfo);
		BatchVO batchVOorderAuditInfo = new BatchVO("ADD", "updateOrderAuditInfo", orderAuditInfo);
		// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
		batchVOs.add(batchVOorderInfo);
		batchVOs.add(batchVOorderAuditInfo);
		try {
			ibatisDAO.updateBatchData(batchVOs);
			logger.info("更新数据库。。。。。。。。。。");

			// if(caseType.equals("0") || caseType.equals("5")){
			// billingService.synchBillingToSFTP();
			// }
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "审批时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "审批时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();

	}

	/**
	 * 申请分布式文件存储
	 */
	private String sentFileStorageCreate(String userId) {
		logger.info("申请分布式文件存储开始");
		try {
			FileStorageBean fsbean = (FileStorageBean) ibatisDAO.getSingleRecord("getFsBeanByOrderId", orderId);
			RPPFileStorageCreateReq fsreq = getFileStorageBean(fsbean);
			BatchVO batchFSorderInfo = null;
			RPPFileStorageCreateResponse createFileStorage = fileStorage.createFileStorage(fsreq);
			if (null == createFileStorage.getFaultString()) {
				String fileStorageID = createFileStorage.getFileStorageID();
				String fsUrl = createFileStorage.getFSUrl();
				logger.info("fileStorageID:="+fileStorageID+",fsUrl:="+fsUrl);
				HashMap<String, String> fsmap = new HashMap<String, String>();
				fsmap.put("orderId", orderId);
				fsmap.put("fileStorageID", fileStorageID);
				fsmap.put("fsUrl", fsUrl);
				fsmap.put("status", "1");
				fsmap.put("updateTime", TIMESTAMP.print(new DateTime()));
				batchFSorderInfo = new BatchVO("MOD", "updateFsInfoByOrderId", fsmap);
				batchVOs.add(batchFSorderInfo);
				logger.info("申请分布式文件存储结束! createUserName: " + userId);
				return ConstantEnum.SUCCESS.toString();
			} else {
				HashMap<String, String> fsmap = new HashMap<String, String>();
				fsmap.put("orderId", orderId);
				fsmap.put("fileStorageID", null);
				fsmap.put("fsUrl", null);
				fsmap.put("status", "2");
				fsmap.put("updateTime", TIMESTAMP.print(new DateTime()));
				batchFSorderInfo = new BatchVO("MOD", "updateFsInfoByOrderId", fsmap);
				batchVOs.add(batchFSorderInfo);
				
			       FSCreateFail fsCreateFail = new FSCreateFail();
			        fsCreateFail.setFailCause(createFileStorage.getFaultString());
			        fsCreateFail.setFailCode(createFileStorage.getResultCode());
			        fsCreateFail.setResPoolId(fsreq.getResourcePoolId());
			        fsCreateFail.setResPoolPartId(fsreq.getResourcePoolPartId());
			        fsCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
			        fsCreateFail.setFsName(fsreq.getFileStorageName());
			        fsCreateFail.setCreateUser(userId);
			        fsCreateFail.setNum("1");
			        fsCreateFail.setAppId(fsreq.getAppID());
			        
			        BatchVO batchFSCreateFail = new BatchVO("MOD", "insertFSCreateFail", fsmap);
	                batchVOs.add(batchFSCreateFail);
				throw new Exception(createFileStorage.getFaultString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			e.printStackTrace();
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
	}

	/**
	 * 申请虚拟防火墙 
	 */
	private String sentVirFwCreate() {
		try {
			logger.info("申请虚拟防火墙审批开始");
			com.neusoft.mid.cloong.rpproxy.interfaces.virfw.CreateVirFwBean virfw = (com.neusoft.mid.cloong.rpproxy.interfaces.virfw.CreateVirFwBean) ibatisDAO.getSingleRecord("getVirFwInfoByOrderId", orderId);
			RPPApplyVirfwOperationRequst virFWReq = getVirFWReq(virfw);
			BatchVO batchVirFworderInfo =null;
			RPPApplyVirfwOperationResponse createVirFw = virfwClient.createVirFw(virFWReq);
			String resultCode = createVirFw.getResultCode();
			logger.info("resultCode:="+resultCode);
			Map<String, String> virfwmap = null;
			if ("0".equals(resultCode)) {
				virfwmap = new HashMap<String, String>();
				String virfwid = createVirFw.getResultMessage();
				logger.info("virfwid:="+virfwid);
				virfwmap.put("virfwid", virfwid);
				virfwmap.put("orderId", orderId);
				virfwmap.put("status", "1");
				virfwmap.put("updateTime", TIMESTAMP.print(new DateTime()));
				batchVirFworderInfo = new BatchVO("MOD", "updateVirFwInfoByOrderId", virfwmap);
				batchVOs.add(batchVirFworderInfo);
				logger.info("申请虚拟防火墙审批结束");
				return ConstantEnum.SUCCESS.toString();
			} else {
				virfwmap = new HashMap<String, String>();
				virfwmap.put("virfwid", null);
				virfwmap.put("orderId", orderId);
				virfwmap.put("status", "2");
				virfwmap.put("updateTime", TIMESTAMP.print(new DateTime()));
				batchVirFworderInfo = new BatchVO("MOD", "updateVirFwInfoByOrderId", virfwmap);
				batchVOs.add(batchVirFworderInfo);
				VFWCreateFail vfwCreateFail = new VFWCreateFail();
		        vfwCreateFail.setFailCause(createVirFw.getResultMessage());
		        vfwCreateFail.setFailCode(resultCode);
		        vfwCreateFail.setResPoolId(virFWReq.getResourcePoolId());
		        vfwCreateFail.setResPoolPartId(virFWReq.getResourcePoolPartId());
		        vfwCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
		        vfwCreateFail.setAppId(virFWReq.getAppID());
		        vfwCreateFail.setFwName(virFWReq.getFwName());
		        vfwCreateFail.setNum("1");
		        vfwCreateFail.setCreateUser(userId);
		        BatchVO batchVirFwCreateFail =new BatchVO("ADD", "insertVFWCreateFail", vfwCreateFail);
		        batchVOs.add(batchVirFwCreateFail);
				throw new Exception(createVirFw.getResultMessage());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			e.printStackTrace();
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		
	}
	
	   /**
     * 申请公网IP
     */
    private String sentPublicIpCreate(String userId) {
        logger.info("申请公网IP审批开始");
        BatchVO batchPipOrderInfo =null;
        HashMap<String, String> pIpmap = new HashMap<String, String>();
        pIpmap.put("orderId", orderId);
        pIpmap.put("status", "1");
        pIpmap.put("updateTime", TIMESTAMP.print(new DateTime()));
        batchPipOrderInfo = new BatchVO("MOD", "updatePipInfoByOrderId", pIpmap);
        batchVOs.add(batchPipOrderInfo); 
        logger.info("申请公网IP成功！createUserName: " + userId);
        return ConstantEnum.SUCCESS.toString();
    }
    

	/**
	 * @param virfw
	 */
	private RPPApplyVirfwOperationRequst getVirFWReq(com.neusoft.mid.cloong.rpproxy.interfaces.virfw.CreateVirFwBean virfw) {
		logger.info("virfwAppid:="+virfw.getAppId()+",virfwAppName:="+virfw.getAppName()+",virfwFwName:="+virfw.getFwName());
		RPPApplyVirfwOperationRequst virfwreq = new RPPApplyVirfwOperationRequst();
		virfwreq.setAppID(virfw.getAppId());
		virfwreq.setAppName(virfw.getAppName());
		virfwreq.setFwName(virfw.getFwName());
		virfwreq.setResourcePoolId(virfw.getResPoolId());
		virfwreq.setResourcePoolPartId(virfw.getResPoolPartId());
		return virfwreq;
	}

	/**
	 * @param fsbean
	 */
	private RPPFileStorageCreateReq getFileStorageBean(FileStorageBean fsbean) {
		logger.info("appid:="+fsbean.getAppId()+",appName:="+fsbean.getAppName()+",fsbean.getFsName():="+fsbean.getFsName());
		RPPFileStorageCreateReq req = new RPPFileStorageCreateReq();
		req.setAppID(fsbean.getAppId());
		req.setAppName(fsbean.getAppName());
		req.setFileStorageName(fsbean.getFsName());
		req.setPassword(fsbean.getFsAdminPassword());
		req.setResourcePoolId(fsbean.getResPoolId());
		req.setResourcePoolPartId(fsbean.getResPoolPartId());
		req.setQuotaSize(Integer.parseInt(fsbean.getFsQuotasize()));
		req.setSharetype(Integer.parseInt(fsbean.getFsShareType()));
		return req;
	}

	/**
	 * 对不通过的订单删除已经存在的实例信息
	 * 
	 * @return 是否成功
	 */
	private String delInstanceInfo() {
		try {
			if ("0".equals(status)) {// 审批不通过时，需要将资源数据置位成无效
				if ("0".equals(caseType)) {
					// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
					BatchVO delInfo = new BatchVO("DEL", "delVMInstanceInfo", orderId);
					batchVOs.add(delInfo);
					VMInstanceInfo vmInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmInstanceInfo", orderId);
					List<NetInfo> netInfos = ibatisDAO.getData("getNetByCaseId", vmInfo.getCaseId());
					for (NetInfo net : netInfos) {
						int bindCount = ibatisDAO.getCount("getVlanIPBind", net);// 查询待生效的绑定关系
						if (bindCount > 0) {// 审批不通过时将绑定关系状态置位成失效
							BatchVO batchInfo = new BatchVO("MOD", "delVlanIPBind", net);
							batchVOs.add(batchInfo);
						}
					}
					// ibatisDAO.updateBatchData(batchVOs);
				} else if ("1".equals(caseType)) {
					// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
					BatchVO delInfo = new BatchVO("DEL", "delPMInstanceInfo", orderId);
					batchVOs.add(delInfo);
					PMInstanceInfo pmInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord("queryPmInstanceInfo", orderId);
					List<NetInfo> netInfos = ibatisDAO.getData("getNetByCaseIdPm", pmInfo.getCaseId());
					for (NetInfo net : netInfos) {
						int bindCount = ibatisDAO.getCount("getVlanIPBind", net);// 查询待生效的绑定关系
						if (bindCount > 0) {// 审批不通过时将绑定关系状态置位成失效
							BatchVO batchInfo = new BatchVO("MOD", "delVlanIPBind", net);
							batchVOs.add(batchInfo);
						}
					}
					// ibatisDAO.updateBatchData(batchVOs);
				} else if ("4".equals(caseType)) {
					// ibatisDAO.deleteData("delVMBAKInstanceInfo", orderId);
					BatchVO batchDelVmBak = new BatchVO("DEL", "delVMBAKInstanceInfo", orderId);
					batchVOs.add(batchDelVmBak);
				} else if ("5".equals(caseType)) {
					// ibatisDAO.deleteData("delEBSInstanceInfo", orderId);
					BatchVO batchDelEbs = new BatchVO("DEL", "delEBSInstanceInfo", orderId);
					batchVOs.add(batchDelEbs);
				} else if ("12".equals(caseType)) {
					// ibatisDAO.deleteData("delPIPInstanceInfo", orderId);
					BatchVO batchDelPip = new BatchVO("DEL", "delPIPInstanceInfo", orderId);
					batchVOs.add(batchDelPip);
				} else if ("13".equals(caseType)) {
					// ibatisDAO.deleteData("delVLANInstanceInfo", orderId);
					BatchVO batchDelVlan = new BatchVO("DEL", "delVLANInstanceInfo", orderId);
					batchVOs.add(batchDelVlan);
				} else if ("14".equals(caseType)) {
					// ibatisDAO.deleteData("delVLANInstanceInfo", orderId);
					BatchVO batchDelLB = new BatchVO("MOD", "delLBInstanceInfo", orderId);
					batchVOs.add(batchDelLB);
				} else if ("15".equals(caseType)) {
					BatchVO batchFS = new BatchVO("MOD", "updateFSInstance", orderId);
					batchVOs.add(batchFS);
				} else if ("16".equals(caseType)) {
					BatchVO batchVirfw = new BatchVO("MOD", "updateVirFwInstance", orderId);
					batchVOs.add(batchVirfw);
				}else if ("17".equals(caseType)) {
						BatchVO batchDelVlanSdn = new BatchVO("MOD", "delVLANSdnInstanceInfo", orderId);
						batchVOs.add(batchDelVlanSdn);					
				}
			}else if("7".equals(status)){
				if ("17".equals(caseType)){
					ibatisDAO.deleteData("delVLANSDNUpdateInstanceInfo",orderId);
				} 
			}else if("8".equals(status)){
				if ("17".equals(caseType)){
					flag = "no";
				}
			}
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "删除实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "删除实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	
	/**
	 * sentLbCreate创建负载均衡
	 */
	private String sentLbCreate(String userId) {
		// 负载均衡信息
		LoadBalanceInfo lb;
		try {
			lb = (LoadBalanceInfo) ibatisDAO.getSingleRecord("queryAuditLbInstanceInfo", orderId);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPLBCreateReq req = new RPPLBCreateReq();
		// 请求号
		req.setSerialNum(seqCen.generatorSerialNum());

		LBDemand lbDemand = new LBDemand();
		lbDemand.setConnectNum(Integer.parseInt(lb.getConnectNum()));
		lbDemand.setNewConnectNum(Integer.parseInt(lb.getNewConnectNum()));
		lbDemand.setThroughput(Integer.parseInt(lb.getThroughput()));

		req.setAppID(lb.getAppid());
		req.setAppName(lb.getAppname());
		// 不使用模板 ，默认为1
		req.setParamFlag("1");
		req.setLbDemand(lbDemand);
		req.setLbPort(lb.getLbPort());
		req.setProtocal(lb.getProtocal());
		if (!privateIpSelect.equals(lb.getLbip())) {
			lb.setLbip(privateIpSelect);
		}
		if (!ipsegmentSelect.equals(lb.getLbIpSegment())) {
			lb.setLbIpSegment(ipsegmentSelect);
		}
		if (!vlanSelect.equals(lb.getLbVlan())) {
			lb.setLbVlan(vlanSelect);
		}
		logger.info("lb.getLbIpSegment() : " + lb.getLbIpSegment());
		req.setLbip(privateIpSelect+"="+lb.getLbIpSegment());

//		req.setLbip(privateIpSelect);
		req.setLbStrategy(Integer.parseInt(lb.getStrategy()));
		req.setLbName(lb.getLbname());
		req.setResourcePoolId(lb.getRespoolId());
		req.setResourcePoolPartId(lb.getRespoolPartId());
		RPPLBCreateResp resp = new RPPLBCreateResp();
		resp = lbCreate.createLB(req);
		logger.info("创建负载均衡请求返回CODE为++++++++++++" + resp.getResultCode());
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			lb.setInstanceId(resp.getLbId());
			BatchVO batchLBInfo = new BatchVO("MOD", "updateLBInfo", lb);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchLBInfo);
			logger.info("创建负载均衡发送申请成功！createUserName: " + userId);
			logger.info("创建负载均衡返回：" + resp.getFaultstring());
			return ConstantEnum.SUCCESS.toString();
		} else {
			// 返回失败，入失败库
			logger.info("创建负载均衡发送申请失败！");
			logger.info("创建负载均衡返回：" + resp.getFaultstring());
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
	}

	/**
	 * 发送创建虚拟机请求
	 * 
	 * @return 是否成功
	 */
	private String sentVmCreate(String userId) {
		// 查询虚拟机实例信息
		VMInstanceInfo vmInstanceInfo;
		ChargesInfo chargesInfo;
		String memorySize = "";
		try {
			vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmInstanceInfo", orderId);
			ChargesInfo charges = new ChargesInfo();
			charges.setChargesType("0");// 虚拟机类型.
			charges.setCpuNumber(vmInstanceInfo.getCpuNum());

			BigDecimal big = new BigDecimal(vmInstanceInfo.getRamSize());
			memorySize = big.divide(new BigDecimal("1024"), 0, BigDecimal.ROUND_UP).toString();
			charges.setMemorySize(memorySize);
			// chargesInfo =(ChargesInfo) ibatisDAO.getSingleRecord("findChargeInfo",
			// charges);
			// if(chargesInfo==null){
			// logger.info("CPU:"+vmInstanceInfo.getCpuNum()+",内存:"+vmInstanceInfo.getRamSize()+"不存在资费信息,资源申请不能通过");
			// resultPath = ConstantEnum.ERROR.toString();
			// message
			// ="CPU:"+vmInstanceInfo.getCpuNum()+"个,内存:"+vmInstanceInfo.getRamSize()+"MB不存在资费信息,<br>生成计费话单异常";
			// return ConstantEnum.ERROR.toString();
			// }
			vmInstanceInfo.setPmId(pmId);
			ibatisDAO.updateData("updateVmCasePmId", vmInstanceInfo);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPVMCreateReq req = new RPPVMCreateReq();
		RPPVMCreateResp resp = new RPPVMCreateResp();
		// 请求号
		req.setSerialNum(seqCen.generatorSerialNum());
		// 虚拟机信息
		if ("0".equals(vmInstanceInfo.getParamFlag())) {
			req.setCreateModel(VMCreateModel.TemplateModel);
			req.setStandardId(vmInstanceInfo.getStandardId());
		} else {
			req.setCreateModel(VMCreateModel.CustomModel);
		}
		req.setAppId(vmInstanceInfo.getAppId());
		req.setAppName(vmInstanceInfo.getAppName());
		req.setCount(1);
		req.setCpuNum(Integer.valueOf(vmInstanceInfo.getCpuNum()));
		req.setMemorySizsMB(Integer.valueOf(vmInstanceInfo.getRamSize()));
		req.setOsDiskType("1"); // 设置本地磁盘或者共享磁盘或其他0: 本地; 1: 共享磁盘FC SAN; 99: 其他
		req.setOsId(vmInstanceInfo.getIsoId());
		req.setOsSizeGB(Integer.valueOf(vmInstanceInfo.getDiscSize()));
		req.setResourcePoolId(vmInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
		req.setSecurity("1");
		req.setPmId(vmInstanceInfo.getPmId());
		// req.setVmName(vmInstanceInfo.getVmName());
		List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();
		List<EthInfo> ethInfos = new ArrayList<EthInfo>();
		List<NetInfo> netInfos = new ArrayList<NetInfo>();
		try {
			netInfos = ibatisDAO.getData("getNetByCaseId", vmInstanceInfo.getCaseId());
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		for (NetInfo netInfo : netInfos) {
			EthInfo ethInfo = new EthInfo();
			ethInfo.setIp(netInfo.getIp());
			ethInfo.setPurpose(VMEthPurpose.Business);
			ethInfo.setSubNetMask(netInfo.getSubNetMask());
			ethInfo.setVlanId(netInfo.getVlanId());
			ethInfo.setDefaultGateWay(netInfo.getGateway());
			ethInfo.setName(netInfo.getEth());
			ethInfos.add(ethInfo);
		}
		ethList.add(ethInfos);
		if (netInfos.size() > 0) {// 有网卡信息，需要写入接口
			req.setEthList(ethList);
		}
		resp = vmCreate.createCustomVM(req);
		logger.info("创建虚拟机请求返回CODE为++++++++++++" + resp.getResultCode());
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			logger.info("创建成功啦 成功啦");
			logger.info("VMStatus.CREATING: "+VMStatus.CREATING);
			vmInstanceInfo.setStatus(VMStatus.CREATING);// 异步 创建中
			
			List<VMInfo> vmInfos = resp.getVmInfoList();
			logger.info("resp.getVmInfoList: "+resp.getVmInfoList());
			logger.info("vmInfos.get(0): "+vmInfos.get(0));
			VMInfo vmInfo = vmInfos.get(0);// 请求中只有一个虚拟机
			logger.info("已创建的虚拟机信息为, vminfo: " + vmInfo.getVmName() + ",vmid: " + vmInfo.getVmId() + ",OperationIP: "
					+ vmInfo.getOperationIP() + ",password: " + vmInfo.getPassWord() + ",UserName: "
					+ vmInfo.getUserName() + ",Url: " + vmInfo.getUrl() + ", createUserName: " + userId + ", AppName: "
					+ req.getAppName() + ", CpuNum: " + req.getCpuNum() + ", memorySize: " + req.getMemorySizsMB()
					+ ", diskSize: " + req.getOsSizeGB() + ", count: " + req.getCount() + ", osId: " + req.getOsId());
			vmInstanceInfo.setVmId(vmInfo.getVmId());
			vmInstanceInfo.setPrivateIP(vmInfo.getOperationIP());
			vmInstanceInfo.setVmPassword(vmInfo.getPassWord());
			vmInstanceInfo.setUserName(vmInfo.getUserName());
			vmInstanceInfo.setOperationURL(vmInfo.getUrl());
			// 更新网卡中的vmid
			logger.info("到这啦，到这啦");
			NetInfo info = new NetInfo();
			logger.info("vmInstanceInfo.getCaseId(): "+ vmInstanceInfo.getCaseId());
			info.setCaseId(vmInstanceInfo.getCaseId());
			logger.info("vmInfo.getVmId(): "+ vmInfo.getVmId());
			info.setVmId(vmInfo.getVmId());
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			logger.info("vmstatus: "+vmInstanceInfo.getStatus());
			BatchVO batchVOVmInstanceInfo = new BatchVO("MOD", "updateVmInstanceInfo", vmInstanceInfo);
			;
			batchVOs.add(batchVOVmInstanceInfo);
			logger.info("batchVOs： "+batchVOs.size());
			if (netInfos.size() > 0) {// 有网卡信息，需要更新
				BatchVO batchVONetInstanceInfo = new BatchVO("MOD", "updateVmNet", info);
				batchVOs.add(batchVONetInstanceInfo);
			}

			// 记录计费话单数据.
			// BillingRecodInfo billingRecord = new BillingRecodInfo();
			// billingRecord.setChargesUserId(vmInstanceInfo.getChargesUserId());
			// billingRecord.setResourceId(vmInstanceInfo.getCaseId());
			// billingRecord.setLengthTime(vmInstanceInfo.getLengthTime());
			// billingRecord.setLengthUnit(vmInstanceInfo.getLengthUnit());
			// billingRecord.setCpu(vmInstanceInfo.getCpuNum());
			// billingRecord.setMemory(memorySize);

			// if(vmInstanceInfo.getLengthUnit().equals("h")){
			// billingRecord.setPrice(calculateVmBilling(chargesInfo.getHourPrice(),
			// vmInstanceInfo.getLengthTime()));
			// }else if(vmInstanceInfo.getLengthUnit().equals("m")){
			// billingRecord.setPrice(calculateVmBilling(chargesInfo.getMonthPrice(),
			// vmInstanceInfo.getLengthTime()));
			// }
			// billingRecord.setResourceType("0");
			// billingRecord.setChargesId(chargesInfo.getId());
			// billingRecord.setUserId(vmInstanceInfo.getCreateUser());
			// batchVOs.add(new BatchVO("ADD", "insertBilling", billingRecord));
			/*
			 * try { ibatisDAO.updateBatchData(batchVOs); } catch (SQLException e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); } catch (Exception e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); }
			 */
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 计算虚拟机费用.
	 * 
	 * @param price
	 * @param time
	 * @return
	 */
	private String calculateVmBilling(String price, String time) {
		BigDecimal pricebiDecimal = new BigDecimal(price);
		BigDecimal timeBigDecimal = new BigDecimal(time);
		BigDecimal resultBigDecimal = pricebiDecimal.multiply(timeBigDecimal);
		return resultBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 计算云硬盘费用.
	 */
	private String calculateEbsBilling(String price, String time, String size) {
		BigDecimal pricebiDecimal = new BigDecimal(price);
		BigDecimal timeBigDecimal = new BigDecimal(time);
		BigDecimal sizeBigDecimal = new BigDecimal(size);
		BigDecimal resultBigDecimal = pricebiDecimal.multiply(timeBigDecimal).multiply(sizeBigDecimal);
		return resultBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 发送修改虚拟机请求
	 * 
	 * @return 是否成功
	 */
	private String sentVmModify() {
		VMModifyReq req = new VMModifyReq();
		VMModifyResp resp = new VMModifyResp();
		// 查询虚拟机实例信息
		VMInstanceInfo vmInstanceInfo;
		List<NetInfo> netList = new ArrayList<NetInfo>();
		List<NetInfo> newNetList = new ArrayList<NetInfo>();
		try {
			vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmInstanceInfo", orderId);
			// 查询修改信息最大时间戳
			String maxTime = (String) ibatisDAO.getSingleRecord("getMaxTime", vmInstanceInfo.getVmId());
			vmInstanceInfo.setMaxTime(maxTime);
			netList = ibatisDAO.getData("getNetByCaseIdOnlyEdit", vmInstanceInfo);// 变化的网卡，用于发送接口
			if (netList.size() != 0) {
				req.setNetList(netList);
				newNetList = ibatisDAO.getData("getNetByCaseIdEdit", vmInstanceInfo);// 变化后的网卡，用户更新数据库
			}
			VMInstanceInfo vmInfoEdit = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmInstanceInfoEdit",
					vmInstanceInfo);// 如果只修改了网卡信息则vmInfoEdit为空
			if (vmInfoEdit != null) {
				/*
				 * if(vmInfoEdit.getVmName()!=null &&!"".equals(vmInfoEdit.getVmName())){
				 * vmInstanceInfo.setVmName(vmInfoEdit.getVmName());
				 * req.setVmName(vmInfoEdit.getVmName()); }
				 */
				if (vmInfoEdit.getCpuNum() != null && !"".equals(vmInfoEdit.getCpuNum())) {
					vmInstanceInfo.setCpuNum(vmInfoEdit.getCpuNum());
					req.setCpuNum(vmInfoEdit.getCpuNum());
				}
				if (vmInfoEdit.getRamSize() != null && !"".equals(vmInfoEdit.getRamSize())) {
					vmInstanceInfo.setRamSize(vmInfoEdit.getRamSize());
					req.setRamSize(vmInfoEdit.getRamSize());
				}
				if (vmInfoEdit.getDiscSize() != null && !"".equals(vmInfoEdit.getDiscSize())) {
					vmInstanceInfo.setDiscSize(vmInfoEdit.getDiscSize());
					req.setDiscSize(vmInfoEdit.getDiscSize());
				}
			}
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

		// 虚拟机信息
		req.setVmId(vmInstanceInfo.getVmId());
		req.setResourcePoolId(vmInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
		req.setSerialNum(seqCen.generatorSerialNum());

		resp = vmModify.modifyVM(req);
		// resp.setResultCode(SUCCEESS_CODE);
		// 虚拟机批量修改标识位
		String vmModifyFlag = "";
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			vmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
			vmInstanceInfo.setUpdateUser(userId);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			BatchVO batchVOVmInstanceInfo = new BatchVO("MOD", "updateVmInstanceInfo", vmInstanceInfo);
			batchVOs.add(batchVOVmInstanceInfo);
			if (netList.size() != 0) {
				BatchVO delVo = new BatchVO("DEL", "deleteVMNetInfo", vmInstanceInfo);
				batchVOs.add(delVo);
				for (NetInfo info : newNetList) {
					info.setCaseId(vmInstanceInfo.getCaseId());
					info.setVmId(vmInstanceInfo.getVmId());
					BatchVO addVo = new BatchVO("ADD", "insertVMNetInfo", info);
					batchVOs.add(addVo);
				}
			}
			vmModifyFlag = "1";
			/*
			 * try { ibatisDAO.updateBatchData(batchVOs); } catch (SQLException e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); } catch (Exception e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); }
			 */
		} else {
			vmModifyFlag = "0";
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "修改虚拟机发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}

		// 父订单包含VMP，则证明该订单为虚拟机批量修改类型的订单
		if (parentId.indexOf("VMBP") > -1) {
			BatchVMInfo batchVmInfo = new BatchVMInfo();
			batchVmInfo.setId(UUID.randomUUID().toString());
			String str = resp.getResultMessage();
			String bianma = "";
			try {
				bianma = new String(str.getBytes("utf8"), "utf8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // 解码

			logger.info("修改虚拟机资源池返回Message+++++++：" + bianma);
			batchVmInfo.setModifyDesc(bianma);
			batchVmInfo.setVmId(vmInstanceInfo.getVmId());
			batchVmInfo.setVmModifyFlag(vmModifyFlag);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			batchVmInfo.setCreateTime(df.format(new Date()));
			try {
				ibatisDAO.insertData("insertVMBatchInfo", batchVmInfo);
			} catch (SQLException e) {
				// 返回失败，入失败库
				logger.info("批量修改虚拟机失败，入库时出错！" + e.getMessage());
				this.isSuccess = ConstantEnum.ERROR.toString();
				message = message + "批量修改虚拟机失败，入库时出错！";
			}
		}

		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送创建物理机请求
	 * 
	 * @return 是否成功
	 */
	private String sentPmCreate() {
		// 查询虚拟机实例信息
		PMInstanceInfo pmInstanceInfo;
		try {
			pmInstanceInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord("queryPmInstanceInfo", orderId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPPMCreateReq req = new RPPPMCreateReq();
		RPPPMCreateResp resp = new RPPPMCreateResp();
		// 请求号
		req.setSerialNum(seqCen.generatorSerialNum());
		// 虚拟机信息
		if ("0".equals(pmInstanceInfo.getParamFlag())) {
			req.setCreateModel(PMCreateModel.TemplateModel);
			req.setStandardId(pmInstanceInfo.getStandardId());
		} else {
			req.setCreateModel(PMCreateModel.CustomModel);
		}
		req.setAppId(pmInstanceInfo.getAppId());
		req.setAppName(pmInstanceInfo.getAppName());
		req.setCount(1);
		req.setCPUType(pmInstanceInfo.getCpuType());
		req.setMemorySizsMB(Integer.valueOf(pmInstanceInfo.getRamSize()));
		req.setOsId(pmInstanceInfo.getIsoId());
		req.setOsSizeGB(Integer.valueOf(pmInstanceInfo.getDiscSize()));
		req.setServerType(pmInstanceInfo.getServerType());
		req.setResourcePoolId(pmInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(pmInstanceInfo.getResPoolPartId());
		req.setSecurity("1");
		// req.setPmName(pmInstanceInfo.getPmName());
		List<List<com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo>> ethList = new ArrayList<List<com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo>>();
		List<com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo> ethInfos = new ArrayList<com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo>();
		List<NetInfo> netInfos = new ArrayList<NetInfo>();
		try {
			netInfos = ibatisDAO.getData("getNetByCaseIdPm", pmInstanceInfo.getCaseId());
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		for (NetInfo netInfo : netInfos) {
			com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo ethInfo = new com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo();
			ethInfo.setIp(netInfo.getIp());
			ethInfo.setPurpose(PMEthPurpose.Business);
			ethInfo.setSubNetMask(netInfo.getSubNetMask());
			ethInfo.setVlanId(netInfo.getVlanId());
			ethInfo.setDefaultGateWay(netInfo.getGateway());
			ethInfo.setName(netInfo.getEth());
			ethInfos.add(ethInfo);
		}
		ethList.add(ethInfos);
		if (netInfos.size() > 0) {// 有网卡信息，需要写入接口
			req.setEthList(ethList);
		}
		resp = pmCreate.createCustomPM(req);
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			pmInstanceInfo.setStatus(PMStatus.CREATING);// 异步 创建中
			List<PMInfo> pmInfos = resp.getPmInfoList();
			PMInfo pmInfo = pmInfos.get(0);// 请求中只有一个虚拟机
			pmInstanceInfo.setPmId(pmInfo.getPmId());
			pmInstanceInfo.setOperationIP(pmInfo.getOperationIP());
			pmInstanceInfo.setPmPassword(pmInfo.getPassWord());
			pmInstanceInfo.setPmUser(pmInfo.getUserName());
			/*
			 * pmInstanceInfo.setPmId("testPm001");
			 * pmInstanceInfo.setOperationIP("1.1.1.1");
			 * pmInstanceInfo.setPmPassword("pass"); pmInstanceInfo.setPmUser("user");
			 */
			// 更新网卡中的pmid
			NetInfo info = new NetInfo();
			info.setCaseId(pmInstanceInfo.getCaseId());
			info.setPmId(pmInstanceInfo.getPmId());
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			BatchVO batchVOPmInstanceInfo = new BatchVO("MOD", "updatePmInstanceInfo", pmInstanceInfo);
			batchVOs.add(batchVOPmInstanceInfo);
			if (netInfos.size() > 0) {// 有网卡信息，需要更新
				BatchVO batchVONetInstanceInfo = new BatchVO("MOD", "updatePmNet", info);
				batchVOs.add(batchVONetInstanceInfo);
			}
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送修改物理机请求
	 * 
	 * @return 是否成功
	 */
	private String sentPmModify() {
		PMModifyReq req = new PMModifyReq();
		PMModifyResp resp = new PMModifyResp();
		// 查询虚拟机实例信息
		PMInstanceInfo pmInstanceInfo;
		List<NetInfo> netList = new ArrayList<NetInfo>();
		List<NetInfo> newNetList = new ArrayList<NetInfo>();
		try {
			pmInstanceInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord("queryPmInstanceInfo", orderId);
			// 查询修改信息最大时间戳
			String maxTime = (String) ibatisDAO.getSingleRecord("getMaxTimePm", pmInstanceInfo.getPmId());
			pmInstanceInfo.setMaxTime(maxTime);
			netList = ibatisDAO.getData("getNetByCaseIdOnlyEditPm", pmInstanceInfo);// 变化的网卡，用于发送接口
			if (netList.size() != 0) {
				req.setNetList(netList);
				newNetList = ibatisDAO.getData("getNetByCaseIdEditPm", pmInstanceInfo);// 变化后的网卡，用户更新数据库
			}
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

		// 虚拟机信息
		req.setPmId(pmInstanceInfo.getPmId());
		req.setResourcePoolId(pmInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(pmInstanceInfo.getResPoolPartId());
		req.setSerialNum(seqCen.generatorSerialNum());

		resp = pmModify.modifyPM(req);
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			pmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
			pmInstanceInfo.setUpdateUser(userId);
			BatchVO batchVOPmInstanceInfo = new BatchVO("MOD", "updatePmInstanceInfo", pmInstanceInfo);
			batchVOs.add(batchVOPmInstanceInfo);
			if (netList.size() != 0) {
				BatchVO delVo = new BatchVO("DEL", "deletePMNetInfo", pmInstanceInfo);
				batchVOs.add(delVo);
				for (NetInfo info : newNetList) {
					info.setCaseId(pmInstanceInfo.getCaseId());
					info.setPmId(pmInstanceInfo.getPmId());
					BatchVO addVo = new BatchVO("ADD", "insertPMNetInfo", info);
					batchVOs.add(addVo);
				}
			}
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "修改虚拟机发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送创建虚拟机备份任务请求
	 * 
	 * @return 是否成功
	 */
	private String sentBkCreate() {
		// 查询虚拟机备份任务实例信息
		VmBakInstanceInfo vmBakInstanceInfo;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			vmBakInstanceInfo = (VmBakInstanceInfo) ibatisDAO.getSingleRecord("queryBkInstanceInfo", orderId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPVMBakCreateReq req = new RPPVMBakCreateReq();
		RPPVMBakCreateResp resp = new RPPVMBakCreateResp();
		req.setVmId(vmBakInstanceInfo.getVmId());
		req.setVmBackupType(VMBakType.INCREMENT_BACK); // 备份类型（2：增量备份）
		req.setVmBackupMode(VMBakMode.CYCLE_BACK); // 备份方式（2：周期备份）
		req.setBackupCyc(Integer.parseInt(vmBakInstanceInfo.getBackupCyc())); // 备份周期
		req.setBackStoreTime(Integer.parseInt(vmBakInstanceInfo.getBackStoreTime())); // 备份保留时间
		req.setResourcePoolId(vmBakInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(vmBakInstanceInfo.getResPoolPartId());
		req.setCaseId(vmBakInstanceInfo.getCaseId()); // 实例ID
		req.setSerialNum(seqCen.generatorSerialNum());

		/* 设置备份任务执行的开始时间，请求报文发送时间要比备份开始时间提前五分钟，否则就把开始时间延迟到下一周期 */
		String backupCyc = vmBakInstanceInfo.getBackupCyc();
		String backupStartTime = DateParse.parse(vmBakInstanceInfo.getBackupStartTime());
		backupStartTime = this.calBackupStartTime(backupCyc, backupStartTime);
		req.setBackupStartTime(DateParse.generateDateFromLongString(backupStartTime));
		vmBakInstanceInfo.setBackupStartTime(DateParse.parseTime(backupStartTime));

		resp = vmBakCreate.createVmBak(req);
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			vmBakInstanceInfo.setVmBakId(resp.getVmBackupId());
			vmBakInstanceInfo.setAcceptTime(sdf.format(resp.getBackupTime()));
			vmBakInstanceInfo.setStatus(VmBakStatus.PREPARE);

			BatchVO batchVOBkInstanceInfo = new BatchVO("MOD", "updateVMBAKInstanceInfo", vmBakInstanceInfo);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOBkInstanceInfo);
			/*
			 * try { ibatisDAO.updateBatchData(batchVOs); } catch (SQLException e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); } catch (Exception e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); }
			 */
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机备份任务发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送创建硬盘请求
	 * 
	 * @return 是否成功
	 */
	private String sentEbsCreate(String userId) {
		logger.info("审批云硬盘开始");
		// 查询EBS实例信息
		EBSInstanceInfo ebsInstanceInfo;
		/* ChargesInfo charge = new ChargesInfo(); */
		try {

			/*
			 * charge.setChargesType("1"); List<ChargesInfo> chargesList
			 * =(List<ChargesInfo>) ibatisDAO.getData("queryAllCharges", charge);
			 * if(chargesList==null || chargesList.isEmpty()){
			 * logger.info("不存在云硬盘资费信息,审核失败"); resultPath = ConstantEnum.ERROR.toString();
			 * return ConstantEnum.ERROR.toString(); } charge = chargesList.get(0);
			 */
			ebsInstanceInfo = (EBSInstanceInfo) ibatisDAO.getSingleRecord("queryEBSInstanceInfo", orderId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPEBSCreateReq req = new RPPEBSCreateReq();
		RPPEBSCreateResp resp = new RPPEBSCreateResp();
		req.setResourcePoolId(ebsInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(ebsInstanceInfo.getResPoolPartId());
		req.setEbsName(ebsInstanceInfo.getEbsName());
		req.setAppId(ebsInstanceInfo.getAppId());
		req.setAppName(ebsInstanceInfo.getAppName());
		req.setSerialNum(seqCen.generatorSerialNum());
		if (ebsInstanceInfo.getStandardId() != null && !"".equals(ebsInstanceInfo.getStandardId())) {
			/* 模板创建 */
			req.setCreateModel(EBSCreateModel.template);
			req.setStandardId(ebsInstanceInfo.getStandardId());
		} else {
			/* 自定义创建 */
			req.setCreateModel(EBSCreateModel.paramArray);
			logger.info("自定义云硬盘开始");
			BsParam para = new BsParam();
			logger.info("ebsInstanceInfo.getDiskSize():"+ebsInstanceInfo.getDiskSize());
			para.setVolSize((int)Float.parseFloat(ebsInstanceInfo.getDiskSize()));
			logger.info("para.getVol: "+para.getVolSize());
			para.setVolNum(1);
			logger.info("raid:"+raid);
			para.setRaid(raid);
			logger.info("ebsInstanceInfo.getResourceType():"+ebsInstanceInfo.getResourceType());
			para.setResourceType(Integer.valueOf(ebsInstanceInfo.getResourceType()));
			logger.info("storageNet:"+storageNet);
			para.setStorageNet(storageNet);
			logger.info("tier:"+tier);
			para.setTier(tier);
			logger.info("tierOpen"+tierOpen);
			para.setTierOpen(tierOpen);

			req.setBSParamArray(para);
		}
		logger.info("准备下发");
		resp = ebsCreate.createEBS(req);
		// resp.setResultCode(SUCCEESS_CODE);
		logger.info("management 创建云硬盘返回的状态码：" + resp.getResultCode());
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			ebsInstanceInfo.setEbsId(resp.getBSIDs().get(0));
			ebsInstanceInfo.setStatus(EBSStatus.CREATING);// 异步 创建中

			logger.info("云硬盘创建成功！ createUserName is :" + userId);
			BatchVO batchVOeBSInstanceInfo = new BatchVO("MOD", "updateEBSInstanceInfo", ebsInstanceInfo);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOeBSInstanceInfo);

			// 记录计费话单数据.
			/*
			 * BillingRecodInfo billingRecord = new BillingRecodInfo();
			 * billingRecord.setChargesUserId(ebsInstanceInfo.getChargesUserId());
			 * billingRecord.setResourceId(ebsInstanceInfo.getCaseId());
			 * billingRecord.setLengthTime(ebsInstanceInfo.getLengthTime());
			 * billingRecord.setLengthUnit(ebsInstanceInfo.getLengthUnit());
			 * billingRecord.setDiskSize(ebsInstanceInfo.getDiskSize());
			 * 
			 * if(ebsInstanceInfo.getLengthUnit().equals("h")){
			 * billingRecord.setPrice(calculateEbsBilling(charge.getHourPrice(),
			 * ebsInstanceInfo.getLengthTime(), ebsInstanceInfo.getDiskSize())); }else
			 * if(ebsInstanceInfo.getLengthUnit().equals("m")){
			 * billingRecord.setPrice(calculateEbsBilling(charge.getMonthPrice(),
			 * ebsInstanceInfo.getLengthTime(), ebsInstanceInfo.getDiskSize())); }
			 * billingRecord.setResourceType("5");
			 * billingRecord.setChargesId(charge.getId());
			 * billingRecord.setUserId(ebsInstanceInfo.getCreateUser());
			 */
			// batchVOs.add(new BatchVO("ADD", "insertBilling", billingRecord));
			/*
			 * try { ibatisDAO.updateBatchData(batchVOs); } catch (SQLException e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); } catch (Exception e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); }
			 */
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建硬盘发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送修改硬盘请求
	 * 
	 * @return 是否成功
	 */
	private String sentEbsModify() {
		// 查询EBS实例信息
		EBSInstanceInfo ebsInstanceInfo;
		try {
			ebsInstanceInfo = (EBSInstanceInfo) ibatisDAO.getSingleRecord("queryEBSInstanceInfo", orderId);
			EBSInstanceInfo temp = (EBSInstanceInfo) ibatisDAO.getSingleRecord("queryEBSInstanceInfoEdit",
					ebsInstanceInfo.getEbsId());
			ebsInstanceInfo.setDiskSize(temp.getDiskSize());
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPEBSModifyReq req = new RPPEBSModifyReq();
		RPPEBSModifyResp resp = new RPPEBSModifyResp();

		req.setEbsId(ebsInstanceInfo.getEbsId());
		req.setEbsName(ebsInstanceInfo.getEbsName());
		req.setDiskSize(Integer.valueOf(ebsInstanceInfo.getDiskSize()));
		req.setResourcePoolId(ebsInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(ebsInstanceInfo.getResPoolPartId());
		req.setSerialNum(seqCen.generatorSerialNum());

		resp = ebsModify.modify(req);
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			ebsInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
			ebsInstanceInfo.setUpdateUser(userId);

			BatchVO batchVOeBSInstanceInfo = new BatchVO("MOD", "updateEBSInstanceInfo", ebsInstanceInfo);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOeBSInstanceInfo);
			/*
			 * try { ibatisDAO.updateBatchData(batchVOs); } catch (SQLException e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); } catch (Exception e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); }
			 */
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "修改硬盘信息调用资源池代理接口失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送创建IP请求
	 * 
	 * @return 是否成功
	 */
	private String sentPipCreate(String userId) {
		// 查询IP段实例信息
		PipInstanceInfo pipInstanceInfo;
		try {
			pipInstanceInfo = (PipInstanceInfo) ibatisDAO.getSingleRecord("queryPipInstanceInfo", orderId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPIPApplyReq req = new RPPIPApplyReq();
		RPPIPApplyResp resp = new RPPIPApplyResp();
		req.setAppId(pipInstanceInfo.getAppId());
		req.setAppName(pipInstanceInfo.getAppName());
		req.setCreateModel(IPCreateModel.CustomModel);
		req.setIpSegmentURI(pipInstanceInfo.getIpsegmentId());
		req.setResourcePoolId(pipInstanceInfo.getResPoolId());
		req.setResourcePoolPartId(pipInstanceInfo.getResPoolPartId());
		req.setSerialNum(seqCen.generatorSerialNum());
		req.setCount(1);
		if ("0".equals(pipInstanceInfo.getIpType())) {
			req.setIpProType(IPProType.IPV4);
		} else if ("1".equals(pipInstanceInfo.getIpType())) {
			req.setIpProType(IPProType.IPV6);
		}
		req.setIpType(IPType.PRIVATE_IP);

		resp = ipCreate.apply(req);
		// resp.setResultCode(SUCCEESS_CODE);
		logger.info("结果:="+resp.getResultCode());
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			logger.info("okokokokok");
			// 拼凑实例信息
			pipInstanceInfo.setReleased("0");
			logger.info("创建IP成功！pipInstanceInfo: " + pipInstanceInfo.getReleased() + ",caseid: "+pipInstanceInfo.getCaseId() + ", createUserName: " + userId);
			BatchVO batchVOPipInstanceInfo = new BatchVO("MOD", "updatePipInstanceInfo", pipInstanceInfo);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOPipInstanceInfo);
			
			 try {
				 logger.info("更新更新更新");
				 ibatisDAO.updateBatchData(batchVOs); 
				 }
			 catch (SQLException e) {
				 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
						 	+ "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
						 			ConstantEnum.ERROR.toString(); }
			 catch (Exception e) {
				 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
						 	+ "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
						 			ConstantEnum.ERROR.toString();
			  }			 
		} else {
			// 返回失败，入失败库
			logger.info("失败失败失败");
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建IP段发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送创建VLAN请求
	 * 
	 * @return 是否成功
	 */
	private String sentVlanCreate(String userId) {
		// 查询VLAN实例信息
		VlanInstanceInfo vlanInstanceInfo;
		try {
			vlanInstanceInfo = (VlanInstanceInfo) ibatisDAO.getSingleRecord("queryVlanInstanceInfo", orderId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		RPPVlanApplyReq req = new RPPVlanApplyReq();
		RPPVlanApplyResp resp = new RPPVlanApplyResp();
		req.setResourcePoolId(vlanInstanceInfo.getResPoolId());
		// 创建vlan需要跨分区 2016/05/09
		// req.setResourcePoolPartId(vlanInstanceInfo.getResPoolPartId());
		req.setCount(1);
		req.setAppId(vlanInstanceInfo.getAppId());
		req.setAppName(vlanInstanceInfo.getAppName());
		req.setSerialNum(seqCen.generatorSerialNum());

		resp = vlanCreate.create(req);
		// resp.setResultCode(SUCCEESS_CODE);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			// 拼凑实例信息
			vlanInstanceInfo.setVlanId(resp.getVlanIdList().get(0));
			vlanInstanceInfo.setCanceled("0");

			BatchVO batchVOVlanInstanceInfo = new BatchVO("MOD", "updateVlanInstanceInfo", vlanInstanceInfo);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOVlanInstanceInfo);
			logger.info("创建vlan成功！ createUserName is: " + userId);
			/*
			 * try { ibatisDAO.updateBatchData(batchVOs); } catch (SQLException e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); } catch (Exception e) {
			 * logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId
			 * + "更新实例信息时，操作数据库异常！", e); resultPath = ConstantEnum.ERROR.toString(); return
			 * ConstantEnum.ERROR.toString(); }
			 */
		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建VLAN发送申请失败！");
			this.isSuccess = ConstantEnum.ERROR.toString();
			message = message + resp.getResultMessage();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 发送创建3.4期VLAN请求
	 * 
	 * @return 是否成功
	 */
	private String sentVlan4SDNCreate(String userId) {
		// 查询VLAN实例信息
		Vlan4SDNInstanceInfo vlan4SDNInstanceInfo;
		try {
			vlan4SDNInstanceInfo = (Vlan4SDNInstanceInfo) ibatisDAO.getSingleRecord("queryVlan4SDNInstanceInfo", orderId);
			H3cVlanResp h3cVlanResp = h3CHttpClientService.createH3cVlan(vlan4SDNInstanceInfo);
			vlan4SDNInstanceInfo.setRangeId(h3cVlanResp.getGateway_vlan_range().getId());
			vlan4SDNInstanceInfo.setCanceled("0");
			if (h3cVlanResp.getGateway_vlan_range().getAllocated()) {
				vlan4SDNInstanceInfo.setAllocated("1");
			} else {
				vlan4SDNInstanceInfo.setAllocated("0");
			}
			vlan4SDNInstanceInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
			vlan4SDNInstanceInfo.setCreateUser(userId);
			BatchVO batchVOVlanInstanceInfo = new BatchVO("MOD", "updateVlanSDNInstanceInfo", vlan4SDNInstanceInfo);
			// List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOVlanInstanceInfo);
			logger.info("创建华三 vlan成功！ createUserName is: " + userId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	
	/**
	 * 发送更新3.4期VLAN请求
	 * @param userId
	 * @return
	 */
	private String sentVlan4SDNUpdate(String userId) {
		// 查询VLAN实例信息
		try {
			Vlan4SDNInstanceInfo vlan4SDNUpdateInstanceInfo = (Vlan4SDNInstanceInfo) ibatisDAO.getSingleRecord("queryVlan4SDNUpdateInstanceInfo", orderId);
			H3cVlanResp h3cVlanResp = h3CHttpClientService.updateH3cVlan(vlan4SDNUpdateInstanceInfo);
			Vlan4SDNInstanceInfo vlan4SDNInstanceInfo = new Vlan4SDNInstanceInfo();
			vlan4SDNInstanceInfo.setCanceled("0");
			if (h3cVlanResp.getGateway_vlan_range().getAllocated()) {
				vlan4SDNInstanceInfo.setAllocated("1");
			} else {
				vlan4SDNInstanceInfo.setAllocated("0");
			}
			vlan4SDNInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
			vlan4SDNInstanceInfo.setUpdateUser(userId);
			vlan4SDNInstanceInfo.setVlanName(vlan4SDNUpdateInstanceInfo.getVlanName());
			vlan4SDNInstanceInfo.setStartId(vlan4SDNUpdateInstanceInfo.getStartId());
			vlan4SDNInstanceInfo.setEndId(vlan4SDNUpdateInstanceInfo.getEndId());
			vlan4SDNInstanceInfo.setCaseId(vlan4SDNUpdateInstanceInfo.getCaseId());
			BatchVO batchVOVlanInstanceInfo = new BatchVO("MOD", "updateVlanSDNInstanceInfo", vlan4SDNInstanceInfo);
			BatchVO batchDelVlan = new BatchVO("DEL", "delVLANSDNUpdateInstanceInfo", orderId);
			batchVOs.add(batchDelVlan);
			batchVOs.add(batchVOVlanInstanceInfo);
			logger.info("创建华三 vlan成功！ createUserName is: " + userId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	
	/**
	 * 发送删除3.4期VLAN请求
	 * @param userId
	 * @return
	 */
	private String sentVlan4SDNDel(String userId) {
		// 查询VLAN实例信息
		Vlan4SDNInstanceInfo vlan4SDNInstanceInfo;
		try {
			vlan4SDNInstanceInfo = (Vlan4SDNInstanceInfo) ibatisDAO.getSingleRecord("queryVlan4SDNInstanceInfo", orderId);
			h3CHttpClientService.deleteH3cVlan(vlan4SDNInstanceInfo);
			vlan4SDNInstanceInfo.setCanceled("1");
			vlan4SDNInstanceInfo.setAllocated("0"); // 删除后自动释放
			vlan4SDNInstanceInfo.setCancelTime(DateParse.generateDateFormatyyyyMMddHHmmss());
			vlan4SDNInstanceInfo.setCancelUser(userId);
			
			BatchVO batchVOVlanInstanceInfo = new BatchVO("MOD", "updateVlanSDNInstanceInfo", vlan4SDNInstanceInfo);
			batchVOs.add(batchVOVlanInstanceInfo);
			logger.info("创建华三 vlan成功！ createUserName is: " + userId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为" + orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	
	/**
	 * 计算备份任务执行的开始时间，请求报文发送时间要比备份开始时间提前五分钟，否则就把开始时间延迟到下一周期
	 */
	private String calBackupStartTime(String backupCyc, String backupStartTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 5);
		String compareTime = DateParse.generateDateFormatLong(cal.getTime());

		while (compareTime.compareTo(backupStartTime) > 0) { // （请求报文发送时间 + 5分钟）> 备份开始时间，要把开始时间延迟到下一周期
			Date startDate = DateParse.generateDateFromLongString(backupStartTime);
			cal.setTime(startDate);
			if ("1".equals(backupCyc)) { // 备份周期：每天
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} else if ("7".equals(backupCyc)) { // 备份周期：每周
				cal.add(Calendar.DAY_OF_MONTH, 7);
			}
			backupStartTime = DateParse.generateDateFormatLong(cal.getTime());
		}
		return backupStartTime;
	}

	/**
	 * 
	 * getOrderId 方法
	 * 
	 * @return 方法
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 
	 * setOrderId 方法
	 * 
	 * @param orderId
	 *            方法
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 * getPass 方法
	 * 
	 * @return 方法
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * 
	 * setPass 方法
	 * 
	 * @param pass
	 *            方法
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * 
	 * getAuditInfo 方法
	 * 
	 * @return 方法
	 */
	public String getAuditInfo() {
		return auditInfo;
	}

	/**
	 * 
	 * setAuditInfo 方法
	 * 
	 * @param auditInfo
	 *            方法
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	/**
	 * 
	 * getOrderInfo 方法
	 * 
	 * @return 方法
	 */
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * 
	 * setOrderInfo 方法
	 * 
	 * @param orderInfo
	 *            方法
	 */
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * 
	 * getOrderAuditInfo 方法
	 * 
	 * @return 方法
	 */
	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

	/**
	 * 
	 * setOrderAuditInfo 方法
	 * 
	 * @param orderAuditInfo
	 *            方法
	 */
	public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
		this.orderAuditInfo = orderAuditInfo;
	}

	/**
	 * 
	 * getResultPath 方法
	 * 
	 * @return 方法
	 */
	public String getResultPath() {
		return resultPath;
	}

	/**
	 * 
	 * setResultPath 方法
	 * 
	 * @param resultPath
	 *            方法
	 */
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	/**
	 * 
	 * getEbsCreate 方法
	 * 
	 * @return 方法
	 */
	public EBSCreate getEbsCreate() {
		return ebsCreate;
	}

	/**
	 * 
	 * setEbsCreate 方法
	 * 
	 * @param ebsCreate
	 *            方法
	 */
	public void setEbsCreate(EBSCreate ebsCreate) {
		this.ebsCreate = ebsCreate;
	}

	/**
	 * 
	 * getMessage 方法
	 * 
	 * @return 方法
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * setMessage 方法
	 * 
	 * @param message
	 *            方法
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the caseType
	 */
	public String getCaseType() {
		return caseType;
	}

	/**
	 * @param caseType
	 *            the caseType to set
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the vmBakCreate
	 */
	public VmBakCreate getVmBakCreate() {
		return vmBakCreate;
	}

	/**
	 * @param vmBakCreate
	 *            the vmBakCreate to set
	 */
	public void setVmBakCreate(VmBakCreate vmBakCreate) {
		this.vmBakCreate = vmBakCreate;
	}

	/**
	 * @return the ebsModify
	 */
	public EBSModify getEbsModify() {
		return ebsModify;
	}

	/**
	 * @param ebsModify
	 *            the ebsModify to set
	 */
	public void setEbsModify(EBSModify ebsModify) {
		this.ebsModify = ebsModify;
	}

	/**
	 * @return the ipCreate
	 */
	public ApplyIpSegment getIpCreate() {
		return ipCreate;
	}

	/**
	 * @param ipCreate
	 *            the ipCreate to set
	 */
	public void setIpCreate(ApplyIpSegment ipCreate) {
		this.ipCreate = ipCreate;
	}

	/**
	 * @return the vlanCreate
	 */
	public VLANCreate getVlanCreate() {
		return vlanCreate;
	}

	/**
	 * @param vlanCreate
	 *            the vlanCreate to set
	 */
	public void setVlanCreate(VLANCreate vlanCreate) {
		this.vlanCreate = vlanCreate;
	}

	/**
	 * @return the seqCen
	 */
	public CommonSequenceGenerator getSeqCen() {
		return seqCen;
	}

	/**
	 * @param seqCen
	 *            the seqCen to set
	 */
	public void setSeqCen(CommonSequenceGenerator seqCen) {
		this.seqCen = seqCen;
	}

	/**
	 * @return the vmCreate
	 */
	public VMCreate getVmCreate() {
		return vmCreate;
	}

	/**
	 * @param vmCreate
	 *            the vmCreate to set
	 */
	public void setVmCreate(VMCreate vmCreate) {
		this.vmCreate = vmCreate;
	}

	/**
	 * @return the vmModify
	 */
	public VMModify getVmModify() {
		return vmModify;
	}

	/**
	 * @param vmModify
	 *            the vmModify to set
	 */
	public void setVmModify(VMModify vmModify) {
		this.vmModify = vmModify;
	}

	/**
	 * @return the tier
	 */
	public Integer getTier() {
		return tier;
	}

	/**
	 * @param tier
	 *            the tier to set
	 */
	public void setTier(Integer tier) {
		this.tier = tier;
	}

	/**
	 * @return the raid
	 */
	public Integer getRaid() {
		return raid;
	}

	/**
	 * @param raid
	 *            the raid to set
	 */
	public void setRaid(Integer raid) {
		this.raid = raid;
	}

	/**
	 * @return the storageNet
	 */
	public Integer getStorageNet() {
		return storageNet;
	}

	/**
	 * @param storageNet
	 *            the storageNet to set
	 */
	public void setStorageNet(Integer storageNet) {
		this.storageNet = storageNet;
	}

	/**
	 * @return the resourceType
	 */
	public Integer getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            the resourceType to set
	 */
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the tierOpen
	 */
	public Integer getTierOpen() {
		return tierOpen;
	}

	/**
	 * @param tierOpen
	 *            the tierOpen to set
	 */
	public void setTierOpen(Integer tierOpen) {
		this.tierOpen = tierOpen;
	}

	/**
	 * @return the pmCreate
	 */
	public PMCreate getPmCreate() {
		return pmCreate;
	}

	/**
	 * @param pmCreate
	 *            the pmCreate to set
	 */
	public void setPmCreate(PMCreate pmCreate) {
		this.pmCreate = pmCreate;
	}

	/**
	 * @return the pmModify
	 */
	public PMModify getPmModify() {
		return pmModify;
	}

	/**
	 * @param pmModify
	 *            the pmModify to set
	 */
	public void setPmModify(PMModify pmModify) {
		this.pmModify = pmModify;
	}

	/**
	 * @return the pmId
	 */
	public String getPmId() {
		return pmId;
	}

	/**
	 * @param pmId
	 *            the pmId to set
	 */
	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	// /**
	// * @return the billingService
	// */
	// public BillingChargesService getBillingService() {
	// return billingService;
	// }
	//
	// /**
	// * @param billingService the billingService to set
	// */
	// public void setBillingService(BillingChargesService billingService) {
	// this.billingService = billingService;
	// }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public LBCreate getLbCreate() {
		return lbCreate;
	}

	public void setLbCreate(LBCreate lbCreate) {
		this.lbCreate = lbCreate;
	}

	public String getVlanSelect() {
		return vlanSelect;
	}

	public void setVlanSelect(String vlanSelect) {
		this.vlanSelect = vlanSelect;
	}

	public String getIpsegmentSelect() {
		return ipsegmentSelect;
	}

	public void setIpsegmentSelect(String ipsegmentSelect) {
		this.ipsegmentSelect = ipsegmentSelect;
	}

	public String getPrivateIpSelect() {
		return privateIpSelect;
	}

	public void setPrivateIpSelect(String privateIpSelect) {
		this.privateIpSelect = privateIpSelect;
	}

	public FileStorageExecuter getFileStorage() {
		return fileStorage;
	}

	public void setFileStorage(FileStorageExecuter fileStorage) {
		this.fileStorage = fileStorage;
	}

	public CreateVirFW getVirfwClient() {
		return virfwClient;
	}

	public void setVirfwClient(CreateVirFW virfwClient) {
		this.virfwClient = virfwClient;
	}

	/**
	 * @return the h3CHttpClientService
	 */
	public H3CHttpClientService getH3CHttpClientService() {
		return h3CHttpClientService;
	}

	/**
	 * @param h3cHttpClientService the h3CHttpClientService to set
	 */
	public void setH3CHttpClientService(H3CHttpClientService h3cHttpClientService) {
		h3CHttpClientService = h3cHttpClientService;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}
