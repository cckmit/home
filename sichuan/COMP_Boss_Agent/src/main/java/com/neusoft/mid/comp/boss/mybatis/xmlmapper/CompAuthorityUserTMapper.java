package com.neusoft.mid.comp.boss.mybatis.xmlmapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.neusoft.mid.comp.boss.mybatis.bean.CompAuthorityUserT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompAuthorityUserTExample;

public interface CompAuthorityUserTMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int countByExample(CompAuthorityUserTExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int deleteByExample(CompAuthorityUserTExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int deleteByPrimaryKey(String userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int insert(CompAuthorityUserT record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int insertSelective(CompAuthorityUserT record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	List<CompAuthorityUserT> selectByExample(CompAuthorityUserTExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	CompAuthorityUserT selectByPrimaryKey(String userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int updateByExampleSelective(@Param("record") CompAuthorityUserT record,
			@Param("example") CompAuthorityUserTExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int updateByExample(@Param("record") CompAuthorityUserT record,
			@Param("example") CompAuthorityUserTExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int updateByPrimaryKeySelective(CompAuthorityUserT record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_authority_user_t
	 * @mbggenerated  Fri May 04 17:34:15 CST 2018
	 */
	int updateByPrimaryKey(CompAuthorityUserT record);
}