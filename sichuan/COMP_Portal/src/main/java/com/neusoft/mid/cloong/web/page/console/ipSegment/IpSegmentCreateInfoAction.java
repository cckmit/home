package com.neusoft.mid.cloong.web.page.console.ipSegment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.common.util.ip.IpSegmentUtil;
import com.neusoft.mid.cloong.common.util.ip.Ipv6Util;
import com.neusoft.mid.cloong.common.util.ip.MaskErrorException;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.ipSegment.core.ApplyIpSegment;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPProType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyResp;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请IP段后入库
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月10日 下午4:18:12
 */
public class IpSegmentCreateInfoAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -7548018976455833702L;

    private static final LogService logger = LogService.getLogger(IpSegmentCreateInfoAction.class);

    /**
     * 资源池ID
     */
    private String respoolId;

    /**
     * 资源池分区ID
     */
    //private String respoolPartId;

    /**
     * IP段子网
     */
    private String subnet;

    /**
     * IP段唯一标识
     */
    private String ipSegmentURI;

    /**
     * IP段描述
     */
    private String ipSegmentDesc;

    /**
     * IP段子网
     */
    private String ipSubnet;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * 订单,实例ID生成器
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * 创建IP段接口
     */
    private ApplyIpSegment applyIpSegmentImpl;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit;

    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";

    /**
     * IP的地址类型：IPV4, IPV6
     */
    private String ipType;
    
    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    @SuppressWarnings("deprecation")
    public String execute() {

        logger.info("创建IP段Action开始");

        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();

        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.IP.getParentCode());
        String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.IP.toString());
        String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.IP.toString());
        // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        OrderInfo orderInfo = assembleOrder(userId, orderId, caseId, parentId);

        RPPIPApplyResp resp = new RPPIPApplyResp();
        resp.setResultCode(SUCCEESS_CODE);
        if (AUTO.equals(audit)) {
            // 自动审批
            // 发送申请订单至接口
            RPPIPApplyReq req = setProperties4Req();

            resp = applyIpSegmentImpl.apply(req);
        }

        if (null != resp.getResultCode() && SUCCEESS_CODE.equals(resp.getResultCode())) {
            // 拼凑实例信息
            try {
                /* 如果是自动审批,说明这个resp已经重新赋过值,说明调用接口成功,此时需要生效时间 */
                if (AUTO.equals(audit))
                    orderInfo.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createEBSOrder", orderInfo));

                // 拼凑IP段实例信息
                IpSegmentInfo info = new IpSegmentInfo();
                if (AUTO.equals(audit)) {
                    assembleEBSInstance(userId, orderInfo, resp, info);
                } else {
                    assembleEBSInstanceAudit(userId, orderInfo, info);
                }

                // 更新数据库
                updateBatchVO.add(new BatchVO("ADD", "createIpSegmentInstanceInfo", info));
            } catch (MaskErrorException e1) {
                // 掩码位数异常
                logger.error("掩码位数异常", e1);
                this.addActionError(getText("ipSegment.applyinfo.fail"));
                errMsg = getText("ipSegment.applyinfo.fail");
                return ConstantEnum.ERROR.toString();
            } catch (Exception e2) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(getText("ipSegment.applyinfo.fail"), e2);
                this.addActionError(getText("ipSegment.applyinfo.fail"));
                errMsg = getText("ipSegment.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }

            try {
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("申请ipSegment订单记入数据库成功！");
                }
            } catch (SQLException e) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(getText("ipSegment.applyinfo.fail"), e);
                this.addActionError(getText("ipSegment.applyinfo.fail"));
                errMsg = getText("ipSegment.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            } catch (Exception e2) {
                // 如果记入数据库失败，打印error日志，记录云硬盘订单发生异常，返回浏览云硬盘列表页面，提示“提交申请云硬盘订单失败！”。
                logger.error(getText("ipSegment.applyinfo.fail"), e2);
                this.addActionError(getText("ipSegment.applyinfo.fail"));
                errMsg = getText("ipSegment.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
        } else {
            // 返回失败
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建ipSegment发送申请失败！");
            this.addActionError(getText("ipSegment.applyinfo.sendcreateEBS.fail"));
            errMsg = getText("ipSegment.applyinfo.sendcreateEBS.fail");
            return ConstantEnum.FAILURE.toString();
        }

        if (AUTO.equals(audit)) {
            msg = "IP段申请成功";
        } else {
            msg = "IP段申请已提交,请等待审核！";
        }

        logger.info("创建IP段Action完成");

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * setProperties4Req 对请求对象进行封装
     * @return 封装后的REQ对象
     */
    private RPPIPApplyReq setProperties4Req() {

        RPPIPApplyReq req = new RPPIPApplyReq();

        req.setAppId(appId);
        req.setAppName(appName);
        req.setCreateModel(IPCreateModel.CustomModel);
        req.setIpSegmentURI(ipSegmentURI);
        req.setResourcePoolId(respoolId);
        //req.setResourcePoolPartId(respoolPartId);
        req.setSerialNum(seqCen.generatorSerialNum());
        req.setCount(1);
        req.setIpProType(IPProType.IPV4);
        req.setIpType(IPType.PRIVATE_IP);

        return req;
    }

    /**
     * assembleEBSInstanceAudit 手动审核时,bean的赋值
     * @param userId
     * @param orderInfo
     * @param info
     * @throws MaskErrorException 掩码位数不对的时候会抛出异常
     */
    private void assembleEBSInstanceAudit(String userId, OrderInfo orderInfo, IpSegmentInfo info)
            throws MaskErrorException {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        List<String> ips = new ArrayList<String>();
        if ("0".equals(ipType)) { // IPV4
        	 ips = IpSegmentUtil.getExtremeIp(ipSubnet);
        	 info.setIpTotal(IpSegmentUtil.getIpSegmentSize(ips.get(0), ips.get(1)).toString());
        	 info.setIpType("0");
        }
        if ("1".equals(ipType)) { // IPV6
        	ips = Ipv6Util.getIpv6ExtremeIp(ipSubnet);
        	info.setIpTotal(Ipv6Util.getIpv6Size(ipSubnet.split("/")[1]));
        	info.setIpType("1");
        }
        info.setAppId(appId);
        info.setAppName(appName);
        info.setCaseId(orderInfo.getCaseId());
        info.setOrderId(orderInfo.getOrderId());
        info.setIpSegmentId(ipSegmentURI);
        info.setResPoolId(respoolId);
        //info.setResPoolPartId(respoolPartId);
        info.setCreateTime(time);
        info.setCreateUser(userId);
        info.setIpSegmentDesc(ipSegmentDesc);
        info.setIpSubnet(ipSubnet);
        info.setStartIp(ips.get(0));
        info.setEndIp(ips.get(1));
        
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(2);
        info.setReleased(tmp);

    }

    /**
     * assembleEBSInstance 自动审核时,bean的赋值
     * @param userId
     * @param orderInfo
     * @param resp
     * @param info
     * @throws MaskErrorException 掩码位数不对的时候会抛出异常
     */
    private void assembleEBSInstance(String userId, OrderInfo orderInfo, RPPIPApplyResp resp,
            IpSegmentInfo info) throws MaskErrorException {
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        List<String> ips = new ArrayList<String>();
        if ("0".equals(ipType)) {
        	 ips = IpSegmentUtil.getExtremeIp(resp.getIpSubNet());
        }
        if ("1".equals(ipType)) {
        	ips = Ipv6Util.getIpv6ExtremeIp(resp.getIpSubNet());
        }

        info.setAppId(appId);
        info.setAppName(appName);
        info.setCaseId(orderInfo.getCaseId());
        info.setOrderId(orderInfo.getOrderId());
        info.setResPoolId(respoolId);
        //info.setResPoolPartId(respoolPartId);
        info.setCreateTime(time);
        info.setCreateUser(userId);
        info.setIpSegmentId(resp.getIpSegmentURI());
        info.setIpSegmentDesc(ipSegmentDesc);
        info.setIpSubnet(resp.getIpSubNet());
        info.setStartIp(ips.get(0));
        info.setEndIp(ips.get(1));
        info.setIpTotal(IpSegmentUtil.getIpSegmentSize(ips.get(0), ips.get(1)).toString());
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        info.setReleased(tmp);

    }

    private OrderInfo assembleOrder(String userId, String orderId, String caseId, String parentId) {
        OrderInfo orderInfo = new OrderInfo();
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
        orderInfo.setParentId(parentId);
        orderInfo.setCaseType("12");
        orderInfo.setAppId(appId);
        orderInfo.setResPoolId(respoolId);
        return orderInfo;
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

//    /**
//     * 获取respoolPartId字段数据
//     * @return Returns the respoolPartId.
//     */
//    public String getRespoolPartId() {
//        return respoolPartId;
//    }
//
//    /**
//     * 设置respoolPartId字段数据
//     * @param respoolPartId The respoolPartId to set.
//     */
//    public void setRespoolPartId(String respoolPartId) {
//        this.respoolPartId = respoolPartId;
//    }

    /**
     * 获取subnet字段数据
     * @return Returns the subnet.
     */
    public String getSubnet() {
        return subnet;
    }

    /**
     * 设置subnet字段数据
     * @param subnet The subnet to set.
     */
    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    /**
     * 获取ipSegmentURI字段数据
     * @return Returns the ipSegmentURI.
     */
    public String getIpSegmentURI() {
        return ipSegmentURI;
    }

    /**
     * 设置ipSegmentURI字段数据
     * @param ipSegmentURI The ipSegmentURI to set.
     */
    public void setIpSegmentURI(String ipSegmentURI) {
        this.ipSegmentURI = ipSegmentURI;
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
     * 获取applyIpSegmentImpl字段数据
     * @return Returns the applyIpSegmentImpl.
     */
    public ApplyIpSegment getApplyIpSegmentImpl() {
        return applyIpSegmentImpl;
    }

    /**
     * 设置applyIpSegmentImpl字段数据
     * @param applyIpSegmentImpl The applyIpSegmentImpl to set.
     */
    public void setApplyIpSegmentImpl(ApplyIpSegment applyIpSegmentImpl) {
        this.applyIpSegmentImpl = applyIpSegmentImpl;
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
     * 获取succeessCode字段数据
     * @return Returns the succeessCode.
     */
    public static String getSucceessCode() {
        return SUCCEESS_CODE;
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

    /**
     * 获取ipSegmentDesc字段数据
     * @return Returns the ipSegmentDesc.
     */
    public String getIpSegmentDesc() {
        return ipSegmentDesc;
    }

    /**
     * 设置ipSegmentDesc字段数据
     * @param ipSegmentDesc The ipSegmentDesc to set.
     */
    public void setIpSegmentDesc(String ipSegmentDesc) {
        this.ipSegmentDesc = ipSegmentDesc;
    }

    /**
     * 获取ipSubnet字段数据
     * @return Returns the ipSubnet.
     */
    public String getIpSubnet() {
        return ipSubnet;
    }

    /**
     * 设置ipSubnet字段数据
     * @param ipSubnet The ipSubnet to set.
     */
    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
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
    
}
