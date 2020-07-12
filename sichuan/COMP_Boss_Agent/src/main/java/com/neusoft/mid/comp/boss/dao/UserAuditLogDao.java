package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompUserAuditLogT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompUserAuditLogTMapper;

@Repository
public class UserAuditLogDao {

	@Autowired
	private CompUserAuditLogTMapper compUserAuditLogTMapper;
	
	public int insertUserAuditLog(CompUserAuditLogT compUserAuditLog ) {
		return this.compUserAuditLogTMapper.insert(compUserAuditLog);
	}
	
}
