package com.neusoft.mid.comp.boss.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mid.comp.boss.dao.AppDao;
import com.neusoft.mid.comp.boss.dao.UserAppDao;
import com.neusoft.mid.comp.boss.dao.UserAuditLogDao;
import com.neusoft.mid.comp.boss.dao.UserDao;
import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.mybatis.bean.CompAppT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompAuthorityUserAppT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompAuthorityUserT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompUserAuditLogT;
import com.neusoft.mid.comp.boss.mybatis.bean.CompUserUnionAppT;
import com.neusoft.mid.comp.boss.server.ws.msg.CorporationInfo;
import com.neusoft.mid.comp.boss.util.DateParse;

@Service
public class UserAppService {

	@Autowired
	private AppDao appDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAppDao userAppDao;
	
	@Autowired
	private UserAuditLogDao userAuditLogDao;
	
	private Logger logger = LoggerFactory.getLogger(UserAppService.class);

	public CompAppT selectAppByCorpId(CorporationInfo corporationInfo) {
		return this.appDao.selectAppByCorpId(corporationInfo.getCorpId());
	}

	public void makeAppUser(CorporationInfo corporationInfo) throws DbException {
		try {
			// 录入用户表
			CompAuthorityUserT compAuthorityUser = makeUserBean(corporationInfo);
			userDao.insertUser(compAuthorityUser);
			logger.info("insert user data successful!");

			// 录入业务表
			CompAppT compAppTmp = makeAppBean(corporationInfo);
			appDao.insertApp(compAppTmp);
			logger.info("insert app data successful!");

			// 录入用户业务关联表
			CompAuthorityUserAppT compAuthorityUserApp = makeUserAppBean(corporationInfo);
			userAppDao.insertUserApp(compAuthorityUserApp);
			// 同一个企业(业务)既给同步的同名用户使用又分配给admin使用
			CompAuthorityUserAppT adminUserApp = makeAdminAppBean(corporationInfo);
			userAppDao.insertUserApp(adminUserApp);
			logger.info("insert user & app data successful!");

			// 录入用户审批表
			CompUserAuditLogT compUserAuditLog = makeUserAuditBean(corporationInfo);
			userAuditLogDao.insertUserAuditLog(compUserAuditLog);
			logger.info("insert user audit log data successful!");
		} catch (Exception e) {
            throw new DbException("create new app data failed!", e);
        }
		
		
	}

	private CompUserAuditLogT makeUserAuditBean(CorporationInfo corporationInfo) {
		CompUserAuditLogT compUserAuditLog = new CompUserAuditLogT();
		compUserAuditLog.setUserId(corporationInfo.getCorpId());
		compUserAuditLog.setStatus("0"); // 用户审批通过
		compUserAuditLog.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compUserAuditLog.setAuditUser("BOSS");
		return compUserAuditLog;
	}

	private CompAuthorityUserAppT makeUserAppBean(CorporationInfo corporationInfo) {
		CompAuthorityUserAppT compAuthorityUserApp = new CompAuthorityUserAppT();
		compAuthorityUserApp.setAppId(corporationInfo.getCorpId());
		compAuthorityUserApp.setUserId(corporationInfo.getCorpId());
		compAuthorityUserApp.setAppbindStatus("2"); // 业务绑定状态审批通过
		compAuthorityUserApp.setAuditUser("BOSS");
		compAuthorityUserApp.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		return compAuthorityUserApp;
	}
	
	// 同一个企业(业务)既给同步的同名用户使用又分配给admin使用
	private CompAuthorityUserAppT makeAdminAppBean(CorporationInfo corporationInfo) {
		CompAuthorityUserAppT compAuthorityUserApp = new CompAuthorityUserAppT();
		compAuthorityUserApp.setAppId(corporationInfo.getCorpId());
		compAuthorityUserApp.setUserId("admin");
		compAuthorityUserApp.setAppbindStatus("2"); // 业务绑定状态审批通过
		compAuthorityUserApp.setAuditUser("BOSS");
		compAuthorityUserApp.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		return compAuthorityUserApp;
	}
	

	private CompAppT makeAppBean(CorporationInfo corporationInfo) {
		CompAppT compAppTmp = new CompAppT();
		compAppTmp.setAppId(corporationInfo.getCorpId());
		compAppTmp.setAppName(corporationInfo.getCorpName());
		compAppTmp.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compAppTmp.setCreateUser("BOSS");
		compAppTmp.setCreateFlag("2");
		if (corporationInfo.getDescription() != null && !corporationInfo.getDescription().equals("")) {
			compAppTmp.setDescription(corporationInfo.getDescription());
		}
		return compAppTmp;
	}

	private CompAuthorityUserT makeUserBean(CorporationInfo corporationInfo) {
		CompAuthorityUserT compAuthorityUser = new CompAuthorityUserT();
		compAuthorityUser.setUserId(corporationInfo.getCorpId());
		compAuthorityUser.setUserName(corporationInfo.getCorpName());
		compAuthorityUser.setPassword(
				"AEUAMQAwAEEARABDADMAOQA0ADkAQgBBADUAOQBBAEIAQgBFADUANgBFADAANQA3AEYAMgAwAEYAOAA4ADMARQ==");
		compAuthorityUser.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compAuthorityUser.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		compAuthorityUser.setStatus("0"); // 启用
		compAuthorityUser.setMobile(corporationInfo.getPhone());
		compAuthorityUser.setEmail(corporationInfo.getEmail());
		compAuthorityUser.setCreateUser("BOSS");
		if (corporationInfo.getAddress() != null && !corporationInfo.getAddress().equals("")) {
			compAuthorityUser.setAddress(corporationInfo.getAddress());
		}
		if (corporationInfo.getTelephone() != null && !corporationInfo.getTelephone().equals("")) {
			compAuthorityUser.setTelphone(corporationInfo.getTelephone());
		}
		if (corporationInfo.getDepartmentName() != null && !corporationInfo.getDepartmentName().equals("")) {
			compAuthorityUser.setDepartmentName(corporationInfo.getDepartmentName());
		}
		if (corporationInfo.getDescription() != null && !corporationInfo.getDescription().equals("")) {
			compAuthorityUser.setDescription(corporationInfo.getDescription());
		}
		return compAuthorityUser;
	}
	
	public CompUserUnionAppT selectUserByAppId(String appId) {
		return this.userAppDao.selectUserByAppId(appId);
	}
}
