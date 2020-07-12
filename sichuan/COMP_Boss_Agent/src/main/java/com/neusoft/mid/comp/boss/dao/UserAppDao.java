package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompAuthorityUserAppT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompUserUnionAppT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompAuthorityUserAppTMapper;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompUserUnionAppTMapper;

@Repository
public class UserAppDao {

	@Autowired
	private CompAuthorityUserAppTMapper compAuthorityUserAppTMapper;
	
	@Autowired
	private CompUserUnionAppTMapper compUserUnionAppTMapper;

	public int insertUserApp(CompAuthorityUserAppT compAuthorityUserApp) {
		return this.compAuthorityUserAppTMapper.insert(compAuthorityUserApp);
	}
	
	public CompUserUnionAppT selectUserByAppId(String appId) {
		return this.compUserUnionAppTMapper.selectUserByAppId(appId);
	}

}
