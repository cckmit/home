package com.neusoft.mid.comp.boss.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPApplyState;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPProType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.IPType;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp.IPSegmentInfo;
import com.neusoft.mid.comp.boss.dao.IpsegmentDao;
import com.neusoft.mid.comp.boss.dao.OrderDao;
import com.neusoft.mid.comp.boss.dao.ResPoolDao;
import com.neusoft.mid.comp.boss.exception.BossException;
import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.mybatis.bean.CompCaseIpsegmentT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompOrderT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompResPoolT;
import com.neusoft.mid.comp.boss.server.ws.msg.CorporationInfo;
import com.neusoft.mid.comp.boss.util.CommonSequenceGenerator;
import com.neusoft.mid.comp.boss.util.CommonStatusCode;
import com.neusoft.mid.comp.boss.util.DateParse;
import com.neusoft.mid.comp.boss.util.IpSegmentUtil;
import com.neusoft.mid.comp.boss.util.ResourceTypeEnum;
import com.neusoft.mid.comp.boss.util.SequenceGenerator;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;

@Service
public class IpSegmentService {

	@Autowired
	private ResPoolDao resPoolDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private IpsegmentDao ipsegmentDao;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private CommonSequenceGenerator seqCen;
	
	@Autowired
	private HttpSyncSendHelper senderEntry;
	
	/**
	 * 成功的接口响应码
	 */
	private final String SUCCEESS_CODE = "00000000";

	/**
     * 接收超时时间
     */
    private int receiveTimeout = 5000;

    /**
     * 协议超时时间
     */
    private int httpTimeOut = 50;

    /**
     * 资源池代理系统URL
     */
    @Value("${resourcePoolProxyApplyIpSegmentUrl}")
    private String url;
    
    @Value("${resourcePoolProxyQueryIpSegmentUrl}")
    private String queryUrl;
	
	private Logger logger = LoggerFactory.getLogger(VlanService.class);

	public void makeIpSegment(CorporationInfo corporationInfo) throws BossException, DbException {
		CompResPoolT compResPool = resPoolDao.selectResPool();

		// 从资源池查找ip段并获取第一个
		List<CompCaseIpsegmentT> ipSegmentList = queryIpsegmentInfo(compResPool);  // TODO
        // 得到IPSegmentURI之后，指定这个IPSegmentURI到资源池创建ip段
		if (ipSegmentList.size() > 0) {
			String ipsegmentURI = ipSegmentList.get(0).getIpsegmentId();// TODO 
//			String ipsegmentURI = "8989889";  // TODO
	        
	        logger.info("select one ipsegmentURI from ziyuanchi! " + ipsegmentURI);
	        // 拼凑订单信息,同一个订单父ID
			CompOrderT compOrder = makeOrderInfo(corporationInfo, compResPool);
			// 自动审批, 发送申请订单至接口
			logger.info("Prepare to send IP segment apply request to ziyuanchi!");
			RPPIPApplyReq req = makeIPApplyReq(corporationInfo, compResPool, ipsegmentURI);
			RPPIPApplyResp resp = new RPPIPApplyResp();
			resp = createIpSegment(req);  // TODO
//			resp.setResultCode("00000000");  // TODO 

			if (SUCCEESS_CODE.equals(resp.getResultCode())) {
				/* 自动审批,说明这个resp已经重新赋过值,说明调用接口成功,此时需要生效时间 */
				compOrder.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
				try {
					// 更新数据库
		            orderDao.insertOrder(compOrder);
		            logger.info("insert ip segment order data successful!");
					// 拼凑IP段实例信息
					CompCaseIpsegmentT compCaseIpsegment = makeIpsegmentInfo(corporationInfo, compResPool, compOrder, resp);
					// 更新数据库
					ipsegmentDao.insertIpSegment(compCaseIpsegment);
					logger.info("insert ip segment resource data successful!");
				} catch (Exception e) {
		            throw new DbException("crate ip segment order & ip segment resource data failed!", e);
		        }
			} else {
				logger.error("从资源池创建IP段信息出错, " + resp.getResultCode() + ", " + resp.getFaultstring());
				// 由于运营不能通过资源池自动创建vlan和ip段，所以每次boss同步过来的请求可能并不能自动创建vlan和ip段，
	        	// 所以这种情况发生时，让企业信息、boss订单信息入库，vlan和ip段相关信息就无法正常创建了。即这种情况发生时不做全流程回滚。即 此处不做throw new BossException(""); 因为向上抛异常整个流程就数据回滚了
			}
		} else {
			logger.error("没有从资源池查询获取到IP段信息! ");
			// 由于运营不能通过资源池自动创建vlan和ip段，所以每次boss同步过来的请求可能并不能自动创建vlan和ip段，
        	// 所以这种情况发生时，让企业信息、boss订单信息入库，vlan和ip段相关信息就无法正常创建了。即这种情况发生时不做全流程回滚。即 此处不做throw new BossException(""); 因为向上抛异常整个流程就数据回滚了
		}
	}
	
	private List<CompCaseIpsegmentT> queryIpsegmentInfo(CompResPoolT compResPool) throws BossException {
		RPPIPQueryReq queryReq = new RPPIPQueryReq();
		queryReq.setResourcePoolId(compResPool.getResPoolId());
        RPPIPQueryResp queryResp = queryIpSegment(queryReq);
        List<CompCaseIpsegmentT> ipSegmentList = new ArrayList<CompCaseIpsegmentT>();
        List<CompCaseIpsegmentT> reportingList = ipsegmentDao.selectIpSubnet();

        if (null != queryResp && SUCCEESS_CODE.equals(queryResp.getResultCode())) {
            for (IPSegmentInfo rppInfo : queryResp.getIpSegmentInfoList()) {
                boolean flag = true;
                /* 过滤待审的IP段 */
                for (CompCaseIpsegmentT ipSegmentInfo : reportingList)
                    if (ipSegmentInfo.getIpSubnet().equals(rppInfo.getIpSubNet())) {
                        flag = false;
                        break;
                    }

                if (flag && IPApplyState.NO_APPLY.equals(rppInfo.getApplyState())) {
                	CompCaseIpsegmentT ipSegment = new CompCaseIpsegmentT();
                    ipSegment.setIpsegmentId(rppInfo.getIpSegmentURI());
                    String subnet = rppInfo.getIpSubNet();
                    String firstIp = "";
                    String lastIp = "";
                    try {
                        List<String> extreme = IpSegmentUtil.getExtremeIp(subnet);
                        firstIp = extreme.get(0);
                        lastIp = extreme.get(1);
                    } catch (Exception e) {
                        logger.error("查询IP段接口中,传回IP段的掩码位数异常", e);
                    }

                    ipSegment.setStartIp(firstIp);
                    ipSegment.setEndIp(lastIp);
                    ipSegment.setIpSubnet(subnet);
                    ipSegment.setIpTotal(IpSegmentUtil.getIpSegmentSize(firstIp, lastIp).toString());
                    ipSegmentList.add(ipSegment);
                }
            }
        } else {
        	logger.error("从资源池获取IP段信息出错, " + queryResp.getResultCode() + ", " + queryResp.getFaultstring());
        	// 由于运营不能通过资源池自动创建vlan和ip段，所以每次boss同步过来的请求可能并不能自动创建vlan和ip段，
        	// 所以这种情况发生时，让企业信息、boss订单信息入库，vlan和ip段相关信息就无法正常创建了。即这种情况发生时不做全流程回滚。即 此处不做throw new BossException(""); 因为向上抛异常整个流程就数据回滚了
        }
		return ipSegmentList;
	}

	private CompOrderT makeOrderInfo(CorporationInfo corporationInfo, CompResPoolT compResPool) {
		String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.IP.getParentCode());
		String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.IP.toString());
		String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.IP.toString());
		CompOrderT compOrder = new CompOrderT();
		compOrder.setOrderId(orderId);
		compOrder.setStatus("3"); // 已生效
		compOrder.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compOrder.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compOrder.setCreateUser("BOSS");
		compOrder.setUpdateUser("BOSS");
		compOrder.setCaseId(caseId);
		compOrder.setParentId(parentId);
		compOrder.setCaseType("12");
		compOrder.setAppId(corporationInfo.getCorpId());
		compOrder.setResPoolId(compResPool.getResPoolId());
		return compOrder;
	}
	
	private RPPIPApplyReq makeIPApplyReq(CorporationInfo corporationInfo, CompResPoolT compResPool, String ipsegmentURI) {
		RPPIPApplyReq req = new RPPIPApplyReq();
		req.setAppId(corporationInfo.getCorpId());
		req.setAppName(corporationInfo.getCorpName());
		req.setCreateModel(IPCreateModel.CustomModel);
		req.setIpSegmentURI(ipsegmentURI);
		req.setResourcePoolId(compResPool.getResPoolId());
		req.setSerialNum(seqCen.generatorSerialNum());
		req.setCount(1); // 只创建一个
		req.setIpProType(IPProType.IPV4);
		req.setIpType(IPType.PRIVATE_IP);
		return req;
	}

	private CompCaseIpsegmentT makeIpsegmentInfo(CorporationInfo corporationInfo, CompResPoolT compResPool,
			CompOrderT compOrder, RPPIPApplyResp resp) {
		CompCaseIpsegmentT compCaseIpsegment = new CompCaseIpsegmentT();
		List<String> ips = null;
		try {
			ips = IpSegmentUtil.getExtremeIp(resp.getIpSubNet()); // TODO
//			ips = IpSegmentUtil.getExtremeIp("10.152.62.1/24");  //TODO
		} catch (Exception e) {
			logger.error("查询IP段接口中,传回IP段的掩码位数异常", e);
		}
		compCaseIpsegment.setCaseId(compOrder.getCaseId());
		compCaseIpsegment.setIpsegmentId(resp.getIpSegmentURI());  // TODO
//		compCaseIpsegment.setIpsegmentId("8989889"); // TODO
		compCaseIpsegment.setIpsegmentDesc("IP段" + corporationInfo.getCorpName());
		compCaseIpsegment.setOrderId(compOrder.getOrderId());
		compCaseIpsegment.setAppId(corporationInfo.getCorpId());
		compCaseIpsegment.setResPoolId(compResPool.getResPoolId());
		compCaseIpsegment.setIpSubnet(resp.getIpSubNet());  // TODO 
//		compCaseIpsegment.setIpSubnet("10.152.62.1/24");  // TODO
		compCaseIpsegment.setStartIp(ips.get(0));
		compCaseIpsegment.setEndIp(ips.get(1));
		compCaseIpsegment.setIpTotal(IpSegmentUtil.getIpSegmentSize(ips.get(0), ips.get(1)).toString());
		compCaseIpsegment.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compCaseIpsegment.setCreateUser("BOSS");
		compCaseIpsegment.setReleased("0");
		return compCaseIpsegment;
	}
	
	public RPPIPApplyResp createIpSegment(RPPIPApplyReq ipReq) {
		RuntimeContext req = new RuntimeContext();
		req.setAttribute(RPPBaseReq.REQ_BODY, ipReq);
		HttpSyncRespData resp = null;
		try {
			resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);
		} catch (IOException e) {
			logger.error("向资源池代理系统发送请求失败，IO错误", e);
			return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
		} catch (InvalidProtocolException e) {
			logger.error("向资源池代理系统发送请求失败，无效的http协议", e);
			return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议");
		} catch (Exception e) {
			logger.error("向资源池代理系统发送请求失败，自服务系统内部错误", e);
			return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
		}
		return assembleResp(resp);
	}

	private RPPIPApplyResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        RPPIPApplyResp ipResp = new RPPIPApplyResp();
        ipResp.setResultCode(code.toString());
        ipResp.setResultMessage(errorMessage);
        return ipResp;
    }
	
	private RPPIPApplyResp assembleResp(HttpSyncRespData resp) {
        RPPIPApplyResp ipResp = new RPPIPApplyResp();
        ipResp = (RPPIPApplyResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
        ipResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        return ipResp;
    }
	
	public RPPIPQueryResp queryIpSegment(RPPIPQueryReq ipReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, ipReq);
        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(req, queryUrl, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error("向资源池代理系统发送请求失败，IO错误", e);
            return queryAssmblErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error("向资源池代理系统发送请求失败，无效的http协议", e);
            return queryAssmblErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error("向资源池代理系统发送请求失败，自服务系统内部错误", e);
            return queryAssmblErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }
        return queryAssembleResp(resp);
    }

    private RPPIPQueryResp queryAssmblErrorResp(CommonStatusCode code, String errorMessage) {
        RPPIPQueryResp ipResp = new RPPIPQueryResp();
        ipResp.setResultCode(code.toString());
        ipResp.setResultMessage(errorMessage);
        return ipResp;
    }

    private RPPIPQueryResp queryAssembleResp(HttpSyncRespData resp) {
        RPPIPQueryResp ipResp = new RPPIPQueryResp();
        ipResp = (RPPIPQueryResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
        ipResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        return ipResp;
    }
	
}
