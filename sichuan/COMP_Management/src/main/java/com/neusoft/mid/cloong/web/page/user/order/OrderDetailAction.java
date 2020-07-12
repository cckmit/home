/*******************************************************************************
 * @(#)OrderDetailAction.java 2015-3-7
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.PmInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaCapacityInfo;
import com.neusoft.mid.cloong.web.page.user.order.lb.LoadBalanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询订单详细信息
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-3-07 上午08:30:39
 */
public class OrderDetailAction extends BaseAction {

	private static final long serialVersionUID = -3291560302940406846L;

	private static LogService logger = LogService
			.getLogger(OrderDetailAction.class);

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 订单信息
	 */
	private OrderInfo orderInfo;

	/**
	 * 资源类型
	 */
	private String caseType;


	/**
	 * 物理机list
	 */
	private List<PmInfo> pms = new ArrayList<PmInfo>();

	/**
	 * 虚拟硬盘信息
	 */
	private EBSInstanceInfo ebsInfo;

	/**
	 * 虚拟硬盘信息——修改
	 */
	private EBSInstanceInfo ebsInfoEdit;

	/**
	 * vlan信息
	 */
	private VlanInstanceInfo vlanInfo;
	
	/**
	 * vlan_SDN信息
	 */
	private Vlan4SDNInstanceInfo vlanSdnInfo;
	
	/**
	 * vlan_SDN信息——修改
	 */
	private Vlan4SDNInstanceInfo vlanSdnInfoEdit;

	/**
	 * 私网IP信息
	 */
	private PipInstanceInfo pipInfo;

	/**
	 * 虚拟机备份任务
	 */
	private VmBakInstanceInfo bkInfo;

	/**
	 * 虚拟机
	 */
	private VMInstanceInfo vmInfo;

	/**
	 * 物理机
	 */
	private PMInstanceInfo pmInfo;

	/**
	 * 虚拟机--修改
	 */
	private VMInstanceInfo vmInfoEdit;

	/**
	 * 物理机
	 */
	private PMInstanceInfo pmInfoEdit;

	/**
	 * 订单审批记录表实体
	 */
	private OrderAuditInfo orderAuditInfo;
	
	/**
	 * 负载均衡
	 */
	private LoadBalanceInfo lbInfo;
	
	/**
	 * 分佈式文件存储
	 */
	private FileStoreInstanceInfo fsInfo;
	
	private PublicIpInstanceInfo pIpInfo;
	
	/**
	 * 虚拟防火墙
	 */
	private VfwInstanceInfo vfwInfo;
	
	/**
	 * VCPU实例
	 */
	private StaCapacityInfo vcpu = new StaCapacityInfo();

	/**
	 * 内存实例
	 */
	private StaCapacityInfo memory = new StaCapacityInfo();

	/**
	 * 磁盘实例
	 */
	private StaCapacityInfo disk = new StaCapacityInfo();

	/**
	 * 返回路径，用于在界面判断是否系统错误
	 */
	private String resultPath = ConstantEnum.SUCCESS.toString();
	
	private String status;

	public String execute() {
		orderInfo = new OrderInfo();
		orderInfo.setOrderId(orderId);
		try {
			String resPoolPartName = "";
			orderInfo = (OrderInfo) ibatisDAO.getSingleRecord(
					"queryOrderDetailInfo", orderInfo);
			if ("0".equals(caseType)) {
				vmInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord(
						"queryVmInstanceInfo", orderId);
				
				if (vmInfo.getResPoolId() != null
						&& vmInfo.getResPoolPartId() != null) {
					pms = queryPms(vmInfo.getResPoolId(),
							vmInfo.getResPoolPartId());
				}
				List<NetInfo> netList = new ArrayList<NetInfo>();
				if ("7".equals(orderInfo.getStatus())) {// 修改信息
					// 查询修改信息最大时间戳
					String maxTime = (String) ibatisDAO.getSingleRecord(
							"getMaxTime", vmInfo.getVmId());
					vmInfo.setMaxTime(maxTime);
					vmInfoEdit = (VMInstanceInfo) ibatisDAO.getSingleRecord(
							"queryVmInstanceInfoEdit", vmInfo);// 如果只修改了网卡信息则vmInfoEdit为空
					if(vmInfoEdit != null){
						int ramSize = StringUtils.isEmpty(vmInfoEdit.getRamSize())?0:Integer.valueOf(vmInfoEdit.getRamSize());
						vmInfoEdit.setRamSize(String.valueOf(ramSize/1024));
						String str = String.valueOf(ramSize);
						if(str.equals("0")){
							String size=vmInfo.getRamSize();
							Float ramSizes = Float.parseFloat(size);
							ramSizes = ramSizes/1024;
							size = String.valueOf(ramSizes);
							vmInfoEdit.setRamSize(size);
						}
					}
					
					netList = ibatisDAO.getData("getNetByCaseIdEdit", vmInfo);
				} else {
					netList = ibatisDAO.getData("getNetByCaseId",
							vmInfo.getCaseId());
				}
				int ramSize = StringUtils.isNotEmpty(vmInfo.getRamSize())?Integer.valueOf(vmInfo.getRamSize()):0;
				
				if (String.valueOf(Float.parseFloat(vmInfo.getDiscSize())/1024).contains(".0")) { // 如果是1.0这样的形式时把。0去掉
					vmInfo.setDiscSize(String.valueOf(Math.round(Float.parseFloat(vmInfo.getDiscSize())/1024)));
				} else {
					vmInfo.setDiscSize(String.valueOf(Float.parseFloat(vmInfo.getDiscSize())/1024));
				}
				
				vmInfo.setRamSize(String.valueOf(ramSize/1024));
				
				vmInfo.setNetList(netList);
				resPoolPartName = vmInfo.getResPoolPartName();
				// 资源池分区容量信息
				getCapacity(vmInfo.getResPoolId(), vmInfo.getResPoolPartId());
			} else if ("1".equals(caseType)) {
				pmInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord(
						"queryPmInstanceInfo", orderId);
				List<NetInfo> netList = new ArrayList<NetInfo>();
				if ("7".equals(orderInfo.getStatus())) {// 修改信息
					// 查询修改信息最大时间戳
					String maxTime = (String) ibatisDAO.getSingleRecord(
							"getMaxTimePm", pmInfo.getPmId());
					pmInfo.setMaxTime(maxTime);
					pmInfoEdit = (PMInstanceInfo) ibatisDAO.getSingleRecord(
							"queryPmInstanceInfoEdit", pmInfo);// 如果只修改了网卡信息则vmInfoEdit为空
					netList = ibatisDAO.getData("getNetByCaseIdEditPm", pmInfo);
				} else {
					netList = ibatisDAO.getData("getNetByCaseIdPm",
							pmInfo.getCaseId());
				}
				pmInfo.setNetList(netList);
				resPoolPartName = pmInfo.getResPoolPartName();
				// 资源池分区容量信息
				getCapacity(pmInfo.getResPoolId(), pmInfo.getResPoolPartId());
			} else if ("4".equals(caseType)) {
				bkInfo = (VmBakInstanceInfo) ibatisDAO.getSingleRecord(
						"queryBkInstanceInfo", orderId);
				resPoolPartName = bkInfo.getResPoolPartName();
			} else if ("5".equals(caseType)) {
				ebsInfo = (EBSInstanceInfo) ibatisDAO.getSingleRecord(
						"queryEBSInstanceInfo", orderId);
				if ("7".equals(orderInfo.getStatus())) {// 修改信息
					ebsInfoEdit = (EBSInstanceInfo) ibatisDAO.getSingleRecord(
							"queryEBSInstanceInfoEdit", ebsInfo.getEbsId());
				}
				resPoolPartName = ebsInfo.getResPoolPartName();
				// 资源池分区容量信息
				getCapacity(ebsInfo.getResPoolId(), ebsInfo.getResPoolPartId());
			} else if ("12".equals(caseType)) {
				pipInfo = (PipInstanceInfo) ibatisDAO.getSingleRecord(
						"queryPipInstanceInfo", orderId);
				resPoolPartName = pipInfo.getResPoolPartName();
			} else if ("13".equals(caseType)) {
				vlanInfo = (VlanInstanceInfo) ibatisDAO.getSingleRecord(
						"queryVlanInstanceInfo", orderId);
				resPoolPartName = vlanInfo.getResPoolPartName();
			}
			else if ("14".equals(caseType)) {
				Map<String, String> lbParam = new HashMap<String, String>();
				lbParam.put("orderId", orderId);
				if(null==status){
					lbParam.put("status", "0");
				}else{
					lbParam.put("status", status);
				}
				lbInfo = (LoadBalanceInfo) ibatisDAO.getSingleRecord(
						"queryLbInstanceInfo", lbParam);
				if("0".equals(lbInfo.getIpType())){
					String ipSegmentDesc = lbInfo.getStartIp()+"~"+lbInfo.getEndIp();
					lbInfo.setIpSegmentDesc(ipSegmentDesc);
				}
				resPoolPartName = lbInfo.getRespoolPartname();
			}
			else if ("7".equals(caseType)) {
			    pIpInfo=(PublicIpInstanceInfo) ibatisDAO.getSingleRecord("queryPublicIpInstanceInfo", orderId);
                resPoolPartName = pIpInfo.getResPoolPartName();
            }
			else if ("15".equals(caseType)) {
			    fsInfo=(FileStoreInstanceInfo) ibatisDAO.getSingleRecord("queryFsInstanceInfo", orderId);
			    resPoolPartName = fsInfo.getResPoolPartName();
			}
			else if ("16".equals(caseType)) {
			    vfwInfo=(VfwInstanceInfo) ibatisDAO.getSingleRecord("queryVfwInstanceInfo", orderId);
			    resPoolPartName = vfwInfo.getResPoolPartName();
			}
			else if("17".equals(caseType)){
				vlanSdnInfo = (Vlan4SDNInstanceInfo) ibatisDAO.getSingleRecord("queryVlanSdnInstanceInfo", orderId);
				if ("7".equals(orderInfo.getStatus())) {// 修改信息
					vlanSdnInfoEdit = (Vlan4SDNInstanceInfo) ibatisDAO.getSingleRecord(
							"queryVlanSdnInstanceInfoEdit", orderInfo.getOrderId());	
				}
				resPoolPartName = vlanSdnInfo.getResPoolPartName();
			}
			if (resPoolPartName != null) {
				orderInfo.setResPoolPartName(resPoolPartName);
			}
			orderAuditInfo = (OrderAuditInfo) ibatisDAO.getSingleRecord(
					"queryOrderAuditInfo", orderId);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"查询订单编号为" + orderId + "的详细信息时失败，数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"查询订单编号为" + orderId + "的详细信息时失败，数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		if (orderInfo.getEffectiveTime() != null) {
			orderInfo.setEffectiveTime(DateParse.parse(orderInfo
					.getEffectiveTime()));
		}
		if (orderInfo.getExpireTime() != null) {
			orderInfo.setExpireTime(DateParse.parse(orderInfo.getExpireTime()));
		}
		if (orderInfo.getUpdateTime() != null) {
			orderInfo.setUpdateTime(DateParse.parse(orderInfo.getUpdateTime()));
		}
		orderInfo.setCreateTime(DateParse.parse(orderInfo.getCreateTime()));

		if (vmInfo != null && vmInfo.getCreateTime() != null) {
			vmInfo.setCreateTime(DateParse.parse(vmInfo.getCreateTime()));
		}
		if (pmInfo != null && pmInfo.getCreateTime() != null) {
			pmInfo.setCreateTime(DateParse.parse(pmInfo.getCreateTime()));
		}
		if (vmInfo != null && vmInfo.getExpireTime() != null) {
			vmInfo.setExpireTime(DateParse.parse(vmInfo.getExpireTime()));
		}
		if (bkInfo != null && bkInfo.getBackupStartTime() != null) {
			bkInfo.setBackupStartTime(DateParse.parse(bkInfo
					.getBackupStartTime()));
		}
		if (orderAuditInfo != null && orderAuditInfo.getAuditTime() != null) {
			orderAuditInfo.setAuditTime(DateParse.parse(orderAuditInfo
					.getAuditTime()));
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 获取资源池分区容量信息
	 * 
	 * @param resPoolId
	 * @param resPoolPartId
	 * @throws SQLException
	 */
	private void getCapacity(String resPoolId, String resPoolPartId)
			throws SQLException {
		// 查询资源池VCPU最新值
		vcpu.setResPoolId(resPoolId);
		vcpu.setPoolPartId(resPoolPartId);
		vcpu.setResType("0");
		vcpu = (StaCapacityInfo) ibatisDAO.getSingleRecord(
				"queryCapacityforNum", vcpu);

		// 查询资源池内存使用情况
		memory.setResPoolId(resPoolId);
		memory.setPoolPartId(resPoolPartId);
		memory.setResType("1");
		memory = (StaCapacityInfo) ibatisDAO.getSingleRecord(
				"queryCapacityforGB", memory);
		// 查询资源池磁盘使用情况
		disk.setResPoolId(resPoolId);
		disk.setPoolPartId(resPoolPartId);
		disk.setResType("2");
		disk = (StaCapacityInfo) ibatisDAO.getSingleRecord(
				"queryCapacityforGB", disk);

		// 这里分别防止vcpu，memory，disk为null
		if (vcpu == null) {
			vcpu = new StaCapacityInfo();
		} else {
			vcpu = this.getVcpu();
		}
		if (memory == null) {
			memory = new StaCapacityInfo();
		} else {
			memory = this.getMemory();
		}
		if (disk == null) {
			disk = new StaCapacityInfo();
		} else {
			disk = this.getDisk();
		}
	}

	/**
	 * 对应物理机list
	 * 
	 * @param respoolId
	 * @param respoolPartId
	 * @return
	 * @throws SQLException
	 */
	private List<PmInfo> queryPms(String respoolId, String respoolPartId)
			throws SQLException {
		Map queryparam = new HashMap();
		queryparam.put("respoolId", respoolId);
		queryparam.put("respoolPartId", respoolPartId);
		return (List<PmInfo>) ibatisDAO.getData("queryPms", queryparam);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public String getResultPath() {
		return resultPath;
	}

	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

	/**
	 * @return the ebsInfo
	 */
	public EBSInstanceInfo getEbsInfo() {
		return ebsInfo;
	}

	/**
	 * @param ebsInfo
	 *            the ebsInfo to set
	 */
	public void setEbsInfo(EBSInstanceInfo ebsInfo) {
		this.ebsInfo = ebsInfo;
	}

	/**
	 * @return the ebsInfoEdit
	 */
	public EBSInstanceInfo getEbsInfoEdit() {
		return ebsInfoEdit;
	}

	/**
	 * @param ebsInfoEdit
	 *            the ebsInfoEdit to set
	 */
	public void setEbsInfoEdit(EBSInstanceInfo ebsInfoEdit) {
		this.ebsInfoEdit = ebsInfoEdit;
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
	 * @return the vlanInfo
	 */
	public VlanInstanceInfo getVlanInfo() {
		return vlanInfo;
	}

	/**
	 * @param vlanInfo
	 *            the vlanInfo to set
	 */
	public void setVlanInfo(VlanInstanceInfo vlanInfo) {
		this.vlanInfo = vlanInfo;
	}

	/**
	 * @return the pipInfo
	 */
	public PipInstanceInfo getPipInfo() {
		return pipInfo;
	}

	/**
	 * @param pipInfo
	 *            the pipInfo to set
	 */
	public void setPipInfo(PipInstanceInfo pipInfo) {
		this.pipInfo = pipInfo;
	}

	/**
	 * @return the bkInfo
	 */
	public VmBakInstanceInfo getBkInfo() {
		return bkInfo;
	}

	/**
	 * @param bkInfo
	 *            the bkInfo to set
	 */
	public void setBkInfo(VmBakInstanceInfo bkInfo) {
		this.bkInfo = bkInfo;
	}

	/**
	 * @return the vmInfo
	 */
	public VMInstanceInfo getVmInfo() {
		return vmInfo;
	}

	/**
	 * @param vmInfo
	 *            the vmInfo to set
	 */
	public void setVmInfo(VMInstanceInfo vmInfo) {
		this.vmInfo = vmInfo;
	}

	/**
	 * @return the vmInfoEdit
	 */
	public VMInstanceInfo getVmInfoEdit() {
		return vmInfoEdit;
	}

	/**
	 * @param vmInfoEdit
	 *            the vmInfoEdit to set
	 */
	public void setVmInfoEdit(VMInstanceInfo vmInfoEdit) {
		this.vmInfoEdit = vmInfoEdit;
	}

	/**
	 * @param orderInfo
	 *            the orderInfo to set
	 */
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @param orderAuditInfo
	 *            the orderAuditInfo to set
	 */
	public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
		this.orderAuditInfo = orderAuditInfo;
	}

	/**
	 * @param resultPath
	 *            the resultPath to set
	 */
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	/**
	 * @return the pmInfo
	 */
	public PMInstanceInfo getPmInfo() {
		return pmInfo;
	}

	/**
	 * @param pmInfo
	 *            the pmInfo to set
	 */
	public void setPmInfo(PMInstanceInfo pmInfo) {
		this.pmInfo = pmInfo;
	}

	/**
	 * @return the pmInfoEdit
	 */
	public PMInstanceInfo getPmInfoEdit() {
		return pmInfoEdit;
	}

	/**
	 * @param pmInfoEdit
	 *            the pmInfoEdit to set
	 */
	public void setPmInfoEdit(PMInstanceInfo pmInfoEdit) {
		this.pmInfoEdit = pmInfoEdit;
	}

	public StaCapacityInfo getVcpu() {
		return vcpu;
	}

	public void setVcpu(StaCapacityInfo vcpu) {
		this.vcpu = vcpu;
	}

	public StaCapacityInfo getMemory() {
		return memory;
	}

	public void setMemory(StaCapacityInfo memory) {
		this.memory = memory;
	}

	public StaCapacityInfo getDisk() {
		return disk;
	}

	public void setDisk(StaCapacityInfo disk) {
		this.disk = disk;
	}

	/**
	 * @return the pms
	 */
	public List<PmInfo> getPms() {
		return pms;
	}

	/**
	 * @param pms
	 *            the pms to set
	 */
	public void setPms(List<PmInfo> pms) {
		this.pms = pms;
	}

	public LoadBalanceInfo getLbInfo() {
		return lbInfo;
	}

	public void setLbInfo(LoadBalanceInfo lbInfo) {
		this.lbInfo = lbInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public FileStoreInstanceInfo getFsInfo() {
        return fsInfo;
    }

    public void setFsInfo(FileStoreInstanceInfo fsInfo) {
        this.fsInfo = fsInfo;
    }

    public VfwInstanceInfo getVfwInfo() {
        return vfwInfo;
    }

    public void setVfwInfo(VfwInstanceInfo vfwInfo) {
        this.vfwInfo = vfwInfo;
    }

    public PublicIpInstanceInfo getpIpInfo() {
        return pIpInfo;
    }

    public void setpIpInfo(PublicIpInstanceInfo pIpInfo) {
        this.pIpInfo = pIpInfo;
    }

	public Vlan4SDNInstanceInfo getVlanSdnInfo() {
		return vlanSdnInfo;
	}

	public void setVlanSdnInfo(Vlan4SDNInstanceInfo vlanSdnInfo) {
		this.vlanSdnInfo = vlanSdnInfo;
	}

	public Vlan4SDNInstanceInfo getVlanSdnInfoEdit() {
		return vlanSdnInfoEdit;
	}

	public void setVlanSdnInfoEdit(Vlan4SDNInstanceInfo vlanSdnInfoEdit) {
		this.vlanSdnInfoEdit = vlanSdnInfoEdit;
	}



}
