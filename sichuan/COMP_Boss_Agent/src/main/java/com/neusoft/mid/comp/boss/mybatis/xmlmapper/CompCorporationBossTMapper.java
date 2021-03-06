package com.neusoft.mid.comp.boss.mybatis.xmlmapper;

import com.neusoft.mid.comp.boss.mybatis.bean.CompCorporationBossT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompCorporationBossTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompCorporationBossTMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int countByExample(CompCorporationBossTExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int deleteByExample(CompCorporationBossTExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int deleteByPrimaryKey(String appId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int insert(CompCorporationBossT record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int insertSelective(CompCorporationBossT record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    List<CompCorporationBossT> selectByExample(CompCorporationBossTExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    CompCorporationBossT selectByPrimaryKey(String appId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int updateByExampleSelective(@Param("record") CompCorporationBossT record, @Param("example") CompCorporationBossTExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int updateByExample(@Param("record") CompCorporationBossT record, @Param("example") CompCorporationBossTExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int updateByPrimaryKeySelective(CompCorporationBossT record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comp_corporation_boss_t
     *
     * @mbggenerated Tue May 15 15:02:55 CST 2018
     */
    int updateByPrimaryKey(CompCorporationBossT record);
}