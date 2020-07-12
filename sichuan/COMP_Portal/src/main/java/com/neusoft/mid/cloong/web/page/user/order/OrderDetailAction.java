/*******************************************************************************
 * @(#)OrderDetailAction.java 2015-3-7
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.fileStore.info.FileStoreInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.LoadBalanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.cloong.web.page.console.vFirewall.info.VfirewallInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.ip.order.info.PublicIpInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询订单详细信息
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-3-07 上午08:30:39
 */
public class OrderDetailAction extends BaseAction {

    private static final long serialVersionUID = -3291560302940406846L;

    private static LogService logger = LogService.getLogger(OrderDetailAction.class);

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
     *所属物理机
     */
    private String pmName;

    /**
     * 云硬盘信息
     */
    private EBSInstanceInfo ebsInfo;
    
    /**
     * 云硬盘信息——修改
     */
    private EBSInstanceInfo ebsInfoEdit;
    
    /**
     * vlan信息
     */
    private VlanInstanceInfo vlanInfo;
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
     * 公网ip
     */
    private PublicIpInfo publicIpInfo;
    
    /**
     * 负载均衡FileStoreInfo
     */
    private LoadBalanceInfo loadbalanceInfo;   
    
    /**
     * 分布式文件存储
     */
    private FileStoreInfo fileStoreInfo; 
    
    /**
     * 虚拟防火墙
     */
    private VfirewallInfo vfireWallInfo; 		

	/**
     * 订单审批记录表实体
     */
    private OrderAuditInfo orderAuditInfo;

    /**
     * 返回路径，用于在界面判断是否系统错误
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        try {
        	String resPoolPartName = "";
            orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryOrderDetailInfo", orderInfo);
            if("0".equals(caseType)){
                vmInfo = (VMInstanceInfo)ibatisDAO.getSingleRecord("queryVmInstanceInfo", orderId);
               if(vmInfo.getPmId()!=null){
            	  pmName=(String) ibatisDAO.getSingleRecord("queryPmNameByPmId", vmInfo.getPmId());
               }
               if(vmInfo.getVmPassword()!=null){
               	vmInfo.setVmPassword("********");
			   }
                List<NetInfo> netList = new ArrayList<NetInfo>();
                if("7".equals(orderInfo.getStatus())){//修改信息
                    //查询修改信息最大时间戳
                    String maxTime = (String)ibatisDAO.getSingleRecord("getMaxTime", vmInfo.getVmId());
                    vmInfo.setMaxTime(maxTime);
                    vmInfoEdit = (VMInstanceInfo)ibatisDAO.getSingleRecord("queryVmInstanceInfoEdit", vmInfo);//如果只修改了网卡信息则vmInfoEdit为空
                    netList = ibatisDAO.getData("getNetByCaseIdEditForOrder", vmInfo);
                }else{
                    netList = ibatisDAO.getData("getNetByCaseIdForOrder", vmInfo.getCaseId());
                }
                vmInfo.setNetList(netList);
                resPoolPartName = vmInfo.getResPoolPartName();
            }else if("1".equals(caseType)){
            	pmInfo = (PMInstanceInfo)ibatisDAO.getSingleRecord("queryPmInstanceInfo", orderId);
            	List<NetInfo> netList = new ArrayList<NetInfo>();
            	if("7".equals(orderInfo.getStatus())){//修改信息
            		//查询修改信息最大时间戳
            		String maxTime = (String)ibatisDAO.getSingleRecord("getMaxTimePm", pmInfo.getPmId());
            		pmInfo.setMaxTime(maxTime);
                	pmInfoEdit = (PMInstanceInfo)ibatisDAO.getSingleRecord("queryPmInstanceInfoEdit", pmInfo);//如果只修改了网卡信息则vmInfoEdit为空
                	netList = ibatisDAO.getData("getNetByCaseIdEditForOrderPm", pmInfo);
                }else{
                	netList = ibatisDAO.getData("getNetByCaseIdForOrderPm", pmInfo.getCaseId());
                }
            	pmInfo.setNetList(netList);
                resPoolPartName = pmInfo.getResPoolPartName();
            }else if("4".equals(caseType)){
            	bkInfo = (VmBakInstanceInfo)ibatisDAO.getSingleRecord("queryBkInstanceInfo", orderId);
            	resPoolPartName = bkInfo.getResPoolPartName();
            }else if("5".equals(caseType)){
            	ebsInfo = (EBSInstanceInfo)ibatisDAO.getSingleRecord("queryEBSInstanceInfo", orderId);
                if("7".equals(orderInfo.getStatus())){//修改信息
                	ebsInfoEdit = (EBSInstanceInfo)ibatisDAO.getSingleRecord("queryEBSInstanceInfoEdit", ebsInfo.getEbsId());
                }
                resPoolPartName = ebsInfo.getResPoolPartName();
            }else if("12".equals(caseType)){
            	pipInfo = (PipInstanceInfo)ibatisDAO.getSingleRecord("queryPipInstanceInfo", orderId);
            	resPoolPartName = pipInfo.getResPoolPartName();
            }else if("13".equals(caseType)){
            	vlanInfo = (VlanInstanceInfo)ibatisDAO.getSingleRecord("queryVlanInstanceInfo", orderId);
            	resPoolPartName = vlanInfo.getResPoolPartName();
            }else if("7".equals(caseType)){
                publicIpInfo = (PublicIpInfo)ibatisDAO.getSingleRecord("queryPublicIpInstanceInfo", orderId);
                resPoolPartName = publicIpInfo.getResPoolPartName();
            }
            else if("14".equals(caseType)){
            	loadbalanceInfo = (LoadBalanceInfo)ibatisDAO.getSingleRecord("queryLoadBalanceInstanceInfo", orderId);
                resPoolPartName = loadbalanceInfo.getRespoolPartname();
            }
            else if("15".equals(caseType)){  
            	fileStoreInfo = (FileStoreInfo)ibatisDAO.getSingleRecord("queryFileStoreInstanceInfo", orderId);
                resPoolPartName = fileStoreInfo.getResPoolPartName();
            }
            else if("16".equals(caseType)){  
            	vfireWallInfo = (VfirewallInfo)ibatisDAO.getSingleRecord("queryVFireWallInstanceInfo", orderId);
                resPoolPartName = vfireWallInfo.getResPoolPartName();
            }
            if(resPoolPartName!=null){
            	orderInfo.setResPoolPartName(resPoolPartName);
            }
            orderAuditInfo = (OrderAuditInfo) ibatisDAO.getSingleRecord("queryOrderAuditInfo",orderId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单编号为" + orderId
                    + "的详细信息时失败，数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询订单编号为" + orderId
                    + "的详细信息时失败，数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        if (orderInfo.getEffectiveTime() != null) {
            orderInfo.setEffectiveTime(DateParse.parse(orderInfo.getEffectiveTime()));
        }
        if (orderInfo.getExpireTime() != null) {
            orderInfo.setExpireTime(DateParse.parse(orderInfo.getExpireTime()));
        }
        if (orderInfo.getUpdateTime() != null) {
            orderInfo.setUpdateTime(DateParse.parse(orderInfo.getUpdateTime()));
        }
        orderInfo.setCreateTime(DateParse.parse(orderInfo.getCreateTime()));
        
        if (vmInfo != null && vmInfo.getCreateTime() != null ) {
        	vmInfo.setCreateTime(DateParse.parse(vmInfo.getCreateTime()));
		}
        if (vmInfo != null && vmInfo.getExpireTime() != null ) {
			vmInfo.setExpireTime(DateParse.parse(vmInfo.getExpireTime()));
		}
        if (bkInfo != null && bkInfo.getBackupStartTime() != null) {
    		bkInfo.setBackupStartTime(DateParse.parse(bkInfo.getBackupStartTime()));
        }
        if (orderAuditInfo != null && orderAuditInfo.getAuditTime() != null) {
        	orderAuditInfo.setAuditTime(DateParse.parse(orderAuditInfo.getAuditTime()));
        }
        return ConstantEnum.SUCCESS.toString();
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
	 * @param ebsInfo the ebsInfo to set
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
	 * @param ebsInfoEdit the ebsInfoEdit to set
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
	 * @param caseType the caseType to set
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
	 * @param vlanInfo the vlanInfo to set
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
	 * @param pipInfo the pipInfo to set
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
	 * @param bkInfo the bkInfo to set
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
	 * @param vmInfo the vmInfo to set
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
	 * @param vmInfoEdit the vmInfoEdit to set
	 */
	public void setVmInfoEdit(VMInstanceInfo vmInfoEdit) {
		this.vmInfoEdit = vmInfoEdit;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @param orderAuditInfo the orderAuditInfo to set
	 */
	public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
		this.orderAuditInfo = orderAuditInfo;
	}

	/**
	 * @param resultPath the resultPath to set
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
	 * @param pmInfo the pmInfo to set
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
	 * @param pmInfoEdit the pmInfoEdit to set
	 */
	public void setPmInfoEdit(PMInstanceInfo pmInfoEdit) {
		this.pmInfoEdit = pmInfoEdit;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

    public PublicIpInfo getPublicIpInfo() {
        return publicIpInfo;
    }

    public void setPublicIpInfo(PublicIpInfo publicIpInfo) {
        this.publicIpInfo = publicIpInfo;
    }
    public LoadBalanceInfo getLoadbalanceInfo() {
		return loadbalanceInfo;
	}

	public void setLoadbalanceInfo(LoadBalanceInfo loadbalanceInfo) {
		this.loadbalanceInfo = loadbalanceInfo;
	}
	
	public FileStoreInfo getFileStoreInfo() {
		return fileStoreInfo;
	}

	public void setFileStoreInfo(FileStoreInfo fileStoreInfo) {
		this.fileStoreInfo = fileStoreInfo;
	}
	
	public VfirewallInfo getVfireWallInfo() {
		return vfireWallInfo;
	}

	public void setVfireWallInfo(VfirewallInfo vfireWallInfo) {
		this.vfireWallInfo = vfireWallInfo;
	}

}
