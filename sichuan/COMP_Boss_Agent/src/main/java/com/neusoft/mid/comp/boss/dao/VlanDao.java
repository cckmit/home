package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompCaseVlanT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompCaseVlanTMapper;

@Repository
public class VlanDao {

	@Autowired
	private CompCaseVlanTMapper compCaseVlanTMapper;
	
	public int insertVlan(CompCaseVlanT compCaseVlanT) {
		return this.compCaseVlanTMapper.insert(compCaseVlanT);
	}
}
