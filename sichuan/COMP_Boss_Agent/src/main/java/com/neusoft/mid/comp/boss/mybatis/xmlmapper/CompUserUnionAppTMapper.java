/**
 * 
 */
package com.neusoft.mid.comp.boss.mybatis.xmlmapper;

import com.neusoft.mid.comp.boss.mybatis.bean.CompUserUnionAppT;

/**
 * @author li-lei
 *
 */
public interface CompUserUnionAppTMapper {

	// 根据appId查找用户信息
	public CompUserUnionAppT selectUserByAppId(String appId);
}
