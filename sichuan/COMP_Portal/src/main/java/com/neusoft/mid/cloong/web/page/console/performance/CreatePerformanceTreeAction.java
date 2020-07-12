/*******************************************************************************
 * @(#)CreateTreeAction.java 2014-12-31
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.performance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.CreateTreeInfo;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 生成菜单树Action
 * 
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class CreatePerformanceTreeAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService
			.getLogger(CreatePerformanceTreeAction.class);

	private String result;

	private JSONArray jsonArray = new JSONArray();

	@SuppressWarnings("unchecked")
	public String execute() {
		String userId = getCurrentUserId();

		/* 一级节点-根节点 */
		JSONObject jsonObjectRoot = new JSONObject();
		jsonObjectRoot.put("id", "xnst");
		jsonObjectRoot.put("pId", "-1");
		jsonObjectRoot.put("name", "所有业务");
		jsonObjectRoot.put("t", "所有业务");
		jsonObjectRoot.put("pName", "");
		jsonObjectRoot.put("nodeType", "top");
		jsonObjectRoot.put("curFun", "xnst");
		jsonObjectRoot.put("skipUrl", "performanceOverview.action");
		jsonObjectRoot.put("open", true);
		jsonObjectRoot.put("selected", true);
		jsonObjectRoot.put("icon", "../images/tree_res.png");
		jsonArray.add(jsonObjectRoot);

		/* 二级节点 */
		CreateTreeInfo createTreeInfoQuery = new CreateTreeInfo();
		createTreeInfoQuery.setUserId(this.getCurrentUserId());
		List<CreateTreeInfo> createTreeInfoList = null;
		try {
			createTreeInfoList = ibatisDAO.getData("queryAppInfoByUserId",
					createTreeInfoQuery);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"创建资源视图菜单树时查询错误！原因：查询业务时，数据库异常。", e);
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"创建资源视图菜单树时查询错误！原因：查询业务时，数据库异常。", e);
			return ConstantEnum.ERROR.toString();
		}

		CreateTreeInfo createTreeInfo = null;
		JSONObject jsonObjectApp = null;
		JSONObject jsonObjectAppRes = null;
		for (int i = 0; i < createTreeInfoList.size(); i++) {
			createTreeInfo = createTreeInfoList.get(i);

			/* 二级节点-业务 */
			jsonObjectApp = new JSONObject();
			jsonObjectApp.put("id", "xnst-" + createTreeInfo.getAppId());
			jsonObjectApp.put("pId", "xnst");

			String appName = createTreeInfo.getAppName();
			jsonObjectApp.put("name", this.interceptStr(appName, 10, ""));
			jsonObjectApp.put("t", appName);

			jsonObjectApp.put("pName", "所有业务");
			jsonObjectApp.put("nodeType", "app");
			jsonObjectApp.put("curFun", "xnst");
			jsonObjectApp.put("skipUrl", "performanceOverview.action?appIdFromTree=" + createTreeInfo.getAppId());
			jsonObjectApp.put("icon", "../images/tree_business.png");

			if (pnodeId != null && pnodeId.equals(jsonObjectApp.get("id"))) {
				jsonObjectApp.put("open", true);
			} else {
				jsonObjectApp.put("open", false);
			}
			jsonArray.add(jsonObjectApp);

			/* 三级节点 */
			
			List<VMInstanceInfo> vmList = null;
			VMInstanceInfo vmInfo = new VMInstanceInfo();
			vmInfo.setAppId(createTreeInfo.getAppId());
			try {
				vmList = ibatisDAO.getData("queryVmsByAppId", vmInfo);
			} catch (SQLException e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						"创建性能菜单树时查询错误！原因：查询业务对应虚拟机时，数据库异常。", e);
				return ConstantEnum.ERROR.toString();
			} catch (Exception e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						"创建性能菜单树时查询错误！原因：查询业务对应虚拟机时，数据库异常。", e);
				return ConstantEnum.ERROR.toString();
			}
			
			for (VMInstanceInfo vm : vmList) {
				/* 三级节点-虚拟机 */
				jsonObjectAppRes = new JSONObject();
				jsonObjectAppRes.put("id", "xnst-" + createTreeInfo.getAppId() + "-" + vm.getVmId());
				jsonObjectAppRes.put("pId", "xnst-" + createTreeInfo.getAppId());
				jsonObjectAppRes.put("name", this.interceptStr(vm.getVmName(), 10, ""));
				jsonObjectAppRes.put("t", vm.getVmName());
				jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
				jsonObjectAppRes.put("nodeType", "app_vm");
				jsonObjectAppRes.put("curFun", "xnst");
				jsonObjectAppRes.put("skipUrl", "performanceVmview.action?vmId=" + vm.getVmId());
				jsonObjectAppRes.put("open", false);
				jsonObjectAppRes.put("icon", "../images/tree_vm.png");
				jsonArray.add(jsonObjectAppRes);
			}

		}

		result = jsonArray.toString();

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(result.getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * getCurrentUserId 获取当前登录用户
	 * 
	 * @return 用户ID
	 */
	protected UserBean getCurrentUser() {
		UserBean user = (UserBean) ServletActionContext.getRequest()
				.getSession().getAttribute(SessionKeys.userInfo.toString());
		return user;
	}

	/**
	 * 截取字符串　超出的字符用symbol代替
	 * 
	 * @param len
	 *            字符串长度（GBK编码），1个汉字1个单位长度，1个全角数字或全角字母（不分大小写）1个单位长度，两个半角数字或两个半角字母
	 *            （不分大小写）1个单位长度
	 * @param str
	 *            待截字符串
	 * @param symbol
	 *            截取后，补在字符串后面的符号，一般为...
	 * @return String
	 */
	private String interceptStr(String str, int len, String symbol) {
		int iLen = len * 2;
		int counterOfDoubleByte = 0; // 统计会不会截取到半个汉字，能被2整除就没有半个汉字，不能被2整除截取的串最后就是半个汉字
		String strRet = "";
		try {
			if (null != str) {
				byte[] b = str.getBytes("GBK");
				if (b.length > iLen) {
					for (int i = 0; i < iLen; i++) {
						if (b[i] < 0) { // 汉字拆成的两个字节都是小于0的
							counterOfDoubleByte++;
						}
					}
					if (counterOfDoubleByte % 2 == 0) {
						strRet = new String(b, 0, iLen, "GBK") + symbol;
					} else { // 截取的串，最后是半个汉字
						strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
					}
				} else {
					strRet = str;
				}
			}
		} catch (Exception ex) {
			return str.substring(0, len);
		}

		return strRet;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
