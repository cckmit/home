package com.neusoft.mid.comp.boss.mybatis.bean;

import java.util.ArrayList;
import java.util.List;

public class CompAppTExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public CompAppTExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andAppIdIsNull() {
			addCriterion("APP_ID is null");
			return (Criteria) this;
		}

		public Criteria andAppIdIsNotNull() {
			addCriterion("APP_ID is not null");
			return (Criteria) this;
		}

		public Criteria andAppIdEqualTo(String value) {
			addCriterion("APP_ID =", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdNotEqualTo(String value) {
			addCriterion("APP_ID <>", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdGreaterThan(String value) {
			addCriterion("APP_ID >", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdGreaterThanOrEqualTo(String value) {
			addCriterion("APP_ID >=", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdLessThan(String value) {
			addCriterion("APP_ID <", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdLessThanOrEqualTo(String value) {
			addCriterion("APP_ID <=", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdLike(String value) {
			addCriterion("APP_ID like", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdNotLike(String value) {
			addCriterion("APP_ID not like", value, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdIn(List<String> values) {
			addCriterion("APP_ID in", values, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdNotIn(List<String> values) {
			addCriterion("APP_ID not in", values, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdBetween(String value1, String value2) {
			addCriterion("APP_ID between", value1, value2, "appId");
			return (Criteria) this;
		}

		public Criteria andAppIdNotBetween(String value1, String value2) {
			addCriterion("APP_ID not between", value1, value2, "appId");
			return (Criteria) this;
		}

		public Criteria andAppNameIsNull() {
			addCriterion("APP_NAME is null");
			return (Criteria) this;
		}

		public Criteria andAppNameIsNotNull() {
			addCriterion("APP_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andAppNameEqualTo(String value) {
			addCriterion("APP_NAME =", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameNotEqualTo(String value) {
			addCriterion("APP_NAME <>", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameGreaterThan(String value) {
			addCriterion("APP_NAME >", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameGreaterThanOrEqualTo(String value) {
			addCriterion("APP_NAME >=", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameLessThan(String value) {
			addCriterion("APP_NAME <", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameLessThanOrEqualTo(String value) {
			addCriterion("APP_NAME <=", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameLike(String value) {
			addCriterion("APP_NAME like", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameNotLike(String value) {
			addCriterion("APP_NAME not like", value, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameIn(List<String> values) {
			addCriterion("APP_NAME in", values, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameNotIn(List<String> values) {
			addCriterion("APP_NAME not in", values, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameBetween(String value1, String value2) {
			addCriterion("APP_NAME between", value1, value2, "appName");
			return (Criteria) this;
		}

		public Criteria andAppNameNotBetween(String value1, String value2) {
			addCriterion("APP_NAME not between", value1, value2, "appName");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNull() {
			addCriterion("DESCRIPTION is null");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNotNull() {
			addCriterion("DESCRIPTION is not null");
			return (Criteria) this;
		}

		public Criteria andDescriptionEqualTo(String value) {
			addCriterion("DESCRIPTION =", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotEqualTo(String value) {
			addCriterion("DESCRIPTION <>", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThan(String value) {
			addCriterion("DESCRIPTION >", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("DESCRIPTION >=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThan(String value) {
			addCriterion("DESCRIPTION <", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThanOrEqualTo(String value) {
			addCriterion("DESCRIPTION <=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLike(String value) {
			addCriterion("DESCRIPTION like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotLike(String value) {
			addCriterion("DESCRIPTION not like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionIn(List<String> values) {
			addCriterion("DESCRIPTION in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotIn(List<String> values) {
			addCriterion("DESCRIPTION not in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionBetween(String value1, String value2) {
			addCriterion("DESCRIPTION between", value1, value2, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotBetween(String value1, String value2) {
			addCriterion("DESCRIPTION not between", value1, value2, "description");
			return (Criteria) this;
		}

		public Criteria andCreateFlagIsNull() {
			addCriterion("CREATE_FLAG is null");
			return (Criteria) this;
		}

		public Criteria andCreateFlagIsNotNull() {
			addCriterion("CREATE_FLAG is not null");
			return (Criteria) this;
		}

		public Criteria andCreateFlagEqualTo(String value) {
			addCriterion("CREATE_FLAG =", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagNotEqualTo(String value) {
			addCriterion("CREATE_FLAG <>", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagGreaterThan(String value) {
			addCriterion("CREATE_FLAG >", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagGreaterThanOrEqualTo(String value) {
			addCriterion("CREATE_FLAG >=", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagLessThan(String value) {
			addCriterion("CREATE_FLAG <", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagLessThanOrEqualTo(String value) {
			addCriterion("CREATE_FLAG <=", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagLike(String value) {
			addCriterion("CREATE_FLAG like", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagNotLike(String value) {
			addCriterion("CREATE_FLAG not like", value, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagIn(List<String> values) {
			addCriterion("CREATE_FLAG in", values, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagNotIn(List<String> values) {
			addCriterion("CREATE_FLAG not in", values, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagBetween(String value1, String value2) {
			addCriterion("CREATE_FLAG between", value1, value2, "createFlag");
			return (Criteria) this;
		}

		public Criteria andCreateFlagNotBetween(String value1, String value2) {
			addCriterion("CREATE_FLAG not between", value1, value2, "createFlag");
			return (Criteria) this;
		}

		public Criteria andAppContactsIsNull() {
			addCriterion("APP_CONTACTS is null");
			return (Criteria) this;
		}

		public Criteria andAppContactsIsNotNull() {
			addCriterion("APP_CONTACTS is not null");
			return (Criteria) this;
		}

		public Criteria andAppContactsEqualTo(String value) {
			addCriterion("APP_CONTACTS =", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsNotEqualTo(String value) {
			addCriterion("APP_CONTACTS <>", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsGreaterThan(String value) {
			addCriterion("APP_CONTACTS >", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsGreaterThanOrEqualTo(String value) {
			addCriterion("APP_CONTACTS >=", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsLessThan(String value) {
			addCriterion("APP_CONTACTS <", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsLessThanOrEqualTo(String value) {
			addCriterion("APP_CONTACTS <=", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsLike(String value) {
			addCriterion("APP_CONTACTS like", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsNotLike(String value) {
			addCriterion("APP_CONTACTS not like", value, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsIn(List<String> values) {
			addCriterion("APP_CONTACTS in", values, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsNotIn(List<String> values) {
			addCriterion("APP_CONTACTS not in", values, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsBetween(String value1, String value2) {
			addCriterion("APP_CONTACTS between", value1, value2, "appContacts");
			return (Criteria) this;
		}

		public Criteria andAppContactsNotBetween(String value1, String value2) {
			addCriterion("APP_CONTACTS not between", value1, value2, "appContacts");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("CREATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("CREATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(String value) {
			addCriterion("CREATE_TIME =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(String value) {
			addCriterion("CREATE_TIME <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(String value) {
			addCriterion("CREATE_TIME >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
			addCriterion("CREATE_TIME >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(String value) {
			addCriterion("CREATE_TIME <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(String value) {
			addCriterion("CREATE_TIME <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLike(String value) {
			addCriterion("CREATE_TIME like", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotLike(String value) {
			addCriterion("CREATE_TIME not like", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<String> values) {
			addCriterion("CREATE_TIME in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<String> values) {
			addCriterion("CREATE_TIME not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(String value1, String value2) {
			addCriterion("CREATE_TIME between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(String value1, String value2) {
			addCriterion("CREATE_TIME not between", value1, value2, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateUserIsNull() {
			addCriterion("CREATE_USER is null");
			return (Criteria) this;
		}

		public Criteria andCreateUserIsNotNull() {
			addCriterion("CREATE_USER is not null");
			return (Criteria) this;
		}

		public Criteria andCreateUserEqualTo(String value) {
			addCriterion("CREATE_USER =", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotEqualTo(String value) {
			addCriterion("CREATE_USER <>", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserGreaterThan(String value) {
			addCriterion("CREATE_USER >", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
			addCriterion("CREATE_USER >=", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLessThan(String value) {
			addCriterion("CREATE_USER <", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLessThanOrEqualTo(String value) {
			addCriterion("CREATE_USER <=", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserLike(String value) {
			addCriterion("CREATE_USER like", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotLike(String value) {
			addCriterion("CREATE_USER not like", value, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserIn(List<String> values) {
			addCriterion("CREATE_USER in", values, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotIn(List<String> values) {
			addCriterion("CREATE_USER not in", values, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserBetween(String value1, String value2) {
			addCriterion("CREATE_USER between", value1, value2, "createUser");
			return (Criteria) this;
		}

		public Criteria andCreateUserNotBetween(String value1, String value2) {
			addCriterion("CREATE_USER not between", value1, value2, "createUser");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("UPDATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("UPDATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeEqualTo(String value) {
			addCriterion("UPDATE_TIME =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(String value) {
			addCriterion("UPDATE_TIME <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(String value) {
			addCriterion("UPDATE_TIME >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
			addCriterion("UPDATE_TIME >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(String value) {
			addCriterion("UPDATE_TIME <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
			addCriterion("UPDATE_TIME <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLike(String value) {
			addCriterion("UPDATE_TIME like", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotLike(String value) {
			addCriterion("UPDATE_TIME not like", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<String> values) {
			addCriterion("UPDATE_TIME in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<String> values) {
			addCriterion("UPDATE_TIME not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(String value1, String value2) {
			addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(String value1, String value2) {
			addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateUserIsNull() {
			addCriterion("UPDATE_USER is null");
			return (Criteria) this;
		}

		public Criteria andUpdateUserIsNotNull() {
			addCriterion("UPDATE_USER is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateUserEqualTo(String value) {
			addCriterion("UPDATE_USER =", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserNotEqualTo(String value) {
			addCriterion("UPDATE_USER <>", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserGreaterThan(String value) {
			addCriterion("UPDATE_USER >", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
			addCriterion("UPDATE_USER >=", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserLessThan(String value) {
			addCriterion("UPDATE_USER <", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserLessThanOrEqualTo(String value) {
			addCriterion("UPDATE_USER <=", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserLike(String value) {
			addCriterion("UPDATE_USER like", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserNotLike(String value) {
			addCriterion("UPDATE_USER not like", value, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserIn(List<String> values) {
			addCriterion("UPDATE_USER in", values, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserNotIn(List<String> values) {
			addCriterion("UPDATE_USER not in", values, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserBetween(String value1, String value2) {
			addCriterion("UPDATE_USER between", value1, value2, "updateUser");
			return (Criteria) this;
		}

		public Criteria andUpdateUserNotBetween(String value1, String value2) {
			addCriterion("UPDATE_USER not between", value1, value2, "updateUser");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table comp_app_t
	 * @mbggenerated  Fri May 04 17:33:30 CST 2018
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table comp_app_t
     *
     * @mbggenerated do_not_delete_during_merge Wed May 02 14:51:43 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}