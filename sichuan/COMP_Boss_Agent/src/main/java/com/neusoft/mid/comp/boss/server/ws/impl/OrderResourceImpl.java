/**
 * 
 */
package com.neusoft.mid.comp.boss.server.ws.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.mail.SendMail;
import com.neusoft.mid.comp.boss.mybatis.bean.CompBossOrderT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompUserUnionAppT;
import com.neusoft.mid.comp.boss.server.ws.OrderResource;
import com.neusoft.mid.comp.boss.server.ws.impl.processor.BossServerProcessor;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.CancelCorporationInfoReq;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.OrderResourceInfoReq;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.ResourceCreditControlInfoReq;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.ResourceInfoResp;
import com.neusoft.mid.comp.boss.service.BossOrderService;
import com.neusoft.mid.comp.boss.service.UserAppService;
import com.neusoft.mid.comp.boss.util.DateParse;

/**
 * @author li-lei
 *
 */

public class OrderResourceImpl implements OrderResource {

	@Autowired
	private BossOrderService bossOrderService;
	
	@Autowired
	private UserAppService userAppService;

	@Autowired
	private BossServerProcessor bossServerProcessor;
	
	@Value("${mailNoticeFlag}")
    private String mailNoticeFlag;
	
	@Value("${mail.notice.hello}")
	private String mailNoticeHello;
	
	@Value("${mail.notice.content}")
	private String mailNoticeContent;
	
	@Autowired
	private SendMail sendMail;

	private Logger logger = LoggerFactory.getLogger(OrderResourceImpl.class);

	@Override
	public ResourceInfoResp orderResource(OrderResourceInfoReq req) {
		ResourceInfoResp resourceInfoResp = new ResourceInfoResp();
		if ("0".equals(req.getProductType())) { // IaaS业务
			logger.info("Received BOSS order request, corporationId: " + req.getCorporationInfo().getCorpId()
					+ ", corporationName: " + req.getCorporationInfo().getCorpName() + ", productOrderNumber: "
					+ req.getProductOrderNumber() + ", productType: " + req.getProductType() + ", phone: "
					+ req.getCorporationInfo().getPhone() + ", email: " + req.getCorporationInfo().getEmail());
			try {
				bossServerProcessor.processOrderResource(req);
				resourceInfoResp.setRes_code("200");
				resourceInfoResp.setRes_desc(
						"received boss request successful!  CorporationId is : " + req.getCorporationInfo().getCorpId()
								+ ", productOrderNumber is :" + req.getProductOrderNumber());
			} catch (Exception e) {
				logger.error("operating BOSS order request failed! ", e);
				resourceInfoResp.setRes_code("404");
				resourceInfoResp.setRes_desc("operating BOSS order request failed! " + e);
			}

		} else {
			logger.error("CLOUD plat received not IAAS data!");
			resourceInfoResp.setRes_code("500");
			resourceInfoResp.setRes_desc("CLOUD plat received not IAAS data!");
		}
		return resourceInfoResp;
	}

	@Override
	public ResourceInfoResp resourceCreditControl(ResourceCreditControlInfoReq req) {
		ResourceInfoResp resourceInfoResp = new ResourceInfoResp();
		CompBossOrderT bossOrder = new CompBossOrderT();
		bossOrder.setBossOrderId(req.getProductOrderNumber());
		bossOrder.setOperateType(req.getOperateType());
		if (!"".equals(req.getReasonType()) || null != req.getReasonType()) {
			bossOrder.setReasonType(req.getReasonType());
		}
		// TODO 需要做具体operateType的操作
		bossOrder.setTimeStamp(DateParse.generateDateFormatyyyyMMddHHmmss());
		try {
			bossOrderService.updateBossOrder(bossOrder, req.getProductOrderNumber());
			resourceInfoResp.setRes_code("200");
			resourceInfoResp.setRes_desc("received boss credit control request successful!");
			
			if ("1".equals(mailNoticeFlag)) {
				// 从业务(企业)和boss订单号关联表中获取当前订单号对应的是哪个企业
				String appId = bossOrderService.selectCorporationBossList(req.getProductOrderNumber()).get(0).getAppId();
				CompUserUnionAppT user = userAppService.selectUserByAppId(appId);
				sendMail.intMail("虚拟资源欠费提醒", user.getEmail(), null);
				sendMail.setMessage(user.getUserName() + "," + mailNoticeHello + req.getProductOrderNumber() + mailNoticeContent);
//				sendMail.setMessage("hello<br>your order id is " + req.getProductOrderNumber() + "faif ea fa！<br>");
				sendMail.send();
			}
			
		} catch (DbException e) {
			logger.error("operating BOSS credit control request into DB failed!", e);
			resourceInfoResp.setRes_code("404");
			resourceInfoResp.setRes_desc("operating BOSS order request failed!" + e);
		}
		return resourceInfoResp;
	}

	@Override
	public ResourceInfoResp cancelCorporation(CancelCorporationInfoReq req) {
		ResourceInfoResp resourceInfoResp = new ResourceInfoResp();
		logger.info("received cancel corporation information: corporationId is: " + req.getCorpId() + ", operateType is : " + req.getOperateType());
		if ("1".equals(req.getOperateType())) {
			// TODO prepare cancel corporation
			resourceInfoResp.setRes_code("200");
			resourceInfoResp.setRes_desc("received boss prepare cancel corporation infomation OK!");
		} else if ("2".equals(req.getOperateType())) {
			// TODO cancel corporation
			resourceInfoResp.setRes_code("200");
			resourceInfoResp.setRes_desc("received boss cancel corporation infomation OK! ");
		}
		return resourceInfoResp;
	}

}
