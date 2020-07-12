package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompAppT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompAppTMapper;

@Repository
public class AppDao {

	@Autowired
	private  CompAppTMapper compAppTMapper;
	
	public CompAppT selectAppByCorpId(String corporationId) {
		return this.compAppTMapper.selectByPrimaryKey(corporationId);
	}
	
	public int insertApp(CompAppT compApp) {
		return this.compAppTMapper.insert(compApp);
	}
}
