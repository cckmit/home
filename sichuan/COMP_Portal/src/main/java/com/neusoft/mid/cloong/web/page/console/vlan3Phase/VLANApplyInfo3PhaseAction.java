package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.VlanType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.ZoneType;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.vlan3Phase.core.VLANCreate;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.VlanStatusCode;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * vlan申请action
 */
public class VLANApplyInfo3PhaseAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -3246558237673423698L;

    private static LogService logger = LogService.getLogger(VLANApplyInfo3PhaseAction.class);

    private String respoolId;
    //创建vlan需要跨分区  2016/05/09
    //private String respoolPartId;

    private String respoolName;
    //创建vlan需要跨分区  2016/05/09
    //private String respoolPartName;

    private String vlanName;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;
    
    /**
     * 起始Vlan_ID
     */
    private String startId;
    
    /**
     * 终止Vlan_ID
     */
    private String endId;

    private SequenceGenerator sequenceGenerator;

    /**
     * vlan创建接口
     */
    private VLANCreate vlanCreate;

    /**
     * 成功的接口响应码
     */
    private final String SUCCEESS_CODE = "00000000";

    /**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit;

    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;
    
    private String reStartId;
    
    private String reEndId;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {   	
        if (logger.isDebugEnable()) {
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolName = " + respoolName);
            logger.debug("appId = " + appId);
            logger.debug("startId = " + startId);
            logger.debug("endId = " + endId);
        }    
        logger.info("申请VLAN范围重复校验开始！");
        List<VlanInfo> range = new ArrayList<VlanInfo>();
	    List<VlanInfo> rangeUpdate = new ArrayList<VlanInfo>();
        VlanInfo vlan = new VlanInfo();
        try{
        	range = ibatisDAO.getData("vlanRange3Phase", vlan);
        	rangeUpdate = ibatisDAO.getData("vlanRangeUpdate", vlan);
        } catch (Exception e) {
            logger.error(VlanStatusCode.createVlanOrderException,
                    getText("vlan3Phase.applyinfo.fail"), e);
            this.addActionError(getText("vlan3Phase.applyinfo.fail"));
            errMsg = getText("vlan3Phase.applyinfo.fail");
            return ConstantEnum.FAILURE.toString();
        }
    	if(validation(range,rangeUpdate)){
            logger.error(VlanStatusCode.createVlanOrderException,
                    getText("vlan3Phase.rangeList.fail"));
            errMsg = getText("已存在范围为"+reStartId+"-"+reEndId+"的Vlan，请重新申请！");
            return ConstantEnum.FAILURE.toString();
    	}
        logger.info("申请VLAN范围重复校验结束！");
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
//        RPPVlanApplyReq ebsReq = new RPPVlanApplyReq();

        // 拼凑订单信息
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        OrderInfo orderInfo = new OrderInfo();
        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.VT.getParentCode());
        String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.VT.toString());
        String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VT.toString());
        // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
        assembleOrder(userId, orderInfo, orderId, caseId);
        orderInfo.setParentId(parentId);// 同一个订单父ID
        if (logger.isDebugEnable()) {
            logger.debug("orderId = " + orderId);
            logger.debug("caseId = " + caseId);
            logger.debug("parentId = " + parentId);
        }
        try{
            // 如果是自动审批,说明这个resp已经重新赋过值,说明调用接口成功,此时需要生效时间 
            if (AUTO.equals(audit))
                orderInfo.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            // 更新数据库
            updateBatchVO.add(new BatchVO("ADD", "createEBSOrder", orderInfo));            
            // 拼凑EBS实例信息
            VlanInfo vlanInfo = new VlanInfo();
            assembleEBSInstanceAudit(userId, orderInfo, vlanInfo);           
            // 更新数据库
            updateBatchVO.add(new BatchVO("ADD", "createVLANInstanceInfo3Phase", vlanInfo));
            if (logger.isDebugEnable()) {
                logger.debug("拼凑订单信息完成！");
            }
        } catch (Exception e2) {
            // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
            logger.error(VlanStatusCode.createVlanOrderException,
                    getText("vlan3Phase.applyinfo.fail"), e2);
            this.addActionError(getText("vlan3Phase.applyinfo.fail"));
            errMsg = getText("vlan3Phase.applyinfo.fail");
            return ConstantEnum.FAILURE.toString();
        }        
        try {
            ibatisDAO.updateBatchData(updateBatchVO);
            if (logger.isDebugEnable()) {
                logger.debug("申请vlan订单记入数据库成功！");
            }
        } catch (SQLException e) {
            // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
            logger.error(VlanStatusCode.createVlanOrderException,
                    getText("ebs.applyinfo.fail"), e);
            this.addActionError(getText("vlan3Phase.applyinfo.fail"));
            errMsg = getText("vlan3Phase.applyinfo.fail");
            return ConstantEnum.FAILURE.toString();
        } catch (Exception e2) {
            // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
            logger.error(VlanStatusCode.createVlanOrderException,
                    getText("vlan3Phase.applyinfo.fail"), e2);
            this.addActionError(getText("vlan3Phase.applyinfo.fail"));
            errMsg = getText("vlan3Phase.applyinfo.fail");
            return ConstantEnum.FAILURE.toString();
        }               
      /*  
        RPPVlanApplyResp resp = new RPPVlanApplyResp();
        resp.setResultCode(SUCCEESS_CODE);
        if (AUTO.equals(audit)) {
            // 自动审批
            // 发送申请订单至接口
            assembleReq(ebsReq);
            resp = vlanCreate.create(ebsReq);
        }  
        
         if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，云硬盘订单和实例入库

            // 拼凑实例信息
            try {
                // 如果是自动审批,说明这个resp已经重新赋过值,说明调用接口成功,此时需要生效时间 
                if (AUTO.equals(audit))
                    orderInfo.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createEBSOrder", orderInfo));

                // 拼凑EBS实例信息
                VlanInfo vlanInfo = new VlanInfo();
                if (AUTO.equals(audit)) {
                    assembleEBSInstance(userId, orderInfo, resp, vlanInfo);
                } else {
                    assembleEBSInstanceAudit(userId, orderInfo, vlanInfo);
                }

                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createVLANInstanceInfo3Phase", vlanInfo));
                if (logger.isDebugEnable()) {
                    logger.debug("拼凑订单信息完成！");
                }
            } catch (Exception e2) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(VlanStatusCode.createVlanOrderException,
                        getText("vlan3Phase.applyinfo.fail"), e2);
                this.addActionError(getText("vlan3Phase.applyinfo.fail"));
                errMsg = getText("vlan3Phase.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
            try {
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("申请vlan订单记入数据库成功！");
                }
            } catch (SQLException e) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(VlanStatusCode.createVlanOrderException,
                        getText("ebs.applyinfo.fail"), e);
                this.addActionError(getText("vlan3Phase.applyinfo.fail"));
                errMsg = getText("vlan3Phase.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            } catch (Exception e2) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(VlanStatusCode.createVlanOrderException,
                        getText("vlan3Phase.applyinfo.fail"), e2);
                this.addActionError(getText("vlan3Phase.applyinfo.fail"));
                errMsg = getText("vlan3Phase.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建vlan发送申请失败！");
            this.addActionError(getText("vlan3Phase.applyinfo.sendcreateEBS.fail"));
            errMsg = getText("vlan3Phase.applyinfo.sendcreateEBS.fail");
            return ConstantEnum.FAILURE.toString();
        }  
    */       
        if (logger.isDebugEnable()) {
            logger.debug("创建云硬盘发送申请成功！");
        }
        if (AUTO.equals(audit)) {
            msg = "Vlan申请成功";
        } else {
            msg = "Vlan申请已提交,请等待审核！";
        }
        return ConstantEnum.SUCCESS.toString();
    }

    private void assembleReq(RPPVlanApplyReq vlanReq) {
        vlanReq.setResourcePoolId(respoolId);
        vlanReq.setCount(1);
        vlanReq.setAppId(appId);
        vlanReq.setAppName(appName);
        vlanReq.setSerialNum(seqCen.generatorSerialNum());
        vlanReq.setVlanType(VlanType.Business);
        vlanReq.setZoneType(ZoneType.DMZ_ZONE);
    }

    private void assembleEBSInstance(String userId, OrderInfo orderInfo, RPPVlanApplyResp resp,
            VlanInfo vlanInfo) {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        vlanInfo.setCaseId(orderInfo.getCaseId());
        vlanInfo.setVlanId(resp.getVlanIdList().get(0));
        vlanInfo.setVlanName(vlanName);
        vlanInfo.setResPoolId(respoolId);
        vlanInfo.setCreateTime(time);
        vlanInfo.setCreateUser(userId);
        vlanInfo.setOrderId(orderInfo.getOrderId());
        vlanInfo.setAppId(appId);
        vlanInfo.setAppName(appName);
        vlanInfo.setStartId(Integer.parseInt(startId));
        vlanInfo.setEndId(Integer.parseInt(endId));
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        vlanInfo.setCanceled(tmp);
    }

    /**
     * 为人工审批时做准备，为保存资源池id资源池分区id
     * @param userId 用户id
     * @param orderInfo 订单信息
     * @param vlanInfo ebs实例
     */
    private void assembleEBSInstanceAudit(String userId, OrderInfo orderInfo, VlanInfo vlanInfo) {
//      String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        vlanInfo.setCaseId(orderInfo.getCaseId());
        vlanInfo.setVlanName(vlanName);
        vlanInfo.setResPoolId(respoolId);
//      vlanInfo.setResPoolPartId(respoolPartId);
//      vlanInfo.setCreateTime(time);
//      vlanInfo.setCreateUser(userId);
        vlanInfo.setOrderId(orderInfo.getOrderId());
        vlanInfo.setAppId(appId);
        vlanInfo.setAppName(appName);
        vlanInfo.setStartId(Integer.parseInt(startId));
        vlanInfo.setEndId(Integer.parseInt(endId));
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(2);
        vlanInfo.setCanceled(tmp);
    }

    private void assembleOrder(String userId, OrderInfo orderInfo, String orderId, String caseId) {
        orderInfo.setOrderId(orderId);
        if (AUTO.equals(audit)) {
            orderInfo.setStatus("3");
        } else {
            orderInfo.setStatus("0");
        }
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);
        orderInfo.setCaseId(caseId);
        orderInfo.setAppId(appId);
        orderInfo.setResPoolId(respoolId);
        orderInfo.setCaseType("17");
    }
    
//    申请vlan校验
    private boolean validation(List<VlanInfo> range,List<VlanInfo> rangeUpdate ){  
    	int rangeStart = Integer.parseInt(startId);
        int rangeEnd = Integer.parseInt(endId);
        if(!"0".equals(String.valueOf(range.size()))){
        	for (VlanInfo it : range){
            	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
            		reStartId = String.valueOf(it.getStartId());
            		reEndId = String.valueOf(it.getEndId());
            		return true;
            	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
            		reStartId = String.valueOf(it.getStartId());
            		reEndId = String.valueOf(it.getEndId());
            		return true;
            	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
            		reStartId = String.valueOf(it.getStartId());
            		reEndId = String.valueOf(it.getEndId());
            		return true;
            	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
            		reStartId = String.valueOf(it.getStartId());
            		reEndId = String.valueOf(it.getEndId());
            		return true;
            	}
        	}
        }else if(!"0".equals(String.valueOf(rangeUpdate.size()))){
        	for (VlanInfo it : rangeUpdate){
	        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}        		
        	}	
        }           	
    	return false;
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

    public String getRespoolName() {
        return respoolName;
    }

    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    /**
     * getAudit 是否人工审批
     * @return 是否人工审批
     */
    public String getAudit() {
        return audit;
    }

    /**
     * setAudit 是否人工审批
     * @param audit 是否人工审批
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取seqCen字段数据
     * @return Returns the seqCen.
     */
    public CommonSequenceGenerator getSeqCen() {
        return seqCen;
    }

    /**
     * 设置seqCen字段数据
     * @param seqCen The seqCen to set.
     */
    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }
       

    public String getStartId() {
		return startId;
	}

	public void setStartId(String startId) {
		this.startId = startId;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	/**
     * 获取vlanName字段数据
     * @return Returns the vlanName.
     */
    public String getVlanName() {
        return vlanName;
    }

    /**
     * 设置vlanName字段数据
     * @param vlanName The vlanName to set.
     */
    public void setVlanName(String vlanName) {
        this.vlanName = vlanName;
    }

    /**
     * 获取vlanCreate字段数据
     * @return Returns the vlanCreate.
     */
    public VLANCreate getVlanCreate() {
        return vlanCreate;
    }

    /**
     * 设置vlanCreate字段数据
     * @param vlanCreate The vlanCreate to set.
     */
    public void setVlanCreate(VLANCreate vlanCreate) {
        this.vlanCreate = vlanCreate;
    }

	public String getReStartId() {
		return reStartId;
	}

	public void setReStartId(String reStartId) {
		this.reStartId = reStartId;
	}

	public String getReEndId() {
		return reEndId;
	}

	public void setReEndId(String reEndId) {
		this.reEndId = reEndId;
	}

}
