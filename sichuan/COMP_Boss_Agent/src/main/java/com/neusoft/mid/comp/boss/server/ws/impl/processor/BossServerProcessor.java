package com.neusoft.mid.comp.boss.server.ws.impl.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.comp.boss.exception.BossException;
import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.mybatis.bean.CompAppT;
import com.neusoft.mid.comp.boss.server.ws.msg.CorporationInfo;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.OrderResourceInfoReq;
import com.neusoft.mid.comp.boss.service.BossOrderService;
import com.neusoft.mid.comp.boss.service.IpSegmentService;
import com.neusoft.mid.comp.boss.service.UserAppService;
import com.neusoft.mid.comp.boss.service.VlanService;

public class BossServerProcessor {

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private VlanService vlanService;

	@Autowired
	private IpSegmentService ipSegmentService;

	@Autowired
	private BossOrderService bossOrderService;
	
	private Logger logger = LoggerFactory.getLogger(BossServerProcessor.class);

	public void processOrderResource(OrderResourceInfoReq req) throws DbException, BossException {

		CorporationInfo corporationInfo = req.getCorporationInfo();

		CompAppT compApp = userAppService.selectAppByCorpId(corporationInfo);

		try {
			if (compApp == null) {
				userAppService.makeAppUser(corporationInfo);
				// 默认创建vlan
				vlanService.makeVlan(corporationInfo);
				// 默认创建IP段
				ipSegmentService.makeIpSegment(corporationInfo);
			}

			// 将实时BOSS订购实例号码入库(此时只有boss的订单号码)
			bossOrderService.inserBossOrder(req.getProductOrderNumber());
			// 将企业(业务)ID和boss订购实例号进行绑定
			bossOrderService.insertCorporationBoss(corporationInfo.getCorpId(), req.getProductOrderNumber());
		} catch (Exception e) {
			String failStr = "process boss order info failed! CorporationId is : " + corporationInfo.getCorpId()
					+ ", productOrderNumber is :" + req.getProductOrderNumber() + e.getMessage();
			logger.error(failStr, e);
			throw new BossException(failStr, e);
		}

	}
}
