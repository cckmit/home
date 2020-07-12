package com.neusoft.mid.comp.boss.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neusoft.mid.comp.boss.mybatis.bean.CompCaseIpsegmentT;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompCaseIpsegmentTMapper;
import com.neusoft.mid.comp.boss.mybatis.xmlmapper.CompOrderIpSegmentMapper;

@Repository
public class IpsegmentDao {

	@Autowired
	private CompCaseIpsegmentTMapper compCaseIpsegmentTMapper;
	
	@Autowired CompOrderIpSegmentMapper compOrderIpSegmentMapper;

	public int insertIpSegment(CompCaseIpsegmentT compCaseIpsegmentT) {
		return this.compCaseIpsegmentTMapper.insert(compCaseIpsegmentT);
	}
	
	public List<CompCaseIpsegmentT> selectIpSubnet() {
		return this.compOrderIpSegmentMapper.selectIpSubnet();
	}
}
