package com.neusoft.mid.comp.boss.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.VlanType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.ZoneType;
import com.neusoft.mid.comp.boss.dao.OrderDao;
import com.neusoft.mid.comp.boss.dao.ResPoolDao;
import com.neusoft.mid.comp.boss.dao.VlanDao;
import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.mybatis.bean.CompCaseVlanT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompOrderT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompResPoolT;
import com.neusoft.mid.comp.boss.server.ws.msg.CorporationInfo;
import com.neusoft.mid.comp.boss.util.CommonSequenceGenerator;
import com.neusoft.mid.comp.boss.util.CommonStatusCode;
import com.neusoft.mid.comp.boss.util.DateParse;
import com.neusoft.mid.comp.boss.util.ResourceTypeEnum;
import com.neusoft.mid.comp.boss.util.SequenceGenerator;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;

@Service
public class VlanService {

	@Autowired
	private  ResPoolDao resPoolDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private VlanDao vlanDao;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private CommonSequenceGenerator seqCen;
	
	@Autowired
	private HttpSyncSendHelper senderEntry;
		
	@Value("${resourcePoolProxyCreateVlanUrl}")
    private String url;
	
   
	/**
     * 接收超时时间
     */
    private int receiveTimeout = 5000;

    /**
     * 协议超时时间
     */
    private int httpTimeOut = 50;
	
	private Logger logger = LoggerFactory.getLogger(VlanService.class);
	
	/**
     * 成功的接口响应码
     */
    private final String SUCCEESS_CODE = "00000000";
    
	public void makeVlan(CorporationInfo corporationInfo) throws DbException {
		CompResPoolT compResPool = resPoolDao.selectResPool();

        // 拼凑订单信息,同一个订单父ID
        CompOrderT compOrderT = makeOrderInfo(corporationInfo, compResPool);

        // 自动审批,发送申请订单至接口
        RPPVlanApplyReq vlanReq = makeVlanApplyReq(corporationInfo, compResPool);  
        logger.info("Prepare to send vlan apply request to ziyuanchi!");
        RPPVlanApplyResp resp =  new RPPVlanApplyResp();
        resp = createVlan(vlanReq); // TODO 
//        resp.setResultCode("00000000");  // TODO 
        
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            /* 自动审批,说明这个resp已经重新赋过值,说明调用接口成功,此时需要生效时间 */
        	compOrderT.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        	try {
        		orderDao.insertOrder(compOrderT);
        		logger.info("insert vlan order data successful!");
                // 拼凑vlan实例信息
                CompCaseVlanT compCaseVlanT = makeVlanInfo(corporationInfo, compResPool, compOrderT, resp);    
                vlanDao.insertVlan(compCaseVlanT);
                logger.info("insert vlan resource data successful!");
        	} catch (Exception e) {
        		String failStr = " create vlan's order & vlan resource data failed!" + e.getMessage();
	            throw new DbException(failStr, e);
	        }
            
        } else {
        	logger.error("创建vlan资源出错，错误码：" + resp.getResultCode() + ", 错误信息：" + resp.getFaultstring());
        	// 由于运营不能通过资源池自动创建vlan和ip段，所以每次boss同步过来的请求可能并不能自动创建vlan和ip段，
        	// 所以这种情况发生时，让企业信息、boss订单信息入库，vlan和ip段相关信息就无法正常创建了。即这种情况发生时不做全流程回滚。即 此处不做throw new BossException(""); 因为向上抛异常整个流程就数据回滚了
        }

    }

	private CompOrderT makeOrderInfo(CorporationInfo corporationInfo, CompResPoolT compResPool) {
		String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.VL.getParentCode());
        String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.VL.toString());
        String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.VL.toString());
        CompOrderT compOrderT = new CompOrderT();
        compOrderT.setOrderId(orderId);
        compOrderT.setStatus("3"); // 自动审批通过生效
        compOrderT.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        compOrderT.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        compOrderT.setCreateUser("BOSS");
        compOrderT.setUpdateUser("BOSS");
        compOrderT.setCaseId(caseId);
        compOrderT.setAppId(corporationInfo.getCorpId());
        compOrderT.setResPoolId(compResPool.getResPoolId());
        compOrderT.setCaseType("13");
        compOrderT.setParentId(parentId);// 同一个订单父ID
		return compOrderT;
	}
	
	private RPPVlanApplyReq makeVlanApplyReq(CorporationInfo corporationInfo, CompResPoolT compResPool) {
		RPPVlanApplyReq vlanReq = new RPPVlanApplyReq();
        vlanReq.setResourcePoolId(compResPool.getResPoolId());
        vlanReq.setCount(1); // 只创建一个
        vlanReq.setAppId(corporationInfo.getCorpId());
        vlanReq.setAppName(corporationInfo.getCorpName());
        vlanReq.setSerialNum(seqCen.generatorSerialNum());
        vlanReq.setVlanType(VlanType.Business);
        vlanReq.setZoneType(ZoneType.DMZ_ZONE);
		return vlanReq;
	}
	
	private CompCaseVlanT makeVlanInfo(CorporationInfo corporationInfo, CompResPoolT compResPool, CompOrderT compOrderT,
			RPPVlanApplyResp resp) {
		CompCaseVlanT compCaseVlanT = new CompCaseVlanT();
		compCaseVlanT.setCaseId(compOrderT.getCaseId());
		compCaseVlanT.setVlanId(resp.getVlanIdList().get(0)); // TODO
//		compCaseVlanT.setVlanId("1000");  // TODO
		compCaseVlanT.setVlanName("VLAN" + corporationInfo.getCorpName());
		compCaseVlanT.setOrderId(compOrderT.getOrderId());
		compCaseVlanT.setAppId(corporationInfo.getCorpId());
		compCaseVlanT.setResPoolId(compResPool.getResPoolId());
		compCaseVlanT.setZoneType("0");
		compCaseVlanT.setVlanType("2");
		compCaseVlanT.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compCaseVlanT.setCreateUser("BOSS");
		compCaseVlanT.setCanceled("0");
		return compCaseVlanT;
	}
	
	public RPPVlanApplyResp createVlan(RPPVlanApplyReq vlanReq) {

        RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, vlanReq);

        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error("向资源池代理系统发送请求失败，IO错误", e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error("向资源池代理系统发送请求失败，无效的http协议", e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error("向资源池代理系统发送请求失败", e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }

        return (RPPVlanApplyResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
    }

	private RPPVlanApplyResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        RPPVlanApplyResp vlanResp = new RPPVlanApplyResp();
        vlanResp.setResultCode(code.toString());
        vlanResp.setResultMessage(errorMessage);
        return vlanResp;
    }
	
}
