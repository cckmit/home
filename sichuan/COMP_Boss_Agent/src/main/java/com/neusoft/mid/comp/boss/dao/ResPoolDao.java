package com.neusoft.mid.comp.boss.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompResPoolT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompResPoolTExample;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompResPoolTMapper;

@Repository
public class ResPoolDao {

	@Autowired
	private  CompResPoolTMapper compResPoolTMapper;
	
	public CompResPoolT selectResPool() {
		CompResPoolTExample example = new CompResPoolTExample();
		example.createCriteria().andStatusEqualTo("1");
		return this.compResPoolTMapper.selectByExample(example).get(0);
	}
}
