package com.neusoft.mid.comp.boss.mybatis.xmlmapper;

import java.util.List;

import com.neusoft.mid.comp.boss.mybatis.bean.CompCaseIpsegmentT;

public interface CompOrderIpSegmentMapper {

	// 查找ipSubnet 待审待创建的
	public List<CompCaseIpsegmentT> selectIpSubnet();
}
