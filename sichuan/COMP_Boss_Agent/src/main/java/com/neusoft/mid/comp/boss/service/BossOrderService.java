package com.neusoft.mid.comp.boss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mid.comp.boss.dao.BossOrderDao;
import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.mybatis.bean.CompBossOrderT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompCorporationBossT;
import com.neusoft.mid.comp.boss.util.DateParse;

@Service
public class BossOrderService {

	@Autowired
	private  BossOrderDao bossOrderDao;
	
	public void inserBossOrder(String productOrderNumber) throws DbException {
		CompBossOrderT bossOrder = new CompBossOrderT();
		bossOrder.setBossOrderId(productOrderNumber);
		bossOrder.setTimeStamp(DateParse.generateDateFormatyyyyMMddHHmmss());
		bossOrderDao.insertBossOrder(bossOrder);
	}
	
	public void updateBossOrder(CompBossOrderT bossOrder, String bossOrderId) throws DbException {
		bossOrderDao.updateBossOrder(bossOrder, bossOrderId);
	}
	
	public void insertCorporationBoss(String corporationId, String bossOrderId) throws DbException {
		CompCorporationBossT corporationBoss = new CompCorporationBossT();
		corporationBoss.setAppId(corporationId);
		corporationBoss.setBossOrderId(bossOrderId);
		bossOrderDao.insertCorporationBoss(corporationBoss);
	}
	
	public List<CompCorporationBossT> selectCorporationBossList(String bossOrderId) {
		return this.bossOrderDao.selectCorporationBossList(bossOrderId);
	}
	
}
