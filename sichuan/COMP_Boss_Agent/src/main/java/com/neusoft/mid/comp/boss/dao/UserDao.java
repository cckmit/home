package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompAuthorityUserT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompAuthorityUserTMapper;

@Repository
public class UserDao {

	@Autowired
	private CompAuthorityUserTMapper compAuthorityUserTMapper;
	
	public int insertUser(CompAuthorityUserT compAuthorityUser) {
		return this.compAuthorityUserTMapper.insert(compAuthorityUser);
	}
}
