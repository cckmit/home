package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompOrderT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompOrderTMapper;

@Repository
public class OrderDao {

	@Autowired
	private CompOrderTMapper compOrderTMapper;
	
	public int insertOrder(CompOrderT compOrderT) {
		return this.compOrderTMapper.insert(compOrderT);
	}
}
