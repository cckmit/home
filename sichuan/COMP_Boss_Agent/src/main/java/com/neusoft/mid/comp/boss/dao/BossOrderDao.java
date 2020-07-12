package com.neusoft.mid.comp.boss.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompBossOrderT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompBossOrderTExample;
import com.neusoft.mid.comp.boss.mybatis.bean.CompCorporationBossT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompCorporationBossTExample;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompBossOrderTMapper;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompCorporationBossTMapper;

@Repository
public class BossOrderDao {

	@Autowired
	private  CompBossOrderTMapper compBossOrderTMapper;
	
	@Autowired
	private CompCorporationBossTMapper compCorporationBossTMapper;
	
	public int insertBossOrder(CompBossOrderT compBossOrderT) {
		return this.compBossOrderTMapper.insert(compBossOrderT);
	}
	
	public int updateBossOrder(CompBossOrderT compBossOrderT, String bossOrderId) {
		 CompBossOrderTExample example = new CompBossOrderTExample();
		 example.createCriteria().andBossOrderIdEqualTo(bossOrderId);
		return this.compBossOrderTMapper.updateByExampleSelective(compBossOrderT, example);
	}
	
	public int insertCorporationBoss(CompCorporationBossT compCorporationBoss) {
		return this.compCorporationBossTMapper.insert(compCorporationBoss);
	}
	
	public List<CompCorporationBossT> selectCorporationBossList(String bossOrderId) {
		CompCorporationBossTExample example = new CompCorporationBossTExample();
		example.createCriteria().andBossOrderIdEqualTo(bossOrderId);
		return compCorporationBossTMapper.selectByExample(example);
	}
}
